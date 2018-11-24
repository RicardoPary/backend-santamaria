package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MedicalConsultationDetails.
 */
@Entity
@Table(name = "medical_consultation_details")
public class MedicalConsultationDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "detail")
    private String detail;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "id_staff")
    private Long idStaff;

    @Column(name = "id_supply")
    private Long idSupply;

    @ManyToOne
    @JsonIgnoreProperties("")
    private MedicalConsultation medicalConsultation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MedicalConsultationDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public MedicalConsultationDetails detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public MedicalConsultationDetails quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getIdStaff() {
        return idStaff;
    }

    public MedicalConsultationDetails idStaff(Long idStaff) {
        this.idStaff = idStaff;
        return this;
    }

    public void setIdStaff(Long idStaff) {
        this.idStaff = idStaff;
    }

    public Long getIdSupply() {
        return idSupply;
    }

    public MedicalConsultationDetails idSupply(Long idSupply) {
        this.idSupply = idSupply;
        return this;
    }

    public void setIdSupply(Long idSupply) {
        this.idSupply = idSupply;
    }

    public MedicalConsultation getMedicalConsultation() {
        return medicalConsultation;
    }

    public MedicalConsultationDetails medicalConsultation(MedicalConsultation medicalConsultation) {
        this.medicalConsultation = medicalConsultation;
        return this;
    }

    public void setMedicalConsultation(MedicalConsultation medicalConsultation) {
        this.medicalConsultation = medicalConsultation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MedicalConsultationDetails medicalConsultationDetails = (MedicalConsultationDetails) o;
        if (medicalConsultationDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalConsultationDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalConsultationDetails{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", quantity=" + getQuantity() +
            ", idStaff=" + getIdStaff() +
            ", idSupply=" + getIdSupply() +
            "}";
    }
}
