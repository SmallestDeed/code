package com.nork.design.service.impl;

import com.nork.design.dao.CompanyDesignPlanIncomeMapper;
import com.nork.design.model.CompanyDesignPlanIncome;
import com.nork.design.model.CompanyDesignPlanIncomeAggregated;
import com.nork.design.model.DesignPlanBO;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.service.CompanyDesignPlanIncomeService;
import com.nork.design.service.DesignPlanRecommendedService;
import com.nork.payAccount.model.PayAccount;
import com.nork.payAccount.service.PayAccountService;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service("companyDesignPlanIncomeService")
public class CompanyDesignPlanIncomeServiceImpl implements CompanyDesignPlanIncomeService {

    private Logger logger = LoggerFactory.getLogger(CompanyDesignPlanIncomeServiceImpl.class);

    @Autowired
    private CompanyDesignPlanIncomeMapper companyDesignPlanIncomeMapper;

    @Autowired
    private DesignPlanRecommendedService designPlanRecommendedService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PayAccountService payAccountService;

    @Override
    public void insert(CompanyDesignPlanIncome companyDesignPlanIncome) {
        companyDesignPlanIncomeMapper.insert(companyDesignPlanIncome);
    }

    @Override
    public CompanyDesignPlanIncomeAggregated getCompanyAggregatedByCompanyId(Integer companyId) {
        return companyDesignPlanIncomeMapper.getCompanyAggregatedByCompanyId(companyId);
    }

    @Override
    public void addCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated cdpia) {
        companyDesignPlanIncomeMapper.insertDesignPlanIncomeAggregated(cdpia);
    }

    @Override
    public void updateCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated companyAggregated) {
        companyDesignPlanIncomeMapper.updateCompanyDesignPlanIncomeAggregated(companyAggregated);
    }

    @Override
    public Boolean isExitsBuyDesignPlanCopyRight(Long userId, Integer planId,Integer planType) {
        int i = companyDesignPlanIncomeMapper.countDesignPlanIncomeRecordByUserIdAndPlanId(userId, planId, planType);
        return i > 0?Boolean.TRUE:Boolean.FALSE;
    }

    @Override
    public Map<String, Object> checkReplaceDesignPlanPay(Long userId, Integer recommendedPlanId, String platformCode,Integer planType) {

        DesignPlanBO designPlanBO;

        if (Objects.equals(planType,1)){
            //推荐方案
            DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(recommendedPlanId);
            designPlanBO = this.tranformDesignPlanBO(designPlanRecommended.getId(),designPlanRecommended.getCompanyId(),designPlanRecommended.getPlanPrice(),designPlanRecommended.getChargeType(),designPlanRecommended.getPlanCode(),designPlanRecommended.getUserId());
        }else{
            designPlanBO = designPlanRecommendedService.getFullHouseDesignPlan(recommendedPlanId);
        }

        logger.error("替换渲染检验是否要收费 =>{}"+designPlanBO.toString()+"planType =>{}"+planType);

        //查询用户额
        PayAccount payAccount = payAccountService.getUserPayAccountByUserId(userId,"mobile2b".equals(platformCode) ? "2b" : "2c");
        //设置用户余额
        designPlanBO.setCurrentUserDubi(Objects.isNull(payAccount) ? 0 : payAccount.getBalanceAmount()/10);

        Map<String,Object> map= new HashMap<>();
        map.put("flag",Boolean.TRUE);
        map.put("designPlanRecommended",designPlanBO);
        if (designPlanBO != null && designPlanBO.getChargeType() == 0){
            return map;
        }

        if ("mobile2b".equals(platformCode)){
            //移动B端检验方案是否同一企业
            SysUser sysUser = sysUserService.get(userId.intValue());
            if (sysUser != null){
                if (Objects.equals(designPlanBO.getCompanyId(),sysUser.getCompanyId())){
                    return map;
                }
            }
        }

        int count = companyDesignPlanIncomeMapper.countDesignPlanIncomeRecordByUserIdAndPlanId(userId, recommendedPlanId, planType);
        if (count < 1){
            map.put("flag",Boolean.FALSE);
        }
        return map;
    }

    private DesignPlanBO tranformDesignPlanBO(Integer id, Integer companyId, Double planPrice, Integer chargeType, String planCode, Integer userId) {
          DesignPlanBO bo = new DesignPlanBO();
          bo.setPlanId(id);
          bo.setCompanyId(companyId);
          bo.setPlanPrice(planPrice);
          bo.setChargeType(chargeType);
          bo.setPlanCode(planCode);
          bo.setUserId(userId);
          return bo;
    }

    @Override
    public Integer planSceneIdTransformRecommendedPlanId(Integer designPlanRenderSceneId) {
        return designPlanRecommendedService.getRecommendedPlanId(designPlanRenderSceneId);
    }

    @Override
    public int updateCurrentDubiANDTotalIncomeDubi(Double planPrice, Integer companyId) {
        return companyDesignPlanIncomeMapper.updateCurrentDubiANDTotalIncomeDubi(planPrice,companyId);
    }

    @Override
    public Integer getFullHouseDesignPlanId(String uuid) {
        return companyDesignPlanIncomeMapper.getFullHouseDesignPlanId(uuid);
    }
}
