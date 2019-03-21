package com.nork.common.model;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

public class LoginUser extends Mapper implements Serializable {

	private static final long serialVersionUID = 2712880546833875417L;
	private Integer id;// 数字主键 s
	private Integer userType;
	private String loginId;// 字符主键
	private String loginName;// 登录名称
	private String loginPhone;// 登录手机号
	private String loginEmail;// 登录手机号
	private String name;// 登录中文名
    private String appKey;
    private String token;
    private String mediaType;
    private Integer groupId;//组织ID
	private Integer sex;//性别
	private String picPath;//头像
	private String brandIds;//品牌(多个用逗号隔开)
	
	private String serverUrl;
	private String resourcesUrl;
	
	private String siteName;
	
	private String sitekey;
	
	private String userKey; // the key of cache user in redis
	/**
	 * 用户余额，单位：分
	 */
	private Double balanceAmount;
	/**
	 * 总计消费金额，单位：分
	 */
	private Double consumAmount;
	
	/**
	 * 角色
	 */
	private Set<String> roles;

	//企业Id（厂商：企业ID；经销商：企业经销商ID非企业ID）
	private Long businessAdministrationId;

	public Long getBusinessAdministrationId() {
		return businessAdministrationId;
	}

	public void setBusinessAdministrationId(Long businessAdministrationId) {
		this.businessAdministrationId = businessAdministrationId;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Double getConsumAmount() {
		return consumAmount;
	}

	public void setConsumAmount(Double consumAmount) {
		this.consumAmount = consumAmount;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getResourcesUrl() {
		return resourcesUrl;
	}

	public void setResourcesUrl(String resourcesUrl) {
		this.resourcesUrl = resourcesUrl;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSitekey() {
		return sitekey;
	}

	public void setSitekey(String sitekey) {
		this.sitekey = sitekey;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
    
	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPhone() {
		return loginPhone;
	}

	public void setLoginPhone(String loginPhone) {
		this.loginPhone = loginPhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(String brandIds) {
		this.brandIds = brandIds;
	}
	
	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
    }
}
