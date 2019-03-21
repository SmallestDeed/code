package com.nork.design.dao;

import com.nork.design.model.CompanyDesignPlanIncome;
import com.nork.design.model.CompanyDesignPlanIncomeAggregated;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDesignPlanIncomeMapper {

    void insert(CompanyDesignPlanIncome companyDesignPlanIncome);

    CompanyDesignPlanIncomeAggregated getCompanyAggregatedByCompanyId(Integer companyId);

    void insertDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated cdpia);

    void updateCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated companyAggregated);
}
