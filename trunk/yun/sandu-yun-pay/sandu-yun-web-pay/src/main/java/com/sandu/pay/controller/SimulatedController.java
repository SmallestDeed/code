package com.sandu.pay.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.google.gson.Gson;
import com.sandu.common.constant.PayConstant;
import com.sandu.common.constant.PlatformConstants;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.pay.IdGenerator;
import com.sandu.common.util.StringUtils;
import com.sandu.common.util.Utils;
import com.sandu.pay.alipay.common.AlipayConfig;
import com.sandu.pay.base.model.BasePlatform;
import com.sandu.pay.base.service.BasePlatformService;
import com.sandu.pay.mgrRecharge.model.MgrRechargeLog;
import com.sandu.pay.order.metadata.*;
import com.sandu.pay.order.model.PayAccount;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.PayOrderModel;
import com.sandu.pay.order.model.PayProductConstans;
import com.sandu.pay.order.service.MgrRechargeLogService;
import com.sandu.pay.order.service.PayAccountService;
import com.sandu.pay.order.service.PayOrderService;
import com.sandu.pay.wexin.common.Util;
import com.sandu.pay.wexin.metadata.WxTradeType;
import com.sandu.pay.wexin.protocol.UnifiedOrderReqData;
import com.sandu.pay.wexin.protocol.UnifiedOrderResData;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.user.model.CompanyFranchiserGroup;
import com.sandu.user.model.LoginUser;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.CompanyFranchiserGroupService;
import com.sandu.user.service.SysUserService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * H5支付模拟接口
 * @author WangHaiLin
 * @date 2018/6/28  11:23
 */
@RestController
@RequestMapping("/v1/web/pay/simulated/payOrder")
public class SimulatedController {
    //Json转换类
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[支付服务]:";
    private static Logger logger = LogManager.getLogger(PayOrderController.class);

    /**
     * 支付开关
     **/
    private static final Boolean PAYABLE = true;

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MgrRechargeLogService mgrRechargeLogService;
    /**
     * 未支付订单最大允许数
     */
    private static final Integer PAYINGORDERMAX = 10;
    @Resource
    private BasePlatformService basePlatformService;
    @Resource
    private CompanyFranchiserGroupService companyFranchiserGroupService;
    @Autowired
    private PayAccountService payAccountService;



