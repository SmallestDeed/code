package com.sandu.user.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;


public class UserLoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String appKey;

    private String userKey;

    private String token;

    private Integer userType;

    private String serverUrl;

    private String resourcesUrl;

    private String siteName;

    private Double balanceAmount;

    private Double consumAmount;

    private Integer mediaType;

    private String cryptKey;

    private Set<String> permissions;
    private Map<String, Set<String>> queryFields;
    
    private Integer passwordUpdateFlag;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    public String getCryptKey() {
        return cryptKey;
    }

    public void setCryptKey(String cryptKey) {
        this.cryptKey = cryptKey;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Map<String, Set<String>> getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(Map<String, Set<String>> queryFields) {
        this.queryFields = queryFields;
    }

    public Integer getPasswordUpdateFlag() {
        return passwordUpdateFlag;
    }

    public void setPasswordUpdateFlag(Integer passwordUpdateFlag) {
        this.passwordUpdateFlag = passwordUpdateFlag;
    }

    @Override
    public String toString() {
        return "UserLoginVO{" +
                "id=" + id +
                ", appKey='" + appKey + '\'' +
                ", userKey='" + userKey + '\'' +
                ", token='" + token + '\'' +
                ", userType=" + userType +
                ", serverUrl='" + serverUrl + '\'' +
                ", resourcesUrl='" + resourcesUrl + '\'' +
                ", siteName='" + siteName + '\'' +
                ", balanceAmount=" + balanceAmount +
                ", consumAmount=" + consumAmount +
                ", mediaType=" + mediaType +
                ", cryptKey='" + cryptKey + '\'' +
                ", permissions=" + permissions +
                ", queryFields=" + queryFields +
                ", passwordUpdateFlag=" + passwordUpdateFlag +
                '}';
    }
}
