package com.nork.mobile.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.model.LoginUser;
import com.nork.mobile.model.search.HouseSearchModel;
import com.nork.mobile.service.MobileAutoRenderAndOneKeyCopyService;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;
/**
 * 
 * @author yangzhun
 *
 */
@Controller
@RequestMapping("/{style}/mobile/autoRenderAndOneKeyCopy")
public class MobileAutoRenderAndOneKeyCopyController {

	@Autowired
	private MobileAutoRenderAndOneKeyCopyService mobileAutoRenderAndOneKeyCopyService;
	
	@Autowired
	private SysUserService sysUserService;

	@RequestMapping("/transformAndCopyPlan")
	@ResponseBody
	public Object transformAndCopyPlan(@RequestBody HouseSearchModel model, HttpServletRequest request) {
		
		Integer planId = model.getPlanId();//获取前端传一个一键生成的设计方案的id
		Integer userId = model.getUserId();
		SysUser sysUser = sysUserService.get(userId);
		LoginUser loginUser = sysUser.toLoginUser();
		
		return mobileAutoRenderAndOneKeyCopyService.transformAndCopyPlan(planId, loginUser);
	}
}
