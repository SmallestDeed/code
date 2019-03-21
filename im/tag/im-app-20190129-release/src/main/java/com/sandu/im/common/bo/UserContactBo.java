package com.sandu.im.common.bo;

public class UserContactBo {
	private String contactSessionId;
	private String contactName;
	private Integer unreadMsg;
	private Integer userType;
	private String lastMsg;
	private String sendTime;
	private Integer msgType;  //消息类型
	
	/**
	 * 关联实体类型(1:店铺 2:供求关系)
	 */
	private Integer relatedObjType;
	
	/**
	 * 关联实体id
	 */
	private Long relatedObjId;
	
	/**
	 * 关联实体的创建人
	 */
	private String relatedObjOwnerSessionId;
	
	/**
	 * 关联实体为联系人所创建
	 * @return
	 */
	public boolean isObjOwnByContact() {
		return contactSessionId.equals(relatedObjOwnerSessionId);
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getContactSessionId() {
		return contactSessionId;
	}
	public void setContactSessionId(String contactSessionId) {
		this.contactSessionId = contactSessionId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public Integer getUnreadMsg() {
		return unreadMsg;
	}
	public void setUnreadMsg(Integer unreadMsg) {
		this.unreadMsg = unreadMsg;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getLastMsg() {
		return lastMsg;
	}
	public void setLastMsg(String lastMsg) {
		this.lastMsg = lastMsg;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getRelatedObjType() {
		return relatedObjType;
	}
	public void setRelatedObjType(Integer relatedObjType) {
		this.relatedObjType = relatedObjType;
	}
	public Long getRelatedObjId() {
		return relatedObjId;
	}
	public void setRelatedObjId(Long relatedObjId) {
		this.relatedObjId = relatedObjId;
	}
	public String getRelatedObjOwnerSessionId() {
		return relatedObjOwnerSessionId;
	}
	public void setRelatedObjOwnerSessionId(String relatedObjOwnerSessionId) {
		this.relatedObjOwnerSessionId = relatedObjOwnerSessionId;
	}
	
	
	
}
