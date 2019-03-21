package com.sandu.api.income.service;

import com.sandu.api.income.model.CompanyDesignPlanIncomeAggregated;

import java.util.List;

public interface CompanyDesignPlanIncomeAggregatedService {
    List<CompanyDesignPlanIncomeAggregated> getAggrgatedByCompanyId(Long companyId);

    void updateTransferDubi(Long companyId, Double dubi);

    void updateWithdrawDubi(Long companyId, Double dubi);

    boolean checkCurrentDubiEnough(Double dubi,Long companyId);
}
