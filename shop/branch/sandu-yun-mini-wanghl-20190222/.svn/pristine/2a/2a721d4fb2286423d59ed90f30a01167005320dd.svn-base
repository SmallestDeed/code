package com.sandu.activity.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户可被使用优惠卷输出
 * @author WangHaiLin
 * @date 2018/7/30  16:20
 */
@Data
public class CouponCanBeUsedVO  implements Serializable{

    @ApiModelProperty(value = "用户优惠券活动Id")
    private Long couponId;

    @ApiModelProperty(value = "领取优惠卷编号")
    private String receiveNo;

    @ApiModelProperty(value = "有效期开始日期")
    private Date startTime;

    @ApiModelProperty(value = "有效期截止日期")
    private Date endTime;

    @ApiModelProperty(value = "优惠方式(1:固定金额 2:折扣)")
    private Integer discountMode;

    @ApiModelProperty(value = "折扣金额")
    private Long discountAmount;

    @ApiModelProperty(value = "折扣系数")
    private Double rebateFactor;

    @ApiModelProperty(value = "订单金额(使用条件)")
    private Double orderAmount;

    @ApiModelProperty(value = "优惠卷类型(1:新人券;2:普通券)")
    private Integer couponType;

    @ApiModelProperty(value = "此次是否可用(1:可用;0:不可用)")
    private Integer canBeUseThisTime;

    @ApiModelProperty(value = "真实优惠金额(可使用才有，不可使用则没有)(“固定金额”时等于折扣金额;“折扣”时等于商品总价*折扣系数)")
    private BigDecimal realDiscount;

    @ApiModelProperty(value = "每人限领数量,0:表示不限制")
    private Integer maxReceiveQty;

    @ApiModelProperty(value = "每张优惠券领取数量")
    private Integer number;

    @ApiModelProperty(value = "商品使用范围类别 1:所有门店商品 2:指定商品")
    private Integer productScopeType;

    @ApiModelProperty(value = "商品id")
    private Long goodId;

    private String beginTime;

    private String finalTime;

    private Long couponUserNo;
}
