package com.sandu.pay.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.*;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.sandu.common.pay.QrCodeUtil;
import com.sandu.common.util.FileUploadUtils;
import com.sandu.config.ResourceConfig;
import com.sandu.pay.alipay.common.AlipayConfig;
import com.sandu.pay.alipay.model.ExtendParams;
import com.sandu.pay.alipay.model.GoodsDetail;
import com.sandu.pay.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import com.sandu.pay.alipay.model.result.AlipayF2FPrecreateResult;
import com.sandu.pay.alipay.service.AlipayTradeService;
import com.sandu.pay.alipay.service.impl.AlipayTradeServiceImpl;
import com.sandu.pay.order.metadata.ScanPayReqData;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.ResultMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScanPayUtil {
    private static Logger logger = LogManager.getLogger(ScanPayUtil.class);

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
//         BigDecimal   b   =   new   BigDecimal(order.getTotalFee());  
 // 		 String totalAmount  =   b.multiply(new BigDecimal(0.01)).setScale(2,   BigDecimal.ROUND_DOWN).toPlainString();
  		String totalAmount = order.getTotalFee()==null?"0":String.valueOf(order.getTotalFee().intValue());       

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
//                 String codeUrl = QrCodeUtil.generateQrCode(res.getQrCode(), order.getOrderNo());
                 /*reqData.setCode_url(FileUploadUtils.RESOURCES_URL+codeUrl);*/
                 /*reqData.setCode_url(Utils.getAbsoluteUrlByRelativeUrl(codeUrl)); */
                 reqData.setCode_url(res.getQrCode());//直接返回访问路径，不在返回2维码
//                 reqData.setQrCodePath(codeUrl);
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

    //接口去掉HttpServletRequest参数
    public static ResultMessage addScanpayOrder(PayOrder order,ScanPayReqData reqData,String imageServerUrl,String upladRoot){
    	 ResultMessage message=new ResultMessage();
    	 AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
         // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
         // 需保证商户系统端不能重复，建议通过数据库sequence生成，
         String outTradeNo = order.getOrderNo();

         // (必填) 订单标题，粗略描述用户的支付目的。如“喜士多（浦东店）消费”
         String subject =order.getProductName();

         // (必填) 订单总金额，单位为元，不能超过1亿元
         // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
         String totalAmount =String.valueOf(order.getTotalFee());

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
                 String codeUrl = QrCodeUtil.generateQrCode(upladRoot,res.getQrCode(), order.getOrderNo());
                 /*reqData.setCode_url(FileUploadUtils.RESOURCES_URL+codeUrl);*/
                 //reqData.setCode_url(Utils.getAbsoluteUrlByRelativeUrl(codeUrl));
//                 reqData.setCode_url(res.getQrCode());//直接返回访问路径，不在返回2维码
//                 reqData.setQrCodePath(res.getQrCode());
                 reqData.setCode_url(imageServerUrl+codeUrl);
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

    //接口去掉HttpServletRequest参数
    public static ResultMessage addScanpayOrder(PayOrder order,ScanPayReqData reqData,Integer totalFee,String imageServerUrl,String upladRoot){
        ResultMessage message=new ResultMessage();
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo();

        // (必填) 订单标题，粗略描述用户的支付目的。如“喜士多（浦东店）消费”
        String subject =order.getProductName();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        BigDecimal   b   =   new   BigDecimal(totalFee.intValue());  
        String totalAmount  =   b.multiply(new BigDecimal(0.01)).setScale(2,   BigDecimal.ROUND_DOWN).toPlainString();
        //String totalAmount = totalFee==null?"0":String.valueOf(totalFee.intValue());
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
                .setNotifyUrl(order.getNotifyUrl())
                .setGoodsDetailList(goodsDetailList);
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                AlipayTradePrecreateResponse res = result.getResponse();
                String codeUrl = QrCodeUtil.generateQrCode(upladRoot,res.getQrCode(), order.getOrderNo());
                 /*reqData.setCode_url(FileUploadUtils.RESOURCES_URL+codeUrl);*/
                //reqData.setCode_url(Utils.getAbsoluteUrlByRelativeUrl(codeUrl));
//                 reqData.setCode_url(res.getQrCode());//直接返回访问路径，不在返回2维码
//                 reqData.setQrCodePath(res.getQrCode());
                reqData.setCode_url(imageServerUrl+codeUrl);
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

    /**
     *
     * 支付宝手机网页支付
     *
     * @param order 订单信息对象
     * @param notifyUrl 异步通知地址（改变订单状态）
     * @param returnUrl 同步地址（支付成功跳转的地址）
     * @return
     */
    public static String addScanpayOrder(PayOrder order,String notifyUrl, String  returnUrl) {
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
        String privateKey = ResourceConfig.ALIPAY_PRIVATE_KEY_MOBILE_PAY;
        String format = AlipayConfig.getFormat();
        String charset = AlipayConfig.getInput_charset();
        String alipayPulicKey = ResourceConfig.ALIPAY_PUBLIC_KEY_MOBILE_PAY;
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
            form = client.pageExecute(alipay_request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    
    /**
    *
    * 支付宝手机------------------------App支付
     * @throws AlipayApiException 
    *
    */
   public static String addScanpayAppOrder(PayOrder order, String notifyUrl) throws AlipayApiException {
	   logger.info("支付宝手机------------------------App支付");
	 //支付宝分配给开发者的应用ID
       String appId = AlipayConfig.getApp_id();
       //支付宝私钥(应用密钥，即：应用私钥2048（签名工具生成）)
       String privateKey = ResourceConfig.ALIPAY_PRIVATE_KEY_MOBILE_PAY;
       //支付宝公钥
       String alipayPulicKey = ResourceConfig.ALIPAY_PUBLIC_KEY_MOBILE_PAY;
       //字符编码格式 utf-8
       String charset = AlipayConfig.getInput_charset();
       //加密方式 RSA2(签名类型)
       String SIGNTYPE = AlipayConfig.getSign_type_two();
       //json(参数返回格式)
       String format = AlipayConfig.getFormat();
       //公共API接口地址  https://openapi.alipay.com/gateway.do(支付宝网关)
       String serverUrl = AlipayConfig.getOpen_api_domain();
       
     //测试环境end
       AlipayClient client = new DefaultAlipayClient(serverUrl,
               appId,
               privateKey,
               format, charset,
               alipayPulicKey,
               SIGNTYPE);// 获得初始化的AlipayClient
       
       AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest(); // 创建API对应的request类
       AlipayTradeAppPayModel model = new AlipayTradeAppPayModel(); // 封装请求支付信息
       // 商品描述，可空
       String body = order.getProductDesc();
       // 订单名称，必填
       String subject = order.getProductName();
       // 商户订单号，商户网站订单系统中唯一订单号，必填
       String out_trade_no = order.getOrderNo();
       // 超时时间 可空
       String timeout_express = org.apache.commons.lang3.StringUtils.isBlank(order.getTimeoutExpress()) ? "120m" : order.getTimeoutExpress();
       // 付款金额，必填(因为订单里面保存的是分，所以要转换为元，直接除以100)
       String total_amount = String.valueOf(Double.valueOf(order.getTotalFee())/100);
       //笔订单允许的最晚付款时间，逾期将关闭交易。
       model.setTimeoutExpress(timeout_express);
       //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
       model.setBody(body);
       //商品的标题/交易标题/订单标题/订单关键字等。
       model.setSubject(subject);
       // 商户网站唯一订单号
       model.setOutTradeNo(out_trade_no);
       // 订单总金额，单位为元
       model.setTotalAmount(total_amount);
     //销售产品码，商家和支付宝签约的产品码 QUICK_MSECURITY_PAY
       model.setProductCode("QUICK_MSECURITY_PAY");
       request.setBizModel(model);
	   request.setNotifyUrl(notifyUrl);
       logger.info("notifyUrl:"+notifyUrl);
	   AlipayTradeAppPayResponse response = client.sdkExecute(request); // 通过alipayClient调用API，获得对应的response类
       String orderString = response.getBody();
       logger.info("就是orderString 可以直接给客户端请求，无需再做处理:"+orderString);//就是orderString 可以直接给客户端请求，无需再做处理。
       return orderString;
   }

    
    
    
    /**
     * 接口去掉HttpServletRequest参数(支付宝扫码支付返回二维码，需要用户自己拼接)
     *
     * @param order
     * @param reqData
     * @param totalFee
     * @return
     */
    public static ResultMessage addScanpayOrderGetQrCode(PayOrder order,ScanPayReqData reqData,Integer totalFee){
        ResultMessage message=new ResultMessage();
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo();

        // (必填) 订单标题，粗略描述用户的支付目的。如“喜士多（浦东店）消费”
        String subject =order.getProductName();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        BigDecimal   b   =   new   BigDecimal(totalFee.intValue());
        String totalAmount  =   b.multiply(new BigDecimal(0.01)).setScale(2,   BigDecimal.ROUND_DOWN).toPlainString();
        //String totalAmount = totalFee==null?"0":String.valueOf(totalFee.intValue());
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
                .setNotifyUrl(order.getNotifyUrl())
                .setGoodsDetailList(goodsDetailList);
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                AlipayTradePrecreateResponse res = result.getResponse();
                reqData.setQrCodePath(res.getQrCode());
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
