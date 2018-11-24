package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SantamariaApp;

import com.mycompany.myapp.domain.TypeAttention;
import com.mycompany.myapp.repository.TypeAttentionRepository;
import com.mycompany.myapp.service.TypeAttentionService;
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
 * Test class for the TypeAttentionResource REST controller.
 *
 * @see TypeAttentionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SantamariaApp.class)
public class TypeAttentionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Long DEFAULT_ID_PROVIDER = 1L;
    private static final Long UPDATED_ID_PROVIDER = 2L;

    @Autowired
    private TypeAttentionRepository typeAttentionRepository;

    @Autowired
    private TypeAttentionService typeAttentionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeAttentionMockMvc;

    private TypeAttention typeAttention;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeAttentionResource typeAttentionResource = new TypeAttentionResource(typeAttentionService);
        this.restTypeAttentionMockMvc = MockMvcBuilders.standaloneSetup(typeAttentionResource)
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
    public static TypeAttention createEntity(EntityManager em) {
        TypeAttention typeAttention = new TypeAttention()
            .name(DEFAULT_NAME)
            .company(DEFAULT_COMPANY)
            .type(DEFAULT_TYPE)
            .phone(DEFAULT_PHONE)
            .detail(DEFAULT_DETAIL)
            .quantity(DEFAULT_QUANTITY)
            .price(DEFAULT_PRICE)
            .idProvider(DEFAULT_ID_PROVIDER);
        return typeAttention;
    }

    @Before
    public void initTest() {
        typeAttention = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeAttention() throws Exception {
        int databaseSizeBeforeCreate = typeAttentionRepository.findAll().size();

        // Create the TypeAttention
        restTypeAttentionMockMvc.perform(post("/api/type-attentions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeAttention)))
            .andExpect(status().isCreated());

        // Validate the TypeAttention in the database
        List<TypeAttention> typeAttentionList = typeAttentionRepository.findAll();
        assertThat(typeAttentionList).hasSize(databaseSizeBeforeCreate + 1);
        TypeAttention testTypeAttention = typeAttentionList.get(typeAttentionList.size() - 1);
        assertThat(testTypeAttention.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTypeAttention.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testTypeAttention.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTypeAttention.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testTypeAttention.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testTypeAttention.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTypeAttention.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTypeAttention.getIdProvider()).isEqualTo(DEFAULT_ID_PROVIDER);
    }

    @Test
    @Transactional
    public void createTypeAttentionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeAttentionRepository.findAll().size();

        // Create the TypeAttention with an existing ID
        typeAttention.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeAttentionMockMvc.perform(post("/api/type-attentions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeAttention)))
            .andExpect(status().isBadRequest());

        // Validate the TypeAttention in the database
        List<TypeAttention> typeAttentionList = typeAttentionRepository.findAll();
        assertThat(typeAttentionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeAttentions() throws Exception {
        // Initialize the database
        typeAttentionRepository.saveAndFlush(typeAttention);

        // Get all the typeAttentionList
        restTypeAttentionMockMvc.perform(get("/api/type-attentions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeAttention.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].idProvider").value(hasItem(DEFAULT_ID_PROVIDER.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeAttention() throws Exception {
        // Initialize the database
        typeAttentionRepository.saveAndFlush(typeAttention);

        // Get the typeAttention
        restTypeAttentionMockMvc.perform(get("/api/type-attentions/{id}", typeAttention.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeAttention.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.idProvider").value(DEFAULT_ID_PROVIDER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeAttention() throws Exception {
        // Get the typeAttention
        restTypeAttentionMockMvc.perform(get("/api/type-attentions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeAttention() throws Exception {
        // Initialize the database
        typeAttentionService.save(typeAttention);

        int databaseSizeBeforeUpdate = typeAttentionRepository.findAll().size();

        // Update the typeAttention
        TypeAttention updatedTypeAttention = typeAttentionRepository.findById(typeAttention.getId()).get();
        // Disconnect from session so that the updates on updatedTypeAttention are not directly saved in db
        em.detach(updatedTypeAttention);
        updatedTypeAttention
            .name(UPDATED_NAME)
            .company(UPDATED_COMPANY)
            .type(UPDATED_TYPE)
            .phone(UPDATED_PHONE)
            .detail(UPDATED_DETAIL)
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE)
            .idProvider(UPDATED_ID_PROVIDER);

        restTypeAttentionMockMvc.perform(put("/api/type-attentions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeAttention)))
            .andExpect(status().isOk());

        // Validate the TypeAttention in the database
        List<TypeAttention> typeAttentionList = typeAttentionRepository.findAll();
        assertThat(typeAttentionList).hasSize(databaseSizeBeforeUpdate);
        TypeAttention testTypeAttention = typeAttentionList.get(typeAttentionList.size() - 1);
        assertThat(testTypeAttention.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTypeAttention.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testTypeAttention.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTypeAttention.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testTypeAttention.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testTypeAttention.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTypeAttention.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTypeAttention.getIdProvider()).isEqualTo(UPDATED_ID_PROVIDER);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeAttention() throws Exception {
        int databaseSizeBeforeUpdate = typeAttentionRepository.findAll().size();

        // Create the TypeAttention

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeAttentionMockMvc.perform(put("/api/type-attentions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeAttention)))
            .andExpect(status().isBadRequest());

        // Validate the TypeAttention in the database
        List<TypeAttention> typeAttentionList = typeAttentionRepository.findAll();
        assertThat(typeAttentionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeAttention() throws Exception {
        // Initialize the database
        typeAttentionService.save(typeAttention);

        int databaseSizeBeforeDelete = typeAttentionRepository.findAll().size();

        // Get the typeAttention
        restTypeAttentionMockMvc.perform(delete("/api/type-attentions/{id}", typeAttention.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeAttention> typeAttentionList = typeAttentionRepository.findAll();
        assertThat(typeAttentionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeAttention.class);
        TypeAttention typeAttention1 = new TypeAttention();
        typeAttention1.setId(1L);
        TypeAttention typeAttention2 = new TypeAttention();
        typeAttention2.setId(typeAttention1.getId());
        assertThat(typeAttention1).isEqualTo(typeAttention2);
        typeAttention2.setId(2L);
        assertThat(typeAttention1).isNotEqualTo(typeAttention2);
        typeAttention1.setId(null);
        assertThat(typeAttention1).isNotEqualTo(typeAttention2);
    }
}
