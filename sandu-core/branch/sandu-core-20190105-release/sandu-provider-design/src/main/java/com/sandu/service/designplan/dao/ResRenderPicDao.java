package com.sandu.service.designplan.dao;

import com.sandu.api.designplan.model.ResRenderPic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kono on 2018/6/2 0002.
 */
@Repository
public interface ResRenderPicDao {

    /**
     * 通过推荐方案ids查询渲染图列表
     * @param planRecommendedIds
     * @return
     */
    List<ResRenderPic> selectRenderPicListByRecommendIds(@Param("planRecommendedIds")List<Integer> planRecommendedIds);

    /**
     * created by zhangchengda
     * 2018/8/20 19:23
     * 通过渲染图方案ID、fileKey和渲染类型查询渲染图
     *
     * @param designSceneId 渲染图方案ID
     * @param fileKey       fileKey
     * @param renderingType 渲染类型
     * @return 渲染图数据
     */
    List<ResRenderPic> selectBySceneIdAndKeyAndRenderType(@Param("designSceneId") Integer designSceneId,
                                                          @Param("fileKey") String fileKey,
                                                          @Param("renderingType") Integer renderingType);

    /**
     * created by zhangchengda
     * 2018/8/30 15:48
     * 通过推荐方案ID、fileKey和渲染类型查询渲染图
     *
     * @param designPlanRecommendedId
     * @param fileKey
     * @param renderingType
     * @return
     */
    List<ResRenderPic> selectByRecommendedIdAndKeyAndRenderType(@Param("designPlanRecommendedId") Integer designPlanRecommendedId,
                                                                @Param("fileKey") String fileKey,
                                                                @Param("renderingType") Integer renderingType);
}
