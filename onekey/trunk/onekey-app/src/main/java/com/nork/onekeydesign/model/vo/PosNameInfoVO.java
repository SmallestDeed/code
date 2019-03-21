package com.nork.onekeydesign.model.vo;

import java.io.Serializable;

/**
 * Created by kono on 2017/12/26 0026.
 */
public class PosNameInfoVO implements Serializable{
    private static final long serialVersionUID = 1L;

    private String bmPosName;

    private String productCode;

    private String isBgWall;

    private String planPosName;

    private String matchLogInfo;

    public String getBmPosName() {
        return bmPosName;
    }

    public void setBmPosName(String bmPosName) {
        this.bmPosName = bmPosName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getIsBgWall() {
        return isBgWall;
    }

    public void setIsBgWall(String isBgWall) {
        this.isBgWall = isBgWall;
    }

    public String getPlanPosName() {
        return planPosName;
    }

    public void setPlanPosName(String planPosName) {
        this.planPosName = planPosName;
    }

    public String getMatchLogInfo() {
        return matchLogInfo;
    }

    public void setMatchLogInfo(String matchLogInfo) {
        this.matchLogInfo = matchLogInfo;
    }
}
