package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AppJhipsterApp;
import com.mycompany.myapp.domain.Commercial;
import com.mycompany.myapp.repository.CommercialRepository;
import com.mycompany.myapp.service.CommercialService;
import com.mycompany.myapp.service.dto.CommercialDTO;
import com.mycompany.myapp.service.mapper.CommercialMapper;
import com.mycompany.myapp.service.dto.CommercialCriteria;
import com.mycompany.myapp.service.CommercialQueryService;

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
 * Integration tests for the {@link CommercialResource} REST controller.
 */
@SpringBootTest(classes = AppJhipsterApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommercialResourceIT {

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CommercialRepository commercialRepository;

    @Autowired
    private CommercialMapper commercialMapper;

    @Autowired
    private CommercialService commercialService;

    @Autowired
    private CommercialQueryService commercialQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommercialMockMvc;

    private Commercial commercial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commercial createEntity(EntityManager em) {
        Commercial commercial = new Commercial()
            .firstname(DEFAULT_FIRSTNAME)
            .name(DEFAULT_NAME);
        return commercial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commercial createUpdatedEntity(EntityManager em) {
        Commercial commercial = new Commercial()
            .firstname(UPDATED_FIRSTNAME)
            .name(UPDATED_NAME);
        return commercial;
    }

    @BeforeEach
    public void initTest() {
        commercial = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercial() throws Exception {
        int databaseSizeBeforeCreate = commercialRepository.findAll().size();
        // Create the Commercial
        CommercialDTO commercialDTO = commercialMapper.toDto(commercial);
        restCommercialMockMvc.perform(post("/api/commercials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commercialDTO)))
            .andExpect(status().isCreated());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeCreate + 1);
        Commercial testCommercial = commercialList.get(commercialList.size() - 1);
        assertThat(testCommercial.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testCommercial.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCommercialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialRepository.findAll().size();

        // Create the Commercial with an existing ID
        commercial.setId(1L);
        CommercialDTO commercialDTO = commercialMapper.toDto(commercial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialMockMvc.perform(post("/api/commercials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commercialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialRepository.findAll().size();
        // set the field null
        commercial.setName(null);

        // Create the Commercial, which fails.
        CommercialDTO commercialDTO = commercialMapper.toDto(commercial);


        restCommercialMockMvc.perform(post("/api/commercials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commercialDTO)))
            .andExpect(status().isBadRequest());

        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercials() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get all the commercialList
        restCommercialMockMvc.perform(get("/api/commercials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercial.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCommercial() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get the commercial
        restCommercialMockMvc.perform(get("/api/commercials/{id}", commercial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commercial.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getCommercialsByIdFiltering() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        Long id = commercial.getId();

        defaultCommercialShouldBeFound("id.equals=" + id);
        defaultCommercialShouldNotBeFound("id.notEquals=" + id);

        defaultCommercialShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommercialShouldNotBeFound("id.greaterThan=" + id);

        defaultCommercialShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommercialShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCommercialsByFirstnameIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get all the commercialList where firstname equals to DEFAULT_FIRSTNAME
        defaultCommercialShouldBeFound("firstname.equals=" + DEFAULT_FIRSTNAME);

        // Get all the commercialList where firstname equals to UPDATED_FIRSTNAME
        defaultCommercialShouldNotBeFound("firstname.equals=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    public void getAllCommercialsByFirstnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get all the commercialList where firstname not equals to DEFAULT_FIRSTNAME
        defaultCommercialShouldNotBeFound("firstname.notEquals=" + DEFAULT_FIRSTNAME);

        // Get all the commercialList where firstname not equals to UPDATED_FIRSTNAME
        defaultCommercialShouldBeFound("firstname.notEquals=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    public void getAllCommercialsByFirstnameIsInShouldWork() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get all the commercialList where firstname in DEFAULT_FIRSTNAME or UPDATED_FIRSTNAME
        defaultCommercialShouldBeFound("firstname.in=" + DEFAULT_FIRSTNAME + "," + UPDATED_FIRSTNAME);

        // Get all the commercialList where firstname equals to UPDATED_FIRSTNAME
        defaultCommercialShouldNotBeFound("firstname.in=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    public void getAllCommercialsByFirstnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get all the commercialList where firstname is not null
        defaultCommercialShouldBeFound("firstname.specified=true");

        // Get all the commercialList where firstname is null
        defaultCommercialShouldNotBeFound("firstname.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommercialsByFirstnameContainsSomething() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get all the commercialList where firstname contains DEFAULT_FIRSTNAME
        defaultCommercialShouldBeFound("firstname.contains=" + DEFAULT_FIRSTNAME);

        // Get all the commercialList where firstname contains UPDATED_FIRSTNAME
        defaultCommercialShouldNotBeFound("firstname.contains=" + UPDATED_FIRSTNAME);
    }

    @Test
    @Transactional
    public void getAllCommercialsByFirstnameNotContainsSomething() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get all the commercialList where firstname does not contain DEFAULT_FIRSTNAME
        defaultCommercialShouldNotBeFound("firstname.doesNotContain=" + DEFAULT_FIRSTNAME);

        // Get all the commercialList where firstname does not contain UPDATED_FIRSTNAME
        defaultCommercialShouldBeFound("firstname.doesNotContain=" + UPDATED_FIRSTNAME);
    }


    @Test
    @Transactional
    public void getAllCommercialsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get all the commercialList where name equals to DEFAULT_NAME
        defaultCommercialShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the commercialList where name equals to UPDATED_NAME
        defaultCommercialShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCommercialsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get all the commercialList where name not equals to DEFAULT_NAME
        defaultCommercialShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the commercialList where name not equals to UPDATED_NAME
        defaultCommercialShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCommercialsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get all the commercialList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCommercialShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the commercialList where name equals to UPDATED_NAME
        defaultCommercialShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCommercialsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get all the commercialList where name is not null
        defaultCommercialShouldBeFound("name.specified=true");

        // Get all the commercialList where name is null
        defaultCommercialShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommercialsByNameContainsSomething() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get all the commercialList where name contains DEFAULT_NAME
        defaultCommercialShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the commercialList where name contains UPDATED_NAME
        defaultCommercialShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCommercialsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        // Get all the commercialList where name does not contain DEFAULT_NAME
        defaultCommercialShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the commercialList where name does not contain UPDATED_NAME
        defaultCommercialShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommercialShouldBeFound(String filter) throws Exception {
        restCommercialMockMvc.perform(get("/api/commercials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercial.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCommercialMockMvc.perform(get("/api/commercials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommercialShouldNotBeFound(String filter) throws Exception {
        restCommercialMockMvc.perform(get("/api/commercials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialMockMvc.perform(get("/api/commercials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCommercial() throws Exception {
        // Get the commercial
        restCommercialMockMvc.perform(get("/api/commercials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercial() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        int databaseSizeBeforeUpdate = commercialRepository.findAll().size();

        // Update the commercial
        Commercial updatedCommercial = commercialRepository.findById(commercial.getId()).get();
        // Disconnect from session so that the updates on updatedCommercial are not directly saved in db
        em.detach(updatedCommercial);
        updatedCommercial
            .firstname(UPDATED_FIRSTNAME)
            .name(UPDATED_NAME);
        CommercialDTO commercialDTO = commercialMapper.toDto(updatedCommercial);

        restCommercialMockMvc.perform(put("/api/commercials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commercialDTO)))
            .andExpect(status().isOk());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeUpdate);
        Commercial testCommercial = commercialList.get(commercialList.size() - 1);
        assertThat(testCommercial.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testCommercial.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercial() throws Exception {
        int databaseSizeBeforeUpdate = commercialRepository.findAll().size();

        // Create the Commercial
        CommercialDTO commercialDTO = commercialMapper.toDto(commercial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialMockMvc.perform(put("/api/commercials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commercialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commercial in the database
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommercial() throws Exception {
        // Initialize the database
        commercialRepository.saveAndFlush(commercial);

        int databaseSizeBeforeDelete = commercialRepository.findAll().size();

        // Delete the commercial
        restCommercialMockMvc.perform(delete("/api/commercials/{id}", commercial.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commercial> commercialList = commercialRepository.findAll();
        assertThat(commercialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
