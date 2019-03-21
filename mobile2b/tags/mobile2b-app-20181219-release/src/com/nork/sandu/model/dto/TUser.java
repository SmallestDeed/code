package com.nork.sandu.model.dto;

public class TUser {
	private int id;
	private String userName;
	private String mobile;
	private String nickName;
	private String password;
	private int userType;
	private String brandIds;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(String brandIds) {
		this.brandIds = brandIds;
	}

}
