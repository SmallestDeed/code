package com.nork.pay.service.impl;


import com.nork.common.model.LoginUser;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.pay.common.TradeStatusConstant;
import com.nork.pay.dao.UserDesignPlanPurchaseRecordMapper;
import com.nork.pay.model.UserDesignPlanPurchaseRecord;
import com.nork.pay.service.UserDesignPlanPurchaseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service("userDesignPlanPurchaseRecordService")
public class UserDesignPlanPurchaseRecordServiceImpl implements UserDesignPlanPurchaseRecordService {

    @Autowired
    private UserDesignPlanPurchaseRecordMapper userDesignPlanPurchaseRecordMapper;

    /**
     * 物理删除
     */
    public int deleteByPrimaryKey(Long id){
        return userDesignPlanPurchaseRecordMapper.deleteByPrimaryKey(id);
    }

    /**
     * 选择新增
     */
    public int insertSelective(UserDesignPlanPurchaseRecord record){
       userDesignPlanPurchaseRecordMapper.insertSelective(record);
       if(null != record.getId() && record.getId() >0)
           return record.getId().intValue();
       else
           return 0;
    }

    public UserDesignPlanPurchaseRecord insertSelective(String designPlanId, String tradeType, String notifyUrl, String payUrl, String wxpayIp, DesignPlanRecommended designPlanRecommended,String useType, LoginUser loginUser){

        UserDesignPlanPurchaseRecord userDesignPlanPurchaseRecord = new UserDesignPlanPurchaseRecord();
        userDesignPlanPurchaseRecord.setUserId(new Long(loginUser.getId()));
        userDesignPlanPurchaseRecord.setDesignPlanId(Long.parseLong(designPlanId));
        userDesignPlanPurchaseRecord.setPlanName(designPlanRecommended.getPlanName());
        userDesignPlanPurchaseRecord.setTradeNo(UUID.randomUUID().toString().replaceAll("-", ""));
        userDesignPlanPurchaseRecord.setTradeStatus(TradeStatusConstant.PAY_UNPAID);
        userDesignPlanPurchaseRecord.setTradeAmount(new Double(designPlanRecommended.getPlanPrice()));
        userDesignPlanPurchaseRecord.setTradeType(Integer.parseInt(tradeType));
        userDesignPlanPurchaseRecord.setUseType(Integer.parseInt(useType));
        userDesignPlanPurchaseRecord.setTradeTime(new Date());
        userDesignPlanPurchaseRecord.setCreator(loginUser.getLoginName());
        userDesignPlanPurchaseRecord.setGmtCreate(new Date());
        userDesignPlanPurchaseRecord.setModifier(loginUser.getLoginName());
        userDesignPlanPurchaseRecord.setGmtModified(new Date());
        userDesignPlanPurchaseRecord.setIsDeleted(0);

        userDesignPlanPurchaseRecordMapper.insertSelective(userDesignPlanPurchaseRecord);

        if(null != userDesignPlanPurchaseRecord.getId() && userDesignPlanPurchaseRecord.getId() >0) {

            userDesignPlanPurchaseRecord.setNotifyUrl(notifyUrl);
            userDesignPlanPurchaseRecord.setPayUrl(payUrl);
            userDesignPlanPurchaseRecord.setWxPayIp(wxpayIp);
            return userDesignPlanPurchaseRecord;

        }else {

            return null;
        }

    }

    /**
     * 主键查询
     */
    public UserDesignPlanPurchaseRecord selectByPrimaryKey(Long id){
        return userDesignPlanPurchaseRecordMapper.selectByPrimaryKey(id);
    }

    /**
     * 选择修改
     */
    public int updateByPrimaryKeySelective(UserDesignPlanPurchaseRecord record){
        return userDesignPlanPurchaseRecordMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据交易单号 修改交易状态
     * @param tradeNo       交易单号
     * @param recordStatus  交易状态
     * @return
     */
    public int updateObjByTradeNo(String tradeNo,Integer recordStatus){
        return userDesignPlanPurchaseRecordMapper.updateObjByTradeNo(tradeNo,recordStatus);
    }

    /**
     * 校验用户购买方案
     * @author chenqiang
     * @param userId            用户id
     * @param designPlanId      方案id
     * @return
     * @date 2018/9/10 0010 11:55
     */
    public int getPlanZFCheck(Integer userId,Integer designPlanId){
        return userDesignPlanPurchaseRecordMapper.selectPlanZFCheck(userId,designPlanId);
    }

    /**
     * 根据交易单号获取对象
     * @author chenqiang
     * @param tradeNo 交易单号
     * @return
     * @date 2018/9/11 0011 16:40
     */
    public UserDesignPlanPurchaseRecord getUserDesignPlanPurchaseRecordByNO(String tradeNo){
        return userDesignPlanPurchaseRecordMapper.selectUserDesignPlanPurchaseRecordByNO(tradeNo);
    }
}