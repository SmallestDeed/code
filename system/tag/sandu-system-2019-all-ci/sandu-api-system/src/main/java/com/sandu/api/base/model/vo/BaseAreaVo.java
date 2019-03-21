package com.sandu.api.base.model.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

/***
 * 行政区域
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "行政区域", description = "行政区域")
public class BaseAreaVo extends BaseVo<BaseAreaVo> {
	private static final long serialVersionUID = -708159452136055591L;
	private String areaCode;
	private String areaName;



	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
