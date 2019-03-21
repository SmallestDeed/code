package com.nork.customerservice.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: SysFeedback.java 
 * @Package com.nork.customerservice.model.small
 * @Description:客服中心-问题反馈
 * @createAuthor pandajun 
 * @CreateDate 2016-04-29 15:34:27
 * @version V1.0   
 */
public class SysFeedbackSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  提问人id  **/
	private Integer userId;
	/**  提问人用户名  **/
	private String username;
	/**  问题标题  **/
	private String title;
	/**  问题内容  **/
	private String content;
	/**  问题状态  **/
	private Integer status;
	/**  处理人id  **/
	private Integer chargePersonId;
	/**  处理时间  **/
	private Date handlingTime;
	/**  处理结果  **/
	private String handlingResult;
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

	public  Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public  String getUsername() {
		return username;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public  String getTitle() {
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public  String getContent() {
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public  Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	public  Integer getChargePersonId() {
		return chargePersonId;
	}
	public void setChargePersonId(Integer chargePersonId){
		this.chargePersonId = chargePersonId;
	}
	public  Date getHandlingTime() {
		return handlingTime;
	}
	public void setHandlingTime(Date handlingTime){
		this.handlingTime = handlingTime;
	}
	public  String getHandlingResult() {
		return handlingResult;
	}
	public void setHandlingResult(String handlingResult){
		this.handlingResult = handlingResult;
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


}
