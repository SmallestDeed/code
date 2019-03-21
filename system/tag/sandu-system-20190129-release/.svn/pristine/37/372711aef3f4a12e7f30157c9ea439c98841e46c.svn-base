package com.sandu.api.servicepurchase.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class ServicesPurchaseRenewals implements Serializable {

    @ApiModelProperty(value = "套餐id" ,required = true)
    @NotNull(message = "套餐id不能为空")
    @Min(value = 1, message = "套餐ID最小为1")
    private Integer serviceId;

    @ApiModelProperty(value = "套餐价格id",required = true)
    @NotNull(message = "套餐价格id不能为空")
    @Min(value = 1, message = "套餐价格ID最小为1")
    private Integer priceId;

    @ApiModelProperty(value = "购买数量(1-1000)", required = true)
    @Min(value = 1,message = "数量不能为空")
    private Double purchaseAmount;

    @ApiModelProperty(value = "支付方式(0-支付宝;1-微信;2-其他)", required = true)
    @Pattern(regexp = "[0-2]", message = "请选择正确的支付方式")
    @NotEmpty(message = "请选择支付方式")
    private String payType;

    @ApiModelProperty(value = "公司id" ,required = true)
    @NotNull(message = "公司id不能为空")
    @Min(value = 1, message = "请输入正确的用户ID")
    private Integer companyId;

    @ApiModelProperty(value = "用户ID" ,required = true)
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1, message = "请输入正确的用户ID")
    private Integer userId;

    @ApiModelProperty("购买来源(1、三度官网，2、三度后台，3、商家后台，4、科创，5、抢工长)")
    @NotNull(message = "购买来源不能为空")
    @Min(value = 0, message = "请输入购买来源")
    private String purchaseSource;

    @ApiModelProperty("0-购买;1-续费;2-试用;3-代购;4-升级")
    @NotNull(message = "操作类型")
    @Pattern(regexp = "[0-4]", message = "请选择正确的操作类型")
    private String businessType;

    @ApiModelProperty("平台编码")
    @NotEmpty(message = "平台编码不能为空")
    private String platformCode;

    @ApiModelProperty("续费/升级账户ID")
    private Integer accountUserId;
}
