package com.sandu.gateway.pay.forward.service.base.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sandu.gateway.pay.common.exception.BizException;
import com.sandu.gateway.pay.common.exception.ExceptionCodes;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.gateway.pay.forward.service.PayService;
import com.sandu.gateway.pay.input.PayParam;
import com.sandu.gateway.pay.input.PayTradeQueryVo;
import com.sandu.gateway.pay.input.RefundParam;
import com.sandu.gateway.pay.input.TransfersParam;
import com.sandu.gateway.pay.trade.model.PayTradeRecord;
import com.sandu.gateway.pay.trade.model.PayTradeRecordLog;
import com.sandu.gateway.pay.trade.model.PayTradeRefundRecord;
import com.sandu.gateway.pay.trade.model.PayTradeRefundRecordLog;
import com.sandu.gateway.pay.trade.model.PayTradeTransfersRecord;
import com.sandu.gateway.pay.trade.model.PayTradeTransfersRecordLog;
import com.sandu.gateway.pay.trade.service.PayTradeRecordLogService;
import com.sandu.gateway.pay.trade.service.PayTradeRecordService;
import com.sandu.gateway.pay.trade.service.PayTradeRefundRecordLogService;
import com.sandu.gateway.pay.trade.service.PayTradeRefundRecordService;
import com.sandu.gateway.pay.trade.service.PayTradeTransfersRecordLogService;
import com.sandu.gateway.pay.trade.service.PayTradeTransfersRecordService;

public abstract class BasePayServiceImpl implements PayService {
    private static Logger logger = LogManager.getLogger(BasePayServiceImpl.class);
    
    @Resource
    private PayTradeRecordService payTradeRecordService ;
    
    @Resource
    private PayTradeRecordLogService payTradeRecordLogService ;
    
    @Resource
    private PayTradeRefundRecordService payTradeRefundRecordService ;
    
    @Resource
    private PayTradeRefundRecordLogService payTradeRefundRecordLogService ;
    
    @Resource
    private PayTradeTransfersRecordService payTradeTransfersRecordService;
    
    @Resource
    private PayTradeTransfersRecordLogService payTradeTransfersRecordLogService;
    
    private DateFormat df=new SimpleDateFormat("yyyyMMddHHmmssSSS");
    
    public String doPay(PayParam payParam){
    	//step1.参数验证
    	validateParameter(payParam);
  
        //step2.构建接口参数
    	String payTradeNo = getPayTradeNo(payParam.getSource().toString(),payParam.getOperator());
    	payParam.setPayTradeNo(payTradeNo);
    	Object params = this.buildPayParameter(payParam);
		logger.info("公共回传参数 =>{}"+payParam.getPassbackParams());
    	logger.info("支付参数 =>{}"+params.toString());

        //step3.记录交易流水
        PayTradeRecord tradeRecord = addTradeRecord(payParam);
        
        //step4.调用第三方支付接口:微信是xml,支付宝是json
        String xmlOrJsonResult = this.executePay(params);
        
        //step5.保存支付返回结果
        saveExcuteLog(tradeRecord,GsonUtil.toJson(params),xmlOrJsonResult);
        
        //step6.封装执行结果返回给调用方
        String jsonResult = packageResult(payTradeNo,xmlOrJsonResult);
        
        return jsonResult;
    }

    private void validateParameter(PayParam payParam) {
     	if(StringUtils.isBlank(payParam.getIntenalTradeNo())) {
       		throw new BizException(ExceptionCodes.CODE_20010001,"交易号不能为空");
       	}
       	if(StringUtils.isBlank(payParam.getTradeDesc())) {
       		throw new BizException(ExceptionCodes.CODE_20010002,"交易详情不能为空");
       	}
       	if(payParam.getTotalFee()==null) {
       		throw new BizException(ExceptionCodes.CODE_20010003,"交易金额不能为空");
       	}else if(payParam.getTotalFee()<=0){
       		throw new BizException(ExceptionCodes.CODE_20010004,"交易金额必须大于0");
       	}
       	if(StringUtils.isBlank(payParam.getPayMethod())) {
       		throw new BizException(ExceptionCodes.CODE_20010005,"支付方式不能为空");
       	}
       	if(StringUtils.isBlank(payParam.getIp())) {
       		throw new BizException(ExceptionCodes.CODE_20010006,"ip地址不能为空");
       	}
       	if(StringUtils.isBlank(payParam.getNotifyUrl())) {
       		throw new BizException(ExceptionCodes.CODE_20010007,"回调地址不能为空");
       	}
       	if(StringUtils.isBlank(payParam.getPlatformCode())) {
       		throw new BizException(ExceptionCodes.CODE_20010008,"平台id不能为空");
       	}
       	if(payParam.getSource()==null) {
       		throw new BizException(ExceptionCodes.CODE_20010009,"交易来源不能为空");
       	}
    }
    
