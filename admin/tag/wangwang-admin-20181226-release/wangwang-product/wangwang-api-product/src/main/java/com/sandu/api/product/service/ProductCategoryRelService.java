package com.sandu.api.product.service;

import com.sandu.api.product.model.ProductCategoryRel;

import java.util.List;

/**
 * 产品-分类中间表服务
 *
 * @author Sandu
 * @date 2017/12/19
 */
public interface ProductCategoryRelService {
    /**
     * 保存单条记录
     * @param productCategoryRel
     * @return
     */
    int saveProductCategoryRel(ProductCategoryRel productCategoryRel);

    /**
     * 保存多条记录
     * @param list
     * @return
     */
    int saveProductCategoryRelList(List<ProductCategoryRel> list);

    /**
     * 根据主键删除
     * @param id
     * @return
     */
    int deleteProductCategoryRelById(long id);

    /**
     * 根据产品ID删除相关记录
     * @param productId
     * @return
     */
    int deleteProductCategoryRelByProductId(long productId);

    void updateProductCategoryRel(ProductCategoryRel productCategoryRel);

    /**
     * 根据主键获取单条记录
     * @param id
     * @return
     */
    ProductCategoryRel getProductCategoryRelById(long id);

    /**
     * 根据分类获取多条记录
     * @param categoryId
     * @return
     */
    List<ProductCategoryRel> getProductCategoryRelByCategoryId(int categoryId);

    List<ProductCategoryRel> getProductCategoryRelByProductId(int productId);

    void deleteProductCategoryRelByProductIds(List<Integer> productIds);
}
