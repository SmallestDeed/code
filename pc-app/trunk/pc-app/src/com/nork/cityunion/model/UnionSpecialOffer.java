package com.nork.cityunion.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.nork.common.model.Mapper;

/**   
 * @Title: UnionSpecialOffer.java 
 * @Package com.nork.cityunion.model
 * @Description:同城联盟-联盟优惠活动表
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:24:34
 * @version V1.0   
 */
public class UnionSpecialOffer  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  用户ID  **/
	private Integer userId;
	/**  优惠活动名称  **/
	private String specialOfferName;
	/**  优惠活动内容  **/
	private String specialOfferContent;
	/**  是否显示(0否/1是)  **/
	private Integer isDisplayed;
	/**  图片id  **/
	private Integer picId;
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

	/** 活动开始时间 **/
	private Date effectiveBegin;
	/** 活动结束时间 **/
	private Date effectiveEnd;
	/** 活动内容格式是否已处理 **/
	private boolean isTransformContent;

	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  String getSpecialOfferName() {
		return specialOfferName;
	}
	public void setSpecialOfferName(String specialOfferName){
		this.specialOfferName = specialOfferName;
	}
	public  String getSpecialOfferContent() {
		return specialOfferContent;
	}
	public void setSpecialOfferContent(String specialOfferContent){
		this.specialOfferContent = specialOfferContent;
	}
	public  Integer getIsDisplayed() {
		return isDisplayed;
	}
	public void setIsDisplayed(Integer isDisplayed){
		this.isDisplayed = isDisplayed;
	}
	public  Integer getPicId() {
		return picId;
	}
	public void setPicId(Integer picId){
		this.picId = picId;
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

	public boolean isTransformContent() {
		return isTransformContent;
	}

	public void setTransformContent(boolean transformContent) {
		isTransformContent = transformContent;
	}

	public Date getEffectiveBegin() {
		return effectiveBegin;
	}

	public void setEffectiveBegin(Date effectiveBegin) {
		this.effectiveBegin = effectiveBegin;
	}

	public Date getEffectiveEnd() {
		return effectiveEnd;
	}

	public void setEffectiveEnd(Date effectiveEnd) {
		this.effectiveEnd = effectiveEnd;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;

		UnionSpecialOffer that = (UnionSpecialOffer) object;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
		if (specialOfferName != null ? !specialOfferName.equals(that.specialOfferName) : that.specialOfferName != null)
			return false;
		if (specialOfferContent != null ? !specialOfferContent.equals(that.specialOfferContent) : that.specialOfferContent != null)
			return false;
		if (isDisplayed != null ? !isDisplayed.equals(that.isDisplayed) : that.isDisplayed != null) return false;
		if (picId != null ? !picId.equals(that.picId) : that.picId != null) return false;
		if (sysCode != null ? !sysCode.equals(that.sysCode) : that.sysCode != null) return false;
		if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
		if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null) return false;
		if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
		if (gmtModified != null ? !gmtModified.equals(that.gmtModified) : that.gmtModified != null) return false;
		if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
		if (att1 != null ? !att1.equals(that.att1) : that.att1 != null) return false;
		if (att2 != null ? !att2.equals(that.att2) : that.att2 != null) return false;
		if (numa1 != null ? !numa1.equals(that.numa1) : that.numa1 != null) return false;
		if (numa2 != null ? !numa2.equals(that.numa2) : that.numa2 != null) return false;
		if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
		if (effectiveBegin != null ? !effectiveBegin.equals(that.effectiveBegin) : that.effectiveBegin != null)
			return false;
		return effectiveEnd != null ? effectiveEnd.equals(that.effectiveEnd) : that.effectiveEnd == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (userId != null ? userId.hashCode() : 0);
		result = 31 * result + (specialOfferName != null ? specialOfferName.hashCode() : 0);
		result = 31 * result + (specialOfferContent != null ? specialOfferContent.hashCode() : 0);
		result = 31 * result + (isDisplayed != null ? isDisplayed.hashCode() : 0);
		result = 31 * result + (picId != null ? picId.hashCode() : 0);
		result = 31 * result + (sysCode != null ? sysCode.hashCode() : 0);
		result = 31 * result + (creator != null ? creator.hashCode() : 0);
		result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
		result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
		result = 31 * result + (gmtModified != null ? gmtModified.hashCode() : 0);
		result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
		result = 31 * result + (att1 != null ? att1.hashCode() : 0);
		result = 31 * result + (att2 != null ? att2.hashCode() : 0);
		result = 31 * result + (numa1 != null ? numa1.hashCode() : 0);
		result = 31 * result + (numa2 != null ? numa2.hashCode() : 0);
		result = 31 * result + (remark != null ? remark.hashCode() : 0);
		result = 31 * result + (effectiveBegin != null ? effectiveBegin.hashCode() : 0);
		result = 31 * result + (effectiveEnd != null ? effectiveEnd.hashCode() : 0);
		return result;
	}

	/**获取对象的copy**/
    public UnionSpecialOffer copy(){
       UnionSpecialOffer obj =  new UnionSpecialOffer();
       obj.setUserId(this.userId);
       obj.setSpecialOfferName(this.specialOfferName);
       obj.setSpecialOfferContent(this.specialOfferContent);
       obj.setIsDisplayed(this.isDisplayed);
       obj.setPicId(this.picId);
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setAtt1(this.att1);
       obj.setAtt2(this.att2);
       obj.setNuma1(this.numa1);
       obj.setNuma2(this.numa2);
       obj.setRemark(this.remark);

       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("userId",this.userId);
       map.put("specialOfferName",this.specialOfferName);
       map.put("specialOfferContent",this.specialOfferContent);
       map.put("isDisplayed",this.isDisplayed);
       map.put("picId",this.picId);
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("att1",this.att1);
       map.put("att2",this.att2);
       map.put("numa1",this.numa1);
       map.put("numa2",this.numa2);
       map.put("remark",this.remark);

       return map;
    }
}
