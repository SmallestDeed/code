package com.nork.system.model.search;

import java.io.Serializable;
import java.util.Date;
import com.nork.system.model.SysUserSystemOperationLog;

/**   
 * @Title: SysUserSystemOperationLogSearch.java 
 * @Package com.nork.system.model
 * @Description:系统模块-用户系统操作记录查询对象
 * @createAuthor pandajun 
 * @CreateDate 2017-07-05 19:43:31
 * @version V1.0   
 */
public class SysUserSystemOperationLogSearch extends  SysUserSystemOperationLog implements Serializable{
private static final long serialVersionUID = 1L;
	/**  url地址-模糊查询  **/
	private String sch_OperationUrlAddress_;
	/**  url地址-左模糊查询  **/
	private String sch_OperationUrlAddress;
	/**  url地址-右模糊查询  **/
	private String schOperationUrlAddress_;
	/**  url地址-区间查询-开始字符串  **/
	private String operationUrlAddressStart;
	/**  url地址-区间查询-结束字符串  **/
	private String operationUrlAddressEnd;
	/**  客户端IP-模糊查询  **/
	private String sch_OperationClientIp_;
	/**  客户端IP-左模糊查询  **/
	private String sch_OperationClientIp;
	/**  客户端IP-右模糊查询  **/
	private String schOperationClientIp_;
	/**  客户端IP-区间查询-开始字符串  **/
	private String operationClientIpStart;
	/**  客户端IP-区间查询-结束字符串  **/
	private String operationClientIpEnd;
	/**  服务器IP-模糊查询  **/
	private String sch_OperationServerIp_;
	/**  服务器IP-左模糊查询  **/
	private String sch_OperationServerIp;
	/**  服务器IP-右模糊查询  **/
	private String schOperationServerIp_;
	/**  服务器IP-区间查询-开始字符串  **/
	private String operationServerIpStart;
	/**  服务器IP-区间查询-结束字符串  **/
	private String operationServerIpEnd;
	/**  登录设备-模糊查询  **/
	private String sch_OperationLoginDevice_;
	/**  登录设备-左模糊查询  **/
	private String sch_OperationLoginDevice;
	/**  登录设备-右模糊查询  **/
	private String schOperationLoginDevice_;
	/**  登录设备-区间查询-开始字符串  **/
	private String operationLoginDeviceStart;
	/**  登录设备-区间查询-结束字符串  **/
	private String operationLoginDeviceEnd;
	/**  模块名称-模糊查询  **/
	private String sch_OperationModuleName_;
	/**  模块名称-左模糊查询  **/
	private String sch_OperationModuleName;
	/**  模块名称-右模糊查询  **/
	private String schOperationModuleName_;
	/**  模块名称-区间查询-开始字符串  **/
	private String operationModuleNameStart;
	/**  模块名称-区间查询-结束字符串  **/
	private String operationModuleNameEnd;
	/**  接口耗时-模糊查询  **/
	private String sch_OperationPortTime_;
	/**  接口耗时-左模糊查询  **/
	private String sch_OperationPortTime;
	/**  接口耗时-右模糊查询  **/
	private String schOperationPortTime_;
	/**  接口耗时-区间查询-开始字符串  **/
	private String operationPortTimeStart;
	/**  接口耗时-区间查询-结束字符串  **/
	private String operationPortTimeEnd;
	/**  登录时间-区间查询-开始时间  **/
	private Date operationLoginTimeStart;
	/**  登录时间-区间查询-结束时间  **/
	private Date operationLoginTimeEnd;
	/**  操作业务-模糊查询  **/
	private String sch_OperationBusiness_;
	/**  操作业务-左模糊查询  **/
	private String sch_OperationBusiness;
	/**  操作业务-右模糊查询  **/
	private String schOperationBusiness_;
	/**  操作业务-区间查询-开始字符串  **/
	private String operationBusinessStart;
	/**  操作业务-区间查询-结束字符串  **/
	private String operationBusinessEnd;
	/**  操作系统型号-模糊查询  **/
	private String sch_OperationSystemModel_;
	/**  操作系统型号-左模糊查询  **/
	private String sch_OperationSystemModel;
	/**  操作系统型号-右模糊查询  **/
	private String schOperationSystemModel_;
	/**  操作系统型号-区间查询-开始字符串  **/
	private String operationSystemModelStart;
	/**  操作系统型号-区间查询-结束字符串  **/
	private String operationSystemModelEnd;
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

