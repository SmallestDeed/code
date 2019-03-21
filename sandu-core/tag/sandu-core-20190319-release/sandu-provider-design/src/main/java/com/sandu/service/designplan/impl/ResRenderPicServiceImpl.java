package com.sandu.service.designplan.impl;

import com.sandu.api.designplan.model.ResRenderPic;
import com.sandu.api.designplan.service.ResRenderPicService;
import com.sandu.service.designplan.dao.ResRenderPicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kono on 2018/6/2 0002.
 */
@Service("resRenderPicService")
public class ResRenderPicServiceImpl implements ResRenderPicService {

    @Autowired
    private ResRenderPicDao resRenderPicDao;

    @Override
    public List<ResRenderPic> getResRenderPicListByRecommendedIds(List <Integer> planRecommendedIds) {
        return resRenderPicDao.selectRenderPicListByRecommendIds(planRecommendedIds);
    }

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
    @Override
    public List<ResRenderPic> selectBySceneIdAndKeyAndRenderType(Integer designSceneId, String fileKey, Integer renderingType) {
        return resRenderPicDao.selectBySceneIdAndKeyAndRenderType(designSceneId, fileKey, renderingType);
    }

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
    @Override
    public List<ResRenderPic> selectByRecommendedIdAndKeyAndRenderType(Integer designPlanRecommendedId, String fileKey, Integer renderingType) {
        return resRenderPicDao.selectByRecommendedIdAndKeyAndRenderType(designPlanRecommendedId, fileKey, renderingType);
    }
}
