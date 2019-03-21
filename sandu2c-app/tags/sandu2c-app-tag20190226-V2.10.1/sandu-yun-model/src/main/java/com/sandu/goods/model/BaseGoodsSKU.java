package com.sandu.goods.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BaseGoodsSKU implements Serializable
{
    private Integer id;

    private Integer productId;

    private Integer spuId;

    private String attributeIds;

    private Integer inventory;

    private BigDecimal price;

    private Integer listPicId;

    private Integer specificationPicId;

    private Integer mainPicId;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public String getAttributeIds() {
        return attributeIds;
    }

    public void setAttributeIds(String attributeIds) {
        this.attributeIds = attributeIds == null ? null : attributeIds.trim();
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getListPicId() {
        return listPicId;
    }

    public void setListPicId(Integer listPicId) {
        this.listPicId = listPicId;
    }

    public Integer getSpecificationPicId() {
        return specificationPicId;
    }

    public void setSpecificationPicId(Integer specificationPicId) {
        this.specificationPicId = specificationPicId;
    }

    public Integer getMainPicId() {
        return mainPicId;
    }

    public void setMainPicId(Integer mainPicId) {
        this.mainPicId = mainPicId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}