package com.sandu.api.servicepurchase.output;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandu.api.servicepurchase.model.vo.ServicesPurchaseListVO;
import com.sandu.api.servicepurchase.model.vo.ServicesPurchaseListVO.ServicesPurchaseListVOBuilder;

import io.swagger.annotations.ApiModelProperty;
/**
 * @author 
 */
@Data
@Builder
public class ServicesPriceListVO implements Serializable {
    private static final long serialVersionUID = 8367056931259434942L;
   
    /**
     * 套餐价格id
     */
    @ApiModelProperty(value = "套餐价格id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Long id;
    /**
     * 期限
     */
    @ApiModelProperty(value = "价格单元(0-年;1-月;2-日)")
    private String priceUnit;

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
     * 套餐价格
     */
    @ApiModelProperty(value = "套餐时长")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer duration;
}