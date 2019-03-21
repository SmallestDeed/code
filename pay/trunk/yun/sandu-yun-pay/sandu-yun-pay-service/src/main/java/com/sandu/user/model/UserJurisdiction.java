package com.sandu.user.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户权限详情表对应的实体类
 *
 * @Author yzw
 * @Date 2018/3/20 11:22
 */
public class UserJurisdiction implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 平台id
     */
    private Integer platformId;

    /**
     * 权限状态（1、已开通，2已结束）
     */
    private Integer jurisdictionStatus;

    /**
     * 权限生效时间
     */
    private Date jurisdictionEffectiveTime;
    /**
     * 权限到期时间
     */
    private Date jurisdictionFailureTime;

    /**
     * last操作人id
     */
    private Integer modifierUserId;

    /**
     * 系统编码
     */
    private String sysCode;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否删除：0,未删除；1,已删除
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public UserJurisdiction() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public Integer getJurisdictionStatus() {
        return jurisdictionStatus;
    }

    public void setJurisdictionStatus(Integer jurisdictionStatus) {
        this.jurisdictionStatus = jurisdictionStatus;
    }

    public Date getJurisdictionEffectiveTime() {
        return jurisdictionEffectiveTime;
    }

    public void setJurisdictionEffectiveTime(Date jurisdictionEffectiveTime) {
        this.jurisdictionEffectiveTime = jurisdictionEffectiveTime;
    }

    public Date getJurisdictionFailureTime() {
        return jurisdictionFailureTime;
    }

    public void setJurisdictionFailureTime(Date jurisdictionFailureTime) {
        this.jurisdictionFailureTime = jurisdictionFailureTime;
    }

    public Integer getModifierUserId() {
        return modifierUserId;
    }

    public void setModifierUserId(Integer modifierUserId) {
        this.modifierUserId = modifierUserId;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode == null ? null : sysCode.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
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
        this.modifier = modifier == null ? null : modifier.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}