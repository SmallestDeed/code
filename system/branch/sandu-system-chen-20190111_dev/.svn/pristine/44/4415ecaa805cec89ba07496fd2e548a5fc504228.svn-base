package com.sandu.api.servicepurchase.input;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServicesPurchaseOfficial implements Serializable {
	
	@ApiModelProperty(value = "套餐id", hidden = true)
	private Long id;

	@ApiModelProperty(value = "适用用户", required = true)
	@NotEmpty(message = "适用用户")
	@Size(min = 1, max = 40, message = "请选择适用用户")
	private String userTypeId;

	@ApiModelProperty(value = "时长选择", required = true)
	@NotNull(message = "请选择时长")
	@Min(value = 1, message = "请选择时长")
	private Long priceId;

	@ApiModelProperty(value = "购买数量(1-1000)", required = true)
	@Min(value = 1, message = "购买数量最少是1")
	@Max(value = 999, message = "购买数量不能超过1000")
	private Double purchaseAmount;

	@ApiModelProperty(value = "支付方式(0-支付宝;1-微信;2-其他)", required = true)
	@Min(value = 0, message = "请选择支付方式")
	private String payType;

	@ApiModelProperty("公司id")
	@NotNull(message = "公司id不能为空")
	@Min(value = 1, message = "请输入正确的用户ID")
	private Integer companyId;

	@ApiModelProperty("用户ID")
	@NotNull(message = "用户ID不能为空")
	@Min(value = 1, message = "请输入正确的用户ID")
	private Integer userId;

	@ApiModelProperty("购买来源(1、三度官网，2、三度后台，3、商家后台，4、科创，5、抢工长)")
	@NotNull(message = "购买来源不能为空")
	@Min(value = 0, message = "请输入购买来源")
	private String purchaseSource;
}
