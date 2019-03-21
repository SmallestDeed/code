package com.sandu.pay2.service.impl;

import com.jpay.ext.kit.PaymentKit;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.sandu.pay.common.exception.BizException;
import com.sandu.pay.common.exception.ExceptionCodes;
import com.sandu.common.pay.IdGenerator;
import com.sandu.common.util.Utils;
import com.sandu.config.ResourceConfig;
import com.sandu.pay.base.model.BaseCompany;
import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.base.service.BaseCompanyService;
import com.sandu.pay.order.dao.PayOrderMapper;
import com.sandu.pay.order.metadata.*;
import com.sandu.pay.order.model.PayAccount;
import com.sandu.pay.order.model.PayModelConfig;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.PayProductConstans;
import com.sandu.pay.order.service.PayAccountService;
import com.sandu.pay.order.service.PayModelGroupRefService;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.pay.wexin.common.RandomStringGenerator;
import com.sandu.pay.wexin.common.WxConfigure;
import com.sandu.pay2.output.MiniProgramOrderVO;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service("miniProgramPayService")
public class MiniProgramPayServiceImpl extends WechatPayServiceImpl {

    private static final ThreadLocal<String> TL = new ThreadLocal<String>();
    private static Logger logger = LogManager.getLogger(MiniProgramPayServiceImpl.class);
    private static final Map<String, WxPayApiConfig> CFG_MAP = new ConcurrentHashMap<String, WxPayApiConfig>();
    @Resource
    private BaseCompanyService baseCompanyService;
    @Resource
    private PayOrderService payOrderService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private PayAccountService payAccountService;
    @Resource
    private PayModelGroupRefService payModelGroupRefService;
    @Autowired
    private PayOrderMapper payOrderMapper;


