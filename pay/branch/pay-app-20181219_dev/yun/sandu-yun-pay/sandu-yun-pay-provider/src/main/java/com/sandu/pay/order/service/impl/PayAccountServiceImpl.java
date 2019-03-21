package com.sandu.pay.order.service.impl;

import com.google.gson.Gson;
import com.sandu.common.pay.IdGenerator;
import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.base.service.BasePlatformService;
import com.sandu.pay.common.exception.BizException;
import com.sandu.pay.common.exception.ExceptionCodes;
import com.sandu.pay.order.dao.PayAccountMapper;
import com.sandu.pay.order.metadata.*;
import com.sandu.pay.order.model.PayAccount;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.ResultMessage;
import com.sandu.pay.order.service.PayAccountService;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.user.model.CompanyFranchiserGroup;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.CompanyFranchiserGroupService;
import com.sandu.user.service.SysUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private PayOrderService payOrderService;
    @Resource
    private BasePlatformService basePlatformService;
    @Resource
    private CompanyFranchiserGroupService companyFranchiserGroupService;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private PayAccountService payAccountService;

    /**
     * 新增
     *
     * @param record
     * @return
     */
    @Override
    public PayAccount add(PayAccount record) {
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
     * @param userId     用户id
     * @param platformId 平台id
     * @return
     */
    @Override
    public PayAccount getPayAccountInfo(Integer userId, Integer platformId) {
        BasePlatform basePlatform = basePlatformService.get(platformId);
        if (null == basePlatform) {
            return null;
        }
        PayAccount record = new PayAccount();
        record.setUserId(userId);
        record.setPlatformId(platformId);
        record.setPlatformBussinessType(basePlatform.getPlatformBussinessType());
        PayAccount payAccount = this.payAccountMapper.getPayAccountInfo(record);
        return payAccount;
    }

    /**
     * 更新用户的消费金额和剩余金额
     *
     * @param payAccount
     * @param payOrder
     * @param isShare    true表示有度币共享的权限，false表示没有
     * @return
     */
    public PayAccount updateUserAmount(PayAccount payAccount, PayOrder payOrder, boolean isShare) {
        logger.info("++++++++++paystate:" + payOrder.getPayState() + ";biztype:" + payOrder.getBizType() + ";paytype:" + payOrder.getPayType() + ";TotalFee:" + payOrder.getTotalFee());
        if (payOrder.getPayState() != null && payOrder.getBizType() != null) {
            if (payOrder.getFinanceType().equalsIgnoreCase(FinanceType.OUT)) {
                //普通消费度币start
                if (payOrder.getPayState().equalsIgnoreCase(PayState.SUCCESS)
                        && payOrder.getBizType().equalsIgnoreCase(BizType.BUY)
                        && payOrder.getPayType().equalsIgnoreCase(PayType.PREDEPOSIT)) {
                    if (isShare) {
                        companyFranchiserGroupService.updateReduceIntegral(payOrder.getUserId(), payOrder.getTotalFee()); // 减少公共度币，添加消费度币
                    } else {
                        payAccount.setBalanceAmount(payAccount.getBalanceAmount() - payOrder.getTotalFee());
                        payAccount.setConsumAmount(Optional.ofNullable(payAccount.getConsumAmount()).isPresent()?payAccount.getConsumAmount() : 0 + payOrder.getTotalFee());
                    }
                }
                //普通消费度币end
                // ==度币共享消费start
                if (payOrder.getPayState().equalsIgnoreCase(PayState.SUCCESS)
                        && payOrder.getBizType().equalsIgnoreCase(BizType.BUY)
                        && payOrder.getPayType().equalsIgnoreCase(PayType.INTEGRAL_SHARE_PAY)) {
                    companyFranchiserGroupService.updateReduceIntegral(payOrder.getUserId(), payOrder.getTotalFee()); // 减少公共度币，添加消费度币
                }
                // ==度币共享消费end
                if (payOrder.getPayState().equalsIgnoreCase(PayState.SUCCESS)
                        && (payOrder.getPayType().equalsIgnoreCase(PayType.ALIPAY)
                        || payOrder.getPayType().equalsIgnoreCase(PayType.WXPAY))) {
                    if (isShare) {
                        companyFranchiserGroupService.updateConsumeIntegral(payOrder.getUserId(), payOrder.getTotalFee()); //添加消费度币，公共度币不作处理
                    } else {
                        payAccount.setConsumAmount(payAccount.getConsumAmount() + payOrder.getTotalFee());
                    }
                }
            }
                //充值start
                if (payOrder.getFinanceType().equalsIgnoreCase(FinanceType.IN)) {
                    if (payOrder.getPayState().equalsIgnoreCase(PayState.SUCCESS)
                            && payOrder.getBizType().equalsIgnoreCase(BizType.RECHARGE)) {
                        logger.info("用户度币充值：订单号：orderNo:{}" + payOrder.getOrderNo() + "订单金额：totalFee:{}" + payOrder.getTotalFee() + "赠送金额：adjustFee:{}" + payOrder.getAdjustFee() + "是否具备经销商权限？：" + isShare);
                        if (isShare) {
                            companyFranchiserGroupService.updateRechargeIntegral(payOrder.getUserId(), payOrder.getTotalFee()
                                    + (null == payOrder.getAdjustFee() ? 0 : payOrder.getAdjustFee().intValue())); // 添加公共度币，消费度币不作处理
                        } else {
                            payAccount.setBalanceAmount(payAccount.getBalanceAmount() + Double.parseDouble(payOrder.getTotalFee().toString()) + payOrder.getAdjustFee().intValue());
                        }
                    }

                //充值end
                //退款更新start
                if (payOrder.getPayState().equalsIgnoreCase(PayState.SUCCESS)
                        && payOrder.getBizType().equalsIgnoreCase(BizType.REFUND)) {
                    if (isShare) {
                        companyFranchiserGroupService.updateAddIntegral(payOrder.getUserId(), payOrder.getTotalFee()); // 添加公共度币，减少消费度币
                    } else {
                        payAccount.setBalanceAmount(payAccount.getBalanceAmount() + payOrder.getTotalFee());
                        payAccount.setConsumAmount(payAccount.getConsumAmount() - payOrder.getTotalFee());
                    }
                }
                //退款更新start
            }
        }
        return payAccount;
    }

    /**
     * 下单完成时候更新支付账户信息（不更新订单信息）
     *
     * @param orderNo 订单号
     * @return
     */
    @Override
    public ResultMessage updateAmountForOrder(String orderNo) {
        ResultMessage message = new ResultMessage();
        if (null == orderNo) {
            logger.info("订单号为空:orderNo:{}" + orderNo);
            message.setMessage("订单号为空:orderNo:{}" + orderNo);
            return message;
        }
        PayOrder payOrder = payOrderService.getOrderByOrderNo(orderNo);
        if (null == payOrder) {
            logger.info("订单信息为空：payOrder:{}" + payOrder);
            message.setMessage("订单信息为空：payOrder:{}" + payOrder);
            return message;
        }
        SysUser sysUser = sysUserService.get(payOrder.getUserId());
        if (null == sysUser) {
            logger.info("用户信息为空：sysUser:{}" + sysUser);
            message.setMessage("用户信息为空：sysUser:{}" + sysUser);
            return message;
        }
        BasePlatform basePlatform = basePlatformService.get(payOrder.getPlatformId());
        if (null == basePlatform) {
            logger.info("平台信息为空：basePlatform:{}" + basePlatform);
            message.setMessage("平台信息为空：basePlatform:{}" + basePlatform);
            return message;
        }
        // 这里直接判断用户是否具备度币共享权限
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(payOrder.getUserId());
        boolean isShare = false; // 不具备度币共享
        if (null != companyFranchiserGroup) {
            isShare = true; // 具备度币共享权限
        }
        PayAccount payAccount = getPayAccountInfo(payOrder.getUserId(), payOrder.getPlatformId());
        if (null == payAccount) {
            payAccount = new PayAccount();
            payAccount.setGmtCreate(new Date());
            payAccount.setCreator(sysUser.getMobile());
            payAccount.setModifier(sysUser.getMobile());
            payAccount.setGmtModified(new Date());
            payAccount.setIsDeleted(0);
            payAccount.setUserId(payOrder.getUserId());
//            payAccount.setPlatformId(payOrder.getPlatformId());
            payAccount.setConsumAmount(0d);
            payAccount.setBalanceAmount(Double.valueOf(payOrder.getTotalFee()));
            payAccount.setPlatformBussinessType(basePlatform.getPlatformBussinessType());
            payAccount = updateUserAmount(payAccount, payOrder, isShare);
            if (!isShare) {
                int i = this.payAccountMapper.insertSelective(payAccount);
                if (i != 1) {
                    logger.info("用户id：userId:{}" + payOrder.getUserId() + ",平台id：platformId：{}" + payOrder.getPlatformId() + "的账户余额信息更新失败");
                    message.setMessage("用户id：userId:{}" + payOrder.getUserId() + ",平台id：platformId：{}" + payOrder.getPlatformId() + "的账户余额信息更新失败");
                    return message;
                }
                logger.info("用户id:userId:{}" + payOrder.getUserId() + "的剩余金额为：balanceAmount：{}" + Double.valueOf(payOrder.getTotalFee())
                        + ",消费金额为：consumAmount：{}" + 0);
            }

        } else {
            payAccount = updateUserAmount(payAccount, payOrder, isShare);
            payAccount.setModifier(sysUser.getMobile());
            payAccount.setGmtModified(new Date());
            if (!isShare) {
                logger.info("payAccount:{}" + gson.toJson(payAccount));
                int i = this.payAccountMapper.updateByPrimaryKeySelective(payAccount);
                if (i != 1) {
                    logger.info("用户id：userId:{}" + payOrder.getUserId() + ",平台id：platformId：{}" + payOrder.getPlatformId() + "的账户余额信息更新失败");
                    message.setMessage("用户id：userId:{}" + payOrder.getUserId() + ",平台id：platformId：{}" + payOrder.getPlatformId() + "的账户余额信息更新失败");
                    return message;
                }
                logger.info("用户id:userId:{}" + payOrder.getUserId() + "的剩余金额为：balanceAmount：{}" + payAccount.getBalanceAmount()
                        + ",消费金额为：consumAmount：{}" + payAccount.getConsumAmount());
            }

        }
        logger.info("用户id：userId:{}" + payOrder.getUserId() + ",平台id：platformId：{}" + payOrder.getPlatformId() + "的账户余额信息更新成功");
        message.setMessage("用户id：userId:{}" + payOrder.getUserId() + ",平台id：platformId：{}" + payOrder.getPlatformId() + "的账户余额信息更新成功");
        message.setSuccess(true);
        return message;
    }

    @Override
    public void givingIntegral(Integer userId) {
        SysUser sysUser = sysUserService.get(userId);
        logger.info("获取用户信息,userId =>{}"+(sysUser == null?-1:sysUser.getId()));
        //组装payAccount对象
        PayAccount payAccount = this.creatPayAccount(sysUser);

        int result = payAccountMapper.updateByUserId(payAccount);

        if(result < 1 ){
            logger.info("用户度币记录不存在,userId =>{}"+sysUser.getId());
            //用户度币不存在,插入一条数据
            payAccount.setGmtCreate(new Date());
            payAccount.setCreator("miniProgram");
            payAccount.setPlatformBussinessType("2c");
            payAccount.setIsDeleted(0);
            payAccountMapper.insertSelective(payAccount);
        }
        //保存赠送度币的消费记录
        this.creatPayOrder(sysUser,payAccount.getBalanceAmount());
    }

    @Override
    public int minProgramGivingIntegral(Long userId) {
        logger.info("小程序首次登录绑定送积分begin");
        //查询数据字典赠送积分记录
        SysDictionary sysDictionary = selectIntegral("minProgramGivingIntegral");
        //组装payAccount对象
        PayAccount payAccount = buildPayAccount(userId, sysDictionary);

        int  result = payAccountMapper.insertSelective(payAccount);

        return result;
    }


    private SysDictionary selectIntegral(String type){
        logger.info("查询数据字段小程序登录积分赠送记录begin");
        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setType(type);
        List<SysDictionary> list = sysDictionaryService.getList(sysDictionary);
        if(list == null || list.size()<=0){
            throw  new BizException(ExceptionCodes.CODE_10010019,"数据字典记录为空");
        }
        return list.get(0);
    }

    private PayAccount buildPayAccount(Long userId,SysDictionary sysDictionary){

        logger.info("组装payAccount对象");
        PayAccount payAccount = new PayAccount();
        payAccount.setGmtCreate(new Date());
        payAccount.setCreator("miniProgram");
        payAccount.setModifier("miniProgram");
        payAccount.setGmtModified(new Date());
        payAccount.setIsDeleted(0);
        payAccount.setUserId(userId.intValue());
        payAccount.setConsumAmount(0d);
        payAccount.setPlatformBussinessType("2c");
        payAccount.setBalanceAmount(Double.parseDouble(sysDictionary.getValue().toString()) * 10);
        return payAccount;
    }

    private void creatPayOrder(SysUser sysUser, Double balanceAmount) {

        PayOrder payOrder = new PayOrder();
        Double totalFee = 0d;
        Date date = new Date();
        int money = Objects.isNull(balanceAmount)? 0 : balanceAmount.intValue()/10;
        payOrder.setTotalFee(totalFee.intValue());
        payOrder.setPayType(PayType.PAY_MINPRO_GIVE_BING_MOBILE);
        payOrder.setProductName("绑定手机号赠送"+money+"度币");
        payOrder.setProductDesc("绑定手机号赠送"+money+"度币");
        payOrder.setTradeType(TradeType.PAY_MINPRO_GIVE_ACCOUNT);
        payOrder.setUserId(sysUser.getId());

        PayAccount queryAccount = new PayAccount();
        queryAccount.setUserId(sysUser.getId());
        PayAccount payAccount =payAccountMapper.getPayAccountInfo(queryAccount);
        if(null == payAccount){
            payOrder.setCurrentIntegral(0);
            payOrder.setRemark("用户账户不存在");
        }else{
            payOrder.setCurrentIntegral(payAccount.getBalanceAmount().intValue()+(balanceAmount.intValue())); // 当前度币
        }
        payOrder.setProductId(0);
        payOrder.setProductType("小程序绑定手机号赠送"+money+"度币");
        payOrder.setBizType(BizType.GIVE);
        payOrder.setFinanceType(FinanceType.GIVE);
        payOrder.setPayState(PayState.GIVE_SUCCESS);
        payOrder.setPlatformId(3);// =>直接写死brand2C,后加的逻辑,没有把平台id传过来
        payOrder.setGmtCreate(date);
        payOrder.setGmtModified(date);
        payOrder.setCreator(sysUser.getMobile());
        payOrder.setModifier(sysUser.getMobile());
        payOrder.setOrderNo(IdGenerator.generateNo());
        payOrder.setOrderDate(new Date());
        payOrder.setIsDeleted(0);
        payOrder.setTaskId(-1);

        int i = payOrderService.add(payOrder);

        if(i>0){
            logger.info("赠送度币=>插入订单信息成功");
        }
    }

    private PayAccount creatPayAccount(SysUser sysUser) {

        logger.info("组装payAccount对象");
        //字典表中获取绑定小程序赠送度币
        SysDictionary sysDictionary = selectIntegral("minProBindMobileGive");
        PayAccount payAccount = new PayAccount();
        payAccount.setModifier(sysUser.getMobile());
        payAccount.setGmtModified(new Date());
        payAccount.setUserId(sysUser.getId());
        payAccount.setBalanceAmount(Double.parseDouble(sysDictionary.getValue().toString()) * 10);
        return payAccount;
    }

    @Override
    public PayAccount getInfoByUserIdAndPlatformBussinessType(Integer userId, String platformBussinessType) {
        return payAccountMapper.selectByUserIdAndPlatformBussinessType(userId,platformBussinessType);
    }
}
