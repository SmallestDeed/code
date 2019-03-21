package com.nork.pay.model;

import java.util.Date;

/**
 * Created by kono on 2018/3/3 0003.
 */
public class RenderFailPayOrder {

    private static final long serialVersionUID = 1L;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 用户ID **/
    private Integer userId;
    private String productName;

    /** 订单号 **/
    private String orderNo;
    /** 订单总金额，单位为分 **/
    private Integer totalFee;
    /** 支出类型 **/
    private String payType;
    /** 支出状态 **/
    private String payState;
    /** 预付id **/
    private String prepayId;
    /** 参考id **/
    private String refId;
    /** 凭证时间 **/
    private Date orderDate;
    /** 业务类别 **/
    private String bizType;
    /** 财务类别 **/
    private String financeType;

    private String tradeType;

    /** 备注 **/
    private String remark;
}
