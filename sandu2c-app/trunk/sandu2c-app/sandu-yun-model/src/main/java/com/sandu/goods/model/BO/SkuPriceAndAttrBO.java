package com.sandu.goods.model.BO;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuPriceAndAttrBO implements Serializable
{
    private Integer productId;

    private Integer skuId;

    private BigDecimal price;

    private BigDecimal salePrice;

    private String productName;

    private String spePicPath;

    private String defaultPicPath;

    private Integer inventory;

    private String attrValueIds;

    private BigDecimal transportMoney;
}
