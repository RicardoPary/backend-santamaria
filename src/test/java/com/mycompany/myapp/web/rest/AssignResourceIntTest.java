package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SantamariaApp;

import com.mycompany.myapp.domain.Assign;
import com.mycompany.myapp.repository.AssignRepository;
import com.mycompany.myapp.service.AssignService;
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
 * Test class for the AssignResource REST controller.
 *
 * @see AssignResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SantamariaApp.class)
public class AssignResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Long DEFAULT_ID_CONSULTATION = 1L;
    private static final Long UPDATED_ID_CONSULTATION = 2L;

    @Autowired
    private AssignRepository assignRepository;

    @Autowired
    private AssignService assignService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssignMockMvc;

    private Assign assign;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssignResource assignResource = new AssignResource(assignService);
        this.restAssignMockMvc = MockMvcBuilders.standaloneSetup(assignResource)
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
    public static Assign createEntity(EntityManager em) {
        Assign assign = new Assign()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .quantity(DEFAULT_QUANTITY)
            .idConsultation(DEFAULT_ID_CONSULTATION);
        return assign;
    }

    @Before
    public void initTest() {
        assign = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssign() throws Exception {
        int databaseSizeBeforeCreate = assignRepository.findAll().size();

        // Create the Assign
        restAssignMockMvc.perform(post("/api/assigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assign)))
            .andExpect(status().isCreated());

        // Validate the Assign in the database
        List<Assign> assignList = assignRepository.findAll();
        assertThat(assignList).hasSize(databaseSizeBeforeCreate + 1);
        Assign testAssign = assignList.get(assignList.size() - 1);
        assertThat(testAssign.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAssign.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testAssign.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testAssign.getIdConsultation()).isEqualTo(DEFAULT_ID_CONSULTATION);
    }

    @Test
    @Transactional
    public void createAssignWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assignRepository.findAll().size();

        // Create the Assign with an existing ID
        assign.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssignMockMvc.perform(post("/api/assigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assign)))
            .andExpect(status().isBadRequest());

        // Validate the Assign in the database
        List<Assign> assignList = assignRepository.findAll();
        assertThat(assignList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAssigns() throws Exception {
        // Initialize the database
        assignRepository.saveAndFlush(assign);

        // Get all the assignList
        restAssignMockMvc.perform(get("/api/assigns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assign.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].idConsultation").value(hasItem(DEFAULT_ID_CONSULTATION.intValue())));
    }
    
    @Test
    @Transactional
    public void getAssign() throws Exception {
        // Initialize the database
        assignRepository.saveAndFlush(assign);

        // Get the assign
        restAssignMockMvc.perform(get("/api/assigns/{id}", assign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assign.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.idConsultation").value(DEFAULT_ID_CONSULTATION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssign() throws Exception {
        // Get the assign
        restAssignMockMvc.perform(get("/api/assigns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssign() throws Exception {
        // Initialize the database
        assignService.save(assign);

        int databaseSizeBeforeUpdate = assignRepository.findAll().size();

        // Update the assign
        Assign updatedAssign = assignRepository.findById(assign.getId()).get();
        // Disconnect from session so that the updates on updatedAssign are not directly saved in db
        em.detach(updatedAssign);
        updatedAssign
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .quantity(UPDATED_QUANTITY)
            .idConsultation(UPDATED_ID_CONSULTATION);

        restAssignMockMvc.perform(put("/api/assigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssign)))
            .andExpect(status().isOk());

        // Validate the Assign in the database
        List<Assign> assignList = assignRepository.findAll();
        assertThat(assignList).hasSize(databaseSizeBeforeUpdate);
        Assign testAssign = assignList.get(assignList.size() - 1);
        assertThat(testAssign.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssign.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAssign.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testAssign.getIdConsultation()).isEqualTo(UPDATED_ID_CONSULTATION);
    }

    @Test
    @Transactional
    public void updateNonExistingAssign() throws Exception {
        int databaseSizeBeforeUpdate = assignRepository.findAll().size();

        // Create the Assign

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssignMockMvc.perform(put("/api/assigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assign)))
            .andExpect(status().isBadRequest());

        // Validate the Assign in the database
        List<Assign> assignList = assignRepository.findAll();
        assertThat(assignList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssign() throws Exception {
        // Initialize the database
        assignService.save(assign);

        int databaseSizeBeforeDelete = assignRepository.findAll().size();

        // Get the assign
        restAssignMockMvc.perform(delete("/api/assigns/{id}", assign.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Assign> assignList = assignRepository.findAll();
        assertThat(assignList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assign.class);
        Assign assign1 = new Assign();
        assign1.setId(1L);
        Assign assign2 = new Assign();
        assign2.setId(assign1.getId());
        assertThat(assign1).isEqualTo(assign2);
        assign2.setId(2L);
        assertThat(assign1).isNotEqualTo(assign2);
        assign1.setId(null);
        assertThat(assign1).isNotEqualTo(assign2);
    }
}
