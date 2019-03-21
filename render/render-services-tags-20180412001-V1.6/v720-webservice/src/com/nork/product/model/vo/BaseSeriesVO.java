package com.nork.product.model.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/11 0011.
 */
public class BaseSeriesVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //系列ID
    private Integer seriesId = new Integer(0);
    //系列名称
    private String seriesName;
    //系列绑定的匹配名称
    private String brandName;
    //资源图片地址
    private String picPath;
    //缩略图
    private String picSmallPath;
    //说明
    private String remark;

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicSmallPath() {
        return picSmallPath;
    }

    public void setPicSmallPath(String picSmallPath) {
        this.picSmallPath = picSmallPath;
    }
}
