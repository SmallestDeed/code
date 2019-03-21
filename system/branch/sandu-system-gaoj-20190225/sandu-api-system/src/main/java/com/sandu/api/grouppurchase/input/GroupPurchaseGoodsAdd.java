package com.sandu.api.grouppurchase.input;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 描述:group_purchase_goods表的实体类
 * @version
 * @author:  Sandu
 * @创建时间: 2018-12-06
 */

@Data
public class GroupPurchaseGoodsAdd implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 活动ID(关联group_purchase_activity表)
     */
    private Long purchaseActivityId;

    /**
     * 商品spu_id(关联base_goods_spu)
     */
    private Long spuId;

    /**
     * 商品sku_id(关联base_goods_sku)
     */
    private Long skuId;

    /**
     * 拼团价格
     */
    private BigDecimal activityPrice;

    /**
     * sku总库存
     */
    private BigDecimal qty;

    /**
     * sku剩余库存(可用库存)
     */
    private BigDecimal useQty;

    /**
     * sku预下库存(预占库存)
     */
    private BigDecimal outQty;

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
    private Integer isDeleted;

    private List<Integer> skuIds;

    private List<Double> activityPrices;

    private List<Double> qtys;
}