package com.sandu.api.solution.service;


import com.sandu.api.solution.model.PlanDecoratePrice;

import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 */
public interface PlanDecoratePriceService {

    /**
     * 根据全屋id删除报价信息
     *
     * @param fullHouseId
     * @return
     */
    int deleteByFullHouseId(Integer fullHouseId);

    /**
     * 根据推荐方案id删除报价信息
     * @param recommendId
     * @return
     */
    int deleteByRecommendId(Integer recommendId);

    void updateByPlanRecommendId(PlanDecoratePrice planDecoratePrice);

    List<PlanDecoratePrice> listByPlanIdAndType(Integer planId,Integer planType);
}
