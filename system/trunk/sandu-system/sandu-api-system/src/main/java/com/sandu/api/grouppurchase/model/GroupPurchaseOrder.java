package com.sandu.api.grouppurchase.model;

import com.sandu.order.OrderProduct;
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
 * @date 2018/12/11 10:33
 * @since 1.8
 */

@Setter
@Getter
@ToString
public class GroupPurchaseOrder implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer companyId;
    private String appId;
    private String orderCode;
    private Integer orderStatus;
    private Integer shippingStatus;
    private Integer payStatus;
    private String consignee;
    private String province;
    private String city;
    private String district;
    private String address;
    // 完整地址
    private String fullAddress;
    private String zipCode;
    private String mobile;
    private BigDecimal orderAmount;
    private String sysCode;
    private String creator;
    private Date gmtCreate;
    private String modifier;
    private Date gmtModified;
    private Integer isDeleted;
    private String remark;
    private List<OrderProduct> orderProductList;
    private Integer start;
    private Integer limit;
    private String openId;
    private String payStatusStr;
    private List<Integer> payStatusList;
    private String orderStatusStr;
    private List<Integer> orderStatusList;
    private BigDecimal spuTotalPrice;
    private BigDecimal totalTransportCost;
    private Integer isCart;
    private Long couponId;
    private Long couponNo;
    private Date couponUsedTime;
    private BigDecimal discountPrice;
    private Long receiveNo;
    private String couponUserNo;
}
