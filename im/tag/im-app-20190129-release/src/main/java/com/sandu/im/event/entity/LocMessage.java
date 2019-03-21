package com.sandu.im.event.entity;

public class LocMessage implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 其他窗口
	 */
	public static final Integer LOC_OHTER_VIEW = 3;
	/**
	 * 店铺聊天窗口
	 */
	public static final Integer LOC_CHAT_VIEW = 1;
	
	/**
	 * 供求聊天窗口
	 */
	public static final Integer LOC_SUPPLY_VIEW = 2;
	
	
	/**
	 * 移动端
	 */
	public static final Integer APP_ID_MOBILE2B = 1;
	/**
	 * pc端
	 */
	public static final Integer APP_ID_PC2B = 2;
	/**
	 * 小程序端
	 */
	public static final Integer APP_ID_MINI_SD = 16;


	/**
	 *pc端同城联盟
	 */
	public static final Integer APP_ID_PC_CITY_UNION = 11;

	/**
	 * 用户当用使用的app(移动端,pc端,小程序端)
	 */
	private Integer appId;
	/**
	 * 用户标识
	 */
	private String userSessionId;
	/**
	 * 位置( 1:表示在店铺聊天窗口, 2:表示在供求关系聊天窗口,3:表示在其他窗口.)
	 */
	private Integer loc; 
	
	/**
	 * 关联实体类型(1:店铺 2:供求关系)(只有loc=1或者loc=2即用户在聊天窗口时才有值)
	 */
	private Integer relatedObjType;
	
	/**
	 * 关联实体id(只有loc=1或者loc=2,即用户在聊天窗口时才有值)
	 */
	private Long relatedObjId;
	
	/**
	 * 联系人标识(只有loc=1或者loc=2,即用户在聊天窗口时才有值)
	 */
	private String contactSessionId; 
	
	
	
	public String getUserSessionId() {
		return userSessionId;
	}
	public void setUserSessionId(String userSessionId) {
		this.userSessionId = userSessionId;
	}
	public Integer getLoc() {
		return loc;
	}
	public void setLoc(Integer loc) {
		this.loc = loc;
	}
	public String getContactSessionId() {
		return contactSessionId;
	}
	public void setContactSessionId(String contactSessionId) {
		this.contactSessionId = contactSessionId;
	}
	
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
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
	@Override
	public String toString() {
		return "LocMessage [appId=" + appId + ", userSessionId=" + userSessionId + ", loc=" + loc + ", relatedObjType="
				+ relatedObjType + ", relatedObjId=" + relatedObjId + ", contactSessionId=" + contactSessionId + "]";
	}
	
	
	
}
