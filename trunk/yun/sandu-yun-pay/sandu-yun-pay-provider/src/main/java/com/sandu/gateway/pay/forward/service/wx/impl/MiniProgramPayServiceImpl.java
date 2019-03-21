package com.sandu.gateway.pay.forward.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpay.ext.kit.PaymentKit;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.sandu.config.GatewayPayConfig;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.gateway.pay.config.model.BaseCompanyMiniProgramConfig;
import com.sandu.gateway.pay.config.service.BaseCompanyMiniProgramConfigService;
import com.sandu.gateway.pay.input.PayParam;
import com.sandu.gateway.pay.input.PayTradeQueryVo;
import com.sandu.gateway.pay.input.RefundParam;
import com.sandu.gateway.pay.input.TransfersParam;
import com.sandu.gateway.pay.trade.model.PayTradeRecord;
import com.sandu.gateway.pay.trade.model.PayTradeRefundRecord;
import com.sandu.gateway.pay.trade.model.PayTradeTransfersRecord;
import com.sandu.gateway.pay.trade.service.PayTradeRecordService;
import com.sandu.gateway.pay.trade.service.PayTradeRefundRecordService;
import com.sandu.gateway.pay.trade.service.PayTradeTransfersRecordService;
import com.sandu.pay.common.exception.BizException;
import com.sandu.pay.common.exception.ExceptionCodes;
import com.sandu.pay.wexin.common.RandomStringGenerator;
import com.sandu.pay.wexin.common.WxConfigure;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;

@Service("miniPayService")
public class MiniProgramPayServiceImpl extends WxPayServiceImpl {

	private static final ThreadLocal<String> TL = new ThreadLocal<String>();
	private static final Map<String, WxPayApiConfig> CFG_MAP = new ConcurrentHashMap<String, WxPayApiConfig>();
    private Logger logger = LoggerFactory.getLogger(MiniProgramPayServiceImpl.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private BaseCompanyMiniProgramConfigService miniProgramConfigService;
    
    @Resource
    private PayTradeRecordService payTradeRecordService ;

    @Resource
    private PayTradeRefundRecordService payTradeRefundRecordService ;
    
    @Resource
    private PayTradeTransfersRecordService payTradeTransfersRecordService;
    
	@Override
	protected Integer getPayMethod() {
		return PayTradeRecord.PAY_METHOD_WX_MINIPRO;
	}

	@Override
	protected Map<String, String> buildPayParameter(PayParam payParam) {
	    SysUser sysUser = sysUserService.get(payParam.getOperator().intValue());
		if(sysUser==null) {
			logger.error("userId:{}",payParam.getOperator());
			throw new BizException("用户不存在!");
		}
        BaseCompanyMiniProgramConfig miniConfig = miniProgramConfigService.getMiniProgramConfig(sysUser.getAppId());
        if(miniConfig==null) {
        	logger.error("appId:{}",sysUser.getAppId());
			throw new BizException("未找到小程序相关配置!");
		}
		WxPayApiConfig apiConfig = WxPayApiConfig.New();
		Map<String, String> params = apiConfig
				.setBody(payParam.getTradeDesc())
				.setOutTradeNo(payParam.getPayTradeNo())
				.setTotalFee(payParam.getTotalFee().toString())
                .setMchId(miniConfig.getMchId())
                .setOpenId(sysUser.getOpenId())
                .setPaternerKey(miniConfig.getMchKey())
                .setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL)
                .setNonceStr(RandomStringGenerator.getRandomStringByLength(32))
                .setSpbillCreateIp(WxConfigure.getIP())
                .setTradeType(WxPayApi.TradeType.JSAPI)
                .setAppId(miniConfig.getAppId())
                .setNotifyUrl(GatewayPayConfig.WX_NOTIFY_URL)
				.build();
		CFG_MAP.put(apiConfig.getAppId(), apiConfig);
		TL.set(miniConfig.getAppId());
		return params;
	}

	@Override
	protected String packageResult(String payTradeNo,String xmlResult) {

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
		return GsonUtil.toJson(packageParams);
	}
	
	@Override
    protected Map<String, String> buildRefundParameter(RefundParam refundParam) {
		SysUser sysUser = sysUserService.get(refundParam.getOperator().intValue());
		if(sysUser==null) {
			logger.error("userId:{}",refundParam.getOperator());
			throw new BizException("用户不存在!");
		}
        BaseCompanyMiniProgramConfig miniConfig = miniProgramConfigService.getMiniProgramConfig(sysUser.getAppId());
        if(miniConfig==null) {
        	logger.error("appId:{}",sysUser.getAppId());
			throw new BizException("未找到小程序相关配置!");
		}
        Map<String, String> params = new HashMap<String, String>();
		params.put("appid", miniConfig.getAppId());
		params.put("mch_id",miniConfig.getMchId());
		params.put("nonce_str", System.currentTimeMillis()+"");
		
		if (StringUtils.isNotBlank(refundParam.getOriginInternalTradeNo())) {
			//如果是通过系统内部交易号,则取支付网关的交易号
			PayTradeRecord trade = this.getTradeRecordByIntenalTradeNo(refundParam.getOriginInternalTradeNo());
			if(trade!=null) {
				params.put("out_trade_no", trade.getPayTradeNo());
			}else {
				throw new BizException("原交易不存在!");
			}
			
		}else {
			//如果是通过支付网关的交易号,则取微信号
			PayTradeRecord trade = this.getTradeRecordByPayTradeNo(refundParam.getOriginPayTradeNo());
			if(trade!=null) {
				params.put("transaction_id", trade.getExtenalTradeNo());
			}else {
				throw new BizException("原交易不存在!");
			}
		}
		params.put("out_refund_no", refundParam.getPayRefundNo());
		params.put("total_fee", refundParam.getTotalFee().toString());
		params.put("refund_fee", refundParam.getRefundFee().toString());
		params.put("notify_url", GatewayPayConfig.WX_REFUND_NOTIFY_URL);
		params.put("sign", PaymentKit.createSign(params, miniConfig.getMchKey()));
		params.put("api_cert_path", miniConfig.getApiCertPath());
		return params;
    }
	
