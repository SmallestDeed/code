package com.sandu.im.common.bo;

public class HistoryMessageBo {
	
	public static final Integer DIRECTION_SEND=1;
	public static final Integer DIRECTION_RECEIVE=2;
	private String fromUserName;
	private String toUserName;
	private String msgBody;
	private String time;
	private Integer msgType;
	/**
	 * 1:表示发送消息
	 * 2:表示接收消息
	 */
	private Integer direction;

	private String id;

	private Object obj;
	
	
	public HistoryMessageBo(String fromUserName, String toUserName, String msgBody, String time, Integer direction,String id,Integer msgType) {
		this.fromUserName = fromUserName;
		this.toUserName = toUserName;
		this.msgBody = msgBody;
		this.time = time;
		this.direction = direction;
		this.id = id;
		this.msgType = msgType;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		toUserName = toUserName;
	}
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	public Integer getDirection() {
		return direction;
	}
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
