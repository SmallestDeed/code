package com.nork.pano.model.qrcode;

import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/10/8 0008 16:45
 * @Modified By
 */
public class QrInfo implements Serializable{
    private static final long serialVersionUID = 8671527231936224865L;

    private Integer i;
    private Integer t;
    private Integer b;

    public QrInfo(int i,int t ,int b){
        this.i = i;
        this.t = t;
        this.b = b;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getT() {
        return t;
    }

    public void setT(Integer t) {
        this.t = t;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }
}
