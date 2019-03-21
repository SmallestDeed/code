package com.nork.product.model.search;

import java.io.Serializable;
import java.util.Date;

import com.nork.product.model.AuthorizedConfig;

/**   
 * @Title: AuthorizedConfigSearch.java 
 * @Package com.nork.product.model
 * @Description:产品-授权配置查询对象
 * @createAuthor pandajun 
 * @CreateDate 2016-04-27 14:07:34
 * @version V1.0   
 */
public class AuthorizedConfigSearch extends  AuthorizedConfig implements Serializable{
private static final long serialVersionUID = 1L;
	/**  序列号-模糊查询  **/
	private String sch_AuthorizedCode_;
	/**  序列号-左模糊查询  **/
	private String sch_AuthorizedCode;
	/**  序列号-右模糊查询  **/
	private String schAuthorizedCode_;
	/**  序列号-区间查询-开始字符串  **/
	private String authorizedCodeStart;
	/**  序列号-区间查询-结束字符串  **/
	private String authorizedCodeEnd;
	/**  密码-模糊查询  **/
	private String sch_Password_;
	/**  密码-左模糊查询  **/
	private String sch_Password;
	/**  密码-右模糊查询  **/
	private String schPassword_;
	/**  密码-区间查询-开始字符串  **/
	private String passwordStart;
	/**  密码-区间查询-结束字符串  **/
	private String passwordEnd;
	/**  企业ID-模糊查询  **/
	private String sch_CompanyId_;
	/**  企业ID-左模糊查询  **/
	private String sch_CompanyId;
	/**  企业ID-右模糊查询  **/
	private String schCompanyId_;
	/**  企业ID-区间查询-开始字符串  **/
	private String companyIdStart;
	/**  企业ID-区间查询-结束字符串  **/
	private String companyIdEnd;
	/**  品牌ID-模糊查询  **/
	private String sch_BrandIds_;
	/**  品牌ID-左模糊查询  **/
	private String sch_BrandIds;
	/**  品牌ID-右模糊查询  **/
	private String schBrandIds_;
	/**  品牌ID-区间查询-开始字符串  **/
	private String brandIdsStart;
	/**  品牌ID-区间查询-结束字符串  **/
	private String brandIdsEnd;
	/**  大分类-模糊查询  **/
	private String sch_BigType_;
	/**  大分类-左模糊查询  **/
	private String sch_BigType;
	/**  大分类-右模糊查询  **/
	private String schBigType_;
	/**  大分类-区间查询-开始字符串  **/
	private String bigTypeStart;
	/**  大分类-区间查询-结束字符串  **/
	private String bigTypeEnd;
	/**  小分类-模糊查询  **/
	private String sch_SmallType_;
	/**  小分类-左模糊查询  **/
	private String sch_SmallType;
	/**  小分类-右模糊查询  **/
	private String schSmallType_;
	/**  小分类-区间查询-开始字符串  **/
	private String smallTypeStart;
	/**  小分类-区间查询-结束字符串  **/
	private String smallTypeEnd;
	/**  小分类ids-模糊查询  **/
	private String sch_SmallTypeIds_;
	/**  小分类ids-左模糊查询  **/
	private String sch_SmallTypeIds;
	/**  小分类ids-右模糊查询  **/
	private String schSmallTypeIds_;
	/**  小分类ids-区间查询-开始字符串  **/
	private String smallTypeIdsStart;
	/**  小分类ids-区间查询-结束字符串  **/
	private String smallTypeIdsEnd;
	/**  产品IDS-模糊查询  **/
	private String sch_ProductIds_;
	/**  产品IDS-左模糊查询  **/
	private String sch_ProductIds;
	/**  产品IDS-右模糊查询  **/
	private String schProductIds_;
	/**  产品IDS-区间查询-开始字符串  **/
	private String productIdsStart;
	/**  产品IDS-区间查询-结束字符串  **/
	private String productIdsEnd;
	/**  终端IMEI-模糊查询  **/
	private String sch_TerminalImei_;
	/**  终端IMEI-左模糊查询  **/
	private String sch_TerminalImei;
	/**  终端IMEI-右模糊查询  **/
	private String schTerminalImei_;
	/**  终端IMEI-区间查询-开始字符串  **/
	private String terminalImeiStart;
	/**  终端IMEI-区间查询-结束字符串  **/
	private String terminalImeiEnd;
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

