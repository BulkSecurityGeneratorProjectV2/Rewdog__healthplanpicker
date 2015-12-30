package com.makesnocents.healthplanpicker.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.makesnocents.healthplanpicker.domain.enumeration.ScenarioType;

import com.makesnocents.healthplanpicker.domain.enumeration.FrequencyType;
import com.makesnocents.healthplanpicker.domain.enumeration.PremiumFrequency;

/**
 * A Scenario.
 */
@Entity
@Table(name = "scenario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Scenario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ScenarioType type;

    @NotNull
    @Min(value = 0)
    @Column(name = "cost", precision=10, scale=2, nullable = false)
    private BigDecimal cost;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "frequency", nullable = false)
    private FrequencyType frequency;

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

    public ScenarioType getType() {
        return type;
    }

    public void setType(ScenarioType type) {
        this.type = type;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public FrequencyType getFrequency() {
        return frequency;
    }

    public void setFrequency(FrequencyType frequency) {
        this.frequency = frequency;
    }
    
    /**
     * Calculate the annual cost
     */
    public BigDecimal getAnnualCost()
    {
    	BigDecimal cost = getCost();
    	FrequencyType frequency = getFrequency();
    	switch (frequency) {
		case weekly:
			return cost.multiply(new BigDecimal(52));
		case biweekly:
			return cost.multiply(new BigDecimal(26));
		case monthly:
			return cost.multiply(new BigDecimal(12));
		case once:
			return cost.multiply(new BigDecimal(1));
		default:
			return cost;
		}
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Scenario scenario = (Scenario) o;
        return Objects.equals(id, scenario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Scenario{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", cost='" + cost + "'" +
            ", frequency='" + frequency + "'" +
            ", annualCost='" + getAnnualCost() + "'" +
            '}';
    }
}
