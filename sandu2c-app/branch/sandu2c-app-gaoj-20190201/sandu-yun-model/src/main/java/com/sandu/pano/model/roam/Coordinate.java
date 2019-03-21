package com.sandu.pano.model.roam;

import java.io.Serializable;

public class Coordinate implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String ath;

    private String atv;

    private int  targetFieldName;

    private String  targetFieldNamePath;

    public int getTargetFieldName() {
        return targetFieldName;
    }

    public void setTargetFieldName(int targetFieldName) {
        this.targetFieldName = targetFieldName;
    }

    public String getTargetFieldNamePath() {
        return targetFieldNamePath;
    }

    public void setTargetFieldNamePath(String targetFieldNamePath) {
        this.targetFieldNamePath = targetFieldNamePath;
    }

    public String getAth() {
        return ath;
    }

    public void setAth(String ath) {
        this.ath = ath;
    }

    public String getAtv() {
        return atv;
    }

    public void setAtv(String atv) {
        this.atv = atv;
    }


}
