package com.sandu.user.model;

import java.io.Serializable;

/**
 * 用户会话对象
 */
public class UserSO implements Serializable {

    private static final long serialVersionUID = -6655949654446453083L;

    //用户对象会话常量
    public final static String USER_OBJECT_SESSION_CONTANT = "UserInfo";

    //用户ID
    private Integer userId;
    //用户名
    private String userName;
    //用户类型
    private Integer userType;
    //用户电话
    private String userMobile;
    //会话ID
    private String sessionId;
    //用户会话创建时间(用于判断有效时间--[时间戳])
    private long createSessionTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getCreateSessionTime() {
        return createSessionTime;
    }

    public void setCreateSessionTime(long createSessionTime) {
        this.createSessionTime = createSessionTime;
    }

    @Override
    public String toString() {
        return "UserSO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userType=" + userType +
                ", userMobile='" + userMobile + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", createSessionTime='" + createSessionTime + '\'' +
                '}';
    }
}
