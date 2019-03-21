package com.nork.product.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.PrepProductSearchInfo;
import com.nork.product.model.search.PrepProductSearchInfoSearch;

/**   
 * @Title: PrepProductSearchInfoMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-预处理表(产品搜索)Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-02-22 17:12:03
 * @version V1.0   
 */
@Repository
@Transactional
public interface PrepProductSearchInfoMapper {
    int insertSelective(PrepProductSearchInfo record);

    int updateByPrimaryKeySelective(PrepProductSearchInfo record);
  
    int deleteByPrimaryKey(Integer id);
        
    PrepProductSearchInfo selectByPrimaryKey(Integer id);
    
    int selectCount(PrepProductSearchInfoSearch prepProductSearchInfoSearch);
    
	List<PrepProductSearchInfo> selectPaginatedList(
			PrepProductSearchInfoSearch prepProductSearchInfoSearch);
			
    List<PrepProductSearchInfo> selectList(PrepProductSearchInfo prepProductSearchInfo);

	List<Integer> getProductIdList(PrepProductSearchInfo prepProductSearchInfo);

	Integer getCount(PrepProductSearchInfo prepProductSearchInfo);

	List<CategoryProductResult> getProductIdListV2(PrepProductSearchInfo prepProductSearchInfo);
    
	List<PrepProductSearchInfo> getPPSIByProductIdList(
			@Param("productIdList") List<Integer> productIdList,
			@Param("productStatusList") List<Integer> productStatusList, @Param("start") Integer start,
			@Param("limit") Integer limit);
	
	void deleteByProductIdList(@Param("productIdList") Set<Integer> productIdList);
	
	void batchSave(@Param("list") List<PrepProductSearchInfo> list);

	void deleteByProductStatusList(@Param("productStatusList") List<Integer> productStatusList);
	
}
