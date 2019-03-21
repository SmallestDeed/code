package com.nork.payAccount.service;

import com.nork.payAccount.model.PayAccount;
import com.nork.render.model.PayDesignPlanFree;

import java.util.Map;

public interface PayAccountService {
    Map<String,Object> handlerUserPayDubi(PayDesignPlanFree payDesignPlanFree);

    PayAccount getUserPayAccountByUserId(Long userId, String platformBussinessType);

    /**
     * 扣费
     * @author: chenm
     * @date: 2019/1/17 16:17
     * @param planPrice 需支付费用
     * @param userId 用户id
     * @param platformBussinessType 平台类型(2b/2c)
     * @return: void
     */
    void updateBalanceAmount(Double planPrice, Integer userId, String platformBussinessType);
}
