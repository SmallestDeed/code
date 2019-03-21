package com.sandu.system.service;

import com.sandu.system.model.ResRenderVideo;

import java.util.List;


/**
 * @version V1.0
 * @Title: ResRenderVideoService.java
 * @Package com.sandu.system.service
 * @Description:渲染视频资源库-渲染视频资源表Service
 * @createAuthor pandajun
 * @CreateDate 2017-07-07 19:03:29
 */
public interface ResRenderVideoService {
    /**
     * 新增数据
     *
     * @param resRenderVideo
     * @return int
     */
    int add(ResRenderVideo resRenderVideo);

    /**
     * 更新数据
     *
     * @param resRenderVideo
     * @return int
     */
    int update(ResRenderVideo resRenderVideo);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return ResRenderVideo
     */
    ResRenderVideo get(Integer id);

    /**
     * 所有数据
     *
     * @param resRenderVideo
     * @return List<ResRenderVideo>
     */
    List<ResRenderVideo> getList(ResRenderVideo resRenderVideo);


    /**
     * 其他
     *
     */

    /**
     * 根据封面图id（渲染任务id）查到视频资源
     *
     * @param sysTaskPicId
     * @return
     */
    ResRenderVideo selectBySysTaskPicId(Integer sysTaskPicId);
}
