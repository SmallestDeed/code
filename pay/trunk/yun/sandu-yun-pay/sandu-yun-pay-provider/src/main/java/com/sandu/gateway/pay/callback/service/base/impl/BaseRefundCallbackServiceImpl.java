package com.sandu.gateway.pay.callback.service.base.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sandu.gateway.pay.callback.service.RefundCallbackService;
import com.sandu.gateway.pay.common.exception.BizException;
import com.sandu.gateway.pay.common.exception.ExceptionCodes;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.gateway.pay.common.http.HttpClientUtil;
import com.sandu.gateway.pay.input.PayTradeRefundQueryVo;
import com.sandu.gateway.pay.trade.model.PayTradeRefundRecord;
import com.sandu.gateway.pay.trade.service.PayTradeRefundRecordLogService;
import com.sandu.gateway.pay.trade.service.PayTradeRefundRecordService;

public abstract class BaseRefundCallbackServiceImpl implements RefundCallbackService{
	
	private static Logger logger = LogManager.getLogger(BaseRefundCallbackServiceImpl.class);
	
	@Resource
    private PayTradeRefundRecordService payTradeRefundRecordService ;
    
    @Resource
    private PayTradeRefundRecordLogService payTradeRefundRecordLogService ;
    
    @Override
	public boolean callback(Object notifyBody) {
    	boolean success = false;
    	//step0.验证签名
    	//boolean signFlag = verifySignature(notifyBody);
    	boolean signFlag = true;
    	if(signFlag) {
			Map<String,String> params = getNotifyParams(notifyBody);
    		String resultCode = params.get("refundStatus");
    		String payRefundNo = params.get("payRefundNo");
        	if(lockRecord(payRefundNo)) {
        		PayTradeRefundRecord tradeRefund = getTradeRefundRecord(payRefundNo);
        		
        		//step2.记录外部系统(微信,支付宝)通知日志
    			saveExternalNotifyBody(tradeRefund.getId(), (notifyBody instanceof String)?(String)notifyBody:GsonUtil.toJson(notifyBody));
    			
    			//step3.保存外部系统(微信,支付宝)退款号及更新交易状态为成功
    			modifyTradeRefundInfo(tradeRefund.getId(),params);

    			//step4.获取通知内部系统所需参数
    			Map<String,String> internalNotifyParams = this.getInternalSystemNotifyParams("SUCCESS".equals(resultCode), tradeRefund,params);
    			
    			//step5.通知内部系统(订单,充值等)
    			Map<String,String> ret = notifyInternalSystem(tradeRefund.getNotifyUrl(), internalNotifyParams);
    			
    			//step6.记录内部系统通知日志
    			saveInternalSystemNotifyLog(tradeRefund.getId(), GsonUtil.toJson(internalNotifyParams),GsonUtil.toJson(ret));
        		
    			//step7.更新内部系统通知结果
    			boolean internalSuccess = getInternalSystemNotifyResult(ret);
    			modifyInternalNotifyResult(tradeRefund.getId(),internalSuccess?PayTradeRefundRecord.NOTIFY_RESULT_SUCCESS:PayTradeRefundRecord.NOTIFY_RESULT_FAILURE);
    			success = true;
    		}else {
    			logger.error("记录已锁定:"+payRefundNo);
    			throw new BizException("记录已锁定");
    		}
    	}else {
    		logger.error("签名错误:",(notifyBody instanceof String)?(String)notifyBody:GsonUtil.toJson(notifyBody)); 
			throw new BizException(ExceptionCodes.CODE_20010012, "签名错误");
		}
    	return success;
    }
    
    private PayTradeRefundRecord getTradeRefundRecord(String payRefundNo) {
    	if(StringUtils.isBlank(payRefundNo)) {
    		throw new BizException("交易不存在");
    	}
    	PayTradeRefundQueryVo queryVo = new PayTradeRefundQueryVo();
    	queryVo.setPayRefundNo(payRefundNo);
    	List<PayTradeRefundRecord> list = payTradeRefundRecordService.getList(queryVo);
    	if(list!=null && list.size()>0) {
    		return list.get(0);
    	}
    	throw new BizException("交易不存在");
    }
    
    private boolean lockRecord(String payRefundNo) {
    	int count = payTradeRefundRecordService.changeToProcessStatus(payRefundNo);
		return count>0?true:false;
	}

