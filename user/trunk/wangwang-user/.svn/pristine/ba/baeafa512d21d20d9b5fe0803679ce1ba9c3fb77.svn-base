package com.sandu.service.income.impl;

import com.sandu.api.income.model.CompanyDesignPlanIncomeAggregated;
import com.sandu.api.income.service.CompanyDesignPlanIncomeAggregatedService;
import com.sandu.service.income.dao.CompanyDesignPlanIncomeAggregatedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("companyDesignPlanIncomeAggregatedService")
public class CompanyDesignPlanIncomeAggregatedServiceImpl implements CompanyDesignPlanIncomeAggregatedService{

    @Autowired
    private CompanyDesignPlanIncomeAggregatedDao companyDesignPlanIncomeAggregatedDao;

    @Override
    public List<CompanyDesignPlanIncomeAggregated> getAggrgatedByCompanyId(Long companyId) {
        return companyDesignPlanIncomeAggregatedDao.getAggrgatedByCompanyId(companyId);
    }

    @Override
    public void updateTransferDubi(Long companyId, Double dubi) {
        companyDesignPlanIncomeAggregatedDao.updateTransferDubi(companyId,dubi);
    }

    @Override
    public void updateWithdrawDubi(Long companyId, Double dubi) {
        companyDesignPlanIncomeAggregatedDao.updateWithdrawDubi(companyId,dubi);
    }

    @Override
    public boolean checkCurrentDubiEnough(Double dubi, Long companyId) {
        List<CompanyDesignPlanIncomeAggregated> lists = companyDesignPlanIncomeAggregatedDao.getAggrgatedByCompanyId(companyId);
        if (Optional.ofNullable(lists).isPresent() && !lists.isEmpty()){
            return lists.stream().findFirst().get().getCurrentDubi() >= dubi;
        }
        return Boolean.FALSE;
    }

}
