package com.nork.pay.alipay;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipayHashMap;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.RequestParametersHolder;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.internal.util.WebUtils;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.Utils;
import com.nork.pay.alipay.common.AlipayConfig;
import com.nork.pay.alipay.model.ExtendParams;
import com.nork.pay.alipay.model.GoodsDetail;
import com.nork.pay.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import com.nork.pay.alipay.model.result.AlipayF2FPrecreateResult;
import com.nork.pay.alipay.service.AlipayTradeService;
import com.nork.pay.alipay.service.impl.AlipayTradeServiceImpl;
import com.nork.pay.common.QrCodeUtil;
import com.nork.pay.metadata.ScanPayReqData;
import com.nork.pay.model.PayOrder;
import com.nork.pay.model.ResultMessage;

public class ScanPayUtil {
	
	public static Map<String, Object> requestScanpay(AlipayTradePrecreateRequestBuilder builder) throws AlipayApiException{
		Map<String, Object> result = new HashMap<String, Object>();
	    int  connectTimeout = 3000;
	    int  readTimeout    = 15000;
		String charset=AlipayConfig.getInput_charset();
		String appId=AlipayConfig.getApp_id();
		String sign_type=AlipayConfig.getSign_type();
		String privateKey=AlipayConfig.getPrivate_key();
		String sign=null;
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(builder.getNotifyUrl());
        //request.putOtherTextParam("app_auth_token", builder.getAppAuthToken());
        request.setBizContent(builder.toJsonString());
        RequestParametersHolder requestHolder = new RequestParametersHolder();
        AlipayHashMap appParams = new AlipayHashMap(request.getTextParams());
        requestHolder.setApplicationParams(appParams);

        if (StringUtils.isEmpty(charset)) {
            charset = AlipayConstants.CHARSET_UTF8;
        }

        AlipayHashMap protocalMustParams = new AlipayHashMap();
        protocalMustParams.put(AlipayConstants.METHOD, request.getApiMethodName());
        protocalMustParams.put(AlipayConstants.VERSION, request.getApiVersion());
        protocalMustParams.put(AlipayConstants.APP_ID, appId);
        protocalMustParams.put(AlipayConstants.SIGN_TYPE, sign_type);
        protocalMustParams.put(AlipayConstants.TERMINAL_TYPE, request.getTerminalType());
        protocalMustParams.put(AlipayConstants.TERMINAL_INFO, request.getTerminalInfo());
        protocalMustParams.put(AlipayConstants.NOTIFY_URL, request.getNotifyUrl());
        protocalMustParams.put(AlipayConstants.CHARSET, charset);

        Long timestamp = System.currentTimeMillis();
        DateFormat df = new SimpleDateFormat(AlipayConstants.DATE_TIME_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone(AlipayConstants.DATE_TIMEZONE));
        protocalMustParams.put(AlipayConstants.TIMESTAMP, df.format(new Date(timestamp)));
        requestHolder.setProtocalMustParams(protocalMustParams);

        AlipayHashMap protocalOptParams = new AlipayHashMap();
        protocalOptParams.put(AlipayConstants.FORMAT, "json");
        protocalOptParams.put(AlipayConstants.ACCESS_TOKEN, null);
        protocalOptParams.put(AlipayConstants.ALIPAY_SDK, AlipayConstants.SDK_VERSION);
        protocalOptParams.put(AlipayConstants.PROD_CODE, request.getProdCode());
        requestHolder.setProtocalOptParams(protocalOptParams);

        if (AlipayConstants.SIGN_TYPE_RSA.equals(sign_type)) {
        	String signContent = AlipaySignature.getSignatureContent(requestHolder);
            sign=AlipaySignature.rsaSign(signContent, privateKey, charset); 
            //////System.out.println("sign:"+sign);
            protocalMustParams.put(AlipayConstants.SIGN,sign);
        } else {
            protocalMustParams.put(AlipayConstants.SIGN, "");
        }
        String serverUrl=AlipayConfig.getOpen_api_domain();
        StringBuffer urlSb = new StringBuffer(serverUrl);
        try {
            String sysMustQuery = WebUtils.buildQuery(requestHolder.getProtocalMustParams(),
                charset);
            String sysOptQuery = WebUtils.buildQuery(requestHolder.getProtocalOptParams(), charset);

            urlSb.append("?");
            urlSb.append(sysMustQuery);
            if (sysOptQuery != null & sysOptQuery.length() > 0) {
                urlSb.append("&");
                urlSb.append(sysOptQuery);
            }
        } catch (IOException e) {
            throw new AlipayApiException(e);
        }

        String rsp = null;
        try {
        	rsp = WebUtils.doPost(urlSb.toString(), appParams, charset, connectTimeout,
                    readTimeout);
        } catch (IOException e) {
            throw new AlipayApiException(e);
        }
        //////System.out.println("rsp:"+rsp);
        result.put("rsp", rsp);
        result.put("textParams", appParams);
        result.put("protocalMustParams", protocalMustParams);
        result.put("protocalOptParams", protocalOptParams);
        result.put("url", urlSb.toString());
        return result;
	}
	
