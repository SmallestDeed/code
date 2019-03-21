package com.nork.pay.service.impl;

import com.google.gson.Gson;
import com.nork.base.model.BasePlatform;
import com.nork.base.service.BasePlatformService;
import com.nork.pay.dao.PayAccountMapper;
import com.nork.pay.model.PayAccount;
import com.nork.pay.service.PayAccountService;
import com.nork.system.model.SysUser;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 支付账户
 *
 * @Author yzw
 * @Date 2018/1/4 20:40
 */
@Service("payAccountService")
public class PayAccountServiceImpl implements PayAccountService {

    private static final Logger logger = LoggerFactory.getLogger(PayAccountServiceImpl.class);
    private final static Gson gson = new Gson();

    @Resource
    private PayAccountMapper payAccountMapper;
    @Resource
    private BasePlatformService basePlatformService;

    /**
     * 新增
     *
     * @param record
     * @return
     */
    @Override
    public PayAccount add(PayAccount record) {
        if (record == null || record.getUserId() == null || record.getPlatformBussinessType() == null) {
            return null;
        }
        PayAccount payAccountInfo = payAccountMapper.getPayAccountInfo(record);
        if (payAccountInfo != null) {
            return null;
        }
        if (this.payAccountMapper.insertSelective(record) == 1)
            return record;
        return null;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(Integer id) {
        return this.payAccountMapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 更新
     *
     * @param record
     * @return
     */
    @Override
    public PayAccount update(PayAccount record) {
        if (this.payAccountMapper.updateByPrimaryKeySelective(record) == 1)
            return record;
        return null;
    }

    /**
     * 获取
     *
     * @param id
     * @return
     */
    @Override
    public PayAccount get(Integer id) {
        return this.payAccountMapper.selectByPrimaryKey(id);
    }


    /**
     * 获取支付账户信息(暂时区分2b和2c)
     *
     * @param userId       用户id
     * @param platformCode 平台编码
     * @return
     */
    @Override
    public PayAccount getPayAccountInfo(Integer userId, String platformCode) {
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform) {
            return null;
        }
        PayAccount record = new PayAccount();
        record.setUserId(userId);
        record.setPlatformBussinessType(basePlatform.getPlatformBussinessType());
        PayAccount payAccount = this.payAccountMapper.getPayAccountInfo(record);
        return payAccount;
    }

    /**
     * 用户绑定序列号时候赠送积分
     *
     * @param sysUser       用户对象
     * @param platformCode  平台编码
     * @param priceStrategy 赠送的积分
     */
    @Override
    public void updatePayAccount(SysUser sysUser, String platformCode, Double priceStrategy) {
        if (null == sysUser) {
            logger.info("用户绑定序列号时候赠送积分的用户信息为空：sysUser:{}" + sysUser);
            return;
        }
        if (null == priceStrategy || priceStrategy <= 0) {
            logger.info("用户绑定序列号时候赠送积分的赠送积分信息有误：priceStrategy:{}" + priceStrategy);
            return;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform) {
            logger.info("用户绑定序列号时候赠送积分的平台信息为空：basePlatform:{}" + basePlatform);
            return;
        }
        PayAccount record = new PayAccount();
        record.setUserId(sysUser.getId());
        record.setPlatformBussinessType(basePlatform.getPlatformBussinessType());
        PayAccount payAccount = this.payAccountMapper.getPayAccountInfo(record);
        if (null == payAccount) {
            payAccount = new PayAccount();
            payAccount.setGmtCreate(new Date());
            payAccount.setCreator(sysUser.getMobile());
            payAccount.setModifier(sysUser.getMobile());
            payAccount.setGmtModified(new Date());
            payAccount.setIsDeleted(0);
            payAccount.setUserId(sysUser.getId());
            payAccount.setConsumAmount(0d);
            payAccount.setPlatformBussinessType(basePlatform.getPlatformBussinessType());
            payAccount.setBalanceAmount(priceStrategy);
            int i = this.payAccountMapper.insertSelective(payAccount);
            if (i != 1) {
                logger.info("用户id：userId:{}" + sysUser.getId() + ",平台id：platformId：{}" + basePlatform.getId() + "的账户赠送积分失败");
            }
        } else {
            payAccount.setBalanceAmount(null == payAccount.getBalanceAmount() ? priceStrategy : (payAccount.getBalanceAmount() + priceStrategy));
            payAccount.setGmtModified(new Date());
            int i = this.payAccountMapper.updateByPrimaryKeySelective(payAccount);
            if (i != 1) {
                logger.info("用户id：userId:{}" + sysUser.getId() + ",平台id：platformId：{}" + basePlatform.getId() + "的账户赠送积分失败");
            }
        }
    }


}
