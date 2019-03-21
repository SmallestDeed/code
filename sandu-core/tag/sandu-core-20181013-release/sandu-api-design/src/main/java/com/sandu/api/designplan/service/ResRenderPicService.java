package com.sandu.api.designplan.service;

import com.sandu.api.designplan.model.ResRenderPic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Desc 渲染图片资源库-渲染图片资源库Service
 */
public interface ResRenderPicService {


    /**
     * 根据推荐方案ID查询渲染图列表
     *
     * @param planRecommendedIds
     * @return
     */
    List<ResRenderPic> getResRenderPicListByRecommendedIds(List<Integer> planRecommendedIds);

    /**
     * created by zhangchengda
     * 2018/8/20 19:23
     * 通过效果图方案ID、fileKey和渲染类型查询渲染图
     *
     * @param designSceneId 效果图方案ID
     * @param fileKey       fileKey
     * @param renderingType 渲染类型
     * @return 渲染图数据
     */
    List<ResRenderPic> selectBySceneIdAndKeyAndRenderType(Integer designSceneId, String fileKey, Integer renderingType);

    /**
     * created by zhangchengda
     * 2018/8/30 15:46
     * 通过推荐方案ID、fileKey和渲染类型查询渲染图
     *
     * @param designPlanRecommendedId 推荐方案方案ID
     * @param fileKey                 fileKey
     * @param renderingType           渲染类型
     * @return 渲染图数据
     */
    List<ResRenderPic> selectByRecommendedIdAndKeyAndRenderType(Integer designPlanRecommendedId, String fileKey, Integer renderingType);
}
