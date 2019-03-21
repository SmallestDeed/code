package com.nork.onekeydesign.service;

import com.nork.onekeydesign.exception.IntelligenceDecorationException;
import com.nork.onekeydesign.model.DesignPlanRecommended;

public interface DesignPlanRecommendedService {

	DesignPlanRecommended get(Integer recommendedPlanId);

	/**
	 * 查询样板房在推荐方案组中最匹配的方案ID
	 * @param designTemplateId	样板房ID
	 * @param recommendedPlanId	打组推荐方案的主ID
	 * @return
	 */
	Integer getBestMatchInPlanGroup(Integer designTemplateId, Integer recommendedPlanId);

	/**
	 * 获取小面积推荐方案id
	 * 
	 * @author huangsongbo
	 * @param planRecommendedId
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	Integer getStandbyRecommendedId(Integer planRecommendedId) throws IntelligenceDecorationException;
	
}
