package com.makesnocents.healthplanpicker.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Simulation.
 */
@Entity
@Table(name = "simulation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Simulation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "usersimulation_id")
    private User usersimulation;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "simulation_healthplansimulation",
               joinColumns = @JoinColumn(name="simulations_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="healthplansimulations_id", referencedColumnName="ID"))
    private Set<HealthPlan> healthplansimulations = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "simulation_scenariosimulation",
               joinColumns = @JoinColumn(name="simulations_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="scenariosimulations_id", referencedColumnName="ID"))
    private Set<Scenario> scenariosimulations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUsersimulation() {
        return usersimulation;
    }

    public void setUsersimulation(User user) {
        this.usersimulation = user;
    }

    public Set<HealthPlan> getHealthplansimulations() {
        return healthplansimulations;
    }

    public void setHealthplansimulations(Set<HealthPlan> healthPlans) {
        this.healthplansimulations = healthPlans;
    }

    public Set<Scenario> getScenariosimulations() {
        return scenariosimulations;
    }

    public void setScenariosimulations(Set<Scenario> scenarios) {
        this.scenariosimulations = scenarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Simulation simulation = (Simulation) o;
        return Objects.equals(id, simulation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Simulation{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
