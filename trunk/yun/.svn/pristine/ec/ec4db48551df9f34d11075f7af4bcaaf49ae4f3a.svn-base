package com.sandu.pay.alipay.model;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.sandu.common.pay.ZxingUtils;
import com.sandu.common.util.Utils;
import com.sandu.pay.alipay.common.AlipayConfig;
import com.sandu.pay.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import com.sandu.pay.alipay.model.result.AlipayF2FPrecreateResult;
import com.sandu.pay.alipay.service.AlipayTradeService;
import com.sandu.pay.alipay.service.impl.AlipayTradeServiceImpl;
import com.sandu.pay.order.metadata.ScanPayReqData;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.ResultMessage;

import java.util.ArrayList;
import java.util.List;


public class ScanPayUtil {

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
                 .setNotifyUrl(order.getNotifyUrl())
                 .setGoodsDetailList(goodsDetailList);
         AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
         switch (result.getTradeStatus()) {
             case SUCCESS: 
                 AlipayTradePrecreateResponse res = result.getResponse();
                 String codeUrl=generateQrCode(res.getQrCode(), order.getOrderNo());
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
    
    public static String generateQrCode(String content, String batchNo) {
		String codeUrl = Utils.getPropertyName("config/res","pay.files.code.update.path","/AA/f_resource/[yyyy]/[MM]/[dd]/[HH]/pay/files/code/")+"qr-" + batchNo + ".png";
		codeUrl = Utils.replaceDate(codeUrl);
		// String  path=request.getServletContext().getRealPath("/files/code")+batchNo+".png";
//		String basePath = request.getServletContext().getRealPath("/");
		/*String basePath = Constants.UPLOAD_ROOT;*/
		/*String filePath = new StringBuilder(basePath).append(codeUrl).toString();*/
		String filePath = Utils.getAbsolutePath(codeUrl, null);
		ZxingUtils.getQRCodeImge(content, 256, filePath);
		return codeUrl;
	}
}
