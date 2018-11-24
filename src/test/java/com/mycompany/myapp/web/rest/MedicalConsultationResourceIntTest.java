package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SantamariaApp;

import com.mycompany.myapp.domain.MedicalConsultation;
import com.mycompany.myapp.repository.MedicalConsultationRepository;
import com.mycompany.myapp.service.MedicalConsultationService;
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
 * Test class for the MedicalConsultationResource REST controller.
 *
 * @see MedicalConsultationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SantamariaApp.class)
public class MedicalConsultationResourceIntTest {

    private static final String DEFAULT_DIAGNOSIS = "AAAAAAAAAA";
    private static final String UPDATED_DIAGNOSIS = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_PATIENT = 1L;
    private static final Long UPDATED_ID_PATIENT = 2L;

    private static final Long DEFAULT_ID_STAFF = 1L;
    private static final Long UPDATED_ID_STAFF = 2L;

    private static final Long DEFAULT_ID_TYPE_ATTENTION = 1L;
    private static final Long UPDATED_ID_TYPE_ATTENTION = 2L;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MedicalConsultationRepository medicalConsultationRepository;

    @Autowired
    private MedicalConsultationService medicalConsultationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicalConsultationMockMvc;

    private MedicalConsultation medicalConsultation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalConsultationResource medicalConsultationResource = new MedicalConsultationResource(medicalConsultationService);
        this.restMedicalConsultationMockMvc = MockMvcBuilders.standaloneSetup(medicalConsultationResource)
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
    public static MedicalConsultation createEntity(EntityManager em) {
        MedicalConsultation medicalConsultation = new MedicalConsultation()
            .Diagnosis(DEFAULT_DIAGNOSIS)
            .detail(DEFAULT_DETAIL)
            .idPatient(DEFAULT_ID_PATIENT)
            .idStaff(DEFAULT_ID_STAFF)
            .idTypeAttention(DEFAULT_ID_TYPE_ATTENTION)
            .date(DEFAULT_DATE);
        return medicalConsultation;
    }

