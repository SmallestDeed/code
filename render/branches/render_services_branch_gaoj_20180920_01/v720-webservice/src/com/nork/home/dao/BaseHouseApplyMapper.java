package com.nork.home.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.home.model.BaseHouseApply;
import com.nork.home.model.search.BaseHouseApplySearch;

/**   
 * @Title: BaseHouseApplyMapper.java 
 * @Package com.nork.home.dao
 * @Description:户型房型-户型申请表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-10-13 11:45:31
 * @version V1.0   
 */
@Repository
@Transactional
public interface BaseHouseApplyMapper {
    int insertSelective(BaseHouseApply record);

    int updateByPrimaryKeySelective(BaseHouseApply record);
  
    int deleteByPrimaryKey(Integer id);
        
    BaseHouseApply selectByPrimaryKey(Integer id);
    
    int selectCount(BaseHouseApplySearch baseHouseApplySearch);
    
	List<BaseHouseApply> selectPaginatedList(
			BaseHouseApplySearch baseHouseApplySearch);
			
    List<BaseHouseApply> selectList(BaseHouseApply baseHouseApply);
    
	/**
	 * 其他
	 * 
	 */
}
