package com.sandu.pay2.service.impl;

import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.order.model.PayModelConfig;
import com.sandu.pay.order.model.PayOrder;

import java.util.Map;

public class AlipayPayServiceImpl extends  BasePayServiceImpl{
    @Override
    protected String getPayType() {
        return null;
    }

    @Override
    protected Map<String, String> preparedRecharParameters(Integer userId, String totalFee, String orderNo) {
        return null;
    }


    @Override
    protected String execute(Map<String, String> params) {
        return null;
    }

    @Override
    protected Object getExecuteResult(String xmlResult) {
        return null;
    }

    @Override
    protected PayOrder createPayOrder(Object retVO, Integer platformId, Integer userId, Integer productId, String orderNo, Integer totalFee) {
        return null;
    }

    @Override
    protected void createRechargePayOrder(Object retVO, Integer platformId, Integer userId, Integer productId, String orderNo, Integer totalFee) {

    }

    @Override
    protected void createMallOrderPayOrder(Object retVO, Integer platformId, Integer userId, Integer productId, String orderNo, Integer totalFee) {

    }

    @Override
    protected Map<String, String> preparedMallOrderParameters(Integer userId, String totalFee, String orderNo) {
        return null;
    }

    @Override
    protected void validatePackage(PayModelConfig payModelConfig, int userId) {

    }

    @Override
    protected void creatPackageOrder(Object vo, BasePlatform basePlatform, PayModelConfig payModelConfig, Integer userId, String orderNo) {
    }

    @Override
    protected Map<String, String> packagePayParameters(Integer userId, String s, String orderNo) {
        return null;
    }
}
