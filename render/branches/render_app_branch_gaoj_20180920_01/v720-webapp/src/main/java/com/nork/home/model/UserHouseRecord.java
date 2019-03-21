package com.nork.home.model;

import java.io.Serializable;
import java.util.Date;

public class UserHouseRecord implements Serializable {
    private Integer id;

    private String sysCode;

    private Integer userId;

    private Integer houseId;

    private Integer useCount;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;
    /**户型类型**/
    private String houseCommonCode;
    /**户型名称**/
    private String houseName;
    /**小区名称**/
    private String livingName;

    private Integer platformId;

    private Integer renderType;

    public String getHouseCommonCode() {
        return houseCommonCode;
    }

    public void setHouseCommonCode(String houseCommonCode) {
        this.houseCommonCode = houseCommonCode;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getLivingName() {
        return livingName;
    }

    public void setLivingName(String livingName) {
        this.livingName = livingName;
    }

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
        this.sysCode = sysCode == null ? null : sysCode.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
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

    public Integer getPlatformId()
    {
        return platformId;
    }

    public void setPlatformId(Integer platformId)
    {
        this.platformId = platformId;
    }

    public Integer getRenderType()
    {
        return renderType;
    }

    public void setRenderType(Integer renderType)
    {
        this.renderType = renderType;
    }
}