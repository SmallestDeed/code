package com.sandu.api.pointmall.model;

import java.io.Serializable;
import java.util.Date;

public class UserPointInout implements Serializable {
    private Integer id;
    private Integer buyerId;
    private Integer inOutAmount;
    private Integer endPoint;
    private Integer orderId;
    private String describes;
    private String remark;
    private String sysCode;
    private String creator;
    private Date gmtCreate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getInOutAmount() {
        return inOutAmount;
    }

    public void setInOutAmount(Integer inOutAmount) {
        this.inOutAmount = inOutAmount;
    }

    public Integer getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Integer endPoint) {
        this.endPoint = endPoint;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describe) {
        this.describes = describe;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
