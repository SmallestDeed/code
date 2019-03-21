package com.sandu.service.product.dao;

import com.sandu.api.product.model.ProductAttribute;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Sandu
 * @date 2017/12/19
 */
@Repository
public interface ProductAttributeDao {
    int save(ProductAttribute productAttribute);

    int deleteByPrimaryKey(long id);

    void update(ProductAttribute productAttribute);

    ProductAttribute getProductAttributeById(@Param("id") long id);

    List<ProductAttribute> getProductAttributeByProductId(@Param("productId") int productId);

    int saveProductAttributeList(List<ProductAttribute> attrs);

    void deleteProductAttributeByProductId(@Param("productId") Long productId);

    void deleteProductAttributeByProductIds(@Param("productIds") List<Long> productIds);
}
