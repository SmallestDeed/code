package com.nork.design.service.impl;


import com.google.gson.Gson;
import com.nork.common.util.Utils;
import com.nork.design.common.HttpResult;
import com.nork.design.common.HttpService;
import com.nork.design.dao.UserDesignPlanPurchaseRecordMapper;
import com.nork.design.model.*;
import com.nork.design.service.CompanyDesignPlanIncomeService;
import com.nork.design.service.DesignPlanRecommendedService;
import com.nork.design.service.UserDesignPlanPurchaseRecordService;
import com.nork.pay.common.IdGenerator;
import com.nork.product.model.BaseCompany;
import com.nork.product.service.BaseCompanyService;
import com.nork.render.model.PayDesignPlanFree;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service("userDesignPlanPurchaseRecordService")
public class UserDesignPlanPurchaseRecordServiceImpl implements UserDesignPlanPurchaseRecordService {

    private Logger log = LoggerFactory.getLogger(UserDesignPlanPurchaseRecordServiceImpl.class);

    @Autowired
    private DesignPlanRecommendedService designPlanRecommendedService;

    @Autowired
    private UserDesignPlanPurchaseRecordMapper userDesignPlanPurchaseRecordMapper;

    @Autowired
    private CompanyDesignPlanIncomeService companyDesignPlanIncomeService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Autowired
    private SysUserService sysUserService;

    private static final String wxPayIp = "114.119.11.234";

    @Override
    public boolean checkUserDesignIsExist(Integer userId, Integer planRecommendedId) {
        DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(planRecommendedId);
        if (designPlanRecommended != null && designPlanRecommended.getChargeType() == 0){
            return Boolean.TRUE;
        }
        int count = userDesignPlanPurchaseRecordMapper.isExistBuyDesignPlanCopyRight(userId, planRecommendedId, 2);
        return count > 0;
    }

    @Override
    public String payDesignPlanCopyRight(PayDesignPlanFree payDesignPlanFree) {
        DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(payDesignPlanFree.getPlanRecommendedId().intValue());
        if (designPlanRecommended != null) {
            //插入用户购买方案记录
            String tradeNo = this.addUserDesignRecord(payDesignPlanFree.getUserId().intValue(),designPlanRecommended.getPlanPrice(), designPlanRecommended.getPlanName(), designPlanRecommended.getId(), payDesignPlanFree.getPayType(),payDesignPlanFree.getUseType());
            log.info("插入用户购买方案记录成功 tradeNo=>{}"+tradeNo);
            //封装请求支付参数
            HttpResult httpResult = this.requestPay(payDesignPlanFree, designPlanRecommended, tradeNo);

            return httpResult.getBody();
        }
        return null;
    }

    private HttpResult requestPay(PayDesignPlanFree payDesignPlanFree, DesignPlanRecommended designPlanRecommended, String tradeNo) {
        log.error("封装支付参数begin");
        try {
            String payUrl = Utils.getPropertyName("app", "pay.gateway.url", "");
            String notifyUrl = Utils.getPropertyName("app", "pay.notifyUrl.url", "");

            Map<String, Object> requestParam = new HashMap<>();
            requestParam.put("intenalTradeNo", tradeNo);
            requestParam.put("tradeDesc", "方案版权购买");
            log.error("支付金额:{}", designPlanRecommended.getPlanPrice());
            Double totalFee = designPlanRecommended.getPlanPrice() * 100;
            requestParam.put("totalFee",totalFee.longValue());
            requestParam.put("payMethod", payDesignPlanFree.getPayMethod());
            requestParam.put("ip", wxPayIp);
            requestParam.put("notifyUrl", notifyUrl);
            requestParam.put("operator", new Long(payDesignPlanFree.getUserId()));
            requestParam.put("source", 2);
            if (payDesignPlanFree.getRedirectUrl() != null){
                requestParam.put("redirectUrl",payDesignPlanFree.getRedirectUrl());
            }
            //requestParam.put("passbackParams",payDesignPlanFree.getUseType()+"");
            log.error("支付参数:{}", requestParam);
            List<BasicHeader> basicHeaders = Arrays.asList(new BasicHeader("Platform-Code", payDesignPlanFree.getPlatformCode()),
                    new BasicHeader("Authorization", payDesignPlanFree.getToken()));
            HttpResult httpResult;
            log.error("start to rest pay parameter =>{}"+requestParam.toString());
            HttpService httpService = new HttpService();
            httpResult = httpService.doPost(payUrl, requestParam, basicHeaders);
            log.error("rest pay result:{}", httpResult);
            return httpResult;
        } catch (Exception e) {
           log.error("系统错误",e);
        }
        return new HttpResult();
    }

