package com.sandu.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class MallBaseOrder implements Serializable{


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

    private String zipcode;

    private String mobile;

    private BigDecimal orderAmount;

    private String sysCode;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String remark;

    private List <OrderProduct> orderProductList;
    
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

    /**add by wangHl**/
    /**用户优惠卷Id**/
    private Long couponId;

    /**优惠卷编码**/
    private Long couponNo;

    /**优惠卷使用时间**/
    private Date couponUsedTime;

    /**优惠金额**/
    private BigDecimal discountPrice;

    /**用户领取优惠券编码**/
    private Long receiveNo;

    private String couponUserNo;
}