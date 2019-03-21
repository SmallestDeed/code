package com.nork.pay.service;


import com.nork.common.model.LoginUser;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.pay.model.UserDesignPlanPurchaseRecord;
import org.springframework.stereotype.Component;

@Component
public interface UserDesignPlanPurchaseRecordService {
    /**
     * 物理删除
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 选择新增
     */
    int insertSelective(UserDesignPlanPurchaseRecord record);

    /**
     * 主键查询
     */
    UserDesignPlanPurchaseRecord selectByPrimaryKey(Long id);

    /**
     * 选择修改
     */
    int updateByPrimaryKeySelective(UserDesignPlanPurchaseRecord record);

    /**
     * 选择新增
     */
    UserDesignPlanPurchaseRecord insertSelective(String designPlanId,String tradeType, String notifyUrl,String payUrl,String wxpayIp,DesignPlanRecommended designPlanRecommended,String useType,LoginUser loginUser);

    /**
     * 根据交易单号 修改交易状态
     * @param tradeNo       交易单号
     * @param recordStatus  交易状态
     * @return
     */
    int updateObjByTradeNo(String tradeNo,Integer recordStatus);

    /**
     * 校验用户购买方案
     * @author chenqiang
     * @param userId            用户id
     * @param designPlanId      方案id
     * @return
     * @date 2018/9/10 0010 11:55
     */
    int getPlanZFCheck(Integer userId,Integer designPlanId);

    
    /**
     * 根据交易单号获取对象
     * @author chenqiang
     * @param tradeNo 交易单号
     * @return 
     * @date 2018/9/11 0011 16:40
     */
    UserDesignPlanPurchaseRecord getUserDesignPlanPurchaseRecordByNO(String tradeNo);

}