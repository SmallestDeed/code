package com.sandu.service.merchantManagePay.impl;


import com.sandu.api.income.model.CompanyDesignPlanIncome;
import com.sandu.api.income.model.CompanyDesignPlanIncomeAggregated;
import com.sandu.api.income.service.CompanyDesignPlanIncomeService;
import com.sandu.api.merchantManagePay.model.*;
import com.sandu.api.merchantManagePay.service.DesinPlanRecommendedService;
import com.sandu.api.merchantManagePay.service.PayOrderService;
import com.sandu.api.platform.model.BasePlatform;
import com.sandu.api.platform.service.BasePlatformService;
import com.sandu.api.servicepurchase.model.IdGenerator;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;


import com.sandu.pay.order.model.PayAccount;
import com.sandu.pay.order.service.PayAccountService;
import com.sandu.service.merchantManagePay.dao.PayOrderDao;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service(value = "payOrderService")
public class PayOrderServiceImpl implements PayOrderService {

    @Autowired
    private PayOrderDao payOrderDao;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PayAccountService payAccountService;

    //支付类型
    private static final String TRADE_TYPE = "ScanCode";

    //支付中
    private static final String PAY_STATE = "PAYING";

    //方案售卖 =>{} 商家后台
    private final static String DESIGN_PLAN_SALE = "DESIGN_PLAN_SALE";

    //方案版权费 =>{} B端C端
    private final static String DESIGN_PLAN_COPYRIGHT = "DESIGN_PLAN_COPYRIGHT";

    private final static String DESIGN_PLAN_FEE = "DESIGN_PLAN_FEE";

    @Autowired
    private BasePlatformService basePlatformService;

    public static final String PRODUCT_NAME = "三度云享家_度币充值";

    @Autowired
    private DesinPlanRecommendedService desinPlanRecommendedService;

    @Autowired
    private CompanyDesignPlanIncomeService companyDesignPlanIncomeService;

    @Override
    public String insertPayOrder(Long userId, Double money, Integer payType, String platformCode) {

        return this.buildPayOrder(userId, money, payType, platformCode);
    }

    private String buildPayOrder(Long userId, Double money, Integer payType, String platformCode) {
        PayOrder payOrder = new PayOrder();

        String orderNo = IdGenerator.generateNo();
        Date now = new Date();
        SysUser sysUser = sysUserService.get(userId.intValue());
        BasePlatform platform = basePlatformService.getByPlatformCode(platformCode);

        payOrder.setProductType(PRODUCT_NAME);
        payOrder.setProductName(PRODUCT_NAME);
        payOrder.setOrderNo(orderNo);
        payOrder.setTotalFee(new BigDecimal(money * 100).intValue()); //充值金额存分
        payOrder.setBizType("Recharge");
        payOrder.setIsDeleted(0);
        payOrder.setPayState(PAY_STATE);
        payOrder.setPayType(payType == 0 ? "weixinpay" : "alipay");
        payOrder.setPlatformId(Optional.ofNullable(platform).isPresent() ? platform.getId() : 0);
        payOrder.setTradeType(TRADE_TYPE);
        payOrder.setUserId(userId.intValue());
        payOrder.setCreator(sysUser.getMobile());
        payOrder.setModifier(sysUser.getMobile());
        payOrder.setGmtCreate(now);
        payOrder.setGmtModified(now);
        payOrder.setOrderDate(now);
        payOrder.setFinanceType("In");

        payOrderDao.insert(payOrder);

        return orderNo;
    }

    @Override
    public int updatePayState(String payState, String orderNo) {
        return payOrderDao.updatePayState(payState, orderNo);
    }

    @Override
    public Map<String, Integer> insertMgrRechargeLog(String orderNo) {
        PayOrder payOrder = payOrderDao.getbyOrderNo(orderNo);
        SysUser sysUser = sysUserService.get(payOrder.getUserId());

        Date now = new Date();
        MgrRechargeLog log = MgrRechargeLog.builder()
                .orderNo(payOrder.getOrderNo())
                .isDeleted(0)
                .creator(sysUser.getMobile())
                .gmtCreate(now)
                .gmtCreate(now)
                .gmtModified(now)
                .rechargeAmount(Double.valueOf(payOrder.getTotalFee() + ""))
                .userId(sysUser.getId().intValue())
                .modifier(sysUser.getMobile())
                .mobile(sysUser.getMobile())
                .rechargeStatus(1)
                .build();

        payOrderDao.inserMgrRechargeLog(log);

        Map<String, Integer> returnMap = new HashMap<>();
        returnMap.put("userId", sysUser.getId().intValue());
        returnMap.put("totalFee", payOrder.getTotalFee());

        return returnMap;
    }

