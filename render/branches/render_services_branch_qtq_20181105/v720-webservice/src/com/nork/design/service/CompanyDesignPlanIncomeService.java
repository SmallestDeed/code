package com.nork.design.service;

import com.nork.design.model.CompanyDesignPlanIncome;
import com.nork.design.model.CompanyDesignPlanIncomeAggregated;
import com.nork.design.model.UserDesignPlanPurchaseRecord;

import java.util.Map;

public interface CompanyDesignPlanIncomeService {

    void insert(CompanyDesignPlanIncome companyDesignPlanIncome);

    CompanyDesignPlanIncomeAggregated getCompanyAggregatedByCompanyId(Integer id);

    void addCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated cdpia);

    void updateCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated companyAggregated);

    Boolean isExitsBuyDesignPlanCopyRight(Long userId, Integer recommendedPlanId,Integer planType);

    Map<String,Object> checkReplaceDesignPlanPay(Long userId, Integer recommendedPlanId, String platformCode,Integer planType);

    Integer planSceneIdTransformRecommendedPlanId(Integer designPlanRenderSceneId);

    int updateCurrentDubiANDTotalIncomeDubi(Double planPrice, Integer companyId);

    Integer getFullHouseDesignPlanId(String uuid);
}
