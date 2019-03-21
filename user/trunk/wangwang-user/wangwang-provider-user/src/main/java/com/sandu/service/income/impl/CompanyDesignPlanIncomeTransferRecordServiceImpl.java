package com.sandu.service.income.impl;

import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.income.model.CompanyDesignPlanIncomeTransferRecord;
import com.sandu.api.income.service.CompanyDesignPlanIncomeTransferRecordService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.service.income.dao.CompanyDesignPlanIncomeTransferRecordDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("companyDesignPlanIncomeTransferRecordService")
public class CompanyDesignPlanIncomeTransferRecordServiceImpl implements CompanyDesignPlanIncomeTransferRecordService {

    @Autowired
    private CompanyDesignPlanIncomeTransferRecordDao companyDesignPlanIncomeTransferRecordDao;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Override
    public void addTransferRecord(Long transferUserId, Long receiveUserId, Double dubi) {

        CompanyDesignPlanIncomeTransferRecord record = new CompanyDesignPlanIncomeTransferRecord();
        //获取转出人信息
        SysUser transferUser = sysUserService.get(transferUserId.intValue());
        //获取转入人信息
        SysUser receviceUser = sysUserService.get(receiveUserId.intValue());
        //获取转出人公司信息
        BaseCompany baseCompany = baseCompanyService.queryById(transferUser.getCompanyId());
        Date now = new Date();
        record.setCompanyId(baseCompany.getId());
        record.setCompanyName(baseCompany.getCompanyName());
        record.setTransferUserId(transferUserId);
        record.setTransferUserName(StringUtils.isNotEmpty(transferUser.getUserName())?transferUser.getUserName():StringUtils.isNotEmpty(transferUser.getMobile())?transferUser.getMobile() : transferUser.getNickName());
        record.setTransferTime(now);
        record.setTransferDubi(dubi);
        record.setIsDeleted(0);
        record.setReceiveTime(now);
        record.setReceiveUserId(receiveUserId);
        record.setReceiveUserName(StringUtils.isNotEmpty(receviceUser.getUserName())?receviceUser.getUserName():StringUtils.isNotEmpty(receviceUser.getMobile())?receviceUser.getMobile() : receviceUser.getNickName());
        record.setTransferStatus(1);
        record.setTransferAmount(dubi/10);

        companyDesignPlanIncomeTransferRecordDao.insert(record);
    }

    @Override
    public int countTransferRecordList(Long companyId, String transferUserName) {
        return companyDesignPlanIncomeTransferRecordDao.countTransferRecordList(companyId,transferUserName);
    }

    @Override
    public List<CompanyDesignPlanIncomeTransferRecord> selectTransferRecordList(Long companyId, String transferUserName, Integer start, Integer limit) {
        return companyDesignPlanIncomeTransferRecordDao.selectTransferRecordList(companyId,transferUserName, start,limit);
    }
}
