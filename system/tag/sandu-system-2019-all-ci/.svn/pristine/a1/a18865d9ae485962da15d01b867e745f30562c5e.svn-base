package com.sandu.api.servicepurchase.input;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 官网试用输入参数
 * @author Sandu
 *
 */
@Data
public class ServicesOfficialTrial implements Serializable {
	
	/**
	 * 
	*/
	private static final long serialVersionUID = 2008885017338853683L;

//	@ApiModelProperty(value = "套餐id")
//	private Long id;
//
//	@ApiModelProperty(value = "套餐价格id")
//	private Long sevicePriceId;
	
	@ApiModelProperty("手机号码")
	@NotNull(message = "手机号码不能为空")
	@Min(value = 1, message = "请输入手机号码")
	private String telephone;
	
	@ApiModelProperty("手机号码验证码")
	@NotNull(message = "手机验证码不能为空")
	@Min(value = 1, message = "请输入手机号码验证码")
	private String telephoneCode;
	

//	@ApiModelProperty("试用渠道来源(1-三度官网;2-三度后台代购;3-商家后台;4-科创;5-抢工长)")
//	@NotNull(message = "试用渠道不能为空")
//	@Min(value = 0, message = "请输入试用渠道来源")
//	private String purchaseSource;
//	
//	@ApiModelProperty("业务类型(0-购买;1-续费;2-试用;3-代购)")
//	@NotNull(message = "业务类型不能为空")
//	@Min(value = 0, message = "请输入业务类型")
//	private String businessType;
}
