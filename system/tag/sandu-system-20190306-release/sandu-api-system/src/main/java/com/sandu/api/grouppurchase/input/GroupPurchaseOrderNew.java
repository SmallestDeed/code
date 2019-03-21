package com.sandu.api.grouppurchase.input;

import com.sandu.order.OrderProduct;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/12/11 10:27
 * @since 1.8
 */

@Setter
@Getter
@ToString
public class GroupPurchaseOrderNew implements Serializable {

    Integer id;
    Integer userId;
    Integer companyId;
    String appId;
    String orderCode;
    Integer orderStatus;
    Integer shippingStatus;
    Integer payStatus;
    String consignee;
    String province;
    String city;
    String district;
    String address;
    // 完整地址
    String fullAddress;
    String zipCode;
    String mobile;
    BigDecimal orderAmount;

    List<OrderProductNew> orderProductList;

    String openId;
    String payStatusStr;
    List<Integer> payStatusList;
    String orderStatusStr;
    List<Integer> orderStatusList;
    BigDecimal spuTotalPrice;
    BigDecimal totalTransportCost;
    Integer isCart;

    @ApiModelProperty("用户优惠卷Id")
    Long couponId;

    @ApiModelProperty("优惠卷编码")
    Long couponNo;

    @ApiModelProperty("优惠卷使用时间")
    Date couponUsedTime;

    @ApiModelProperty("优惠金额")
    BigDecimal discountPrice;

    @ApiModelProperty("用户领取优惠券编码")
    Long receiveNo;

    String couponUserNo;

    @ApiModelProperty("获取当前的企业ID")
    String domainUrl;
}
