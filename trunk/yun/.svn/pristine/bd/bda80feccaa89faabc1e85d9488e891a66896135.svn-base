package com.sandu.gateway.pay.callback.service.async;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.jpay.alipay.AliPayApi;
import com.jpay.alipay.AliPayApiConfig;
import com.jpay.alipay.AliPayApiConfigKit;
import com.sandu.common.util.StringUtils;
import com.sandu.config.GatewayPayConfig;
import com.sandu.gateway.pay.common.exception.BizException;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.gateway.pay.common.http.HttpClientUtil;
import com.sandu.gateway.pay.input.PayTradeRefundQueryVo;
import com.sandu.gateway.pay.trade.model.PayTradeRefundRecord;
import com.sandu.gateway.pay.trade.service.PayTradeRefundRecordLogService;
import com.sandu.gateway.pay.trade.service.PayTradeRefundRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Component
public class AsyncQueryAliRefundResult {

    private Logger logger = LoggerFactory.getLogger(AsyncQueryAliRefundResult.class);

    @Resource
    private PayTradeRefundRecordService payTradeRefundRecordService ;

    @Resource
    private PayTradeRefundRecordLogService payTradeRefundRecordLogService;

    /**
     * 异步查询Ali支付退款
     * @param payTradeNo
     */
    @Async
    public void asyncQueryAliRefundResult(String payTradeNo,Object refundPayTime){
        logger.info("开始异步查询支付宝退款是否成功 =>{交易商户订单号}" + payTradeNo);
        boolean isSuccess;

        AlipayTradeFastpayRefundQueryModel params = this.buildQueryRefundParams(payTradeNo);

        //获取请求结果
        Map resultMap = this.getResponseResult(params);

        //解析响应结果,获取转态
        isSuccess = this.resolveResult(resultMap,params);

        if (isSuccess){
            //获取退款交易信息
            PayTradeRefundRecord record = this.getTradeRefundRecord(payTradeNo);

            //记录外部系统通知日志
            saveExternalNotifyBody(record.getId(),(String)resultMap.get("jsonResult"));

            //step3.保存外部系统(微信,支付宝)退款号及更新交易状态为成功
            modifyTradeRefundInfo(record.getId(),resultMap,refundPayTime);

            //step4.获取通知内部系统所需参数
            Map<String,String> internalNotifyParams = this.getInternalSystemNotifyParams("Success".equals(resultMap.get("msg")), record);

            //step5.通知内部系统(订单,充值等)
            Map<String,String> ret = notifyInternalSystem(record.getNotifyUrl(), internalNotifyParams);

            //step6.记录内部系统通知日志
            saveInternalSystemNotifyLog(record.getId(), GsonUtil.toJson(internalNotifyParams),GsonUtil.toJson(ret));

            //step7.更新内部系统通知结果
            modifyInternalNotifyResult(record.getId(),isSuccess ? PayTradeRefundRecord.NOTIFY_RESULT_SUCCESS:PayTradeRefundRecord.NOTIFY_RESULT_FAILURE);
        }
    }