    protected abstract Integer getPayMethod();
    
    private String getPayTradeNo(String source,Long userId) {
    	String nowStr = df.format(new Date());
    	return source+nowStr+(10000000000L-userId);
    }
    
    private String getRefundTradeNo(String source,Long userId) {
    	String nowStr = df.format(new Date());
    	return "RF"+source+nowStr+(10000000000L-userId);
    }
    
    private PayTradeRecord addTradeRecord(PayParam payParam) {
    	Date now = new Date();
    	PayTradeRecord trade = new PayTradeRecord();
    	trade.setIntenalTradeNo(payParam.getIntenalTradeNo());
    	trade.setPayTradeNo(payParam.getPayTradeNo());
    	trade.setTradeDesc(payParam.getTradeDesc());
    	trade.setTotalFee(payParam.getTotalFee());
    	trade.setTradeDate(now);
    	trade.setTradeType(PayTradeRecord.TRADE_TYPE_PAY);
    	trade.setPayMethod(getPayMethod()); 
    	trade.setNotifyUrl(payParam.getNotifyUrl());
    	trade.setSign(payParam.getSign());
    	trade.setStatus(PayTradeRecord.STATUS_BEGIN);
    	trade.setPlatformCode(payParam.getPlatformCode());
    	trade.setSource(payParam.getSource());
    	trade.setOperator(payParam.getOperator());
    	trade.setCreator(payParam.getOperator().toString());
    	trade.setGmtCreate(now);
    	trade.setModifier(payParam.getOperator().toString());
    	trade.setGmtModified(now);
    	trade.setIsDeleted(0);
    	payTradeRecordService.addPayTradeRecord(trade);
    	return trade;
    }
    
    protected abstract Object buildPayParameter(PayParam payParam);

    protected abstract String executePay(Object params);

    private void saveExcuteLog(PayTradeRecord tradeRecord, String request, String response) {
    	Date now = new Date();
    	PayTradeRecordLog log = new PayTradeRecordLog();
    	log.setTradeId(tradeRecord.getId());
    	log.setExternalRequest(request);
    	log.setExternalResponse(response);
    	log.setOperator(tradeRecord.getOperator());
    	log.setCreator(tradeRecord.getCreator());
    	log.setGmtCreate(now);
    	log.setModifier(tradeRecord.getModifier());
    	log.setGmtModified(now);
    	log.setIsDeleted(0);
    	payTradeRecordLogService.addPayTradeRecordLog(log);
    }
    
    protected abstract String packageResult(String payTradeNo,String xmlResult);

    protected abstract String updateAndPackageRefundResult(PayTradeRefundRecord tradeRefundRecord,String xmlResult);
    
    public String doRefund(RefundParam refundParam){
    	//step1.参数验证
    	validateParameter(refundParam);
    	
    	//生成退款交易号
    	String refundNo = this.getRefundTradeNo(refundParam.getSource().toString(), refundParam.getOperator());
       
    	//step2.构建接口参数
    	refundParam.setPayRefundNo(refundNo);
    	Object params = this.buildRefundParameter(refundParam);
    	
        //step3.记录交易流水
    	PayTradeRefundRecord tradeRefundRecord = addTradeRefundRecord(refundParam);
        
        //step4.调用第三方支付接口:微信是xml,支付宝是json
        String xmlOrJsonResult = this.executeRefund(params);
        
        //step5.保存支付返回结果
        saveExcuteRefundLog(tradeRefundRecord,GsonUtil.toJson(params),xmlOrJsonResult);
       
        //step6.更新返回信息及封装执行结果返回给调用方
        String jsonResult =  updateAndPackageRefundResult(tradeRefundRecord,xmlOrJsonResult);
        
        return jsonResult;
    }
    
    protected abstract Object buildRefundParameter(RefundParam refundParam);
    
    
    
    private void validateParameter(RefundParam refundParam) {
     	if(StringUtils.isBlank(refundParam.getOriginInternalTradeNo())
     			&& StringUtils.isBlank(refundParam.getOriginPayTradeNo())) {
       		throw new BizException(ExceptionCodes.CODE_20010001,"原交易号或原支付号必须二选一");
       	}
     	
     	if(StringUtils.isBlank(refundParam.getInternalRefundNo())) {
       		throw new BizException(ExceptionCodes.CODE_20010005,"退款交易号不能为空");
       	}
    	if(refundParam.getRefundFee()==null) {
       		throw new BizException(ExceptionCodes.CODE_20010003,"退款金额不能为空");
       	}else if(refundParam.getRefundFee()<=0){
			throw new BizException(ExceptionCodes.CODE_20010004,"退款金额必须大于0");
		}
     	
       	if(StringUtils.isBlank(refundParam.getIp())) {
       		throw new BizException(ExceptionCodes.CODE_20010006,"ip地址不能为空");
       	}
       	if(StringUtils.isBlank(refundParam.getNotifyUrl())) {
       		throw new BizException(ExceptionCodes.CODE_20010007,"回调地址不能为空");
       	}
       	if(StringUtils.isBlank(refundParam.getPlatformCode())) {
       		throw new BizException(ExceptionCodes.CODE_20010008,"平台id不能为空");
       	}
       	if(refundParam.getSource()==null) {
       		throw new BizException(ExceptionCodes.CODE_20010009,"交易来源不能为空");
       	}
    }
    