	public  String getSch_AuthorizedCode_() {
		return sch_AuthorizedCode_;
	}
	public void setSch_AuthorizedCode_(String sch_AuthorizedCode_){
		this.sch_AuthorizedCode_ = sch_AuthorizedCode_;
	}
	public  String getSch_AuthorizedCode() {
		return sch_AuthorizedCode;
	}
	public void setSch_AuthorizedCode(String sch_AuthorizedCode){
		this.sch_AuthorizedCode = sch_AuthorizedCode;
	}
	public  String getSchAuthorizedCode_() {
		return schAuthorizedCode_;
	}
	public void setSchAuthorizedCode_(String schAuthorizedCode_){
		this.schAuthorizedCode_ = schAuthorizedCode_;
	}
	public  String getAuthorizedCodeStart() {
		return authorizedCodeStart;
	}
	public void setAuthorizedCodeStart(String authorizedCodeStart){
		this.authorizedCodeStart = authorizedCodeStart;
	}
	public  String getAuthorizedCodeEnd() {
		return authorizedCodeEnd;
	}
	public void setAuthorizedCodeEnd(String authorizedCodeEnd){
		this.authorizedCodeEnd = authorizedCodeEnd;
	}
	public  String getSch_Password_() {
		return sch_Password_;
	}
	public void setSch_Password_(String sch_Password_){
		this.sch_Password_ = sch_Password_;
	}
	public  String getSch_Password() {
		return sch_Password;
	}
	public void setSch_Password(String sch_Password){
		this.sch_Password = sch_Password;
	}
	public  String getSchPassword_() {
		return schPassword_;
	}
	public void setSchPassword_(String schPassword_){
		this.schPassword_ = schPassword_;
	}
	public  String getPasswordStart() {
		return passwordStart;
	}
	public void setPasswordStart(String passwordStart){
		this.passwordStart = passwordStart;
	}
	public  String getPasswordEnd() {
		return passwordEnd;
	}
	public void setPasswordEnd(String passwordEnd){
		this.passwordEnd = passwordEnd;
	}
	public  String getSch_CompanyId_() {
		return sch_CompanyId_;
	}
	public void setSch_CompanyId_(String sch_CompanyId_){
		this.sch_CompanyId_ = sch_CompanyId_;
	}
	public  String getSch_CompanyId() {
		return sch_CompanyId;
	}
	public void setSch_CompanyId(String sch_CompanyId){
		this.sch_CompanyId = sch_CompanyId;
	}
	public  String getSchCompanyId_() {
		return schCompanyId_;
	}
	public void setSchCompanyId_(String schCompanyId_){
		this.schCompanyId_ = schCompanyId_;
	}
	public  String getCompanyIdStart() {
		return companyIdStart;
	}
	public void setCompanyIdStart(String companyIdStart){
		this.companyIdStart = companyIdStart;
	}
	public  String getCompanyIdEnd() {
		return companyIdEnd;
	}
	public void setCompanyIdEnd(String companyIdEnd){
		this.companyIdEnd = companyIdEnd;
	}
	public  String getSch_BrandIds_() {
		return sch_BrandIds_;
	}
	public void setSch_BrandIds_(String sch_BrandIds_){
		this.sch_BrandIds_ = sch_BrandIds_;
	}
	public  String getSch_BrandIds() {
		return sch_BrandIds;
	}
	public void setSch_BrandIds(String sch_BrandIds){
		this.sch_BrandIds = sch_BrandIds;
	}
	public  String getSchBrandIds_() {
		return schBrandIds_;
	}
	public void setSchBrandIds_(String schBrandIds_){
		this.schBrandIds_ = schBrandIds_;
	}
	public  String getBrandIdsStart() {
		return brandIdsStart;
	}
	public void setBrandIdsStart(String brandIdsStart){
		this.brandIdsStart = brandIdsStart;
	}
	public  String getBrandIdsEnd() {
		return brandIdsEnd;
	}
	public void setBrandIdsEnd(String brandIdsEnd){
		this.brandIdsEnd = brandIdsEnd;
	}
	public  String getSch_BigType_() {
		return sch_BigType_;
	}
	public void setSch_BigType_(String sch_BigType_){
		this.sch_BigType_ = sch_BigType_;
	}
	public  String getSch_BigType() {
		return sch_BigType;
	}
	public void setSch_BigType(String sch_BigType){
		this.sch_BigType = sch_BigType;
	}
	public  String getSchBigType_() {
		return schBigType_;
	}
	public void setSchBigType_(String schBigType_){
		this.schBigType_ = schBigType_;
	}
	public  String getBigTypeStart() {
		return bigTypeStart;
	}
	public void setBigTypeStart(String bigTypeStart){
		this.bigTypeStart = bigTypeStart;
	}
	public  String getBigTypeEnd() {
		return bigTypeEnd;
	}
	public void setBigTypeEnd(String bigTypeEnd){
		this.bigTypeEnd = bigTypeEnd;
	}
	public  String getSch_SmallType_() {
		return sch_SmallType_;
	}
	public void setSch_SmallType_(String sch_SmallType_){
		this.sch_SmallType_ = sch_SmallType_;
	}
	public  String getSch_SmallType() {
		return sch_SmallType;
	}
	public void setSch_SmallType(String sch_SmallType){
		this.sch_SmallType = sch_SmallType;
	}
	public  String getSchSmallType_() {
		return schSmallType_;
	}
	public void setSchSmallType_(String schSmallType_){
		this.schSmallType_ = schSmallType_;
	}
	public  String getSmallTypeStart() {
		return smallTypeStart;
	}
	public void setSmallTypeStart(String smallTypeStart){
		this.smallTypeStart = smallTypeStart;
	}
	public  String getSmallTypeEnd() {
		return smallTypeEnd;
	}
	public void setSmallTypeEnd(String smallTypeEnd){
		this.smallTypeEnd = smallTypeEnd;
	}
	public  String getSch_SmallTypeIds_() {
		return sch_SmallTypeIds_;
	}
	public void setSch_SmallTypeIds_(String sch_SmallTypeIds_){
		this.sch_SmallTypeIds_ = sch_SmallTypeIds_;
	}
	public  String getSch_SmallTypeIds() {
		return sch_SmallTypeIds;
	}
	public void setSch_SmallTypeIds(String sch_SmallTypeIds){
		this.sch_SmallTypeIds = sch_SmallTypeIds;
	}
	public  String getSchSmallTypeIds_() {
		return schSmallTypeIds_;
	}
	public void setSchSmallTypeIds_(String schSmallTypeIds_){
		this.schSmallTypeIds_ = schSmallTypeIds_;
	}
	public  String getSmallTypeIdsStart() {
		return smallTypeIdsStart;
	}
	public void setSmallTypeIdsStart(String smallTypeIdsStart){
		this.smallTypeIdsStart = smallTypeIdsStart;
	}
	public  String getSmallTypeIdsEnd() {
		return smallTypeIdsEnd;
	}
	public void setSmallTypeIdsEnd(String smallTypeIdsEnd){
		this.smallTypeIdsEnd = smallTypeIdsEnd;
	}
	public  String getSch_ProductIds_() {
		return sch_ProductIds_;
	}
	public void setSch_ProductIds_(String sch_ProductIds_){
		this.sch_ProductIds_ = sch_ProductIds_;
	}
	public  String getSch_ProductIds() {
		return sch_ProductIds;
	}
	public void setSch_ProductIds(String sch_ProductIds){
		this.sch_ProductIds = sch_ProductIds;
	}
	public  String getSchProductIds_() {
		return schProductIds_;
	}
	public void setSchProductIds_(String schProductIds_){
		this.schProductIds_ = schProductIds_;
	}
	public  String getProductIdsStart() {
		return productIdsStart;
	}
	public void setProductIdsStart(String productIdsStart){
		this.productIdsStart = productIdsStart;
	}
	public  String getProductIdsEnd() {
		return productIdsEnd;
	}
	public void setProductIdsEnd(String productIdsEnd){
		this.productIdsEnd = productIdsEnd;
	}
	public  String getSch_TerminalImei_() {
		return sch_TerminalImei_;
	}
	public void setSch_TerminalImei_(String sch_TerminalImei_){
		this.sch_TerminalImei_ = sch_TerminalImei_;
	}
	public  String getSch_TerminalImei() {
		return sch_TerminalImei;
	}
	public void setSch_TerminalImei(String sch_TerminalImei){
		this.sch_TerminalImei = sch_TerminalImei;
	}
	public  String getSchTerminalImei_() {
		return schTerminalImei_;
	}
	public void setSchTerminalImei_(String schTerminalImei_){
		this.schTerminalImei_ = schTerminalImei_;
	}
	public  String getTerminalImeiStart() {
		return terminalImeiStart;
	}
	public void setTerminalImeiStart(String terminalImeiStart){
		this.terminalImeiStart = terminalImeiStart;
	}
	public  String getTerminalImeiEnd() {
		return terminalImeiEnd;
	}
	public void setTerminalImeiEnd(String terminalImeiEnd){
		this.terminalImeiEnd = terminalImeiEnd;
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
