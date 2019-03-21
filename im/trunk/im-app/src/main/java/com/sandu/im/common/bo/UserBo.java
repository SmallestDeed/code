package com.sandu.im.common.bo;

import org.apache.commons.lang3.StringUtils;

public class UserBo {

	private Long userId;
	private String sessionId;
	private String userName;
	private String userType;
	private String mobile;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUserName() {
		if(StringUtils.isNotBlank(userName)) {
			return userName;
		}
		if(StringUtils.isNotBlank(mobile)) {
			return mobile;
		}
		return "匿名用户";
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getUserNameAndType() {
		if(StringUtils.isNotBlank(this.userName)) {
			return this.userName+"&"+this.userType;
		}
		return this.mobile+"&"+this.userType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
