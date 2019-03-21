package com.sandu.im.event.entity;

import java.util.Date;

public class ChatMessage implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 消息来源(1:移动端 2:pc端 16:小程序)
	 */
	private Integer fromAppId;
	/**
	 * 发送方用户标识
	 */
	private String fromUserSessionId;
	/**
	 * 发送方用户名
	 */
	private String fromUserName;
	/**
	 * 发送方ip
	 */
	private String fromUserIp;
	/**
	 * 接收方用户标识
	 */
	private String toUserSessionId;
	/**
	 * 接收方用户名
	 */
	private String toUserName;
	/**
	 * 消息内容
	 */
	private String msgBody;
	/**
	 * 消息发送时间
	 */
	private Date sendTime;
	
	/**
	 * 关联实体类型(1:店铺 2:供求关系)
	 */
	private Integer relatedObjType;
	
	/**
	 * 关联实体id
	 */
	private Long relatedObjId;
	
	private String relatedObjOwnerSessionId;

	/**
	 * 消息类型(0.文字消息,1.图片消息,2.单空间方案id,3.全屋方案id,4.户型)
	 */
	private Integer msgType;

	private Object obj;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Integer getFromAppId() {
		return fromAppId;
	}
	public void setFromAppId(Integer fromAppId) {
		this.fromAppId = fromAppId;
	}
	public String getFromUserSessionId() {
		return fromUserSessionId;
	}
	public void setFromUserSessionId(String fromUserSessionId) {
		this.fromUserSessionId = fromUserSessionId;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getFromUserIp() {
		return fromUserIp;
	}
	public void setFromUserIp(String fromUserIp) {
		this.fromUserIp = fromUserIp;
	}
	
	public String getToUserSessionId() {
		return toUserSessionId;
	}
	public void setToUserSessionId(String toUserSessionId) {
		this.toUserSessionId = toUserSessionId;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Long getRelatedObjId() {
		return relatedObjId;
	}
	public void setRelatedObjId(Long relatedObjId) {
		this.relatedObjId = relatedObjId;
	}
	public Integer getRelatedObjType() {
		return relatedObjType;
	}
	public void setRelatedObjType(Integer relatedObjType) {
		this.relatedObjType = relatedObjType;
	}
	public String getRelatedObjOwnerSessionId() {
		return relatedObjOwnerSessionId;
	}
	public void setRelatedObjOwnerSessionId(String relatedObjOwnerSessionId) {
		this.relatedObjOwnerSessionId = relatedObjOwnerSessionId;
	}

	@Override
	public String toString() {
		return "ChatMessage{" +
				"fromAppId=" + fromAppId +
				", fromUserSessionId='" + fromUserSessionId + '\'' +
				", fromUserName='" + fromUserName + '\'' +
				", fromUserIp='" + fromUserIp + '\'' +
				", toUserSessionId='" + toUserSessionId + '\'' +
				", toUserName='" + toUserName + '\'' +
				", msgBody='" + msgBody + '\'' +
				", sendTime=" + sendTime +
				", relatedObjType=" + relatedObjType +
				", relatedObjId=" + relatedObjId +
				", relatedObjOwnerSessionId='" + relatedObjOwnerSessionId + '\'' +
				", msgType=" + msgType +
				'}';
	}
}
