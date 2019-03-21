package com.sandu.service.solution.dao;

import com.sandu.api.solution.model.ResRenderVideo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**   
 * @Title: ResRenderVideoMapper.java 
 * @Package com.nork.system.dao
 * @Description:渲染视频资源库-渲染视频资源表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-07-07 19:03:29
 * @version V1.0   
 */
public interface ResRenderVideoMapper {
    int insertSelective(ResRenderVideo record);

    int updateByPrimaryKeySelective(ResRenderVideo record);
  
    int deleteByPrimaryKey(Integer id);
        
    ResRenderVideo selectByPrimaryKey(Integer id);
    
    List<ResRenderVideo> selectList(ResRenderVideo resRenderVideo);
    
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

	List<ResRenderVideo> listPicForFixPath(@Param("limit") int limit);
}
