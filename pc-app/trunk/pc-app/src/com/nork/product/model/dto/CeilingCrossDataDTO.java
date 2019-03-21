package com.nork.product.model.dto;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 7:06 2018/7/12 0012
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import java.io.Serializable;

/**
 * @Title: 截面天花筛选接受数据
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/7/12 0012PM 7:06
 */

public class CeilingCrossDataDTO implements Serializable{

    private String styleCode; //款式CODE


    private Integer applyArea; //适用面积

    private String areaSign; //区域信息


    public String getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
    }

    public Integer getApplyArea() {
        return applyArea;
    }

    public void setApplyArea(Integer applyArea) {
        this.applyArea = applyArea;
    }

    public String getAreaSign() {
        return areaSign;
    }

    public void setAreaSign(String areaSign) {
        this.areaSign = areaSign;
    }
}
