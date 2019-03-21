package com.sandu.pay2.service;

public interface Pay2Service {

    Object recharge(Integer rechargeItemId, Integer userId, String platformCode);

    /**
     * 小程序商城订单支付
     *
     * @param orderNo      订单号
     * @param userId       用户id
     * @param platformCode 平台编码
     * @return
     */
    Object mallOrderPaying(String orderNo, Integer userId, String platformCode);

    Object packagePay(Integer payModelConfigId, Integer id, String platformCode);
}
