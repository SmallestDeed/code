package com.sandu.user.model;

import java.io.Serializable;
import java.util.Date;

public class UserPO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1965680230362534415L;

    private Integer id;
    /**
     * 系统编码
     **/
    private String sysCode;
    /**
     * 创建者
     **/
    private String creator;
    /**
     * 创建时间
     **/
    private Date gmtCreate;
    /**
     * 修改人
     **/
    private String modifier;
    /**
     * 修改时间
     **/
    private Date gmtModified;
    /**
     * 是否删除
     **/
    private Integer isDeleted;
    /**
     * 用户名
     **/
    private String userName;
    /**
     * 昵称
     **/
    private String nickName;
    /**
     * 密码
     **/
    private String password;
    /**
     * 邮箱
     **/
    private String email;
    /**
     * 移动
     **/
    private String mobile;
    /**
     * 用户类型
     **/
    private Integer userType;
    /**
     * 性别
     **/
    private Integer sex;
    /**
     * 职业
     **/
    private String job;
    /**
     * 区域
     **/
    private Integer areaId;
    /**
     * 区域 长编码（省市）
     **/
    private String areaLongCode;
    /**
     * 头像id
     **/
    private Integer picId;
    /**
     * 媒介类型
     **/
    private Integer mediaType;
    /**
     * 字符备用1
     **/
    private String appKey;
    /**
     * 字符备用2
     **/
    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaLongCode() {
        return areaLongCode;
    }

    public void setAreaLongCode(String areaLongCode) {
        this.areaLongCode = areaLongCode;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
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


    @Override
    public String toString() {
        return "UserPO{" +
                "id=" + id +
                ", sysCode='" + sysCode + '\'' +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                ", isDeleted=" + isDeleted +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userType=" + userType +
                ", sex=" + sex +
                ", job='" + job + '\'' +
                ", areaId=" + areaId +
                ", areaLongCode='" + areaLongCode + '\'' +
                ", picId=" + picId +
                ", mediaType=" + mediaType +
                ", appKey='" + appKey + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
