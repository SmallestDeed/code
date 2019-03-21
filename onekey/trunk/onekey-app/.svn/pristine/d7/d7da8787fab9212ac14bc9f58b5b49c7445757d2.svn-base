package com.nork.onekeydesign.service;

import java.util.List;

import com.nork.onekeydesign.exception.IntelligenceDecorationException;
import com.nork.onekeydesign.model.DesignPlanRecommendedProduct;
import com.nork.onekeydesign.model.ProductListByTypeInfo;

public interface DesignPlanRecommendedProductServiceV2 {

	/**
	 * 得到推荐方案产品列表以及额外的信息
	 * 
	 * @author huangsongbo
	 * @param planRecommendedId
	 * @return
	 */
	List<DesignPlanRecommendedProduct> getListByRecommendedId(
			Integer planRecommendedId);

	/**
	 * 遍历推荐方案产品列表
	 * 将单品/结构/组合分类放置
	 * 
	 * @author huangsongbo
	 * @param designPlanRecommendedProductList
	 * @return
	 * @throws IntelligenceDecorationException 
	 */
	public ProductListByTypeInfo getProductListByTypeInfo(
			List<DesignPlanRecommendedProduct> designPlanRecommendedProductList) throws IntelligenceDecorationException;

}
