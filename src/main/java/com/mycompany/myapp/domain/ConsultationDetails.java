package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ConsultationDetails.
 */
@Entity
@Table(name = "consultation_details")
public class ConsultationDetails implements Serializable {

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
    private Consultation consultation;

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

    public ConsultationDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public ConsultationDetails detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ConsultationDetails quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getIdStaff() {
        return idStaff;
    }

    public ConsultationDetails idStaff(Long idStaff) {
        this.idStaff = idStaff;
        return this;
    }

    public void setIdStaff(Long idStaff) {
        this.idStaff = idStaff;
    }

    public Long getIdSupply() {
        return idSupply;
    }

    public ConsultationDetails idSupply(Long idSupply) {
        this.idSupply = idSupply;
        return this;
    }

    public void setIdSupply(Long idSupply) {
        this.idSupply = idSupply;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public ConsultationDetails consultation(Consultation consultation) {
        this.consultation = consultation;
        return this;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
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
        ConsultationDetails consultationDetails = (ConsultationDetails) o;
        if (consultationDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultationDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultationDetails{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", quantity=" + getQuantity() +
            ", idStaff=" + getIdStaff() +
            ", idSupply=" + getIdSupply() +
            "}";
    }
}
