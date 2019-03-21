package com.sandu.service.income.dao;

import com.sandu.api.income.model.CompanyDesignPlanIncome;
import com.sandu.api.income.model.CompanyDesignPlanIncomeAggregated;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyDesignPlanIncomeDao {
    int countCompanyIncome(@Param("companyId") Long comapnyId, @Param("platformId") Long platformId, @Param("planCode") String planCode,@Param("planCreator") String planCreator);

    List<CompanyDesignPlanIncome> selectCompanyIncomeList(@Param("companyId") Long comapnyId, @Param("platformId") Long platformId, @Param("planCode") String planCode, @Param("planCreator")String planCreator, @Param("start") Integer start, @Param("limit") Integer limit);

    void batchInsertIncomes(@Param("lists") List<CompanyDesignPlanIncome> lists);

    int updateCurrentDubiANDTotalIncomeDubi(@Param("planPrice")Double planPrice, @Param("companyId")Integer companyId);

    void insertDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated cdpia);
}
