package com.sandu.service.product.dao;

import com.sandu.api.product.model.ProductCategoryRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Sandu
 * @date 2017/12/19
 */
@Repository
public interface ProductCategoryRelDao {

    int save(ProductCategoryRel productCategoryRel);

    int delete(long id);

    void update(ProductCategoryRel productCategoryRel);

    ProductCategoryRel getProductCategoryRelById(@Param("id") long id);

    List<ProductCategoryRel> getProductCategoryRelByCategoryId(@Param("categoryId")int categoryId);

    int deleteProductCategoryRelByProductId(@Param("productId") long productId);

    int saveProductCategoryRelList(@Param("list") List<ProductCategoryRel> list);

    List<ProductCategoryRel> getProductCategoryRelByProductId(@Param("productId") int productId);

    int checkExistNewProductCategoryRel(@Param("productId") long productId);

    int updateByProductIdAndAttr1(ProductCategoryRel productCategoryRel);

    void deleteProductCategoryRelByProductIds(List<Integer> productIds);
}