	private boolean getInternalSystemNotifyResult(Map<String,String> ret) {
    	String code = ret.get("resultCode");
		boolean internalSuccess = false;
		if("200".equals(code)) {
			String resultData = ret.get("resultData");
			if("SUCCESS".equalsIgnoreCase(resultData)) {
				internalSuccess = true;
			}
		}
		return internalSuccess;
    }
	 
	
    
    private Map<String,String> getInternalSystemNotifyParams(boolean success, PayTradeRefundRecord tradeRefund,Map<String,String> params) {
    	Map<String,String> notifyParams = new HashMap<String, String>();
    	if (success) {
			success = true;
			notifyParams.put("resultCode", "SUCCESS");
			notifyParams.put("resultMsg", "OK");
		}else {
			notifyParams.put("resultCode", "FAIL");
			notifyParams.put("resultMsg", "FAIL");
		}
    	String refundStatus = params.get("refundStatus");
		String successTime = params.get("successTime");
    	notifyParams.put("intenalTradeNo", tradeRefund.getOriginInternalTradeNo()); 
    	notifyParams.put("internalRefundNo", tradeRefund.getInternalRefundNo());
    	notifyParams.put("payTradeNo", tradeRefund.getOriginPayTradeNo());
    	notifyParams.put("payRefundNo", tradeRefund.getPayRefundNo());
		notifyParams.put("refundFee", tradeRefund.getRefundFee().toString());
		notifyParams.put("refundDesc", tradeRefund.getRefundDesc());
		notifyParams.put("refundStatus", refundStatus);
		notifyParams.put("successTime", successTime);
		notifyParams.put("operator", tradeRefund.getOperator().toString());
		notifyParams.put("source", tradeRefund.getSource().toString());
		notifyParams.put("platformCode", tradeRefund.getPlatformCode());
    	return notifyParams;
    }

    
    public abstract Map<String, String> getNotifyParams(Object notifyBody);

	public abstract boolean verifySignature(Object notifyBody);
    
	protected Map<String,String> notifyInternalSystem(String notifyUrl,Map<String,String> notifyParams) {
		logger.info("开始通知内部系统:"+notifyUrl);
		return HttpClientUtil.doPost(notifyUrl,notifyParams);
	}
	
	public void saveExternalNotifyBody(Long tradeRefundId, String notifyBody) {
		logger.info("记录外部系统(微信,支付宝)通知日志:"+notifyBody);
		payTradeRefundRecordLogService.saveExternalNotifyBody(tradeRefundId,notifyBody);
	}
	
	public void modifyTradeRefundInfo(Long tradeRefundId, Map<String,String> params) {
		String resultCode = params.get("refundStatus");
		PayTradeRefundRecord payTradeRefundRecord = new PayTradeRefundRecord();
		payTradeRefundRecord.setId(tradeRefundId);
		payTradeRefundRecord.setExternalRefundNo(params.get("externalRefundNo"));
		payTradeRefundRecord.setRefundStatus(params.get("refundStatus"));
		payTradeRefundRecord.setRefundSuccessTime(params.get("successTime"));
		payTradeRefundRecord.setStatus("SUCCESS".equals(resultCode)?PayTradeRefundRecord.STATUS_SUCCESS:PayTradeRefundRecord.STATUS_FAILURE);
		payTradeRefundRecord.setGmtModified(new Date());
		payTradeRefundRecordService.modify(payTradeRefundRecord);
	}
	
	public void saveInternalSystemNotifyLog(Long tradeRefundId, String request, String response) {
		logger.info("记录内部系统通知日志:"+tradeRefundId);
		payTradeRefundRecordLogService.saveInternalSystemNotifyLog(tradeRefundId, request,response);
	}
	
	public void modifyInternalNotifyResult(Long tradeRefundId, Integer notifyResult) {
		logger.info("更新内部系统通知结果:"+tradeRefundId);
		PayTradeRefundRecord payTradeRefundRecord = new PayTradeRefundRecord();
		payTradeRefundRecord.setId(tradeRefundId);
		payTradeRefundRecord.setNotifyResult(notifyResult);
		payTradeRefundRecord.setGmtModified(new Date());
		payTradeRefundRecordService.modify(payTradeRefundRecord);
	}
	
}
