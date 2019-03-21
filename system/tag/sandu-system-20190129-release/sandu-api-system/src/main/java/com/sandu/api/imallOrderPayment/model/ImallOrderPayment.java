package com.sandu.api.imallOrderPayment.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ImallOrderPayment implements Serializable {

    /**
     * id
     */
    private long id;

    /**
     * 付款单号
     */
    private String paymentNo;

    /**
     * 平台类型:1:选装网,2:企业小程序
     */
    private int platformType;

    /**
     * 订单id
     */
    private long orderId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 客户信息_客户ID
     */
    private long buyerId;

    /**
     * 客户信息_客户名称
     */
    private String buyerNickName;

    /**
     * 卖家id(选装网的店铺id,或企业小程序的企业id)
     */
    private long sellerId;

    /**
     * 卖家信息_卖家昵称
     */
    private String sellerNickName;

    /**
     * 支付积分
     */
    private int pointPaid;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 支付状态
     */
    private int status;

    /**
     * 系统编码
     */
    private String sysCode;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否删除
     */
    private int isDeleted;
}
