package com.nork.mobile.service;

import com.nork.common.model.ResponseEnvelope;
import com.nork.mobile.model.search.MobileRenderingModel;
import com.nork.pay.model.PayOrder;
import com.nork.pay.model.ResultMessage;
import com.nork.system.model.SysUser;

public interface MobileUserService {
	
	SysUser findByMobileAndPwd(String mobile, String pwd);

	/**
	 * 移动端登录接口
	 * @param sysUser
	 * @return
	 */
	ResponseEnvelope login(SysUser sysUser,String sessionId);

	/**
	 * 找回密码接口
	 */
	//Object findPassword(SysUser sysUser2)

	/**
	 * 个人消费记录列表
	 * @return
	 */
	Object findExpenseRecordList(MobileRenderingModel model);
	
	/**
	 * 开通移动端
	 * @return
	 */
	Object openMobile(PayOrder payOrder,ResultMessage message);
}
