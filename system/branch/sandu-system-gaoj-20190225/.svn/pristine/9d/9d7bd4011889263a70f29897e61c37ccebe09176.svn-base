package com.sandu.api.servicepurchase.input;

import com.sandu.systemutil.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sandu
 * @ClassName InnerServiceRenew
 * @date 2018/11/6
 */
@Data
public class InnerServiceRenew extends BaseModel implements Serializable {
	@ApiModelProperty(value = "续费用户", required = true)
	@NotNull(message = "用户id不能为空")
	@Min(1)
	private Integer userId;

	@ApiModelProperty(value = "续费时长", required = true)
	@NotNull(message = "续费时长不能为空")
	@Min(1)
	private Integer renewMount;

	@ApiModelProperty(value = "时长单元(0-年;1-月;2-日)", required = true)
	@NotNull(message = "时长单元不能为空")
	@Min(0)
	private Integer renewTimeUnit;

}
