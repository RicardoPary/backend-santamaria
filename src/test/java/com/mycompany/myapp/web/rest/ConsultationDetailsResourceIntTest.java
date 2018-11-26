package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SantamariaApp;

import com.mycompany.myapp.domain.ConsultationDetails;
import com.mycompany.myapp.repository.ConsultationDetailsRepository;
import com.mycompany.myapp.service.ConsultationDetailsService;
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
 * Test class for the ConsultationDetailsResource REST controller.
 *
 * @see ConsultationDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SantamariaApp.class)
public class ConsultationDetailsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Long DEFAULT_ID_CONSULTATION = 1L;
    private static final Long UPDATED_ID_CONSULTATION = 2L;

    @Autowired
    private ConsultationDetailsRepository consultationDetailsRepository;

    @Autowired
    private ConsultationDetailsService consultationDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsultationDetailsMockMvc;

    private ConsultationDetails consultationDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsultationDetailsResource consultationDetailsResource = new ConsultationDetailsResource(consultationDetailsService);
        this.restConsultationDetailsMockMvc = MockMvcBuilders.standaloneSetup(consultationDetailsResource)
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
    public static ConsultationDetails createEntity(EntityManager em) {
        ConsultationDetails consultationDetails = new ConsultationDetails()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .quantity(DEFAULT_QUANTITY)
            .idConsultation(DEFAULT_ID_CONSULTATION);
        return consultationDetails;
    }

    @Before
    public void initTest() {
        consultationDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultationDetails() throws Exception {
        int databaseSizeBeforeCreate = consultationDetailsRepository.findAll().size();

        // Create the ConsultationDetails
        restConsultationDetailsMockMvc.perform(post("/api/consultation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultationDetails)))
            .andExpect(status().isCreated());

        // Validate the ConsultationDetails in the database
        List<ConsultationDetails> consultationDetailsList = consultationDetailsRepository.findAll();
        assertThat(consultationDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ConsultationDetails testConsultationDetails = consultationDetailsList.get(consultationDetailsList.size() - 1);
        assertThat(testConsultationDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConsultationDetails.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testConsultationDetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testConsultationDetails.getIdConsultation()).isEqualTo(DEFAULT_ID_CONSULTATION);
    }

    @Test
    @Transactional
    public void createConsultationDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultationDetailsRepository.findAll().size();

        // Create the ConsultationDetails with an existing ID
        consultationDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultationDetailsMockMvc.perform(post("/api/consultation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultationDetails)))
            .andExpect(status().isBadRequest());

        // Validate the ConsultationDetails in the database
        List<ConsultationDetails> consultationDetailsList = consultationDetailsRepository.findAll();
        assertThat(consultationDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConsultationDetails() throws Exception {
        // Initialize the database
        consultationDetailsRepository.saveAndFlush(consultationDetails);

        // Get all the consultationDetailsList
        restConsultationDetailsMockMvc.perform(get("/api/consultation-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].idConsultation").value(hasItem(DEFAULT_ID_CONSULTATION.intValue())));
    }
    
    @Test
    @Transactional
    public void getConsultationDetails() throws Exception {
        // Initialize the database
        consultationDetailsRepository.saveAndFlush(consultationDetails);

        // Get the consultationDetails
        restConsultationDetailsMockMvc.perform(get("/api/consultation-details/{id}", consultationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultationDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.idConsultation").value(DEFAULT_ID_CONSULTATION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConsultationDetails() throws Exception {
        // Get the consultationDetails
        restConsultationDetailsMockMvc.perform(get("/api/consultation-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultationDetails() throws Exception {
        // Initialize the database
        consultationDetailsService.save(consultationDetails);

        int databaseSizeBeforeUpdate = consultationDetailsRepository.findAll().size();

        // Update the consultationDetails
        ConsultationDetails updatedConsultationDetails = consultationDetailsRepository.findById(consultationDetails.getId()).get();
        // Disconnect from session so that the updates on updatedConsultationDetails are not directly saved in db
        em.detach(updatedConsultationDetails);
        updatedConsultationDetails
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .quantity(UPDATED_QUANTITY)
            .idConsultation(UPDATED_ID_CONSULTATION);

        restConsultationDetailsMockMvc.perform(put("/api/consultation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConsultationDetails)))
            .andExpect(status().isOk());

        // Validate the ConsultationDetails in the database
        List<ConsultationDetails> consultationDetailsList = consultationDetailsRepository.findAll();
        assertThat(consultationDetailsList).hasSize(databaseSizeBeforeUpdate);
        ConsultationDetails testConsultationDetails = consultationDetailsList.get(consultationDetailsList.size() - 1);
        assertThat(testConsultationDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConsultationDetails.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testConsultationDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testConsultationDetails.getIdConsultation()).isEqualTo(UPDATED_ID_CONSULTATION);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultationDetails() throws Exception {
        int databaseSizeBeforeUpdate = consultationDetailsRepository.findAll().size();

        // Create the ConsultationDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultationDetailsMockMvc.perform(put("/api/consultation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultationDetails)))
            .andExpect(status().isBadRequest());

        // Validate the ConsultationDetails in the database
        List<ConsultationDetails> consultationDetailsList = consultationDetailsRepository.findAll();
        assertThat(consultationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsultationDetails() throws Exception {
        // Initialize the database
        consultationDetailsService.save(consultationDetails);

        int databaseSizeBeforeDelete = consultationDetailsRepository.findAll().size();

        // Get the consultationDetails
        restConsultationDetailsMockMvc.perform(delete("/api/consultation-details/{id}", consultationDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConsultationDetails> consultationDetailsList = consultationDetailsRepository.findAll();
        assertThat(consultationDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultationDetails.class);
        ConsultationDetails consultationDetails1 = new ConsultationDetails();
        consultationDetails1.setId(1L);
        ConsultationDetails consultationDetails2 = new ConsultationDetails();
        consultationDetails2.setId(consultationDetails1.getId());
        assertThat(consultationDetails1).isEqualTo(consultationDetails2);
        consultationDetails2.setId(2L);
        assertThat(consultationDetails1).isNotEqualTo(consultationDetails2);
        consultationDetails1.setId(null);
        assertThat(consultationDetails1).isNotEqualTo(consultationDetails2);
    }
}
