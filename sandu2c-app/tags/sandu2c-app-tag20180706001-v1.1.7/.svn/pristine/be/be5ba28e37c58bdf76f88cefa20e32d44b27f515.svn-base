package com.sandu.user.service.impl;

import com.google.gson.Gson;
import com.sandu.common.constant.VisitorInfoConstant;
import com.sandu.pay.PayAccount;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.SysDictionaryConstant;
import com.sandu.system.service.BasePlatformService;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.user.dao.PayAccountMapper;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.PayAccountService;
import com.sandu.user.service.SysUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 支付账户
 *
 * @Author yzw
 * @Date 2018/1/4 20:40
 */
@Service("payAccountService")
public class PayAccountServiceImpl implements PayAccountService {

    private static Logger logger = LogManager.getLogger(PayAccountServiceImpl.class);
    private final static Gson gson = new Gson();

    @Resource
    private PayAccountMapper payAccountMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Resource
    private BasePlatformService basePlatformService;

    private static final String BRAND2C_PLATFORM_BUSSINESS_TYPE = "2c";
    private static final String MOBILE2B_PLATFORM_BUSSINESS_TYPE = "2b";

    /**
     * 新用户注册的时候赠送积分
     *
     * @param userId     用户id
     * @param platformId 平台id
     * @return
     */
    @Override
    public ResultMessage addGiveIntegral(Integer userId) {
        ResultMessage message = new ResultMessage();
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            logger.info("用户信息为空：sysUser:{}" + sysUser);
            message.setMessage("用户信息为空：sysUser:{}" + sysUser);
            return message;
        }
        List<SysDictionary> balanceIntegrals = sysDictionaryService
                .findAllByType(SysDictionaryConstant.USER_REGISTER_GIFT_INTEGRAL_COUNT);
        if (null == balanceIntegrals || balanceIntegrals.size() == 0) {
            logger.info("数据字典的赠送积分记录为空");
            message.setMessage("数据字典的赠送积分记录为空");
            return message;
        }
        // C端注册插入C端积分记录
        PayAccount brand2cPayAccount = new PayAccount();
        brand2cPayAccount.setGmtCreate(new Date());
        brand2cPayAccount.setCreator(sysUser.getUserName());
        brand2cPayAccount.setModifier(sysUser.getUserName());
        brand2cPayAccount.setGmtModified(new Date());
        brand2cPayAccount.setIsDeleted(0);
        brand2cPayAccount.setUserId(userId);
        brand2cPayAccount.setConsumAmount(0d);
        brand2cPayAccount.setPlatformBussinessType(BRAND2C_PLATFORM_BUSSINESS_TYPE);
        brand2cPayAccount.setBalanceAmount(Double.parseDouble(balanceIntegrals.get(0).getValue().toString()) * 10);
        int i1 = this.payAccountMapper.insertSelective(brand2cPayAccount);
        if (i1 != 1) {
            logger.info("用户id：userId:{}" + userId + "BRAND2C的账户赠送积分失败");
            message.setMessage("用户id：userId:{}" + userId + "BRAND2C的账户赠送积分失败");
            return message;
        }
/*        // C注册插入B端积分记录
        PayAccount mobile2cPayAccount = new PayAccount();
        mobile2cPayAccount.setGmtCreate(new Date());
        mobile2cPayAccount.setCreator(sysUser.getUserName());
        mobile2cPayAccount.setModifier(sysUser.getUserName());
        mobile2cPayAccount.setGmtModified(new Date());
        mobile2cPayAccount.setIsDeleted(0);
        mobile2cPayAccount.setUserId(userId);
        mobile2cPayAccount.setConsumAmount(0d);
        mobile2cPayAccount.setPlatformBussinessType(MOBILE2B_PLATFORM_BUSSINESS_TYPE);
        mobile2cPayAccount.setBalanceAmount(0d);
        int i2 = this.payAccountMapper.insertSelective(mobile2cPayAccount);
        if (i2 != 1) {
            logger.info("用户id：userId:{}" + userId + "MOBILE2B的账户赠送积分失败");
            message.setMessage("用户id：userId:{}" + userId + "MOBILE2B的账户赠送积分失败");
            return message;
        }*/

