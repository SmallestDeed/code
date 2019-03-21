package com.sandu.api.goods.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductSKUBO implements Serializable
{
    private Integer productId;

    private BigDecimal salePrice;

    private Integer picId;

    private List<ProductAttributeBO> attributes;

    private String picPath;

    private String productModelNumber;
}