    private PayTradeRefundRecord addTradeRefundRecord(RefundParam refundParam) {
    	Date now = new Date();
    	PayTradeRefundRecord tradeRefund = new PayTradeRefundRecord();
    	PayTradeRecord originTrade = this.getPayTradeRecord(refundParam);
    	if(originTrade==null) {
    		throw new BizException("原交易不存在!");
    	}
    	tradeRefund.setOriginPayTradeNo(originTrade.getPayTradeNo());
    	tradeRefund.setOriginInternalTradeNo(originTrade.getIntenalTradeNo());
    	tradeRefund.setOriginExternalTradeNo(originTrade.getExtenalTradeNo());
    	tradeRefund.setOriginTradeDate(originTrade.getTradeDate());
    	tradeRefund.setOriginTradeDesc(originTrade.getTradeDesc());
    	tradeRefund.setOriginTotalFee(originTrade.getTotalFee());
    	tradeRefund.setInternalRefundNo(refundParam.getInternalRefundNo());
    	tradeRefund.setPayRefundNo(refundParam.getPayRefundNo());
    	//tradeRefund.setExternalRefundNo();
    	if(refundParam.getTotalFee().longValue()!=originTrade.getTotalFee().longValue()) {
    		throw new BizException("原交易金额不正确!");
    	}
    	tradeRefund.setRefundFee(refundParam.getRefundFee());
    	tradeRefund.setRefundDesc(refundParam.getRefundDesc());
    	tradeRefund.setNotifyUrl(refundParam.getNotifyUrl());
    	tradeRefund.setSign(refundParam.getSign());
    	tradeRefund.setStatus(PayTradeRecord.STATUS_BEGIN);
    	tradeRefund.setPlatformCode(refundParam.getPlatformCode());
    	tradeRefund.setSource(refundParam.getSource());
    	tradeRefund.setOperator(refundParam.getOperator());
    	tradeRefund.setCreator(refundParam.getOperator().toString());
    	tradeRefund.setGmtCreate(now);
    	tradeRefund.setModifier(refundParam.getOperator().toString());
    	tradeRefund.setGmtModified(now);
    	tradeRefund.setIsDeleted(0);
    	payTradeRefundRecordService.add(tradeRefund);
    	return tradeRefund;
    }
    
    private PayTradeRecord getPayTradeRecord(RefundParam refundParam) {
    	PayTradeQueryVo queryVo = new PayTradeQueryVo();
    	if(StringUtils.isNotBlank(refundParam.getOriginInternalTradeNo())) {
    		queryVo.setIntenalTradeNo(refundParam.getOriginInternalTradeNo());
    	}else if(StringUtils.isNotBlank(refundParam.getOriginPayTradeNo())) {
    		queryVo.setPayTradeNo(refundParam.getOriginPayTradeNo());
    	}else {
    		return null;
    	}
    	
    	List<PayTradeRecord> list = payTradeRecordService.getList(queryVo);
    	
    	if(list!=null && list.size()>0) {
    		return list.get(0);
    	}
    	return null;
    }
    
    protected abstract String executeRefund(Object params);
    
    private void saveExcuteRefundLog(PayTradeRefundRecord tradeRefundRecord, String request, String response) {
    	Date now = new Date();
    	PayTradeRefundRecordLog log = new PayTradeRefundRecordLog();
    	log.setTradeRefundId(tradeRefundRecord.getId());
    	log.setExternalRequest(request);
    	log.setExternalResponse(response);
    	log.setOperator(tradeRefundRecord.getOperator());
    	log.setCreator(tradeRefundRecord.getCreator());
    	log.setGmtCreate(now);
    	log.setModifier(tradeRefundRecord.getModifier());
    	log.setGmtModified(now);
    	log.setIsDeleted(0);
    	payTradeRefundRecordLogService.add(log);
    }
  
    
    public String doTransfers(TransfersParam transfersParam){
    	//step1.参数验证
    	validateParameter(transfersParam);
  
        //step2.构建接口参数
    	String payTradeNo = getPayTradeNo(transfersParam.getSource().toString(),transfersParam.getOperator());
    	transfersParam.setPayTradeNo(payTradeNo);
    	Object params = this.buildTransfersParameter(transfersParam);
    	logger.info("支付参数 =>{}"+params);

        //step3.记录交易流水
        PayTradeTransfersRecord tradeTransfersRecord = addTradeTransfersRecord(transfersParam);
        
        //step4.调用第三方支付接口:微信是xml,支付宝是json
        String xmlOrJsonResult = this.executeTransfers(params);
        
        //step5.保存支付返回结果
        saveExcuteLog(tradeTransfersRecord,GsonUtil.toJson(params),xmlOrJsonResult);
        
        //step6.封装执行结果返回给调用方
        String jsonResult = updateAndPackageTransfersResult(tradeTransfersRecord,xmlOrJsonResult);
        
        return jsonResult;
    }
    