        logger.info("用户id：userId:{}" + userId + "的账户赠送积分成功");
        message.setMessage("用户id：userId:{}" + userId + "的账户赠送积分成功");
        message.setSuccess(true);
        return message;
    }

    /**
     * 获取支付账户信息(暂时区分2b和2c)
     *
     * @param userId     用户id
     * @param platformId 平台id
     * @return
     */
    @Override
    public PayAccount getPayAccountInfo(Integer userId) {
        PayAccount record = new PayAccount();
        record.setUserId(userId);
        PayAccount payAccount = payAccountMapper.getPayAccountInfo(record);
        return payAccount;
    }

    /**
     * 游客登录的时候赠送积分
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public ResultMessage addGiveIntegralLoginVisitor(Integer userId) {
        ResultMessage message = new ResultMessage();
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            logger.info("用户信息为空：sysUser:{}" + sysUser);
            message.setMessage("用户信息为空：sysUser:{}" + sysUser);
            return message;
        }
        List<SysDictionary> balanceIntegrals = sysDictionaryService
                .findAllByType(SysDictionaryConstant.USER_REGISTER_GIFT_INTEGRAL_COUNT);
        if (null == balanceIntegrals || balanceIntegrals.size() == 0) {
            logger.info("数据字典的赠送积分记录为空");
            message.setMessage("数据字典的赠送积分记录为空");
            return message;
        }
        // C端注册插入C端积分记录
        PayAccount brand2cPayAccount = new PayAccount();
        brand2cPayAccount.setUserId(userId);
        brand2cPayAccount.setPlatformBussinessType(BRAND2C_PLATFORM_BUSSINESS_TYPE);
        brand2cPayAccount.setGmtCreate(new Date());
        brand2cPayAccount.setCreator(sysUser.getUserName());
        brand2cPayAccount.setModifier(sysUser.getUserName());
        brand2cPayAccount.setGmtModified(new Date());
        brand2cPayAccount.setIsDeleted(0);
        brand2cPayAccount.setConsumAmount(0d);
        brand2cPayAccount.setBalanceAmount(VisitorInfoConstant.VISITOR_BALANCE_AMOUNT);
        int i1 = this.payAccountMapper.insertSelective(brand2cPayAccount);
        if (i1 != 1) {
            logger.info("用户id：userId:{}" + userId + "BRAND2C的账户赠送积分失败");
            message.setMessage("用户id：userId:{}" + userId + "BRAND2C的账户赠送积分失败");
            return message;
        }
/*        // C注册插入B端积分记录
        PayAccount mobile2cPayAccount = new PayAccount();
        mobile2cPayAccount.setUserId(userId);
        mobile2cPayAccount.setPlatformBussinessType(MOBILE2B_PLATFORM_BUSSINESS_TYPE);
        mobile2cPayAccount.setGmtCreate(new Date());
        mobile2cPayAccount.setCreator(sysUser.getUserName());
        mobile2cPayAccount.setModifier(sysUser.getUserName());
        mobile2cPayAccount.setGmtModified(new Date());
        mobile2cPayAccount.setIsDeleted(0);
        mobile2cPayAccount.setConsumAmount(0d);
        mobile2cPayAccount.setBalanceAmount(0d);
        int i2 = this.payAccountMapper.insertSelective(mobile2cPayAccount);
        if (i2 != 1) {
            logger.info("用户id：userId:{}" + userId + "MOBILE2B的账户赠送积分失败");
            message.setMessage("用户id：userId:{}" + userId + "MOBILE2B的账户赠送积分失败");
            return message;
        }*/

        logger.info("用户id：userId:{}" + userId + "的账户赠送积分成功");
        message.setMessage("用户id：userId:{}" + userId + "的账户赠送积分成功");
        message.setSuccess(true);
        return message;
    }

    /**
     * 游客注册的时候赠送积分
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public ResultMessage addGiveIntegralVisitor(Integer userId) {
        ResultMessage message = new ResultMessage();
        SysUser sysUser = sysUserService.get(userId);
        if (null == sysUser) {
            logger.info("用户信息为空：sysUser:{}" + sysUser);
            message.setMessage("用户信息为空：sysUser:{}" + sysUser);
            return message;
        }
        List<SysDictionary> balanceIntegrals = sysDictionaryService
                .findAllByType(SysDictionaryConstant.USER_REGISTER_GIFT_INTEGRAL_COUNT);
        if (null == balanceIntegrals || balanceIntegrals.size() == 0) {
            logger.info("数据字典的赠送积分记录为空");
            message.setMessage("数据字典的赠送积分记录为空");
            return message;
        }
        // C端注册插入C端积分记录
        PayAccount brand2cPayAccount = new PayAccount();
        brand2cPayAccount.setUserId(userId);
        brand2cPayAccount.setPlatformBussinessType(BRAND2C_PLATFORM_BUSSINESS_TYPE);
        brand2cPayAccount = payAccountMapper.getPayAccountInfo(brand2cPayAccount);
        brand2cPayAccount.setModifier(sysUser.getUserName());
        brand2cPayAccount.setGmtModified(new Date());
        brand2cPayAccount.setIsDeleted(0);
        brand2cPayAccount.setConsumAmount(0d);
        brand2cPayAccount.setBalanceAmount(Double.parseDouble(balanceIntegrals.get(0).getValue().toString()) * 10);
        int i1 = this.payAccountMapper.updateByPrimaryKeySelective(brand2cPayAccount);
        if (i1 != 1) {
            logger.info("用户id：userId:{}" + userId + "BRAND2C的账户赠送积分失败");
            message.setMessage("用户id：userId:{}" + userId + "BRAND2C的账户赠送积分失败");
            return message;
        }
        // C注册插入B端积分记录
//  /*      PayAccount mobile2cPayAccount = new PayAccount();
//        mobile2cPayAccount.setUserId(userId);
//        mobile2cPayAccount.setPlatformBussinessType(MOBILE2B_PLATFORM_BUSSINESS_TYPE);
//        mobile2cPayAccount = payAccountMapper.getPayAccountInfo(mobile2cPayAccount);
////		if (null == mobile2cPayAccount) {
////			mobile2cPayAccount.setPlatformBussinessType(MOBILE2B_PLATFORM_BUSSINESS_TYPE);
////			mobile2cPayAccount = payAccountMapper.getPayAccountInfo(mobile2cPayAccount);
////		}
////		mobile2cPayAccount.setGmtCreate(new Date());
////		mobile2cPayAccount.setCreator(sysUser.getUserName());
//        mobile2cPayAccount.setModifier(sysUser.getUserName());
//        mobile2cPayAccount.setGmtModified(new Date());
//        mobile2cPayAccount.setIsDeleted(0);
//        mobile2cPayAccount.setConsumAmount(0d);
//        mobile2cPayAccount.setBalanceAmount(0d);
//        int i2 = this.payAccountMapper.updateByPrimaryKeySelective(mobile2cPayAccount);*/
//        if (i2 != 1) {
//            logger.info("用户id：userId:{}" + userId + "MOBILE2B的账户赠送积分失败");
//            message.setMessage("用户id：userId:{}" + userId + "MOBILE2B的账户赠送积分失败");
//            return message;
//        }

        logger.info("用户id：userId:{}" + userId + "的账户赠送积分成功");
        message.setMessage("用户id：userId:{}" + userId + "的账户赠送积分成功");
        message.setSuccess(true);
        return message;
    }

}
