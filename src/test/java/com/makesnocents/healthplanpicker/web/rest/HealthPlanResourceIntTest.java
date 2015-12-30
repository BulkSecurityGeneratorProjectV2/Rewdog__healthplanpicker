package com.makesnocents.healthplanpicker.web.rest;

import com.makesnocents.healthplanpicker.Application;
import com.makesnocents.healthplanpicker.domain.HealthPlan;
import com.makesnocents.healthplanpicker.repository.HealthPlanRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.makesnocents.healthplanpicker.domain.enumeration.PremiumFrequency;
import com.makesnocents.healthplanpicker.domain.enumeration.HealthPlanType;

/**
 * Test class for the HealthPlanResource REST controller.
 *
 * @see HealthPlanResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HealthPlanResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final BigDecimal DEFAULT_PREMIUM = new BigDecimal(1);
    private static final BigDecimal UPDATED_PREMIUM = new BigDecimal(2);


    private static final PremiumFrequency DEFAULT_PREMIUM_FREQUENCY = PremiumFrequency.weekly;
    private static final PremiumFrequency UPDATED_PREMIUM_FREQUENCY = PremiumFrequency.biweekly;


    private static final HealthPlanType DEFAULT_TYPE = HealthPlanType.cdhp;
    private static final HealthPlanType UPDATED_TYPE = HealthPlanType.ppo;

    private static final BigDecimal DEFAULT_DEDUCTIBLE = new BigDecimal(0);
    private static final BigDecimal UPDATED_DEDUCTIBLE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_OOP_MAX = new BigDecimal(0);
    private static final BigDecimal UPDATED_OOP_MAX = new BigDecimal(1);

    private static final Double DEFAULT_COINSURANCE = 0D;
    private static final Double UPDATED_COINSURANCE = 1D;

    private static final BigDecimal DEFAULT_EMPLOYER_HSACONTRIBUTION = new BigDecimal(0);
    private static final BigDecimal UPDATED_EMPLOYER_HSACONTRIBUTION = new BigDecimal(1);

    private static final BigDecimal DEFAULT_COPAY_PCP = new BigDecimal(0);
    private static final BigDecimal UPDATED_COPAY_PCP = new BigDecimal(1);

    private static final BigDecimal DEFAULT_COPAY_ER = new BigDecimal(1);
    private static final BigDecimal UPDATED_COPAY_ER = new BigDecimal(2);

    private static final BigDecimal DEFAULT_COPAY_PHARMACY = new BigDecimal(0);
    private static final BigDecimal UPDATED_COPAY_PHARMACY = new BigDecimal(1);

    @Inject
    private HealthPlanRepository healthPlanRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHealthPlanMockMvc;

    private HealthPlan healthPlan;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HealthPlanResource healthPlanResource = new HealthPlanResource();
        ReflectionTestUtils.setField(healthPlanResource, "healthPlanRepository", healthPlanRepository);
        this.restHealthPlanMockMvc = MockMvcBuilders.standaloneSetup(healthPlanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        healthPlan = new HealthPlan();
        healthPlan.setName(DEFAULT_NAME);
        healthPlan.setPremium(DEFAULT_PREMIUM);
        healthPlan.setPremiumFrequency(DEFAULT_PREMIUM_FREQUENCY);
        healthPlan.setType(DEFAULT_TYPE);
        healthPlan.setDeductible(DEFAULT_DEDUCTIBLE);
        healthPlan.setOopMax(DEFAULT_OOP_MAX);
        healthPlan.setCoinsurance(DEFAULT_COINSURANCE);
        healthPlan.setEmployerHSAContribution(DEFAULT_EMPLOYER_HSACONTRIBUTION);
        healthPlan.setCopayPCP(DEFAULT_COPAY_PCP);
        healthPlan.setCopayER(DEFAULT_COPAY_ER);
        healthPlan.setCopayPharmacy(DEFAULT_COPAY_PHARMACY);
    }

    @Test
    @Transactional
    public void createHealthPlan() throws Exception {
        int databaseSizeBeforeCreate = healthPlanRepository.findAll().size();

        // Create the HealthPlan

        restHealthPlanMockMvc.perform(post("/api/healthPlans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(healthPlan)))
                .andExpect(status().isCreated());

        // Validate the HealthPlan in the database
        List<HealthPlan> healthPlans = healthPlanRepository.findAll();
        assertThat(healthPlans).hasSize(databaseSizeBeforeCreate + 1);
        HealthPlan testHealthPlan = healthPlans.get(healthPlans.size() - 1);
        assertThat(testHealthPlan.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHealthPlan.getPremium()).isEqualTo(DEFAULT_PREMIUM);
        assertThat(testHealthPlan.getPremiumFrequency()).isEqualTo(DEFAULT_PREMIUM_FREQUENCY);
        assertThat(testHealthPlan.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testHealthPlan.getDeductible()).isEqualTo(DEFAULT_DEDUCTIBLE);
        assertThat(testHealthPlan.getOopMax()).isEqualTo(DEFAULT_OOP_MAX);
        assertThat(testHealthPlan.getCoinsurance()).isEqualTo(DEFAULT_COINSURANCE);
        assertThat(testHealthPlan.getEmployerHSAContribution()).isEqualTo(DEFAULT_EMPLOYER_HSACONTRIBUTION);
        assertThat(testHealthPlan.getCopayPCP()).isEqualTo(DEFAULT_COPAY_PCP);
        assertThat(testHealthPlan.getCopayER()).isEqualTo(DEFAULT_COPAY_ER);
        assertThat(testHealthPlan.getCopayPharmacy()).isEqualTo(DEFAULT_COPAY_PHARMACY);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthPlanRepository.findAll().size();
        // set the field null
        healthPlan.setName(null);

        // Create the HealthPlan, which fails.

        restHealthPlanMockMvc.perform(post("/api/healthPlans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(healthPlan)))
                .andExpect(status().isBadRequest());

        List<HealthPlan> healthPlans = healthPlanRepository.findAll();
        assertThat(healthPlans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthPlanRepository.findAll().size();
        // set the field null
        healthPlan.setType(null);

        // Create the HealthPlan, which fails.

        restHealthPlanMockMvc.perform(post("/api/healthPlans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(healthPlan)))
                .andExpect(status().isBadRequest());

        List<HealthPlan> healthPlans = healthPlanRepository.findAll();
        assertThat(healthPlans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeductibleIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthPlanRepository.findAll().size();
        // set the field null
        healthPlan.setDeductible(null);

        // Create the HealthPlan, which fails.

        restHealthPlanMockMvc.perform(post("/api/healthPlans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(healthPlan)))
                .andExpect(status().isBadRequest());

        List<HealthPlan> healthPlans = healthPlanRepository.findAll();
        assertThat(healthPlans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOopMaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthPlanRepository.findAll().size();
        // set the field null
        healthPlan.setOopMax(null);

        // Create the HealthPlan, which fails.

        restHealthPlanMockMvc.perform(post("/api/healthPlans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(healthPlan)))
                .andExpect(status().isBadRequest());

        List<HealthPlan> healthPlans = healthPlanRepository.findAll();
        assertThat(healthPlans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHealthPlans() throws Exception {
        // Initialize the database
        healthPlanRepository.saveAndFlush(healthPlan);

        // Get all the healthPlans
        restHealthPlanMockMvc.perform(get("/api/healthPlans?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(healthPlan.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].premium").value(hasItem(DEFAULT_PREMIUM.intValue())))
                .andExpect(jsonPath("$.[*].premiumFrequency").value(hasItem(DEFAULT_PREMIUM_FREQUENCY.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].deductible").value(hasItem(DEFAULT_DEDUCTIBLE.intValue())))
                .andExpect(jsonPath("$.[*].oopMax").value(hasItem(DEFAULT_OOP_MAX.intValue())))
                .andExpect(jsonPath("$.[*].coinsurance").value(hasItem(DEFAULT_COINSURANCE.doubleValue())))
                .andExpect(jsonPath("$.[*].employerHSAContribution").value(hasItem(DEFAULT_EMPLOYER_HSACONTRIBUTION.intValue())))
                .andExpect(jsonPath("$.[*].copayPCP").value(hasItem(DEFAULT_COPAY_PCP.intValue())))
                .andExpect(jsonPath("$.[*].copayER").value(hasItem(DEFAULT_COPAY_ER.intValue())))
                .andExpect(jsonPath("$.[*].copayPharmacy").value(hasItem(DEFAULT_COPAY_PHARMACY.intValue())));
    }

    @Test
    @Transactional
    public void getHealthPlan() throws Exception {
        // Initialize the database
        healthPlanRepository.saveAndFlush(healthPlan);

        // Get the healthPlan
        restHealthPlanMockMvc.perform(get("/api/healthPlans/{id}", healthPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(healthPlan.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.premium").value(DEFAULT_PREMIUM.intValue()))
            .andExpect(jsonPath("$.premiumFrequency").value(DEFAULT_PREMIUM_FREQUENCY.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.deductible").value(DEFAULT_DEDUCTIBLE.intValue()))
            .andExpect(jsonPath("$.oopMax").value(DEFAULT_OOP_MAX.intValue()))
            .andExpect(jsonPath("$.coinsurance").value(DEFAULT_COINSURANCE.doubleValue()))
            .andExpect(jsonPath("$.employerHSAContribution").value(DEFAULT_EMPLOYER_HSACONTRIBUTION.intValue()))
            .andExpect(jsonPath("$.copayPCP").value(DEFAULT_COPAY_PCP.intValue()))
            .andExpect(jsonPath("$.copayER").value(DEFAULT_COPAY_ER.intValue()))
            .andExpect(jsonPath("$.copayPharmacy").value(DEFAULT_COPAY_PHARMACY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHealthPlan() throws Exception {
        // Get the healthPlan
        restHealthPlanMockMvc.perform(get("/api/healthPlans/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHealthPlan() throws Exception {
        // Initialize the database
        healthPlanRepository.saveAndFlush(healthPlan);

		int databaseSizeBeforeUpdate = healthPlanRepository.findAll().size();

        // Update the healthPlan
        healthPlan.setName(UPDATED_NAME);
        healthPlan.setPremium(UPDATED_PREMIUM);
        healthPlan.setPremiumFrequency(UPDATED_PREMIUM_FREQUENCY);
        healthPlan.setType(UPDATED_TYPE);
        healthPlan.setDeductible(UPDATED_DEDUCTIBLE);
        healthPlan.setOopMax(UPDATED_OOP_MAX);
        healthPlan.setCoinsurance(UPDATED_COINSURANCE);
        healthPlan.setEmployerHSAContribution(UPDATED_EMPLOYER_HSACONTRIBUTION);
        healthPlan.setCopayPCP(UPDATED_COPAY_PCP);
        healthPlan.setCopayER(UPDATED_COPAY_ER);
        healthPlan.setCopayPharmacy(UPDATED_COPAY_PHARMACY);

        restHealthPlanMockMvc.perform(put("/api/healthPlans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(healthPlan)))
                .andExpect(status().isOk());

        // Validate the HealthPlan in the database
        List<HealthPlan> healthPlans = healthPlanRepository.findAll();
        assertThat(healthPlans).hasSize(databaseSizeBeforeUpdate);
        HealthPlan testHealthPlan = healthPlans.get(healthPlans.size() - 1);
        assertThat(testHealthPlan.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHealthPlan.getPremium()).isEqualTo(UPDATED_PREMIUM);
        assertThat(testHealthPlan.getPremiumFrequency()).isEqualTo(UPDATED_PREMIUM_FREQUENCY);
        assertThat(testHealthPlan.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testHealthPlan.getDeductible()).isEqualTo(UPDATED_DEDUCTIBLE);
        assertThat(testHealthPlan.getOopMax()).isEqualTo(UPDATED_OOP_MAX);
        assertThat(testHealthPlan.getCoinsurance()).isEqualTo(UPDATED_COINSURANCE);
        assertThat(testHealthPlan.getEmployerHSAContribution()).isEqualTo(UPDATED_EMPLOYER_HSACONTRIBUTION);
        assertThat(testHealthPlan.getCopayPCP()).isEqualTo(UPDATED_COPAY_PCP);
        assertThat(testHealthPlan.getCopayER()).isEqualTo(UPDATED_COPAY_ER);
        assertThat(testHealthPlan.getCopayPharmacy()).isEqualTo(UPDATED_COPAY_PHARMACY);
    }

    @Test
    @Transactional
    public void deleteHealthPlan() throws Exception {
        // Initialize the database
        healthPlanRepository.saveAndFlush(healthPlan);

		int databaseSizeBeforeDelete = healthPlanRepository.findAll().size();

        // Get the healthPlan
        restHealthPlanMockMvc.perform(delete("/api/healthPlans/{id}", healthPlan.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HealthPlan> healthPlans = healthPlanRepository.findAll();
        assertThat(healthPlans).hasSize(databaseSizeBeforeDelete - 1);
    }
}
