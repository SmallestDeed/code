package com.nork.cityunion.model.vo;

import java.io.Serializable;

public class UnionContactVo implements Serializable{

    /** 联系人ID **/
    private Integer id;
    /** 联系人名称 **/
    private String name;
    /** 联系人电话 **/
    private String phone;
    /** 联系人头像 **/
    private String picPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
