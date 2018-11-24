package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SantamariaApp;

import com.mycompany.myapp.domain.Supply;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.repository.SupplyRepository;
import com.mycompany.myapp.service.SupplyService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.SupplyCriteria;
import com.mycompany.myapp.service.SupplyQueryService;

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
 * Test class for the SupplyResource REST controller.
 *
 * @see SupplyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SantamariaApp.class)
public class SupplyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STOCK = 1;
    private static final Integer UPDATED_STOCK = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_SALE_PRICE = 1D;
    private static final Double UPDATED_SALE_PRICE = 2D;

    private static final Double DEFAULT_WHOLESALE_PRICE = 1D;
    private static final Double UPDATED_WHOLESALE_PRICE = 2D;

    private static final Double DEFAULT_PURCHASE_PRICE = 1D;
    private static final Double UPDATED_PURCHASE_PRICE = 2D;

    private static final String DEFAULT_MARKE = "AAAAAAAAAA";
    private static final String UPDATED_MARKE = "BBBBBBBBBB";

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final String DEFAULT_BARCODE = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE = "BBBBBBBBBB";

    private static final String DEFAULT_URL_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_URL_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_CACHE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_CACHE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INVENTORY = false;
    private static final Boolean UPDATED_INVENTORY = true;

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private SupplyQueryService supplyQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSupplyMockMvc;

    private Supply supply;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplyResource supplyResource = new SupplyResource(supplyService, supplyQueryService);
        this.restSupplyMockMvc = MockMvcBuilders.standaloneSetup(supplyResource)
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
    public static Supply createEntity(EntityManager em) {
        Supply supply = new Supply()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .stock(DEFAULT_STOCK)
            .description(DEFAULT_DESCRIPTION)
            .salePrice(DEFAULT_SALE_PRICE)
            .wholesalePrice(DEFAULT_WHOLESALE_PRICE)
            .purchasePrice(DEFAULT_PURCHASE_PRICE)
            .marke(DEFAULT_MARKE)
            .discount(DEFAULT_DISCOUNT)
            .barcode(DEFAULT_BARCODE)
            .urlImage(DEFAULT_URL_IMAGE)
            .imageName(DEFAULT_IMAGE_NAME)
            .imageCache(DEFAULT_IMAGE_CACHE)
            .inventory(DEFAULT_INVENTORY);
        return supply;
    }

    @Before
    public void initTest() {
        supply = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupply() throws Exception {
        int databaseSizeBeforeCreate = supplyRepository.findAll().size();

        // Create the Supply
        restSupplyMockMvc.perform(post("/api/supplies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supply)))
            .andExpect(status().isCreated());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeCreate + 1);
        Supply testSupply = supplyList.get(supplyList.size() - 1);
        assertThat(testSupply.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupply.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSupply.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testSupply.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSupply.getSalePrice()).isEqualTo(DEFAULT_SALE_PRICE);
        assertThat(testSupply.getWholesalePrice()).isEqualTo(DEFAULT_WHOLESALE_PRICE);
        assertThat(testSupply.getPurchasePrice()).isEqualTo(DEFAULT_PURCHASE_PRICE);
        assertThat(testSupply.getMarke()).isEqualTo(DEFAULT_MARKE);
        assertThat(testSupply.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testSupply.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testSupply.getUrlImage()).isEqualTo(DEFAULT_URL_IMAGE);
        assertThat(testSupply.getImageName()).isEqualTo(DEFAULT_IMAGE_NAME);
        assertThat(testSupply.getImageCache()).isEqualTo(DEFAULT_IMAGE_CACHE);
        assertThat(testSupply.isInventory()).isEqualTo(DEFAULT_INVENTORY);
    }

    @Test
    @Transactional
    public void createSupplyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplyRepository.findAll().size();

        // Create the Supply with an existing ID
        supply.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyMockMvc.perform(post("/api/supplies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supply)))
            .andExpect(status().isBadRequest());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSupplies() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList
        restSupplyMockMvc.perform(get("/api/supplies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supply.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].salePrice").value(hasItem(DEFAULT_SALE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].wholesalePrice").value(hasItem(DEFAULT_WHOLESALE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].purchasePrice").value(hasItem(DEFAULT_PURCHASE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].marke").value(hasItem(DEFAULT_MARKE.toString())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE.toString())))
            .andExpect(jsonPath("$.[*].urlImage").value(hasItem(DEFAULT_URL_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].imageName").value(hasItem(DEFAULT_IMAGE_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageCache").value(hasItem(DEFAULT_IMAGE_CACHE.toString())))
            .andExpect(jsonPath("$.[*].inventory").value(hasItem(DEFAULT_INVENTORY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSupply() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get the supply
        restSupplyMockMvc.perform(get("/api/supplies/{id}", supply.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supply.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.salePrice").value(DEFAULT_SALE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.wholesalePrice").value(DEFAULT_WHOLESALE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.purchasePrice").value(DEFAULT_PURCHASE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.marke").value(DEFAULT_MARKE.toString()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE.toString()))
            .andExpect(jsonPath("$.urlImage").value(DEFAULT_URL_IMAGE.toString()))
            .andExpect(jsonPath("$.imageName").value(DEFAULT_IMAGE_NAME.toString()))
            .andExpect(jsonPath("$.imageCache").value(DEFAULT_IMAGE_CACHE.toString()))
            .andExpect(jsonPath("$.inventory").value(DEFAULT_INVENTORY.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllSuppliesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where name equals to DEFAULT_NAME
        defaultSupplyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the supplyList where name equals to UPDATED_NAME
        defaultSupplyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSupplyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the supplyList where name equals to UPDATED_NAME
        defaultSupplyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where name is not null
        defaultSupplyShouldBeFound("name.specified=true");

        // Get all the supplyList where name is null
        defaultSupplyShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where type equals to DEFAULT_TYPE
        defaultSupplyShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the supplyList where type equals to UPDATED_TYPE
        defaultSupplyShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultSupplyShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the supplyList where type equals to UPDATED_TYPE
        defaultSupplyShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where type is not null
        defaultSupplyShouldBeFound("type.specified=true");

        // Get all the supplyList where type is null
        defaultSupplyShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesByStockIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where stock equals to DEFAULT_STOCK
        defaultSupplyShouldBeFound("stock.equals=" + DEFAULT_STOCK);

        // Get all the supplyList where stock equals to UPDATED_STOCK
        defaultSupplyShouldNotBeFound("stock.equals=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void getAllSuppliesByStockIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where stock in DEFAULT_STOCK or UPDATED_STOCK
        defaultSupplyShouldBeFound("stock.in=" + DEFAULT_STOCK + "," + UPDATED_STOCK);

        // Get all the supplyList where stock equals to UPDATED_STOCK
        defaultSupplyShouldNotBeFound("stock.in=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void getAllSuppliesByStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where stock is not null
        defaultSupplyShouldBeFound("stock.specified=true");

        // Get all the supplyList where stock is null
        defaultSupplyShouldNotBeFound("stock.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesByStockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where stock greater than or equals to DEFAULT_STOCK
        defaultSupplyShouldBeFound("stock.greaterOrEqualThan=" + DEFAULT_STOCK);

        // Get all the supplyList where stock greater than or equals to UPDATED_STOCK
        defaultSupplyShouldNotBeFound("stock.greaterOrEqualThan=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void getAllSuppliesByStockIsLessThanSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where stock less than or equals to DEFAULT_STOCK
        defaultSupplyShouldNotBeFound("stock.lessThan=" + DEFAULT_STOCK);

        // Get all the supplyList where stock less than or equals to UPDATED_STOCK
        defaultSupplyShouldBeFound("stock.lessThan=" + UPDATED_STOCK);
    }


    @Test
    @Transactional
    public void getAllSuppliesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where description equals to DEFAULT_DESCRIPTION
        defaultSupplyShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the supplyList where description equals to UPDATED_DESCRIPTION
        defaultSupplyShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSuppliesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSupplyShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the supplyList where description equals to UPDATED_DESCRIPTION
        defaultSupplyShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSuppliesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where description is not null
        defaultSupplyShouldBeFound("description.specified=true");

        // Get all the supplyList where description is null
        defaultSupplyShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesBySalePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where salePrice equals to DEFAULT_SALE_PRICE
        defaultSupplyShouldBeFound("salePrice.equals=" + DEFAULT_SALE_PRICE);

        // Get all the supplyList where salePrice equals to UPDATED_SALE_PRICE
        defaultSupplyShouldNotBeFound("salePrice.equals=" + UPDATED_SALE_PRICE);
    }

    @Test
    @Transactional
    public void getAllSuppliesBySalePriceIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where salePrice in DEFAULT_SALE_PRICE or UPDATED_SALE_PRICE
        defaultSupplyShouldBeFound("salePrice.in=" + DEFAULT_SALE_PRICE + "," + UPDATED_SALE_PRICE);

        // Get all the supplyList where salePrice equals to UPDATED_SALE_PRICE
        defaultSupplyShouldNotBeFound("salePrice.in=" + UPDATED_SALE_PRICE);
    }

    @Test
    @Transactional
    public void getAllSuppliesBySalePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where salePrice is not null
        defaultSupplyShouldBeFound("salePrice.specified=true");

        // Get all the supplyList where salePrice is null
        defaultSupplyShouldNotBeFound("salePrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesByWholesalePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where wholesalePrice equals to DEFAULT_WHOLESALE_PRICE
        defaultSupplyShouldBeFound("wholesalePrice.equals=" + DEFAULT_WHOLESALE_PRICE);

        // Get all the supplyList where wholesalePrice equals to UPDATED_WHOLESALE_PRICE
        defaultSupplyShouldNotBeFound("wholesalePrice.equals=" + UPDATED_WHOLESALE_PRICE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByWholesalePriceIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where wholesalePrice in DEFAULT_WHOLESALE_PRICE or UPDATED_WHOLESALE_PRICE
        defaultSupplyShouldBeFound("wholesalePrice.in=" + DEFAULT_WHOLESALE_PRICE + "," + UPDATED_WHOLESALE_PRICE);

        // Get all the supplyList where wholesalePrice equals to UPDATED_WHOLESALE_PRICE
        defaultSupplyShouldNotBeFound("wholesalePrice.in=" + UPDATED_WHOLESALE_PRICE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByWholesalePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where wholesalePrice is not null
        defaultSupplyShouldBeFound("wholesalePrice.specified=true");

        // Get all the supplyList where wholesalePrice is null
        defaultSupplyShouldNotBeFound("wholesalePrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesByPurchasePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where purchasePrice equals to DEFAULT_PURCHASE_PRICE
        defaultSupplyShouldBeFound("purchasePrice.equals=" + DEFAULT_PURCHASE_PRICE);

        // Get all the supplyList where purchasePrice equals to UPDATED_PURCHASE_PRICE
        defaultSupplyShouldNotBeFound("purchasePrice.equals=" + UPDATED_PURCHASE_PRICE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByPurchasePriceIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where purchasePrice in DEFAULT_PURCHASE_PRICE or UPDATED_PURCHASE_PRICE
        defaultSupplyShouldBeFound("purchasePrice.in=" + DEFAULT_PURCHASE_PRICE + "," + UPDATED_PURCHASE_PRICE);

        // Get all the supplyList where purchasePrice equals to UPDATED_PURCHASE_PRICE
        defaultSupplyShouldNotBeFound("purchasePrice.in=" + UPDATED_PURCHASE_PRICE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByPurchasePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where purchasePrice is not null
        defaultSupplyShouldBeFound("purchasePrice.specified=true");

        // Get all the supplyList where purchasePrice is null
        defaultSupplyShouldNotBeFound("purchasePrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesByMarkeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where marke equals to DEFAULT_MARKE
        defaultSupplyShouldBeFound("marke.equals=" + DEFAULT_MARKE);

        // Get all the supplyList where marke equals to UPDATED_MARKE
        defaultSupplyShouldNotBeFound("marke.equals=" + UPDATED_MARKE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByMarkeIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where marke in DEFAULT_MARKE or UPDATED_MARKE
        defaultSupplyShouldBeFound("marke.in=" + DEFAULT_MARKE + "," + UPDATED_MARKE);

        // Get all the supplyList where marke equals to UPDATED_MARKE
        defaultSupplyShouldNotBeFound("marke.in=" + UPDATED_MARKE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByMarkeIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where marke is not null
        defaultSupplyShouldBeFound("marke.specified=true");

        // Get all the supplyList where marke is null
        defaultSupplyShouldNotBeFound("marke.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where discount equals to DEFAULT_DISCOUNT
        defaultSupplyShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the supplyList where discount equals to UPDATED_DISCOUNT
        defaultSupplyShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllSuppliesByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultSupplyShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the supplyList where discount equals to UPDATED_DISCOUNT
        defaultSupplyShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllSuppliesByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where discount is not null
        defaultSupplyShouldBeFound("discount.specified=true");

        // Get all the supplyList where discount is null
        defaultSupplyShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesByBarcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where barcode equals to DEFAULT_BARCODE
        defaultSupplyShouldBeFound("barcode.equals=" + DEFAULT_BARCODE);

        // Get all the supplyList where barcode equals to UPDATED_BARCODE
        defaultSupplyShouldNotBeFound("barcode.equals=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByBarcodeIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where barcode in DEFAULT_BARCODE or UPDATED_BARCODE
        defaultSupplyShouldBeFound("barcode.in=" + DEFAULT_BARCODE + "," + UPDATED_BARCODE);

        // Get all the supplyList where barcode equals to UPDATED_BARCODE
        defaultSupplyShouldNotBeFound("barcode.in=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByBarcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where barcode is not null
        defaultSupplyShouldBeFound("barcode.specified=true");

        // Get all the supplyList where barcode is null
        defaultSupplyShouldNotBeFound("barcode.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesByUrlImageIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where urlImage equals to DEFAULT_URL_IMAGE
        defaultSupplyShouldBeFound("urlImage.equals=" + DEFAULT_URL_IMAGE);

        // Get all the supplyList where urlImage equals to UPDATED_URL_IMAGE
        defaultSupplyShouldNotBeFound("urlImage.equals=" + UPDATED_URL_IMAGE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByUrlImageIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where urlImage in DEFAULT_URL_IMAGE or UPDATED_URL_IMAGE
        defaultSupplyShouldBeFound("urlImage.in=" + DEFAULT_URL_IMAGE + "," + UPDATED_URL_IMAGE);

        // Get all the supplyList where urlImage equals to UPDATED_URL_IMAGE
        defaultSupplyShouldNotBeFound("urlImage.in=" + UPDATED_URL_IMAGE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByUrlImageIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where urlImage is not null
        defaultSupplyShouldBeFound("urlImage.specified=true");

        // Get all the supplyList where urlImage is null
        defaultSupplyShouldNotBeFound("urlImage.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesByImageNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where imageName equals to DEFAULT_IMAGE_NAME
        defaultSupplyShouldBeFound("imageName.equals=" + DEFAULT_IMAGE_NAME);

        // Get all the supplyList where imageName equals to UPDATED_IMAGE_NAME
        defaultSupplyShouldNotBeFound("imageName.equals=" + UPDATED_IMAGE_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliesByImageNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where imageName in DEFAULT_IMAGE_NAME or UPDATED_IMAGE_NAME
        defaultSupplyShouldBeFound("imageName.in=" + DEFAULT_IMAGE_NAME + "," + UPDATED_IMAGE_NAME);

        // Get all the supplyList where imageName equals to UPDATED_IMAGE_NAME
        defaultSupplyShouldNotBeFound("imageName.in=" + UPDATED_IMAGE_NAME);
    }

    @Test
    @Transactional
    public void getAllSuppliesByImageNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where imageName is not null
        defaultSupplyShouldBeFound("imageName.specified=true");

        // Get all the supplyList where imageName is null
        defaultSupplyShouldNotBeFound("imageName.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesByImageCacheIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where imageCache equals to DEFAULT_IMAGE_CACHE
        defaultSupplyShouldBeFound("imageCache.equals=" + DEFAULT_IMAGE_CACHE);

        // Get all the supplyList where imageCache equals to UPDATED_IMAGE_CACHE
        defaultSupplyShouldNotBeFound("imageCache.equals=" + UPDATED_IMAGE_CACHE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByImageCacheIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where imageCache in DEFAULT_IMAGE_CACHE or UPDATED_IMAGE_CACHE
        defaultSupplyShouldBeFound("imageCache.in=" + DEFAULT_IMAGE_CACHE + "," + UPDATED_IMAGE_CACHE);

        // Get all the supplyList where imageCache equals to UPDATED_IMAGE_CACHE
        defaultSupplyShouldNotBeFound("imageCache.in=" + UPDATED_IMAGE_CACHE);
    }

    @Test
    @Transactional
    public void getAllSuppliesByImageCacheIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where imageCache is not null
        defaultSupplyShouldBeFound("imageCache.specified=true");

        // Get all the supplyList where imageCache is null
        defaultSupplyShouldNotBeFound("imageCache.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesByInventoryIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where inventory equals to DEFAULT_INVENTORY
        defaultSupplyShouldBeFound("inventory.equals=" + DEFAULT_INVENTORY);

        // Get all the supplyList where inventory equals to UPDATED_INVENTORY
        defaultSupplyShouldNotBeFound("inventory.equals=" + UPDATED_INVENTORY);
    }

    @Test
    @Transactional
    public void getAllSuppliesByInventoryIsInShouldWork() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where inventory in DEFAULT_INVENTORY or UPDATED_INVENTORY
        defaultSupplyShouldBeFound("inventory.in=" + DEFAULT_INVENTORY + "," + UPDATED_INVENTORY);

        // Get all the supplyList where inventory equals to UPDATED_INVENTORY
        defaultSupplyShouldNotBeFound("inventory.in=" + UPDATED_INVENTORY);
    }

    @Test
    @Transactional
    public void getAllSuppliesByInventoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyRepository.saveAndFlush(supply);

        // Get all the supplyList where inventory is not null
        defaultSupplyShouldBeFound("inventory.specified=true");

        // Get all the supplyList where inventory is null
        defaultSupplyShouldNotBeFound("inventory.specified=false");
    }

    @Test
    @Transactional
    public void getAllSuppliesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        Category category = CategoryResourceIntTest.createEntity(em);
        em.persist(category);
        em.flush();
        supply.setCategory(category);
        supplyRepository.saveAndFlush(supply);
        Long categoryId = category.getId();

        // Get all the supplyList where category equals to categoryId
        defaultSupplyShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the supplyList where category equals to categoryId + 1
        defaultSupplyShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplyShouldBeFound(String filter) throws Exception {
        restSupplyMockMvc.perform(get("/api/supplies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supply.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].salePrice").value(hasItem(DEFAULT_SALE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].wholesalePrice").value(hasItem(DEFAULT_WHOLESALE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].purchasePrice").value(hasItem(DEFAULT_PURCHASE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].marke").value(hasItem(DEFAULT_MARKE.toString())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE.toString())))
            .andExpect(jsonPath("$.[*].urlImage").value(hasItem(DEFAULT_URL_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].imageName").value(hasItem(DEFAULT_IMAGE_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageCache").value(hasItem(DEFAULT_IMAGE_CACHE.toString())))
            .andExpect(jsonPath("$.[*].inventory").value(hasItem(DEFAULT_INVENTORY.booleanValue())));

        // Check, that the count call also returns 1
        restSupplyMockMvc.perform(get("/api/supplies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSupplyShouldNotBeFound(String filter) throws Exception {
        restSupplyMockMvc.perform(get("/api/supplies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplyMockMvc.perform(get("/api/supplies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupply() throws Exception {
        // Get the supply
        restSupplyMockMvc.perform(get("/api/supplies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupply() throws Exception {
        // Initialize the database
        supplyService.save(supply);

        int databaseSizeBeforeUpdate = supplyRepository.findAll().size();

        // Update the supply
        Supply updatedSupply = supplyRepository.findById(supply.getId()).get();
        // Disconnect from session so that the updates on updatedSupply are not directly saved in db
        em.detach(updatedSupply);
        updatedSupply
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .stock(UPDATED_STOCK)
            .description(UPDATED_DESCRIPTION)
            .salePrice(UPDATED_SALE_PRICE)
            .wholesalePrice(UPDATED_WHOLESALE_PRICE)
            .purchasePrice(UPDATED_PURCHASE_PRICE)
            .marke(UPDATED_MARKE)
            .discount(UPDATED_DISCOUNT)
            .barcode(UPDATED_BARCODE)
            .urlImage(UPDATED_URL_IMAGE)
            .imageName(UPDATED_IMAGE_NAME)
            .imageCache(UPDATED_IMAGE_CACHE)
            .inventory(UPDATED_INVENTORY);

        restSupplyMockMvc.perform(put("/api/supplies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSupply)))
            .andExpect(status().isOk());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeUpdate);
        Supply testSupply = supplyList.get(supplyList.size() - 1);
        assertThat(testSupply.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupply.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSupply.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testSupply.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSupply.getSalePrice()).isEqualTo(UPDATED_SALE_PRICE);
        assertThat(testSupply.getWholesalePrice()).isEqualTo(UPDATED_WHOLESALE_PRICE);
        assertThat(testSupply.getPurchasePrice()).isEqualTo(UPDATED_PURCHASE_PRICE);
        assertThat(testSupply.getMarke()).isEqualTo(UPDATED_MARKE);
        assertThat(testSupply.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testSupply.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testSupply.getUrlImage()).isEqualTo(UPDATED_URL_IMAGE);
        assertThat(testSupply.getImageName()).isEqualTo(UPDATED_IMAGE_NAME);
        assertThat(testSupply.getImageCache()).isEqualTo(UPDATED_IMAGE_CACHE);
        assertThat(testSupply.isInventory()).isEqualTo(UPDATED_INVENTORY);
    }

    @Test
    @Transactional
    public void updateNonExistingSupply() throws Exception {
        int databaseSizeBeforeUpdate = supplyRepository.findAll().size();

        // Create the Supply

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyMockMvc.perform(put("/api/supplies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supply)))
            .andExpect(status().isBadRequest());

        // Validate the Supply in the database
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupply() throws Exception {
        // Initialize the database
        supplyService.save(supply);

        int databaseSizeBeforeDelete = supplyRepository.findAll().size();

        // Get the supply
        restSupplyMockMvc.perform(delete("/api/supplies/{id}", supply.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Supply> supplyList = supplyRepository.findAll();
        assertThat(supplyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Supply.class);
        Supply supply1 = new Supply();
        supply1.setId(1L);
        Supply supply2 = new Supply();
        supply2.setId(supply1.getId());
        assertThat(supply1).isEqualTo(supply2);
        supply2.setId(2L);
        assertThat(supply1).isNotEqualTo(supply2);
        supply1.setId(null);
        assertThat(supply1).isNotEqualTo(supply2);
    }
}