    private String addUserDesignRecord(Integer userId, Double planPrice, String planName, Integer designPlanId, Integer payType, Integer useType) {

        UserDesignPlanPurchaseRecord u = new UserDesignPlanPurchaseRecord();
        Date now = new Date();
        u.setUserId(new Long(userId));
        u.setDesignPlanId(new Long(designPlanId));
        u.setPlanName(planName);
        u.setTradeAmount(planPrice);
        u.setTradeStatus(UserDesignPlanPurchaseRecord.TRADE_STATUS_0);
        u.setIsDeleted(0);
        u.setGmtCreate(now);
        u.setGmtModified(now);
        u.setTradeType(payType);
        u.setTradeNo(IdGenerator.generateNo());
        u.setCreator("system");
        u.setUseType(useType);

        userDesignPlanPurchaseRecordMapper.insert(u);

        return u.getTradeNo();
    }

    @Override
    public void updateCallBackStatus(String intenalTradeNo, int tradeStatus) {
        userDesignPlanPurchaseRecordMapper.updateTradeStatusByTradeNo(intenalTradeNo, tradeStatus);
    }

    @Override
    public void updateRecommendedPlanUseCount(Integer recommendedPlanId) {
        designPlanRecommendedService.updateDesignPlanUseCount(recommendedPlanId);
    }

    @Override
    public void addCompanyDesignPlanIncome(UserDesignPlanPurchaseRecord userDesignPlanPurchaseRecord,Integer useType) {
        log.error("支付回调开始插入公司方案统计表 useType=>{}"+useType);
        //获取方案详情
        DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(userDesignPlanPurchaseRecord.getDesignPlanId().intValue());
        Integer companyId = designPlanRecommended.getCompanyId();
        if ("share".equals(designPlanRecommended.getPlanSource())){
            Integer SourceId =  designPlanRecommendedService.getDesignPlanTargetId(designPlanRecommended.getId());
            DesignPlanRecommended targetDesignPlanRecommended = designPlanRecommendedService.get(SourceId);
            companyId = targetDesignPlanRecommended.getCompanyId();
        }
        //获取公司方案提现率
        BaseCompany baseCompany = baseCompanyService.get(companyId);
        //获取用户信息
        SysUser sysUser = sysUserService.get(userDesignPlanPurchaseRecord.getUserId().intValue());
        CompanyDesignPlanIncome c = new CompanyDesignPlanIncome();
        double withdrawAmount = new BigDecimal(designPlanRecommended.getPlanPrice() * baseCompany.getWithdrawRate()).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        Date now = new Date();
       /* c.setPlanCode(designPlanRecommended.getPlanCode());
        c.setPlanPrice(designPlanRecommended.getPlanPrice());
        c.setWithdrawAmount(withdrawAmount);
        c.setWithdrawStatus(0);
        c.setCompanyId(new Long(baseCompany.getId()));
        c.setUseTime(now);
        c.setBuyerId(userDesignPlanPurchaseRecord.getUserId());
        c.setIsDeleted(0);
        c.setCreator(sysUser.getUserName());
        c.setGmtCreate(now);
        c.setGmtModified(now);
        c.setUseType(useType == null ? 0:useType);
        c.setModifier(sysUser.getUserName());*/

        companyDesignPlanIncomeService.insert(c);
        log.error("insert into CompanyDesignPlanIncome parameter =>{}"+new Gson().toJson(c));

        //更新公司统计收益表
        CompanyDesignPlanIncomeAggregated companyAggregated = companyDesignPlanIncomeService.getCompanyAggregatedByCompanyId(baseCompany.getId());
        log.error("update CompanyDesignPlanIncomeAggregated =>{}"+(companyAggregated == null?0:1));
        if (companyAggregated == null) {
            //该公司首次收益
            this.addCompanyDesignPlanIncomeAggregated(c);
        } else {
            //this.updateCompanyDesignPlanIncomeAggregated(companyAggregated,c);
        }

    }

