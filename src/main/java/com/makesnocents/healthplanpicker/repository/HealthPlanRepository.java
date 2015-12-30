package com.makesnocents.healthplanpicker.repository;

import com.makesnocents.healthplanpicker.domain.HealthPlan;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HealthPlan entity.
 */
public interface HealthPlanRepository extends JpaRepository<HealthPlan,Long> {

    @Query("select healthPlan from HealthPlan healthPlan where healthPlan.user.login = ?#{principal.username}")
    List<HealthPlan> findByUserIsCurrentUser();

}
