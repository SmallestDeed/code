package com.nork.design.model.search;

import java.io.Serializable;
import java.util.Date;

import com.nork.design.model.DesignPlanComment;

/**   
 * @Title: DesignPlanCommentSearch.java 
 * @Package com.nork.design.model
 * @Description:设计方案-评论表查询对象
 * @createAuthor pandajun 
 * @CreateDate 2015-07-23 16:35:30
 * @version V1.0   
 */
public class DesignPlanCommentSearch extends  DesignPlanComment implements Serializable{
private static final long serialVersionUID = 1L;
	/**  评论内容-模糊查询  **/
	private String sch_Content_;
	/**  评论内容-左模糊查询  **/
	private String sch_Content;
	/**  评论内容-右模糊查询  **/
	private String schContent_;
	/**  评论内容-区间查询-开始字符串  **/
	private String contentStart;
	/**  评论内容-区间查询-结束字符串  **/
	private String contentEnd;
	/**  讨论方案-模糊查询  **/
	private String sch_Discussionprogram_;
	/**  讨论方案-左模糊查询  **/
	private String sch_Discussionprogram;
	/**  讨论方案-右模糊查询  **/
	private String schDiscussionprogram_;
	/**  讨论方案-区间查询-开始字符串  **/
	private String discussionprogramStart;
	/**  讨论方案-区间查询-结束字符串  **/
	private String discussionprogramEnd;
	/**  时间-区间查询-开始时间  **/
	private Date dateStart;
	/**  时间-区间查询-结束时间  **/
	private Date dateEnd;
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
	private String userName;
	private String tomorrow;
	public String getTomorrow() {
		return tomorrow;
	}
	public void setTomorrow(String tomorrow) {
		this.tomorrow = tomorrow;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**  用户名-模糊查询  **/
	private String sch_UserName_;
	/**  用户名-左模糊查询  **/
	private String sch_UserName;
	/**  用户名-右模糊查询  **/
	private String schUserName_;
	/**  用户名-区间查询-开始字符串  **/
	private String userNameStart;
	/**  用户名-区间查询-结束字符串  **/
	private String userNameEnd;

	public String getSch_UserName_() {
		return sch_UserName_;
	}
	public void setSch_UserName_(String sch_UserName_) {
		this.sch_UserName_ = sch_UserName_;
	}
	public String getSch_UserName() {
		return sch_UserName;
	}
	public void setSch_UserName(String sch_UserName) {
		this.sch_UserName = sch_UserName;
	}
	public String getSchUserName_() {
		return schUserName_;
	}
	public void setSchUserName_(String schUserName_) {
		this.schUserName_ = schUserName_;
	}
	public String getUserNameStart() {
		return userNameStart;
	}
	public void setUserNameStart(String userNameStart) {
		this.userNameStart = userNameStart;
	}
	public String getUserNameEnd() {
		return userNameEnd;
	}
	public void setUserNameEnd(String userNameEnd) {
		this.userNameEnd = userNameEnd;
	}
	public  String getSch_Content_() {
		return sch_Content_;
	}
	public void setSch_Content_(String sch_Content_){
		this.sch_Content_ = sch_Content_;
	}
	public  String getSch_Content() {
		return sch_Content;
	}
	public void setSch_Content(String sch_Content){
		this.sch_Content = sch_Content;
	}
	public  String getSchContent_() {
		return schContent_;
	}
	public void setSchContent_(String schContent_){
		this.schContent_ = schContent_;
	}
	public  String getContentStart() {
		return contentStart;
	}
	public void setContentStart(String contentStart){
		this.contentStart = contentStart;
	}
	public  String getContentEnd() {
		return contentEnd;
	}
	public void setContentEnd(String contentEnd){
		this.contentEnd = contentEnd;
	}
	public  String getSch_Discussionprogram_() {
		return sch_Discussionprogram_;
	}
	public void setSch_Discussionprogram_(String sch_Discussionprogram_){
		this.sch_Discussionprogram_ = sch_Discussionprogram_;
	}
	public  String getSch_Discussionprogram() {
		return sch_Discussionprogram;
	}
	public void setSch_Discussionprogram(String sch_Discussionprogram){
		this.sch_Discussionprogram = sch_Discussionprogram;
	}
	public  String getSchDiscussionprogram_() {
		return schDiscussionprogram_;
	}
	public void setSchDiscussionprogram_(String schDiscussionprogram_){
		this.schDiscussionprogram_ = schDiscussionprogram_;
	}
	public  String getDiscussionprogramStart() {
		return discussionprogramStart;
	}
	public void setDiscussionprogramStart(String discussionprogramStart){
		this.discussionprogramStart = discussionprogramStart;
	}
	public  String getDiscussionprogramEnd() {
		return discussionprogramEnd;
	}
	public void setDiscussionprogramEnd(String discussionprogramEnd){
		this.discussionprogramEnd = discussionprogramEnd;
	}
	public  Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart){
		this.dateStart = dateStart;
	}
	public  Date getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd){
		this.dateEnd = dateEnd;
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
