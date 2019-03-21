package com.sandu.api.decorateorder.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * decorate_order
 * 
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-10-18 14:00
 */

@Data
public class DecorateOrderQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6297293642729962834L;

	@ApiModelProperty("合同状态(0-待上传;1-待审核;2-审核通过)")
	private Integer contractStatus;

	@ApiModelProperty("是否已收款(0-待收款;1-已收款)")
	private Integer financeReceiptsStatus;

	@ApiModelProperty("退款状态(0-待审核;1-审核通过-;2-已驳回)")
	private Integer refundStatus;

	@ApiModelProperty("退款装企ID")
	private Integer companyId;
	
	@ApiModelProperty("退款装企名称")
	private String companyName;
	
    @ApiModelProperty(value = "页码", required = true)
    @Min(value = 1, message = "请输入正确的数值")
    @NotNull(message = "页码不能为空")
    private Integer page;

    @ApiModelProperty(value = "条数", required = true)
    @Min(value = 1, message = "请输入正确的数值")
    @NotNull(message = "条数不能为空")
    private Integer limit;
	
    @ApiModelProperty(hidden = true)
	private Integer businessType;

	@ApiModelProperty("客户姓名")
	private String userName;

	@ApiModelProperty("客户号码")
	private String mobile;

}
