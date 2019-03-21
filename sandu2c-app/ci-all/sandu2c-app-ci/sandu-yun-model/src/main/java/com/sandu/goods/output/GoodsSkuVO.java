package com.sandu.goods.output;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GoodsSkuVO implements Serializable
{
    private Integer skuId;

    private Integer productId;

    private String productPicPath;

    private String price;

    private String salePrice;

    private Integer inventory;

    private String productName;

    private List<SpuAttributeVO> attr;

    private String attribute;

    private String transportMoney;
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
