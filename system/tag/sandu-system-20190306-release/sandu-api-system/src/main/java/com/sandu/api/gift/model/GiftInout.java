package com.sandu.api.gift.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 礼品库存记录表
 * @author djc
 * @datetime 2018/4/26 15:48
 */
@Data
public class GiftInout implements Serializable {

    /**
     * id
     */
    private long id;

    /**
     * 礼品id
     */
    private int giftId;

    /**
     * 操作类型0礼品操作1订单
     */
    private int operType;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 价值
     */
    private double price;

    /**
     * 积分
     */
    private int point;

    /**
     * 数量
     */
    private int count;

    /**
     * 结存
     */
    private int inventory;

    /**
     * 内容
     */
    private String remark;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

}
