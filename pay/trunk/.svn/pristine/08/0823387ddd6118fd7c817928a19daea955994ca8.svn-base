package com.sandu.gateway.pay.callback.service.base.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sandu.gateway.pay.callback.service.PayCallbackService;
import com.sandu.gateway.pay.common.exception.BizException;
import com.sandu.gateway.pay.common.exception.ExceptionCodes;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.gateway.pay.common.http.HttpClientUtil;
import com.sandu.gateway.pay.config.service.BaseCompanyMiniProgramConfigService;
import com.sandu.gateway.pay.trade.model.PayTradeRecord;
import com.sandu.gateway.pay.trade.service.PayTradeRecordLogService;
import com.sandu.gateway.pay.trade.service.PayTradeRecordService;

public abstract class BasePayCallbackServiceImpl implements PayCallbackService{
	
	private static Logger logger = LogManager.getLogger(BasePayCallbackServiceImpl.class);
	
	@Resource
    private PayTradeRecordService payTradeRecordService ;
    
    @Resource
    private PayTradeRecordLogService payTradeRecordLogService ;
    
    @Autowired
    private BaseCompanyMiniProgramConfigService miniProgramConfigService;
    
    @Override
	public boolean callback(Object notifyBody) {
    	boolean success = false;
    	//step0.验证签名
    	//boolean signFlag = verifySignature(notifyBody);
    	boolean signFlag = true;
    	if(signFlag) {
			Map<String,String> params = getNotifyParams(notifyBody);
    		String resultCode = params.get("resultCode");
    		String payTradeNo = params.get("payTradeNo");//支付网关交易号
    		String extenalTradeNo = params.get("extenalTradeNo");
    		String totalFee = params.get("totalFee"); //交易金额
			String passbackParams = params.get("passbackParams");//回调调起支付系统所需参数

			logger.info("回调调起支付系统所需参数 =>{}"+passbackParams);

    		//step1.验证交易金额
    		PayTradeRecord trade = getTrade(payTradeNo,GsonUtil.toJson(params));
    		if(trade.getTotalFee().longValue()!=Long.valueOf(totalFee).longValue()) {
    			throw new BizException(ExceptionCodes.CODE_20010013, "交易金额不正确!");
    		}
        	if(lockRecord(payTradeNo)) {
        		//step2.记录外部系统(微信,支付宝)通知日志
    			saveExternalNotifyBody(trade.getId(), (notifyBody instanceof String)?(String)notifyBody:GsonUtil.toJson(notifyBody));
    			
    			//step3.保存外部系统(微信,支付宝)交易号及更新交易状态为成功
    			modifyExtenalTradeNoAndStatus(trade.getId(),extenalTradeNo,"SUCCESS".equals(resultCode)?PayTradeRecord.STATUS_SUCCESS:PayTradeRecord.STATUS_FAILURE);

    			//step4.获取通知内部系统所需参数
    			Map<String,String> internalNotifyParams = this.getInternalSystemNotifyParams("SUCCESS".equals(resultCode), trade,passbackParams);
    			
    			//step5.通知内部系统(订单,充值等)
    			Map<String,String> ret = notifyInternalSystem(trade.getNotifyUrl(), internalNotifyParams);
    			
    			//step6.记录内部系统通知日志
    			saveInternalSystemNotifyLog(trade.getId(), GsonUtil.toJson(internalNotifyParams),GsonUtil.toJson(ret));
        		
    			//step7.更新内部系统通知结果
    			boolean internalSuccess = getInternalSystemNotifyResult(ret);
    			modifyInternalNotifyResult(trade.getId(),internalSuccess?PayTradeRecord.NOTIFY_RESULT_SUCCESS:PayTradeRecord.NOTIFY_RESULT_FAILURE);
    			success = true;
    		}else {
    			logger.error("记录已锁定:"+payTradeNo);
    			throw new BizException("记录已锁定");
    		}
    	}else {
    		logger.error("签名错误:",(notifyBody instanceof String)?(String)notifyBody:GsonUtil.toJson(notifyBody)); 
			throw new BizException(ExceptionCodes.CODE_20010012, "签名错误");
		}
    	return success;
    }
    
    private boolean lockRecord(String payTradeNo) {
    	int count = payTradeRecordService.changeToProcessStatus(payTradeNo);
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
    
    private Map<String,String> getInternalSystemNotifyParams(boolean success, PayTradeRecord trade, String passbackParams) {
    	Map<String,String> notifyParams = new HashMap<String, String>();
    	if (success) {
			success = true;
			notifyParams.put("resultCode", "SUCCESS");
			notifyParams.put("resultMsg", "OK");
		}else {
			notifyParams.put("resultCode", "FAIL");
			notifyParams.put("resultMsg", "FAIL");
		}
    	notifyParams.put("payTradeNo", trade.getPayTradeNo());
		notifyParams.put("intenalTradeNo", trade.getIntenalTradeNo());
		notifyParams.put("totalFee", trade.getTotalFee().toString());
		notifyParams.put("tradeDesc", trade.getTradeDesc());
		notifyParams.put("operator", trade.getOperator().toString());
		notifyParams.put("source", trade.getSource().toString());
		notifyParams.put("platformCode", trade.getPlatformCode());
		notifyParams.put("passbackParams",passbackParams);
    	return notifyParams;
    }

    
    public abstract Map<String, String> getNotifyParams(Object notifyBody);

	public abstract boolean verifySignature(Object notifyBody);
    
	protected Map<String,String> notifyInternalSystem(String notifyUrl,Map<String,String> notifyParams) {
		logger.info("开始通知内部系统:"+notifyUrl);
		return HttpClientUtil.doPost(notifyUrl,notifyParams);
	}

	public PayTradeRecord getTrade(String payTradeNo,String notifyBody) {
		if(StringUtils.isNotBlank(payTradeNo)) {
			PayTradeRecord trade = payTradeRecordService.getTradeRecord(payTradeNo);
			if(trade == null) {
				logger.error("交易不存在:{}",notifyBody);
				throw new BizException(ExceptionCodes.CODE_20010011, "交易不存在");
			}
			return trade;
		}else {
			logger.error("交易不存在:{}",notifyBody);
			throw new BizException(ExceptionCodes.CODE_20010011, "交易不存在");
		}
		
	}
	
	public void saveExternalNotifyBody(Long tradeId, String notifyBody) {
		logger.info("记录外部系统(微信,支付宝)通知日志:"+notifyBody);
		payTradeRecordLogService.saveExternalNotifyBody(tradeId,notifyBody);
	}
	
	public void modifyExtenalTradeNoAndStatus(Long tradeId, String extenalTradeNo,Integer status) {
		logger.info("保存外部系统(微信,支付宝)交易号及更新交易状态为成功:"+extenalTradeNo);
		payTradeRecordService.modifyExtenalTradeNoAndStatus(tradeId,extenalTradeNo,status);
	}
	
	public void saveInternalSystemNotifyLog(Long tradeId, String request, String response) {
		logger.info("记录内部系统通知日志:"+tradeId);
		payTradeRecordLogService.saveInternalSystemNotifyLog(tradeId, request,response);
	}
	
	public void modifyInternalNotifyResult(Long tradeId, Integer notifyResult) {
		logger.info("更新内部系统通知结果:"+tradeId);
		payTradeRecordService.modifyInternalNotifyResult(tradeId,notifyResult);
	}
	
}
