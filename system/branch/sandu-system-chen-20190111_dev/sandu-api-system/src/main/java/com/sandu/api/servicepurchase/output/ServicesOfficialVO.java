package com.sandu.api.servicepurchase.output;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 官网试用输入参数
 * @author Sandu
 *
 */
@Data
public class ServicesOfficialVO implements Serializable {

	private static final long serialVersionUID = 7006817824349178464L;

	@ApiModelProperty(value = "套餐id")
	private Long id;

	@ApiModelProperty(value = "套餐价格id")
	private Long sevicePriceId;
	
	@ApiModelProperty("试用渠道来源(0-商家后台;1-三度后台代购;2-三度官网;3-科创;4-其他)")
	@Min(value = 0, message = "请输入试用渠道来源")
	private String purchaseSource;
	
	@ApiModelProperty("业务类型(0-购买;1-续费;2-试用)")
	@Min(value = 0, message = "请输入业务类型")
	private String businessType;
	
}
