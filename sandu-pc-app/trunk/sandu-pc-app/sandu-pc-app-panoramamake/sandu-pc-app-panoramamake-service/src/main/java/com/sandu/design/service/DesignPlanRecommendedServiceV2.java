package com.sandu.design.service;

import com.sandu.design.model.input.DesignPlanRecommendedSearch;
import com.sandu.design.model.output.DesignPlanRecommendedVo;
import com.sandu.design.model.output.DesignPlanStyleVo;

import java.util.List;

public interface DesignPlanRecommendedServiceV2 {

    /**
     * 查询一键方案数量
     * @param designPlanRecommendedSearch
     * @return
     */
    int selectCount(DesignPlanRecommendedSearch designPlanRecommendedSearch);

    /**
     * 查询一键方案列表
     * @param designPlanRecommendedSearch
     * @return
     */
    List<DesignPlanRecommendedVo> selectList(DesignPlanRecommendedSearch designPlanRecommendedSearch);

    /**
     * 通过方案类型获取对应的方案风格 TEMP
     * @param designPlanType
     * @return
     */
    List<DesignPlanStyleVo> getPlanStyleList(Integer designPlanType);

}
