package com.makesnocents.healthplanpicker.repository;

import com.makesnocents.healthplanpicker.domain.Simulation;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Simulation entity.
 */
public interface SimulationRepository extends JpaRepository<Simulation,Long> {

    @Query("select simulation from Simulation simulation where simulation.usersimulation.login = ?#{principal.username}")
    List<Simulation> findByUsersimulationIsCurrentUser();

    @Query("select distinct simulation from Simulation simulation left join fetch simulation.healthplansimulations left join fetch simulation.scenariosimulations")
    List<Simulation> findAllWithEagerRelationships();

    @Query("select simulation from Simulation simulation left join fetch simulation.healthplansimulations left join fetch simulation.scenariosimulations where simulation.id =:id")
    Simulation findOneWithEagerRelationships(@Param("id") Long id);

}
