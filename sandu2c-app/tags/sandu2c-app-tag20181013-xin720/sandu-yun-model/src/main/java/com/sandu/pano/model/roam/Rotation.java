package com.sandu.pano.model.roam;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/11.
 */
public class Rotation implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double x;

    private double y;

    private double z;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
