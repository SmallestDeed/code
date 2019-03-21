package com.nork.pay.service.impl;

import com.nork.pay.dao.CompanyDesignPlanIncomeMapper;
import com.nork.pay.model.CompanyDesignPlanIncome;
import com.nork.pay.model.CompanyDesignPlanIncomeAggregated;
import com.nork.pay.service.CompanyDesignPlanIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("companyDesignPlanIncomeService")
public class CompanyDesignPlanIncomeServiceImpl implements CompanyDesignPlanIncomeService {

    @Autowired
    private CompanyDesignPlanIncomeMapper companyDesignPlanIncomeMapper;

    @Override
    public void insert(CompanyDesignPlanIncome companyDesignPlanIncome) {
        companyDesignPlanIncomeMapper.insert(companyDesignPlanIncome);
    }

    @Override
    public CompanyDesignPlanIncomeAggregated getCompanyAggregatedByCompanyId(Integer companyId) {
        return companyDesignPlanIncomeMapper.getCompanyAggregatedByCompanyId(companyId);
    }

    @Override
    public void addCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated cdpia) {
        companyDesignPlanIncomeMapper.insertDesignPlanIncomeAggregated(cdpia);
    }

    @Override
    public void updateCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated companyAggregated) {
        companyDesignPlanIncomeMapper.updateCompanyDesignPlanIncomeAggregated(companyAggregated);
    }
}
