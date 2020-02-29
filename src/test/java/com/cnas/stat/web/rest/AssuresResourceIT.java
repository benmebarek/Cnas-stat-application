package com.cnas.stat.web.rest;

import com.cnas.stat.CnasStatApp;
import com.cnas.stat.domain.Assures;
import com.cnas.stat.repository.AssuresRepository;
import com.cnas.stat.service.AssuresService;
import com.cnas.stat.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cnas.stat.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AssuresResource} REST controller.
 */
@SpringBootTest(classes = CnasStatApp.class)
public class AssuresResourceIT {

    private static final String DEFAULT_NOASSURE = "AAAAAAAAAA";
    private static final String UPDATED_NOASSURE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    @Autowired
    private AssuresRepository assuresRepository;

    @Autowired
    private AssuresService assuresService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAssuresMockMvc;

    private Assures assures;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssuresResource assuresResource = new AssuresResource(assuresService);
        this.restAssuresMockMvc = MockMvcBuilders.standaloneSetup(assuresResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assures createEntity(EntityManager em) {
        Assures assures = new Assures()
            .noassure(DEFAULT_NOASSURE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM);
        return assures;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assures createUpdatedEntity(EntityManager em) {
        Assures assures = new Assures()
            .noassure(UPDATED_NOASSURE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM);
        return assures;
    }

    @BeforeEach
    public void initTest() {
        assures = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssures() throws Exception {
        int databaseSizeBeforeCreate = assuresRepository.findAll().size();

        // Create the Assures
        restAssuresMockMvc.perform(post("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assures)))
            .andExpect(status().isCreated());

        // Validate the Assures in the database
        List<Assures> assuresList = assuresRepository.findAll();
        assertThat(assuresList).hasSize(databaseSizeBeforeCreate + 1);
        Assures testAssures = assuresList.get(assuresList.size() - 1);
        assertThat(testAssures.getNoassure()).isEqualTo(DEFAULT_NOASSURE);
        assertThat(testAssures.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAssures.getPrenom()).isEqualTo(DEFAULT_PRENOM);
    }

    @Test
    @Transactional
    public void createAssuresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assuresRepository.findAll().size();

        // Create the Assures with an existing ID
        assures.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssuresMockMvc.perform(post("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assures)))
            .andExpect(status().isBadRequest());

        // Validate the Assures in the database
        List<Assures> assuresList = assuresRepository.findAll();
        assertThat(assuresList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAssures() throws Exception {
        // Initialize the database
        assuresRepository.saveAndFlush(assures);

        // Get all the assuresList
        restAssuresMockMvc.perform(get("/api/assures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assures.getId().intValue())))
            .andExpect(jsonPath("$.[*].noassure").value(hasItem(DEFAULT_NOASSURE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)));
    }
    
    @Test
    @Transactional
    public void getAssures() throws Exception {
        // Initialize the database
        assuresRepository.saveAndFlush(assures);

        // Get the assures
        restAssuresMockMvc.perform(get("/api/assures/{id}", assures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assures.getId().intValue()))
            .andExpect(jsonPath("$.noassure").value(DEFAULT_NOASSURE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM));
    }

    @Test
    @Transactional
    public void getNonExistingAssures() throws Exception {
        // Get the assures
        restAssuresMockMvc.perform(get("/api/assures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssures() throws Exception {
        // Initialize the database
        assuresService.save(assures);

        int databaseSizeBeforeUpdate = assuresRepository.findAll().size();

        // Update the assures
        Assures updatedAssures = assuresRepository.findById(assures.getId()).get();
        // Disconnect from session so that the updates on updatedAssures are not directly saved in db
        em.detach(updatedAssures);
        updatedAssures
            .noassure(UPDATED_NOASSURE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM);

        restAssuresMockMvc.perform(put("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssures)))
            .andExpect(status().isOk());

        // Validate the Assures in the database
        List<Assures> assuresList = assuresRepository.findAll();
        assertThat(assuresList).hasSize(databaseSizeBeforeUpdate);
        Assures testAssures = assuresList.get(assuresList.size() - 1);
        assertThat(testAssures.getNoassure()).isEqualTo(UPDATED_NOASSURE);
        assertThat(testAssures.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAssures.getPrenom()).isEqualTo(UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void updateNonExistingAssures() throws Exception {
        int databaseSizeBeforeUpdate = assuresRepository.findAll().size();

        // Create the Assures

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssuresMockMvc.perform(put("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assures)))
            .andExpect(status().isBadRequest());

        // Validate the Assures in the database
        List<Assures> assuresList = assuresRepository.findAll();
        assertThat(assuresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssures() throws Exception {
        // Initialize the database
        assuresService.save(assures);

        int databaseSizeBeforeDelete = assuresRepository.findAll().size();

        // Delete the assures
        restAssuresMockMvc.perform(delete("/api/assures/{id}", assures.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Assures> assuresList = assuresRepository.findAll();
        assertThat(assuresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
