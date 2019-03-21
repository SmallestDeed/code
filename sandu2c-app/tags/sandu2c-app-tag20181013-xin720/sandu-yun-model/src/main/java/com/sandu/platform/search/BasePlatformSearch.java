package com.sandu.platform.search;

import java.io.Serializable;
import java.util.Date;

import com.sandu.platform.BasePlatform;


/**   
 * @Title: BasePlatformSearch.java 
 * @Package com.nork.platform.model
 * @Description:基础-平台表查询对象
 * @createAuthor pandajun 
 * @CreateDate 2017-12-29 10:16:41
 * @version V1.0   
 */
public class BasePlatformSearch extends  BasePlatform implements Serializable{
private static final long serialVersionUID = 1L;
	/**  平台名称-模糊查询  **/
	private String sch_PlatformName_;
	/**  平台名称-左模糊查询  **/
	private String sch_PlatformName;
	/**  平台名称-右模糊查询  **/
	private String schPlatformName_;
	/**  平台名称-区间查询-开始字符串  **/
	private String platformNameStart;
	/**  平台名称-区间查询-结束字符串  **/
	private String platformNameEnd;
	/**  平台类型(前台/后台)-模糊查询  **/
	private String sch_PlatformType_;
	/**  平台类型(前台/后台)-左模糊查询  **/
	private String sch_PlatformType;
	/**  平台类型(前台/后台)-右模糊查询  **/
	private String schPlatformType_;
	/**  平台类型(前台/后台)-区间查询-开始字符串  **/
	private String platformTypeStart;
	/**  平台类型(前台/后台)-区间查询-结束字符串  **/
	private String platformTypeEnd;
	/**  媒介类型-模糊查询  **/
	private String sch_MediaType_;
	/**  媒介类型-左模糊查询  **/
	private String sch_MediaType;
	/**  媒介类型-右模糊查询  **/
	private String schMediaType_;
	/**  媒介类型-区间查询-开始字符串  **/
	private String mediaTypeStart;
	/**  媒介类型-区间查询-结束字符串  **/
	private String mediaTypeEnd;
	/**  平台编码-模糊查询  **/
	private String sch_PlatformCode_;
	/**  平台编码-左模糊查询  **/
	private String sch_PlatformCode;
	/**  平台编码-右模糊查询  **/
	private String schPlatformCode_;
	/**  平台编码-区间查询-开始字符串  **/
	private String platformCodeStart;
	/**  平台编码-区间查询-结束字符串  **/
	private String platformCodeEnd;
	/**  所属平台分类(2b/2c/sandu)-模糊查询  **/
	private String sch_PlatformBussinessType_;
	/**  所属平台分类(2b/2c/sandu)-左模糊查询  **/
	private String sch_PlatformBussinessType;
	/**  所属平台分类(2b/2c/sandu)-右模糊查询  **/
	private String schPlatformBussinessType_;
	/**  所属平台分类(2b/2c/sandu)-区间查询-开始字符串  **/
	private String platformBussinessTypeStart;
	/**  所属平台分类(2b/2c/sandu)-区间查询-结束字符串  **/
	private String platformBussinessTypeEnd;
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

