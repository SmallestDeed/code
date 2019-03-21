package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.ResRenderVideo;
import com.nork.system.model.search.ResRenderVideoSearch;

/**   
 * @Title: ResRenderVideoMapper.java 
 * @Package com.nork.system.dao
 * @Description:渲染视频资源库-渲染视频资源表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-07-07 19:03:29
 * @version V1.0   
 */
@Repository
@Transactional
public interface ResRenderVideoMapper {
    int insertSelective(ResRenderVideo record);

    int updateByPrimaryKeySelective(ResRenderVideo record);
  
    int deleteByPrimaryKey(Integer id);
        
    ResRenderVideo selectByPrimaryKey(Integer id);
    
    int selectCount(ResRenderVideoSearch resRenderVideoSearch);
    
	List<ResRenderVideo> selectPaginatedList(
			ResRenderVideoSearch resRenderVideoSearch);
			
    List<ResRenderVideo> selectList(ResRenderVideo resRenderVideo);
    
	/**
	 * 其他
	 * 
	 */
    
    /**
     * 根据封面图id（渲染任务id）查到视频资源
     */
    ResRenderVideo selectBySysTaskPicId(Integer sysTaskPicId);
    
    /**
     * 根据封面图id（渲染任务id）查到自动渲染的视频资源
     * @param sysTaskPicId
     * @return
     */
    ResRenderVideo selectAutoRenderVideoBySysTaskPicId(Integer sysTaskPicId);
}
