package com.nork.design.service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.design.model.ReleaseDesignPlanModel;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.BaseProductStyle;
import com.nork.system.model.SysUserPlanRecommended;

public interface NewDesignPlanRecommendService {
	
	public ResponseEnvelope<DesignPlanRecommendedResult> getPlanRecommendedList(String houseType, String livingName,
			String areaValue, String designRecommendedStyleId, String isMainList, String msgId, LoginUser loginUser);

	public ResponseEnvelope<DesignPlanRecommendedResult> recommendDesignPlan(ReleaseDesignPlanModel model, String msgId, LoginUser loginUser) ;
	
	public ResponseEnvelope<BaseBrand> getRecommendedBrandList() ;

	
	public ResponseEnvelope<BaseBrand> getCurrentBrandListByPlanId() ;

	
	public ResponseEnvelope<BaseBrand> delCurrentBrandListByPlanId() ;

	
	public ResponseEnvelope<DesignPlan> recommendDesignPlanDetails();

	
	public ResponseEnvelope<BaseProductStyle> getDesignStyleList() ;

	
	public ResponseEnvelope<SysUserPlanRecommended> getCheckUserListOfPlan();

	
	public ResponseEnvelope<SysUserPlanRecommended> delCheckUserOfPlan();

	
	public ResponseEnvelope<DesignPlan> checkDesignPlanRecommendedCheck();

	
	public ResponseEnvelope<DesignPlanRecommendedResult> getCheckListOfDesignPlan();
		

	
	public ResponseEnvelope<DesignPlan> getRecommendedDesignPlanDetails();
}
