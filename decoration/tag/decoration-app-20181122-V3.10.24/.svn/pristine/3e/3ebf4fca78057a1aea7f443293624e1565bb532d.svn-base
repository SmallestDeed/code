package com.nork.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.business.model.RoomCommon;
import com.nork.business.model.search.RoomCommonSearch;

/**   
 * @Title: RoomCommonMapper.java 
 * @Package com.nork.business.dao
 * @Description:业务-通用房型Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 12:08:11
 * @version V1.0   
 */
@Repository
@Transactional
public interface RoomCommonMapper {
    int insertSelective(RoomCommon record);

    int updateByPrimaryKeySelective(RoomCommon record);
  
    int deleteByPrimaryKey(Integer id);
        
    RoomCommon selectByPrimaryKey(Integer id);
    
    int selectCount(RoomCommonSearch roomCommonSearch);
    
	List<RoomCommon> selectPaginatedList(
			RoomCommonSearch roomCommonSearch);
			
    List<RoomCommon> selectList(RoomCommon roomCommon);
    
	/**
	 * 其他
	 * 
	 */
}
