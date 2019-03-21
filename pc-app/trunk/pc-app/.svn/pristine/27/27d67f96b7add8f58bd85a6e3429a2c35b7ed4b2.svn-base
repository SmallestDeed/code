package com.nork.pay.service;

import com.nork.pay.model.PayAccount;
import com.nork.system.model.SysUser;

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
     * @param userId       用户id
     * @param platformCode 平台编码
     * @return
     */
    public PayAccount getPayAccountInfo(Integer userId, String platformCode);

    /**
     * 用户绑定序列号时候赠送积分
     *
     * @param sysUser        用户对象
     * @param platformCode  平台编码
     * @param priceStrategy 赠送的积分
     */
    public void updatePayAccount(SysUser sysUser, String platformCode, Double priceStrategy);

}