   /* private void updateCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncomeAggregated companyAggregated,CompanyDesignPlanIncome companyDesignPlanIncome) {
        log.error("更新之前信息companyAggregated" + (new Gson().toJson(companyAggregated)));
        //判断企业当天是否有收益
        boolean vaildCurrentDay = this.vaildCurrentDay(companyAggregated.getLastUpdatedDate());
        log.error("判断今天是否收益 =>{}"+vaildCurrentDay);
        if (!vaildCurrentDay) {
             companyAggregated.setTodayIncome(companyDesignPlanIncome.getPlanPrice());
        }else{
             companyAggregated.setTodayIncome(companyAggregated.getTodayIncome()+ companyDesignPlanIncome.getPlanPrice());
        }
        companyAggregated.setWaitWithdrawAmount(companyAggregated.getWaitWithdrawAmount()+companyDesignPlanIncome.getWithdrawAmount());
        companyAggregated.setTotalIncome(companyAggregated.getTotalIncome()+companyDesignPlanIncome.getPlanPrice());
        companyAggregated.setLastUpdatedDate(new Date());
        log.error("更新对象信息companyAggregated" + (new Gson().toJson(companyAggregated)));
        companyDesignPlanIncomeService.updateCompanyDesignPlanIncomeAggregated(companyAggregated);
    }*/

    /**
     * 检验是否是当天时间
     *
     * @param lastUpdatedDate
     * @return
     */
    private boolean vaildCurrentDay(Date lastUpdatedDate) {
        LocalDateTime startTime = LocalDate.now().atTime(0, 0, 0);
        LocalDateTime endTime = LocalDate.now().atTime(23, 59, 59);
        LocalDateTime vaildTime = LocalDateTime.ofInstant(lastUpdatedDate.toInstant(), ZoneId.systemDefault());

        return vaildTime.isAfter(startTime) && vaildTime.isBefore(endTime);
    }

    private void addCompanyDesignPlanIncomeAggregated(CompanyDesignPlanIncome c) {
        /*CompanyDesignPlanIncomeAggregated cdpia = new CompanyDesignPlanIncomeAggregated();
        cdpia.setCompanyId(c.getCompanyId());
        cdpia.setIsDeleted(0);
        cdpia.setTodayIncome(c.getPlanPrice());
        cdpia.setTotalIncome(c.getPlanPrice());
        cdpia.setWaitWithdrawAmount(c.getWithdrawAmount());
        cdpia.setLastUpdatedDate(new Date());
        companyDesignPlanIncomeService.addCompanyDesignPlanIncomeAggregated(cdpia);*/
    }

    @Override
    public UserDesignPlanPurchaseRecord getRecordByUserIdAndDesignPlanId(Integer userId, Integer planRecommendedId) {
        return userDesignPlanPurchaseRecordMapper.getRecordByUserIdAndDesignPlanId(userId,planRecommendedId);
    }

    @Override
    public UserDesignPlanPurchaseRecord getByTradeNo(String tradeNo,Integer tradeStatus) {
        return userDesignPlanPurchaseRecordMapper.getByTradeNo(tradeNo,tradeStatus);
    }

    @Override
    public Map<String, Object> checkReplaceDesignPlanPay(int userId, Integer recommendedPlanId,String platformCode) {
        DesignPlanRecommended designPlanRecommended = designPlanRecommendedService.get(recommendedPlanId);
        Map<String,Object> map= new HashMap<>();
        map.put("flag",Boolean.TRUE);
        map.put("designPlanRecommended",designPlanRecommended);
        if (designPlanRecommended != null && designPlanRecommended.getChargeType() == 0){
            return map;
        }
        
        if ("mobile2b".equals(platformCode)){
            //移动B端检验方案是否同一企业
            SysUser sysUser = sysUserService.get(userId);
            if (sysUser != null){
                if (Objects.equals(designPlanRecommended.getCompanyId(),sysUser.getCompanyId())){
                    return map;
                }
            }
        }
        
        int count = userDesignPlanPurchaseRecordMapper.isExistBuyDesignPlanCopyRight(userId, recommendedPlanId, 2);
        if (count < 1){
            map.put("flag",Boolean.FALSE);
        }
        return map;
    }

    @Override
    public Integer checkReplaceByDesignPlanRenderSceneId(Integer designPlanRenderSceneId) {
        return userDesignPlanPurchaseRecordMapper.getRecommendedPlanId(designPlanRenderSceneId);
    }
}