	public  String getSch_PlatformName_() {
		return sch_PlatformName_;
	}
	public void setSch_PlatformName_(String sch_PlatformName_){
		this.sch_PlatformName_ = sch_PlatformName_;
	}
	public  String getSch_PlatformName() {
		return sch_PlatformName;
	}
	public void setSch_PlatformName(String sch_PlatformName){
		this.sch_PlatformName = sch_PlatformName;
	}
	public  String getSchPlatformName_() {
		return schPlatformName_;
	}
	public void setSchPlatformName_(String schPlatformName_){
		this.schPlatformName_ = schPlatformName_;
	}
	public  String getPlatformNameStart() {
		return platformNameStart;
	}
	public void setPlatformNameStart(String platformNameStart){
		this.platformNameStart = platformNameStart;
	}
	public  String getPlatformNameEnd() {
		return platformNameEnd;
	}
	public void setPlatformNameEnd(String platformNameEnd){
		this.platformNameEnd = platformNameEnd;
	}
	public  String getSch_PlatformType_() {
		return sch_PlatformType_;
	}
	public void setSch_PlatformType_(String sch_PlatformType_){
		this.sch_PlatformType_ = sch_PlatformType_;
	}
	public  String getSch_PlatformType() {
		return sch_PlatformType;
	}
	public void setSch_PlatformType(String sch_PlatformType){
		this.sch_PlatformType = sch_PlatformType;
	}
	public  String getSchPlatformType_() {
		return schPlatformType_;
	}
	public void setSchPlatformType_(String schPlatformType_){
		this.schPlatformType_ = schPlatformType_;
	}
	public  String getPlatformTypeStart() {
		return platformTypeStart;
	}
	public void setPlatformTypeStart(String platformTypeStart){
		this.platformTypeStart = platformTypeStart;
	}
	public  String getPlatformTypeEnd() {
		return platformTypeEnd;
	}
	public void setPlatformTypeEnd(String platformTypeEnd){
		this.platformTypeEnd = platformTypeEnd;
	}
	public  String getSch_MediaType_() {
		return sch_MediaType_;
	}
	public void setSch_MediaType_(String sch_MediaType_){
		this.sch_MediaType_ = sch_MediaType_;
	}
	public  String getSch_MediaType() {
		return sch_MediaType;
	}
	public void setSch_MediaType(String sch_MediaType){
		this.sch_MediaType = sch_MediaType;
	}
	public  String getSchMediaType_() {
		return schMediaType_;
	}
	public void setSchMediaType_(String schMediaType_){
		this.schMediaType_ = schMediaType_;
	}
	public  String getMediaTypeStart() {
		return mediaTypeStart;
	}
	public void setMediaTypeStart(String mediaTypeStart){
		this.mediaTypeStart = mediaTypeStart;
	}
	public  String getMediaTypeEnd() {
		return mediaTypeEnd;
	}
	public void setMediaTypeEnd(String mediaTypeEnd){
		this.mediaTypeEnd = mediaTypeEnd;
	}
	public  String getSch_PlatformCode_() {
		return sch_PlatformCode_;
	}
	public void setSch_PlatformCode_(String sch_PlatformCode_){
		this.sch_PlatformCode_ = sch_PlatformCode_;
	}
	public  String getSch_PlatformCode() {
		return sch_PlatformCode;
	}
	public void setSch_PlatformCode(String sch_PlatformCode){
		this.sch_PlatformCode = sch_PlatformCode;
	}
	public  String getSchPlatformCode_() {
		return schPlatformCode_;
	}
	public void setSchPlatformCode_(String schPlatformCode_){
		this.schPlatformCode_ = schPlatformCode_;
	}
	public  String getPlatformCodeStart() {
		return platformCodeStart;
	}
	public void setPlatformCodeStart(String platformCodeStart){
		this.platformCodeStart = platformCodeStart;
	}
	public  String getPlatformCodeEnd() {
		return platformCodeEnd;
	}
	public void setPlatformCodeEnd(String platformCodeEnd){
		this.platformCodeEnd = platformCodeEnd;
	}
	public  String getSch_PlatformBussinessType_() {
		return sch_PlatformBussinessType_;
	}
	public void setSch_PlatformBussinessType_(String sch_PlatformBussinessType_){
		this.sch_PlatformBussinessType_ = sch_PlatformBussinessType_;
	}
	public  String getSch_PlatformBussinessType() {
		return sch_PlatformBussinessType;
	}
	public void setSch_PlatformBussinessType(String sch_PlatformBussinessType){
		this.sch_PlatformBussinessType = sch_PlatformBussinessType;
	}
	public  String getSchPlatformBussinessType_() {
		return schPlatformBussinessType_;
	}
	public void setSchPlatformBussinessType_(String schPlatformBussinessType_){
		this.schPlatformBussinessType_ = schPlatformBussinessType_;
	}
	public  String getPlatformBussinessTypeStart() {
		return platformBussinessTypeStart;
	}
	public void setPlatformBussinessTypeStart(String platformBussinessTypeStart){
		this.platformBussinessTypeStart = platformBussinessTypeStart;
	}
	public  String getPlatformBussinessTypeEnd() {
		return platformBussinessTypeEnd;
	}
	public void setPlatformBussinessTypeEnd(String platformBussinessTypeEnd){
		this.platformBussinessTypeEnd = platformBussinessTypeEnd;
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

	private Integer platformId;
	private Integer platformIssueState;
	private String platformBussinessType;

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public Integer getPlatformIssueState() {
		return platformIssueState;
	}

	public void setPlatformIssueState(Integer platformIssueState) {
		this.platformIssueState = platformIssueState;
	}

	@Override
	public String getPlatformBussinessType() {
		return platformBussinessType;
	}

	@Override
	public void setPlatformBussinessType(String platformBussinessType) {
		this.platformBussinessType = platformBussinessType;
	}
}