	public  String getSch_OperationUrlAddress_() {
		return sch_OperationUrlAddress_;
	}
	public void setSch_OperationUrlAddress_(String sch_OperationUrlAddress_){
		this.sch_OperationUrlAddress_ = sch_OperationUrlAddress_;
	}
	public  String getSch_OperationUrlAddress() {
		return sch_OperationUrlAddress;
	}
	public void setSch_OperationUrlAddress(String sch_OperationUrlAddress){
		this.sch_OperationUrlAddress = sch_OperationUrlAddress;
	}
	public  String getSchOperationUrlAddress_() {
		return schOperationUrlAddress_;
	}
	public void setSchOperationUrlAddress_(String schOperationUrlAddress_){
		this.schOperationUrlAddress_ = schOperationUrlAddress_;
	}
	public  String getOperationUrlAddressStart() {
		return operationUrlAddressStart;
	}
	public void setOperationUrlAddressStart(String operationUrlAddressStart){
		this.operationUrlAddressStart = operationUrlAddressStart;
	}
	public  String getOperationUrlAddressEnd() {
		return operationUrlAddressEnd;
	}
	public void setOperationUrlAddressEnd(String operationUrlAddressEnd){
		this.operationUrlAddressEnd = operationUrlAddressEnd;
	}
	public  String getSch_OperationClientIp_() {
		return sch_OperationClientIp_;
	}
	public void setSch_OperationClientIp_(String sch_OperationClientIp_){
		this.sch_OperationClientIp_ = sch_OperationClientIp_;
	}
	public  String getSch_OperationClientIp() {
		return sch_OperationClientIp;
	}
	public void setSch_OperationClientIp(String sch_OperationClientIp){
		this.sch_OperationClientIp = sch_OperationClientIp;
	}
	public  String getSchOperationClientIp_() {
		return schOperationClientIp_;
	}
	public void setSchOperationClientIp_(String schOperationClientIp_){
		this.schOperationClientIp_ = schOperationClientIp_;
	}
	public  String getOperationClientIpStart() {
		return operationClientIpStart;
	}
	public void setOperationClientIpStart(String operationClientIpStart){
		this.operationClientIpStart = operationClientIpStart;
	}
	public  String getOperationClientIpEnd() {
		return operationClientIpEnd;
	}
	public void setOperationClientIpEnd(String operationClientIpEnd){
		this.operationClientIpEnd = operationClientIpEnd;
	}
	public  String getSch_OperationServerIp_() {
		return sch_OperationServerIp_;
	}
	public void setSch_OperationServerIp_(String sch_OperationServerIp_){
		this.sch_OperationServerIp_ = sch_OperationServerIp_;
	}
	public  String getSch_OperationServerIp() {
		return sch_OperationServerIp;
	}
	public void setSch_OperationServerIp(String sch_OperationServerIp){
		this.sch_OperationServerIp = sch_OperationServerIp;
	}
	public  String getSchOperationServerIp_() {
		return schOperationServerIp_;
	}
	public void setSchOperationServerIp_(String schOperationServerIp_){
		this.schOperationServerIp_ = schOperationServerIp_;
	}
	public  String getOperationServerIpStart() {
		return operationServerIpStart;
	}
	public void setOperationServerIpStart(String operationServerIpStart){
		this.operationServerIpStart = operationServerIpStart;
	}
	public  String getOperationServerIpEnd() {
		return operationServerIpEnd;
	}
	public void setOperationServerIpEnd(String operationServerIpEnd){
		this.operationServerIpEnd = operationServerIpEnd;
	}
	public  String getSch_OperationLoginDevice_() {
		return sch_OperationLoginDevice_;
	}
	public void setSch_OperationLoginDevice_(String sch_OperationLoginDevice_){
		this.sch_OperationLoginDevice_ = sch_OperationLoginDevice_;
	}
	public  String getSch_OperationLoginDevice() {
		return sch_OperationLoginDevice;
	}
	public void setSch_OperationLoginDevice(String sch_OperationLoginDevice){
		this.sch_OperationLoginDevice = sch_OperationLoginDevice;
	}
	public  String getSchOperationLoginDevice_() {
		return schOperationLoginDevice_;
	}
	public void setSchOperationLoginDevice_(String schOperationLoginDevice_){
		this.schOperationLoginDevice_ = schOperationLoginDevice_;
	}
	public  String getOperationLoginDeviceStart() {
		return operationLoginDeviceStart;
	}
	public void setOperationLoginDeviceStart(String operationLoginDeviceStart){
		this.operationLoginDeviceStart = operationLoginDeviceStart;
	}
	public  String getOperationLoginDeviceEnd() {
		return operationLoginDeviceEnd;
	}
	public void setOperationLoginDeviceEnd(String operationLoginDeviceEnd){
		this.operationLoginDeviceEnd = operationLoginDeviceEnd;
	}
	public  String getSch_OperationModuleName_() {
		return sch_OperationModuleName_;
	}
	public void setSch_OperationModuleName_(String sch_OperationModuleName_){
		this.sch_OperationModuleName_ = sch_OperationModuleName_;
	}
	public  String getSch_OperationModuleName() {
		return sch_OperationModuleName;
	}
	public void setSch_OperationModuleName(String sch_OperationModuleName){
		this.sch_OperationModuleName = sch_OperationModuleName;
	}
	public  String getSchOperationModuleName_() {
		return schOperationModuleName_;
	}
	public void setSchOperationModuleName_(String schOperationModuleName_){
		this.schOperationModuleName_ = schOperationModuleName_;
	}
	public  String getOperationModuleNameStart() {
		return operationModuleNameStart;
	}
	public void setOperationModuleNameStart(String operationModuleNameStart){
		this.operationModuleNameStart = operationModuleNameStart;
	}
	public  String getOperationModuleNameEnd() {
		return operationModuleNameEnd;
	}
	public void setOperationModuleNameEnd(String operationModuleNameEnd){
		this.operationModuleNameEnd = operationModuleNameEnd;
	}
	public  String getSch_OperationPortTime_() {
		return sch_OperationPortTime_;
	}
	public void setSch_OperationPortTime_(String sch_OperationPortTime_){
		this.sch_OperationPortTime_ = sch_OperationPortTime_;
	}
	public  String getSch_OperationPortTime() {
		return sch_OperationPortTime;
	}
	public void setSch_OperationPortTime(String sch_OperationPortTime){
		this.sch_OperationPortTime = sch_OperationPortTime;
	}
	public  String getSchOperationPortTime_() {
		return schOperationPortTime_;
	}
	public void setSchOperationPortTime_(String schOperationPortTime_){
		this.schOperationPortTime_ = schOperationPortTime_;
	}
	public  String getOperationPortTimeStart() {
		return operationPortTimeStart;
	}
	public void setOperationPortTimeStart(String operationPortTimeStart){
		this.operationPortTimeStart = operationPortTimeStart;
	}
	public  String getOperationPortTimeEnd() {
		return operationPortTimeEnd;
	}
	public void setOperationPortTimeEnd(String operationPortTimeEnd){
		this.operationPortTimeEnd = operationPortTimeEnd;
	}
	public  Date getOperationLoginTimeStart() {
		return operationLoginTimeStart;
	}
	public void setOperationLoginTimeStart(Date operationLoginTimeStart){
		this.operationLoginTimeStart = operationLoginTimeStart;
	}
	public  Date getOperationLoginTimeEnd() {
		return operationLoginTimeEnd;
	}
	public void setOperationLoginTimeEnd(Date operationLoginTimeEnd){
		this.operationLoginTimeEnd = operationLoginTimeEnd;
	}
	public  String getSch_OperationBusiness_() {
		return sch_OperationBusiness_;
	}
	public void setSch_OperationBusiness_(String sch_OperationBusiness_){
		this.sch_OperationBusiness_ = sch_OperationBusiness_;
	}
	public  String getSch_OperationBusiness() {
		return sch_OperationBusiness;
	}
	public void setSch_OperationBusiness(String sch_OperationBusiness){
		this.sch_OperationBusiness = sch_OperationBusiness;
	}
	public  String getSchOperationBusiness_() {
		return schOperationBusiness_;
	}
	public void setSchOperationBusiness_(String schOperationBusiness_){
		this.schOperationBusiness_ = schOperationBusiness_;
	}
	public  String getOperationBusinessStart() {
		return operationBusinessStart;
	}
	public void setOperationBusinessStart(String operationBusinessStart){
		this.operationBusinessStart = operationBusinessStart;
	}
	public  String getOperationBusinessEnd() {
		return operationBusinessEnd;
	}
	public void setOperationBusinessEnd(String operationBusinessEnd){
		this.operationBusinessEnd = operationBusinessEnd;
	}
	public  String getSch_OperationSystemModel_() {
		return sch_OperationSystemModel_;
	}
	public void setSch_OperationSystemModel_(String sch_OperationSystemModel_){
		this.sch_OperationSystemModel_ = sch_OperationSystemModel_;
	}
	public  String getSch_OperationSystemModel() {
		return sch_OperationSystemModel;
	}
	public void setSch_OperationSystemModel(String sch_OperationSystemModel){
		this.sch_OperationSystemModel = sch_OperationSystemModel;
	}
	public  String getSchOperationSystemModel_() {
		return schOperationSystemModel_;
	}
	public void setSchOperationSystemModel_(String schOperationSystemModel_){
		this.schOperationSystemModel_ = schOperationSystemModel_;
	}
	public  String getOperationSystemModelStart() {
		return operationSystemModelStart;
	}
	public void setOperationSystemModelStart(String operationSystemModelStart){
		this.operationSystemModelStart = operationSystemModelStart;
	}
	public  String getOperationSystemModelEnd() {
		return operationSystemModelEnd;
	}
	public void setOperationSystemModelEnd(String operationSystemModelEnd){
		this.operationSystemModelEnd = operationSystemModelEnd;
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
