package com.sandu.base.dao;

import org.springframework.stereotype.Repository;

import com.sandu.common.persistence.CrudDao;
import com.sandu.sys.model.ProductCategory;
import com.sandu.sys.model.query.ProductCategoryQuery;
import com.sandu.sys.model.vo.ProductCategoryVo;

@Repository
public interface BaseProductCategoryDao extends 
        CrudDao<ProductCategory,ProductCategoryQuery,ProductCategoryVo>{
    
}
