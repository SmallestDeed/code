package com.sandu.api.decorateorder.input;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * decorate_order
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-10-18 14:00
 */
@Data
public class DecorateOrderReceiptUpdate implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1115735022233391913L;

	@ApiModelProperty(value = "id", required = true)
	@NotNull(message = "id不能为空")
	@Min(value = 1, message = "id不能为空")
	private Integer Id;

	@ApiModelProperty(value = "财务收款状态(0-待收款;1已收款)", required = true)
	@NotNull(message = "请选择收款状态")
	@Min(value = 0, message = "收款状态异常")
	private Integer financeReceiptsStatus;

}
