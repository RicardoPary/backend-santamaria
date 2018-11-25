package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SantamariaApp;

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
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MedicalConsultationDetailsResource REST controller.
 *
 * @see MedicalConsultationDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SantamariaApp.class)
public class MedicalConsultationDetailsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Long DEFAULT_ID_STAFF = 1L;
    private static final Long UPDATED_ID_STAFF = 2L;

    private static final Long DEFAULT_ID_SUPPLY = 1L;
    private static final Long UPDATED_ID_SUPPLY = 2L;

    @Autowired
    private MedicalConsultationDetailsRepository medicalConsultationDetailsRepository;

    @Autowired
    private MedicalConsultationDetailsService medicalConsultationDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicalConsultationDetailsMockMvc;

    private MedicalConsultationDetails medicalConsultationDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalConsultationDetailsResource medicalConsultationDetailsResource = new MedicalConsultationDetailsResource(medicalConsultationDetailsService);
        this.restMedicalConsultationDetailsMockMvc = MockMvcBuilders.standaloneSetup(medicalConsultationDetailsResource)
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
    public static MedicalConsultationDetails createEntity(EntityManager em) {
        MedicalConsultationDetails medicalConsultationDetails = new MedicalConsultationDetails()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .quantity(DEFAULT_QUANTITY)
            .idStaff(DEFAULT_ID_STAFF)
            .idSupply(DEFAULT_ID_SUPPLY);
        return medicalConsultationDetails;
    }

    @Before
    public void initTest() {
        medicalConsultationDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalConsultationDetails() throws Exception {
        int databaseSizeBeforeCreate = medicalConsultationDetailsRepository.findAll().size();

        // Create the MedicalConsultationDetails
        restMedicalConsultationDetailsMockMvc.perform(post("/api/medical-consultation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalConsultationDetails)))
            .andExpect(status().isCreated());

        // Validate the MedicalConsultationDetails in the database
        List<MedicalConsultationDetails> medicalConsultationDetailsList = medicalConsultationDetailsRepository.findAll();
        assertThat(medicalConsultationDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalConsultationDetails testMedicalConsultationDetails = medicalConsultationDetailsList.get(medicalConsultationDetailsList.size() - 1);
        assertThat(testMedicalConsultationDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMedicalConsultationDetails.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testMedicalConsultationDetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMedicalConsultationDetails.getIdStaff()).isEqualTo(DEFAULT_ID_STAFF);
        assertThat(testMedicalConsultationDetails.getIdSupply()).isEqualTo(DEFAULT_ID_SUPPLY);
    }

    @Test
    @Transactional
    public void createMedicalConsultationDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalConsultationDetailsRepository.findAll().size();

        // Create the MedicalConsultationDetails with an existing ID
        medicalConsultationDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalConsultationDetailsMockMvc.perform(post("/api/medical-consultation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalConsultationDetails)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalConsultationDetails in the database
        List<MedicalConsultationDetails> medicalConsultationDetailsList = medicalConsultationDetailsRepository.findAll();
        assertThat(medicalConsultationDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedicalConsultationDetails() throws Exception {
        // Initialize the database
        medicalConsultationDetailsRepository.saveAndFlush(medicalConsultationDetails);

        // Get all the medicalConsultationDetailsList
        restMedicalConsultationDetailsMockMvc.perform(get("/api/medical-consultation-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalConsultationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].idStaff").value(hasItem(DEFAULT_ID_STAFF.intValue())))
            .andExpect(jsonPath("$.[*].idSupply").value(hasItem(DEFAULT_ID_SUPPLY.intValue())));
    }
    
    @Test
    @Transactional
    public void getMedicalConsultationDetails() throws Exception {
        // Initialize the database
        medicalConsultationDetailsRepository.saveAndFlush(medicalConsultationDetails);

        // Get the medicalConsultationDetails
        restMedicalConsultationDetailsMockMvc.perform(get("/api/medical-consultation-details/{id}", medicalConsultationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalConsultationDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.idStaff").value(DEFAULT_ID_STAFF.intValue()))
            .andExpect(jsonPath("$.idSupply").value(DEFAULT_ID_SUPPLY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalConsultationDetails() throws Exception {
        // Get the medicalConsultationDetails
        restMedicalConsultationDetailsMockMvc.perform(get("/api/medical-consultation-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalConsultationDetails() throws Exception {
        // Initialize the database
        medicalConsultationDetailsService.save(medicalConsultationDetails);

        int databaseSizeBeforeUpdate = medicalConsultationDetailsRepository.findAll().size();

        // Update the medicalConsultationDetails
        MedicalConsultationDetails updatedMedicalConsultationDetails = medicalConsultationDetailsRepository.findById(medicalConsultationDetails.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalConsultationDetails are not directly saved in db
        em.detach(updatedMedicalConsultationDetails);
        updatedMedicalConsultationDetails
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .quantity(UPDATED_QUANTITY)
            .idStaff(UPDATED_ID_STAFF)
            .idSupply(UPDATED_ID_SUPPLY);

        restMedicalConsultationDetailsMockMvc.perform(put("/api/medical-consultation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalConsultationDetails)))
            .andExpect(status().isOk());

        // Validate the MedicalConsultationDetails in the database
        List<MedicalConsultationDetails> medicalConsultationDetailsList = medicalConsultationDetailsRepository.findAll();
        assertThat(medicalConsultationDetailsList).hasSize(databaseSizeBeforeUpdate);
        MedicalConsultationDetails testMedicalConsultationDetails = medicalConsultationDetailsList.get(medicalConsultationDetailsList.size() - 1);
        assertThat(testMedicalConsultationDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedicalConsultationDetails.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testMedicalConsultationDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMedicalConsultationDetails.getIdStaff()).isEqualTo(UPDATED_ID_STAFF);
        assertThat(testMedicalConsultationDetails.getIdSupply()).isEqualTo(UPDATED_ID_SUPPLY);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalConsultationDetails() throws Exception {
        int databaseSizeBeforeUpdate = medicalConsultationDetailsRepository.findAll().size();

        // Create the MedicalConsultationDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalConsultationDetailsMockMvc.perform(put("/api/medical-consultation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalConsultationDetails)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalConsultationDetails in the database
        List<MedicalConsultationDetails> medicalConsultationDetailsList = medicalConsultationDetailsRepository.findAll();
        assertThat(medicalConsultationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicalConsultationDetails() throws Exception {
        // Initialize the database
        medicalConsultationDetailsService.save(medicalConsultationDetails);

        int databaseSizeBeforeDelete = medicalConsultationDetailsRepository.findAll().size();

        // Get the medicalConsultationDetails
        restMedicalConsultationDetailsMockMvc.perform(delete("/api/medical-consultation-details/{id}", medicalConsultationDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MedicalConsultationDetails> medicalConsultationDetailsList = medicalConsultationDetailsRepository.findAll();
        assertThat(medicalConsultationDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalConsultationDetails.class);
        MedicalConsultationDetails medicalConsultationDetails1 = new MedicalConsultationDetails();
        medicalConsultationDetails1.setId(1L);
        MedicalConsultationDetails medicalConsultationDetails2 = new MedicalConsultationDetails();
        medicalConsultationDetails2.setId(medicalConsultationDetails1.getId());
        assertThat(medicalConsultationDetails1).isEqualTo(medicalConsultationDetails2);
        medicalConsultationDetails2.setId(2L);
        assertThat(medicalConsultationDetails1).isNotEqualTo(medicalConsultationDetails2);
        medicalConsultationDetails1.setId(null);
        assertThat(medicalConsultationDetails1).isNotEqualTo(medicalConsultationDetails2);
    }
}
