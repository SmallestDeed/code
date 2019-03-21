package com.sandu.web.task.model.search;

import com.sandu.web.task.model.SysTask;

import java.io.Serializable;
import java.util.Date;

/**   
 * @Title: SysTaskSearch.java 
 * @Package com.sandu.task.model
 * @Description:任务-系统任务表查询对象
 * @createAuthor pandajun 
 * @CreateDate 2015-11-18 10:51:21
 * @version V1.0   
 */
public class SysTaskSearch extends SysTask implements Serializable{
private static final long serialVersionUID = 1L;
	/**  业务CODE-模糊查询  **/
	private String sch_BusinessCode_;
	/**  业务CODE-左模糊查询  **/
	private String sch_BusinessCode;
	/**  业务CODE-右模糊查询  **/
	private String schBusinessCode_;
	/**  业务CODE-区间查询-开始字符串  **/
	private String businessCodeStart;
	/**  业务CODE-区间查询-结束字符串  **/
	private String businessCodeEnd;
	/**  业务名称-模糊查询  **/
	private String sch_BusinessName_;
	/**  业务名称-左模糊查询  **/
	private String sch_BusinessName;
	/**  业务名称-右模糊查询  **/
	private String schBusinessName_;
	/**  业务名称-区间查询-开始字符串  **/
	private String businessNameStart;
	/**  业务名称-区间查询-结束字符串  **/
	private String businessNameEnd;
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
	/** 发送渲染指令时间-开始 **/
	private Date instructionTimeStart;
	/** 发送渲染指令时间-结束 **/
	private Date instructionTimeEnd;
	/** 暂停时间 **/
	private Date suspendTime;
	/** 暂停后允许的存在时间，过期会销毁任务的资源文件**/
	private Integer maxAllowLiveTime;

	public  String getSch_BusinessCode_() {
		return sch_BusinessCode_;
	}
	public void setSch_BusinessCode_(String sch_BusinessCode_){
		this.sch_BusinessCode_ = sch_BusinessCode_;
	}
	public  String getSch_BusinessCode() {
		return sch_BusinessCode;
	}
	public void setSch_BusinessCode(String sch_BusinessCode){
		this.sch_BusinessCode = sch_BusinessCode;
	}
	public  String getSchBusinessCode_() {
		return schBusinessCode_;
	}
	public void setSchBusinessCode_(String schBusinessCode_){
		this.schBusinessCode_ = schBusinessCode_;
	}
	public  String getBusinessCodeStart() {
		return businessCodeStart;
	}
	public void setBusinessCodeStart(String businessCodeStart){
		this.businessCodeStart = businessCodeStart;
	}
	public  String getBusinessCodeEnd() {
		return businessCodeEnd;
	}
	public void setBusinessCodeEnd(String businessCodeEnd){
		this.businessCodeEnd = businessCodeEnd;
	}
	public  String getSch_BusinessName_() {
		return sch_BusinessName_;
	}
	public void setSch_BusinessName_(String sch_BusinessName_){
		this.sch_BusinessName_ = sch_BusinessName_;
	}
	public  String getSch_BusinessName() {
		return sch_BusinessName;
	}
	public void setSch_BusinessName(String sch_BusinessName){
		this.sch_BusinessName = sch_BusinessName;
	}
	public  String getSchBusinessName_() {
		return schBusinessName_;
	}
	public void setSchBusinessName_(String schBusinessName_){
		this.schBusinessName_ = schBusinessName_;
	}
	public  String getBusinessNameStart() {
		return businessNameStart;
	}
	public void setBusinessNameStart(String businessNameStart){
		this.businessNameStart = businessNameStart;
	}
	public  String getBusinessNameEnd() {
		return businessNameEnd;
	}
	public void setBusinessNameEnd(String businessNameEnd){
		this.businessNameEnd = businessNameEnd;
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
	public Date getInstructionTimeStart() {
		return instructionTimeStart;
	}
	public void setInstructionTimeStart(Date instructionTimeStart) {
		this.instructionTimeStart = instructionTimeStart;
	}
	public Date getInstructionTimeEnd() {
		return instructionTimeEnd;
	}
	public void setInstructionTimeEnd(Date instructionTimeEnd) {
		this.instructionTimeEnd = instructionTimeEnd;
	}
	public Date getSuspendTime() {
		return suspendTime;
	}
	public void setSuspendTime(Date suspendTime) {
		this.suspendTime = suspendTime;
	}
	public Integer getMaxAllowLiveTime() {
		return maxAllowLiveTime;
	}
	public void setMaxAllowLiveTime(Integer maxAllowLiveTime) {
		this.maxAllowLiveTime = maxAllowLiveTime;
	}
}
