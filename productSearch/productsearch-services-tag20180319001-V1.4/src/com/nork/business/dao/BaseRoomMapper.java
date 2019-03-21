package com.nork.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.business.model.BaseRoom;
import com.nork.business.model.search.BaseRoomSearch;

/**   
 * @Title: BaseRoomMapper.java 
 * @Package com.nork.business.dao
 * @Description:业务-房型Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 12:04:29
 * @version V1.0   
 */
@Repository
@Transactional
public interface BaseRoomMapper {
    int insertSelective(BaseRoom record);

    int updateByPrimaryKeySelective(BaseRoom record);
  
    int deleteByPrimaryKey(Integer id);
        
    BaseRoom selectByPrimaryKey(Integer id);
    
    int selectCount(BaseRoomSearch baseRoomSearch);
    
	List<BaseRoom> selectPaginatedList(
			BaseRoomSearch baseRoomSearch);
			
    List<BaseRoom> selectList(BaseRoom baseRoom);
    
	/**
	 * 其他
	 * 
	 */
}
