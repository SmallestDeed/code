package com.sandu.product.dao;

import com.sandu.productprops.model.ProductProps;
import com.sandu.productprops.model.search.ProductPropsSearch;
import org.springframework.stereotype.Repository;

/**
 * @version V1.0
 * @Title: ProductPropsMapper.java
 * @Package com.sandu.productprops.dao
 * @Description:产品属性-产品属性表Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-09-01 10:40:03
 */
@Repository
public interface ProductPropsMapper {
    int insertSelective(ProductProps record);

    int updateByPrimaryKeySelective(ProductProps record);

    int deleteByPrimaryKey(Integer id);

    ProductProps selectByPrimaryKey(Integer id);

    int selectCount(ProductPropsSearch productPropsSearch);


}
