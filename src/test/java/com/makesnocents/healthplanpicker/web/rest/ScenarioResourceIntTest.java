package com.makesnocents.healthplanpicker.web.rest;

import com.makesnocents.healthplanpicker.Application;
import com.makesnocents.healthplanpicker.domain.Scenario;
import com.makesnocents.healthplanpicker.repository.ScenarioRepository;

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

import com.makesnocents.healthplanpicker.domain.enumeration.ScenarioType;
import com.makesnocents.healthplanpicker.domain.enumeration.FrequencyType;

/**
 * Test class for the ScenarioResource REST controller.
 *
 * @see ScenarioResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ScenarioResourceIntTest {

    private static final String DEFAULT_NAME = "A";
    private static final String UPDATED_NAME = "B";


    private static final ScenarioType DEFAULT_TYPE = ScenarioType.PCP;
    private static final ScenarioType UPDATED_TYPE = ScenarioType.Pharmacy;

    private static final BigDecimal DEFAULT_COST = new BigDecimal(0);
    private static final BigDecimal UPDATED_COST = new BigDecimal(1);


    private static final FrequencyType DEFAULT_FREQUENCY = FrequencyType.once;
    private static final FrequencyType UPDATED_FREQUENCY = FrequencyType.weekly;

    @Inject
    private ScenarioRepository scenarioRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restScenarioMockMvc;

    private Scenario scenario;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ScenarioResource scenarioResource = new ScenarioResource();
        ReflectionTestUtils.setField(scenarioResource, "scenarioRepository", scenarioRepository);
        this.restScenarioMockMvc = MockMvcBuilders.standaloneSetup(scenarioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        scenario = new Scenario();
        scenario.setName(DEFAULT_NAME);
        scenario.setType(DEFAULT_TYPE);
        scenario.setCost(DEFAULT_COST);
        scenario.setFrequency(DEFAULT_FREQUENCY);
    }

    @Test
    @Transactional
    public void createScenario() throws Exception {
        int databaseSizeBeforeCreate = scenarioRepository.findAll().size();

        // Create the Scenario

        restScenarioMockMvc.perform(post("/api/scenarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scenario)))
                .andExpect(status().isCreated());

        // Validate the Scenario in the database
        List<Scenario> scenarios = scenarioRepository.findAll();
        assertThat(scenarios).hasSize(databaseSizeBeforeCreate + 1);
        Scenario testScenario = scenarios.get(scenarios.size() - 1);
        assertThat(testScenario.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testScenario.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testScenario.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testScenario.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = scenarioRepository.findAll().size();
        // set the field null
        scenario.setName(null);

        // Create the Scenario, which fails.

        restScenarioMockMvc.perform(post("/api/scenarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scenario)))
                .andExpect(status().isBadRequest());

        List<Scenario> scenarios = scenarioRepository.findAll();
        assertThat(scenarios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = scenarioRepository.findAll().size();
        // set the field null
        scenario.setType(null);

        // Create the Scenario, which fails.

        restScenarioMockMvc.perform(post("/api/scenarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scenario)))
                .andExpect(status().isBadRequest());

        List<Scenario> scenarios = scenarioRepository.findAll();
        assertThat(scenarios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = scenarioRepository.findAll().size();
        // set the field null
        scenario.setCost(null);

        // Create the Scenario, which fails.

        restScenarioMockMvc.perform(post("/api/scenarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scenario)))
                .andExpect(status().isBadRequest());

        List<Scenario> scenarios = scenarioRepository.findAll();
        assertThat(scenarios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = scenarioRepository.findAll().size();
        // set the field null
        scenario.setFrequency(null);

        // Create the Scenario, which fails.

        restScenarioMockMvc.perform(post("/api/scenarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scenario)))
                .andExpect(status().isBadRequest());

        List<Scenario> scenarios = scenarioRepository.findAll();
        assertThat(scenarios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScenarios() throws Exception {
        // Initialize the database
        scenarioRepository.saveAndFlush(scenario);

        // Get all the scenarios
        restScenarioMockMvc.perform(get("/api/scenarios?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(scenario.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
                .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())));
    }

    @Test
    @Transactional
    public void getScenario() throws Exception {
        // Initialize the database
        scenarioRepository.saveAndFlush(scenario);

        // Get the scenario
        restScenarioMockMvc.perform(get("/api/scenarios/{id}", scenario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(scenario.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingScenario() throws Exception {
        // Get the scenario
        restScenarioMockMvc.perform(get("/api/scenarios/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScenario() throws Exception {
        // Initialize the database
        scenarioRepository.saveAndFlush(scenario);

		int databaseSizeBeforeUpdate = scenarioRepository.findAll().size();

        // Update the scenario
        scenario.setName(UPDATED_NAME);
        scenario.setType(UPDATED_TYPE);
        scenario.setCost(UPDATED_COST);
        scenario.setFrequency(UPDATED_FREQUENCY);

        restScenarioMockMvc.perform(put("/api/scenarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scenario)))
                .andExpect(status().isOk());

        // Validate the Scenario in the database
        List<Scenario> scenarios = scenarioRepository.findAll();
        assertThat(scenarios).hasSize(databaseSizeBeforeUpdate);
        Scenario testScenario = scenarios.get(scenarios.size() - 1);
        assertThat(testScenario.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScenario.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testScenario.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testScenario.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    public void deleteScenario() throws Exception {
        // Initialize the database
        scenarioRepository.saveAndFlush(scenario);

		int databaseSizeBeforeDelete = scenarioRepository.findAll().size();

        // Get the scenario
        restScenarioMockMvc.perform(delete("/api/scenarios/{id}", scenario.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Scenario> scenarios = scenarioRepository.findAll();
        assertThat(scenarios).hasSize(databaseSizeBeforeDelete - 1);
    }
}
