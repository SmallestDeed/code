package com.sandu.service.income.dao;

import com.sandu.api.income.model.CompanyDesignPlanIncomeAggregated;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/6/12  18:23
 */
@Repository
public interface CompanyDesignPlanIncomeAggregatedDao {

    List<CompanyDesignPlanIncomeAggregated> getAggrgatedByCompanyId(Long companyId);

    void updateTransferDubi(@Param("companyId") Long companyId, @Param("dubi")Double dubi);

    void updateWithdrawDubi(@Param("companyId") Long companyId, @Param("dubi")Double dubi);
}
