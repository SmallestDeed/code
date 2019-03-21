package com.nork.resource.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.resource.model.ResUsedProducts;
import com.nork.resource.model.search.ResUsedProductsSearch;

/**   
 * @Title: ResUsedProductsMapper.java 
 * @Package com.nork.resource.dao
 * @Description:文件资源-已使用产品资源库Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-07-26 16:50:18
 * @version V1.0   
 */
@Repository
@Transactional
public interface ResUsedProductsMapper {
    int insertSelective(ResUsedProducts record);

    int updateByPrimaryKeySelective(ResUsedProducts record);
  
    int deleteByPrimaryKey(Integer id);
        
    ResUsedProducts selectByPrimaryKey(Integer id);
    
    int selectCount(ResUsedProductsSearch resUsedProductsSearch);
    
	List<ResUsedProducts> selectPaginatedList(
			ResUsedProductsSearch resUsedProductsSearch);
			
    List<ResUsedProducts> selectList(ResUsedProducts resUsedProducts);
    
	/**
	 * 其他
	 * 
	 */
}
