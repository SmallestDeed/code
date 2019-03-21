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
public class DecorateOrderUpdate implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1115735022233391913L;

	@ApiModelProperty(value = "id", required = true)
	@NotNull(message = "id不能为空")
	@Min(value = 1, message = "id不能为空")
	private Integer Id;

	@ApiModelProperty(value = "签约金额", required = true)
	@NotNull(message = "签约金额")
	@Min(value = 1, message = "签约金额")
	private Integer contractFee;

	@ApiModelProperty(value = "服务费", required = true)
	@NotNull(message = "服务费不能为空")
	@Min(value = 1, message = "服务费不能为空")
	private Double serviceFee;

	@ApiModelProperty(value = "合同id", required = true)
	@NotNull(message = "合同不能为空")
	@Min(value = 1, message = "请先上传合同!")
	private Integer resFileId;

	@ApiModelProperty(value = "上传平台类型(0-商家后台上传;1-移动B端上传)", required = true)
	@NotNull(message = "上传平台类型不能为空!")
	@Min(value = 0, message = "请选择上传平台类型!")
	private Integer platformType;
	
}
