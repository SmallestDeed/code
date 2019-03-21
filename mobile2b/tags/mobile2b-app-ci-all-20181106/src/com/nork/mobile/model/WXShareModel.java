package com.nork.mobile.model;

import java.io.Serializable;

public class WXShareModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String appId;
	
	private String appSecret;
	
	private String httpUrl;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	
	
}
