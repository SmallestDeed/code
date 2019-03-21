package com.sandu.gateway.pay.forward.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sandu.config.GatewayPayConfig;
import com.sandu.gateway.pay.config.model.BaseCompanyMiniProgramConfig;
import com.sandu.gateway.pay.config.service.BaseCompanyMiniProgramConfigService;
import com.sandu.gateway.pay.input.PayTradeQueryVo;
import com.sandu.gateway.pay.input.RefundParam;
import com.sandu.gateway.pay.trade.service.PayTradeRecordService;
import com.sandu.gateway.pay.trade.service.PayTradeRefundRecordService;
import com.sandu.gateway.pay.trade.service.PayTradeTransfersRecordService;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jpay.ext.kit.PaymentKit;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.sandu.gateway.pay.common.exception.BizException;
import com.sandu.gateway.pay.common.exception.ExceptionCodes;
import com.sandu.gateway.pay.common.gson.GsonUtil;
import com.sandu.gateway.pay.input.PayParam;
import com.sandu.gateway.pay.trade.model.PayTradeRecord;

import javax.annotation.Resource;

@Service("wxScanCodePayService")
public class WxScanCodePayServiceImpl extends WxPayServiceImpl {
	
    private static Logger logger = LogManager.getLogger(WxScanCodePayServiceImpl.class);

    @Value("${certificate.path}")
    private String CERTIFICATE_PATH;

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
    protected Map<String, String> buildPayParameter(PayParam payParam) {
        WxPayApiConfig apiConfig = super.getWxPayApiConfig();
        Map<String, String> params = apiConfig
                .setBody(payParam.getTradeDesc())
                .setOutTradeNo(payParam.getPayTradeNo())
                .setTotalFee(payParam.getTotalFee().toString())
                .setSpbillCreateIp(payParam.getIp())
				.setTradeType(WxPayApi.TradeType.NATIVE)
                .build();
        return params;
    }
    
    @Override
	protected Integer getPayMethod() {
		return PayTradeRecord.PAY_METHOD_WX_SCANCODE;
	}


	@Override
	protected String packageResult(String payTradeNo,String xmlResult) {
	 	Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        String returnCode = result.get("return_code");
        String returnMsg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(returnCode)) {
            logger.info(xmlResult);
            throw new BizException(ExceptionCodes.CODE_20011001, "微信下单异常:" + returnMsg);
        }
        String resultCode = result.get("result_code");
        String errCodeDes = result.get("err_code_des");
        if (!PaymentKit.codeIsOK(resultCode)) {
            logger.info(xmlResult);
            throw new BizException(ExceptionCodes.CODE_20011002, "微信下单结果异常:" + errCodeDes);
        }
        Map<String, String> packageRetParams = new HashMap<String, String>();
        String qrCodeUrl = result.get("code_url");
        packageRetParams.put("payTradeNo", payTradeNo);
        packageRetParams.put("qrCodeUrl", qrCodeUrl);
		return GsonUtil.toJson(packageRetParams);
	}

	public Object buildRefundParameter(RefundParam refundParam){
        SysUser sysUser = sysUserService.get(refundParam.getOperator().intValue());
        if(sysUser==null) {
            logger.error("userId:{}",refundParam.getOperator());
            throw new com.sandu.pay.common.exception.BizException("用户不存在!");
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", GatewayPayConfig.WX_APP_ID);
        params.put("mch_id",GatewayPayConfig.WX_MCH_ID);
        params.put("nonce_str", System.currentTimeMillis()+"");

        if (StringUtils.isNotBlank(refundParam.getOriginInternalTradeNo())) {
            //如果是通过系统内部交易号,则取支付网关的交易号
            PayTradeRecord trade = this.getTradeRecordByIntenalTradeNo(refundParam.getOriginInternalTradeNo());
            if(trade!=null) {
                params.put("out_trade_no", trade.getPayTradeNo());
            }else {
                throw new com.sandu.pay.common.exception.BizException("原交易不存在!");
            }

        }else {
            //如果是通过支付网关的交易号,则取微信号
            PayTradeRecord trade = this.getTradeRecordByPayTradeNo(refundParam.getOriginPayTradeNo());
            if(trade!=null) {
                params.put("transaction_id", trade.getExtenalTradeNo());
            }else {
                throw new com.sandu.pay.common.exception.BizException("原交易不存在!");
            }
        }
        params.put("out_refund_no", refundParam.getPayRefundNo());
        params.put("total_fee", refundParam.getTotalFee().toString());
        params.put("refund_fee", refundParam.getRefundFee().toString());
        params.put("notify_url", GatewayPayConfig.WX_REFUND_NOTIFY_URL);
        params.put("sign", PaymentKit.createSign(params, GatewayPayConfig.WX_MCH_KEY));
        params.put("api_cert_path", "C:\\Users\\Sandu\\Desktop\\apiclient_cert.p12");
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


}
