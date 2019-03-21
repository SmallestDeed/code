package com.sandu.product.model.search;

import java.io.Serializable;
import java.util.Date;

import com.sandu.product.model.ProductAttribute;

/**   
 * @Title: ProductAttributeSearch.java 
 * @Package com.nork.product.model
 * @Description:产品模块-产品属性查询对象
 * @createAuthor pandajun 
 * @CreateDate 2015-09-01 13:17:36
 * @version V1.0   
 */
public class ProductAttributeSearch extends  ProductAttribute implements Serializable{
	private static final long serialVersionUID = 1L;
	/**  属性key-模糊查询  **/
	private String sch_AttributeKey_;
	/**  属性key-左模糊查询  **/
	private String sch_AttributeKey;
	/**  属性key-右模糊查询  **/
	private String schAttributeKey_;
	/**  属性key-区间查询-开始字符串  **/
	private String attributeKeyStart;
	/**  属性key-区间查询-结束字符串  **/
	private String attributeKeyEnd;
	/**  属性名称-模糊查询  **/
	private String sch_AttributeName_;
	/**  属性名称-左模糊查询  **/
	private String sch_AttributeName;
	/**  属性名称-右模糊查询  **/
	private String schAttributeName_;
	/**  属性名称-区间查询-开始字符串  **/
	private String attributeNameStart;
	/**  属性名称-区间查询-结束字符串  **/
	private String attributeNameEnd;
	/**  属性值key-模糊查询  **/
	private String sch_AttributeValueKey_;
	/**  属性值key-左模糊查询  **/
	private String sch_AttributeValueKey;
	/**  属性值key-右模糊查询  **/
	private String schAttributeValueKey_;
	/**  属性值key-区间查询-开始字符串  **/
	private String attributeValueKeyStart;
	/**  属性值key-区间查询-结束字符串  **/
	private String attributeValueKeyEnd;
	/**  属性值名称-模糊查询  **/
	private String sch_AttributeValueName_;
	/**  属性值名称-左模糊查询  **/
	private String sch_AttributeValueName;
	/**  属性值名称-右模糊查询  **/
	private String schAttributeValueName_;
	/**  属性值名称-区间查询-开始字符串  **/
	private String attributeValueNameStart;
	/**  属性值名称-区间查询-结束字符串  **/
	private String attributeValueNameEnd;
	/**  属性值图片-模糊查询  **/
	private String sch_AttributeValuePicIds_;
	/**  属性值图片-左模糊查询  **/
	private String sch_AttributeValuePicIds;
	/**  属性值图片-右模糊查询  **/
	private String schAttributeValuePicIds_;
	/**  属性值图片-区间查询-开始字符串  **/
	private String attributeValuePicIdsStart;
	/**  属性值图片-区间查询-结束字符串  **/
	private String attributeValuePicIdsEnd;
	/**  系统编码-模糊查询  **/
	private String sch_SysCode_;
	/**  系统编码-左模糊查询  **/
	private String sch_SysCode;
	/**  系统编码-右模糊查询  **/
	private String schSysCode_;
	/**  系统编码-区间查询-开始字符串  **/
	private String sysCodeStart;
	/**  系统编码-区间查询-结束字符串  **/
	private String sysCodeEnd;
	/**  创建者-模糊查询  **/
	private String sch_Creator_;
	/**  创建者-左模糊查询  **/
	private String sch_Creator;
	/**  创建者-右模糊查询  **/
	private String schCreator_;
	/**  创建者-区间查询-开始字符串  **/
	private String creatorStart;
	/**  创建者-区间查询-结束字符串  **/
	private String creatorEnd;
	/**  创建时间-区间查询-开始时间  **/
	private Date gmtCreateStart;
	/**  创建时间-区间查询-结束时间  **/
	private Date gmtCreateEnd;
	/**  修改人-模糊查询  **/
	private String sch_Modifier_;
	/**  修改人-左模糊查询  **/
	private String sch_Modifier;
	/**  修改人-右模糊查询  **/
	private String schModifier_;
	/**  修改人-区间查询-开始字符串  **/
	private String modifierStart;
	/**  修改人-区间查询-结束字符串  **/
	private String modifierEnd;
	/**  修改时间-区间查询-开始时间  **/
	private Date gmtModifiedStart;
	/**  修改时间-区间查询-结束时间  **/
	private Date gmtModifiedEnd;
	/**  字符备用1-模糊查询  **/
	private String sch_Att1_;
	/**  字符备用1-左模糊查询  **/
	private String sch_Att1;
	/**  字符备用1-右模糊查询  **/
	private String schAtt1_;
	/**  字符备用1-区间查询-开始字符串  **/
	private String att1Start;
	/**  字符备用1-区间查询-结束字符串  **/
	private String att1End;
	/**  字符备用2-模糊查询  **/
	private String sch_Att2_;
	/**  字符备用2-左模糊查询  **/
	private String sch_Att2;
	/**  字符备用2-右模糊查询  **/
	private String schAtt2_;
	/**  字符备用2-区间查询-开始字符串  **/
	private String att2Start;
	/**  字符备用2-区间查询-结束字符串  **/
	private String att2End;
	/**  备注-模糊查询  **/
	private String sch_Remark_;
	/**  备注-左模糊查询  **/
	private String sch_Remark;
	/**  备注-右模糊查询  **/
	private String schRemark_;
	/**  备注-区间查询-开始字符串  **/
	private String remarkStart;
	/**  备注-区间查询-结束字符串  **/
	private String remarkEnd;

