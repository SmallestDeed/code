package com.sandu.gateway.pay.forward.service;

import com.sandu.gateway.pay.input.PayParam;
import com.sandu.gateway.pay.input.RefundParam;
import com.sandu.gateway.pay.input.TransfersParam;

public interface PayService {

	/**
	 * 用户支付
	 * @param payParam
	 * @return
	 */
   String doPay(PayParam payParam);
   
   /**
    * 退款
    * @param refundParam
    * @return
    */
   String doRefund(RefundParam refundParam);
   
   /**
    * 企业付款
    * @param transfersParam
    * @return
    */
   String doTransfers(TransfersParam transfersParam);
}
