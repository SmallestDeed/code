package com.nork.design.service;

import com.nork.design.model.CompanyDesignPlanIncome;
import com.nork.design.model.CompanyDesignPlanIncomeAggregated;
import com.nork.design.model.UserDesignPlanPurchaseRecord;

public interface CompanyDesignPlanIncomeService {

    void insert(CompanyDesignPlanIncome companyDesignPlanIncome);

    CompanyDesignPlanIncomeAggregated getCompanyAggregatedByCompanyId(Integer id);

    void addCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated cdpia);

    void updateCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated companyAggregated);

}
