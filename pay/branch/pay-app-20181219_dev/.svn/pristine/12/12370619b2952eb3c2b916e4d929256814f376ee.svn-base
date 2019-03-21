package com.sandu.pay.order.service;

import com.sandu.pay.mgrRecharge.model.MgrRechargeLog;
import com.sandu.user.model.LoginUser;
import com.sandu.user.model.SysUser;

/**
 * Created by Administrator on 2017/9/20.
 */
public interface MgrRechargeLogService {

    /**
     * 添加充值记录
     * @param mgrRechargeLog
     * @return
     */
    int add(MgrRechargeLog mgrRechargeLog);

    /**
     * 更新充值记录
     * @param record
     * @return
     */
    int update(MgrRechargeLog record);

    /**
     * 通过订单NO获取充值信息
     * @param orderNo
     * @return
     */
    MgrRechargeLog getReChargeByOrderNo(String orderNo);

    /**
     * 组装系统字段
     * @param mgrRechargeLog
     * @param loginUser
     */
    void sysSave(MgrRechargeLog mgrRechargeLog, LoginUser loginUser);

    /**
     * 保存充值记录
     * @param mgrRechargeLog
     * @param sysUser
     * @return
     */
    String saveLogAndUpdateUserBalance(MgrRechargeLog mgrRechargeLog, SysUser sysUser);

    /**
     * 保存充值度币记录
     * @param mgrRechargeLog
     * @param sysUser
     * @return
     */
    String saveLogAndUpdateUserIntegral(MgrRechargeLog mgrRechargeLog,SysUser sysUser);
}
