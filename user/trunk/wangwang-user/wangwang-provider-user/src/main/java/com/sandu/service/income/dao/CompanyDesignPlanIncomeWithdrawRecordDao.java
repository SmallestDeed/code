package com.sandu.service.income.dao;

import com.sandu.api.income.model.CompanyDesignPlanIncomeTransferRecord;
import com.sandu.api.income.model.CompanyDesignPlanIncomeWithdrawRecord;
import com.sandu.api.income.search.CompanyDesignPlanIncomeWithdrawRecordSearch;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyDesignPlanIncomeWithdrawRecordDao {

    void insert(CompanyDesignPlanIncomeWithdrawRecord record);

    List<CompanyDesignPlanIncomeWithdrawRecord> selectWithdrawList(@Param("companyId") Long companyId,
                                                                   @Param("withdrawStatus")Integer withdrawStatus,
                                                                   @Param("withdrawType") Integer withdrawType,
                                                                   @Param("applyUserName")String applyUserName,
                                                                   @Param("start")Integer start,
                                                                   @Param("limit")Integer limit);

    int countWithdrawList(@Param("companyId") Long companyId,
                          @Param("withdrawStatus")Integer withdrawStatus,
                          @Param("withdrawType") Integer withdrawType,
                          @Param("applyUserName")String applyUserName
    );

	long selectCountBySearch(CompanyDesignPlanIncomeWithdrawRecordSearch search);
}
