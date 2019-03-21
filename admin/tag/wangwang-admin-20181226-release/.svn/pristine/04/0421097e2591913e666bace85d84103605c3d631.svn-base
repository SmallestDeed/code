package com.sandu.api.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @author Sandu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseGoodsSpu implements Serializable {

    /**
     * 主键
     * <p>
     * isNullAble:0
     */
    private Integer id;

    /**
     * spu ID
     * isNullAble:0
     */
    private Integer spuId;

    /**
     * spu CODE
     * isNullAble:0
     */
    private String spuCode;

    /**
     * 产品名称
     * isNullAble:0
     */
    private String productName;

    /**
     * 产品型号
     * isNullAble:0
     */
    private String productModelNumber;

    /**
     * 产品小分类（产品属性父级）
     * isNullAble:0
     */
    private String smallTypeMark;

    /**
     * 产品描述
     * isNullAble:1
     */
    private String productDesc;

    /**
     * 商品名称
     * isNullAble:0
     */
    private String spuName;

    /**
     * 限购信息
     * isNullAble:0
     */
    private Integer purhaseLimitation;

    /**
     * 物流ID
     * isNullAble:1
     */
    private Integer transportId;

    /**
     * 固定物流费用
     * isNullAble:1
     */
    private java.math.BigDecimal fixTransportExpense;

    /**
     * 赠送的积分数
     * isNullAble:1
     */
    private Integer integral;

    /**
     * 是否预售
     * isNullAble:0
     */
    private java.math.BigDecimal isPresell;

    /**
     * 预售截止时间
     * isNullAble:1
     */
    private java.time.LocalDateTime presellDeadline;

    /**
     * 尾款支付开始时间
     * isNullAble:1
     */
    private java.time.LocalDateTime balancePaymentStarttime;

    /**
     * 尾款支付结束时间
     * isNullAble:1
     */
    private java.time.LocalDateTime balancePaymentEndtime;

    /**
     * 定金
     * isNullAble:1
     */
    private java.math.BigDecimal deposit;

    /**
     * 支付尾款后多少天发货
     * isNullAble:1
     */
    private Integer deliveryTime;

    /**
     * 商品介绍
     * isNullAble:0
     */
    private String spuIntroduce;

    /**
     * 商品列表缩略图
     * isNullAble:1
     */
    private Integer picId;

    private String picIds;

    /**
     * 总库存
     * isNullAble:1
     */
    private Integer totalInventory;

    /**
     * 获得时间
     * isNullAble:1
     */
    private java.time.LocalDateTime getTime;

    /**
     * 上架情况
     * isNullAble:0
     */
    private java.math.BigDecimal isPutaway;

    /**
     * 创建者
     * isNullAble:1
     */
    private String creator;

    /**
     * 创建时间
     * isNullAble:0,defaultVal:CURRENT_TIMESTAMP
     */
    private java.time.LocalDateTime gmtCreate;

    /**
     * 修改者
     * isNullAble:1
     */
    private String modifier;

    /**
     * 修改时间
     * isNullAble:0,defaultVal:CURRENT_TIMESTAMP
     */
    private java.time.LocalDateTime gmtModified;

    /**
     * 是否删除
     * isNullAble:1
     */
    private Integer isDeleted;

    private Integer companyId;
}