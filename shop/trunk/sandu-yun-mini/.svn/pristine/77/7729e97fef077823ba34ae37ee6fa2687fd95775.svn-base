package com.sandu.activity.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询当前(订单)可使用优惠卷列表入参
 * @author WangHaiLin
 * @date 2018/7/30  17:31
 */
@Data
public class CouponCanBeUsedQuery implements Serializable {

    @ApiModelProperty(value = "用户Id",required = true)
    private Long userId;

    @ApiModelProperty(value = "企业ID",required = true)
    private Long companyId;

    @ApiModelProperty(value = "商品总价",required = true)
    private Double totalPrice;

    @ApiModelProperty(value = "商品Id集合",required = true)
    private String productId;

}
