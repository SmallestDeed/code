package com.nork.resource.dao;


import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.resource.model.ResPicHouse;
import com.nork.resource.model.search.ResPicHouseSearch;


/**   
 * @Title: ResPicMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统-图片资源库Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 16:06:59
 * @version V1.0   
 */
@Repository
@Transactional
public interface ResPicHouseMapper {
    int insertSelective(ResPicHouse record);

    int updateByPrimaryKeySelective(ResPicHouse record);
  
    int deleteByPrimaryKey(Integer id);
        
    ResPicHouse selectByPrimaryKey(Integer id);
    
    int selectCount(ResPicHouseSearch resPicHouseSearch);
    
	List<ResPicHouse> selectPaginatedList(ResPicHouseSearch resPicHouseSearch);
	
	public ResPicHouse getPrevResPicHouseId(Integer resPicHouseId);
	
	public ResPicHouse getNextResPicHouseId(Integer resPicHouseId);
	
    
}
