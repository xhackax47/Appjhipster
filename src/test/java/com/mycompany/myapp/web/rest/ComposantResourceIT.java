package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AppJhipsterApp;
import com.mycompany.myapp.domain.Composant;
import com.mycompany.myapp.repository.ComposantRepository;
import com.mycompany.myapp.service.ComposantService;
import com.mycompany.myapp.service.dto.ComposantDTO;
import com.mycompany.myapp.service.mapper.ComposantMapper;
import com.mycompany.myapp.service.dto.ComposantCriteria;
import com.mycompany.myapp.service.ComposantQueryService;

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
 * Integration tests for the {@link ComposantResource} REST controller.
 */
@SpringBootTest(classes = AppJhipsterApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ComposantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ComposantRepository composantRepository;

    @Autowired
    private ComposantMapper composantMapper;

    @Autowired
    private ComposantService composantService;

    @Autowired
    private ComposantQueryService composantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComposantMockMvc;

    private Composant composant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Composant createEntity(EntityManager em) {
        Composant composant = new Composant()
            .name(DEFAULT_NAME);
        return composant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Composant createUpdatedEntity(EntityManager em) {
        Composant composant = new Composant()
            .name(UPDATED_NAME);
        return composant;
    }

    @BeforeEach
    public void initTest() {
        composant = createEntity(em);
    }

    @Test
    @Transactional
    public void createComposant() throws Exception {
        int databaseSizeBeforeCreate = composantRepository.findAll().size();
        // Create the Composant
        ComposantDTO composantDTO = composantMapper.toDto(composant);
        restComposantMockMvc.perform(post("/api/composants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(composantDTO)))
            .andExpect(status().isCreated());

        // Validate the Composant in the database
        List<Composant> composantList = composantRepository.findAll();
        assertThat(composantList).hasSize(databaseSizeBeforeCreate + 1);
        Composant testComposant = composantList.get(composantList.size() - 1);
        assertThat(testComposant.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createComposantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = composantRepository.findAll().size();

        // Create the Composant with an existing ID
        composant.setId(1L);
        ComposantDTO composantDTO = composantMapper.toDto(composant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComposantMockMvc.perform(post("/api/composants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(composantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Composant in the database
        List<Composant> composantList = composantRepository.findAll();
        assertThat(composantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = composantRepository.findAll().size();
        // set the field null
        composant.setName(null);

        // Create the Composant, which fails.
        ComposantDTO composantDTO = composantMapper.toDto(composant);


        restComposantMockMvc.perform(post("/api/composants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(composantDTO)))
            .andExpect(status().isBadRequest());

        List<Composant> composantList = composantRepository.findAll();
        assertThat(composantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComposants() throws Exception {
        // Initialize the database
        composantRepository.saveAndFlush(composant);

        // Get all the composantList
        restComposantMockMvc.perform(get("/api/composants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(composant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getComposant() throws Exception {
        // Initialize the database
        composantRepository.saveAndFlush(composant);

        // Get the composant
        restComposantMockMvc.perform(get("/api/composants/{id}", composant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(composant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getComposantsByIdFiltering() throws Exception {
        // Initialize the database
        composantRepository.saveAndFlush(composant);

        Long id = composant.getId();

        defaultComposantShouldBeFound("id.equals=" + id);
        defaultComposantShouldNotBeFound("id.notEquals=" + id);

        defaultComposantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComposantShouldNotBeFound("id.greaterThan=" + id);

        defaultComposantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComposantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllComposantsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        composantRepository.saveAndFlush(composant);

        // Get all the composantList where name equals to DEFAULT_NAME
        defaultComposantShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the composantList where name equals to UPDATED_NAME
        defaultComposantShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllComposantsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        composantRepository.saveAndFlush(composant);

        // Get all the composantList where name not equals to DEFAULT_NAME
        defaultComposantShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the composantList where name not equals to UPDATED_NAME
        defaultComposantShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllComposantsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        composantRepository.saveAndFlush(composant);

        // Get all the composantList where name in DEFAULT_NAME or UPDATED_NAME
        defaultComposantShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the composantList where name equals to UPDATED_NAME
        defaultComposantShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllComposantsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        composantRepository.saveAndFlush(composant);

        // Get all the composantList where name is not null
        defaultComposantShouldBeFound("name.specified=true");

        // Get all the composantList where name is null
        defaultComposantShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllComposantsByNameContainsSomething() throws Exception {
        // Initialize the database
        composantRepository.saveAndFlush(composant);

        // Get all the composantList where name contains DEFAULT_NAME
        defaultComposantShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the composantList where name contains UPDATED_NAME
        defaultComposantShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllComposantsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        composantRepository.saveAndFlush(composant);

        // Get all the composantList where name does not contain DEFAULT_NAME
        defaultComposantShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the composantList where name does not contain UPDATED_NAME
        defaultComposantShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComposantShouldBeFound(String filter) throws Exception {
        restComposantMockMvc.perform(get("/api/composants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(composant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restComposantMockMvc.perform(get("/api/composants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComposantShouldNotBeFound(String filter) throws Exception {
        restComposantMockMvc.perform(get("/api/composants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComposantMockMvc.perform(get("/api/composants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingComposant() throws Exception {
        // Get the composant
        restComposantMockMvc.perform(get("/api/composants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComposant() throws Exception {
        // Initialize the database
        composantRepository.saveAndFlush(composant);

        int databaseSizeBeforeUpdate = composantRepository.findAll().size();

        // Update the composant
        Composant updatedComposant = composantRepository.findById(composant.getId()).get();
        // Disconnect from session so that the updates on updatedComposant are not directly saved in db
        em.detach(updatedComposant);
        updatedComposant
            .name(UPDATED_NAME);
        ComposantDTO composantDTO = composantMapper.toDto(updatedComposant);

        restComposantMockMvc.perform(put("/api/composants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(composantDTO)))
            .andExpect(status().isOk());

        // Validate the Composant in the database
        List<Composant> composantList = composantRepository.findAll();
        assertThat(composantList).hasSize(databaseSizeBeforeUpdate);
        Composant testComposant = composantList.get(composantList.size() - 1);
        assertThat(testComposant.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingComposant() throws Exception {
        int databaseSizeBeforeUpdate = composantRepository.findAll().size();

        // Create the Composant
        ComposantDTO composantDTO = composantMapper.toDto(composant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComposantMockMvc.perform(put("/api/composants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(composantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Composant in the database
        List<Composant> composantList = composantRepository.findAll();
        assertThat(composantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComposant() throws Exception {
        // Initialize the database
        composantRepository.saveAndFlush(composant);

        int databaseSizeBeforeDelete = composantRepository.findAll().size();

        // Delete the composant
        restComposantMockMvc.perform(delete("/api/composants/{id}", composant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Composant> composantList = composantRepository.findAll();
        assertThat(composantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
