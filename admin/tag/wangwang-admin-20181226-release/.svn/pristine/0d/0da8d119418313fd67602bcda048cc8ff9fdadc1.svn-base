package com.sandu.api.goods.output;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class GoodsDetailVO implements Serializable
{
    /**
     * spuId
     */
    private Integer spuId;
    /**
     * 企业ID
     */
    private Integer companyId;
    /**
     * 商品描述
     */
    private String describe;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品主缩略图
     */
    private PicVO mainPic;
    /**
     * 商品图片列表
     */
    private List<PicVO> picList;
    /**
     * 限购
     */
    private Integer limitation;
    /**
     * 固定物流费用
     */
    private BigDecimal fixTransCost;
    /**
     * 是否预售
     */
    private Integer presell;
    /**
     * 预售截止时间
     */
    private Date presellDDL;
    /**
     * 尾款支付开始时间
     */
    private Date payTailStarttime;
    /**
     * 尾款支付结束时间
     */
    private Date payTailEndtime;
    /**
     * 定金
     */
    private BigDecimal earnest;
    /**
     * 预售商品发货时间
     */
    private Integer shipmentsPresell;
    /**
     * 商品最早发货时间（天数）
     */
    private Integer shipmentsDay;
    /**
     * 商品最早发货时间（日期）
     */
    private Date shipmentsDate;
    /**
     * 总库存
     */
    private Integer totalRepertory;
    /**
     * sku列表表头
     */
    private List<String> tableHeads;
    /**
     * sku列表
     */
    private List<GoodsSKUVO> skuList;
    /**
     * 主缩略图ID
     */
    private Integer mainPicId;
    /**
     * 图片ids
     */
    private String picIds;
    /**
     * 是否特卖商品
     */
    private Integer isSpeSell;
}
