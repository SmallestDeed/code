package com.nork.mobile.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlanRes;
import com.nork.design.service.OptimizePlanService;
import com.nork.mobile.service.MobileAutoRenderAndOneKeyCopyService;
import com.nork.system.model.SysUser;
import com.nork.system.service.SysUserService;

@Service("mobileAutoRenderAndOneKeyCopyService")
public class MobileAutoRenderAndOneKeyCopyServiceImpl implements MobileAutoRenderAndOneKeyCopyService {

	
	private static Logger logger = Logger.getLogger(MobileAutoRenderAndOneKeyCopyServiceImpl.class);
	@Autowired
	private OptimizePlanService optimizePlanService;
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 将一键生成和自动渲染生成的方案复制到我的设计
	 * @param planId 渲染之后生成的方案的id
	 * @param loginUser
	 * @return
	 */
	@Override
	public Integer transformAndCopyPlan(Integer planId, LoginUser loginUser, Integer platformId) {
		//获取登录用户信息
		DesignPlanRes autoandonekey = optimizePlanService.getAutoRenderDesignPlanRes(planId);
		Integer designPlanSceneId = optimizePlanService.saveAsRenderBakScene(autoandonekey, loginUser,platformId);//复制到效果图的方案ID
		return designPlanSceneId;
	}

}
