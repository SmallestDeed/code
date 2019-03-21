package com.nork.design.service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.system.model.ResRenderPic;

public interface DesignPlanRecommendedServiceV2 {

	
	/**
	 * 详情查看
	 * @param msgId
	 * @return
	 */
	ResponseEnvelope<ResRenderPic> detailsSee(String msgId, String picId,String detailsSeeType,LoginUser loginUser);
	
	public DesignPlanRecommended get(Integer id);

	

}