    @Before
    public void initTest() {
        medicalConsultation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalConsultation() throws Exception {
        int databaseSizeBeforeCreate = medicalConsultationRepository.findAll().size();

        // Create the MedicalConsultation
        restMedicalConsultationMockMvc.perform(post("/api/medical-consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalConsultation)))
            .andExpect(status().isCreated());

        // Validate the MedicalConsultation in the database
        List<MedicalConsultation> medicalConsultationList = medicalConsultationRepository.findAll();
        assertThat(medicalConsultationList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalConsultation testMedicalConsultation = medicalConsultationList.get(medicalConsultationList.size() - 1);
        assertThat(testMedicalConsultation.getDiagnosis()).isEqualTo(DEFAULT_DIAGNOSIS);
        assertThat(testMedicalConsultation.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testMedicalConsultation.getIdPatient()).isEqualTo(DEFAULT_ID_PATIENT);
        assertThat(testMedicalConsultation.getIdStaff()).isEqualTo(DEFAULT_ID_STAFF);
        assertThat(testMedicalConsultation.getIdTypeAttention()).isEqualTo(DEFAULT_ID_TYPE_ATTENTION);
        assertThat(testMedicalConsultation.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createMedicalConsultationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalConsultationRepository.findAll().size();

        // Create the MedicalConsultation with an existing ID
        medicalConsultation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalConsultationMockMvc.perform(post("/api/medical-consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalConsultation)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalConsultation in the database
        List<MedicalConsultation> medicalConsultationList = medicalConsultationRepository.findAll();
        assertThat(medicalConsultationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedicalConsultations() throws Exception {
        // Initialize the database
        medicalConsultationRepository.saveAndFlush(medicalConsultation);

        // Get all the medicalConsultationList
        restMedicalConsultationMockMvc.perform(get("/api/medical-consultations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalConsultation.getId().intValue())))
            .andExpect(jsonPath("$.[*].Diagnosis").value(hasItem(DEFAULT_DIAGNOSIS.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].idPatient").value(hasItem(DEFAULT_ID_PATIENT.intValue())))
            .andExpect(jsonPath("$.[*].idStaff").value(hasItem(DEFAULT_ID_STAFF.intValue())))
            .andExpect(jsonPath("$.[*].idTypeAttention").value(hasItem(DEFAULT_ID_TYPE_ATTENTION.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getMedicalConsultation() throws Exception {
        // Initialize the database
        medicalConsultationRepository.saveAndFlush(medicalConsultation);

        // Get the medicalConsultation
        restMedicalConsultationMockMvc.perform(get("/api/medical-consultations/{id}", medicalConsultation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalConsultation.getId().intValue()))
            .andExpect(jsonPath("$.Diagnosis").value(DEFAULT_DIAGNOSIS.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.idPatient").value(DEFAULT_ID_PATIENT.intValue()))
            .andExpect(jsonPath("$.idStaff").value(DEFAULT_ID_STAFF.intValue()))
            .andExpect(jsonPath("$.idTypeAttention").value(DEFAULT_ID_TYPE_ATTENTION.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalConsultation() throws Exception {
        // Get the medicalConsultation
        restMedicalConsultationMockMvc.perform(get("/api/medical-consultations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalConsultation() throws Exception {
        // Initialize the database
        medicalConsultationService.save(medicalConsultation);

        int databaseSizeBeforeUpdate = medicalConsultationRepository.findAll().size();

        // Update the medicalConsultation
        MedicalConsultation updatedMedicalConsultation = medicalConsultationRepository.findById(medicalConsultation.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalConsultation are not directly saved in db
        em.detach(updatedMedicalConsultation);
        updatedMedicalConsultation
            .Diagnosis(UPDATED_DIAGNOSIS)
            .detail(UPDATED_DETAIL)
            .idPatient(UPDATED_ID_PATIENT)
            .idStaff(UPDATED_ID_STAFF)
            .idTypeAttention(UPDATED_ID_TYPE_ATTENTION)
            .date(UPDATED_DATE);

        restMedicalConsultationMockMvc.perform(put("/api/medical-consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalConsultation)))
            .andExpect(status().isOk());

        // Validate the MedicalConsultation in the database
        List<MedicalConsultation> medicalConsultationList = medicalConsultationRepository.findAll();
        assertThat(medicalConsultationList).hasSize(databaseSizeBeforeUpdate);
        MedicalConsultation testMedicalConsultation = medicalConsultationList.get(medicalConsultationList.size() - 1);
        assertThat(testMedicalConsultation.getDiagnosis()).isEqualTo(UPDATED_DIAGNOSIS);
        assertThat(testMedicalConsultation.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testMedicalConsultation.getIdPatient()).isEqualTo(UPDATED_ID_PATIENT);
        assertThat(testMedicalConsultation.getIdStaff()).isEqualTo(UPDATED_ID_STAFF);
        assertThat(testMedicalConsultation.getIdTypeAttention()).isEqualTo(UPDATED_ID_TYPE_ATTENTION);
        assertThat(testMedicalConsultation.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalConsultation() throws Exception {
        int databaseSizeBeforeUpdate = medicalConsultationRepository.findAll().size();

        // Create the MedicalConsultation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalConsultationMockMvc.perform(put("/api/medical-consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalConsultation)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalConsultation in the database
        List<MedicalConsultation> medicalConsultationList = medicalConsultationRepository.findAll();
        assertThat(medicalConsultationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicalConsultation() throws Exception {
        // Initialize the database
        medicalConsultationService.save(medicalConsultation);

        int databaseSizeBeforeDelete = medicalConsultationRepository.findAll().size();

        // Get the medicalConsultation
        restMedicalConsultationMockMvc.perform(delete("/api/medical-consultations/{id}", medicalConsultation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MedicalConsultation> medicalConsultationList = medicalConsultationRepository.findAll();
        assertThat(medicalConsultationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalConsultation.class);
        MedicalConsultation medicalConsultation1 = new MedicalConsultation();
        medicalConsultation1.setId(1L);
        MedicalConsultation medicalConsultation2 = new MedicalConsultation();
        medicalConsultation2.setId(medicalConsultation1.getId());
        assertThat(medicalConsultation1).isEqualTo(medicalConsultation2);
        medicalConsultation2.setId(2L);
        assertThat(medicalConsultation1).isNotEqualTo(medicalConsultation2);
        medicalConsultation1.setId(null);
        assertThat(medicalConsultation1).isNotEqualTo(medicalConsultation2);
    }
}