    protected abstract String updateAndPackageTransfersResult(PayTradeTransfersRecord tradeTransfersRecord, String xmlOrJsonResult);

	private void saveExcuteLog(PayTradeTransfersRecord transfersRecord, String request, String response) {
    	Date now = new Date();
    	PayTradeTransfersRecordLog log = new PayTradeTransfersRecordLog();
    	log.setTradeTransfersId(transfersRecord.getId());
    	log.setExternalRequest(request);
    	log.setExternalResponse(response);
    	log.setOperator(transfersRecord.getOperator());
    	log.setCreator(transfersRecord.getCreator());
    	log.setGmtCreate(now);
    	log.setModifier(transfersRecord.getModifier());
    	log.setGmtModified(now);
    	log.setIsDeleted(0);
    	payTradeTransfersRecordLogService.add(log);
    }
    
    protected abstract String executeTransfers(Object params);

	private PayTradeTransfersRecord addTradeTransfersRecord(TransfersParam transfersParam) {
    	Date now = new Date();
    	PayTradeTransfersRecord tansfersRecord = new PayTradeTransfersRecord();
    	tansfersRecord.setPayTradeNo(transfersParam.getPayTradeNo());
    	tansfersRecord.setIntenalTradeNo(transfersParam.getIntenalTradeNo());
    	//tansfersRecord.setExtenalTradeNo();
    	tansfersRecord.setPayMethod(PayTradeTransfersRecord.PAY_METHOD_WX_MINIPRO);
    	tansfersRecord.setAmount(transfersParam.getAmount());
    	tansfersRecord.setTradeDate(now);
    	tansfersRecord.setTradeDesc(transfersParam.getTradeDesc());
    	tansfersRecord.setOpenId(transfersParam.getOpenId());
    	tansfersRecord.setSign(transfersParam.getSign());
    	tansfersRecord.setStatus(PayTradeTransfersRecord.STATUS_BEGIN);
    	//tansfersRecord.setPaymentTime(paymentTime);
    	tansfersRecord.setIp(transfersParam.getIp());
    	tansfersRecord.setPlatformCode(transfersParam.getPlatformCode());
    	tansfersRecord.setSource(transfersParam.getSource());
    	tansfersRecord.setOperator(transfersParam.getOperator());
    	//tansfersRecord.setSysCode(transfersParam.gets);
    	tansfersRecord.setCreator(transfersParam.getOperator().toString());
    	tansfersRecord.setGmtCreate(now);
    	tansfersRecord.setModifier(transfersParam.getOperator().toString());
    	tansfersRecord.setGmtModified(now);
    	tansfersRecord.setIsDeleted(0);
    	//tansfersRecord.setRemark(remark);
    	payTradeTransfersRecordService.add(tansfersRecord);
		return tansfersRecord;
	}

	protected abstract Object buildTransfersParameter(TransfersParam transfersParam);
    
	private void validateParameter(TransfersParam transfersParam) {
     	if(StringUtils.isBlank(transfersParam.getIntenalTradeNo())) {
       		throw new BizException("交易号不能为空");
       	}
       	
       	if(StringUtils.isBlank(transfersParam.getOpenId())) {
       		throw new BizException("收款方openId不能为空");
       	}
     	if(transfersParam.getAmount()==null) {
       		throw new BizException("付款金额不能为空");
       	}
     	if(StringUtils.isBlank(transfersParam.getTradeDesc())) {
       		throw new BizException("企业付款描述");
       	}
     	if(transfersParam.getOperator()==null) {
       		throw new BizException("操作人不能为空");
       	}
    	if(StringUtils.isBlank(transfersParam.getIp())) {
       		throw new BizException("ip地址不能为空");
       	}
       	if(StringUtils.isBlank(transfersParam.getPlatformCode())) {
       		throw new BizException("平台id不能为空");
       	}
       	if(transfersParam.getSource()==null) {
       		throw new BizException("交易来源不能为空");
       	}
      
	}
    
}
