package com.nork.onekeydesign.service;

import java.util.List;

import com.nork.onekeydesign.exception.IntelligenceDecorationException;
import com.nork.onekeydesign.model.DesignPlanTemplateProduct;

public interface DesignPlanTemplateProductService {

	/**
	 * copy设计方案产品表(designPlanProduct)至一键装修方案副本产品表(designPlanTemplateProduct)
	 * 
	 * @author huangsongbo
	 * @param planId 设计方案id
	 * @param designPlanTemplateId 一键装修方案副本id
	 * @throws IntelligenceDecorationException 
	 */
	void copyByDesignPlanId(Integer planId, Integer designPlanTemplateId) throws IntelligenceDecorationException;

	/**
	 * create DesignPlanTemplateProduct
	 * 
	 * @author huangsongbo
	 * @param designPlanTemplateProduct
	 * @return 
	 * @throws IntelligenceDecorationException 
	 */
	Integer add(DesignPlanTemplateProduct designPlanTemplateProduct) throws IntelligenceDecorationException;

	/**
	 * 如其名
	 * 
	 * @param designPlanTemplateId
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	List<DesignPlanTemplateProduct> getListByPlanId(Integer designPlanTemplateId) throws IntelligenceDecorationException;

}
