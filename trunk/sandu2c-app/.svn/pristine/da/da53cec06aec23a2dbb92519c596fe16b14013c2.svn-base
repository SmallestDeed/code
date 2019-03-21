package com.sandu.decorateorderscore.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

@Data
public class DecorateOrderQuery implements Serializable {

	/**
	 */
	private static final long serialVersionUID = -6327890663341441267L;

	@ApiModelProperty(value = "页码(从1开始)", required = true)
	@NotNull(message = "页码")
	@Min(value = 1, message = "页码")
	private Long pageNo;

	@ApiModelProperty(value = "每页显示条数", required = true)
	@NotNull(message = "每页显示条数")
	@Min(value = 1, message = "每页显示条数")
	private Integer pageSize;

//	@ApiModelProperty(hidden = true)
//	private Integer status;
	
	@ApiModelProperty(hidden = true)
	private String orderBy;
}