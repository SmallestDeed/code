package com.nork.pano.model.roam;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/11.
 */
public class Position implements Serializable{

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

    @Override
    public String toString(){
        return "\"position\":{" +
                "\"x\":" + x +
                ",\"y\":" + y +
                ",\"z\":" + z +
                "}";
    }

    public static void main(String[] args) {
        Position p = new Position();
        p.setX(1);
        p.setY(1);
        p.setZ(1);
        System.out.println(p.toString());
    }
}
