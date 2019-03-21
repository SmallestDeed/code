package com.nork.home.model.search;

import java.io.Serializable;
import java.util.Date;

import com.nork.home.model.DesignRecommendation;

/**   
 * @Title: DesignRecommendationSearch.java 
 * @Package com.nork.home.model
 * @Description:户型房型-效果图推荐查询对象
 * @createAuthor pandajun 
 * @CreateDate 2015-07-06 20:00:19
 * @version V1.0   
 */
public class DesignRecommendationSearch extends  DesignRecommendation implements Serializable{
private static final long serialVersionUID = 1L;
	/**  方案来源类型  **/
	private String planSourceType;
	/**  来源code-模糊查询  **/
	private String sch_SrcCode_;
	public String getPlanSourceType() {
		return planSourceType;
	}
	public void setPlanSourceType(String planSourceType) {
		this.planSourceType = planSourceType;
	}
	/**  来源code-左模糊查询  **/
	private String sch_SrcCode;
	/**  来源code-右模糊查询  **/
	private String schSrcCode_;
	/**  来源code-区间查询-开始字符串  **/
	private String srcCodeStart;
	/**  来源code-区间查询-结束字符串  **/
	private String srcCodeEnd;
	/**  来源类型-模糊查询  **/
	private String sch_SrcType_;
	/**  来源类型-左模糊查询  **/
	private String sch_SrcType;
	/**  来源类型-右模糊查询  **/
	private String schSrcType_;
	/**  来源类型-区间查询-开始字符串  **/
	private String srcTypeStart;
	/**  来源类型-区间查询-结束字符串  **/
	private String srcTypeEnd;
	/**  图片ID-模糊查询  **/
	private String sch_PicId_;
	/**  图片ID-左模糊查询  **/
	private String sch_PicId;
	/**  图片ID-右模糊查询  **/
	private String schPicId_;
	/**  图片ID-区间查询-开始字符串  **/
	private String picIdStart;
	/**  图片ID-区间查询-结束字符串  **/
	private String picIdEnd;
	/**  设计风格-模糊查询  **/
	private String sch_DesignStyleId_;
	/**  设计风格-左模糊查询  **/
	private String sch_DesignStyleId;
	/**  设计风格-右模糊查询  **/
	private String schDesignStyleId_;
	/**  设计风格-区间查询-开始字符串  **/
	private String designStyleIdStart;
	/**  设计风格-区间查询-结束字符串  **/
	private String designStyleIdEnd;
	/**  面积-模糊查询  **/
	private String sch_SpaceArea_;
	/**  面积-左模糊查询  **/
	private String sch_SpaceArea;
	/**  面积-右模糊查询  **/
	private String schSpaceArea_;
	/**  面积-区间查询-开始字符串  **/
	private String spaceAreaStart;
	/**  面积-区间查询-结束字符串  **/
	private String spaceAreaEnd;
	/**  名称-模糊查询  **/
	private String sch_DesignName_;
	/**  名称-左模糊查询  **/
	private String sch_DesignName;
	/**  名称-右模糊查询  **/
	private String schDesignName_;
	/**  名称-区间查询-开始字符串  **/
	private String designNameStart;
	/**  名称-区间查询-结束字符串  **/
	private String designNameEnd;
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
	private String sch_Description_;
	/**  字符备用1-左模糊查询  **/
	private String sch_Description;
	/**  字符备用1-右模糊查询  **/
	private String schDescription_;
	/**  字符备用1-区间查询-开始字符串  **/
	private String descriptionStart;
	/**  字符备用1-区间查询-结束字符串  **/
	private String descriptionEnd;
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

