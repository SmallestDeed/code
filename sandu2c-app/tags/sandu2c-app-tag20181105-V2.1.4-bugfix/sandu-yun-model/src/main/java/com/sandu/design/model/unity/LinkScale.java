package com.sandu.design.model.unity;

import java.io.Serializable;

public class LinkScale implements Serializable {

    private static final long serialVersionUID = 1L;

    private double x;

    private double y;

    private double z;

    private Normalized normalized;

    private double magnitude;

    private double sqrMagnitude;

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getZ() {
        return this.z;
    }

    public void setNormalized(Normalized normalized) {
        this.normalized = normalized;
    }

    public Normalized getNormalized() {
        return this.normalized;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public double getMagnitude() {
        return this.magnitude;
    }

    public void setSqrMagnitude(double sqrMagnitude) {
        this.sqrMagnitude = sqrMagnitude;
    }

    public double getSqrMagnitude() {
        return this.sqrMagnitude;
    }
}
