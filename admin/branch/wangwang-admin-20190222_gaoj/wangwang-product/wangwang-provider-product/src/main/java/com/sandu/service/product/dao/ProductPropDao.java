package com.sandu.service.product.dao;

import com.sandu.api.product.model.ProductProp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Sandu
 * @date 2017/12/18
 */
@Repository
public interface ProductPropDao {
    int saveProductProp(ProductProp productProp);

    int deleteProductPropById(@Param("id") long id);

    void updateProductProp(ProductProp productProp);

    ProductProp getProductPropInfoById(@Param("id")long id);

    List<ProductProp> getProductPropByPid(@Param("pid")int pid);

    List<ProductProp> getProductPropByProductId(@Param("productId")int productId);

    List<ProductProp> getProductPropByLongCode(@Param("longCode")String longCode);

    List<ProductProp> getParentAndItselfByIds(List<Integer> ids);

    List<ProductProp> getAllParentByCodes(List<String> collect);
}
