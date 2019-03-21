package com.sandu.goods.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsVO implements Serializable
{
    /**
     * 商品ID
     */
    private Integer spuId;
    /**
     * 缩略图地址
     */
    private String picPath;
    /**
     * 商品名称（spu名称，为了不改变字段名减少前端的改动所以还是productName）
     */
    private String productName;
    /**
     * 优惠价格（sku表里的价格，为了不改变字段名减少前端的改动所以还是salePrice）
     */
    private String salePrice;
    /**
     * 是否预售新品
     */
    private Integer isPresell;
    /**
     * 是否特卖商品
     */
    private Integer isSpecialOffer;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private ActivityVO activity;
}
