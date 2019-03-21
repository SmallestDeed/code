package com.nork.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.ProductUsageCount;
import com.nork.product.model.search.ProductUsageCountSearch;

/**   
 * @Title: ProductUsageCountMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-使用次数记录表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-07-26 16:18:44
 * @version V1.0   
 */
@Repository
@Transactional
public interface ProductUsageCountMapper {
    int insertSelective(ProductUsageCount record);

    int updateByPrimaryKeySelective(ProductUsageCount record);
  
    int deleteByPrimaryKey(Integer id);
        
    ProductUsageCount selectByPrimaryKey(Integer id);
    
    int selectCount(ProductUsageCountSearch productUsageCountSearch);
    
	List<ProductUsageCount> selectPaginatedList(
			ProductUsageCountSearch productUsageCountSearch);
			
    List<ProductUsageCount> selectList(ProductUsageCount productUsageCount);
    
	/**
	 * 其他
	 * 
	 */
}
