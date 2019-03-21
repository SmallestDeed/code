package com.sandu.pay.alipay.service;

import com.sandu.pay.alipay.model.builder.AlipayTradePayRequestBuilder;
import com.sandu.pay.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import com.sandu.pay.alipay.model.builder.AlipayTradeQueryRequestBuilder;
import com.sandu.pay.alipay.model.builder.AlipayTradeRefundRequestBuilder;
import com.sandu.pay.alipay.model.result.AlipayF2FPayResult;
import com.sandu.pay.alipay.model.result.AlipayF2FPrecreateResult;
import com.sandu.pay.alipay.model.result.AlipayF2FQueryResult;
import com.sandu.pay.alipay.model.result.AlipayF2FRefundResult;

/**
 * Created by liuyangkly on 15/7/29.
 */
public interface AlipayTradeService {

    // 当面付2.0流程支付
    AlipayF2FPayResult tradePay(AlipayTradePayRequestBuilder builder);

    // 当面付2.0消费查询
    AlipayF2FQueryResult queryTradeResult(AlipayTradeQueryRequestBuilder builder);

    // 当面付2.0消费退款
    AlipayF2FRefundResult tradeRefund(AlipayTradeRefundRequestBuilder builder);

    // 当面付2.0预下单(生成二维码)
    AlipayF2FPrecreateResult tradePrecreate(AlipayTradePrecreateRequestBuilder builder);
}
