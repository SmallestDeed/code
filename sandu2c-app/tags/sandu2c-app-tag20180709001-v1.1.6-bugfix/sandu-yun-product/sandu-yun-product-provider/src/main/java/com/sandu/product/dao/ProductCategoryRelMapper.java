package com.sandu.product.dao;

import com.sandu.product.model.CategoryProductResult;
import com.sandu.product.model.ProCategoryPo;
import com.sandu.product.model.ProductCategoryRel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: ProductCategoryRelMapper.java
 * @Package com.sandu.product.dao
 * @Description:产品模块-产品与产品类目关联Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 14:50:47
 */
@Repository
public interface ProductCategoryRelMapper {
    int insertSelective(ProductCategoryRel record);

    int updateByPrimaryKeySelective(ProductCategoryRel record);

    int deleteByPrimaryKey(Integer id);

    ProductCategoryRel selectByPrimaryKey(Integer id);

    CategoryProductResult getCategoryProductResultByProductId(ProductCategoryRel productCategoryRel);

	int findAllProductCount(ProductCategoryRel productCategoryRel);

	List<CategoryProductResult> findAllProductResult(ProductCategoryRel productCategoryRel);

	int selectAllProductCountByCode(ProductCategoryRel productCategoryRel);

	List<CategoryProductResult> selectAllProductResultByCode(ProductCategoryRel productCategoryRel);

	List<ProCategoryPo> selectProductCategoryByProductId(List<Integer> list);


}
