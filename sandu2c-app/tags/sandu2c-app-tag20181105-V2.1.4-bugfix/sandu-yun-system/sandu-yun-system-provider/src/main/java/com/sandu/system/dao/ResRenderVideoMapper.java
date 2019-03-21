package com.sandu.system.dao;

import com.sandu.system.model.ResRenderVideo;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @version V1.0
 * @Title: ResRenderVideoMapper.java
 * @Package com.sandu.system.dao
 * @Description:渲染视频资源库-渲染视频资源表Mapper
 * @createAuthor pandajun
 * @CreateDate 2017-07-07 19:03:29
 */
@Repository
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
}
