package com.nork.onekeydesign.model.unity;

import java.io.Serializable;

public class LinkQua  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private double x;

	private double y;

	private double z;

	private double w;

	private EulerAngles eulerAngles;

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

	public void setW(double w) {
		this.w = w;
	}

	public double getW() {
		return this.w;
	}

	public void setEulerAngles(EulerAngles eulerAngles) {
		this.eulerAngles = eulerAngles;
	}

	public EulerAngles getEulerAngles() {
		return this.eulerAngles;
	}
}
