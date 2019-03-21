package com.sandu.api.goods.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BizGoodsBO implements Serializable {
    /**
     * spuId
     */
    private Integer spuId;
    /**
     * spuCode
     */
    private String spuCode;
    /**
     * 缩略图地址
     */
    private String picPath;
    /**
     * 商品名称
     */
    private String spuName;
    /**
     * 大类名称
     */
    private String bigType;
    /**
     * 小类名称
     */
    private String smallType;
    /**
     * 最小原价
     */
    private Integer minSalePrice;
    /**
     * 最大原价
     */
    private Integer maxSalePrice;
    /**
     * 最小优惠价
     */
    private BigDecimal minPrice;
    /**
     * 最大优惠价
     */
    private BigDecimal maxPrice;
    /**
     * 商品总库存
     */
    private Integer totalInventory;
    /**
     * 定金
     */
    private BigDecimal deposit;
    /**
     * 预售截止时间
     */
    private Date presellDDL;
}
