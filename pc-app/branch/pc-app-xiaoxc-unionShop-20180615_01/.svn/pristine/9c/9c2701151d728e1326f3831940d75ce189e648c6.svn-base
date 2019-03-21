package com.nork.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.mobile.model.search.MobilePaymentModel;
import com.nork.mobile.service.MobileUnlockingService;

/**
 * 移动端开通扣费的接口
 * @author yangzhun
 * @creatTime 2017年11月22日17:52:19
 */
@Controller
@RequestMapping("/{style}/mobile/unlocking")
public class MobileUnlockingController {

	@Autowired
	private MobileUnlockingService mobileUnlockingService;
	/**
	 * 通过积分开通
	 * @return
	 */
	@RequestMapping(value = "/unlockingByIntegral")
	@ResponseBody
	public Object unlockingByIntegral(@RequestBody MobilePaymentModel mobilePaymentModel) {
		
		
		return mobileUnlockingService.unlockingByIntegral(mobilePaymentModel);
	}
}
