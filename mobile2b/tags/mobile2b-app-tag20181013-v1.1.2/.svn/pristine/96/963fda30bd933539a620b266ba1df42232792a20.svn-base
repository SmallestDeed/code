package com.nork.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.model.BaseSeries;
import com.nork.product.model.search.BaseSeriesSearch;

/**   
 * @Title: BaseSeriesMapper.java 
 * @Package com.nork.product.dao
 * @Description:product-系列表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-11-04 11:06:16
 * @version V1.0   
 */
@Repository
@Transactional
public interface BaseSeriesMapper {
    int insertSelective(BaseSeries record);

    int updateByPrimaryKeySelective(BaseSeries record);
  
    int deleteByPrimaryKey(Integer id);
        
    BaseSeries selectByPrimaryKey(Integer id);
    
    int selectCount(BaseSeriesSearch baseSeriesSearch);
    
	List<BaseSeries> selectPaginatedList(
			BaseSeriesSearch baseSeriesSearch);
			
    List<BaseSeries> selectList(BaseSeries baseSeries);

    List<BaseSeries> findSeriesByUserAuthorizedBrandCode(@Param("userId") Integer userId);

    int findSeriesCount(@Param("idsList") List<Integer> idsList, @Param("level") Integer level);

    List<BaseSeries> findSeriesList(@Param("idsList") List<Integer> idsList, @Param("level") Integer level);
	/**
	 * 其他
	 * 
	 */
}
