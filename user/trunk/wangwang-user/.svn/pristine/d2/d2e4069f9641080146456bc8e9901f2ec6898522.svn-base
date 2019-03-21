package com.sandu.service.income.impl;

import com.sandu.api.company.model.Company;
import com.sandu.api.income.model.CompanyDesignPlanIncome;
import com.sandu.api.income.model.CompanyDesignPlanIncomeAggregated;
import com.sandu.api.income.service.CompanyDesignPlanIncomeService;
import com.sandu.service.income.dao.CompanyDesignPlanIncomeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("companyDesignPlanIncomeService")
public class CompanyDesignPlanIncomeServiceImpl implements CompanyDesignPlanIncomeService{

    @Autowired
    private CompanyDesignPlanIncomeDao companyDesignPlanIncomeDao;

    @Override
    public int countCompanyIncome(Long comapnyId, Long platformId, String planCode,String planCreator) {
        return companyDesignPlanIncomeDao.countCompanyIncome(comapnyId,platformId,planCode,planCreator);
    }

    @Override
    public List<CompanyDesignPlanIncome> selectCompanyIncomeList(Long comapnyId, Long platformId, String planCode,String planCreator,Integer start, Integer limit) {
        return companyDesignPlanIncomeDao.selectCompanyIncomeList(comapnyId,platformId,planCode,planCreator,start,limit);
    }

    @Override
    public void batchInsertIncomes(List<CompanyDesignPlanIncome> lists) {
        companyDesignPlanIncomeDao.batchInsertIncomes(lists);
    }

    @Override
    public int updateCurrentDubiANDTotalIncomeDubi(Double planPrice, Integer companyId) {
        return companyDesignPlanIncomeDao.updateCurrentDubiANDTotalIncomeDubi(planPrice,companyId);
    }

    @Override
    public void addCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated cdpia) {
        companyDesignPlanIncomeDao.insertDesignPlanIncomeAggregated(cdpia);
    }
}
