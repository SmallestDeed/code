package com.sandu.pay.order.service;

import com.sandu.pay.order.model.PayAccount;
import com.sandu.pay.order.model.ResultMessage;

/**
 * 支付账户
 *
 * @Author yzw
 * @Date 2018/1/4 20:40
 */
public interface PayAccountService {

    /**
     * 新增
     *
     * @param record
     * @return
     */
    PayAccount add(PayAccount record);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean delete(Integer id);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    PayAccount update(PayAccount record);

    /**
     * 获取
     *
     * @param id
     * @return
     */
    PayAccount get(Integer id);

    /**
     * 获取支付账户信息(暂时区分2b和2c)
     *
     * @param userId     用户id
     * @param platformId 平台id
     * @return
     */
    public PayAccount getPayAccountInfo(Integer userId, Integer platformId);

    /**
     * 下单完成时候更新支付账户信息（不更新订单信息）
     *
     * @param orderNo 订单号
     * @return
     */
    public ResultMessage updateAmountForOrder(String orderNo);

    void givingIntegral(Integer userId);

    int minProgramGivingIntegral(Long userId);

    PayAccount getInfoByUserIdAndPlatformBussinessType(Integer id, String platformBussinessType);
}
