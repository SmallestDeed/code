package com.sandu.api.income.service;


import com.sandu.api.income.model.CompanyDesignPlanIncomeWithdrawRecord;
import com.sandu.api.income.search.CompanyDesignPlanIncomeWithdrawRecordSearch;
import com.sandu.api.system.output.IsAllowDrawingsResultDTO;

import java.util.List;

public interface CompanyDesignPlanIncomeWithdrawRecordService {

    void addWithdrawRecord(Long companyId, Long id, Integer withdrawType, Double dubi, Long bankcardInfoId, String mobile);

    List<CompanyDesignPlanIncomeWithdrawRecord> selectWithdrawList(Long companyId, Integer withdrawType, String operator, Integer withdrawStatus, Integer start, Integer limit);

    int countWithdrawList(Long companyId, Integer withdrawType, String operator, Integer withdrawStatus);

    /**
     * 验证该用户还有没有提现机会
     * 逻辑为:
     * 1.每个月只能提现两次
     * 
     * @author huangsongbo
     * @param userId
     * @return
     */
	IsAllowDrawingsResultDTO getIsAllowDrawingsResultDTO(Long userId);

	/**
	 * 
	 * @author huangsongbo
	 * @param search
	 * @return
	 */
	long getCountBySearch(CompanyDesignPlanIncomeWithdrawRecordSearch search);
	
}
