package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Contract.
 */
@Entity
@Table(name = "contract")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "salary")
    private Double salary;

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

    public String getType() {
        return type;
    }

    public Contract type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getSalary() {
        return salary;
    }

    public Contract salary(Double salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDate getDate() {
        return date;
    }

    public Contract date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getIdStaff() {
        return idStaff;
    }

    public Contract idStaff(Long idStaff) {
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
        Contract contract = (Contract) o;
        if (contract.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contract.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contract{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", salary=" + getSalary() +
            ", date='" + getDate() + "'" +
            ", idStaff=" + getIdStaff() +
            "}";
    }
}
