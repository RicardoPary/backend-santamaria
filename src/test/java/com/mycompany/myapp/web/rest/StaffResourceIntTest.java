package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SantamariaApp;

import com.mycompany.myapp.domain.Staff;
import com.mycompany.myapp.repository.StaffRepository;
import com.mycompany.myapp.service.StaffService;
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
 * Test class for the StaffResource REST controller.
 *
 * @see StaffResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SantamariaApp.class)
public class StaffResourceIntTest {

    private static final Long DEFAULT_CI = 1L;
    private static final Long UPDATED_CI = 2L;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_MATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_ESPECIALITY = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIALITY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffService staffService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStaffMockMvc;

    private Staff staff;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StaffResource staffResource = new StaffResource(staffService);
        this.restStaffMockMvc = MockMvcBuilders.standaloneSetup(staffResource)
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
    public static Staff createEntity(EntityManager em) {
        Staff staff = new Staff()
            .ci(DEFAULT_CI)
            .nombre(DEFAULT_NOMBRE)
            .paterno(DEFAULT_PATERNO)
            .materno(DEFAULT_MATERNO)
            .email(DEFAULT_EMAIL)
            .profession(DEFAULT_PROFESSION)
            .especiality(DEFAULT_ESPECIALITY)
            .address(DEFAULT_ADDRESS);
        return staff;
    }

    @Before
    public void initTest() {
        staff = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaff() throws Exception {
        int databaseSizeBeforeCreate = staffRepository.findAll().size();

        // Create the Staff
        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isCreated());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeCreate + 1);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getCi()).isEqualTo(DEFAULT_CI);
        assertThat(testStaff.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testStaff.getPaterno()).isEqualTo(DEFAULT_PATERNO);
        assertThat(testStaff.getMaterno()).isEqualTo(DEFAULT_MATERNO);
        assertThat(testStaff.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStaff.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testStaff.getEspeciality()).isEqualTo(DEFAULT_ESPECIALITY);
        assertThat(testStaff.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createStaffWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staffRepository.findAll().size();

        // Create the Staff with an existing ID
        staff.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        // Get all the staffList
        restStaffMockMvc.perform(get("/api/staff?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staff.getId().intValue())))
            .andExpect(jsonPath("$.[*].ci").value(hasItem(DEFAULT_CI.intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].paterno").value(hasItem(DEFAULT_PATERNO.toString())))
            .andExpect(jsonPath("$.[*].materno").value(hasItem(DEFAULT_MATERNO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION.toString())))
            .andExpect(jsonPath("$.[*].especiality").value(hasItem(DEFAULT_ESPECIALITY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }
    
    @Test
    @Transactional
    public void getStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        // Get the staff
        restStaffMockMvc.perform(get("/api/staff/{id}", staff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(staff.getId().intValue()))
            .andExpect(jsonPath("$.ci").value(DEFAULT_CI.intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.paterno").value(DEFAULT_PATERNO.toString()))
            .andExpect(jsonPath("$.materno").value(DEFAULT_MATERNO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION.toString()))
            .andExpect(jsonPath("$.especiality").value(DEFAULT_ESPECIALITY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStaff() throws Exception {
        // Get the staff
        restStaffMockMvc.perform(get("/api/staff/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaff() throws Exception {
        // Initialize the database
        staffService.save(staff);

        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Update the staff
        Staff updatedStaff = staffRepository.findById(staff.getId()).get();
        // Disconnect from session so that the updates on updatedStaff are not directly saved in db
        em.detach(updatedStaff);
        updatedStaff
            .ci(UPDATED_CI)
            .nombre(UPDATED_NOMBRE)
            .paterno(UPDATED_PATERNO)
            .materno(UPDATED_MATERNO)
            .email(UPDATED_EMAIL)
            .profession(UPDATED_PROFESSION)
            .especiality(UPDATED_ESPECIALITY)
            .address(UPDATED_ADDRESS);

        restStaffMockMvc.perform(put("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaff)))
            .andExpect(status().isOk());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getCi()).isEqualTo(UPDATED_CI);
        assertThat(testStaff.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testStaff.getPaterno()).isEqualTo(UPDATED_PATERNO);
        assertThat(testStaff.getMaterno()).isEqualTo(UPDATED_MATERNO);
        assertThat(testStaff.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStaff.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testStaff.getEspeciality()).isEqualTo(UPDATED_ESPECIALITY);
        assertThat(testStaff.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Create the Staff

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffMockMvc.perform(put("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaff() throws Exception {
        // Initialize the database
        staffService.save(staff);

        int databaseSizeBeforeDelete = staffRepository.findAll().size();

        // Get the staff
        restStaffMockMvc.perform(delete("/api/staff/{id}", staff.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Staff.class);
        Staff staff1 = new Staff();
        staff1.setId(1L);
        Staff staff2 = new Staff();
        staff2.setId(staff1.getId());
        assertThat(staff1).isEqualTo(staff2);
        staff2.setId(2L);
        assertThat(staff1).isNotEqualTo(staff2);
        staff1.setId(null);
        assertThat(staff1).isNotEqualTo(staff2);
    }
}
