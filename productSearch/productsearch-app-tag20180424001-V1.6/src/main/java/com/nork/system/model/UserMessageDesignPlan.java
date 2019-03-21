package com.nork.system.model;

import java.io.Serializable;
import java.util.Date;

public class UserMessageDesignPlan  implements Serializable{

	private static final long serialVersionUID = 1L;
	 private Integer id;
	/**  发送者id  **/
	private Integer userId;
	/**消息id	 */
	private Integer messageId;
	/**是否已读*/
	private Integer isReaded;
	/**  发送者 **/
	private String sender;
	/**  消息类型  （0为系统消息(系统公告)、1为一般消息(用户之间的消息） **/
	private Integer messageType;
	/**  消息内容  **/
	private String content;
	/**  接收者名字  **/
	private String receiver;
	private Integer receiverId;
	public Integer getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}
	/**  创建时间  **/
	private Date gmtCreate;
	/**  业务对象id （0为被关注、1为关注、2为修改设计图，3为发送消息） **/
	private Integer businessTypeId;
	private Date tomorrow;
	
	
	/**  方案名称  **/
	private String planName;
	private Integer designPlanId;
	private Integer businessObjId;
	public Integer getBusinessObjId() {
		return businessObjId;
	}
	public void setBusinessObjId(Integer businessObjId) {
		this.businessObjId = businessObjId;
	}
	private String userName;
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public Integer getDesignPlanId() {
		return designPlanId;
	}
	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getTomorrow() {
		return tomorrow;
	}
	public void setTomorrow(Date tomorrow) {
		this.tomorrow = tomorrow;
	}
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Integer getIsReaded() {
		return isReaded;
	}
	public void setIsReaded(Integer isReaded) {
		this.isReaded = isReaded;
	}
	public Integer getBusinessTypeId() {
		return businessTypeId;
	}
	public void setBusinessTypeId(Integer businessTypeId) {
		this.businessTypeId = businessTypeId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
}