    private WxPayApiConfig init(Integer userId) {
        SysUser sysUser = sysUserService.get(userId);
        //BaseCompany baseCompany = baseCompanyService.get(sysUser.getMiniProgramCompanyId());
        BaseCompany baseCompany =baseCompanyService.getByIdAndAppId(sysUser.getMiniProgramCompanyId(),sysUser.getAppId());
        String appId = baseCompany.getAppId();
        WxPayApiConfig apiConfig = WxPayApiConfig.New()
                .setAppId(appId)
                .setMchId(baseCompany.getMchId())
                .setOpenId(sysUser.getOpenId())
                .setPaternerKey(baseCompany.getMchKey())
                .setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL)
                .setNonceStr(RandomStringGenerator.getRandomStringByLength(32))
                .setSpbillCreateIp(WxConfigure.getIP())
                .setTradeType(WxPayApi.TradeType.JSAPI);
        CFG_MAP.put(appId, apiConfig);
        TL.set(appId);
        return apiConfig;
    }

    protected Map<String, String> preparedRecharParameters(Integer userId, String totalFee, String orderNo) {
        WxPayApiConfig apiConfig = init(userId);
        Map<String, String> params = apiConfig
                .setBody(PayProductConstans.RECHARGE_PRODUCT_NAME)
                .setOutTradeNo(orderNo)
                .setTotalFee(totalFee)
                .setNotifyUrl(ResourceConfig.WX_PAY_NOTIFY_URL)
                .build();
        return params;
    }


    @Override
    protected Map<String, String> preparedMallOrderParameters(Integer userId, String totalFee, String orderNo) {
        WxPayApiConfig apiConfig = init(userId);
        Map<String, String> params = apiConfig
                .setBody(PayProductConstans.PAY_WXMINI_ORDER_PAY)
                .setOutTradeNo(orderNo)
                .setTotalFee(totalFee)
                .setNotifyUrl(ResourceConfig.WXPAY_MINI_RETURN_URL)
                .build();
        return params;
    }

    @Override
    protected Map<String, String> packagePayParameters(Integer userId, String totalFee, String orderNo) {
        WxPayApiConfig apiConfig = init(userId);
        Map<String, String> params = apiConfig
                .setBody(PayProductConstans.PAY_WXMINI_ORDER_PAY)
                .setOutTradeNo(orderNo)
                .setTotalFee(totalFee)
                .setNotifyUrl(ResourceConfig.WXPAY_MINI_RETURN_URL)
                .build();
        return params;
    }

    protected Object getExecuteResult(String xmlResult) {
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        String return_code = result.get("return_code");
        String return_msg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(return_code)) {
            logger.info(xmlResult);
            throw new BizException(ExceptionCodes.CODE_10010001, "微信小程序下单异常:" + return_msg);
        }
        String result_code = result.get("result_code");
        if (!PaymentKit.codeIsOK(result_code)) {
            logger.info(xmlResult);
            throw new BizException(ExceptionCodes.CODE_10010002, "微信小程序下单结果异常:" + return_msg);
        }
        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
        String prepay_id = result.get("prepay_id");

        //封装调起微信支付的参数https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=5
        Map<String, String> packageParams = new HashMap<String, String>();
        WxPayApiConfig apiConfig = CFG_MAP.get(TL.get());
        packageParams.put("appId", apiConfig.getAppId());
        packageParams.put("timeStamp", System.currentTimeMillis() / 1000 + "");
        packageParams.put("nonceStr", System.currentTimeMillis() + "");
        packageParams.put("package", "prepay_id=" + prepay_id);
        packageParams.put("signType", "MD5");
        String packageSign = PaymentKit.createSign(packageParams, apiConfig.getPaternerKey());
        packageParams.put("paySign", packageSign);

        MiniProgramOrderVO vo = new MiniProgramOrderVO();
        vo.setAppId(packageParams.get("appId"));
        vo.setTimeStamp(packageParams.get("timeStamp"));
        vo.setNonceStr(packageParams.get("nonceStr"));
        vo.setPackageStr(packageParams.get("package"));
        vo.setSignType(packageParams.get("signType"));
        vo.setPaySign(packageSign);
        vo.setPrepayId(prepay_id);
        return vo;
    }


    @Override
    protected PayOrder createPayOrder(Object retVO, Integer platformId, Integer userId, Integer productId
            , String orderNo, Integer totalFee) {
        SysUser sysUser = sysUserService.get(userId.intValue());
        MiniProgramOrderVO obj = (MiniProgramOrderVO) retVO;
        PayOrder payOrder = new PayOrder();
        payOrder.setPlatformId(platformId);
        payOrder.setOpenId(sysUser.getOpenId());
        payOrder.setPayType(PayType.WXPAY);
        payOrder.setUserId(userId);
        payOrder.setProductId(productId);
        payOrder.setPrepayId(obj.getPrepayId());
        payOrder.setPayState(PayState.PAYING);
        payOrder.setOrderDate(new Date());
        payOrder.setOrderNo(orderNo);
        payOrder.setTotalFee(totalFee);
        payOrder.setTradeType(TradeType.MINIPAY);
        payOrder.setUserId(userId);
        payOrder.setOpenId(sysUser.getOpenId());
        payOrder.setGmtCreate(new Date());
        payOrder.setGmtModified(new Date());
        payOrder.setCreator(sysUser.getMobile());
        payOrder.setIsDeleted(0);
        payOrder.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        payOrder.setGmtModified(new Date());
        payOrder.setModifier(sysUser.getMobile());
        return payOrder;
    }

    @Override
    protected void createRechargePayOrder(Object retVO, Integer platformId, Integer userId, Integer productId
            , String orderNo, Integer totalFee) {
        PayOrder payOrder = this.createPayOrder(retVO, platformId, userId, productId, orderNo, totalFee);
        PayAccount payAccount = payAccountService.getPayAccountInfo(userId, platformId);
        if (payAccount == null) {
            payOrder.setCurrentIntegral(0);
            payOrder.setRemark("用户账户不存在");
        } else {
            payOrder.setCurrentIntegral(payAccount.getBalanceAmount().intValue() + totalFee.intValue());
        }
        payOrder.setBizType(BizType.RECHARGE);
        payOrder.setFinanceType(FinanceType.IN);
        payOrder.setProductType(PayProductConstans.PAY_PRODUCT_TYPE);
        payOrder.setProductDesc(PayProductConstans.RECHARGE_PRODUCT_DESC);
        payOrder.setProductName(PayProductConstans.RECHARGE_PRODUCT_NAME);
        payOrderService.add(payOrder);
    }

    @Override
    protected void createMallOrderPayOrder(Object retVO, Integer platformId, Integer userId, Integer productId
            , String orderNo, Integer totalFee) {
        PayOrder payOrder = this.createPayOrder(retVO, platformId, userId, productId, orderNo, totalFee);
        payOrder.setBizType(BizType.BUY);
        payOrder.setFinanceType(FinanceType.IN);
        payOrderService.add(payOrder);
    }

    @Override
    protected void validatePackage(PayModelConfig payModelConfig, int userId) {
        logger.info("检验包年包月套餐begin");
        // 当前如果是包月，那么判断是否有未过期的包年，如果有，那么就不给购买
        List<Map<String, Object>> list
                = payModelGroupRefService.getUserRefInfoList2C(userId,PayModelConstantType.RENDER_TYPE_2C);
        if (payModelConfig.getTimeType() == 0) {
            if (null != list && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).get("timeType").toString().equals("1")) {
                        logger.info("当前用户存在未过期的包年，请在包年过期后购买，用户id：userId:{}" + userId);
                        throw new BizException(ExceptionCodes.CODE_10010009,"当前用户存在未过期的包年，请在包年过期后购买");
                    }
                }
            }
        }
    }

    @Override
    protected void creatPackageOrder(Object retObj, BasePlatform basePlatform, PayModelConfig payModelConfig, Integer userId, String orderNo) {

        //获取用户信息
        SysUser sysUser = sysUserService.get(userId);

        //保存包年包月订单
        PayOrder payOrder = this.saveOrder(retObj,basePlatform,payModelConfig,sysUser,orderNo);
    }

    private PayOrder saveOrder(Object retObj, BasePlatform basePlatform,
                               PayModelConfig payModelConfig, SysUser sysUser,String orderNo) {

        logger.info("购买渲染包年包月订单的金额是：" + payModelConfig.getPackagePrice());
        String productName =  PayProductConstans.RENDER_PAY_PRODUCT_NAME;

        if (payModelConfig.getTimeType() == 0) {
            productName = productName.replace("@replace_str","包月");
        } else if (payModelConfig.getTimeType() == 1) {
            productName = productName.replace("@replace_str","包年");
        }

        MiniProgramOrderVO obj = (MiniProgramOrderVO) retObj;
        int balanceAmount = 0;
        PayAccount payAccount = payAccountService.getPayAccountInfo(sysUser.getId(), basePlatform.getId());

        PayOrder payOrder = getOrder(Double.valueOf(payModelConfig.getPackagePrice()).intValue(),
                PayType.WXPAY,productName, productName,payModelConfig,sysUser,obj,orderNo,basePlatform);

        sysSave(payOrder, sysUser);

        if (null == payAccount) {
            payOrder.setCurrentIntegral(0);
            payOrder.setRemark("用户账户不存在");
        }else{
            balanceAmount = payAccount.getBalanceAmount().intValue();
            payOrder.setCurrentIntegral(balanceAmount); // 当前度币
        }
        int id = this.addPayOrder(payOrder, sysUser);
        if (id == 0) {
            throw new BizException(ExceptionCodes.CODE_10010012,"保存订单失败");
        }
        payOrder.setId(id);
        return payOrder;
    }

    private PayOrder getOrder(int totalFee, String payType, String productName, String productDesc, PayModelConfig payModelConfig, SysUser sysUser, MiniProgramOrderVO obj, String orderNo, BasePlatform basePlatform) {

        PayOrder payOrder = new PayOrder();
        payOrder.setOrderDate(new Date());
        payOrder.setPayState(PayState.NOTPAY);
        payOrder.setTotalFee(totalFee);
        payOrder.setPayType(payType);
        payOrder.setProductDesc(productDesc);
        payOrder.setProductName(productName);
        payOrder.setTradeType(TradeType.MINIPAY);
        payOrder.setProductType(PayProductConstans.RENDER_PRODUCT_TYPE_2C);
        payOrder.setProductId(payModelConfig.getId());
        payOrder.setBizType(BizType.BUY);
        payOrder.setFinanceType(FinanceType.OUT);
        payOrder.setOpenId(sysUser.getOpenId());
        payOrder.setPrepayId(obj.getPrepayId());
        payOrder.setOrderNo(orderNo);
        payOrder.setPlatformId(basePlatform.getId());
        payOrder.setAtt1(String.valueOf(sysUser.getId())); // 业务id（备用字段1）
        payOrder.setAtt2(String.valueOf(payModelConfig.getId())); // 付款方式表id（备用字段2）
        return payOrder;
    }

    /**
     * 组装系统字段
     * @param order
     * @param sysUser
     */
    private void sysSave(PayOrder order, SysUser sysUser){
        if (order != null) {
            if (order.getId() == null) {
                order.setUserId(sysUser.getId());
                order.setGmtCreate(new Date());
                order.setCreator(sysUser.getMobile());
                order.setIsDeleted(0);
                if (order.getSysCode() == null || "".equals(order.getSysCode())) {
                    order.setSysCode(
                            Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }
            order.setGmtModified(new Date());
            order.setModifier(sysUser.getMobile());
        }
    }

    /**
     *　添加订单数据
     * @param payOrder
     * @return
     */

    public Integer addPayOrder(PayOrder payOrder, SysUser sysUser) {
        if( payOrder == null ){
            return 0;
        }
        sysSave(payOrder, sysUser);
        int i = payOrderMapper.insertSelective(payOrder);
        return payOrder.getId();
    }
}
