package com.sandu.designplan.vo;

import java.io.Serializable;

/**
 * 设计风格VO
 */
public class DesignPlanStyleVo implements Serializable{

    private static final long serialVersionUID = 836687567359467508L;
    //风格名称
    private String styleName;
    //风格编码
    private Integer styleCode;

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public Integer getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(Integer styleCode) {
        this.styleCode = styleCode;
    }

    @Override
    public String toString() {
        return "DesignPlanStyleVo{" +
                "styleName='" + styleName + '\'' +
                ", styleCode=" + styleCode +
                '}';
    }
}
