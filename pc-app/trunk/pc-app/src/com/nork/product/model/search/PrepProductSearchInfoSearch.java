package com.nork.product.model.search;

import java.io.Serializable;
import java.util.Date;

import com.nork.product.model.PrepProductSearchInfo;

/**   
 * @Title: PrepProductSearchInfoSearch.java 
 * @Package com.nork.product.model
 * @Description:产品模块-预处理表(产品搜索)查询对象
 * @createAuthor pandajun 
 * @CreateDate 2017-02-22 17:12:03
 * @version V1.0   
 */
public class PrepProductSearchInfoSearch extends  PrepProductSearchInfo implements Serializable{
private static final long serialVersionUID = 1L;
	/**  分类longCode-模糊查询  **/
	private String sch_CategoryLongCode_;
	/**  分类longCode-左模糊查询  **/
	private String sch_CategoryLongCode;
	/**  分类longCode-右模糊查询  **/
	private String schCategoryLongCode_;
	/**  分类longCode-区间查询-开始字符串  **/
	private String categoryLongCodeStart;
	/**  分类longCode-区间查询-结束字符串  **/
	private String categoryLongCodeEnd;
	/**  拆分一级code-模糊查询  **/
	private String sch_FirstStageCode_;
	/**  拆分一级code-左模糊查询  **/
	private String sch_FirstStageCode;
	/**  拆分一级code-右模糊查询  **/
	private String schFirstStageCode_;
	/**  拆分一级code-区间查询-开始字符串  **/
	private String firstStageCodeStart;
	/**  拆分一级code-区间查询-结束字符串  **/
	private String firstStageCodeEnd;
	/**  拆分二级code-模糊查询  **/
	private String sch_SecondStageCode_;
	/**  拆分二级code-左模糊查询  **/
	private String sch_SecondStageCode;
	/**  拆分二级code-右模糊查询  **/
	private String schSecondStageCode_;
	/**  拆分二级code-区间查询-开始字符串  **/
	private String secondStageCodeStart;
	/**  拆分二级code-区间查询-结束字符串  **/
	private String secondStageCodeEnd;
	/**  拆分三级code-模糊查询  **/
	private String sch_ThirdStageCode_;
	/**  拆分三级code-左模糊查询  **/
	private String sch_ThirdStageCode;
	/**  拆分三级code-右模糊查询  **/
	private String schThirdStageCode_;
	/**  拆分三级code-区间查询-开始字符串  **/
	private String thirdStageCodeStart;
	/**  拆分三级code-区间查询-结束字符串  **/
	private String thirdStageCodeEnd;
	/**  拆分四级code-模糊查询  **/
	private String sch_FourthStageCode_;
	/**  拆分四级code-左模糊查询  **/
	private String sch_FourthStageCode;
	/**  拆分四级code-右模糊查询  **/
	private String schFourthStageCode_;
	/**  拆分四级code-区间查询-开始字符串  **/
	private String fourthStageCodeStart;
	/**  拆分四级code-区间查询-结束字符串  **/
	private String fourthStageCodeEnd;
	/**  拆分五级code-模糊查询  **/
	private String sch_FifthStageCode_;
	/**  拆分五级code-左模糊查询  **/
	private String sch_FifthStageCode;
	/**  拆分五级code-右模糊查询  **/
	private String schFifthStageCode_;
	/**  拆分五级code-区间查询-开始字符串  **/
	private String fifthStageCodeStart;
	/**  拆分五级code-区间查询-结束字符串  **/
	private String fifthStageCodeEnd;
	/**  空间类型-模糊查询  **/
	private String sch_HouseTypeValue_;
	/**  空间类型-左模糊查询  **/
	private String sch_HouseTypeValue;
	/**  空间类型-右模糊查询  **/
	private String schHouseTypeValue_;
	/**  空间类型-区间查询-开始字符串  **/
	private String houseTypeValueStart;
	/**  空间类型-区间查询-结束字符串  **/
	private String houseTypeValueEnd;
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

