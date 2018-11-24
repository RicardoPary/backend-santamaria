package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SantamariaApp;

import com.mycompany.myapp.domain.MedicalConsultationDetails;
import com.mycompany.myapp.repository.MedicalConsultationDetailsRepository;
import com.mycompany.myapp.service.MedicalConsultationDetailsService;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SUBTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBTOTAL = new BigDecimal(2);

    private static final Long DEFAULT_ID_PRODUCT = 1L;
    private static final Long UPDATED_ID_PRODUCT = 2L;

    private static final Integer DEFAULT_INITIAL_CURRENT_STOCK = 1;
    private static final Integer UPDATED_INITIAL_CURRENT_STOCK = 2;

    private static final Integer DEFAULT_FINAL_CURRENT_STOCK = 1;
    private static final Integer UPDATED_FINAL_CURRENT_STOCK = 2;

    private static final String DEFAULT_TYPE_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_METHOD = "BBBBBBBBBB";

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final Long DEFAULT_ID_INVOICE = 1L;
    private static final Long UPDATED_ID_INVOICE = 2L;

    private static final Long DEFAULT_ID_BRANCH = 1L;
    private static final Long UPDATED_ID_BRANCH = 2L;

    private static final Long DEFAULT_ID_BOX = 1L;
    private static final Long UPDATED_ID_BOX = 2L;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

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
            .observation(DEFAULT_OBSERVATION)
            .detail(DEFAULT_DETAIL)
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .price(DEFAULT_PRICE)
            .subtotal(DEFAULT_SUBTOTAL)
            .idProduct(DEFAULT_ID_PRODUCT)
            .initialCurrentStock(DEFAULT_INITIAL_CURRENT_STOCK)
            .finalCurrentStock(DEFAULT_FINAL_CURRENT_STOCK)
            .typeMethod(DEFAULT_TYPE_METHOD)
            .discount(DEFAULT_DISCOUNT)
            .idInvoice(DEFAULT_ID_INVOICE)
            .idBranch(DEFAULT_ID_BRANCH)
            .idBox(DEFAULT_ID_BOX)
            .date(DEFAULT_DATE);
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
        assertThat(testMedicalConsultationDetails.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testMedicalConsultationDetails.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testMedicalConsultationDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMedicalConsultationDetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMedicalConsultationDetails.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMedicalConsultationDetails.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testMedicalConsultationDetails.getIdProduct()).isEqualTo(DEFAULT_ID_PRODUCT);
        assertThat(testMedicalConsultationDetails.getInitialCurrentStock()).isEqualTo(DEFAULT_INITIAL_CURRENT_STOCK);
        assertThat(testMedicalConsultationDetails.getFinalCurrentStock()).isEqualTo(DEFAULT_FINAL_CURRENT_STOCK);
        assertThat(testMedicalConsultationDetails.getTypeMethod()).isEqualTo(DEFAULT_TYPE_METHOD);
        assertThat(testMedicalConsultationDetails.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testMedicalConsultationDetails.getIdInvoice()).isEqualTo(DEFAULT_ID_INVOICE);
        assertThat(testMedicalConsultationDetails.getIdBranch()).isEqualTo(DEFAULT_ID_BRANCH);
        assertThat(testMedicalConsultationDetails.getIdBox()).isEqualTo(DEFAULT_ID_BOX);
        assertThat(testMedicalConsultationDetails.getDate()).isEqualTo(DEFAULT_DATE);
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
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
            .andExpect(jsonPath("$.[*].idProduct").value(hasItem(DEFAULT_ID_PRODUCT.intValue())))
            .andExpect(jsonPath("$.[*].initialCurrentStock").value(hasItem(DEFAULT_INITIAL_CURRENT_STOCK)))
            .andExpect(jsonPath("$.[*].finalCurrentStock").value(hasItem(DEFAULT_FINAL_CURRENT_STOCK)))
            .andExpect(jsonPath("$.[*].typeMethod").value(hasItem(DEFAULT_TYPE_METHOD.toString())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].idInvoice").value(hasItem(DEFAULT_ID_INVOICE.intValue())))
            .andExpect(jsonPath("$.[*].idBranch").value(hasItem(DEFAULT_ID_BRANCH.intValue())))
            .andExpect(jsonPath("$.[*].idBox").value(hasItem(DEFAULT_ID_BOX.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
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
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.idProduct").value(DEFAULT_ID_PRODUCT.intValue()))
            .andExpect(jsonPath("$.initialCurrentStock").value(DEFAULT_INITIAL_CURRENT_STOCK))
            .andExpect(jsonPath("$.finalCurrentStock").value(DEFAULT_FINAL_CURRENT_STOCK))
            .andExpect(jsonPath("$.typeMethod").value(DEFAULT_TYPE_METHOD.toString()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.idInvoice").value(DEFAULT_ID_INVOICE.intValue()))
            .andExpect(jsonPath("$.idBranch").value(DEFAULT_ID_BRANCH.intValue()))
            .andExpect(jsonPath("$.idBox").value(DEFAULT_ID_BOX.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
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
            .observation(UPDATED_OBSERVATION)
            .detail(UPDATED_DETAIL)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE)
            .subtotal(UPDATED_SUBTOTAL)
            .idProduct(UPDATED_ID_PRODUCT)
            .initialCurrentStock(UPDATED_INITIAL_CURRENT_STOCK)
            .finalCurrentStock(UPDATED_FINAL_CURRENT_STOCK)
            .typeMethod(UPDATED_TYPE_METHOD)
            .discount(UPDATED_DISCOUNT)
            .idInvoice(UPDATED_ID_INVOICE)
            .idBranch(UPDATED_ID_BRANCH)
            .idBox(UPDATED_ID_BOX)
            .date(UPDATED_DATE);

        restMedicalConsultationDetailsMockMvc.perform(put("/api/medical-consultation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalConsultationDetails)))
            .andExpect(status().isOk());

        // Validate the MedicalConsultationDetails in the database
        List<MedicalConsultationDetails> medicalConsultationDetailsList = medicalConsultationDetailsRepository.findAll();
        assertThat(medicalConsultationDetailsList).hasSize(databaseSizeBeforeUpdate);
        MedicalConsultationDetails testMedicalConsultationDetails = medicalConsultationDetailsList.get(medicalConsultationDetailsList.size() - 1);
        assertThat(testMedicalConsultationDetails.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testMedicalConsultationDetails.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testMedicalConsultationDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedicalConsultationDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMedicalConsultationDetails.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMedicalConsultationDetails.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testMedicalConsultationDetails.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
        assertThat(testMedicalConsultationDetails.getInitialCurrentStock()).isEqualTo(UPDATED_INITIAL_CURRENT_STOCK);
        assertThat(testMedicalConsultationDetails.getFinalCurrentStock()).isEqualTo(UPDATED_FINAL_CURRENT_STOCK);
        assertThat(testMedicalConsultationDetails.getTypeMethod()).isEqualTo(UPDATED_TYPE_METHOD);
        assertThat(testMedicalConsultationDetails.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testMedicalConsultationDetails.getIdInvoice()).isEqualTo(UPDATED_ID_INVOICE);
        assertThat(testMedicalConsultationDetails.getIdBranch()).isEqualTo(UPDATED_ID_BRANCH);
        assertThat(testMedicalConsultationDetails.getIdBox()).isEqualTo(UPDATED_ID_BOX);
        assertThat(testMedicalConsultationDetails.getDate()).isEqualTo(UPDATED_DATE);
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
