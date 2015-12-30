package com.makesnocents.healthplanpicker.web.rest;

import com.makesnocents.healthplanpicker.Application;
import com.makesnocents.healthplanpicker.domain.Simulation;
import com.makesnocents.healthplanpicker.repository.SimulationRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SimulationResource REST controller.
 *
 * @see SimulationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SimulationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private SimulationRepository simulationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSimulationMockMvc;

    private Simulation simulation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SimulationResource simulationResource = new SimulationResource();
        ReflectionTestUtils.setField(simulationResource, "simulationRepository", simulationRepository);
        this.restSimulationMockMvc = MockMvcBuilders.standaloneSetup(simulationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        simulation = new Simulation();
        simulation.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSimulation() throws Exception {
        int databaseSizeBeforeCreate = simulationRepository.findAll().size();

        // Create the Simulation

        restSimulationMockMvc.perform(post("/api/simulations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(simulation)))
                .andExpect(status().isCreated());

        // Validate the Simulation in the database
        List<Simulation> simulations = simulationRepository.findAll();
        assertThat(simulations).hasSize(databaseSizeBeforeCreate + 1);
        Simulation testSimulation = simulations.get(simulations.size() - 1);
        assertThat(testSimulation.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = simulationRepository.findAll().size();
        // set the field null
        simulation.setName(null);

        // Create the Simulation, which fails.

        restSimulationMockMvc.perform(post("/api/simulations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(simulation)))
                .andExpect(status().isBadRequest());

        List<Simulation> simulations = simulationRepository.findAll();
        assertThat(simulations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSimulations() throws Exception {
        // Initialize the database
        simulationRepository.saveAndFlush(simulation);

        // Get all the simulations
        restSimulationMockMvc.perform(get("/api/simulations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(simulation.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSimulation() throws Exception {
        // Initialize the database
        simulationRepository.saveAndFlush(simulation);

        // Get the simulation
        restSimulationMockMvc.perform(get("/api/simulations/{id}", simulation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(simulation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSimulation() throws Exception {
        // Get the simulation
        restSimulationMockMvc.perform(get("/api/simulations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSimulation() throws Exception {
        // Initialize the database
        simulationRepository.saveAndFlush(simulation);

		int databaseSizeBeforeUpdate = simulationRepository.findAll().size();

        // Update the simulation
        simulation.setName(UPDATED_NAME);

        restSimulationMockMvc.perform(put("/api/simulations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(simulation)))
                .andExpect(status().isOk());

        // Validate the Simulation in the database
        List<Simulation> simulations = simulationRepository.findAll();
        assertThat(simulations).hasSize(databaseSizeBeforeUpdate);
        Simulation testSimulation = simulations.get(simulations.size() - 1);
        assertThat(testSimulation.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteSimulation() throws Exception {
        // Initialize the database
        simulationRepository.saveAndFlush(simulation);

		int databaseSizeBeforeDelete = simulationRepository.findAll().size();

        // Get the simulation
        restSimulationMockMvc.perform(delete("/api/simulations/{id}", simulation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Simulation> simulations = simulationRepository.findAll();
        assertThat(simulations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
