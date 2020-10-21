package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AppJhipsterApp;
import com.mycompany.myapp.domain.Devis;
import com.mycompany.myapp.repository.DevisRepository;
import com.mycompany.myapp.service.DevisService;
import com.mycompany.myapp.service.dto.DevisDTO;
import com.mycompany.myapp.service.mapper.DevisMapper;
import com.mycompany.myapp.service.dto.DevisCriteria;
import com.mycompany.myapp.service.DevisQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DevisResource} REST controller.
 */
@SpringBootTest(classes = AppJhipsterApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DevisResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DevisRepository devisRepository;

    @Autowired
    private DevisMapper devisMapper;

    @Autowired
    private DevisService devisService;

    @Autowired
    private DevisQueryService devisQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDevisMockMvc;

    private Devis devis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Devis createEntity(EntityManager em) {
        Devis devis = new Devis()
            .name(DEFAULT_NAME);
        return devis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Devis createUpdatedEntity(EntityManager em) {
        Devis devis = new Devis()
            .name(UPDATED_NAME);
        return devis;
    }

    @BeforeEach
    public void initTest() {
        devis = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevis() throws Exception {
        int databaseSizeBeforeCreate = devisRepository.findAll().size();
        // Create the Devis
        DevisDTO devisDTO = devisMapper.toDto(devis);
        restDevisMockMvc.perform(post("/api/devis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(devisDTO)))
            .andExpect(status().isCreated());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeCreate + 1);
        Devis testDevis = devisList.get(devisList.size() - 1);
        assertThat(testDevis.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDevisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = devisRepository.findAll().size();

        // Create the Devis with an existing ID
        devis.setId(1L);
        DevisDTO devisDTO = devisMapper.toDto(devis);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevisMockMvc.perform(post("/api/devis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(devisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = devisRepository.findAll().size();
        // set the field null
        devis.setName(null);

        // Create the Devis, which fails.
        DevisDTO devisDTO = devisMapper.toDto(devis);


        restDevisMockMvc.perform(post("/api/devis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(devisDTO)))
            .andExpect(status().isBadRequest());

        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get all the devisList
        restDevisMockMvc.perform(get("/api/devis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devis.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get the devis
        restDevisMockMvc.perform(get("/api/devis/{id}", devis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(devis.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getDevisByIdFiltering() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        Long id = devis.getId();

        defaultDevisShouldBeFound("id.equals=" + id);
        defaultDevisShouldNotBeFound("id.notEquals=" + id);

        defaultDevisShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDevisShouldNotBeFound("id.greaterThan=" + id);

        defaultDevisShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDevisShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDevisByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get all the devisList where name equals to DEFAULT_NAME
        defaultDevisShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the devisList where name equals to UPDATED_NAME
        defaultDevisShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDevisByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get all the devisList where name not equals to DEFAULT_NAME
        defaultDevisShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the devisList where name not equals to UPDATED_NAME
        defaultDevisShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDevisByNameIsInShouldWork() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get all the devisList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDevisShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the devisList where name equals to UPDATED_NAME
        defaultDevisShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDevisByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get all the devisList where name is not null
        defaultDevisShouldBeFound("name.specified=true");

        // Get all the devisList where name is null
        defaultDevisShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDevisByNameContainsSomething() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get all the devisList where name contains DEFAULT_NAME
        defaultDevisShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the devisList where name contains UPDATED_NAME
        defaultDevisShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDevisByNameNotContainsSomething() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get all the devisList where name does not contain DEFAULT_NAME
        defaultDevisShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the devisList where name does not contain UPDATED_NAME
        defaultDevisShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDevisShouldBeFound(String filter) throws Exception {
        restDevisMockMvc.perform(get("/api/devis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devis.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restDevisMockMvc.perform(get("/api/devis/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDevisShouldNotBeFound(String filter) throws Exception {
        restDevisMockMvc.perform(get("/api/devis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDevisMockMvc.perform(get("/api/devis/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDevis() throws Exception {
        // Get the devis
        restDevisMockMvc.perform(get("/api/devis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        int databaseSizeBeforeUpdate = devisRepository.findAll().size();

        // Update the devis
        Devis updatedDevis = devisRepository.findById(devis.getId()).get();
        // Disconnect from session so that the updates on updatedDevis are not directly saved in db
        em.detach(updatedDevis);
        updatedDevis
            .name(UPDATED_NAME);
        DevisDTO devisDTO = devisMapper.toDto(updatedDevis);

        restDevisMockMvc.perform(put("/api/devis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(devisDTO)))
            .andExpect(status().isOk());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
        Devis testDevis = devisList.get(devisList.size() - 1);
        assertThat(testDevis.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();

        // Create the Devis
        DevisDTO devisDTO = devisMapper.toDto(devis);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevisMockMvc.perform(put("/api/devis")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(devisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        int databaseSizeBeforeDelete = devisRepository.findAll().size();

        // Delete the devis
        restDevisMockMvc.perform(delete("/api/devis/{id}", devis.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
