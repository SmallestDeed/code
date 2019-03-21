package com.sandu.api.decoratecustomer.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sandu.commons.LoginUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-10-20 14:27
 */
@Data
public class DecorateCustomerUpdate implements Serializable {


	@ApiModelProperty("客户ID")
	private Integer customerId;

	@ApiModelProperty("装修类型")
	@NotNull
	@Min(value = 1)
	private Integer decorateType;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("回访时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date revisitTime;


	@ApiModelProperty("签约企业")
	private Integer contractCompany;

	@ApiModelProperty("签约价格")
	private String contractPrice;

	@ApiModelProperty(value = "login user info", hidden = true)
	private LoginUser loginUser;
}
