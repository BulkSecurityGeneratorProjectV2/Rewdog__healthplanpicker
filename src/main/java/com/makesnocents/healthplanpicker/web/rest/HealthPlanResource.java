package com.makesnocents.healthplanpicker.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.makesnocents.healthplanpicker.domain.HealthPlan;
import com.makesnocents.healthplanpicker.repository.HealthPlanRepository;
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
 * REST controller for managing HealthPlan.
 */
@RestController
@RequestMapping("/api")
public class HealthPlanResource {

    private final Logger log = LoggerFactory.getLogger(HealthPlanResource.class);
        
    @Inject
    private HealthPlanRepository healthPlanRepository;
    
    /**
     * POST  /healthPlans -> Create a new healthPlan.
     */
    @RequestMapping(value = "/healthPlans",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HealthPlan> createHealthPlan(@Valid @RequestBody HealthPlan healthPlan) throws URISyntaxException {
        log.debug("REST request to save HealthPlan : {}", healthPlan);
        if (healthPlan.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("healthPlan", "idexists", "A new healthPlan cannot already have an ID")).body(null);
        }
        HealthPlan result = healthPlanRepository.save(healthPlan);
        return ResponseEntity.created(new URI("/api/healthPlans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("healthPlan", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /healthPlans -> Updates an existing healthPlan.
     */
    @RequestMapping(value = "/healthPlans",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HealthPlan> updateHealthPlan(@Valid @RequestBody HealthPlan healthPlan) throws URISyntaxException {
        log.debug("REST request to update HealthPlan : {}", healthPlan);
        if (healthPlan.getId() == null) {
            return createHealthPlan(healthPlan);
        }
        HealthPlan result = healthPlanRepository.save(healthPlan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("healthPlan", healthPlan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /healthPlans -> get all the healthPlans.
     */
    @RequestMapping(value = "/healthPlans",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HealthPlan> getAllHealthPlans() {
        log.debug("REST request to get all HealthPlans");
        return healthPlanRepository.findAll();
            }

    /**
     * GET  /healthPlans/:id -> get the "id" healthPlan.
     */
    @RequestMapping(value = "/healthPlans/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HealthPlan> getHealthPlan(@PathVariable Long id) {
        log.debug("REST request to get HealthPlan : {}", id);
        HealthPlan healthPlan = healthPlanRepository.findOne(id);
        return Optional.ofNullable(healthPlan)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /healthPlans/:id -> delete the "id" healthPlan.
     */
    @RequestMapping(value = "/healthPlans/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHealthPlan(@PathVariable Long id) {
        log.debug("REST request to delete HealthPlan : {}", id);
        healthPlanRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("healthPlan", id.toString())).build();
    }
}