    /**
     * 2b度币充值（微信h5支付，支付宝手机网页支付）模拟接口
     *
     * @param request
     * @param payOrderModel
     * @return ResponseEnvelope
     */
    @ResponseBody
    @RequestMapping(value = "/rechargeIntegralByH5Pay2b")
    public Object rechargeIntegralByH5Pay2b(HttpServletRequest request, @RequestBody PayOrderModel payOrderModel) {
        ResponseEnvelope responseEnvelope = new ResponseEnvelope();
        if (null == payOrderModel) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("下单参数为空");
            return responseEnvelope;
        }
        if (StringUtils.isEmpty(payOrderModel.getPayType())) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("支付类型为空：payType:{}" + payOrderModel.getPayType());
            return responseEnvelope;
        }
        if (null == payOrderModel.getProductId()) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("产品id为空");
            return responseEnvelope;
        }
        if (StringUtils.isEmpty(payOrderModel.getRedirectUrl())) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("跳转的url为空：redirectUrl:{}" + payOrderModel.getRedirectUrl());
            return responseEnvelope;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(payOrderModel.getRedirectUrl());
        sb.append(PayConstant.PARAM_NAME);
        payOrderModel.setRedirectUrl(sb.toString());
        Long userId = (Long) request.getAttribute("tokenUserId");
        SysUser sysUser = sysUserService.get(userId.intValue());
        if (null == sysUser) {
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("当前用户信息为空");
            return responseEnvelope;
        }
        String platformCode = null == request.getHeader(PlatformConstants.PLATFORM_CODE_KEY) ? "" : request.getHeader(PlatformConstants.PLATFORM_CODE_KEY);
        //service 中rechargeIntegralByH5Pay2b方法的逻辑////////////////
        if (StringUtils.isEmpty(platformCode)) {
            logger.info("平台编码：platformCode{}为空" + platformCode);
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("平台编码：platformCode{}为空");
            return responseEnvelope;
        }
        BasePlatform basePlatform = basePlatformService.getBasePlatformByCode(platformCode);
        if (null == basePlatform || basePlatform.getIsDeleted() != 0) {
            logger.info("平台信息：basePlatform{}为空" + (null == basePlatform ? "null" : gson.toJson(basePlatform)));
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("平台信息：basePlatform{}为空");
            return responseEnvelope;
        }
        if (StringUtils.isEmpty(payOrderModel.getPayType()) ||
                (!payOrderModel.getPayType().equals(PayType.WXPAY) && !payOrderModel.getPayType().equals(PayType.ALIPAY))) {
            logger.info("支付方式有误，payType:{}"+payOrderModel.getPayType());
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("支付方式有误，payType:{}");
            return responseEnvelope;
        }

        String tradeType = "";
        if (payOrderModel.getPayType().equals(PayType.WXPAY)) {
            tradeType = TradeType.H5;
        } else  if (payOrderModel.getPayType().equals(PayType.ALIPAY)) {
            tradeType = TradeType.WAP;
        }
        SysDictionary sysDictionary = sysDictionaryService.get(payOrderModel.getProductId());
        if (null == sysDictionary) {
            logger.info("数据字典中找不到度币信息"+(null==sysDictionary?"null":gson.toJson(sysDictionary)));
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("数据字典中找不到度币信息");
            return responseEnvelope;
        }
        int totalFee = sysDictionary.getValue().intValue(); // 充值的金额
        int adjustFee = Utils.getIntValue(sysDictionary.getAtt1().trim()); // 赠送的金额
        logger.info("2b的度币充值：产品id：productId{}" + payOrderModel.getProductId() + "充值的金额：totalFee{}" + totalFee + "赠送的金额：adjustFee{}" + adjustFee + "用户的id：userId{}" + userId );
        PayOrder payOrder = getOrder(  totalFee, payOrderModel.getPayType(), PayProductConstans.RECHARGE_PRODUCT_NAME, PayProductConstans.RECHARGE_PRODUCT_DESC, tradeType);
        payOrder.setPlatformId(basePlatform.getId());
        payOrder.setAdjustFee(adjustFee);
        LoginUser loginUser = new LoginUser(sysUser.getId(), sysUser.getUserType(), sysUser.getNickName(),
                sysUser.getMobile());
        CompanyFranchiserGroup companyFranchiserGroup = companyFranchiserGroupService.getCompanyFranchiserGroupByUserId(userId.intValue());
        int balanceAmount = 0;
        if (null == companyFranchiserGroup) {
            // 不具备经销商权限
            PayAccount payAccount = payAccountService.getPayAccountInfo(userId.intValue(), basePlatform.getId());
            if (null == payAccount) {
                responseEnvelope.setStatus(Boolean.FALSE);
                responseEnvelope.setMessage("用户度币信息为空，生成订单失败");
                return responseEnvelope;
            }
            balanceAmount = payAccount.getBalanceAmount().intValue();
        } else {
            // 具备经销商权限
            balanceAmount = null == companyFranchiserGroup.getCommonalityIntegral() ? 0 : companyFranchiserGroup.getCommonalityIntegral().intValue();
        }
        try {
            payOrder.setProductType(PayProductConstans.PAY_PRODUCT_TYPE);
            sysSave(payOrder, loginUser);
            payOrder.setUserId(userId.intValue());
            payOrder.setProductId(payOrderModel.getProductId());
            payOrder.setBizType(BizType.RECHARGE);
            payOrder.setFinanceType(FinanceType.IN);
            payOrder.setCurrentIntegral(totalFee + adjustFee + balanceAmount);
            int id = payOrderService.addPayOrder(payOrder,loginUser);
            if (id == 0) {
                logger.info("保存在线充值单失败");
                responseEnvelope.setStatus(Boolean.FALSE);
                responseEnvelope.setMessage("保存在线充值单失败");
                return responseEnvelope;
            }
            payOrder.setId(id);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderId", payOrder.getId());
            if (payOrderModel.getPayType().equals(PayType.WXPAY)) {
                logger.info("购买度币，微信h5支付start");
                String flag = "SUCCESS";
                //UnifiedOrderService orderService = new UnifiedOrderService();
                String timeExpire = Utils.getTimeExpire(30);
                //******微信支付回调地址(此处为空)
                //String wxNotifyUrl = ResourceConfig.WX_PAY_NOTIFY_URL;
                String wxNotifyUrl = null;
                payOrder.setNotifyUrl(wxNotifyUrl);

                UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(PayProductConstans.RECHARGE_PRODUCT_NAME, payOrder.getOrderNo(),
                        totalFee, WxTradeType.MWEB, timeExpire,wxNotifyUrl);

                //*********************************请求支付服务
                UnifiedOrderResData unifiedOrderResData = this.request(unifiedOrderReqData);

                if (unifiedOrderResData != null && unifiedOrderResData.getResult_code().equalsIgnoreCase(flag)
                        && unifiedOrderResData.getReturn_code().equalsIgnoreCase(flag)) {
                    String redirect_url = payOrderModel.getRedirectUrl() + payOrder.getOrderNo();
                    logger.info("购买度币微信h5支付预下单成功，微信回调地址是：wxNotifyUrl：{}" + wxNotifyUrl
                            + ",支付跳转链接:mwebUrl:{}" + unifiedOrderResData.getMweb_url() + ",微信同步的url是：" + redirect_url);
                    map.put("mwebUrl", unifiedOrderResData.getMweb_url()+"&redirect_url=" + java.net.URLEncoder.encode(redirect_url,   "utf-8"));
                    responseEnvelope.setObj(map);
                    responseEnvelope.setMessage("success");
                    responseEnvelope.setSuccess(Boolean.TRUE);
                    String prepayId = unifiedOrderResData.getPrepay_id(); // 预支付id
                    payOrder.setPrepayId(prepayId);
                    payOrder.setPayState(PayState.PAYING);
                    payOrderService.update(payOrder);
                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId.intValue());
                    mgrRechargeLog.setRechargeType(2);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount(Double.valueOf(totalFee));
                    mgrRechargeLog.setAdministratorId(userId.intValue());
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                    logger.info("购买度币，微信h5支付end");
                    return responseEnvelope;
                }  else {
                    logger.info("微信h5下单失败，订单号为：orderNo:{}" + payOrder.getOrderNo());
                    responseEnvelope.setStatus(Boolean.FALSE);
                    responseEnvelope.setMessage("微信h5下单失败，订单号为：orderNo:{}" + payOrder.getOrderNo());
                    return responseEnvelope;
                }
            }
            if (payOrderModel.getPayType().equals(PayType.ALIPAY)) {
                //****接收支付宝通知结果页面地址(此处为空)
                //String alNotifyUrl = ResourceConfig.ALIPAY_NOTIFY_URL;
                String alNotifyUrl = null;
                payOrder.setNotifyUrl(alNotifyUrl);
                String returnUrl = payOrderModel.getRedirectUrl() + payOrder.getOrderNo();

                String form = this.addScanpayOrder(payOrder, alNotifyUrl, returnUrl);

                if (StringUtils.isEmpty(form)) {
                    responseEnvelope.setStatus(Boolean.FALSE);
                    responseEnvelope.setMessage("支付宝手机网页下单失败，订单号为：orderNo:{}" + payOrder.getOrderNo());
                    return responseEnvelope;
                } else {
                    map.put("form", form);
                    responseEnvelope.setObj(map);
                    responseEnvelope.setMessage("success");
                    responseEnvelope.setSuccess(Boolean.TRUE);
                    payOrder.setPayState(PayState.PAYING);
                    payOrderService.update(payOrder);
                    //FIXME:每次获取充值单就添加一条充值记录
                    MgrRechargeLog mgrRechargeLog = new MgrRechargeLog();
                    mgrRechargeLog.setUserId(userId.intValue());
                    mgrRechargeLog.setRechargeType(3);
                    mgrRechargeLog.setRechargeStatus(2);
                    mgrRechargeLog.setRechargeAmount(Double.valueOf(totalFee));
                    mgrRechargeLog.setAdministratorId(userId.intValue());
                    mgrRechargeLog.setOrderNo(payOrder.getOrderNo());
                    mgrRechargeLog.setPlatformBussinessType(basePlatform.getPlatformBussinessType()); // 平台类型
                    mgrRechargeLogService.sysSave(mgrRechargeLog,loginUser);
                    mgrRechargeLogService.add(mgrRechargeLog);
                    return responseEnvelope;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("2b支付宝手机网页支付或者微信h5支付下单异常,内容是：",e);
            responseEnvelope.setStatus(Boolean.FALSE);
            responseEnvelope.setMessage("下单异常");
            return responseEnvelope;
        }
        return null;
    }

    private PayOrder getOrder(int totalFee, String payType, String productName, String productDesc, String tradeType) {
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderDate(new Date());
        payOrder.setPayState(PayState.NOTPAY);
        payOrder.setOrderNo(IdGenerator.generateNo());
        payOrder.setTotalFee(totalFee);
        payOrder.setPayType(payType);
        payOrder.setProductDesc(productDesc);
        payOrder.setProductName(productName);
        payOrder.setTradeType(tradeType);
        return payOrder;
    }

    public void sysSave(PayOrder order, LoginUser loginUser){
        if (order != null) {
            if (order.getId() == null) {
                order.setUserId(loginUser.getId());
                order.setGmtCreate(new Date());
                order.setCreator(loginUser.getLoginName());
                order.setIsDeleted(0);
                if (order.getSysCode() == null || "".equals(order.getSysCode())) {
                    order.setSysCode(
                            Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }
            order.setGmtModified(new Date());
            order.setModifier(loginUser.getLoginName());
        }
    }


    /**
     *
     * 支付宝手机网页支付
     *
     * @param order 订单信息对象
     * @param notifyUrl 异步通知地址（改变订单状态）
     * @param returnUrl 同步地址（支付成功跳转的地址）
     * @return
     */
    public static String addScanpayOrder(PayOrder order, String notifyUrl, String  returnUrl) {
        logger.info("订单信息：order:{}" + order.toString());
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getOrderNo();
        // 订单名称，必填
        String subject = order.getProductName();
        // 付款金额，必填(因为订单里面保存的是分，所以要转换为元，直接除以100)
        String total_amount = String.valueOf(Double.valueOf(order.getTotalFee())/100);
        // 商品描述，可空
        String body = order.getProductDesc();
        // 超时时间 可空
        String timeout_express = org.apache.commons.lang3.StringUtils.isBlank(order.getTimeoutExpress()) ? "120m" : order.getTimeoutExpress();
        String SIGNTYPE = AlipayConfig.getSign_type_two();
        String serverUrl = AlipayConfig.getOpen_api_domain();
        String appId = AlipayConfig.getApp_id();
        //支付宝私钥(此处为空)
        //String privateKey = ResourceConfig.ALIPAY_PRIVATE_KEY_MOBILE_PAY;
        String privateKey=null;
        String format = AlipayConfig.getFormat();
        String charset = AlipayConfig.getInput_charset();
        //支付宝公钥（此处为空）
        //String alipayPulicKey = ResourceConfig.ALIPAY_PUBLIC_KEY_MOBILE_PAY;
        String alipayPulicKey=null;
        AlipayClient client = new DefaultAlipayClient(serverUrl,
                appId,
                privateKey,
                format, charset,
                alipayPulicKey,
                SIGNTYPE);
        AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();
        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(total_amount);
        model.setBody(body);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(AlipayConfig.getProduct_code());
        alipay_request.setBizModel(model);
        alipay_request.setNotifyUrl(notifyUrl);
        if (!com.sandu.common.util.StringUtils.isEmpty(returnUrl)) {
            alipay_request.setReturnUrl(returnUrl);
            logger.info("支付宝同步地址是：" + returnUrl);
        }

        logger.info("支付宝回调地址是：" + notifyUrl);
        String form = "";
        try {
            //**************************************************调取支付客户端
            //form = client.pageExecute(alipay_request).getBody();
            form="make it myself";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return form;
    }

    /**
     * 请求支付服务
     * @param unifiedOrderReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public UnifiedOrderResData request(UnifiedOrderReqData unifiedOrderReqData) throws Exception {
        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        XStream xStreamForRequestPostData = new XStream(
                new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        // 将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(unifiedOrderReqData);
        //logger.error("---------------------Request Data:"+postDataXML);

        //***************************************************************发起https请求
        //String responseString = BaseService.sendPost(unifiedOrderReqData);
        String responseString=null;

        //logger.error("---------------------responseString:"+responseString);
        //将从API返回的XML数据映射到Java对象
        UnifiedOrderResData scanPayResData=null;
        //*********************************************************手动为UnifiedOrderResData摄值(假值,真正的值在https请求返回的xml中)
        scanPayResData.setResult_code("SUCCESS");
        scanPayResData.setReturn_code("SUCCESS");
        scanPayResData.setPrepay_id(null);
        scanPayResData.setMweb_url("");
        //////System.out.println("responseString:"+responseString);
        if(StringUtils.isNotEmpty(responseString)){
            scanPayResData = (UnifiedOrderResData) Util.getObjectFromXML(responseString, UnifiedOrderResData.class);
        }
        return scanPayResData;
    }
}
