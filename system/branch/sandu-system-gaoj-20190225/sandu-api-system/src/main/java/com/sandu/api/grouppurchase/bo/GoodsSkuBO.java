package com.sandu.api.grouppurchase.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GoodsSkuBO implements Serializable {
    private Integer skuId;
    private Integer spuId;
    private Integer productId;
    private BigDecimal price;
    private BigDecimal salePrice;
    private BigDecimal activityPrice;
    private Integer inventory;
    private Integer picId;
    private Integer spePicId;
    private String productName;
    private Integer defaultPicId;

    private String attributeNames;
    private String attributeValueIds;
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
