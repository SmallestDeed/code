package com.sandu.goods.model.BO;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class GoodsSkuBO implements Serializable
{
    private Integer skuId;

    private Integer productId;

    private BigDecimal price;

    private BigDecimal salePrice;

    private Integer inventory;

    private Integer spePicId;

    private String productName;

    private String attributeValueIds;

    private Integer defaultPicId;

    private String attributeValueNames;

    private BigDecimal transportMoney;
    /**
     * 产品规格
     */
    private String productSpec;
    /**
     * 产品型号
     */
    private String productModelNumber;
    /**
     * 产品品牌
     */
    private String brandName;
}
