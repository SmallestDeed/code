package com.sandu.api.servicepurchase.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceRecordInitBO implements Serializable {

    @ApiModelProperty(value = "时长选择", required = true)
    private Long priceId;

    @ApiModelProperty(value = "购买数量(1-1000)", required = true)
    private Double purchaseAmount;

    @ApiModelProperty(value = "支付方式(0-支付宝;1-微信;2-其他)", required = true)
    private String payType;

    @ApiModelProperty("公司id")
    private Integer companyId;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("被升级/续费的用户ID")
    private Integer accountUserId;

    @ApiModelProperty("购买来源(1-三度官网;2-三度后台代购;3-商家后台;4-科创;5-抢工长)")
    private String purchaseSource;

    @ApiModelProperty("0-购买;1-续费;2-试用;3-代购;4-升级")
    private String businessType;
}
