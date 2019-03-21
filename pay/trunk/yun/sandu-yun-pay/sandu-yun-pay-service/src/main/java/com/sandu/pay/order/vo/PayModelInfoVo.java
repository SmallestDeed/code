package com.sandu.pay.order.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询用户的套餐信息
 */
public class PayModelInfoVo implements Serializable {
    /**
     * 付款方式配置关联表id
     */
    private Integer payModelGroupRefId;
    /**
     * 付款方式表id
     */
    private Integer payModelConfigId;
    /**
     * 最大的截止时间
     */
    private Date maxExpiryTime;

    public Integer getPayModelGroupRefId() {
        return payModelGroupRefId;
    }

    public void setPayModelGroupRefId(Integer payModelGroupRefId) {
        this.payModelGroupRefId = payModelGroupRefId;
    }

    public Integer getPayModelConfigId() {
        return payModelConfigId;
    }

    public void setPayModelConfigId(Integer payModelConfigId) {
        this.payModelConfigId = payModelConfigId;
    }

    public Date getMaxExpiryTime() {
        return maxExpiryTime;
    }

    public void setMaxExpiryTime(Date maxExpiryTime) {
        this.maxExpiryTime = maxExpiryTime;
    }

    @Override
    public String toString() {
        return "PayModelInfoVo{" +
                "payModelGroupRefId=" + payModelGroupRefId +
                ", payModelConfigId=" + payModelConfigId +
                ", maxExpiryTime=" + maxExpiryTime +
                '}';
    }
}
