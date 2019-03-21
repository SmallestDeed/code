package com.sandu.goods.model.BO;

import com.sandu.product.model.ProductAttribute;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductAttrBoV2 implements Serializable, Comparable<ProductAttrBoV2> {
    private Integer productId;

    private String productName;

    private Integer picId;

    private Integer defaultPicId;

    private BigDecimal price;

    private Integer inventory;

    private BigDecimal salePrice;

    private List<ProductAttribute> productAttributeList;

    @Override
    public int compareTo(ProductAttrBoV2 o) {
        return this.price.compareTo(o.getPrice());
    }
}
