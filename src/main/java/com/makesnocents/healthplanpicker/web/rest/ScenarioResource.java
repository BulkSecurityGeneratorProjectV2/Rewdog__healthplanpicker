package com.makesnocents.healthplanpicker.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.makesnocents.healthplanpicker.domain.Scenario;
import com.makesnocents.healthplanpicker.repository.ScenarioRepository;
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
 * REST controller for managing Scenario.
 */
@RestController
@RequestMapping("/api")
public class ScenarioResource {

    private final Logger log = LoggerFactory.getLogger(ScenarioResource.class);
        
    @Inject
    private ScenarioRepository scenarioRepository;
    
    /**
     * POST  /scenarios -> Create a new scenario.
     */
    @RequestMapping(value = "/scenarios",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Scenario> createScenario(@Valid @RequestBody Scenario scenario) throws URISyntaxException {
        log.debug("REST request to save Scenario : {}", scenario);
        if (scenario.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("scenario", "idexists", "A new scenario cannot already have an ID")).body(null);
        }
        Scenario result = scenarioRepository.save(scenario);
        return ResponseEntity.created(new URI("/api/scenarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("scenario", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /scenarios -> Updates an existing scenario.
     */
    @RequestMapping(value = "/scenarios",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Scenario> updateScenario(@Valid @RequestBody Scenario scenario) throws URISyntaxException {
        log.debug("REST request to update Scenario : {}", scenario);
        if (scenario.getId() == null) {
            return createScenario(scenario);
        }
        Scenario result = scenarioRepository.save(scenario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("scenario", scenario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /scenarios -> get all the scenarios.
     */
    @RequestMapping(value = "/scenarios",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Scenario> getAllScenarios() {
        log.debug("REST request to get all Scenarios");
        return scenarioRepository.findAll();
            }

    /**
     * GET  /scenarios/:id -> get the "id" scenario.
     */
    @RequestMapping(value = "/scenarios/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Scenario> getScenario(@PathVariable Long id) {
        log.debug("REST request to get Scenario : {}", id);
        Scenario scenario = scenarioRepository.findOne(id);
        return Optional.ofNullable(scenario)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /scenarios/:id -> delete the "id" scenario.
     */
    @RequestMapping(value = "/scenarios/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteScenario(@PathVariable Long id) {
        log.debug("REST request to delete Scenario : {}", id);
        scenarioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("scenario", id.toString())).build();
    }
}
