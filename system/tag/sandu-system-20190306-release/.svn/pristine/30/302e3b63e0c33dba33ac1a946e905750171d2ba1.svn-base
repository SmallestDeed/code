package com.sandu.api.servicepurchase.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ServicesPurchaseLogListBO implements Serializable {

    @ApiModelProperty(value = "服务ID")
    private Integer servicesId;

    @ApiModelProperty(value = "服务编码")
    private String servicesCode;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "服务名称")
    private String servicesName;

    @ApiModelProperty(value = "服务数量")
    private Integer purchaseAmount;

    @ApiModelProperty(value = "服务价格")
    private Integer purchaseMomey;

    @ApiModelProperty(value = "服务创建人")
    private String creator;

    @ApiModelProperty(value = "购买时间(订单支付成功时间)")
    private Date purchaseTime;

    @ApiModelProperty(value = "状态(0-待支付;1-支付成功;2-支付失败)")
    private Integer purchaseStatus;

    @ApiModelProperty(value = "支付方式(0-支付宝;1-微信;2-其他)")
    private Integer payType;

    @ApiModelProperty(value = "购买来源(1-三度官网;2-三度后台代购;3-商家后台;4-科创;5-抢工长)")
    private Integer purchaseSource;

    private String priceUnit;

    private Integer duration;

    private String purchaseUseTime;
}
