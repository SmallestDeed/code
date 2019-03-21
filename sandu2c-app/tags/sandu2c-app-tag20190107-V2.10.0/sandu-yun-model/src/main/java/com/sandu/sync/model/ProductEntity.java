package com.sandu.sync.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/20.
 */
@XmlRootElement(name = "ProductEntity")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductEntity {

    private Integer id;
    private String sysCode;
    private String creator;
    private Date gmtCreate;
    private String modifier;
    private Date gmtModified;
    private Integer isDeleted;
    private String productCode;
    private String productName;
    private String productDesc;
    private String decorationId;
    private Integer brandId;
    private Integer proStyleId;
    private Integer colorId;
    private String productSpec;
    private String productLength;
    private String productWidth;
    private String productHeight;
    private Double salePrice;
    private Integer picId;
    private Integer spaceComonId;
    private String materialPicIds;
    private String houseTypeValues;
    private String productTypeValue;
    private Integer modelId;
    private String u3dModelId;
    private String mergeProductIds;
    private Date dateAtt1;
    private Date dateAtt2;
    private Integer productSmallTypeValue;
    private Integer putawayState;
    private Integer numAtt3;
    private Integer numAtt4;
    private String remark;
    private Integer ipadU3dModelId;
    private Integer iosU3dModelId;
    private Integer androidU3dModelId;
    private Integer macBookU3dModelId;
    private Integer windowsU3dModelId;
    private Integer designerId;
    private Integer modelingId;
    private Integer renderingId;
    private String originalProductCode;
    private String productSmallTypeMark;
    private String productTypeMark;
    private String productShortCode;
    private String bmIds;
    private Integer codeNumber;
    private Integer parentId;
    private Integer designTempletId;
    private Integer materialFileId;
    private String material3dPicIds;
    private String picIds;
    private Integer productBatchType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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
        this.modifier = modifier;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getDecorationId() {
        return decorationId;
    }

    public void setDecorationId(String decorationId) {
        this.decorationId = decorationId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getProStyleId() {
        return proStyleId;
    }

    public void setProStyleId(Integer proStyleId) {
        this.proStyleId = proStyleId;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getProductLength() {
        return productLength;
    }

    public void setProductLength(String productLength) {
        this.productLength = productLength;
    }

    public String getProductWidth() {
        return productWidth;
    }

    public void setProductWidth(String productWidth) {
        this.productWidth = productWidth;
    }

    public String getProductHeight() {
        return productHeight;
    }

    public void setProductHeight(String productHeight) {
        this.productHeight = productHeight;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getSpaceComonId() {
        return spaceComonId;
    }

    public void setSpaceComonId(Integer spaceComonId) {
        this.spaceComonId = spaceComonId;
    }

    public String getMaterialPicIds() {
        return materialPicIds;
    }

    public void setMaterialPicIds(String materialPicIds) {
        this.materialPicIds = materialPicIds;
    }

    public String getHouseTypeValues() {
        return houseTypeValues;
    }

    public void setHouseTypeValues(String houseTypeValues) {
        this.houseTypeValues = houseTypeValues;
    }

    public String getProductTypeValue() {
        return productTypeValue;
    }

    public void setProductTypeValue(String productTypeValue) {
        this.productTypeValue = productTypeValue;
    }

    public String getU3dModelId() {
        return u3dModelId;
    }

    public void setU3dModelId(String u3dModelId) {
        this.u3dModelId = u3dModelId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getMergeProductIds() {
        return mergeProductIds;
    }

    public void setMergeProductIds(String mergeProductIds) {
        this.mergeProductIds = mergeProductIds;
    }

    public Date getDateAtt1() {
        return dateAtt1;
    }

    public void setDateAtt1(Date dateAtt1) {
        this.dateAtt1 = dateAtt1;
    }

    public Date getDateAtt2() {
        return dateAtt2;
    }

    public void setDateAtt2(Date dateAtt2) {
        this.dateAtt2 = dateAtt2;
    }

    public Integer getProductSmallTypeValue() {
        return productSmallTypeValue;
    }

    public void setProductSmallTypeValue(Integer productSmallTypeValue) {
        this.productSmallTypeValue = productSmallTypeValue;
    }

    public Integer getPutawayState() {
        return putawayState;
    }

    public void setPutawayState(Integer putawayState) {
        this.putawayState = putawayState;
    }

    public Integer getNumAtt3() {
        return numAtt3;
    }

    public void setNumAtt3(Integer numAtt3) {
        this.numAtt3 = numAtt3;
    }

    public Integer getNumAtt4() {
        return numAtt4;
    }

    public void setNumAtt4(Integer numAtt4) {
        this.numAtt4 = numAtt4;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIpadU3dModelId() {
        return ipadU3dModelId;
    }

    public void setIpadU3dModelId(Integer ipadU3dModelId) {
        this.ipadU3dModelId = ipadU3dModelId;
    }

    public Integer getIosU3dModelId() {
        return iosU3dModelId;
    }

    public void setIosU3dModelId(Integer iosU3dModelId) {
        this.iosU3dModelId = iosU3dModelId;
    }

    public Integer getAndroidU3dModelId() {
        return androidU3dModelId;
    }

    public void setAndroidU3dModelId(Integer androidU3dModelId) {
        this.androidU3dModelId = androidU3dModelId;
    }

    public Integer getMacBookU3dModelId() {
        return macBookU3dModelId;
    }

    public void setMacBookU3dModelId(Integer macBookU3dModelId) {
        this.macBookU3dModelId = macBookU3dModelId;
    }

    public Integer getWindowsU3dModelId() {
        return windowsU3dModelId;
    }

    public void setWindowsU3dModelId(Integer windowsU3dModelId) {
        this.windowsU3dModelId = windowsU3dModelId;
    }

    public Integer getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Integer designerId) {
        this.designerId = designerId;
    }

    public Integer getModelingId() {
        return modelingId;
    }

    public void setModelingId(Integer modelingId) {
        this.modelingId = modelingId;
    }

    public Integer getRenderingId() {
        return renderingId;
    }

    public void setRenderingId(Integer renderingId) {
        this.renderingId = renderingId;
    }

    public String getOriginalProductCode() {
        return originalProductCode;
    }

    public void setOriginalProductCode(String originalProductCode) {
        this.originalProductCode = originalProductCode;
    }

    public String getProductSmallTypeMark() {
        return productSmallTypeMark;
    }

    public void setProductSmallTypeMark(String productSmallTypeMark) {
        this.productSmallTypeMark = productSmallTypeMark;
    }

    public String getProductTypeMark() {
        return productTypeMark;
    }

    public void setProductTypeMark(String productTypeMark) {
        this.productTypeMark = productTypeMark;
    }

    public String getProductShortCode() {
        return productShortCode;
    }

    public void setProductShortCode(String productShortCode) {
        this.productShortCode = productShortCode;
    }

    public String getBmIds() {
        return bmIds;
    }

    public void setBmIds(String bmIds) {
        this.bmIds = bmIds;
    }

    public Integer getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(Integer codeNumber) {
        this.codeNumber = codeNumber;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getDesignTempletId() {
        return designTempletId;
    }

    public void setDesignTempletId(Integer designTempletId) {
        this.designTempletId = designTempletId;
    }

    public Integer getMaterialFileId() {
        return materialFileId;
    }

    public void setMaterialFileId(Integer materialFileId) {
        this.materialFileId = materialFileId;
    }

    public String getMaterial3dPicIds() {
        return material3dPicIds;
    }

    public void setMaterial3dPicIds(String material3dPicIds) {
        this.material3dPicIds = material3dPicIds;
    }

    public String getPicIds() {
        return picIds;
    }

    public void setPicIds(String picIds) {
        this.picIds = picIds;
    }

    public Integer getProductBatchType() {
        return productBatchType;
    }

    public void setProductBatchType(Integer productBatchType) {
        this.productBatchType = productBatchType;
    }
}
