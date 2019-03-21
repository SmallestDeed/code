package com.nork.system.model.bo;

import java.io.Serializable;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 *
 * pc-app
 *
 * @author songjianming@sanduspace.cn <p>
 * 2018年1月27日
 */

public class BaseAreaBO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String areaName;
	private String areaCode;
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
