package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.BaseLiving;
import com.nork.system.model.search.BaseLivingSearch;

/**   
 * @Title: BaseLivingMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统-小区Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 14:41:11
 * @version V1.0   
 */
@Repository
@Transactional
public interface BaseLivingMapper {
    int insertSelective(BaseLiving record);

    int updateByPrimaryKeySelective(BaseLiving record);
  
    int deleteByPrimaryKey(Integer id);
        
    BaseLiving selectByPrimaryKey(Integer id);
    
    int selectCount(BaseLivingSearch baseLivingSearch);
    
    int getCountBySearch(BaseLivingSearch baseLivingSearch);
    
    Integer getCountByCreator(BaseLivingSearch baseLivingSearch);
    
	List<BaseLiving> selectPaginatedList(
			BaseLivingSearch baseLivingSearch);
			
    List<BaseLiving> selectList(BaseLiving baseLiving);
    
    //获取同一区域下最大的小区编码数
    public int getMaxCodeNum(BaseLivingSearch baseLivingSearch);

	List<String> findAllName();

	List<Integer> getIdsBySearch(BaseLivingSearch baseLivingSearch);
	
}
