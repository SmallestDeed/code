package com.sandu.pay.mgrRecharge.service.impl;

import com.sandu.common.util.Utils;
import com.sandu.pay.mgrRecharge.dao.MgrRechargeLogMapper;
import com.sandu.pay.mgrRecharge.model.MgrRechargeLog;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.service.MgrRechargeLogService;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.user.model.LoginUser;
import com.sandu.user.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/20.
 */
@Service("mgrRechargeLogService")
@Transactional
public class MgrRechargeServiceImpl implements MgrRechargeLogService{

    @Autowired
    private MgrRechargeLogMapper mgrRechargeLogMapper;
    @Autowired
    private PayOrderService payOrderService;

    /**
     * 添加充值记录
     * @param mgrRechargeLog
     * @return
     */
    @Override
    public int add(MgrRechargeLog mgrRechargeLog) {
        int i = mgrRechargeLogMapper.insertSelective(mgrRechargeLog);
        if( i > 0 ){
            return mgrRechargeLog.getId();
        }
        return 0;
    }

    /**
     * 更新充值记录
     * @param record
     * @return
     */
    @Override
    public int update(MgrRechargeLog record){
        return mgrRechargeLogMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 通过订单NO获取充值信息
     * @param orderNo
     * @return
     */
    @Override
    public MgrRechargeLog getReChargeByOrderNo(String orderNo){
        return mgrRechargeLogMapper.getReChargeByOrderNo(orderNo);
    }

    /**
     * 保存充值记录
     * @param mgrRechargeLog
     * @param sysUser
     * @return
     */
    @SuppressWarnings("unused")
    @Override
    public String saveLogAndUpdateUserBalance(
            MgrRechargeLog mgrRechargeLog,
            SysUser sysUser) {
        mgrRechargeLog.setRechargeStatus(1);
        mgrRechargeLog.setAdministratorId(null == sysUser ? null : sysUser.getId());
        // 计算/更新用户余额 ->start
        if(sysUser == null){
            return "充值用户不存在,充值失败";
        }
        PayOrder payOrder = payOrderService.getOrderByOrderNo(mgrRechargeLog.getOrderNo());

        Double blance = sysUser.getBalanceAmount() == null ? 0.00 : sysUser.getBalanceAmount();
        Double currentBalance = (blance + payOrder.getAdjustFee()) / 100 + mgrRechargeLog.getRechargeAmount();
        // sys_user表中记录的余额单位是分
        mgrRechargeLog.setCurrentBalance(currentBalance);
        if (mgrRechargeLog.getId() == null) {
            int id = this.add(mgrRechargeLog);
            mgrRechargeLog.setId(id);
        } else {
            int id = this.update(mgrRechargeLog);
        }
        return "success";
    }

    /**
     * 保存充值记录
     * @param mgrRechargeLog
     * @param sysUser
     * @return
     */
    @SuppressWarnings("unused")
    @Override
    public String saveLogAndUpdateUserIntegral(
            MgrRechargeLog mgrRechargeLog,
            SysUser sysUser) {
        mgrRechargeLog.setRechargeStatus(1);
        mgrRechargeLog.setAdministratorId(null == sysUser ? null : sysUser.getId());
        // 计算/更新用户余额 ->start
        if(sysUser == null){
            return "充值用户不存在,充值失败";
        }
        PayOrder payOrder = payOrderService.getOrderByOrderNo(mgrRechargeLog.getOrderNo());

        Double blance = sysUser.getBalanceAmount() == null ? 0 : sysUser.getBalanceAmount();
        Double currentBalance = blance + mgrRechargeLog.getRechargeAmount();
        // sys_user表中记录的余额单位是分
        mgrRechargeLog.setCurrentBalance(currentBalance);
        if (mgrRechargeLog.getId() == null) {
            int id = this.add(mgrRechargeLog);
            mgrRechargeLog.setId(id);
        } else {
            int id = this.update(mgrRechargeLog);
        }
        return "success";
    }

    /**
     * 组装系统字段
     * @param mgrRechargeLog
     * @param loginUser
     */
    @Override
    public void sysSave(MgrRechargeLog mgrRechargeLog, LoginUser loginUser){
        if (mgrRechargeLog != null) {
            if (mgrRechargeLog.getId() == null) {
                mgrRechargeLog.setUserId(loginUser.getId());
                mgrRechargeLog.setGmtCreate(new Date());
                mgrRechargeLog.setCreator(loginUser.getLoginName());
                mgrRechargeLog.setIsDeleted(0);
                if (mgrRechargeLog.getSysCode() == null || "".equals(mgrRechargeLog.getSysCode())) {
                    mgrRechargeLog.setSysCode(
                            Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }
            mgrRechargeLog.setGmtModified(new Date());
            mgrRechargeLog.setModifier(loginUser.getLoginName());
        }
    }
}
