package com.nork.client.model;

import java.io.Serializable;

public class UserGuidePicResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String picUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

}
