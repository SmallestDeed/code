package com.nork.design.service;

import java.util.Map;

public interface CompanyDesignPlanIncomeService {
    Boolean isExitsBuyDesignPlanCopyRight(Long userId, Integer recommendedPlanId, Integer planType);

    Map<String,Object> checkReplaceDesignPlanPay(Long userId, Integer recommendedPlanId, String platformCode, Integer planType);

    Integer planSceneIdTransformRecommendedPlanId(Integer designPlanRenderSceneId);

    Integer getFullHouseDesignPlanId(String uuid);
}
