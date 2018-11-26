package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Consultation.
 */
@Entity
@Table(name = "consultation")
public class Consultation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "diagnosis")
    private String Diagnosis;

    @Column(name = "detail")
    private String detail;

    @Column(name = "jhi_date")
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Staff staff;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TypeAttention typeAttention;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return Diagnosis;
    }

    public Consultation Diagnosis(String Diagnosis) {
        this.Diagnosis = Diagnosis;
        return this;
    }

    public void setDiagnosis(String Diagnosis) {
        this.Diagnosis = Diagnosis;
    }

    public String getDetail() {
        return detail;
    }

    public Consultation detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDate getDate() {
        return date;
    }

    public Consultation date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public Consultation patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Staff getStaff() {
        return staff;
    }

    public Consultation staff(Staff staff) {
        this.staff = staff;
        return this;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public TypeAttention getTypeAttention() {
        return typeAttention;
    }

    public Consultation typeAttention(TypeAttention typeAttention) {
        this.typeAttention = typeAttention;
        return this;
    }

    public void setTypeAttention(TypeAttention typeAttention) {
        this.typeAttention = typeAttention;
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
        Consultation consultation = (Consultation) o;
        if (consultation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Consultation{" +
            "id=" + getId() +
            ", Diagnosis='" + getDiagnosis() + "'" +
            ", detail='" + getDetail() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
