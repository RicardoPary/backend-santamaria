package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SantamariaApp;

import com.mycompany.myapp.domain.MedicalHistory;
import com.mycompany.myapp.repository.MedicalHistoryRepository;
import com.mycompany.myapp.service.MedicalHistoryService;
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
 * Test class for the MedicalHistoryResource REST controller.
 *
 * @see MedicalHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SantamariaApp.class)
public class MedicalHistoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_ID_STAFF = 1L;
    private static final Long UPDATED_ID_STAFF = 2L;

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicalHistoryMockMvc;

    private MedicalHistory medicalHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalHistoryResource medicalHistoryResource = new MedicalHistoryResource(medicalHistoryService);
        this.restMedicalHistoryMockMvc = MockMvcBuilders.standaloneSetup(medicalHistoryResource)
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
    public static MedicalHistory createEntity(EntityManager em) {
        MedicalHistory medicalHistory = new MedicalHistory()
            .name(DEFAULT_NAME)
            .amount(DEFAULT_AMOUNT)
            .description(DEFAULT_DESCRIPTION)
            .company(DEFAULT_COMPANY)
            .type(DEFAULT_TYPE)
            .phone(DEFAULT_PHONE)
            .date(DEFAULT_DATE)
            .idStaff(DEFAULT_ID_STAFF);
        return medicalHistory;
    }

    @Before
    public void initTest() {
        medicalHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalHistory() throws Exception {
        int databaseSizeBeforeCreate = medicalHistoryRepository.findAll().size();

        // Create the MedicalHistory
        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isCreated());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalHistory testMedicalHistory = medicalHistoryList.get(medicalHistoryList.size() - 1);
        assertThat(testMedicalHistory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMedicalHistory.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testMedicalHistory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMedicalHistory.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testMedicalHistory.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMedicalHistory.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testMedicalHistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMedicalHistory.getIdStaff()).isEqualTo(DEFAULT_ID_STAFF);
    }

    @Test
    @Transactional
    public void createMedicalHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalHistoryRepository.findAll().size();

        // Create the MedicalHistory with an existing ID
        medicalHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalHistoryMockMvc.perform(post("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedicalHistories() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);

        // Get all the medicalHistoryList
        restMedicalHistoryMockMvc.perform(get("/api/medical-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].idStaff").value(hasItem(DEFAULT_ID_STAFF.intValue())));
    }
    
    @Test
    @Transactional
    public void getMedicalHistory() throws Exception {
        // Initialize the database
        medicalHistoryRepository.saveAndFlush(medicalHistory);

        // Get the medicalHistory
        restMedicalHistoryMockMvc.perform(get("/api/medical-histories/{id}", medicalHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalHistory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.idStaff").value(DEFAULT_ID_STAFF.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalHistory() throws Exception {
        // Get the medicalHistory
        restMedicalHistoryMockMvc.perform(get("/api/medical-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalHistory() throws Exception {
        // Initialize the database
        medicalHistoryService.save(medicalHistory);

        int databaseSizeBeforeUpdate = medicalHistoryRepository.findAll().size();

        // Update the medicalHistory
        MedicalHistory updatedMedicalHistory = medicalHistoryRepository.findById(medicalHistory.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalHistory are not directly saved in db
        em.detach(updatedMedicalHistory);
        updatedMedicalHistory
            .name(UPDATED_NAME)
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .company(UPDATED_COMPANY)
            .type(UPDATED_TYPE)
            .phone(UPDATED_PHONE)
            .date(UPDATED_DATE)
            .idStaff(UPDATED_ID_STAFF);

        restMedicalHistoryMockMvc.perform(put("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalHistory)))
            .andExpect(status().isOk());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeUpdate);
        MedicalHistory testMedicalHistory = medicalHistoryList.get(medicalHistoryList.size() - 1);
        assertThat(testMedicalHistory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedicalHistory.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testMedicalHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMedicalHistory.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testMedicalHistory.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMedicalHistory.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMedicalHistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMedicalHistory.getIdStaff()).isEqualTo(UPDATED_ID_STAFF);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalHistory() throws Exception {
        int databaseSizeBeforeUpdate = medicalHistoryRepository.findAll().size();

        // Create the MedicalHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalHistoryMockMvc.perform(put("/api/medical-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalHistory)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalHistory in the database
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicalHistory() throws Exception {
        // Initialize the database
        medicalHistoryService.save(medicalHistory);

        int databaseSizeBeforeDelete = medicalHistoryRepository.findAll().size();

        // Get the medicalHistory
        restMedicalHistoryMockMvc.perform(delete("/api/medical-histories/{id}", medicalHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MedicalHistory> medicalHistoryList = medicalHistoryRepository.findAll();
        assertThat(medicalHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalHistory.class);
        MedicalHistory medicalHistory1 = new MedicalHistory();
        medicalHistory1.setId(1L);
        MedicalHistory medicalHistory2 = new MedicalHistory();
        medicalHistory2.setId(medicalHistory1.getId());
        assertThat(medicalHistory1).isEqualTo(medicalHistory2);
        medicalHistory2.setId(2L);
        assertThat(medicalHistory1).isNotEqualTo(medicalHistory2);
        medicalHistory1.setId(null);
        assertThat(medicalHistory1).isNotEqualTo(medicalHistory2);
    }
}
