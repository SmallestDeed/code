package com.nork.system.model.small;

import java.io.Serializable;
import java.util.Date;


/**   
 * @Title: BaseMessage.java 
 * @Package com.nork.system.model.small
 * @Description:系统模块-消息表
 * @createAuthor pandajun 
 * @CreateDate 2015-08-13 14:30:45
 * @version V1.0   
 */
public class BaseMessageSmall  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
	/**  用户id  **/
	private Integer userId;
	/**  消息类型  **/
	private Integer messageType;
	/**  业务类型  **/
	private Integer businessTypeId;
	/**  业务对象id  **/
	private Integer businessObjId;
	/**  业务类型类型  **/
	private Integer businessObjType;
	/**  消息内容  **/
	private String content;
	/**  消息状态  **/
	private Integer status;
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
	public  Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType){
		this.messageType = messageType;
	}
	public  Integer getBusinessTypeId() {
		return businessTypeId;
	}
	public void setBusinessTypeId(Integer businessTypeId){
		this.businessTypeId = businessTypeId;
	}
	public  Integer getBusinessObjId() {
		return businessObjId;
	}
	public void setBusinessObjId(Integer businessObjId){
		this.businessObjId = businessObjId;
	}
	public  Integer getBusinessObjType() {
		return businessObjType;
	}
	public void setBusinessObjType(Integer businessObjType){
		this.businessObjType = businessObjType;
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
