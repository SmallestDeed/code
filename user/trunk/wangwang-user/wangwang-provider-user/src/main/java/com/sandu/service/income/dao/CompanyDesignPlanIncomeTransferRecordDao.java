package com.sandu.service.income.dao;

import com.sandu.api.income.model.CompanyDesignPlanIncomeTransferRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyDesignPlanIncomeTransferRecordDao {
    void insert(CompanyDesignPlanIncomeTransferRecord record);

    int countTransferRecordList(@Param("companyId") Long companyId,@Param("transferUserName") String transferUserName);

    List<CompanyDesignPlanIncomeTransferRecord> selectTransferRecordList(@Param("companyId") Long companyId,@Param("transferUserName") String transferUserName, @Param("start") Integer start,@Param("limit") Integer limit);
}