	public  String getSch_AttributeKey_() {
		return sch_AttributeKey_;
	}
	public void setSch_AttributeKey_(String sch_AttributeKey_){
		this.sch_AttributeKey_ = sch_AttributeKey_;
	}
	public  String getSch_AttributeKey() {
		return sch_AttributeKey;
	}
	public void setSch_AttributeKey(String sch_AttributeKey){
		this.sch_AttributeKey = sch_AttributeKey;
	}
	public  String getSchAttributeKey_() {
		return schAttributeKey_;
	}
	public void setSchAttributeKey_(String schAttributeKey_){
		this.schAttributeKey_ = schAttributeKey_;
	}
	public  String getAttributeKeyStart() {
		return attributeKeyStart;
	}
	public void setAttributeKeyStart(String attributeKeyStart){
		this.attributeKeyStart = attributeKeyStart;
	}
	public  String getAttributeKeyEnd() {
		return attributeKeyEnd;
	}
	public void setAttributeKeyEnd(String attributeKeyEnd){
		this.attributeKeyEnd = attributeKeyEnd;
	}
	public  String getSch_AttributeName_() {
		return sch_AttributeName_;
	}
	public void setSch_AttributeName_(String sch_AttributeName_){
		this.sch_AttributeName_ = sch_AttributeName_;
	}
	public  String getSch_AttributeName() {
		return sch_AttributeName;
	}
	public void setSch_AttributeName(String sch_AttributeName){
		this.sch_AttributeName = sch_AttributeName;
	}
	public  String getSchAttributeName_() {
		return schAttributeName_;
	}
	public void setSchAttributeName_(String schAttributeName_){
		this.schAttributeName_ = schAttributeName_;
	}
	public  String getAttributeNameStart() {
		return attributeNameStart;
	}
	public void setAttributeNameStart(String attributeNameStart){
		this.attributeNameStart = attributeNameStart;
	}
	public  String getAttributeNameEnd() {
		return attributeNameEnd;
	}
	public void setAttributeNameEnd(String attributeNameEnd){
		this.attributeNameEnd = attributeNameEnd;
	}
	public  String getSch_AttributeValueKey_() {
		return sch_AttributeValueKey_;
	}
	public void setSch_AttributeValueKey_(String sch_AttributeValueKey_){
		this.sch_AttributeValueKey_ = sch_AttributeValueKey_;
	}
	public  String getSch_AttributeValueKey() {
		return sch_AttributeValueKey;
	}
	public void setSch_AttributeValueKey(String sch_AttributeValueKey){
		this.sch_AttributeValueKey = sch_AttributeValueKey;
	}
	public  String getSchAttributeValueKey_() {
		return schAttributeValueKey_;
	}
	public void setSchAttributeValueKey_(String schAttributeValueKey_){
		this.schAttributeValueKey_ = schAttributeValueKey_;
	}
	public  String getAttributeValueKeyStart() {
		return attributeValueKeyStart;
	}
	public void setAttributeValueKeyStart(String attributeValueKeyStart){
		this.attributeValueKeyStart = attributeValueKeyStart;
	}
	public  String getAttributeValueKeyEnd() {
		return attributeValueKeyEnd;
	}
	public void setAttributeValueKeyEnd(String attributeValueKeyEnd){
		this.attributeValueKeyEnd = attributeValueKeyEnd;
	}
	public  String getSch_AttributeValueName_() {
		return sch_AttributeValueName_;
	}
	public void setSch_AttributeValueName_(String sch_AttributeValueName_){
		this.sch_AttributeValueName_ = sch_AttributeValueName_;
	}
	public  String getSch_AttributeValueName() {
		return sch_AttributeValueName;
	}
	public void setSch_AttributeValueName(String sch_AttributeValueName){
		this.sch_AttributeValueName = sch_AttributeValueName;
	}
	public  String getSchAttributeValueName_() {
		return schAttributeValueName_;
	}
	public void setSchAttributeValueName_(String schAttributeValueName_){
		this.schAttributeValueName_ = schAttributeValueName_;
	}
	public  String getAttributeValueNameStart() {
		return attributeValueNameStart;
	}
	public void setAttributeValueNameStart(String attributeValueNameStart){
		this.attributeValueNameStart = attributeValueNameStart;
	}
	public  String getAttributeValueNameEnd() {
		return attributeValueNameEnd;
	}
	public void setAttributeValueNameEnd(String attributeValueNameEnd){
		this.attributeValueNameEnd = attributeValueNameEnd;
	}
	public  String getSch_AttributeValuePicIds_() {
		return sch_AttributeValuePicIds_;
	}
	public void setSch_AttributeValuePicIds_(String sch_AttributeValuePicIds_){
		this.sch_AttributeValuePicIds_ = sch_AttributeValuePicIds_;
	}
	public  String getSch_AttributeValuePicIds() {
		return sch_AttributeValuePicIds;
	}
	public void setSch_AttributeValuePicIds(String sch_AttributeValuePicIds){
		this.sch_AttributeValuePicIds = sch_AttributeValuePicIds;
	}
	public  String getSchAttributeValuePicIds_() {
		return schAttributeValuePicIds_;
	}
	public void setSchAttributeValuePicIds_(String schAttributeValuePicIds_){
		this.schAttributeValuePicIds_ = schAttributeValuePicIds_;
	}
	public  String getAttributeValuePicIdsStart() {
		return attributeValuePicIdsStart;
	}
	public void setAttributeValuePicIdsStart(String attributeValuePicIdsStart){
		this.attributeValuePicIdsStart = attributeValuePicIdsStart;
	}
	public  String getAttributeValuePicIdsEnd() {
		return attributeValuePicIdsEnd;
	}
	public void setAttributeValuePicIdsEnd(String attributeValuePicIdsEnd){
		this.attributeValuePicIdsEnd = attributeValuePicIdsEnd;
	}
	public  String getSch_SysCode_() {
		return sch_SysCode_;
	}
	public void setSch_SysCode_(String sch_SysCode_){
		this.sch_SysCode_ = sch_SysCode_;
	}
	public  String getSch_SysCode() {
		return sch_SysCode;
	}
	public void setSch_SysCode(String sch_SysCode){
		this.sch_SysCode = sch_SysCode;
	}
	public  String getSchSysCode_() {
		return schSysCode_;
	}
	public void setSchSysCode_(String schSysCode_){
		this.schSysCode_ = schSysCode_;
	}
	public  String getSysCodeStart() {
		return sysCodeStart;
	}
	public void setSysCodeStart(String sysCodeStart){
		this.sysCodeStart = sysCodeStart;
	}
	public  String getSysCodeEnd() {
		return sysCodeEnd;
	}
	public void setSysCodeEnd(String sysCodeEnd){
		this.sysCodeEnd = sysCodeEnd;
	}
	public  String getSch_Creator_() {
		return sch_Creator_;
	}
	public void setSch_Creator_(String sch_Creator_){
		this.sch_Creator_ = sch_Creator_;
	}
	public  String getSch_Creator() {
		return sch_Creator;
	}
	public void setSch_Creator(String sch_Creator){
		this.sch_Creator = sch_Creator;
	}
	public  String getSchCreator_() {
		return schCreator_;
	}
	public void setSchCreator_(String schCreator_){
		this.schCreator_ = schCreator_;
	}
	public  String getCreatorStart() {
		return creatorStart;
	}
	public void setCreatorStart(String creatorStart){
		this.creatorStart = creatorStart;
	}
	public  String getCreatorEnd() {
		return creatorEnd;
	}
	public void setCreatorEnd(String creatorEnd){
		this.creatorEnd = creatorEnd;
	}
	public  Date getGmtCreateStart() {
		return gmtCreateStart;
	}
	public void setGmtCreateStart(Date gmtCreateStart){
		this.gmtCreateStart = gmtCreateStart;
	}
	public  Date getGmtCreateEnd() {
		return gmtCreateEnd;
	}
	public void setGmtCreateEnd(Date gmtCreateEnd){
		this.gmtCreateEnd = gmtCreateEnd;
	}
	public  String getSch_Modifier_() {
		return sch_Modifier_;
	}
	public void setSch_Modifier_(String sch_Modifier_){
		this.sch_Modifier_ = sch_Modifier_;
	}
	public  String getSch_Modifier() {
		return sch_Modifier;
	}
	public void setSch_Modifier(String sch_Modifier){
		this.sch_Modifier = sch_Modifier;
	}
	public  String getSchModifier_() {
		return schModifier_;
	}
	public void setSchModifier_(String schModifier_){
		this.schModifier_ = schModifier_;
	}
	public  String getModifierStart() {
		return modifierStart;
	}
	public void setModifierStart(String modifierStart){
		this.modifierStart = modifierStart;
	}
	public  String getModifierEnd() {
		return modifierEnd;
	}
	public void setModifierEnd(String modifierEnd){
		this.modifierEnd = modifierEnd;
	}
	public  Date getGmtModifiedStart() {
		return gmtModifiedStart;
	}
	public void setGmtModifiedStart(Date gmtModifiedStart){
		this.gmtModifiedStart = gmtModifiedStart;
	}
	public  Date getGmtModifiedEnd() {
		return gmtModifiedEnd;
	}
	public void setGmtModifiedEnd(Date gmtModifiedEnd){
		this.gmtModifiedEnd = gmtModifiedEnd;
	}
	public  String getSch_Att1_() {
		return sch_Att1_;
	}
	public void setSch_Att1_(String sch_Att1_){
		this.sch_Att1_ = sch_Att1_;
	}
	public  String getSch_Att1() {
		return sch_Att1;
	}
	public void setSch_Att1(String sch_Att1){
		this.sch_Att1 = sch_Att1;
	}
	public  String getSchAtt1_() {
		return schAtt1_;
	}
	public void setSchAtt1_(String schAtt1_){
		this.schAtt1_ = schAtt1_;
	}
	public  String getAtt1Start() {
		return att1Start;
	}
	public void setAtt1Start(String att1Start){
		this.att1Start = att1Start;
	}
	public  String getAtt1End() {
		return att1End;
	}
	public void setAtt1End(String att1End){
		this.att1End = att1End;
	}
	public  String getSch_Att2_() {
		return sch_Att2_;
	}
	public void setSch_Att2_(String sch_Att2_){
		this.sch_Att2_ = sch_Att2_;
	}
	public  String getSch_Att2() {
		return sch_Att2;
	}
	public void setSch_Att2(String sch_Att2){
		this.sch_Att2 = sch_Att2;
	}
	public  String getSchAtt2_() {
		return schAtt2_;
	}
	public void setSchAtt2_(String schAtt2_){
		this.schAtt2_ = schAtt2_;
	}
	public  String getAtt2Start() {
		return att2Start;
	}
	public void setAtt2Start(String att2Start){
		this.att2Start = att2Start;
	}
	public  String getAtt2End() {
		return att2End;
	}
	public void setAtt2End(String att2End){
		this.att2End = att2End;
	}
	public  String getSch_Remark_() {
		return sch_Remark_;
	}
	public void setSch_Remark_(String sch_Remark_){
		this.sch_Remark_ = sch_Remark_;
	}
	public  String getSch_Remark() {
		return sch_Remark;
	}
	public void setSch_Remark(String sch_Remark){
		this.sch_Remark = sch_Remark;
	}
	public  String getSchRemark_() {
		return schRemark_;
	}
	public void setSchRemark_(String schRemark_){
		this.schRemark_ = schRemark_;
	}
	public  String getRemarkStart() {
		return remarkStart;
	}
	public void setRemarkStart(String remarkStart){
		this.remarkStart = remarkStart;
	}
	public  String getRemarkEnd() {
		return remarkEnd;
	}
	public void setRemarkEnd(String remarkEnd){
		this.remarkEnd = remarkEnd;
	}

}
