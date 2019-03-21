package com.nork.product.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: ProductAttribute.java 
 * @Package com.nork.product.model.small
 * @Description:产品模块-产品属性
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 13:17:36
 * @version V1.0   
 */
public class ProductAttributeSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  产品小分类（产品属性父级）  **/
	private Integer attributeTypeValue;
	/**  属性值  **/
	private Integer attributeId;
	/**  属性key  **/
	private String attributeKey;
	/**  属性名称  **/
	private String attributeName;
	/**  属性值value  **/
	private Integer attributeValueId;
	/**  属性值key  **/
	private String attributeValueKey;
	/**  属性值名称  **/
	private String attributeValueName;
	/**  属性值图片  **/
	private String attributeValuePicIds;
	/**  产品ID  **/
	private Integer productId;
	/**  系统编码  **/
	private String sysCode;
	/**  创建者  **/
	private String creator;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  修改人  **/
	private String modifier;
	/**  修改时间  **/
	private Date gmtModified;
	/**  是否删除  **/
	private Integer isDeleted;
	/**  字符备用1  **/
	private String att1;
	/**  字符备用2  **/
	private String att2;
	/**  整数备用1  **/
	private Integer numa1;
	/**  整数备用2  **/
	private Integer numa2;
	/**  备注  **/
	private String remark;

	public  Integer getAttributeTypeValue() {
		return attributeTypeValue;
	}
	public void setAttributeTypeValue(Integer attributeTypeValue){
		this.attributeTypeValue = attributeTypeValue;
	}
	public  Integer getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(Integer attributeId){
		this.attributeId = attributeId;
	}
	public  String getAttributeKey() {
		return attributeKey;
	}
	public void setAttributeKey(String attributeKey){
		this.attributeKey = attributeKey;
	}
	public  String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName){
		this.attributeName = attributeName;
	}
	public  Integer getAttributeValueId() {
		return attributeValueId;
	}
	public void setAttributeValueId(Integer attributeValueId){
		this.attributeValueId = attributeValueId;
	}
	public  String getAttributeValueKey() {
		return attributeValueKey;
	}
	public void setAttributeValueKey(String attributeValueKey){
		this.attributeValueKey = attributeValueKey;
	}
	public  String getAttributeValueName() {
		return attributeValueName;
	}
	public void setAttributeValueName(String attributeValueName){
		this.attributeValueName = attributeValueName;
	}
	public  String getAttributeValuePicIds() {
		return attributeValuePicIds;
	}
	public void setAttributeValuePicIds(String attributeValuePicIds){
		this.attributeValuePicIds = attributeValuePicIds;
	}
	public  Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId){
		this.productId = productId;
	}
	public  String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode){
		this.sysCode = sysCode;
	}
	public  String getCreator() {
		return creator;
	}
	public void setCreator(String creator){
		this.creator = creator;
	}
	public  Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}
	public  String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier){
		this.modifier = modifier;
	}
	public  Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}
	public  Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	public  String getAtt1() {
		return att1;
	}
	public void setAtt1(String att1){
		this.att1 = att1;
	}
	public  String getAtt2() {
		return att2;
	}
	public void setAtt2(String att2){
		this.att2 = att2;
	}
	public  Integer getNuma1() {
		return numa1;
	}
	public void setNuma1(Integer numa1){
		this.numa1 = numa1;
	}
	public  Integer getNuma2() {
		return numa2;
	}
	public void setNuma2(Integer numa2){
		this.numa2 = numa2;
	}
	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}


}
