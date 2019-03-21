package com.sandu.goods.output;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class GoodsDetailVO implements Serializable
{
    /**
     * 商品ID
     */
    private Integer id;
    /**
     * banner图列表
     */
    private List<String> smallPiclist;
    /**
     * 商品名称（spu名称，为了减少前端改动所以原字段名没改）
     */
    private String productName;

    // 原价
    private String salePrice;

    // 优惠价
    private String price;

    private Integer isFavorite;

    private String productDesc;
    /**
     * 限购信息
     */
    private Integer limitation;
    /**
     * 预售新品发货时间
     */
    private Integer sendOutPresell;
    /**
     * 商品最早发货时间（天数）
     */
    private Integer sendOutDay;
    /**
     * 商品最早发货时间（日期）
     */
    private Date sendOutDate;
    /**
     * 运费
     */
    private String transportPay;
    //商品封面图
    private Integer picId;
    //封面图路径
    private String picPath;
    //库存
    private Integer totalInventory;
}
