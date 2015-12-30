package com.makesnocents.healthplanpicker.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.makesnocents.healthplanpicker.domain.Simulation;
import com.makesnocents.healthplanpicker.repository.SimulationRepository;
import com.makesnocents.healthplanpicker.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Simulation.
 */
@RestController
@RequestMapping("/api")
public class SimulationResource {

    private final Logger log = LoggerFactory.getLogger(SimulationResource.class);
        
    @Inject
    private SimulationRepository simulationRepository;
    
    /**
     * POST  /simulations -> Create a new simulation.
     */
    @RequestMapping(value = "/simulations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Simulation> createSimulation(@Valid @RequestBody Simulation simulation) throws URISyntaxException {
        log.debug("REST request to save Simulation : {}", simulation);
        if (simulation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("simulation", "idexists", "A new simulation cannot already have an ID")).body(null);
        }
        Simulation result = simulationRepository.save(simulation);
        return ResponseEntity.created(new URI("/api/simulations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("simulation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /simulations -> Updates an existing simulation.
     */
    @RequestMapping(value = "/simulations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Simulation> updateSimulation(@Valid @RequestBody Simulation simulation) throws URISyntaxException {
        log.debug("REST request to update Simulation : {}", simulation);
        if (simulation.getId() == null) {
            return createSimulation(simulation);
        }
        Simulation result = simulationRepository.save(simulation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("simulation", simulation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /simulations -> get all the simulations.
     */
    @RequestMapping(value = "/simulations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Simulation> getAllSimulations() {
        log.debug("REST request to get all Simulations");
        return simulationRepository.findAllWithEagerRelationships();
            }

    /**
     * GET  /simulations/:id -> get the "id" simulation.
     */
    @RequestMapping(value = "/simulations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Simulation> getSimulation(@PathVariable Long id) {
        log.debug("REST request to get Simulation : {}", id);
        Simulation simulation = simulationRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(simulation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /simulations/:id -> delete the "id" simulation.
     */
    @RequestMapping(value = "/simulations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSimulation(@PathVariable Long id) {
        log.debug("REST request to delete Simulation : {}", id);
        simulationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("simulation", id.toString())).build();
    }
}
