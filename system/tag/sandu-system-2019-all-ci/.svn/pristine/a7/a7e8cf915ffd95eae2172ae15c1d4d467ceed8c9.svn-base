package com.sandu.api.servicepurchase.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 
 *
 *
 */
@Data
public class ServicesPurchaseVO implements Serializable {

    private static final long serialVersionUID = -5605209977331228853L;
    @ApiModelProperty(value = "套餐id")
	private Long id;

    @ApiModelProperty(value = "时长选择")
    private String servicesPriceId;
    
    @ApiModelProperty(value = "适用范围",required = true)
    private String userTypeId;

    @ApiModelProperty(value = "购买数量",required = true)
    @Min(value = 1, message = "请输入购买数量")
    private Integer purchaseAmount;
    
    @ApiModelProperty(value = "企业ID",required = true)
    @Min(value = 1, message = "请输入正确的企业ID")
    @NotNull(message = "企业ID不能为空")
    private Integer companyId;

}