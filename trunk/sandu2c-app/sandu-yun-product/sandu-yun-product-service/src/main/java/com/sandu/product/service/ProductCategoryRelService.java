package com.sandu.product.service;

import com.sandu.product.model.CategoryProductResult;
import com.sandu.product.model.ProCategoryPo;
import com.sandu.product.model.ProductCategoryRel;
import com.sandu.user.model.UserSO;

import java.util.List;

/**
 * @version V1.0
 * @Title: ProductCategoryRelService.java
 * @Package com.sandu.product.service
 * @Description:产品模块-产品与产品类目关联Service
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 14:50:47
 */
public interface ProductCategoryRelService {
    /**
     * 新增数据
     *
     * @param productCategoryRel
     * @return int
     */
    int add(ProductCategoryRel productCategoryRel);

    /**
     * 更新数据
     *
     * @param productCategoryRel
     * @return int
     */
    int update(ProductCategoryRel productCategoryRel);

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
     * @return ProductCategoryRel
     */
    ProductCategoryRel get(Integer id);


    CategoryProductResult getCategoryProductResultByProductId(ProductCategoryRel productCategoryRel);



	int findAllProductCount(ProductCategoryRel productCategoryRel);

	List<CategoryProductResult> findAllProductResult(ProductCategoryRel productCategoryRel);

	int findAllProductCountByCode(ProductCategoryRel productCategoryRel);

	List<CategoryProductResult> findAllProductResultByCode(ProductCategoryRel productCategoryRel, UserSO userSo);

	List<ProCategoryPo> findProductCategoryByProductId(List<Integer> productId);

    /**
     * 根据可见范围查询到所有可见的分类编码
     * @param visibilityRangeIdList
     * @return
     */
    List<String> getCodeListByIdList(List<Integer> visibilityRangeIdList);

    List<String> getParentCodeListByIdList(List<Integer> visibilityRangeIdList);
}
