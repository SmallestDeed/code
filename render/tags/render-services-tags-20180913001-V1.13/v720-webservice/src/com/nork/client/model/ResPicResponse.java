package com.nork.client.model;

import java.io.Serializable;

public class ResPicResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String picPath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

}
