package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.ResFile;
import com.nork.system.model.ResVersion;
import com.nork.system.model.search.ResVersionSearch;

/**   
 * @Title: ResVersionMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统版本-系统版本资源表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-08-17 11:41:05
 * @version V1.0   
 */
@Repository
@Transactional
public interface ResVersionMapper {
    int insertSelective(ResVersion record);

    int updateByPrimaryKeySelective(ResVersion record);
  
    int deleteByPrimaryKey(Integer id);
        
    ResVersion selectByPrimaryKey(Integer id);
    
    int selectCount(ResVersionSearch resVersionSearch);
    
	List<ResVersion> selectPaginatedList(
			ResVersionSearch resVersionSearch);
			
    List<ResVersion> selectList(ResVersion resVersion);
    
	/**
	 * 其他
	 * 
	 */
    
	public void updateByBusinessId(ResVersion resVersion) ;
}
