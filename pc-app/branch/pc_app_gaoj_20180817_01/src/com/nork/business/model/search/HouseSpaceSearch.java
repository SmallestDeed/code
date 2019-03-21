package com.nork.business.model.search;

import java.io.Serializable;
import java.util.Date;

import com.nork.business.model.HouseSpace;

/**   
 * @Title: HouseSpaceSearch.java 
 * @Package com.nork.business.model
 * @Description:业务-房型空间定义查询对象
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 11:58:14
 * @version V1.0   
 */
public class HouseSpaceSearch extends  HouseSpace implements Serializable{
private static final long serialVersionUID = 1L;
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
	/**  空间类型-模糊查询  **/
	private String sch_SpaceType_;
	/**  空间类型-左模糊查询  **/
	private String sch_SpaceType;
	/**  空间类型-右模糊查询  **/
	private String schSpaceType_;
	/**  空间类型-区间查询-开始字符串  **/
	private String spaceTypeStart;
	/**  空间类型-区间查询-结束字符串  **/
	private String spaceTypeEnd;
	/**  空间名称-模糊查询  **/
	private String sch_SpaceName_;
	/**  空间名称-左模糊查询  **/
	private String sch_SpaceName;
	/**  空间名称-右模糊查询  **/
	private String schSpaceName_;
	/**  空间名称-区间查询-开始字符串  **/
	private String spaceNameStart;
	/**  空间名称-区间查询-结束字符串  **/
	private String spaceNameEnd;
	/**  是否是主要空间-模糊查询  **/
	private String sch_IsMain_;
	/**  是否是主要空间-左模糊查询  **/
	private String sch_IsMain;
	/**  是否是主要空间-右模糊查询  **/
	private String schIsMain_;
	/**  是否是主要空间-区间查询-开始字符串  **/
	private String isMainStart;
	/**  是否是主要空间-区间查询-结束字符串  **/
	private String isMainEnd;
	/**  空间实际形状-模糊查询  **/
	private String sch_SpaceShape_;
	/**  空间实际形状-左模糊查询  **/
	private String sch_SpaceShape;
	/**  空间实际形状-右模糊查询  **/
	private String schSpaceShape_;
	/**  空间实际形状-区间查询-开始字符串  **/
	private String spaceShapeStart;
	/**  空间实际形状-区间查询-结束字符串  **/
	private String spaceShapeEnd;
	/**  规划空间形状-模糊查询  **/
	private String sch_LogicShape_;
	/**  规划空间形状-左模糊查询  **/
	private String sch_LogicShape;
	/**  规划空间形状-右模糊查询  **/
	private String schLogicShape_;
	/**  规划空间形状-区间查询-开始字符串  **/
	private String logicShapeStart;
	/**  规划空间形状-区间查询-结束字符串  **/
	private String logicShapeEnd;
	/**  占地长度-模糊查询  **/
	private String sch_MaxLength_;
	/**  占地长度-左模糊查询  **/
	private String sch_MaxLength;
	/**  占地长度-右模糊查询  **/
	private String schMaxLength_;
	/**  占地长度-区间查询-开始字符串  **/
	private String maxLengthStart;
	/**  占地长度-区间查询-结束字符串  **/
	private String maxLengthEnd;
	/**  占地宽度-模糊查询  **/
	private String sch_MaxWidth_;
	/**  占地宽度-左模糊查询  **/
	private String sch_MaxWidth;
	/**  占地宽度-右模糊查询  **/
	private String schMaxWidth_;
	/**  占地宽度-区间查询-开始字符串  **/
	private String maxWidthStart;
	/**  占地宽度-区间查询-结束字符串  **/
	private String maxWidthEnd;
	/**  占地面积-模糊查询  **/
	private String sch_MaxAreas_;
	/**  占地面积-左模糊查询  **/
	private String sch_MaxAreas;
	/**  占地面积-右模糊查询  **/
	private String schMaxAreas_;
	/**  占地面积-区间查询-开始字符串  **/
	private String maxAreasStart;
	/**  占地面积-区间查询-结束字符串  **/
	private String maxAreasEnd;
	/**  主体长度-模糊查询  **/
	private String sch_MixLength_;
	/**  主体长度-左模糊查询  **/
	private String sch_MixLength;
	/**  主体长度-右模糊查询  **/
	private String schMixLength_;
	/**  主体长度-区间查询-开始字符串  **/
	private String mixLengthStart;
	/**  主体长度-区间查询-结束字符串  **/
	private String mixLengthEnd;
	/**  主体宽度-模糊查询  **/
	private String sch_MixWidth_;
	/**  主体宽度-左模糊查询  **/
	private String sch_MixWidth;
	/**  主体宽度-右模糊查询  **/
	private String schMixWidth_;
	/**  主体宽度-区间查询-开始字符串  **/
	private String mixWidthStart;
	/**  主体宽度-区间查询-结束字符串  **/
	private String mixWidthEnd;
	/**  主体面积-模糊查询  **/
	private String sch_MixAreas_;
	/**  主体面积-左模糊查询  **/
	private String sch_MixAreas;
	/**  主体面积-右模糊查询  **/
	private String schMixAreas_;
	/**  主体面积-区间查询-开始字符串  **/
	private String mixAreasStart;
	/**  主体面积-区间查询-结束字符串  **/
	private String mixAreasEnd;
	/**  主体高度-模糊查询  **/
	private String sch_MixHigh_;
	/**  主体高度-左模糊查询  **/
	private String sch_MixHigh;
	/**  主体高度-右模糊查询  **/
	private String schMixHigh_;
	/**  主体高度-区间查询-开始字符串  **/
	private String mixHighStart;
	/**  主体高度-区间查询-结束字符串  **/
	private String mixHighEnd;
	/**  空间符号-模糊查询  **/
	private String sch_SpaceSign_;
	/**  空间符号-左模糊查询  **/
	private String sch_SpaceSign;
	/**  空间符号-右模糊查询  **/
	private String schSpaceSign_;
	/**  空间符号-区间查询-开始字符串  **/
	private String spaceSignStart;
	/**  空间符号-区间查询-结束字符串  **/
	private String spaceSignEnd;
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
	/**  字符备用3-模糊查询  **/
	private String sch_Att3_;
	/**  字符备用3-左模糊查询  **/
	private String sch_Att3;
	/**  字符备用3-右模糊查询  **/
	private String schAtt3_;
	/**  字符备用3-区间查询-开始字符串  **/
	private String att3Start;
	/**  字符备用3-区间查询-结束字符串  **/
	private String att3End;
	/**  字符备用4-模糊查询  **/
	private String sch_Att4_;
	/**  字符备用4-左模糊查询  **/
	private String sch_Att4;
	/**  字符备用4-右模糊查询  **/
	private String schAtt4_;
	/**  字符备用4-区间查询-开始字符串  **/
	private String att4Start;
	/**  字符备用4-区间查询-结束字符串  **/
	private String att4End;
	/**  字符备用5-模糊查询  **/
	private String sch_Att5_;
	/**  字符备用5-左模糊查询  **/
	private String sch_Att5;
	/**  字符备用5-右模糊查询  **/
	private String schAtt5_;
	/**  字符备用5-区间查询-开始字符串  **/
	private String att5Start;
	/**  字符备用5-区间查询-结束字符串  **/
	private String att5End;
	/**  字符备用6-模糊查询  **/
	private String sch_Att6_;
	/**  字符备用6-左模糊查询  **/
	private String sch_Att6;
	/**  字符备用6-右模糊查询  **/
	private String schAtt6_;
	/**  字符备用6-区间查询-开始字符串  **/
	private String att6Start;
	/**  字符备用6-区间查询-结束字符串  **/
	private String att6End;
	/**  时间备用1-区间查询-开始时间  **/
	private Date spaceModifiedStart;
	/**  时间备用1-区间查询-结束时间  **/
	private Date spaceModifiedEnd;
	/**  时间备用2-区间查询-开始时间  **/
	private Date dateAtt2Start;
	/**  时间备用2-区间查询-结束时间  **/
	private Date dateAtt2End;
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
	public  String getSch_SpaceType_() {
		return sch_SpaceType_;
	}
	public void setSch_SpaceType_(String sch_SpaceType_){
		this.sch_SpaceType_ = sch_SpaceType_;
	}
	public  String getSch_SpaceType() {
		return sch_SpaceType;
	}
	public void setSch_SpaceType(String sch_SpaceType){
		this.sch_SpaceType = sch_SpaceType;
	}
	public  String getSchSpaceType_() {
		return schSpaceType_;
	}
	public void setSchSpaceType_(String schSpaceType_){
		this.schSpaceType_ = schSpaceType_;
	}
	public  String getSpaceTypeStart() {
		return spaceTypeStart;
	}
	public void setSpaceTypeStart(String spaceTypeStart){
		this.spaceTypeStart = spaceTypeStart;
	}
	public  String getSpaceTypeEnd() {
		return spaceTypeEnd;
	}
	public void setSpaceTypeEnd(String spaceTypeEnd){
		this.spaceTypeEnd = spaceTypeEnd;
	}
	public  String getSch_SpaceName_() {
		return sch_SpaceName_;
	}
	public void setSch_SpaceName_(String sch_SpaceName_){
		this.sch_SpaceName_ = sch_SpaceName_;
	}
	public  String getSch_SpaceName() {
		return sch_SpaceName;
	}
	public void setSch_SpaceName(String sch_SpaceName){
		this.sch_SpaceName = sch_SpaceName;
	}
	public  String getSchSpaceName_() {
		return schSpaceName_;
	}
	public void setSchSpaceName_(String schSpaceName_){
		this.schSpaceName_ = schSpaceName_;
	}
	public  String getSpaceNameStart() {
		return spaceNameStart;
	}
	public void setSpaceNameStart(String spaceNameStart){
		this.spaceNameStart = spaceNameStart;
	}
	public  String getSpaceNameEnd() {
		return spaceNameEnd;
	}
	public void setSpaceNameEnd(String spaceNameEnd){
		this.spaceNameEnd = spaceNameEnd;
	}
	public  String getSch_IsMain_() {
		return sch_IsMain_;
	}
	public void setSch_IsMain_(String sch_IsMain_){
		this.sch_IsMain_ = sch_IsMain_;
	}
	public  String getSch_IsMain() {
		return sch_IsMain;
	}
	public void setSch_IsMain(String sch_IsMain){
		this.sch_IsMain = sch_IsMain;
	}
	public  String getSchIsMain_() {
		return schIsMain_;
	}
	public void setSchIsMain_(String schIsMain_){
		this.schIsMain_ = schIsMain_;
	}
	public  String getIsMainStart() {
		return isMainStart;
	}
	public void setIsMainStart(String isMainStart){
		this.isMainStart = isMainStart;
	}
	public  String getIsMainEnd() {
		return isMainEnd;
	}
	public void setIsMainEnd(String isMainEnd){
		this.isMainEnd = isMainEnd;
	}
	public  String getSch_SpaceShape_() {
		return sch_SpaceShape_;
	}
	public void setSch_SpaceShape_(String sch_SpaceShape_){
		this.sch_SpaceShape_ = sch_SpaceShape_;
	}
	public  String getSch_SpaceShape() {
		return sch_SpaceShape;
	}
	public void setSch_SpaceShape(String sch_SpaceShape){
		this.sch_SpaceShape = sch_SpaceShape;
	}
	public  String getSchSpaceShape_() {
		return schSpaceShape_;
	}
	public void setSchSpaceShape_(String schSpaceShape_){
		this.schSpaceShape_ = schSpaceShape_;
	}
	public  String getSpaceShapeStart() {
		return spaceShapeStart;
	}
	public void setSpaceShapeStart(String spaceShapeStart){
		this.spaceShapeStart = spaceShapeStart;
	}
	public  String getSpaceShapeEnd() {
		return spaceShapeEnd;
	}
	public void setSpaceShapeEnd(String spaceShapeEnd){
		this.spaceShapeEnd = spaceShapeEnd;
	}
	public  String getSch_LogicShape_() {
		return sch_LogicShape_;
	}
	public void setSch_LogicShape_(String sch_LogicShape_){
		this.sch_LogicShape_ = sch_LogicShape_;
	}
	public  String getSch_LogicShape() {
		return sch_LogicShape;
	}
	public void setSch_LogicShape(String sch_LogicShape){
		this.sch_LogicShape = sch_LogicShape;
	}
	public  String getSchLogicShape_() {
		return schLogicShape_;
	}
	public void setSchLogicShape_(String schLogicShape_){
		this.schLogicShape_ = schLogicShape_;
	}
	public  String getLogicShapeStart() {
		return logicShapeStart;
	}
	public void setLogicShapeStart(String logicShapeStart){
		this.logicShapeStart = logicShapeStart;
	}
	public  String getLogicShapeEnd() {
		return logicShapeEnd;
	}
	public void setLogicShapeEnd(String logicShapeEnd){
		this.logicShapeEnd = logicShapeEnd;
	}
	public  String getSch_MaxLength_() {
		return sch_MaxLength_;
	}
	public void setSch_MaxLength_(String sch_MaxLength_){
		this.sch_MaxLength_ = sch_MaxLength_;
	}
	public  String getSch_MaxLength() {
		return sch_MaxLength;
	}
	public void setSch_MaxLength(String sch_MaxLength){
		this.sch_MaxLength = sch_MaxLength;
	}
	public  String getSchMaxLength_() {
		return schMaxLength_;
	}
	public void setSchMaxLength_(String schMaxLength_){
		this.schMaxLength_ = schMaxLength_;
	}
	public  String getMaxLengthStart() {
		return maxLengthStart;
	}
	public void setMaxLengthStart(String maxLengthStart){
		this.maxLengthStart = maxLengthStart;
	}
	public  String getMaxLengthEnd() {
		return maxLengthEnd;
	}
	public void setMaxLengthEnd(String maxLengthEnd){
		this.maxLengthEnd = maxLengthEnd;
	}
	public  String getSch_MaxWidth_() {
		return sch_MaxWidth_;
	}
	public void setSch_MaxWidth_(String sch_MaxWidth_){
		this.sch_MaxWidth_ = sch_MaxWidth_;
	}
	public  String getSch_MaxWidth() {
		return sch_MaxWidth;
	}
	public void setSch_MaxWidth(String sch_MaxWidth){
		this.sch_MaxWidth = sch_MaxWidth;
	}
	public  String getSchMaxWidth_() {
		return schMaxWidth_;
	}
	public void setSchMaxWidth_(String schMaxWidth_){
		this.schMaxWidth_ = schMaxWidth_;
	}
	public  String getMaxWidthStart() {
		return maxWidthStart;
	}
	public void setMaxWidthStart(String maxWidthStart){
		this.maxWidthStart = maxWidthStart;
	}
	public  String getMaxWidthEnd() {
		return maxWidthEnd;
	}
	public void setMaxWidthEnd(String maxWidthEnd){
		this.maxWidthEnd = maxWidthEnd;
	}
	public  String getSch_MaxAreas_() {
		return sch_MaxAreas_;
	}
	public void setSch_MaxAreas_(String sch_MaxAreas_){
		this.sch_MaxAreas_ = sch_MaxAreas_;
	}
	public  String getSch_MaxAreas() {
		return sch_MaxAreas;
	}
	public void setSch_MaxAreas(String sch_MaxAreas){
		this.sch_MaxAreas = sch_MaxAreas;
	}
	public  String getSchMaxAreas_() {
		return schMaxAreas_;
	}
	public void setSchMaxAreas_(String schMaxAreas_){
		this.schMaxAreas_ = schMaxAreas_;
	}
	public  String getMaxAreasStart() {
		return maxAreasStart;
	}
	public void setMaxAreasStart(String maxAreasStart){
		this.maxAreasStart = maxAreasStart;
	}
	public  String getMaxAreasEnd() {
		return maxAreasEnd;
	}
	public void setMaxAreasEnd(String maxAreasEnd){
		this.maxAreasEnd = maxAreasEnd;
	}
	public  String getSch_MixLength_() {
		return sch_MixLength_;
	}
	public void setSch_MixLength_(String sch_MixLength_){
		this.sch_MixLength_ = sch_MixLength_;
	}
	public  String getSch_MixLength() {
		return sch_MixLength;
	}
	public void setSch_MixLength(String sch_MixLength){
		this.sch_MixLength = sch_MixLength;
	}
	public  String getSchMixLength_() {
		return schMixLength_;
	}
	public void setSchMixLength_(String schMixLength_){
		this.schMixLength_ = schMixLength_;
	}
	public  String getMixLengthStart() {
		return mixLengthStart;
	}
	public void setMixLengthStart(String mixLengthStart){
		this.mixLengthStart = mixLengthStart;
	}
	public  String getMixLengthEnd() {
		return mixLengthEnd;
	}
	public void setMixLengthEnd(String mixLengthEnd){
		this.mixLengthEnd = mixLengthEnd;
	}
	public  String getSch_MixWidth_() {
		return sch_MixWidth_;
	}
	public void setSch_MixWidth_(String sch_MixWidth_){
		this.sch_MixWidth_ = sch_MixWidth_;
	}
	public  String getSch_MixWidth() {
		return sch_MixWidth;
	}
	public void setSch_MixWidth(String sch_MixWidth){
		this.sch_MixWidth = sch_MixWidth;
	}
	public  String getSchMixWidth_() {
		return schMixWidth_;
	}
	public void setSchMixWidth_(String schMixWidth_){
		this.schMixWidth_ = schMixWidth_;
	}
	public  String getMixWidthStart() {
		return mixWidthStart;
	}
	public void setMixWidthStart(String mixWidthStart){
		this.mixWidthStart = mixWidthStart;
	}
	public  String getMixWidthEnd() {
		return mixWidthEnd;
	}
	public void setMixWidthEnd(String mixWidthEnd){
		this.mixWidthEnd = mixWidthEnd;
	}
	public  String getSch_MixAreas_() {
		return sch_MixAreas_;
	}
	public void setSch_MixAreas_(String sch_MixAreas_){
		this.sch_MixAreas_ = sch_MixAreas_;
	}
	public  String getSch_MixAreas() {
		return sch_MixAreas;
	}
	public void setSch_MixAreas(String sch_MixAreas){
		this.sch_MixAreas = sch_MixAreas;
	}
	public  String getSchMixAreas_() {
		return schMixAreas_;
	}
	public void setSchMixAreas_(String schMixAreas_){
		this.schMixAreas_ = schMixAreas_;
	}
	public  String getMixAreasStart() {
		return mixAreasStart;
	}
	public void setMixAreasStart(String mixAreasStart){
		this.mixAreasStart = mixAreasStart;
	}
	public  String getMixAreasEnd() {
		return mixAreasEnd;
	}
	public void setMixAreasEnd(String mixAreasEnd){
		this.mixAreasEnd = mixAreasEnd;
	}
	public  String getSch_MixHigh_() {
		return sch_MixHigh_;
	}
	public void setSch_MixHigh_(String sch_MixHigh_){
		this.sch_MixHigh_ = sch_MixHigh_;
	}
	public  String getSch_MixHigh() {
		return sch_MixHigh;
	}
	public void setSch_MixHigh(String sch_MixHigh){
		this.sch_MixHigh = sch_MixHigh;
	}
	public  String getSchMixHigh_() {
		return schMixHigh_;
	}
	public void setSchMixHigh_(String schMixHigh_){
		this.schMixHigh_ = schMixHigh_;
	}
	public  String getMixHighStart() {
		return mixHighStart;
	}
	public void setMixHighStart(String mixHighStart){
		this.mixHighStart = mixHighStart;
	}
	public  String getMixHighEnd() {
		return mixHighEnd;
	}
	public void setMixHighEnd(String mixHighEnd){
		this.mixHighEnd = mixHighEnd;
	}
	public  String getSch_SpaceSign_() {
		return sch_SpaceSign_;
	}
	public void setSch_SpaceSign_(String sch_SpaceSign_){
		this.sch_SpaceSign_ = sch_SpaceSign_;
	}
	public  String getSch_SpaceSign() {
		return sch_SpaceSign;
	}
	public void setSch_SpaceSign(String sch_SpaceSign){
		this.sch_SpaceSign = sch_SpaceSign;
	}
	public  String getSchSpaceSign_() {
		return schSpaceSign_;
	}
	public void setSchSpaceSign_(String schSpaceSign_){
		this.schSpaceSign_ = schSpaceSign_;
	}
	public  String getSpaceSignStart() {
		return spaceSignStart;
	}
	public void setSpaceSignStart(String spaceSignStart){
		this.spaceSignStart = spaceSignStart;
	}
	public  String getSpaceSignEnd() {
		return spaceSignEnd;
	}
	public void setSpaceSignEnd(String spaceSignEnd){
		this.spaceSignEnd = spaceSignEnd;
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
	public  String getSch_Att3_() {
		return sch_Att3_;
	}
	public void setSch_Att3_(String sch_Att3_){
		this.sch_Att3_ = sch_Att3_;
	}
	public  String getSch_Att3() {
		return sch_Att3;
	}
	public void setSch_Att3(String sch_Att3){
		this.sch_Att3 = sch_Att3;
	}
	public  String getSchAtt3_() {
		return schAtt3_;
	}
	public void setSchAtt3_(String schAtt3_){
		this.schAtt3_ = schAtt3_;
	}
	public  String getAtt3Start() {
		return att3Start;
	}
	public void setAtt3Start(String att3Start){
		this.att3Start = att3Start;
	}
	public  String getAtt3End() {
		return att3End;
	}
	public void setAtt3End(String att3End){
		this.att3End = att3End;
	}
	public  String getSch_Att4_() {
		return sch_Att4_;
	}
	public void setSch_Att4_(String sch_Att4_){
		this.sch_Att4_ = sch_Att4_;
	}
	public  String getSch_Att4() {
		return sch_Att4;
	}
	public void setSch_Att4(String sch_Att4){
		this.sch_Att4 = sch_Att4;
	}
	public  String getSchAtt4_() {
		return schAtt4_;
	}
	public void setSchAtt4_(String schAtt4_){
		this.schAtt4_ = schAtt4_;
	}
	public  String getAtt4Start() {
		return att4Start;
	}
	public void setAtt4Start(String att4Start){
		this.att4Start = att4Start;
	}
	public  String getAtt4End() {
		return att4End;
	}
	public void setAtt4End(String att4End){
		this.att4End = att4End;
	}
	public  String getSch_Att5_() {
		return sch_Att5_;
	}
	public void setSch_Att5_(String sch_Att5_){
		this.sch_Att5_ = sch_Att5_;
	}
	public  String getSch_Att5() {
		return sch_Att5;
	}
	public void setSch_Att5(String sch_Att5){
		this.sch_Att5 = sch_Att5;
	}
	public  String getSchAtt5_() {
		return schAtt5_;
	}
	public void setSchAtt5_(String schAtt5_){
		this.schAtt5_ = schAtt5_;
	}
	public  String getAtt5Start() {
		return att5Start;
	}
	public void setAtt5Start(String att5Start){
		this.att5Start = att5Start;
	}
	public  String getAtt5End() {
		return att5End;
	}
	public void setAtt5End(String att5End){
		this.att5End = att5End;
	}
	public  String getSch_Att6_() {
		return sch_Att6_;
	}
	public void setSch_Att6_(String sch_Att6_){
		this.sch_Att6_ = sch_Att6_;
	}
	public  String getSch_Att6() {
		return sch_Att6;
	}
	public void setSch_Att6(String sch_Att6){
		this.sch_Att6 = sch_Att6;
	}
	public  String getSchAtt6_() {
		return schAtt6_;
	}
	public void setSchAtt6_(String schAtt6_){
		this.schAtt6_ = schAtt6_;
	}
	public  String getAtt6Start() {
		return att6Start;
	}
	public void setAtt6Start(String att6Start){
		this.att6Start = att6Start;
	}
	public  String getAtt6End() {
		return att6End;
	}
	public void setAtt6End(String att6End){
		this.att6End = att6End;
	}
	public Date getSpaceModifiedStart() {
		return spaceModifiedStart;
	}
	public void setSpaceModifiedStart(Date spaceModifiedStart) {
		this.spaceModifiedStart = spaceModifiedStart;
	}
	public Date getSpaceModifiedEnd() {
		return spaceModifiedEnd;
	}
	public void setSpaceModifiedEnd(Date spaceModifiedEnd) {
		this.spaceModifiedEnd = spaceModifiedEnd;
	}
	public  Date getDateAtt2Start() {
		return dateAtt2Start;
	}
	public void setDateAtt2Start(Date dateAtt2Start){
		this.dateAtt2Start = dateAtt2Start;
	}
	public  Date getDateAtt2End() {
		return dateAtt2End;
	}
	public void setDateAtt2End(Date dateAtt2End){
		this.dateAtt2End = dateAtt2End;
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
	//空间状态
	public Integer spaceState;
	//户型状态
	public Integer houseState;

	public Integer getSpaceState() {
		return spaceState;
	}
	public void setSpaceState(Integer spaceState) {
		this.spaceState = spaceState;
	}
	public Integer getHouseState() {
		return houseState;
	}
	public void setHouseState(Integer houseState) {
		this.houseState = houseState;
	}

}
