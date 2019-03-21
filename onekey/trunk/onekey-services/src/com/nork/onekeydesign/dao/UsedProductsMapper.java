package com.nork.onekeydesign.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.onekeydesign.model.UsedProducts;
import com.nork.onekeydesign.model.UserProductPlan;
import com.nork.onekeydesign.model.search.UsedProductsSearch;

/**   
 * @Title: UsedProductsMapper.java 
 * @Package com.nork.onekeydesign.dao
 * @Description:设计方案-已使用产品表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-07-10 16:23:04
 * @version V1.0   
 */
@Repository
@Transactional
public interface UsedProductsMapper {
    int insertSelective(UsedProducts record);

    int updateByPrimaryKeySelective(UsedProducts record);
  
    int deleteByPrimaryKey(Integer id);
        
    UsedProducts selectByPrimaryKey(Integer id);
    
    int selectCount(UsedProductsSearch usedProductsSearch);
    
	List<UsedProducts> selectPaginatedList(
			UsedProductsSearch usedProductsSearch);
			
    List<UsedProducts> selectList(UsedProducts usedProducts);
    
    List<UserProductPlan> getUsedProductPlanList(UsedProductsSearch usedProductsSearch);
    List<UserProductPlan> getUserProductPlan(UserProductPlan userProductPlan);
    public Integer getUsedProductPlanCount(UsedProductsSearch usedProductsSearch);
	/**
	 * 其他
	 * 
	 */
}
