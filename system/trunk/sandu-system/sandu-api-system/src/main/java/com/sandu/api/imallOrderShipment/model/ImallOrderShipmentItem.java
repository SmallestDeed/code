package com.sandu.api.imallOrderShipment.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 积分订单发货单明细表
 */
@Data
public class ImallOrderShipmentItem implements Serializable {
    /**
     * id
     */
    private long id;

    /**
     * 发货单id
     */
    private long shipmentId;

    /**
     * 商品id
     */
    private String itemId;

    /**
     * 商品Sku
     */
    private String itemSku;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 商品描述
     */
    private String itemDescription;

    /**
     * 原价
     */
    private double itemOriginalPrice;

    /**
     * 原积分
     */
    private int itemOriginalPoint;

    /**
     * 成交积分
     */
    private int itemPoint;

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
