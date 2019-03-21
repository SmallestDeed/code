package com.sandu.user.model.view;

import java.io.Serializable;

public class LoginUserVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 464829831546767209L;

    private Integer id;// 数字主键 s
    private Integer userType;
    private String loginId;// 字符主键
    private String loginName;// 登录名称
    private String loginPhone;// 登录手机号
    private String loginEmail;// 登录手机号
    private String name;// 登录中文名
    private String appKey;
    private String password;
    private String token;
    private String mediaType;
    private Integer groupId;// 组织ID
    private Integer sex;// 性别
    private String picPath;// 头像
    private String brandIds;// 品牌(多个用逗号隔开)

    private String serverUrl;
    private String resourcesUrl;

    private String siteName;

    private String sitekey;

    private String userKey; // the key of cache user in redis

    public Integer getId() {
        return id;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }


    @Override
    public String toString() {
        return "LoginUserVO{" +
                "id=" + id +
                ", userType=" + userType +
                ", loginId='" + loginId + '\'' +
                ", loginName='" + loginName + '\'' +
                ", loginPhone='" + loginPhone + '\'' +
                ", loginEmail='" + loginEmail + '\'' +
                ", name='" + name + '\'' +
                ", appKey='" + appKey + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", groupId=" + groupId +
                ", sex=" + sex +
                ", picPath='" + picPath + '\'' +
                ", brandIds='" + brandIds + '\'' +
                ", serverUrl='" + serverUrl + '\'' +
                ", resourcesUrl='" + resourcesUrl + '\'' +
                ", siteName='" + siteName + '\'' +
                ", sitekey='" + sitekey + '\'' +
                ", userKey='" + userKey + '\'' +
                '}';
    }
}
