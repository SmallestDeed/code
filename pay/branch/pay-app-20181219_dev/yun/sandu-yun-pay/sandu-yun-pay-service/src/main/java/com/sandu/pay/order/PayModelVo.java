package com.sandu.pay.order;

import java.io.Serializable;
import java.util.Date;

/**
 * 付款方式表vo
 */
public class PayModelVo implements Serializable {

    /**
     * 付款方式表id
     */
    private Integer payModelConfigId;

    /**
     * 付款方式业务关联表id
     */
    private Integer payModelGroupRefId;

    /**
     * 适用的用户类型
     */
    private String packageUserType;

    /**
     * 过期时间
     */
    private Date expiryTime;

    public Integer getPayModelConfigId() {
        return payModelConfigId;
    }

    public void setPayModelConfigId(Integer payModelConfigId) {
        this.payModelConfigId = payModelConfigId;
    }

    public Integer getPayModelGroupRefId() {
        return payModelGroupRefId;
    }

    public void setPayModelGroupRefId(Integer payModelGroupRefId) {
        this.payModelGroupRefId = payModelGroupRefId;
    }

    public String getPackageUserType() {
        return packageUserType;
    }

    public void setPackageUserType(String packageUserType) {
        this.packageUserType = packageUserType;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }
}
