package com.nork.cityunion.model;

import java.io.Serializable;
import java.util.Date;

public class UnionContact implements Serializable {

    /** 主键ID **/
    private Integer id;
    /** 系统code **/
    private String sysCode;
    /** 创建人 **/
    private String creator;
    /** 创建时间 **/
    private Date gmtCreate;
    /** 修改人 **/
    private String modifier;
    /** 修改时间 **/
    private Date gmtModified;
    /** 是否删除 **/
    private Integer isDeleted;
    /** 备注信息 **/
    private String remark;
    /** 用户ID **/
    private Integer userId;
    /** 联系人姓名 **/
    private String name;
    /** 联系人电话 **/
    private String phone;
    /** 头像 **/
    private Integer picId;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    @Override
    public String toString(){
        return "UnionContact{" +
                "id : " + id +
                "userId : " + userId +
                ",name : " + name +
                ",phone : " + phone +
                ",picId : " + picId +
                "}";
    }

}
