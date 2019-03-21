package com.sandu.product.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sandu.product.model.po.ProductAttributePO;

/**   
 * @Title: ProductAttribute.java 
 * @Package com.nork.product.model
 * @Description:产品模块-产品属性
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 13:17:36
 * @version V1.0   
 */
public class ProductAttribute extends ProductAttributePO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 属性值 **/
	private Integer propValue;
	
	/**
	 * 对应product_props表的数据的父属性的filterOrder字段
	 * product_attribute->找出对应的product_props->根据pid找出上级属性product_props->获得filterOrder字段
	 */
	private String parentFilterOrder;
	
	private String productTypeName;
	
	private String  deviceId = null;
	private String  msgId = null;
	private String  ids = null;
	private Integer start = 0;
	private Integer limit = 20;
	private String  order = null;
	private String  orderNum = null;
	private String  orders = null;
	/**级别限制的资源数量*/
	private int levelLimitCount=0;
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public int getLevelLimitCount() {
		return levelLimitCount;
	}

	public void setLevelLimitCount(int levelLimitCount) {
		this.levelLimitCount = levelLimitCount;
	}

	public String getParentFilterOrder() {
		return parentFilterOrder;
	}

	public void setParentFilterOrder(String parentFilterOrder) {
		this.parentFilterOrder = parentFilterOrder;
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
;
        return result;
    }
    
    /**获取对象的copy**/
    public ProductAttribute copy(){
       ProductAttribute obj =  new ProductAttribute();
       obj.setAttributeTypeValue(this.getAttributeTypeValue());
       obj.setAttributeId(this.getAttributeId());
       obj.setAttributeKey(this.getAttributeKey());
       obj.setAttributeName(this.getAttributeName());
       obj.setAttributeValueId(this.getAttributeValueId());
       obj.setAttributeValueKey(this.getAttributeValueKey());
       obj.setAttributeValueName(this.getAttributeValueName());
       obj.setAttributeValuePicIds(this.getAttributeValuePicIds());
       obj.setProductId(this.getProductId());
       obj.setSysCode(this.getSysCode());
       obj.setCreator(this.getCreator());
       obj.setGmtCreate(this.getGmtCreate());
       obj.setModifier(this.getModifier());
       obj.setGmtModified(this.getGmtModified());
       obj.setIsDeleted(this.getIsDeleted());
       obj.setProductCode(this.getProductCode());
       obj.setAtt2(this.getAtt2());
       obj.setNuma1(this.getNuma1());
       obj.setNuma2(this.getNuma2());
       obj.setRemark(this.getRemark());

       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("attributeTypeValue",this.getAttributeTypeValue());
       map.put("attributeId",this.getAttributeId());
       map.put("attributeKey",this.getAttributeKey());
       map.put("attributeName",this.getAttributeName());
       map.put("attributeValueId",this.getAttributeValueId());
       map.put("attributeValueKey",this.getAttributeValueKey());
       map.put("attributeValueName",this.getAttributeValueName());
       map.put("attributeValuePicIds",this.getAttributeValuePicIds());
       map.put("productId",this.getProductId());
       map.put("sysCode",this.getSysCode());
       map.put("creator",this.getCreator());
       map.put("gmtCreate",this.getGmtCreate());
       map.put("modifier",this.getModifier());
       map.put("gmtModified",this.getGmtModified());
       map.put("isDeleted",this.getIsDeleted());
       map.put("productCode",this.getProductCode());
       map.put("att2",this.getAtt2());
       map.put("numa1",this.getNuma1());
       map.put("numa2",this.getNuma2());
       map.put("remark",this.getRemark());
       return map;
    }
    
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
