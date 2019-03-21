package com.nork.mobile.service;

import com.nork.mobile.model.search.MobilePaymentModel;
import com.nork.pay.model.PayOrder;
import com.nork.pay.model.ResultMessage;

public interface MobileUnlockingService {

	/**
	 * 使用积分开通移动端功能
	 * @param mobilePaymentModel
	 * @return
	 */
	Object unlockingByIntegral(MobilePaymentModel mobilePaymentModel);
	
	/**
	 * 获取微信支付配置
	 * @return
	 */
	Object getWXSettings(PayOrder payOrder,ResultMessage message);
}
