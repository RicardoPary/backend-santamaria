package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Supply.
 */
@Entity
@Table(name = "supply")
public class Supply implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "description")
    private String description;

    @Column(name = "sale_price")
    private Double salePrice;

    @Column(name = "wholesale_price")
    private Double wholesalePrice;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "marke")
    private String marke;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "url_image")
    private String urlImage;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_cache")
    private String imageCache;

    @Column(name = "inventory")
    private Boolean inventory;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Category category;

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

    public Supply name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public Supply type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStock() {
        return stock;
    }

    public Supply stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public Supply description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public Supply salePrice(Double salePrice) {
        this.salePrice = salePrice;
        return this;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    public Supply wholesalePrice(Double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
        return this;
    }

    public void setWholesalePrice(Double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public Supply purchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
        return this;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getMarke() {
        return marke;
    }

    public Supply marke(String marke) {
        this.marke = marke;
        return this;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public Double getDiscount() {
        return discount;
    }

    public Supply discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getBarcode() {
        return barcode;
    }

    public Supply barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public Supply urlImage(String urlImage) {
        this.urlImage = urlImage;
        return this;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getImageName() {
        return imageName;
    }

    public Supply imageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageCache() {
        return imageCache;
    }

    public Supply imageCache(String imageCache) {
        this.imageCache = imageCache;
        return this;
    }

    public void setImageCache(String imageCache) {
        this.imageCache = imageCache;
    }

    public Boolean isInventory() {
        return inventory;
    }

    public Supply inventory(Boolean inventory) {
        this.inventory = inventory;
        return this;
    }

    public void setInventory(Boolean inventory) {
        this.inventory = inventory;
    }

    public Category getCategory() {
        return category;
    }

    public Supply category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        Supply supply = (Supply) o;
        if (supply.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supply.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Supply{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", stock=" + getStock() +
            ", description='" + getDescription() + "'" +
            ", salePrice=" + getSalePrice() +
            ", wholesalePrice=" + getWholesalePrice() +
            ", purchasePrice=" + getPurchasePrice() +
            ", marke='" + getMarke() + "'" +
            ", discount=" + getDiscount() +
            ", barcode='" + getBarcode() + "'" +
            ", urlImage='" + getUrlImage() + "'" +
            ", imageName='" + getImageName() + "'" +
            ", imageCache='" + getImageCache() + "'" +
            ", inventory='" + isInventory() + "'" +
            "}";
    }
}
