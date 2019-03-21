package com.sandu.product.service;

import com.sandu.product.model.ProductAttribute;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: ProductAttributeService.java
 * @Package com.sandu.product.service
 * @Description:产品模块-产品属性Service
 * @createAuthor pandajun
 * @CreateDate 2015-09-01 13:17:36
 */
public interface ProductAttributeService {
    /**
     * 新增数据
     *
     * @param productAttribute
     * @return int
     */
    int add(ProductAttribute productAttribute);

    /**
     * 更新数据
     *
     * @param productAttribute
     * @return int
     */
    int update(ProductAttribute productAttribute);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return ProductAttribute
     */
    ProductAttribute get(Integer id);

    /**
     * 所有数据
     *
     * @param productAttribute
     * @return List<ProductAttribute>
     */
    List<ProductAttribute> getList(ProductAttribute productAttribute);

    /**
     * 分页获取数据
     *
     * @return List<ProductAttribute>
     */
    List<ProductAttribute> getMergeAttribute(ProductAttribute productAttributet);


    /*
     * getPropertyMap
     * 通过产品id  获取属性值 的map   map的结构（ 键是product_attribute表中attribute_key  值是product_props表中的prop_value）
     * @param productId
     * @return
     */
    Map<String, String> getPropertyMap(Integer productId);

    /**
     * 查询出满足多个属性的产品
     *
     * @param map
     * @return
     */
    List<ProductAttribute> selectIntersectProductAttribute(Map<String, Object> map);
}
