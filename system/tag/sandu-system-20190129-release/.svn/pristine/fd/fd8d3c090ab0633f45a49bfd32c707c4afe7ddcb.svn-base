package com.sandu.web.merchantManagePay;

import com.sandu.api.merchantManagePay.model.BatchPayPlanFee;
import com.sandu.api.merchantManagePay.model.PayOrder;
import com.sandu.api.merchantManagePay.service.PayOrderService;
import com.sandu.api.user.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.web.BaseController;
import com.sandu.web.servicepurchase.HttpResult;
import com.sandu.web.servicepurchase.HttpService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1/merchantManagePay")
public class MerchantManagePayController extends BaseController{

    @Resource(name = "merchantManagePayOrderService")
    private PayOrderService payOrderService;

    @Autowired
    private HttpService httpService;

    @Value("${service.pay.url}")
    private String payUrl;

    @Value("${rechargeDubi.pay.notifyUrl}")
    private String notifyUrl;

    private final static String PAY_METHOD_WX = "wxScanCodePay";
    private final static String PAY_METHOD_ZF = "aliScanCodePay";

    @Value("${wxpay.ip}")
    private String wxPayIp;

    @ApiOperation("商家后台度币充值")
    @PostMapping("/rechargeDubi")
    public Object rechargeDubi(@RequestParam(value = "money", required = true) Double money,
                               @RequestParam(value = "payType", required = true) Integer payType,
                               HttpServletRequest request
    ) {

        if (Objects.isNull(money)) {
            return new ResponseEnvelope<>(false, "parameter money is null");
        }

        String platformCode = request.getHeader("Platform-Code");

        String token = request.getHeader("Authorization");

        if (StringUtils.isEmpty(platformCode)) {
            return new ResponseEnvelope<>(false, "header Platform-Code is null");
        }

        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        Map<String,Object> result = new HashMap<>();
        HttpResult httpResult = null;
        try {
            //插入订单信息
            String orderNo = payOrderService.insertPayOrder(loginUser.getId(), money, payType, platformCode);
            //调起支付获取二维码
            httpResult = this.initPay(loginUser.getId(), token, platformCode, money, payType, orderNo);
            result.put("orderNo",orderNo);
        } catch (Exception e) {
            log.error("系统错误", e);
        }

        log.info("获取支付二维码,httpResult:{}", httpResult);
        if (Objects.equals(httpResult.getCode(), 200)) {
            result.put("payParam",httpResult.getBody());
            return new ResponseEnvelope<>(true,result);
        }
        return new ResponseEnvelope<>(false, "系统异常，获取支付信息失败");
    }


    @ApiOperation("商家后台批量购买版权费")
    @PostMapping(value = "/batchFee")
    public Object batchPayDesignPlanFee(HttpServletRequest request,@RequestBody@Valid BatchPayPlanFee batchPayPlanFee, BindingResult validResult){

        //1.数据校验
        if (validResult.hasErrors()) {
            return processValidError(validResult,new com.sandu.commons.ResponseEnvelope());
        }
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        batchPayPlanFee.setUserId(loginUser.getId());

        String platformCode = request.getHeader("Platform-Code");

        batchPayPlanFee.setPlatformCode(platformCode);

        try {
            payOrderService.handlerBatchPay(batchPayPlanFee);
        } catch (Exception e) {
            log.error("系统错误",e);
            return new ResponseEnvelope<>(false,e.getMessage());
        }
        return new ResponseEnvelope<>(true,"购买成功!");
    }

    private HttpResult initPay(Long userId, String token, String platformCode, Double money, Integer payType, String orderNo) {

        Map<String, String> requestParam = new HashMap<>();
        requestParam.put("intenalTradeNo", orderNo);
        requestParam.put("tradeDesc", "三度云享家_度币充值");
        log.info("支付金额:{}", money);
        requestParam.put("totalFee", new BigDecimal(money * 100).intValue() + "");
        //requestParam.put("totalFee", "1");
        requestParam.put("payMethod", payType == 0 ? PAY_METHOD_WX : PAY_METHOD_ZF);
        requestParam.put("ip", wxPayIp);
        requestParam.put("notifyUrl", notifyUrl);
        requestParam.put("operator", userId.toString());
        requestParam.put("source", "3"); //表示商家后台
        log.info("支付参数:{}", requestParam);
        List<BasicHeader> basicHeaders = Arrays.asList(new BasicHeader("Platform-Code", platformCode),
                new BasicHeader("Authorization", token));
        HttpResult httpResult = null;
        try {
            httpResult = httpService.doPost(payUrl, requestParam, basicHeaders);
            return httpResult;
        } catch (Exception e) {
            log.error("支付失败", e);
        }
        log.info("rest pay result:{}", httpResult);
        return new HttpResult();
    }

    //获取支付成功订单
    @ApiOperation(value = "获取支付成功订单")
    @GetMapping(value = "/getOrder")
    public Object getOrderSuccess(@RequestParam(value = "orderNo")String orderNo){
        PayOrder payOrder;
        try {
            payOrder = payOrderService.getOrderSuccess(orderNo);
        } catch (Exception e) {
           log.error("系统错误",e);
           return new ResponseEnvelope<>(false,"系统错误");
        }
        return new ResponseEnvelope<>(true,payOrder);
    }

    @ApiOperation(value = "支付完成回调接口")
    @PostMapping("/pay/callback")
    public void payCallbackFun(HttpServletRequest request) {
        log.info("支付完成，接口回调。");
        log.info("request param:{}", request.getParameterMap());
        String recordStatus;
        String orderNo = "";
        try {
            if ("SUCCESS".equals(request.getParameter("resultCode"))) {
                recordStatus = "SUCCESS";
            } else {
                recordStatus = "FAIL";
            }
            orderNo = request.getParameter("intenalTradeNo");
            //更新订单状态
            int row = payOrderService.updatePayState(recordStatus,orderNo);
            log.info("商家后台充值度币回调{更新订单} =>{}"+row);
            //插入用户充值记录
            if (Objects.equals("SUCCESS",recordStatus)){
                //支付成功
                Map<String, Integer> resultMap = payOrderService.insertMgrRechargeLog(orderNo);
                log.info("商家后台充值度币回调{插入充值记录} =>{}"+resultMap.toString());
                //更新用户dubi
                payOrderService.updateUserDubi(resultMap);
            }
        } catch (Exception e) {
            log.error("系统错误", e);
        }
    }

}
