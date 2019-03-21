package com.sandu.api.servicepurchase.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ServicesPriceContentVO implements Serializable {

    private static final long serialVersionUID = 1441790529601519150L;
    /**
     * 套餐价格id
     */
    @ApiModelProperty(value = "套餐价格id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Long id;
    /**
     * 期限
     */
    @ApiModelProperty(value = "期限")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String term;

    /**
     * 赠送天数
     */
    @ApiModelProperty(value = "赠送天数")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer giveDuration;

    /**
     * 免费渲染时间
     */
    @ApiModelProperty(value = "免费渲染时间")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer freeRenderDuration;

    /**
     * 赠送度币
     */
    @ApiModelProperty(value = "赠送度币")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer sanduCurrency;

    /**
     * 套餐价格
     */
    @ApiModelProperty(value = "套餐价格")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private BigDecimal price;

    /**
     * 套餐升级差价
     */
    @ApiModelProperty(value = "套餐升级差价")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private BigDecimal differPrice;
}
