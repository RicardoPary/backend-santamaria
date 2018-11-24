package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MedicalHistory.
 */
@Entity
@Table(name = "medical_history")
public class MedicalHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "id_staff")
    private Long idStaff;

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

    public MedicalHistory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public MedicalHistory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public MedicalHistory type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public MedicalHistory date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getIdStaff() {
        return idStaff;
    }

    public MedicalHistory idStaff(Long idStaff) {
        this.idStaff = idStaff;
        return this;
    }

    public void setIdStaff(Long idStaff) {
        this.idStaff = idStaff;
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
        MedicalHistory medicalHistory = (MedicalHistory) o;
        if (medicalHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalHistory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", date='" + getDate() + "'" +
            ", idStaff=" + getIdStaff() +
            "}";
    }
}
