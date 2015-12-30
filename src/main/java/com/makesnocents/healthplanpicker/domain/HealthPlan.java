package com.makesnocents.healthplanpicker.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.makesnocents.healthplanpicker.domain.enumeration.PremiumFrequency;

import com.makesnocents.healthplanpicker.domain.enumeration.HealthPlanType;

/**
 * A HealthPlan.
 */
@Entity
@Table(name = "health_plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HealthPlan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "premium", precision=10, scale=2)
    private BigDecimal premium;

    @Enumerated(EnumType.STRING)
    @Column(name = "premium_frequency")
    private PremiumFrequency premiumFrequency;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private HealthPlanType type;

    @NotNull
    @Min(value = 0)
    @Column(name = "deductible", precision=10, scale=2, nullable = false)
    private BigDecimal deductible;

    @NotNull
    @Min(value = 0)
    @Column(name = "oop_max", precision=10, scale=2, nullable = false)
    private BigDecimal oopMax;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "coinsurance")
    private Double coinsurance;

    @Min(value = 0)
    @Column(name = "employer_hsacontribution", precision=10, scale=2)
    private BigDecimal employerHSAContribution;

    @Min(value = 0)
    @Column(name = "copay_pcp", precision=10, scale=2)
    private BigDecimal copayPCP;

    @Column(name = "copay_er", precision=10, scale=2)
    private BigDecimal copayER;

    @Min(value = 0)
    @Column(name = "copay_pharmacy", precision=10, scale=2)
    private BigDecimal copayPharmacy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public PremiumFrequency getPremiumFrequency() {
        return premiumFrequency;
    }

    public void setPremiumFrequency(PremiumFrequency premiumFrequency) {
        this.premiumFrequency = premiumFrequency;
    }

    public HealthPlanType getType() {
        return type;
    }

    public void setType(HealthPlanType type) {
        this.type = type;
    }

    public BigDecimal getDeductible() {
        return deductible;
    }

    public void setDeductible(BigDecimal deductible) {
        this.deductible = deductible;
    }

    public BigDecimal getOopMax() {
        return oopMax;
    }

    public void setOopMax(BigDecimal oopMax) {
        this.oopMax = oopMax;
    }

    public Double getCoinsurance() {
        return coinsurance;
    }

    public void setCoinsurance(Double coinsurance) {
        this.coinsurance = coinsurance;
    }

    public BigDecimal getEmployerHSAContribution() {
        return employerHSAContribution;
    }

    public void setEmployerHSAContribution(BigDecimal employerHSAContribution) {
        this.employerHSAContribution = employerHSAContribution;
    }

    public BigDecimal getCopayPCP() {
        return copayPCP;
    }

    public void setCopayPCP(BigDecimal copayPCP) {
        this.copayPCP = copayPCP;
    }

    public BigDecimal getCopayER() {
        return copayER;
    }

    public void setCopayER(BigDecimal copayER) {
        this.copayER = copayER;
    }

    public BigDecimal getCopayPharmacy() {
        return copayPharmacy;
    }

    public void setCopayPharmacy(BigDecimal copayPharmacy) {
        this.copayPharmacy = copayPharmacy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HealthPlan healthPlan = (HealthPlan) o;
        return Objects.equals(id, healthPlan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HealthPlan{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", premium='" + premium + "'" +
            ", premiumFrequency='" + premiumFrequency + "'" +
            ", type='" + type + "'" +
            ", deductible='" + deductible + "'" +
            ", oopMax='" + oopMax + "'" +
            ", coinsurance='" + coinsurance + "'" +
            ", employerHSAContribution='" + employerHSAContribution + "'" +
            ", copayPCP='" + copayPCP + "'" +
            ", copayER='" + copayER + "'" +
            ", copayPharmacy='" + copayPharmacy + "'" +
            '}';
    }
}
