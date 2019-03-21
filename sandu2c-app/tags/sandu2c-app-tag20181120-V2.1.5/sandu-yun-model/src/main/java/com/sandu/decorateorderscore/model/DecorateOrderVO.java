package com.sandu.decorateorderscore.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DecorateOrderVO implements Serializable{
	
    /**
	 */
	private static final long serialVersionUID = -6327890663341441267L;

	@ApiModelProperty(value = "城市")
    private String cityName;

	@ApiModelProperty(value = "用户名")
    private String userName;
	
	@ApiModelProperty(value = "房屋面积")
    private String houseAcreage;
	
	@ApiModelProperty(value = "签约企业")
    private String companyName;
	
	@ApiModelProperty(value = "状态")
    private String statusStr;
	
	@ApiModelProperty(value = "手机号码")
    private String mobile;

}