    @Override
    public void updateUserDubi(Map<String, Integer> resultMap) {

        Integer totalFee = resultMap.get("totalFee");
        Integer userId = resultMap.get("userId");

        int row = payOrderDao.updateUserDubi(totalFee, userId);

        if (row < 1) {
            //用户没有开通账号
            SysUser sysUser = sysUserService.get(userId);
            Date now = new Date();
            PayAccount payAccount = new PayAccount();
            payAccount.setBalanceAmount(new Double(totalFee));
            payAccount.setUserId(userId);
            payAccount.setPlatformBussinessType("2b");
            payAccount.setIsDeleted(0);
            payAccount.setConsumAmount(0.0);
            payAccount.setGmtCreate(now);
            payAccount.setGmtModified(now);
            payAccount.setCreator(sysUser.getMobile());
            payAccount.setModifier(sysUser.getMobile());
            payAccountService.add(payAccount);
        }
    }

    @Override
    public void handlerBatchPay(BatchPayPlanFee batchPayPlanFee) {
        Set<Long> designPlanIds = Arrays.asList(batchPayPlanFee.getPlanIds().split(",")).stream().map(id -> Long.parseLong(id)).collect(Collectors.toSet());
        if (Objects.nonNull(designPlanIds) && !designPlanIds.isEmpty()) {
            log.info("批量支付版权费的方案id =>{}" + designPlanIds.toString());
            List<DesignPlanBO> designPlanBOs = new ArrayList<>();
            if (Objects.equals(1, batchPayPlanFee.getPlanType())) {
                //批量获取普通方案详情
                designPlanBOs = desinPlanRecommendedService.getListsByIds(designPlanIds);
            } else {
                //批量获取全屋方案详情
                List<FullHouseDesignBo> fullHouseDesignBos;
                fullHouseDesignBos = desinPlanRecommendedService.getFullDesignPlans(designPlanIds);
                if (fullHouseDesignBos != null && !fullHouseDesignBos.isEmpty()){
                    //转换成DesignPlanBos
                    List<DesignPlanBO> transformBOS = fullHouseDesignBos.stream().map(it -> {
                        return DesignPlanBO.builder()
                                .companyId(new Long(it.getCompanyId()))
                                .planCode(it.getPlanCode())
                                .planId(new Long(it.getPlanId()))
                                .salePrice(it.getSalePrice())
                                .salePriceChargeType(it.getSalePriceChargeType())
                                .userId(it.getUserId())
                                .build();
                    }).collect(toList());
                    designPlanBOs = transformBOS;
                }
            }
            //检查用户度币 && 扣除用户度币
            this.checkAndDeductUserDubi(designPlanBOs,batchPayPlanFee.getUserId().intValue());

            //获取用户信息
            SysUser sysUser = sysUserService.get(batchPayPlanFee.getUserId().intValue());

            //获取平台信息
            BasePlatform basePlatform = basePlatformService.getByPlatformCode(batchPayPlanFee.getPlatformCode());
            //插入公司收益记录
            this.insertCompanyDesignPlanIncome(batchPayPlanFee, designPlanBOs, basePlatform, sysUser);

            //插入用户消费记录
            this.batchInsertPayOrder(sysUser,designPlanBOs, basePlatform.getId(), batchPayPlanFee);

            //插入企业收益数据
            this.handlerCompanyDesignPlanIncomeAggregated(designPlanBOs);
            return;
        }
    }

    private void handlerCompanyDesignPlanIncomeAggregated(List<DesignPlanBO> designPlanBOs) {

        //统计收入度币
        Double totalFee = designPlanBOs.stream().mapToDouble(DesignPlanBO::getSalePrice).sum();

        Integer companyId = designPlanBOs.stream().findFirst().get().getCompanyId().intValue();

        int update = companyDesignPlanIncomeService.updateCurrentDubiANDTotalIncomeDubi(totalFee,companyId);
        if (update < 1){
            CompanyDesignPlanIncomeAggregated companyDesignPlanIncomeAggregated = new CompanyDesignPlanIncomeAggregated();
            companyDesignPlanIncomeAggregated.setCompanyId(new Long(companyId));
            companyDesignPlanIncomeAggregated.setCurrentDubi(totalFee);
            companyDesignPlanIncomeAggregated.setTotalIncomeDubi(totalFee);
            companyDesignPlanIncomeAggregated.setIsDeleted(0);
            companyDesignPlanIncomeAggregated.setFrozenDubi(0.0);
            companyDesignPlanIncomeAggregated.setTransferDubi(0.0);
            companyDesignPlanIncomeAggregated.setWithdrawDubi(0.0);
            companyDesignPlanIncomeService.addCompanyDesignPlanIncomeAggregated(companyDesignPlanIncomeAggregated);
        }

    }


