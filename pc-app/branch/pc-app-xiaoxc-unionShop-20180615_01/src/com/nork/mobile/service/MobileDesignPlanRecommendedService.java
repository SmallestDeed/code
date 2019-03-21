package com.nork.mobile.service;

import com.nork.common.model.LoginUser;

public interface MobileDesignPlanRecommendedService {

	/**
	 * 
	 * 方案推荐 列表 数据
	 * @param houseType
	 * @param livingName
	 * @param areaValue
	 * @param designRecommendedStyleId
	 * @param isMainList
	 * @param msgId
	 * @param loginUser
	 * @return
	 */
	public Object getPlanRecommendedList(/*String all,*/String creator,String brandName,String houseType, String livingName, String areaValue,
			String designRecommendedStyleId, String isMainList, String msgId, LoginUser loginUser,Integer limit,Integer start,Integer templateId,String planName);
	
	
}
