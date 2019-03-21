package com.nork.design.dao;

import com.nork.design.model.CompanyDesignPlanIncome;
import com.nork.design.model.CompanyDesignPlanIncomeAggregated;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDesignPlanIncomeMapper {

    void insert(CompanyDesignPlanIncome companyDesignPlanIncome);

    CompanyDesignPlanIncomeAggregated getCompanyAggregatedByCompanyId(Integer companyId);

    void insertDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated cdpia);

    void updateCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated companyAggregated);

    int countDesignPlanIncomeRecordByUserIdAndPlanId(@Param("userId") Long userId, @Param("planId") Integer planId,@Param("planType")Integer planType);

    int updateCurrentDubiANDTotalIncomeDubi(@Param("planPrice")Double planPrice, @Param("companyId")Integer companyId);

    Integer getFullHouseDesignPlanId(String uuid);

    int selectCount(CompanyDesignPlanIncome companyDesignPlanIncome);

}
