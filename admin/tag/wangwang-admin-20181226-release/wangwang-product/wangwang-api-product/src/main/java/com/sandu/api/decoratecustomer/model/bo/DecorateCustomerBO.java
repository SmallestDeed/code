package com.sandu.api.decoratecustomer.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DecorateCustomerBO implements Serializable {

	private Integer id;

	@ApiModelProperty("客户名称")
	private String userName;

	@ApiModelProperty("城市名称")
	private String cityName;
	private String cityCode;


	@ApiModelProperty("回访时间")
	private Date revisitTime;

	@ApiModelProperty("备注")
	private String remark;


	@ApiModelProperty("小区")
	private String areaName;
	private String areaCode;

	@ApiModelProperty("详细地址")
	private String address;

	@ApiModelProperty("面积")
	private String houseAcreage;

	@ApiModelProperty("户型")
	private String houseType;

	@ApiModelProperty("预算")
	private Integer decorateBudget;
	private String decorateBudgetStr;

	@ApiModelProperty("装修类型")
	private Integer decorateHouseType;
	private String decorateHouseTypeStr;

	@ApiModelProperty("倾向风格")
	private Integer decorateStyle;
	private String decorateStyleStr;

	@ApiModelProperty("装修方式")
	private Integer decorateType;
	private String decorateTypeStr;



}





