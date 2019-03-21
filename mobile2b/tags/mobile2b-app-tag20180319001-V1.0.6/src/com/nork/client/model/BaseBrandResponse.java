package com.nork.client.model;

import java.io.Serializable;

public class BaseBrandResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String sysCode;
	private String brandName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

}
