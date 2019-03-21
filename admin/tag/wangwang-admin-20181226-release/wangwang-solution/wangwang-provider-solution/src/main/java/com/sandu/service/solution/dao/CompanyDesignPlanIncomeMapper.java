package com.sandu.service.solution.dao;

import com.sandu.api.solution.input.CompanyIncomeQuery;
import com.sandu.api.solution.model.bo.CompanyDesignPlanIncomeAggregatedBO;
import com.sandu.api.solution.model.bo.CompanyDesignPlanIncomeBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CompanyDesignPlanIncomeMapper {

    List<CompanyDesignPlanIncomeBO> listCompanyIncome(CompanyIncomeQuery query);

    CompanyDesignPlanIncomeAggregatedBO selectCompanyIncomeAggregated(Long companyId);

    Set<Integer> isExitsUserBuySaleDesignPlan(@Param("planIds") Set<Integer> planIds, @Param("userId") Long userId,@Param("planType") Integer planType);
}
