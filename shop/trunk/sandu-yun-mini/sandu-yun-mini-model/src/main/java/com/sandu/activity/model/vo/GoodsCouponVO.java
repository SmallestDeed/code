package com.sandu.activity.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
   * @author WangHaiLin
 * @date 2018/7/29  15:20
 */
@Data
public class GoodsCouponVO implements Serializable{

    @ApiModelProperty(value = "优惠卷Id")
    private Long couponId;

    @ApiModelProperty(value = "订单金额(满足多少钱才能使用),0元表示无门槛")
    private double orderAmount;

    @ApiModelProperty(value = "优惠金额")
    private double discountAmount;

    @ApiModelProperty(value = "折扣系数")
    private double rebateFactor;

    @ApiModelProperty(value = "是否可领取:(1:已领取;0:未领取)")
    private Integer isAlreadyGet ;

    @ApiModelProperty(value = "优惠券类型")
    private String couponType;

    @ApiModelProperty(value = "每人限领数量")
    private Integer maxReceiveQty;

    @ApiModelProperty(value = "领取总量")
    private Integer receiveQty;

    @ApiModelProperty(value = "优惠券总量")
    private Integer qty;

    @ApiModelProperty(value = "'有效期方式 1:固定时间段 2:领券日算起'")
    private Integer effectiveDateMode;

    @ApiModelProperty(value = "有效期开始日期")
    private Date startTime;

    @ApiModelProperty(value = "有效期截止日期")
    private Date endTime;

    @ApiModelProperty(value = "用户领取某张优惠券数量")
    private Integer userQty;

    @ApiModelProperty(value = "优惠方式")
    private Integer discountMode;

    @ApiModelProperty(value = "真实优惠金额(可使用才有，不可使用则没有)(“固定金额”时等于折扣金额;“折扣”时等于商品总价*折扣系数)")
    private BigDecimal realDiscount;

}
