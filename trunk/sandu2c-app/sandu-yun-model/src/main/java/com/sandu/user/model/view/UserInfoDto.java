package com.sandu.user.model.view;

import java.io.Serializable;

public class UserInfoDto implements Serializable {
	private static final long serialVersionUID = 3441302014500842659L;
	private String mobile;
	private String password;
	private String newPassword;
	private String code;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
