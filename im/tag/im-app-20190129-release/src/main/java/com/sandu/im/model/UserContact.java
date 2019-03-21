package com.sandu.im.model;

import java.util.Date;

public class UserContact {

	/**
	 * uuid
	 */
	private String id;
	
	/**
	 * 用户标识
	 */
	private String userSessionId;
	
	/**
	 * 联系人标识
	 */
	private String contactSessionId;
	
	/**
	 * 创建时间
	 */
	private Date createdDate;
	
	/**
	 * 最后更新时间
	 */
	private Date lastUpdatedDate;
	
	/**
	 * 未读消息数量
	 */
	private Integer unreadMsg;
	
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
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserSessionId() {
		return userSessionId;
	}
	public void setUserSessionId(String userSessionId) {
		this.userSessionId = userSessionId;
	}
	
	public String getContactSessionId() {
		return contactSessionId;
	}
	public void setContactSessionId(String contactSessionId) {
		this.contactSessionId = contactSessionId;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public Integer getUnreadMsg() {
		return unreadMsg;
	}
	public void setUnreadMsg(Integer unreadMsg) {
		this.unreadMsg = unreadMsg;
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
