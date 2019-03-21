package com.nork.render.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.render.model.RenderTask;
import com.nork.render.model.search.RenderTaskSearch;

/**   
 * @Title: RenderTaskMapper.java 
 * @Package com.nork.render.dao
 * @Description:渲染-渲染任务Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-01-17 20:31:06
 * @version V1.0   
 */
@Repository
@Transactional
public interface RenderTaskMapper {
    int insertSelective(RenderTask record);

    int updateByPrimaryKeySelective(RenderTask record);
  
    int deleteByPrimaryKey(Integer id);
        
    RenderTask selectByPrimaryKey(Integer id);
    
    int selectCount(RenderTaskSearch renderTaskSearch);
    
	List<RenderTask> selectPaginatedList(
			RenderTaskSearch renderTaskSearch);
			
    List<RenderTask> selectList(RenderTask renderTask);

	int selectUsingTaskCount(RenderTaskSearch renderTaskSearch);
    
	RenderTask selectByTaskId(Integer taskId);
	/**
	 * 其他
	 * 
	 */
}
