package com.nork.onekeydesign.service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.onekeydesign.model.DesignPlanRecommended;
import com.nork.onekeydesign.model.UnityDesignPlan;

public interface OptimizePlanService {

	ResponseEnvelope<UnityDesignPlan> findOnekeyAutoRenderPlanInfo(
			Integer designPlanId, Integer designTempletId,
			DesignPlanRecommended designPlanRecommended, String context,
			String msgId, LoginUser loginUser, String mediaType);

	/**
	 * 获取进入设计方案信息
	 * @param designPlanId
	 * @param newFlag
	 * @param houseId
	 * @param livingId
	 * @param residentialUnitsName
	 * @param request
	 * @return Object
	 */
	public Object getDesignPlanInfoForOneKey(Integer designPlanId,Integer newFlag,String houseId,String livingId,String residentialUnitsName, Boolean isRelease, LoginUser loginUser, String mediaType);
	
	public UnityDesignPlan wrapperData(Integer designPlanId, UnityDesignPlan unityDesignPlan);
	
}
