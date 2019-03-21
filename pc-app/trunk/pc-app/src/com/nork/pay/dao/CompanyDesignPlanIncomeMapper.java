package com.nork.pay.dao;

import com.nork.pay.model.CompanyDesignPlanIncome;
import com.nork.pay.model.CompanyDesignPlanIncomeAggregated;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDesignPlanIncomeMapper {

    void insert(CompanyDesignPlanIncome companyDesignPlanIncome);

    CompanyDesignPlanIncomeAggregated getCompanyAggregatedByCompanyId(Integer companyId);

    void insertDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated cdpia);

    void updateCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated companyAggregated);
}
