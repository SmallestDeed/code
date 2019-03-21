package com.sandu.api.account.service;

import com.sandu.api.account.model.MgrRechargeLog;
import com.sandu.api.account.model.PayAccount;
import com.sandu.api.account.model.PayOrder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 支付账户服务接口
 * @author WangHaiLin
 * @date 2018/6/12  18:04
 */
@Component
public interface PayAccountService {

    /**
     * 新增支付账户
     * @param payAccount 入参 支付账户实体
     * @return 新增支付账户Id
     */
    Long insertPayAccount(PayAccount payAccount);

    /**
     * 根据用户Id查询账号
     * @param userId
     * @return
     */
    PayAccount getPayAccountByUserId(Long userId);

    int updateUserAmount(PayAccount p);

    PayAccount getInfoByUserIdAndPlatformBussinessType(Integer userId, String s);

    List<MgrRechargeLog> getUserRechargeRecord(Long id, Integer start, Integer limit);

    int countUserRechargeRecord(Long userId);

    void handlerUserDubiInfo(Long userId, Double dubi);

}
