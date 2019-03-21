package com.sandu.api.income.service;

import com.sandu.api.income.model.CompanyDesignPlanIncome;
import com.sandu.api.income.model.CompanyDesignPlanIncomeAggregated;

import java.util.List;

public interface CompanyDesignPlanIncomeService {
    int countCompanyIncome(Long comapnyId, Long platformId, String planCode,String planCreator);

    List<CompanyDesignPlanIncome> selectCompanyIncomeList(Long comapnyId, Long platformId, String planCode, String planCreator,Integer start, Integer limit);

    void batchInsertIncomes(List<CompanyDesignPlanIncome> lists);

    int updateCurrentDubiANDTotalIncomeDubi(Double planPrice, Integer companyId);

    void addCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated cdpia);
}
