package com.sandu.api.servicepurchase.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.RegEx;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
public class ServicesPurchaseAdd implements Serializable {

    @ApiModelProperty(value = "套餐id", hidden = true)
    private Long id;

    @ApiModelProperty(value = "适用用户", required = true)
    @NotEmpty(message = "使用用户")
    @Size(min = 1, max = 40, message = "请选择适用用户")
    private String userTypeId;

    @ApiModelProperty(value = "时长选择", required = true)
    @NotNull(message = "请选择时长")
    @Min(value = 1, message = "请选择时长")
    private Long priceId;

    @ApiModelProperty(value = "购买数量(1-1000)", required = true)
    @Min(value = 1, message = "购买数量最少是1")
    @Max(value = 999, message = "购买数量不能超过1000")
    private Double purchaseAmount;

    @ApiModelProperty(value = "支付方式(0-支付宝;1-微信;2-其他)", required = true)
    @Pattern(regexp = "[0-2]", message = "请选择正确的支付方式")
    @NotEmpty(message = "请选择支付方式")
    private String payType;

    @ApiModelProperty("公司id")
    @NotNull(message = "公司id不能为空")
    @Min(value = 1, message = "请输入正确的用户ID")
    private Integer companyId;

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1, message = "请输入正确的用户ID")
    private Integer userId;

    @ApiModelProperty("购买来源(1、三度官网，2、三度后台，3、商家后台，4、科创，5、抢工长)")
    @NotNull(message = "购买来源不能为空")
    @Min(value = 0, message = "请输入购买来源")
    private String purchaseSource;

    @ApiModelProperty("0-购买;1-续费;2-试用;3-代购")
    @NotNull(message = "操作类型")
    @Pattern(regexp = "[0-3]", message = "请选择正确的操作类型")
    private String businessType;

    @ApiModelProperty("平台编码")
    @NotEmpty(message = "平台编码不能为空")
    private String platformCode;

}
