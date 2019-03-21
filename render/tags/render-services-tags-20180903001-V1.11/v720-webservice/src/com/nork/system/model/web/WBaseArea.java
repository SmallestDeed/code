package com.nork.system.model.web;

import java.io.Serializable;

public class WBaseArea   implements Serializable{  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String areaName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
