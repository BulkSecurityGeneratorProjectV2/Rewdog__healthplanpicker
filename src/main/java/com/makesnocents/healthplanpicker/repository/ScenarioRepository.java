package com.makesnocents.healthplanpicker.repository;

import com.makesnocents.healthplanpicker.domain.Scenario;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Scenario entity.
 */
public interface ScenarioRepository extends JpaRepository<Scenario,Long> {

}