    public static ResultMessage addScanpayOrder(PayOrder order,HttpServletRequest request,ScanPayReqData reqData){
    	ResultMessage message=new ResultMessage();
    	 AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

         // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
         // 需保证商户系统端不能重复，建议通过数据库sequence生成，
         String outTradeNo = order.getOrderNo();

         // (必填) 订单标题，粗略描述用户的支付目的。如“喜士多（浦东店）消费”
         String subject =order.getProductName();

         // (必填) 订单总金额，单位为元，不能超过1亿元
         // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
         String totalAmount =String.valueOf(order.getTotalFee()*0.01);

         // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
         // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】

         // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
         // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
         String sellerId = AlipayConfig.getSeller_id();

         // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
         String body = order.getProductDesc();

         // 商户操作员编号，添加此参数可以为商户操作员做销售统计
         //String operatorId = "test_operator_id";

         // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
         String storeId = order.getStoreId();

         // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
         ExtendParams extendParams = new ExtendParams();
         extendParams.setSysServiceProviderId(AlipayConfig.getSeller_id());

         // 支付超时，定义为120分钟
         String timeoutExpress = org.apache.commons.lang3.StringUtils.isBlank(order.getTimeoutExpress()) ? "120m" : order.getTimeoutExpress();
         

         // 商品明细列表，需填写购买商品详细信息，
         List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
         // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
         GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", order.getProductName(), order.getTotalFee(), 1);
         // 创建好一个商品后添加至商品明细列表
         goodsDetailList.add(goods1);
         		
         AlipayTradePrecreateRequestBuilder builder =new AlipayTradePrecreateRequestBuilder()
                 .setSubject(subject)
                 .setTotalAmount(totalAmount)
                 .setOutTradeNo(outTradeNo)
                 //.setUndiscountableAmount(undiscountableAmount)
                 .setSellerId(sellerId)
                 .setBody(body)
                 //.setOperatorId(operatorId)
                 .setStoreId(storeId)
                 .setExtendParams(extendParams)
                 .setTimeoutExpress(timeoutExpress)
                 .setNotifyUrl(AlipayConfig.getNotify_url())
                 .setGoodsDetailList(goodsDetailList);
         AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
         switch (result.getTradeStatus()) {
             case SUCCESS: 
                 AlipayTradePrecreateResponse res = result.getResponse();
                 String codeUrl=QrCodeUtil.generateQrCode(request, res.getQrCode(), order.getOrderNo());
                 /*reqData.setCode_url(FileUploadUtils.RESOURCES_URL+codeUrl);*/
                 /*reqData.setCode_url(Utils.getAbsoluteUrlByRelativeUrl(codeUrl)); */
                 reqData.setCode_url(res.getQrCode());//直接返回访问路径，不在返回2维码
                 reqData.setQrCodePath(codeUrl); 
                 message.setSuccess(true);
                 break;

             case FAILED:
            	 message.setMessage("支付宝预下单失败!");
                 break;
                 
             case UNKNOWN:
            	 message.setMessage("系统异常，预下单状态未知!");
                 break;

             default:
            	 message.setMessage("不支持的交易状态，交易返回异常!");
                 break;
         }
         return message;
    }
    //5.6版本：接口去掉HttpServletRequest参数
    public static ResultMessage addScanpayOrder(PayOrder order,ScanPayReqData reqData){
    	ResultMessage message=new ResultMessage();
    	 AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

         // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
         // 需保证商户系统端不能重复，建议通过数据库sequence生成，
         String outTradeNo = order.getOrderNo();

         // (必填) 订单标题，粗略描述用户的支付目的。如“喜士多（浦东店）消费”
         String subject =order.getProductName();

         // (必填) 订单总金额，单位为元，不能超过1亿元
         // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
         String totalAmount =String.valueOf(order.getTotalFee()*0.01);

         // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
         // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】

         // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
         // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
         String sellerId = AlipayConfig.getSeller_id();

         // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
         String body = order.getProductDesc();

         // 商户操作员编号，添加此参数可以为商户操作员做销售统计
         //String operatorId = "test_operator_id";

         // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
         String storeId = order.getStoreId();

         // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
         ExtendParams extendParams = new ExtendParams();
         extendParams.setSysServiceProviderId(AlipayConfig.getSeller_id());

         // 支付超时，定义为120分钟
         String timeoutExpress = org.apache.commons.lang3.StringUtils.isBlank(order.getTimeoutExpress()) ? "120m" : order.getTimeoutExpress();
         

         // 商品明细列表，需填写购买商品详细信息，
         List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
         // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
         GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", order.getProductName(), order.getTotalFee(), 1);
         // 创建好一个商品后添加至商品明细列表
         goodsDetailList.add(goods1);
         		
         AlipayTradePrecreateRequestBuilder builder =new AlipayTradePrecreateRequestBuilder()
                 .setSubject(subject)
                 .setTotalAmount(totalAmount)
                 .setOutTradeNo(outTradeNo)
                 //.setUndiscountableAmount(undiscountableAmount)
                 .setSellerId(sellerId)
                 .setBody(body)
                 //.setOperatorId(operatorId)
                 .setStoreId(storeId)
                 .setExtendParams(extendParams)
                 .setTimeoutExpress(timeoutExpress)
                 .setNotifyUrl(AlipayConfig.getNotify_url())
                 .setGoodsDetailList(goodsDetailList);
         AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
         switch (result.getTradeStatus()) {
             case SUCCESS: 
                 AlipayTradePrecreateResponse res = result.getResponse();
                 String codeUrl = QrCodeUtil.generateQrCode(res.getQrCode(), order.getOrderNo());
                 /*reqData.setCode_url(FileUploadUtils.RESOURCES_URL+codeUrl);*/
                 //reqData.setCode_url(Utils.getAbsoluteUrlByRelativeUrl(codeUrl));
                 reqData.setCode_url(res.getQrCode());//直接返回访问路径，不在返回2维码
                 reqData.setQrCodePath(res.getQrCode());
                 //reqData.setQrCodePath(codeUrl);
                 //reqData.setQrCodePath(QrCodeUtil.generateQrCode(res.getQrCode(), order.getOrderNo()));
                 message.setSuccess(true);
                 break;

             case FAILED:
            	 message.setMessage("支付宝预下单失败!");
                 break;
                 
             case UNKNOWN:
            	 message.setMessage("系统异常，预下单状态未知!");
                 break;

             default:
            	 message.setMessage("不支持的交易状态，交易返回异常!");
                 break;
         }
         return message;
    }
}
