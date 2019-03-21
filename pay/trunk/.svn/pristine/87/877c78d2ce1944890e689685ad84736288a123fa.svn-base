package com.sandu.pay.order.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 套餐详情vo
 */
public class PackageInfoVo implements Serializable {
    /**
     * 用户享受的套餐信息
     */
    private String enjoyPackageInfo;

    /**
     * 剩余时间
     */
    private Integer remainDate;

    /**
     * 1表示有赠送权限3个月免费渲染的权限，2表示有包月权限，3表示有包年权限,4表示用户有免费的权限（包月）
     */
    private Integer state;

    /**
     * 截止时间
     */
    private Date expiryTime;

    /**
     * 最大截止时间
     */
    private Date maxExpiryTime;

    public Date getMaxExpiryTime() {
        return maxExpiryTime;
    }

    public void setMaxExpiryTime(Date maxExpiryTime) {
        this.maxExpiryTime = maxExpiryTime;
    }

    public String getEnjoyPackageInfo() {
        return enjoyPackageInfo;
    }

    public void setEnjoyPackageInfo(String enjoyPackageInfo) {
        this.enjoyPackageInfo = enjoyPackageInfo;
    }

    public Integer getRemainDate() {
        return remainDate;
    }

    public void setRemainDate(Integer remainDate) {
        this.remainDate = remainDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Override
    public String toString() {
        return "PackageInfoVo{" +
                "enjoyPackageInfo='" + enjoyPackageInfo + '\'' +
                ", remainDate=" + remainDate +
                ", state=" + state +
                ", expiryTime=" + expiryTime +
                ", maxExpiryTime=" + maxExpiryTime +
                '}';
    }
}
