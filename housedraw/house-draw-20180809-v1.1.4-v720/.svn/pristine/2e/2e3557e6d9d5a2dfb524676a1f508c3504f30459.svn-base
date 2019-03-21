package com.sandu.service.product.dao;

import com.sandu.api.product.model.ProductProps;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPropsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductProps record);

    int insertSelective(ProductProps record);

    ProductProps selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductProps record);

    int updateByPrimaryKey(ProductProps record);

    ProductProps getProAttrByLongCode(String longCode);
}