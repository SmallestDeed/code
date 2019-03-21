package com.sandu.pay2.service.impl;

import com.sandu.pay.common.exception.BizException;
import com.sandu.pay.common.exception.ExceptionCodes;
import com.sandu.common.pay.IdGenerator;
import com.sandu.common.util.Utils;
import com.sandu.order.MallBaseOrder;
import com.sandu.order.service.MallBaseOrderService;
import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.base.service.BasePlatformService;
import com.sandu.pay.mgrRecharge.model.MgrRechargeLog;
import com.sandu.pay.order.metadata.PayType;
import com.sandu.pay.order.model.PayModelConfig;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.service.MgrRechargeLogService;
import com.sandu.pay.order.service.PayModelConfigService;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.pay2.service.Pay2Service;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

public abstract class BasePayServiceImpl implements Pay2Service {
    private static Logger logger = LogManager.getLogger(BasePayServiceImpl.class);

    @Resource
    private SysDictionaryService sysDictionaryService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private MgrRechargeLogService mgrRechargeLogService;
    @Resource
    private BasePlatformService basePlatformService;
    @Autowired
    private MallBaseOrderService mallBaseOrderService;
    @Resource
    private PayOrderService payOrderService;
    @Autowired
    private PayModelConfigService payModelConfigService;

    @Override
    public Object packagePay(Integer payModelConfigId, Integer userId, String platformCode){

        PayModelConfig payModelConfig =payModelConfigService.get(payModelConfigId);

        if(payModelConfig == null || !"renderType2C".equals(payModelConfig.getBizType())){
            throw  new BizException(ExceptionCodes.CODE_10010007,"包年包月套餐不存在");
        }

        //下单金额
        String orderNo = IdGenerator.generateNo();
        int total_fee = payModelConfig.getPackagePrice().intValue();

        //准备接口参数
        Map<String, String> params = this.packagePayParameters(userId, String.valueOf(total_fee), orderNo);

        //调用第三方支付接口
        String xmlResult = this.execute(params);

        //获取返回结果
        Object vo = this.getExecuteResult(xmlResult);

        //生成订单
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        this.creatPackageOrder(vo,basePlatform,payModelConfig,userId,orderNo);

        return vo;
    }

    protected abstract Map<String,String> packagePayParameters(Integer userId, String s, String orderNo);

    protected abstract void creatPackageOrder(Object vo, BasePlatform basePlatform, PayModelConfig payModelConfig, Integer userId, String orderNo);

    protected abstract void validatePackage(PayModelConfig payModelConfig, int userId);


    @Override
    public Object recharge(Integer rechargeItemId, Integer userId, String platformCode) {
        SysDictionary sysDictionary = sysDictionaryService.get(rechargeItemId);
        if (sysDictionary == null ) {
            throw new BizException(ExceptionCodes.CODE_10010004, "充值信息为空.");
        }
        String orderNo = IdGenerator.generateNo();
        String discountPrice = sysDictionary.getAtt1();// 下单的金额
        //准备接口参数
        Map<String, String> params = this.preparedRecharParameters(userId, discountPrice, orderNo);

        //调用第三方支付接口
        String xmlResult = this.execute(params);

        //获取返回结果
        Object vo = this.getExecuteResult(xmlResult);

        //生成订单
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        Double totalFee = Double.valueOf(Utils.getIntValue(sysDictionary.getAtt1())); // 订单的总价
        this.createRechargePayOrder(vo, basePlatform.getId(), userId, rechargeItemId, orderNo, Double.valueOf(totalFee).intValue());

        //保存充值日志
        SysUser sysUser = sysUserService.get(userId);
        this.saveRechargeLog(sysUser.getId(), sysUser.getMobile(), totalFee, orderNo, basePlatform.getPlatformBussinessType());

        return vo;
    }

    private void saveRechargeLog(Integer userId, String mobile, double totalFee, String orderNo, String platformBussinessType) {
        MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
        mgrRechargeLog.setUserId(userId);
        if (PayType.ALIPAY.equals(this.getPayType())) {
            mgrRechargeLog.setRechargeType(3); // 充值类型
        } else {
            mgrRechargeLog.setRechargeType(2); // 充值类型
        }
        mgrRechargeLog.setRechargeStatus(2); // 充值状态
        mgrRechargeLog.setRechargeAmount(totalFee);
        mgrRechargeLog.setAdministratorId(userId);
        mgrRechargeLog.setOrderNo(orderNo);
        mgrRechargeLog.setPlatformBussinessType(platformBussinessType); // 平台类型
        mgrRechargeLog.setUserId(userId);
        mgrRechargeLog.setGmtCreate(new Date());
        mgrRechargeLog.setCreator(mobile);
        mgrRechargeLog.setIsDeleted(0);
        mgrRechargeLog.setSysCode(
                Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        mgrRechargeLog.setGmtModified(new Date());
        mgrRechargeLog.setModifier(mobile);
        mgrRechargeLogService.add(mgrRechargeLog);

    }

    protected abstract String getPayType();

    protected abstract Map<String, String> preparedRecharParameters(Integer userId, String totalFee, String orderNo);

    protected abstract String execute(Map<String, String> params);

    protected abstract Object getExecuteResult(String xmlResult);

    protected abstract PayOrder createPayOrder(Object retVO, Integer platformId, Integer userId, Integer productId
            , String orderNo, Integer totalFee);

    protected abstract void createRechargePayOrder(Object retVO, Integer platformId, Integer userId, Integer productId
            , String orderNo, Integer totalFee);

    protected abstract void createMallOrderPayOrder(Object retVO, Integer platformId, Integer userId, Integer productId
            , String orderNo, Integer totalFee);


    @Override
    public Object mallOrderPaying(String orderNo, Integer userId, String platformCode) {
        MallBaseOrder baseOrder = mallBaseOrderService.getOrderByOrderNo(orderNo);
        if (baseOrder == null) {
            throw new BizException(ExceptionCodes.CODE_10010004, "订单信息为空.");
        }
        if (baseOrder.getOrderAmount() == null || baseOrder.getOrderAmount().doubleValue() <= 0) {
            throw new BizException(ExceptionCodes.CODE_10010005, "订单金额非法.");
        }

        orderNo = orderNo + Utils.generateRandomDigitString(4);

        //准备接口参数
        Integer totalFee = Double.valueOf(baseOrder.getOrderAmount().doubleValue() * 100).intValue();
        logger.info("小程序订单总价是{}" + baseOrder.getOrderAmount().doubleValue() + ",totalFee{}"+totalFee);
        Map<String, String> params = this.preparedMallOrderParameters(userId, String.valueOf(totalFee), orderNo);

        //调用第三方支付接口
        String xmlResult = this.execute(params);

        //获取返回结果
        Object vo = this.getExecuteResult(xmlResult);

        //生成订单
        PayOrder payOrder = payOrderService.getOrderByOrderNo(orderNo);
        if (payOrder != null) {
          payOrderService.deleteByOrderNo(orderNo);
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        this.createMallOrderPayOrder(vo, basePlatform.getId(), userId, null, orderNo,totalFee);
        return vo;
    }


    protected abstract Map<String, String> preparedMallOrderParameters(Integer userId, String totalFee, String orderNo);
}
