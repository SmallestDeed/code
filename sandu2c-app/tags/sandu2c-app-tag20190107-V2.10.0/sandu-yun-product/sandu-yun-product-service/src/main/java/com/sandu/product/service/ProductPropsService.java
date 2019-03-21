package com.sandu.product.service;

import com.sandu.productprops.model.ProductProps;

/**
 * @version V1.0
 * @Title: ProductPropsService.java
 * @Package com.sandu.productprops.service
 * @Description:产品属性-产品属性表Service
 * @createAuthor pandajun
 * @CreateDate 2015-09-01 10:40:03
 */
public interface ProductPropsService {
    /**
     * 新增数据
     *
     * @param productProps
     * @return int
     */
    int add(ProductProps productProps);

    /**
     * 更新数据
     *
     * @param productProps
     * @return int
     */
    int update(ProductProps productProps);

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
     * @return ProductProps
     */
    ProductProps get(Integer id);


}
