package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MedicalConsultation.
 */
@Entity
@Table(name = "medical_consultation")
public class MedicalConsultation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "company")
    private String company;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "phone")
    private String phone;

    @Column(name = "detail")
    private String detail;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "id_provider")
    private Long idProvider;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Supply supply;

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

    public MedicalConsultation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public MedicalConsultation company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public MedicalConsultation type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public MedicalConsultation phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public MedicalConsultation detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public MedicalConsultation quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public MedicalConsultation price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getIdProvider() {
        return idProvider;
    }

    public MedicalConsultation idProvider(Long idProvider) {
        this.idProvider = idProvider;
        return this;
    }

    public void setIdProvider(Long idProvider) {
        this.idProvider = idProvider;
    }

    public Supply getSupply() {
        return supply;
    }

    public MedicalConsultation supply(Supply supply) {
        this.supply = supply;
        return this;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
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
        MedicalConsultation medicalConsultation = (MedicalConsultation) o;
        if (medicalConsultation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalConsultation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalConsultation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", company='" + getCompany() + "'" +
            ", type='" + getType() + "'" +
            ", phone='" + getPhone() + "'" +
            ", detail='" + getDetail() + "'" +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            ", idProvider=" + getIdProvider() +
            "}";
    }
}
