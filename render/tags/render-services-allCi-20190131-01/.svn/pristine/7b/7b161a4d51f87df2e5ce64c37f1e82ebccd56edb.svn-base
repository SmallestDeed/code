package com.nork.payAccount.service.impl;

import com.nork.design.model.CompanyDesignPlanIncome;
import com.nork.design.model.CompanyDesignPlanIncomeAggregated;
import com.nork.design.model.DesignPlanBO;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.service.CompanyDesignPlanIncomeService;
import com.nork.design.service.DesignPlanRecommendedService;
import com.nork.pay.common.IdGenerator;
import com.nork.payAccount.dao.PayAccountDao;
import com.nork.payAccount.model.PayAccount;
import com.nork.payAccount.model.PayOrder;
import com.nork.payAccount.service.PayAccountService;
import com.nork.payAccount.service.PayOrderService;
import com.nork.platform.model.BasePlatform;
import com.nork.platform.service.BasePlatformService;
import com.nork.render.model.PayDesignPlanFree;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service(value = "payAccountService")
public class PayAccountServiceImpl implements PayAccountService {

    @Autowired
    private CompanyDesignPlanIncomeService companyDesignPlanIncomeService;

    @Autowired
    private DesignPlanRecommendedService designPlanRecommendedService;

    @Autowired
    private PayAccountDao payAccountDao;

    @Autowired
    private BasePlatformService basePlatformService;

    @Autowired
    private SysUserService sysUserService;

    @Resource(name = "payOrderService2")
    private PayOrderService payOrderService;

    private final static String PAY_FAIL_COPYRIGHT_MESSAFE = "您的度币不足,请充值后购买!";

    //方案售卖 =>{} 商家后台
    private final static String DESIGN_PLAN_SALE = "DESIGN_PLAN_SALE";

    //方案版权费 =>{} B端C端
    private final static String DESIGN_PLAN_COPYRIGHT = "DESIGN_PLAN_COPYRIGHT";

    private final static String DESIGN_PLAN_FEE = "DESIGN_PLAN_FEE";

    @Override
    public Map<String, Object> handlerUserPayDubi(PayDesignPlanFree payDesignPlanFree) {

        BasePlatform platformCode = basePlatformService.getByCode(payDesignPlanFree.getPlatformCode());

        //获取用户账号信息
        PayAccount payAccount = payAccountDao.selectPayAccountByUserId(payDesignPlanFree.getUserId(), platformCode.getPlatformBussinessType());

        if (Objects.isNull(payAccount)) {
            //账号未开通
            return buildResult(Boolean.FALSE, PAY_FAIL_COPYRIGHT_MESSAFE);
        }

        DesignPlanBO designPlanBO;
        if (Objects.equals(1,payDesignPlanFree.getPlanType())){
            //获取方案详情
            DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(payDesignPlanFree.getPlanRecommendedId().intValue());
            designPlanBO = this.transformDesignBO(designPlanRecommended);
        }else{
            designPlanBO = designPlanRecommendedService.getFullHouseDesignPlan(payDesignPlanFree.getPlanRecommendedId().intValue());
        }

        if (payAccount.getBalanceAmount()/10 - designPlanBO.getPlanPrice() < 0) {
            //度币不足
            return buildResult(Boolean.FALSE, PAY_FAIL_COPYRIGHT_MESSAFE);
        }

        SysUser sysUser = sysUserService.get(payDesignPlanFree.getUserId().intValue());
        //插入公司收益记录
        this.insertCompanyDesignPlanIncome(payDesignPlanFree, designPlanBO, platformCode, sysUser,payDesignPlanFree.getPlanType());

        //插入用户消费记录
        this.insertPayOrder(sysUser, designPlanBO.getPlanId(), platformCode.getId(), designPlanBO.getPlanPrice(), payDesignPlanFree.getBuyType());

        //扣除用户度币
        payAccountDao.updateBalanceAmount(designPlanBO.getPlanPrice()*10,sysUser.getId(),platformCode.getPlatformBussinessType());

        //插入企业收益数据
        this.handlerCompanyDesignPlanIncomeAggregated(designPlanBO.getPlanPrice(),designPlanBO.getCompanyId());

        return buildResult(Boolean.TRUE, "支付成功!!");
    }

    private DesignPlanBO transformDesignBO(DesignPlanRecommended designPlanRecommended) {

        DesignPlanBO designPlanBO = new DesignPlanBO();
        designPlanBO.setPlanId(designPlanRecommended.getId());
        designPlanBO.setCompanyId(designPlanRecommended.getCompanyId());
        designPlanBO.setPlanPrice(designPlanRecommended.getPlanPrice());
        designPlanBO.setChargeType(designPlanRecommended.getChargeType());
        designPlanBO.setPlanCode(designPlanRecommended.getPlanCode());
        designPlanBO.setUserId(designPlanRecommended.getUserId());

        return designPlanBO;
    }

