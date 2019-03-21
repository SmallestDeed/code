package com.nork.onekeydesign.model;

import com.nork.product.model.SplitTextureDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 基础产品表 橱柜 bean
 */
public class BaseProductCountertops implements Serializable{
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Long productId;

    /**
     * 材质ID
     */
    private Long textureId;

    /**
     * 材质名
     */
    private String textureName;

    /**
     * 截面数据
     */
    private String crossSectionData;

    /**
     * 0、啥都不是;1、靠墙;2、外
     */
    private Short dataType;

    /**
     * 
     */
    private Short isDeleted;

    /**
     * 
     */
    private String creator;

    /**
     * 
     */
    private Date gmtCreate;

    /**
     * 
     */
    private String modifier;

    /**
     * 
     */
    private Date gmtModified;

    /**
     * 
     */
    private String remark;


    /** 判断是否是可拆分材质产品:0:普通产品;1:可拆分材质产品(橱柜) */
    private Integer isSplit = new Integer(0);
    /** 可拆分材质信息 */
    private List<SplitTextureDTO> splitTexturesChoose;


    public Integer getIsSplit() {
        return isSplit;
    }

    public void setIsSplit(Integer isSplit) {
        this.isSplit = isSplit;
    }

    public List<SplitTextureDTO> getSplitTexturesChoose() {
        return splitTexturesChoose;
    }

    public void setSplitTexturesChoose(List<SplitTextureDTO> splitTexturesChoose) {
        this.splitTexturesChoose = splitTexturesChoose;
    }

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return product_id 
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 
     * @param productId 
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 材质ID
     * @return texture_id 材质ID
     */
    public Long getTextureId() {
        return textureId;
    }

    /**
     * 材质ID
     * @param textureId 材质ID
     */
    public void setTextureId(Long textureId) {
        this.textureId = textureId;
    }

    /**
     * 材质名
     * @return texture_name 材质名
     */
    public String getTextureName() {
        return textureName;
    }

    /**
     * 材质名
     * @param textureName 材质名
     */
    public void setTextureName(String textureName) {
        this.textureName = textureName == null ? null : textureName.trim();
    }

    /**
     * 截面数据
     * @return cross_section_data 截面数据
     */
    public String getCrossSectionData() {
        return crossSectionData;
    }

    /**
     * 截面数据
     * @param crossSectionData 截面数据
     */
    public void setCrossSectionData(String crossSectionData) {
        this.crossSectionData = crossSectionData == null ? null : crossSectionData.trim();
    }

    /**
     * 0、啥都不是;1、靠墙;2、外
     * @return data_type 0、啥都不是;1、靠墙;2、外
     */
    public Short getDataType() {
        return dataType;
    }

    /**
     * 0、啥都不是;1、靠墙;2、外
     * @param dataType 0、啥都不是;1、靠墙;2、外
     */
    public void setDataType(Short dataType) {
        this.dataType = dataType;
    }

    /**
     * 
     * @return is_deleted 
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * 
     * @param isDeleted 
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 
     * @return creator 
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 
     * @param creator 
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 
     * @return gmt_create 
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 
     * @param gmtCreate 
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 
     * @return modifier 
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 
     * @param modifier 
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 
     * @return gmt_modified 
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 
     * @param gmtModified 
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 
     * @return remark 
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 
     * @param remark 
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}