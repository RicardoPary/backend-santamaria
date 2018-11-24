package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Supply entity. This class is used in SupplyResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /supplies?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplyCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter type;

    private IntegerFilter stock;

    private StringFilter description;

    private DoubleFilter salePrice;

    private DoubleFilter wholesalePrice;

    private DoubleFilter purchasePrice;

    private StringFilter marke;

    private DoubleFilter discount;

    private StringFilter barcode;

    private StringFilter urlImage;

    private StringFilter imageName;

    private StringFilter imageCache;

    private BooleanFilter inventory;

    private LongFilter idBranch;

    private LongFilter categoryId;

    public SupplyCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public IntegerFilter getStock() {
        return stock;
    }

    public void setStock(IntegerFilter stock) {
        this.stock = stock;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public DoubleFilter getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(DoubleFilter salePrice) {
        this.salePrice = salePrice;
    }

    public DoubleFilter getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(DoubleFilter wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public DoubleFilter getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(DoubleFilter purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public StringFilter getMarke() {
        return marke;
    }

    public void setMarke(StringFilter marke) {
        this.marke = marke;
    }

    public DoubleFilter getDiscount() {
        return discount;
    }

    public void setDiscount(DoubleFilter discount) {
        this.discount = discount;
    }

    public StringFilter getBarcode() {
        return barcode;
    }

    public void setBarcode(StringFilter barcode) {
        this.barcode = barcode;
    }

    public StringFilter getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(StringFilter urlImage) {
        this.urlImage = urlImage;
    }

    public StringFilter getImageName() {
        return imageName;
    }

    public void setImageName(StringFilter imageName) {
        this.imageName = imageName;
    }

    public StringFilter getImageCache() {
        return imageCache;
    }

    public void setImageCache(StringFilter imageCache) {
        this.imageCache = imageCache;
    }

    public BooleanFilter getInventory() {
        return inventory;
    }

    public void setInventory(BooleanFilter inventory) {
        this.inventory = inventory;
    }

    public LongFilter getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(LongFilter idBranch) {
        this.idBranch = idBranch;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplyCriteria that = (SupplyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(stock, that.stock) &&
            Objects.equals(description, that.description) &&
            Objects.equals(salePrice, that.salePrice) &&
            Objects.equals(wholesalePrice, that.wholesalePrice) &&
            Objects.equals(purchasePrice, that.purchasePrice) &&
            Objects.equals(marke, that.marke) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(barcode, that.barcode) &&
            Objects.equals(urlImage, that.urlImage) &&
            Objects.equals(imageName, that.imageName) &&
            Objects.equals(imageCache, that.imageCache) &&
            Objects.equals(inventory, that.inventory) &&
            Objects.equals(idBranch, that.idBranch) &&
            Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        type,
        stock,
        description,
        salePrice,
        wholesalePrice,
        purchasePrice,
        marke,
        discount,
        barcode,
        urlImage,
        imageName,
        imageCache,
        inventory,
        idBranch,
        categoryId
        );
    }

    @Override
    public String toString() {
        return "SupplyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (stock != null ? "stock=" + stock + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (salePrice != null ? "salePrice=" + salePrice + ", " : "") +
                (wholesalePrice != null ? "wholesalePrice=" + wholesalePrice + ", " : "") +
                (purchasePrice != null ? "purchasePrice=" + purchasePrice + ", " : "") +
                (marke != null ? "marke=" + marke + ", " : "") +
                (discount != null ? "discount=" + discount + ", " : "") +
                (barcode != null ? "barcode=" + barcode + ", " : "") +
                (urlImage != null ? "urlImage=" + urlImage + ", " : "") +
                (imageName != null ? "imageName=" + imageName + ", " : "") +
                (imageCache != null ? "imageCache=" + imageCache + ", " : "") +
                (inventory != null ? "inventory=" + inventory + ", " : "") +
                (idBranch != null ? "idBranch=" + idBranch + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            "}";
    }

}