    private boolean resolveResult(Map result, AlipayTradeFastpayRefundQueryModel params) {
        Map resultMap = (Map) result.get("alipay_trade_fastpay_refund_query_response");
        Boolean isSuccess = null;
        try {
            isSuccess = Boolean.FALSE;
            if (resultMap != null && Objects.equals(resultMap.get("code").toString(),"10000")){
                //接口请求成功 =>{}
                if (Objects.nonNull(resultMap.get("code"))){
                    //1.退款成功能
                    isSuccess = Boolean.TRUE;
                }else{
                    //退款失败,重复调用
                    for (int i = 1;i <= 3;i++){
                        Thread.sleep(2000L);
                        resultMap = this.getResponseResult(params);
                        if (resultMap != null && Objects.equals(resultMap.get("code").toString(),"10000")){
                            if (Objects.nonNull(resultMap.get("trade_no"))){
                                //成功
                                isSuccess = Boolean.TRUE;
                                break;
                            }
                        }
                        continue;
                    }
                    //查询失败操作
                   return isSuccess;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    private Map getResponseResult(AlipayTradeFastpayRefundQueryModel params) {
        //调用阿里查询退款接口
        String jsonResult = this.requestAliRefundQuery(params);
        //解析返回结果
        Map resultMap = GsonUtil.fromJson(jsonResult, Map.class);
        resultMap.put("jsonResult",jsonResult);
        return resultMap;
    }

    private void modifyInternalNotifyResult(Long tradeRefundId, Integer notifyResult) {
        logger.info("更新内部系统通知结果:"+tradeRefundId);
        PayTradeRefundRecord payTradeRefundRecord = new PayTradeRefundRecord();
        payTradeRefundRecord.setId(tradeRefundId);
        payTradeRefundRecord.setNotifyResult(notifyResult);
        payTradeRefundRecord.setGmtModified(new Date());
        payTradeRefundRecordService.modify(payTradeRefundRecord);
    }

    private void saveInternalSystemNotifyLog(Long tradeRefundId, String request, String response) {
        logger.info("记录内部系统通知日志:"+tradeRefundId);
        payTradeRefundRecordLogService.saveInternalSystemNotifyLog(tradeRefundId, request,response);
    }

    private Map<String, String> notifyInternalSystem(String notifyUrl, Map<String, String> notifyParams) {
        logger.info("开始通知内部系统:"+notifyUrl);
        return HttpClientUtil.doPost(notifyUrl,notifyParams);
    }


    private Map<String, String> getInternalSystemNotifyParams(boolean msg, PayTradeRefundRecord record) {
        Map<String,String> notifyParams = new HashMap<String, String>();
        if (msg){
            notifyParams.put("resultCode", "SUCCESS");
            notifyParams.put("resultMsg", "OK");
        }else{
            notifyParams.put("resultCode", "FAIL");
            notifyParams.put("resultMsg", "FAIL");
        }
        notifyParams.put("intenalTradeNo", record.getOriginInternalTradeNo());
        notifyParams.put("internalRefundNo", record.getInternalRefundNo());
        notifyParams.put("payTradeNo", record.getOriginPayTradeNo());
        notifyParams.put("payRefundNo", record.getPayRefundNo());
        notifyParams.put("refundFee", record.getRefundFee().toString());
        notifyParams.put("refundDesc", record.getRefundDesc());
        notifyParams.put("refundStatus", record.getStatus() + "");
        notifyParams.put("successTime", record.getRefundSuccessTime());
        notifyParams.put("operator", record.getOperator().toString());
        notifyParams.put("source", record.getSource().toString());
        notifyParams.put("platformCode", record.getPlatformCode());
        return notifyParams;
    }

    public void modifyTradeRefundInfo(Long tradeRefundId, Map params, Object refundPayTime) {
        Map resultMap = (Map) params.get("alipay_trade_fastpay_refund_query_response");
        String resultCode = (String) resultMap.get("msg");
        PayTradeRefundRecord payTradeRefundRecord = new PayTradeRefundRecord();
        payTradeRefundRecord.setId(tradeRefundId);
        payTradeRefundRecord.setExternalRefundNo((String)resultMap.get("out_request_no"));
        payTradeRefundRecord.setRefundStatus(resultCode);
        payTradeRefundRecord.setStatus("Success".equals(resultCode)?PayTradeRefundRecord.STATUS_SUCCESS:PayTradeRefundRecord.STATUS_FAILURE);
        payTradeRefundRecord.setGmtModified(new Date());
        payTradeRefundRecord.setRefundSuccessTime((String) refundPayTime);
        payTradeRefundRecordService.modify(payTradeRefundRecord);
    }

    private String requestAliRefundQuery(AlipayTradeFastpayRefundQueryModel params) {
        try {
            String jsonResult = AliPayApi.tradeRefundQuery(params);
            logger.info("调用退款查询接口返回结果 =>{}" + jsonResult);
            return jsonResult;
        } catch (AlipayApiException e) {
            logger.error("调用退款查询接口异常 =>{}",e);
            throw new BizException("调用退款查询接口异常 =>{}"+e.getMessage());
        }
    }

    private AlipayTradeFastpayRefundQueryModel buildQueryRefundParams(String payTradeNo) {
        //PayTradeRefundRecord record = this.getTradeRefundRecord(payTradeNo);
        //构建Ali公共参数
        buildAliPayConfig();
        //构建查询几口对象
        return buildQueryObject(payTradeNo);

    }

    private AlipayTradeFastpayRefundQueryModel buildQueryObject(String payTradeNo) {
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setOutRequestNo(payTradeNo);
        model.setOutTradeNo(payTradeNo);
        return model;
    }

    private void buildAliPayConfig() {
        AliPayApiConfig aliPayApiConfig = AliPayApiConfig.New()
                .setAppId(GatewayPayConfig.ALI_APP_ID)
                .setAlipayPublicKey(GatewayPayConfig.ALI_PUBLIC_KEY)
                .setCharset("UTF-8")
                .setPrivateKey(GatewayPayConfig.ALI_PRIVATE_KEY)
                .setServiceUrl(GatewayPayConfig.ALI_SERVICE_URL)
                .setSignType("RSA2")
                .build();
        AliPayApiConfigKit.putApiConfig(aliPayApiConfig);
        AliPayApiConfigKit.setThreadLocalAppId(GatewayPayConfig.ALI_APP_ID);
    }

    public void saveExternalNotifyBody(Long tradeRefundId, String notifyBody) {
        logger.info("记录外部系统(微信,支付宝)通知日志:"+notifyBody);
        payTradeRefundRecordLogService.saveExternalNotifyBody(tradeRefundId,notifyBody);
    }

    private PayTradeRefundRecord getTradeRefundRecord(String payTradeNo) {
        if (StringUtils.isEmpty(payTradeNo)){
            throw new BizException("交易不存在");
        }

        PayTradeRefundQueryVo vo = new PayTradeRefundQueryVo();
        vo.setOriginPayTradeNo(payTradeNo);
        List<PayTradeRefundRecord> list = payTradeRefundRecordService.getList(vo);

        if(CollectionUtils.isEmpty(list)){
            throw new BizException("交易不存在");
        }
        return list.get(0);
    }

}
