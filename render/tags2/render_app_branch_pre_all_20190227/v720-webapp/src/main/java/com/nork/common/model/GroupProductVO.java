package com.nork.common.model;
/**
 * 组合产品详情参数VO
 * @author xiaozp
 *
 */
public class GroupProductVO {
	private String msgId;//
	private Integer colorId;//颜色 
	private String materialId;//材质
	private String onclickType;//
	private String mergeProductIds;//产品材质路径
	private Integer productId;//产品ID
	private String attributeKey;//属性key
	private String attributeValueKey;//属性值key
	private Integer groupId;//组合ID
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public Integer getColorId() {
		return colorId;
	}
	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getOnclickType() {
		return onclickType;
	}
	public void setOnclickType(String onclickType) {
		this.onclickType = onclickType;
	}
	public String getMergeProductIds() {
		return mergeProductIds;
	}
	public void setMergeProductIds(String mergeProductIds) {
		this.mergeProductIds = mergeProductIds;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getAttributeKey() {
		return attributeKey;
	}
	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}
	public String getAttributeValueKey() {
		return attributeValueKey;
	}
	public void setAttributeValueKey(String attributeValueKey) {
		this.attributeValueKey = attributeValueKey;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	 
	
	
}
 