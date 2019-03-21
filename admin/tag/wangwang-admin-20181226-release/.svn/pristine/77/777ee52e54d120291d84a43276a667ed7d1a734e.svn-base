package com.sandu.service.solution.impl;


import com.sandu.api.solution.model.PlanDecoratePrice;
import com.sandu.api.solution.service.PlanDecoratePriceService;
import com.sandu.service.solution.dao.PlanDecoratePriceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.sandu.api.solution.model.PlanDecoratePrice.PLAN_DECORATE_PRICE_FULLHOUSE;
import static com.sandu.api.solution.model.PlanDecoratePrice.PLAN_DECORATE_PRICE_RECOMMEND;

/**
 * @Author: YuXingchi
 * @Description:
 */

@Service("planDecoratePriceService")
public class PlanDecoratePriceServiceImpl implements PlanDecoratePriceService {

    @Autowired
    private PlanDecoratePriceMapper planDecoratePriceMapper;

    @Override
    public int deleteByFullHouseId(Integer fullHouseId) {
        return planDecoratePriceMapper.deleteByFullHouseId(fullHouseId);
    }

    @Override
    public int deleteByRecommendId(Integer recommendId) {
        return planDecoratePriceMapper.deleteByRecommonedId(recommendId);
    }

    @Override
    public void updateByPlanRecommendId(PlanDecoratePrice planDecoratePrice) {
        planDecoratePriceMapper.updateByPlanRecommendId(planDecoratePrice);
    }

    @Override
    public List<PlanDecoratePrice> listByPlanIdAndType(Integer planId, Integer planType) {
        if (planType.equals(PLAN_DECORATE_PRICE_RECOMMEND)) {
            return planDecoratePriceMapper.selectByDesignPlanId(planId);
        }
        if (planType.equals(PLAN_DECORATE_PRICE_FULLHOUSE)) {
            return planDecoratePriceMapper.selectByFullHouseId(planId);
        }
        return Collections.emptyList();
    }


}
