package com.sandu.api.imallOrderPayment.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ImallOrderPaymentDetails implements Serializable {
    /**
     * id
     */
    private long id;

    /**
     * 付款单id
     */
    private long paymentId;

    /**
     * 支付方式
     */
    private String payMethod;

    /**
     * 支付金额
     */
    private int pointPaid;

    /**
     * 支付时间
     */
    private Date payTime;

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
