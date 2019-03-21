package com.sandu.service.income.impl;


import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.income.model.CompanyDesignPlanIncomeWithdrawRecord;
import com.sandu.api.income.search.CompanyDesignPlanIncomeWithdrawRecordSearch;
import com.sandu.api.income.service.CompanyDesignPlanIncomeWithdrawRecordService;
import com.sandu.api.system.output.IsAllowDrawingsResultDTO;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.common.Utils;
import com.sandu.service.income.dao.CompanyDesignPlanIncomeWithdrawRecordDao;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service(value = "companyDesignPlanIncomeWithdrawRecordService")
@Log4j2
public class CompanyDesignPlanIncomeWithdrawRecordServiceImpl implements CompanyDesignPlanIncomeWithdrawRecordService {

	private static final String LOGPREFIX = "[提现记录表]:";
	
    @Autowired
    private CompanyDesignPlanIncomeWithdrawRecordDao companyDesignPlanIncomeWithdrawRecordDao;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public void addWithdrawRecord(Long companyId, Long userId, Integer withdrawType, Double dubi, Long bankcardInfoId, String mobile) {

        CompanyDesignPlanIncomeWithdrawRecord record = new CompanyDesignPlanIncomeWithdrawRecord();

        BaseCompany baseCompany = baseCompanyService.queryById(companyId);
        SysUser sysUser = sysUserService.get(userId.intValue());

        Date now = new Date();
        Double applyAmount  = dubi / 10;
        record.setCompanyId(companyId);
        record.setCompanyName(baseCompany.getCompanyName());
        record.setApplyUserId(userId);
 
        record.setApplyUserName(StringUtils.isNotEmpty(sysUser.getUserName())?sysUser.getUserName():StringUtils.isNotEmpty(sysUser.getMobile())?sysUser.getMobile() : sysUser.getNickName());
        record.setApplyUserMobile(mobile);
        record.setApplyTime(now);
        record.setApplyDubi(dubi);
        record.setApplyAmount(applyAmount);
        record.setWithdrawType(withdrawType);
        record.setWithdrawStatus(0);
        record.setRevceiveUserId(userId);
      
        record.setRevceiveUserName(StringUtils.isNotEmpty(sysUser.getUserName())?sysUser.getUserName():StringUtils.isNotEmpty(sysUser.getMobile())?sysUser.getMobile() : sysUser.getNickName());
        record.setIsDeleted(0);
        //计算实际提现金额
        BigDecimal amount = new BigDecimal(applyAmount.toString());
        BigDecimal rate = new BigDecimal(baseCompany.getWithdrawRate().toString());
        record.setRealWithdrawAmount(amount.multiply(rate).doubleValue());
        record.setBankcardInfoId(bankcardInfoId);

        companyDesignPlanIncomeWithdrawRecordDao.insert(record);
    }

    @Override
    public List<CompanyDesignPlanIncomeWithdrawRecord> selectWithdrawList(Long companyId, Integer withdrawType, String operator, Integer withdrawStatus, Integer start, Integer limit) {
        return companyDesignPlanIncomeWithdrawRecordDao.selectWithdrawList(companyId,withdrawStatus,withdrawType,operator,start,limit);
    }

    @Override
    public int countWithdrawList(Long companyId, Integer withdrawType, String operator, Integer withdrawStatus) {
        return companyDesignPlanIncomeWithdrawRecordDao.countWithdrawList(companyId,withdrawStatus,withdrawType,operator);
    }

	@Override
	public IsAllowDrawingsResultDTO getIsAllowDrawingsResultDTO(Long userId) {
		if(userId == null) {
			return new IsAllowDrawingsResultDTO(false, "未检测到登录用户信息, 请重新登录");
		}
		
		// 获取本月的开始日期, eg: 比如现在是2018-12-15号, 我想要的时间是2018-12-1号
		Date date = Utils.getMonthBeginDate(new Date());
		SysUser sysUser = sysUserService.get(userId.intValue());
		if(sysUser == null) {
			return new IsAllowDrawingsResultDTO(false, "用户信息没有查询到(userId = " + userId.intValue() + ")");
		}
		
		CompanyDesignPlanIncomeWithdrawRecordSearch search = new CompanyDesignPlanIncomeWithdrawRecordSearch();
		search.setApplyTimeAfter(date);
		search.setIsDeleted(0);
		/*search.setApplyUserId(userId);*/
		search.setCompanyId(sysUser.getCompanyId());
		long count = this.getCountBySearch(search);
		
		if(count >= 2) {
			return new IsAllowDrawingsResultDTO(false, "贵公司本月已申请2次提现, 需下个月才可继续提现.");
		}
		return new IsAllowDrawingsResultDTO(true, "");
	}

	@Override
	public long getCountBySearch(CompanyDesignPlanIncomeWithdrawRecordSearch search) {
		if(search == null) {
			log.error(LOGPREFIX + "search = null");
			return 0;
		}
		
		return companyDesignPlanIncomeWithdrawRecordDao.selectCountBySearch(search);
	}
}
