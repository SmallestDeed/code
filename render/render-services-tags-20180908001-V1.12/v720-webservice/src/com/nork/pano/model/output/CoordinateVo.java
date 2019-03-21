package com.nork.pano.model.output;

import java.io.Serializable;

/**
 * 720中可行走或者可穿透的热点的坐标
 */
public class CoordinateVo implements Serializable{

    private String atv;
    private String ath;
    /** 跳转目标资源ID **/
    private Integer targetResourceId;
    /** 跳转空间类型 **/
    private String spaceType;
    /** 跳转目标名称 **/
    private String targetResourceName;

    public String getAtv() {
        return atv;
    }

    public void setAtv(String atv) {
        this.atv = atv;
    }

    public String getAth() {
        return ath;
    }

    public void setAth(String ath) {
        this.ath = ath;
    }

    public Integer getTargetResourceId() {
        return targetResourceId;
    }

    public void setTargetResourceId(Integer targetResourceId) {
        this.targetResourceId = targetResourceId;
    }

    public String getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType;
    }

    public String getTargetResourceName() {
        return targetResourceName;
    }

    public void setTargetResourceName(String targetResourceName) {
        this.targetResourceName = targetResourceName;
    }
}
