package com.sandu.common.objectconvert.designplan;

import com.sandu.designplan.model.DesignPlanRecommended;
import com.sandu.designplan.vo.DesignPlanDetailVo;

/**
 * 设计方案详情对象转换类
 */
public class DesignPlanDetail {

    //转换为设计方案VO对象
    public static final DesignPlanDetailVo parseToDesignPlanDetailVo(DesignPlanRecommended designPlanRecommended) {
        DesignPlanDetailVo designPlanDetailVo = null;

        if (designPlanRecommended != null) {
            //初始化对象
            designPlanDetailVo = new DesignPlanDetailVo();
            //加载值
            designPlanDetailVo.setPlanCode(designPlanRecommended.getPlanCode());
            designPlanDetailVo.setPlanName(designPlanRecommended.getPlanName());
            designPlanDetailVo.setUserId(designPlanRecommended.getUserId());
            designPlanDetailVo.setDesignSourceType(designPlanRecommended.getDesignSourceType());
            designPlanDetailVo.setDesignId(designPlanRecommended.getDesignId());
            designPlanDetailVo.setDesignStyleId(designPlanRecommended.getDesignStyleId());
            designPlanDetailVo.setPicId(designPlanRecommended.getPicId());
            designPlanDetailVo.setSpaceCommonId(designPlanRecommended.getSpaceCommonId());
            designPlanDetailVo.setPlanDesc(designPlanRecommended.getPlanDesc());
            designPlanDetailVo.setCreator(designPlanRecommended.getCreator());
            designPlanDetailVo.setGmtCreate(designPlanRecommended.getGmtCreate());
            designPlanDetailVo.setRemark(designPlanRecommended.getRemark());
            designPlanDetailVo.setShareTotal(designPlanRecommended.getShareTotal());
            designPlanDetailVo.setCoverPicId(designPlanRecommended.getCoverPicId());
            designPlanDetailVo.setDesignRecommendedStyleName(designPlanRecommended.getDesignRecommendedStyleName());
            designPlanDetailVo.setSpaceCode(designPlanRecommended.getSpaceCode());
            designPlanDetailVo.setSpaceName(designPlanRecommended.getSpaceName());
            designPlanDetailVo.setSpaceAreas(designPlanRecommended.getSpaceAreas());
            designPlanDetailVo.setRenderPicList(designPlanRecommended.getPicList());
            designPlanDetailVo.setRenderMap(designPlanRecommended.getRenderMap());
        }

        return designPlanDetailVo;
    }
}