    private void batchInsertPayOrder(SysUser sysUser, List<DesignPlanBO> designPlanBOs, Integer platformId, BatchPayPlanFee batchPayPlanFee){

        List<PayOrder> payOrders = designPlanBOs.stream().map(bo -> {

            PayOrder payOrder = new PayOrder();
            Date now = new Date();
            payOrder.setIsDeleted(0);
            payOrder.setCreator(getBuyerName(sysUser));
            payOrder.setUserId(sysUser.getId().intValue());
            payOrder.setGmtCreate(now);
            payOrder.setModifier(getBuyerName(sysUser));
            payOrder.setGmtModified(now);
            payOrder.setProductType(DESIGN_PLAN_FEE);
            payOrder.setProductName("方案售卖");
            payOrder.setBizType("DESIGN_PLAN_SALE");
            payOrder.setPlanId(bo.getPlanId().intValue());
            payOrder.setPlatformId(platformId);
            payOrder.setOrderNo(IdGenerator.generateNo());
            payOrder.setTotalFee(bo.getSalePrice().intValue()*10);
            payOrder.setPayState("SUCCESS");
            payOrder.setOrderDate(now);
            payOrder.setFinanceType("Out");
            payOrder.setOrderDate(now);

            return payOrder;
        }).collect(toList());

        payOrderDao.batchInsertPayorder(payOrders);
    }


    private void insertCompanyDesignPlanIncome(BatchPayPlanFee batchPayPlanFee, List<DesignPlanBO> designPlanBOs, BasePlatform basePlatform, SysUser sysUser) {

        List<CompanyDesignPlanIncome> incomes = designPlanBOs.stream().map(bo -> {

            SysUser planCreator = sysUserService.get(bo.getUserId());

            CompanyDesignPlanIncome income = new CompanyDesignPlanIncome();
            income.setBuyerId(batchPayPlanFee.getUserId());
            income.setPlanCode(bo.getPlanCode());
            income.setPlanId(bo.getPlanId().intValue());
            income.setBuyerName(getBuyerName(sysUser));
            income.setPayDubi(bo.getSalePrice());
            income.setPayTime(new Date());
            income.setPlanCompanyId(bo.getCompanyId().intValue());
            income.setBuyType(batchPayPlanFee.getBuyType());
            income.setPlatformName(basePlatform.getPlatformName());
            income.setIsDeleted(0);
            income.setPlatformId(basePlatform.getId());
            income.setUseType(5);
            income.setPlanType(batchPayPlanFee.getPlanType());
            //检验该方案是否为本公司的制作方案
            if (Objects.nonNull(planCreator)){
                if (Objects.equals(planCreator.getCompanyId(),bo.getCompanyId())){
                    income.setPlanCreator(planCreator == null ? "---" : StringUtils.isEmpty(planCreator.getUserName()) ? planCreator.getUserName() : planCreator.getMobile());
                }else{
                    income.setPlanCreator("---");
                }
            }
            return income;
        }).collect(toList());

        companyDesignPlanIncomeService.batchInsertIncomes(incomes);
    }

    private String getBuyerName(SysUser sysUser) {

        if (StringUtils.isNotEmpty(sysUser.getUserName())) {
            return sysUser.getUserName();
        }
        if (StringUtils.isNotEmpty(sysUser.getMobile())) {
            return sysUser.getMobile();
        }
        return sysUser.getNickName();
    }

    public void checkAndDeductUserDubi(List<DesignPlanBO> designPlanBOs, int userId) {

        //统计要方案购买总额
        Double totalFee = designPlanBOs.stream().mapToDouble(DesignPlanBO::getSalePrice).sum();

        //获取用户余额
        PayAccount payAccount = payOrderDao.getPayAccountByUserId(userId);

        if (Objects.isNull(payAccount) || payAccount.getBalanceAmount() - totalFee < 0){
            throw  new RuntimeException("您的余额不足,请充值后购买");
        }

        //扣除用户度币
       /* PayAccount update = new PayAccount();
        update.setBalanceAmount(payAccount.getBalanceAmount() - totalFee);
        update.setId(payAccount.getId());
        update.setGmtModified(new Date());*/
        payOrderDao.updatePayAccount(payAccount.getId(),totalFee * 10);

    }

    @Override
    public PayOrder getOrderSuccess(String orderNo) {
        return payOrderDao.getOrderSuccess(orderNo);
    }

}