	public  String getSch_CategoryLongCode_() {
		return sch_CategoryLongCode_;
	}
	public void setSch_CategoryLongCode_(String sch_CategoryLongCode_){
		this.sch_CategoryLongCode_ = sch_CategoryLongCode_;
	}
	public  String getSch_CategoryLongCode() {
		return sch_CategoryLongCode;
	}
	public void setSch_CategoryLongCode(String sch_CategoryLongCode){
		this.sch_CategoryLongCode = sch_CategoryLongCode;
	}
	public  String getSchCategoryLongCode_() {
		return schCategoryLongCode_;
	}
	public void setSchCategoryLongCode_(String schCategoryLongCode_){
		this.schCategoryLongCode_ = schCategoryLongCode_;
	}
	public  String getCategoryLongCodeStart() {
		return categoryLongCodeStart;
	}
	public void setCategoryLongCodeStart(String categoryLongCodeStart){
		this.categoryLongCodeStart = categoryLongCodeStart;
	}
	public  String getCategoryLongCodeEnd() {
		return categoryLongCodeEnd;
	}
	public void setCategoryLongCodeEnd(String categoryLongCodeEnd){
		this.categoryLongCodeEnd = categoryLongCodeEnd;
	}
	public  String getSch_FirstStageCode_() {
		return sch_FirstStageCode_;
	}
	public void setSch_FirstStageCode_(String sch_FirstStageCode_){
		this.sch_FirstStageCode_ = sch_FirstStageCode_;
	}
	public  String getSch_FirstStageCode() {
		return sch_FirstStageCode;
	}
	public void setSch_FirstStageCode(String sch_FirstStageCode){
		this.sch_FirstStageCode = sch_FirstStageCode;
	}
	public  String getSchFirstStageCode_() {
		return schFirstStageCode_;
	}
	public void setSchFirstStageCode_(String schFirstStageCode_){
		this.schFirstStageCode_ = schFirstStageCode_;
	}
	public  String getFirstStageCodeStart() {
		return firstStageCodeStart;
	}
	public void setFirstStageCodeStart(String firstStageCodeStart){
		this.firstStageCodeStart = firstStageCodeStart;
	}
	public  String getFirstStageCodeEnd() {
		return firstStageCodeEnd;
	}
	public void setFirstStageCodeEnd(String firstStageCodeEnd){
		this.firstStageCodeEnd = firstStageCodeEnd;
	}
	public  String getSch_SecondStageCode_() {
		return sch_SecondStageCode_;
	}
	public void setSch_SecondStageCode_(String sch_SecondStageCode_){
		this.sch_SecondStageCode_ = sch_SecondStageCode_;
	}
	public  String getSch_SecondStageCode() {
		return sch_SecondStageCode;
	}
	public void setSch_SecondStageCode(String sch_SecondStageCode){
		this.sch_SecondStageCode = sch_SecondStageCode;
	}
	public  String getSchSecondStageCode_() {
		return schSecondStageCode_;
	}
	public void setSchSecondStageCode_(String schSecondStageCode_){
		this.schSecondStageCode_ = schSecondStageCode_;
	}
	public  String getSecondStageCodeStart() {
		return secondStageCodeStart;
	}
	public void setSecondStageCodeStart(String secondStageCodeStart){
		this.secondStageCodeStart = secondStageCodeStart;
	}
	public  String getSecondStageCodeEnd() {
		return secondStageCodeEnd;
	}
	public void setSecondStageCodeEnd(String secondStageCodeEnd){
		this.secondStageCodeEnd = secondStageCodeEnd;
	}
	public  String getSch_ThirdStageCode_() {
		return sch_ThirdStageCode_;
	}
	public void setSch_ThirdStageCode_(String sch_ThirdStageCode_){
		this.sch_ThirdStageCode_ = sch_ThirdStageCode_;
	}
	public  String getSch_ThirdStageCode() {
		return sch_ThirdStageCode;
	}
	public void setSch_ThirdStageCode(String sch_ThirdStageCode){
		this.sch_ThirdStageCode = sch_ThirdStageCode;
	}
	public  String getSchThirdStageCode_() {
		return schThirdStageCode_;
	}
	public void setSchThirdStageCode_(String schThirdStageCode_){
		this.schThirdStageCode_ = schThirdStageCode_;
	}
	public  String getThirdStageCodeStart() {
		return thirdStageCodeStart;
	}
	public void setThirdStageCodeStart(String thirdStageCodeStart){
		this.thirdStageCodeStart = thirdStageCodeStart;
	}
	public  String getThirdStageCodeEnd() {
		return thirdStageCodeEnd;
	}
	public void setThirdStageCodeEnd(String thirdStageCodeEnd){
		this.thirdStageCodeEnd = thirdStageCodeEnd;
	}
	public  String getSch_FourthStageCode_() {
		return sch_FourthStageCode_;
	}
	public void setSch_FourthStageCode_(String sch_FourthStageCode_){
		this.sch_FourthStageCode_ = sch_FourthStageCode_;
	}
	public  String getSch_FourthStageCode() {
		return sch_FourthStageCode;
	}
	public void setSch_FourthStageCode(String sch_FourthStageCode){
		this.sch_FourthStageCode = sch_FourthStageCode;
	}
	public  String getSchFourthStageCode_() {
		return schFourthStageCode_;
	}
	public void setSchFourthStageCode_(String schFourthStageCode_){
		this.schFourthStageCode_ = schFourthStageCode_;
	}
	public  String getFourthStageCodeStart() {
		return fourthStageCodeStart;
	}
	public void setFourthStageCodeStart(String fourthStageCodeStart){
		this.fourthStageCodeStart = fourthStageCodeStart;
	}
	public  String getFourthStageCodeEnd() {
		return fourthStageCodeEnd;
	}
	public void setFourthStageCodeEnd(String fourthStageCodeEnd){
		this.fourthStageCodeEnd = fourthStageCodeEnd;
	}
	public  String getSch_FifthStageCode_() {
		return sch_FifthStageCode_;
	}
	public void setSch_FifthStageCode_(String sch_FifthStageCode_){
		this.sch_FifthStageCode_ = sch_FifthStageCode_;
	}
	public  String getSch_FifthStageCode() {
		return sch_FifthStageCode;
	}
	public void setSch_FifthStageCode(String sch_FifthStageCode){
		this.sch_FifthStageCode = sch_FifthStageCode;
	}
	public  String getSchFifthStageCode_() {
		return schFifthStageCode_;
	}
	public void setSchFifthStageCode_(String schFifthStageCode_){
		this.schFifthStageCode_ = schFifthStageCode_;
	}
	public  String getFifthStageCodeStart() {
		return fifthStageCodeStart;
	}
	public void setFifthStageCodeStart(String fifthStageCodeStart){
		this.fifthStageCodeStart = fifthStageCodeStart;
	}
	public  String getFifthStageCodeEnd() {
		return fifthStageCodeEnd;
	}
	public void setFifthStageCodeEnd(String fifthStageCodeEnd){
		this.fifthStageCodeEnd = fifthStageCodeEnd;
	}
	public  String getSch_HouseTypeValue_() {
		return sch_HouseTypeValue_;
	}
	public void setSch_HouseTypeValue_(String sch_HouseTypeValue_){
		this.sch_HouseTypeValue_ = sch_HouseTypeValue_;
	}
	public  String getSch_HouseTypeValue() {
		return sch_HouseTypeValue;
	}
	public void setSch_HouseTypeValue(String sch_HouseTypeValue){
		this.sch_HouseTypeValue = sch_HouseTypeValue;
	}
	public  String getSchHouseTypeValue_() {
		return schHouseTypeValue_;
	}
	public void setSchHouseTypeValue_(String schHouseTypeValue_){
		this.schHouseTypeValue_ = schHouseTypeValue_;
	}
	public  String getHouseTypeValueStart() {
		return houseTypeValueStart;
	}
	public void setHouseTypeValueStart(String houseTypeValueStart){
		this.houseTypeValueStart = houseTypeValueStart;
	}
	public  String getHouseTypeValueEnd() {
		return houseTypeValueEnd;
	}
	public void setHouseTypeValueEnd(String houseTypeValueEnd){
		this.houseTypeValueEnd = houseTypeValueEnd;
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
