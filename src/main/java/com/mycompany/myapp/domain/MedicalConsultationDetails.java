package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MedicalConsultationDetails.
 */
@Entity
@Table(name = "medical_consultation_detail")
public class MedicalConsultationDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "observation")
    private String observation;

    @Column(name = "detail")
    private String detail;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity", precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "id_product")
    private Long idProduct;

    @Column(name = "initial_current_stock")
    private Integer initialCurrentStock;

    @Column(name = "final_current_stock")
    private Integer finalCurrentStock;

    @Column(name = "type_method")
    private String typeMethod;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "id_invoice")
    private Long idInvoice;

    @Column(name = "id_branch")
    private Long idBranch;

    @Column(name = "id_box")
    private Long idBox;

    @Column(name = "jhi_date")
    private LocalDate date;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }

    public MedicalConsultationDetails observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public MedicalConsultationDetails quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MedicalConsultationDetails price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public MedicalConsultationDetails subtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public MedicalConsultationDetails idProduct(Long idProduct) {
        this.idProduct = idProduct;
        return this;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getInitialCurrentStock() {
        return initialCurrentStock;
    }

    public MedicalConsultationDetails initialCurrentStock(Integer initialCurrentStock) {
        this.initialCurrentStock = initialCurrentStock;
        return this;
    }

    public void setInitialCurrentStock(Integer initialCurrentStock) {
        this.initialCurrentStock = initialCurrentStock;
    }

    public Integer getFinalCurrentStock() {
        return finalCurrentStock;
    }

    public MedicalConsultationDetails finalCurrentStock(Integer finalCurrentStock) {
        this.finalCurrentStock = finalCurrentStock;
        return this;
    }

    public void setFinalCurrentStock(Integer finalCurrentStock) {
        this.finalCurrentStock = finalCurrentStock;
    }

    public String getTypeMethod() {
        return typeMethod;
    }

    public MedicalConsultationDetails typeMethod(String typeMethod) {
        this.typeMethod = typeMethod;
        return this;
    }

    public void setTypeMethod(String typeMethod) {
        this.typeMethod = typeMethod;
    }

    public Double getDiscount() {
        return discount;
    }

    public MedicalConsultationDetails discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Long getIdInvoice() {
        return idInvoice;
    }

    public MedicalConsultationDetails idInvoice(Long idInvoice) {
        this.idInvoice = idInvoice;
        return this;
    }

    public void setIdInvoice(Long idInvoice) {
        this.idInvoice = idInvoice;
    }

    public Long getIdBranch() {
        return idBranch;
    }

    public MedicalConsultationDetails idBranch(Long idBranch) {
        this.idBranch = idBranch;
        return this;
    }

    public void setIdBranch(Long idBranch) {
        this.idBranch = idBranch;
    }

    public Long getIdBox() {
        return idBox;
    }

    public MedicalConsultationDetails idBox(Long idBox) {
        this.idBox = idBox;
        return this;
    }

    public void setIdBox(Long idBox) {
        this.idBox = idBox;
    }

    public LocalDate getDate() {
        return date;
    }

    public MedicalConsultationDetails date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
            ", observation='" + getObservation() + "'" +
            ", detail='" + getDetail() + "'" +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            ", subtotal=" + getSubtotal() +
            ", idProduct=" + getIdProduct() +
            ", initialCurrentStock=" + getInitialCurrentStock() +
            ", finalCurrentStock=" + getFinalCurrentStock() +
            ", typeMethod='" + getTypeMethod() + "'" +
            ", discount=" + getDiscount() +
            ", idInvoice=" + getIdInvoice() +
            ", idBranch=" + getIdBranch() +
            ", idBox=" + getIdBox() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
