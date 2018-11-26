package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SantamariaApp;

import com.mycompany.myapp.domain.Consultation;
import com.mycompany.myapp.repository.ConsultationRepository;
import com.mycompany.myapp.service.ConsultationService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConsultationResource REST controller.
 *
 * @see ConsultationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SantamariaApp.class)
public class ConsultationResourceIntTest {

    private static final String DEFAULT_DIAGNOSIS = "AAAAAAAAAA";
    private static final String UPDATED_DIAGNOSIS = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsultationMockMvc;

    private Consultation consultation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsultationResource consultationResource = new ConsultationResource(consultationService);
        this.restConsultationMockMvc = MockMvcBuilders.standaloneSetup(consultationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consultation createEntity(EntityManager em) {
        Consultation consultation = new Consultation()
            .Diagnosis(DEFAULT_DIAGNOSIS)
            .detail(DEFAULT_DETAIL)
            .date(DEFAULT_DATE);
        return consultation;
    }

    @Before
    public void initTest() {
        consultation = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultation() throws Exception {
        int databaseSizeBeforeCreate = consultationRepository.findAll().size();

        // Create the Consultation
        restConsultationMockMvc.perform(post("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultation)))
            .andExpect(status().isCreated());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeCreate + 1);
        Consultation testConsultation = consultationList.get(consultationList.size() - 1);
        assertThat(testConsultation.getDiagnosis()).isEqualTo(DEFAULT_DIAGNOSIS);
        assertThat(testConsultation.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testConsultation.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createConsultationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultationRepository.findAll().size();

        // Create the Consultation with an existing ID
        consultation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultationMockMvc.perform(post("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultation)))
            .andExpect(status().isBadRequest());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConsultations() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        // Get all the consultationList
        restConsultationMockMvc.perform(get("/api/consultations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultation.getId().intValue())))
            .andExpect(jsonPath("$.[*].Diagnosis").value(hasItem(DEFAULT_DIAGNOSIS.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        // Get the consultation
        restConsultationMockMvc.perform(get("/api/consultations/{id}", consultation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultation.getId().intValue()))
            .andExpect(jsonPath("$.Diagnosis").value(DEFAULT_DIAGNOSIS.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsultation() throws Exception {
        // Get the consultation
        restConsultationMockMvc.perform(get("/api/consultations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultation() throws Exception {
        // Initialize the database
        consultationService.save(consultation);

        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();

        // Update the consultation
        Consultation updatedConsultation = consultationRepository.findById(consultation.getId()).get();
        // Disconnect from session so that the updates on updatedConsultation are not directly saved in db
        em.detach(updatedConsultation);
        updatedConsultation
            .Diagnosis(UPDATED_DIAGNOSIS)
            .detail(UPDATED_DETAIL)
            .date(UPDATED_DATE);

        restConsultationMockMvc.perform(put("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConsultation)))
            .andExpect(status().isOk());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);
        Consultation testConsultation = consultationList.get(consultationList.size() - 1);
        assertThat(testConsultation.getDiagnosis()).isEqualTo(UPDATED_DIAGNOSIS);
        assertThat(testConsultation.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testConsultation.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultation() throws Exception {
        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();

        // Create the Consultation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultationMockMvc.perform(put("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultation)))
            .andExpect(status().isBadRequest());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsultation() throws Exception {
        // Initialize the database
        consultationService.save(consultation);

        int databaseSizeBeforeDelete = consultationRepository.findAll().size();

        // Get the consultation
        restConsultationMockMvc.perform(delete("/api/consultations/{id}", consultation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consultation.class);
        Consultation consultation1 = new Consultation();
        consultation1.setId(1L);
        Consultation consultation2 = new Consultation();
        consultation2.setId(consultation1.getId());
        assertThat(consultation1).isEqualTo(consultation2);
        consultation2.setId(2L);
        assertThat(consultation1).isNotEqualTo(consultation2);
        consultation1.setId(null);
        assertThat(consultation1).isNotEqualTo(consultation2);
    }
}
