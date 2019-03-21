package com.sandu.api.imallOrder.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 积分订单表
 */
@Data
public class ImallOrder implements Serializable {
    /**
     * id
     */
    private int id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单总积分
     */
    private int totalPoint;

    /**
     * 礼品数量
     */
    private int totalCount;

    /**
     * 用户名
     */
    private String buyerNickName;

    /**
     * 用户id
     */
    private int buyerId;

    /**
     * 状态：0待审核，1待发货，2待签收，3签收，4未签收，5退积分，6关闭，7完成
     */
    private int status;

    /**
     * 付款状态0未付款1已付款
     */
    private int paymentStatus;

    /**
     * 付款积分
     */
    private int paymentPoint;

    /**
     * 付款时间
     */
    private Date paymentDate;

    /**
     * 发货状态
     */
    private int shipment_status;

    /**
     * 退款状态
     */
    private int refundmentStatus;

    private double expressFee;

    private String expressNo;

    private String expressCompay;

    /**
     * 订单备注
     */
    private String remark;

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

    private List<ImallOrderItem> itemList;

    private List<ImallOrderLog> logList;

    private List<ImallOrderAddress> addressList;

}