	private PayTradeRecord getTradeRecordByIntenalTradeNo(String intenalTradeNo) {
		if(StringUtils.isBlank(intenalTradeNo)) {
			return null;
		}
		PayTradeQueryVo queryVo = new PayTradeQueryVo();
		queryVo.setIntenalTradeNo(intenalTradeNo);
		List<PayTradeRecord> list = payTradeRecordService.getList(queryVo);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	private PayTradeRecord getTradeRecordByPayTradeNo(String payTradeNo) {
		if(StringUtils.isBlank(payTradeNo)) {
			return null;
		}
		PayTradeQueryVo queryVo = new PayTradeQueryVo();
		queryVo.setPayTradeNo(payTradeNo);
		List<PayTradeRecord> list = payTradeRecordService.getList(queryVo);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	
	@Override
	protected String updateAndPackageRefundResult(PayTradeRefundRecord tradeRefundRecord,String xmlResult) {
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
		String return_code = result.get("return_code");
		String return_msg = result.get("return_msg");
		if (!PaymentKit.codeIsOK(return_code)) {
			logger.error(xmlResult);
			throw new BizException("微信小程序退款异常:" + return_msg);
		}
		String result_code = result.get("result_code");
		if (!PaymentKit.codeIsOK(result_code)) {
			logger.error(xmlResult);
			throw new BizException("微信小程序退款结果异常:" + result.get("err_code_des"));
		}
		this.modifyTradeRefundInfo(tradeRefundRecord.getId(),result);
		Map<String, String> resultParams = new HashMap<String, String>();
		resultParams.put("originInternalTradeNo", tradeRefundRecord.getOriginInternalTradeNo());
		resultParams.put("originPayTradeNo", tradeRefundRecord.getOriginPayTradeNo());
		resultParams.put("internalRefundNo", tradeRefundRecord.getInternalRefundNo());
		resultParams.put("payRefundNo", tradeRefundRecord.getPayRefundNo());
		resultParams.put("refundFee", result.get("refund_fee"));
		return GsonUtil.toJson(resultParams);
	}
	
	private void modifyTradeRefundInfo(Long id,Map<String, String> result) {
		PayTradeRefundRecord obj = new PayTradeRefundRecord();
		obj.setId(id);
		obj.setRefundFee(Long.valueOf(result.get("refund_fee").toString()));
		obj.setExternalRefundNo(result.get("refund_id"));
		payTradeRefundRecordService.modify(obj);
	}

	@Override
	protected Object buildTransfersParameter(TransfersParam transfersParam) {
		SysUser user = sysUserService.getMiniUser(transfersParam.getOpenId());
		BaseCompanyMiniProgramConfig miniConfig = miniProgramConfigService.getMiniProgramConfig(user.getAppId());
		if (miniConfig == null) {
			logger.error("openId:{}", transfersParam.getOpenId());
			throw new BizException("未找到小程序相关配置!");
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("mch_appid", miniConfig.getAppId());
		params.put("mchid", miniConfig.getMchId());
		String nonceStr = String.valueOf(System.currentTimeMillis());
		params.put("nonce_str", nonceStr);
		params.put("partner_trade_no", transfersParam.getPayTradeNo());
		params.put("openid", user.getOpenId());
		params.put("check_name", "NO_CHECK");
		params.put("amount", transfersParam.getAmount().toString());
		params.put("desc", transfersParam.getTradeDesc());
		params.put("spbill_create_ip", transfersParam.getIp());
		params.put("sign", PaymentKit.createSign(params, miniConfig.getMchKey()));
		params.put("api_cert_path", miniConfig.getApiCertPath());
		return params;
	}

	@Override
	protected String updateAndPackageTransfersResult(PayTradeTransfersRecord tradeTransfersRecord, String xmlResult) {
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
		this.modifyTradeTransfersInfo(tradeTransfersRecord.getId(),result);
		String return_code = result.get("return_code");
		String return_msg = result.get("return_msg");
		if (!PaymentKit.codeIsOK(return_code)) {
			logger.error(xmlResult);
			throw new BizException("微信小程序企业付款异常:" + return_msg);
		}
		String result_code = result.get("result_code");
		if (!PaymentKit.codeIsOK(result_code)) {
			logger.error(xmlResult);
			throw new BizException("微信小程序企业付款结果异常:" + result.get("err_code_des"));
		}
		Map<String, String> resultParams = new HashMap<String, String>();
		resultParams.put("internalTradeNo", tradeTransfersRecord.getIntenalTradeNo());
		resultParams.put("payTradeNo", tradeTransfersRecord.getPayTradeNo());
		resultParams.put("paymentTime", result.get("payment_time"));
		return GsonUtil.toJson(resultParams);
	}
	
	private void modifyTradeTransfersInfo(Long id,Map<String, String> result) {
		PayTradeTransfersRecord obj = new PayTradeTransfersRecord();
		obj.setId(id);
		if (!PaymentKit.codeIsOK(result.get("return_code"))) {
			obj.setStatus(PayTradeTransfersRecord.STATUS_SUCCESS);
		}else {
			obj.setStatus(PayTradeTransfersRecord.STATUS_FAILURE);
		}
		obj.setExtenalTradeNo(result.get("payment_no"));
		obj.setPaymentTime(result.get("payment_time"));
		payTradeTransfersRecordService.modify(obj);
	}
    
}