    private void handlerCompanyDesignPlanIncomeAggregated(Double planPrice, Integer companyId) {
        int update = companyDesignPlanIncomeService.updateCurrentDubiANDTotalIncomeDubi(planPrice,companyId);
        if (update < 1){
           CompanyDesignPlanIncomeAggregated companyDesignPlanIncomeAggregated = new CompanyDesignPlanIncomeAggregated();
           companyDesignPlanIncomeAggregated.setCompanyId(new Long(companyId));
           companyDesignPlanIncomeAggregated.setCurrentDubi(planPrice);
           companyDesignPlanIncomeAggregated.setTotalIncomeDubi(planPrice);
           companyDesignPlanIncomeAggregated.setIsDeleted(0);
           companyDesignPlanIncomeAggregated.setFrozenDubi(0.0);
           companyDesignPlanIncomeAggregated.setTransferDubi(0.0);
           companyDesignPlanIncomeAggregated.setWithdrawDubi(0.0);
           companyDesignPlanIncomeService.addCompanyDesignPlanIncomeAggregated(companyDesignPlanIncomeAggregated);
        }
    }

    private void insertPayOrder(SysUser sysUser, Integer designPlanId, Integer platformId, Double totalFree, Integer buyType) {

        PayOrder payOrder = new PayOrder();
        Date now = new Date();
        payOrder.setIsDeleted(0);
        payOrder.setCreator(getBuyerName(sysUser));
        payOrder.setUserId(sysUser.getId());
        payOrder.setGmtCreate(now);
        payOrder.setModifier(getBuyerName(sysUser));
        payOrder.setGmtModified(now);
        payOrder.setProductType(DESIGN_PLAN_FEE);
        payOrder.setProductName("方案版权费");
        payOrder.setBizType(DESIGN_PLAN_COPYRIGHT);
        payOrder.setPlanId(designPlanId);
        payOrder.setPlatformId(platformId);
        payOrder.setTotalFee(totalFree.intValue() * 10);
        payOrder.setOrderNo(IdGenerator.generateNo());
        payOrder.setOrderDate(now);
        payOrder.setPayState("SUCCESS");
        payOrder.setFinanceType("Out");

        int row = payOrderService.add(payOrder);
    }

    private void insertCompanyDesignPlanIncome(PayDesignPlanFree payDesignPlanFree, DesignPlanBO designPlanBO, BasePlatform platformCode, SysUser sysUser, Integer planType) {

        CompanyDesignPlanIncome income = new CompanyDesignPlanIncome();

        SysUser planCreator = sysUserService.get(designPlanBO.getUserId());
        income.setBuyerId(payDesignPlanFree.getUserId());
        income.setPlanCode(designPlanBO.getPlanCode());
        income.setPlanId(designPlanBO.getPlanId());
        income.setBuyerName(getBuyerName(sysUser));
        income.setPayDubi(designPlanBO.getPlanPrice());
        income.setPayTime(new Date());
        income.setPlanCompanyId(designPlanBO.getCompanyId());
        income.setBuyType(payDesignPlanFree.getBuyType());
        income.setPlatformName(platformCode.getPlatformName());
        income.setIsDeleted(0);
        income.setPlatformId(platformCode.getId());
        income.setUseType(payDesignPlanFree.getUseType());
        income.setPlanType(payDesignPlanFree.getPlanType());
        if (Objects.nonNull(planCreator)){
            if (Objects.equals(planCreator.getCompanyId(),designPlanBO.getCompanyId())){
                income.setPlanCreator(StringUtils.isEmpty(planCreator.getUserName())? planCreator.getMobile() : planCreator.getUserName());
            }else{
                income.setPlanCreator("---");
            }
        }

        companyDesignPlanIncomeService.insert(income);
    }

    @Override
    public PayAccount getUserPayAccountByUserId(Long userId, String platformBussinessType) {
        return payAccountDao.selectPayAccountByUserId(userId,platformBussinessType);
    }

    private String getBuyerName(SysUser sysUser) {

        if (StringUtils.isNotEmpty(sysUser.getMobile())) {
            return sysUser.getMobile();
        }
        if (StringUtils.isNotEmpty(sysUser.getNickName())) {
            return sysUser.getNickName();
        }
        return "小程序用户";
    }

    public Map<String, Object> buildResult(boolean flag, String message) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("flag", flag);
        resultMap.put("message", message);
        return resultMap;
    }

    @Override
    public void updateBalanceAmount(Double planPrice, Integer userId, String platformBussinessType) {
        payAccountDao.updateBalanceAmount(planPrice,userId,platformBussinessType);
    }
}