	public  String getSch_SrcCode_() {
		return sch_SrcCode_;
	}
	public void setSch_SrcCode_(String sch_SrcCode_){
		this.sch_SrcCode_ = sch_SrcCode_;
	}
	public  String getSch_SrcCode() {
		return sch_SrcCode;
	}
	public void setSch_SrcCode(String sch_SrcCode){
		this.sch_SrcCode = sch_SrcCode;
	}
	public  String getSchSrcCode_() {
		return schSrcCode_;
	}
	public void setSchSrcCode_(String schSrcCode_){
		this.schSrcCode_ = schSrcCode_;
	}
	public  String getSrcCodeStart() {
		return srcCodeStart;
	}
	public void setSrcCodeStart(String srcCodeStart){
		this.srcCodeStart = srcCodeStart;
	}
	public  String getSrcCodeEnd() {
		return srcCodeEnd;
	}
	public void setSrcCodeEnd(String srcCodeEnd){
		this.srcCodeEnd = srcCodeEnd;
	}
	public  String getSch_SrcType_() {
		return sch_SrcType_;
	}
	public void setSch_SrcType_(String sch_SrcType_){
		this.sch_SrcType_ = sch_SrcType_;
	}
	public  String getSch_SrcType() {
		return sch_SrcType;
	}
	public void setSch_SrcType(String sch_SrcType){
		this.sch_SrcType = sch_SrcType;
	}
	public  String getSchSrcType_() {
		return schSrcType_;
	}
	public void setSchSrcType_(String schSrcType_){
		this.schSrcType_ = schSrcType_;
	}
	public  String getSrcTypeStart() {
		return srcTypeStart;
	}
	public void setSrcTypeStart(String srcTypeStart){
		this.srcTypeStart = srcTypeStart;
	}
	public  String getSrcTypeEnd() {
		return srcTypeEnd;
	}
	public void setSrcTypeEnd(String srcTypeEnd){
		this.srcTypeEnd = srcTypeEnd;
	}
	public  String getSch_PicId_() {
		return sch_PicId_;
	}
	public void setSch_PicId_(String sch_PicId_){
		this.sch_PicId_ = sch_PicId_;
	}
	public  String getSch_PicId() {
		return sch_PicId;
	}
	public void setSch_PicId(String sch_PicId){
		this.sch_PicId = sch_PicId;
	}
	public  String getSchPicId_() {
		return schPicId_;
	}
	public void setSchPicId_(String schPicId_){
		this.schPicId_ = schPicId_;
	}
	public  String getPicIdStart() {
		return picIdStart;
	}
	public void setPicIdStart(String picIdStart){
		this.picIdStart = picIdStart;
	}
	public  String getPicIdEnd() {
		return picIdEnd;
	}
	public void setPicIdEnd(String picIdEnd){
		this.picIdEnd = picIdEnd;
	}
	public  String getSch_DesignStyleId_() {
		return sch_DesignStyleId_;
	}
	public void setSch_DesignStyleId_(String sch_DesignStyleId_){
		this.sch_DesignStyleId_ = sch_DesignStyleId_;
	}
	public  String getSch_DesignStyleId() {
		return sch_DesignStyleId;
	}
	public void setSch_DesignStyleId(String sch_DesignStyleId){
		this.sch_DesignStyleId = sch_DesignStyleId;
	}
	public  String getSchDesignStyleId_() {
		return schDesignStyleId_;
	}
	public void setSchDesignStyleId_(String schDesignStyleId_){
		this.schDesignStyleId_ = schDesignStyleId_;
	}
	public  String getDesignStyleIdStart() {
		return designStyleIdStart;
	}
	public void setDesignStyleIdStart(String designStyleIdStart){
		this.designStyleIdStart = designStyleIdStart;
	}
	public  String getDesignStyleIdEnd() {
		return designStyleIdEnd;
	}
	public void setDesignStyleIdEnd(String designStyleIdEnd){
		this.designStyleIdEnd = designStyleIdEnd;
	}
	public  String getSch_SpaceArea_() {
		return sch_SpaceArea_;
	}
	public void setSch_SpaceArea_(String sch_SpaceArea_){
		this.sch_SpaceArea_ = sch_SpaceArea_;
	}
	public  String getSch_SpaceArea() {
		return sch_SpaceArea;
	}
	public void setSch_SpaceArea(String sch_SpaceArea){
		this.sch_SpaceArea = sch_SpaceArea;
	}
	public  String getSchSpaceArea_() {
		return schSpaceArea_;
	}
	public void setSchSpaceArea_(String schSpaceArea_){
		this.schSpaceArea_ = schSpaceArea_;
	}
	public  String getSpaceAreaStart() {
		return spaceAreaStart;
	}
	public void setSpaceAreaStart(String spaceAreaStart){
		this.spaceAreaStart = spaceAreaStart;
	}
	public  String getSpaceAreaEnd() {
		return spaceAreaEnd;
	}
	public void setSpaceAreaEnd(String spaceAreaEnd){
		this.spaceAreaEnd = spaceAreaEnd;
	}
	public  String getSch_DesignName_() {
		return sch_DesignName_;
	}
	public void setSch_DesignName_(String sch_DesignName_){
		this.sch_DesignName_ = sch_DesignName_;
	}
	public  String getSch_DesignName() {
		return sch_DesignName;
	}
	public void setSch_DesignName(String sch_DesignName){
		this.sch_DesignName = sch_DesignName;
	}
	public  String getSchDesignName_() {
		return schDesignName_;
	}
	public void setSchDesignName_(String schDesignName_){
		this.schDesignName_ = schDesignName_;
	}
	public  String getDesignNameStart() {
		return designNameStart;
	}
	public void setDesignNameStart(String designNameStart){
		this.designNameStart = designNameStart;
	}
	public  String getDesignNameEnd() {
		return designNameEnd;
	}
	public void setDesignNameEnd(String designNameEnd){
		this.designNameEnd = designNameEnd;
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
	public  String getSch_Description_() {
		return sch_Description_;
	}
	public void setSch_Description_(String sch_Description_){
		this.sch_Description_ = sch_Description_;
	}
	public  String getSch_Description() {
		return sch_Description;
	}
	public void setSch_description(String sch_Description){
		this.sch_Description = sch_Description;
	}
	public  String getSchDescription_() {
		return schDescription_;
	}
	public void setSchDescription_(String schDescription_){
		this.schDescription_ = schDescription_;
	}
	public  String getDescriptionStart() {
		return descriptionStart;
	}
	public void setDescriptionStart(String descriptionStart){
		this.descriptionStart = descriptionStart;
	}
	public  String getDescriptionEnd() {
		return descriptionEnd;
	}
	public void setDescriptionEnd(String descriptionEnd){
		this.descriptionEnd = descriptionEnd;
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
