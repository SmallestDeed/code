package com.sandu.product.model;

import com.sandu.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: ProductAttribute.java
 * @Package com.sandu.product.model
 * @Description:产品模块-产品属性
 * @createAuthor pandajun
 * @CreateDate 2015-09-01 13:17:36
 */
public class ProductAttribute extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 产品小分类（产品属性父级）
     **/
    private String attributeTypeValue;
    /**
     * 属性值
     **/
    private Integer attributeId;
    /**
     * 属性key
     **/
    private String attributeKey;
    /**
     * 属性名称
     **/
    private String attributeName;
    /**
     * 属性值value
     **/
    private Integer attributeValueId;
    /**
     * 属性值key
     **/
    private String attributeValueKey;
    /**
     * 属性值名称
     **/
    private String attributeValueName;
    /**
     * 属性值图片
     **/
    private String attributeValuePicIds;
    /**
     * 产品ID
     **/
    private Integer productId;
    /**
     * 系统编码
     **/
    private String sysCode;
    /**
     * 创建者
     **/
    private String creator;
    /**
     * 创建时间
     **/
    private Date gmtCreate;
    /**
     * 修改人
     **/
    private String modifier;
    /**
     * 修改时间
     **/
    private Date gmtModified;
    /**
     * 是否删除
     **/
    private Integer isDeleted;
    /**
     * 字符备用1
     **/
    private String productCode;
    /**
     * 字符备用2
     **/
    private String att2;
    /**
     * 整数备用1
     **/
    private Integer numa1;
    /**
     * 整数备用2
     **/
    private Integer numa2;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 属性值
     **/
    private Integer propValue;

    /**
     * 对应product_props表的数据的父属性的filterOrder字段
     * product_attribute->找出对应的product_props->根据pid找出上级属性product_props->获得filterOrder字段
     */
    private String parentFilterOrder;

    public String getParentFilterOrder() {
        return parentFilterOrder;
    }

    public void setParentFilterOrder(String parentFilterOrder) {
        this.parentFilterOrder = parentFilterOrder;
    }

    public String getAttributeTypeValue() {
        return attributeTypeValue;
    }

    public void setAttributeTypeValue(String attributeTypeValue) {
        this.attributeTypeValue = attributeTypeValue;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Integer getAttributeValueId() {
        return attributeValueId;
    }

    public void setAttributeValueId(Integer attributeValueId) {
        this.attributeValueId = attributeValueId;
    }

    public String getAttributeValueKey() {
        return attributeValueKey;
    }

    public void setAttributeValueKey(String attributeValueKey) {
        this.attributeValueKey = attributeValueKey;
    }

    public String getAttributeValueName() {
        return attributeValueName;
    }

    public void setAttributeValueName(String attributeValueName) {
        this.attributeValueName = attributeValueName;
    }

    public String getAttributeValuePicIds() {
        return attributeValuePicIds;
    }

    public void setAttributeValuePicIds(String attributeValuePicIds) {
        this.attributeValuePicIds = attributeValuePicIds;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public String getAtt2() {
        return att2;
    }

    public void setAtt2(String att2) {
        this.att2 = att2;
    }

    public Integer getNuma1() {
        return numa1;
    }

    public void setNuma1(Integer numa1) {
        this.numa1 = numa1;
    }

    public Integer getNuma2() {
        return numa2;
    }

    public void setNuma2(Integer numa2) {
        this.numa2 = numa2;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ProductAttribute other = (ProductAttribute) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getAttributeTypeValue() == null ? other.getAttributeTypeValue() == null : this.getAttributeTypeValue().equals(other.getAttributeTypeValue()))
                && (this.getAttributeId() == null ? other.getAttributeId() == null : this.getAttributeId().equals(other.getAttributeId()))
                && (this.getAttributeKey() == null ? other.getAttributeKey() == null : this.getAttributeKey().equals(other.getAttributeKey()))
                && (this.getAttributeName() == null ? other.getAttributeName() == null : this.getAttributeName().equals(other.getAttributeName()))
                && (this.getAttributeValueId() == null ? other.getAttributeValueId() == null : this.getAttributeValueId().equals(other.getAttributeValueId()))
                && (this.getAttributeValueKey() == null ? other.getAttributeValueKey() == null : this.getAttributeValueKey().equals(other.getAttributeValueKey()))
                && (this.getAttributeValueName() == null ? other.getAttributeValueName() == null : this.getAttributeValueName().equals(other.getAttributeValueName()))
                && (this.getAttributeValuePicIds() == null ? other.getAttributeValuePicIds() == null : this.getAttributeValuePicIds().equals(other.getAttributeValuePicIds()))
                && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
                && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getProductCode() == null ? other.getProductCode() == null : this.getProductCode().equals(other.getProductCode()))
                && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
                && (this.getNuma1() == null ? other.getNuma1() == null : this.getNuma1().equals(other.getNuma1()))
                && (this.getNuma2() == null ? other.getNuma2() == null : this.getNuma2().equals(other.getNuma2()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAttributeTypeValue() == null) ? 0 : getAttributeTypeValue().hashCode());
        result = prime * result + ((getAttributeId() == null) ? 0 : getAttributeId().hashCode());
        result = prime * result + ((getAttributeKey() == null) ? 0 : getAttributeKey().hashCode());
        result = prime * result + ((getAttributeName() == null) ? 0 : getAttributeName().hashCode());
        result = prime * result + ((getAttributeValueId() == null) ? 0 : getAttributeValueId().hashCode());
        result = prime * result + ((getAttributeValueKey() == null) ? 0 : getAttributeValueKey().hashCode());
        result = prime * result + ((getAttributeValueName() == null) ? 0 : getAttributeValueName().hashCode());
        result = prime * result + ((getAttributeValuePicIds() == null) ? 0 : getAttributeValuePicIds().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getProductCode() == null) ? 0 : getProductCode().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getNuma1() == null) ? 0 : getNuma1().hashCode());
        result = prime * result + ((getNuma2() == null) ? 0 : getNuma2().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    /**
     * 获取对象的copy
     **/
    public ProductAttribute copy() {
        ProductAttribute obj = new ProductAttribute();
        obj.setAttributeTypeValue(this.attributeTypeValue);
        obj.setAttributeId(this.attributeId);
        obj.setAttributeKey(this.attributeKey);
        obj.setAttributeName(this.attributeName);
        obj.setAttributeValueId(this.attributeValueId);
        obj.setAttributeValueKey(this.attributeValueKey);
        obj.setAttributeValueName(this.attributeValueName);
        obj.setAttributeValuePicIds(this.attributeValuePicIds);
        obj.setProductId(this.productId);
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setProductCode(this.productCode);
        obj.setAtt2(this.att2);
        obj.setNuma1(this.numa1);
        obj.setNuma2(this.numa2);
        obj.setRemark(this.remark);

        return obj;
    }

    /**
     * 获取对象的map
     **/
    public Map toMap() {
        Map map = new HashMap();
        map.put("attributeTypeValue", this.attributeTypeValue);
        map.put("attributeId", this.attributeId);
        map.put("attributeKey", this.attributeKey);
        map.put("attributeName", this.attributeName);
        map.put("attributeValueId", this.attributeValueId);
        map.put("attributeValueKey", this.attributeValueKey);
        map.put("attributeValueName", this.attributeValueName);
        map.put("attributeValuePicIds", this.attributeValuePicIds);
        map.put("productId", this.productId);
        map.put("sysCode", this.sysCode);
        map.put("creator", this.creator);
        map.put("gmtCreate", this.gmtCreate);
        map.put("modifier", this.modifier);
        map.put("gmtModified", this.gmtModified);
        map.put("isDeleted", this.isDeleted);
        map.put("productCode", this.productCode);
        map.put("att2", this.att2);
        map.put("numa1", this.numa1);
        map.put("numa2", this.numa2);
        map.put("remark", this.remark);

        return map;
    }

    private String productTypeName;

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    private List<Integer> productIdList;

    public List<Integer> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<Integer> productIdList) {
        this.productIdList = productIdList;
    }

    public Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPropValue() {
        return propValue;
    }

    public void setPropValue(Integer propValue) {
        this.propValue = propValue;
    }
}
