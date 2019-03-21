package com.sandu.api.product.service;

import com.sandu.api.product.model.ProductAttribute;

import java.util.List;

public interface ProductAttributeService {

    /**
     * 获取产品的数据
     * @param proAttrKey
     * @param proAttrValKey
     * @return
     */
    ProductAttribute getProAttr(String proAttrKey, String proAttrValKey);

    Integer addProAttr(ProductAttribute proAttr);

    List<ProductAttribute> listProductAttrByProductId(Long productId);

    Integer batchAddAttribute(List<ProductAttribute> attrs);
}
