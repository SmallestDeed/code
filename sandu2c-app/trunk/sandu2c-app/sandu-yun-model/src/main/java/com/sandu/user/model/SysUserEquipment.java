package com.sandu.user.model;

import com.sandu.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;

public class SysUserEquipment extends Mapper implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    /***/
    Integer id;
    /**
     * 用户id
     */
    Integer userId;
    /**
     * 设备号
     */
    String userImei;
    /**
     * 设备类型
     */
    String equipmentType;

    /**
     * 创建时间
     */
    Date gmtCreate;

    /**
     * 修改时间
     */
    Date gmtModified;
    /**
     * 是否删除
     */
    Integer isDeleted;


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

    public String getUserImei() {
        return userImei;
    }

    public void setUserImei(String userImei) {
        this.userImei = userImei;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
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


}
