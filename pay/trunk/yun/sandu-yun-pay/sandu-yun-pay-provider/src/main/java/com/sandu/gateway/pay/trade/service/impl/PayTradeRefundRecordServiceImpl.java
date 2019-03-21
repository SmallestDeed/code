package com.sandu.gateway.pay.trade.service.impl;

import java.util.List;

import com.jpay.alipay.AliPayApiConfig;
import com.jpay.alipay.AliPayApiConfigKit;
import com.sandu.config.GatewayPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.gateway.pay.input.PayTradeRefundQueryVo;
import com.sandu.gateway.pay.trade.dao.PayTradeRefundRecordMapper;
import com.sandu.gateway.pay.trade.model.PayTradeRefundRecord;
import com.sandu.gateway.pay.trade.service.PayTradeRefundRecordService;


@Service("payTradeRefundRecordService")
public class PayTradeRefundRecordServiceImpl implements PayTradeRefundRecordService {

	@Autowired
	private PayTradeRefundRecordMapper payTradeRefundRecordMapper;
   
	@Override
	public Long add(PayTradeRefundRecord payTradeRefundRecord) {
		payTradeRefundRecordMapper.insertSelective(payTradeRefundRecord);
		return payTradeRefundRecord.getId();
	}

	@Override
	public int modify(PayTradeRefundRecord payTradeRefundRecord) {
		return payTradeRefundRecordMapper.updateById(payTradeRefundRecord);
	}
	
	@Override
	public List<PayTradeRefundRecord> getList(PayTradeRefundQueryVo queryVo) {
		return payTradeRefundRecordMapper.selectList(queryVo);
	}

	@Override
	public int changeToProcessStatus(String payRefundNo) {
		// TODO Auto-generated method stub
		return payTradeRefundRecordMapper.updateToProcessStatus(payRefundNo);
	}

	@Override
	public Object getAliRefundApiConfig() {
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
		return aliPayApiConfig;
	}
}
