package com.nork.product.model;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:55 2018/7/7 0007
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import java.io.Serializable;

/**
 * @Title: 天花截面数据
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/7/7 0007PM 3:55
 */

public class CeilingCrossSectionData implements Serializable{

    private String applyArea; //适用面积
    private String ceilingInfo; //截面数据
    private String lightInfo; //灯带数据

    public String getLightInfo() {
        return lightInfo;
    }

    public void setLightInfo(String lightInfo) {
        this.lightInfo = lightInfo;
    }

    public String getApplyArea() {
        return applyArea;
    }

    public void setApplyArea(String applyArea) {
        this.applyArea = applyArea;
    }

    public String getCeilingInfo() {
        return ceilingInfo;
    }

    public void setCeilingInfo(String ceilingInfo) {
        this.ceilingInfo = ceilingInfo;
    }
}
