package com.nork.render.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.render.model.RenderHost;
import com.nork.render.model.search.RenderHostSearch;

/**   
 * @Title: RenderHostMapper.java 
 * @Package com.nork.render.dao
 * @Description:渲染-渲染主机Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-01-15 17:45:34
 * @version V1.0   
 */
@Repository
@Transactional
public interface RenderHostMapper {
    int insertSelective(RenderHost record);

    int updateByPrimaryKeySelective(RenderHost record);
  
    int deleteByPrimaryKey(Integer id);
        
    RenderHost selectByPrimaryKey(Integer id);
    
    int selectCount(RenderHostSearch renderHostSearch);
    
	List<RenderHost> selectPaginatedList(
			RenderHostSearch renderHostSearch);
			
    List<RenderHost> selectList(RenderHost renderHost);

	Object PrimaryTask();

	RenderHost selectMinTaskAvailableHost();
    
	/**
	 * 其他
	 * 
	 */
}
