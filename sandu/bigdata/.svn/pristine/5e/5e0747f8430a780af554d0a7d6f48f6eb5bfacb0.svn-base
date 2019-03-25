package com.sandu.collect.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EventLog {
	
	public static final String ACCESS_URL = "http://bigdatadev1/log.gif";
	
	/**
     * 用户uuid
     */
    public static final String FIELD_NAME_UID = "uid";
    

    /**
     * 客户端时间
     */
    public static final String FIELD_NAME_CLIENT_TIME = "ct";

    
    /**
     * 事件类型
     */
    public static final String FIELD_NAME_EVENT_TYPE = "et";
    
    /**
     * 事件名称
     */
    public static final String FIELD_NAME_EVENT_NAME = "en";

    /**
     * 事件属性(json格式)
     */
    public static final String FIELD_NAME_EVENT_PROPERTY = "ep";
    
    /**
     * 应用id
     */
    public static final String FIELD_NAME_APPID = "appid";

    /**
     * 采集sdk(wx,js,java)
     */
    public static final String FIELD_NAME_SDK = "sdk";
    
    /**
     * SDK版本信息
     */
    public static final String FIELD_NAME_SDK_VERSION = "ver";
	
	public static final String EVENT_TYPE_PV = "pv";
	public static final String EVENT_TYPE_CLICK = "click";
	public static final String EVENT_TYPE_BUY = "buy";
 
	public static final String EVENT_NAME_PV_COMMON = "common_pv";
	public static final String EVENT_NAME_PV_PLANLIST = "plan_list_pv";
	public static final String EVENT_NAME_PV_PLANDETAILS = "plan_details_pv";
	
	public static final String EVENT_NAME_CLICK_REPLACE_RENDER = "replace_render_click";
	public static final String EVENT_NAME_CLICK_DECORATE_RENDER = "decorate_render_click";
	
	
	public static final String SDK = "javaSdk";
	public static final String VERSION = "1.0.0";
	
	    
	/**
	 * 用户uuid
	 */
	private String uid;
	/**
	 * 客户端时间
	 */
	private Long clientTime;
	/**
	 * 事件类型
	 */
	private String eventType;
	/**
	 * 事件名称
	 */
	private String eventName;
	/**
	 * 事件内容
	 */
	private String eventProperty;
	/**
	 * 应用id:如果是小程序,则直接使用小程序appid
	 */
	private String appid;
	
	/**
	 *sdk:wx/js/java 
	 */
	private String sdk;
	/**
	 * sdk版本
	 */
	private String version;
	
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Long getClientTime() {
		return clientTime;
	}
	public void setClientTime(Long clientTime) {
		this.clientTime = clientTime;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventProperty() {
		return eventProperty;
	}
	public void setEventProperty(String eventProperty) {
		this.eventProperty = eventProperty;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSdk() {
		return sdk;
	}
	public void setSdk(String sdk) {
		this.sdk = sdk;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String buildRequestUrl() {
		if(isEmpty(uid)) throw new RuntimeException("uid 不能为空! ==>"+this.toString());
		if(clientTime==null) throw new RuntimeException("uid 不能为空! ==>"+this.toString());
		if(isEmpty(eventType)) throw new RuntimeException("uid 不能为空! ==>"+this.toString());
		if(isEmpty(eventName)) throw new RuntimeException("uid 不能为空! ==>"+this.toString());
		if(isEmpty(eventProperty)) throw new RuntimeException("uid 不能为空! ==>"+this.toString());
		if(isEmpty(appid)) throw new RuntimeException("uid 不能为空! ==>"+this.toString());
		if(isEmpty(sdk)) throw new RuntimeException("uid 不能为空! ==>"+this.toString());
		if(isEmpty(version)) throw new RuntimeException("uid 不能为空! ==>"+this.toString());
		
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(ACCESS_URL).append("?")
			.append(FIELD_NAME_UID).append("=").append(URLEncoder.encode(uid.trim(), "utf-8")).append("&")
			.append(FIELD_NAME_CLIENT_TIME).append("=").append(clientTime).append("&")
			.append(FIELD_NAME_EVENT_TYPE).append("=").append(URLEncoder.encode(eventType.trim(), "utf-8")).append("&")
			.append(FIELD_NAME_EVENT_NAME).append("=").append(URLEncoder.encode(eventName.trim(), "utf-8")).append("&")
			.append(FIELD_NAME_EVENT_PROPERTY).append("=").append(URLEncoder.encode(eventProperty.trim(), "utf-8")).append("&")
			.append(FIELD_NAME_APPID).append("=").append(URLEncoder.encode(appid.trim(), "utf-8")).append("&")
			.append(FIELD_NAME_SDK).append("=").append(URLEncoder.encode(sdk.trim(), "utf-8")).append("&")
			.append(FIELD_NAME_SDK_VERSION).append("=").append(URLEncoder.encode(version.trim(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}
	
	
	
	@Override
	public String toString() {
		return "EventLog [uid=" + uid + ", clientTime=" + clientTime + ", eventType=" + eventType + ", eventName="
				+ eventName + ", eventValue=" + eventProperty + ", appid=" + appid + ", sdk=" + sdk + ", version="
				+ version + "]";
	}
	/**
	 * 判断字符串是否为空，如果为空，返回true。否则返回false。
	 * 
	 * @param value
	 * @return
	 */
	private boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}
}
