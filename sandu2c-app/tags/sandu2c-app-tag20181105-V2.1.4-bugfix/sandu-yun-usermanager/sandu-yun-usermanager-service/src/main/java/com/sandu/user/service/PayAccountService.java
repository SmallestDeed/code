package com.sandu.user.service;

import com.sandu.pay.PayAccount;
import com.sandu.pay.order.model.ResultMessage;

import java.util.Map;

/**
 * 支付账户
 *
 * @Author yzw
 * @Date 2018/1/4 20:40
 */
public interface PayAccountService {

  

    /**
     * 新用户注册的时候赠送积分
     *
     * @param userId     用户id
     * @return
     */
    public ResultMessage addGiveIntegral(Integer userId);

	PayAccount getPayAccountInfo(Integer userId);

    ResultMessage addGiveIntegralLoginVisitor(Integer userId);
    /**
     * 游客登录的时候赠送积分
     *
     * @param userId     用户id
     * @return
     */
    ResultMessage addGiveIntegralVisitor(Integer userId);

}
