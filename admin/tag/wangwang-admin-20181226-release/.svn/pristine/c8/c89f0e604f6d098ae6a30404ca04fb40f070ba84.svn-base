package com.sandu.service.product.dao;

import com.sandu.api.product.model.ProductStyle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sandu
 */
@Repository
public interface ProductStyleDao {
    int save(ProductStyle productStyle);

    int deleteById(@Param("id") long id);

    void update(ProductStyle productStyle);

    ProductStyle getById(@Param("id")long id);

    List<ProductStyle> listProductStyleIdAndName();
}
