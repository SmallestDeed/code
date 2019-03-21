package com.nork.onekeydesign.service;

import java.util.List;
import java.util.Map;

import com.nork.onekeydesign.exception.IntelligenceDecorationException;
import com.nork.onekeydesign.model.DesignPlanProduct;
import com.nork.onekeydesign.model.DesignPlanRecommendedProduct;
import com.nork.onekeydesign.model.DesignPlanRecommendedProductResult;
import com.nork.onekeydesign.model.ProductListByTypeInfo;
import com.nork.onekeydesign.model.ProductCostDetail;
import com.nork.onekeydesign.model.ProductsCost;
import com.nork.onekeydesign.model.ProductsCostType;
import com.nork.onekeydesign.model.vo.UnityPlanProductRecommended;
import com.nork.product.model.result.DesignProductResult;

public interface DesignPlanRecommendedProductServiceV2 {

	public int add(DesignPlanRecommendedProduct designPlanRecommendedProduct);
	
	public int update(DesignPlanRecommendedProduct designPlanRecommendedProduct);
	
	public int delete(Integer id);
	
	public DesignPlanRecommendedProduct get(Integer id);
	
	public List<DesignPlanRecommendedProduct> getList(DesignPlanRecommendedProduct designPlanRecommendedProduct);

	public int getCount(DesignPlanRecommendedProduct designPlanRecommendedProduct);

	/**
	 * 批量新增 zhaobl
	 * @param recommendedProductlist
	 */
	public void batchAdd(List<DesignPlanRecommendedProduct> recommendedProductlist);

	public List<DesignPlanRecommendedProductResult> getListLeftProduct(DesignPlanRecommendedProduct dprp);

	public int getListLeftProductCount(DesignPlanRecommendedProduct dprp);

	/**
	 * 通过推荐id 删除产品
	 * @param planRecommendedId
	 */
	public void deletedByPlanRecommendedId(int planRecommendedId);

	/**
	 * 根据推荐方案id查询方案推荐中存在的组合的主产品列表
	 * 
	 * @author huangsongbo
	 * @param id 推荐方案id
	 * @return
	 */
	public List<DesignPlanRecommendedProduct> getMainProductListByPlanRecommendedId(Integer id);

	/**
	 * 查询方案推荐的某个组合明细信息
	 * 
	 * @author huangsongbo
	 * @param planRecommendedId 方案推荐id
	 * @param productGroupId 组合id
	 * @return
	 */
	public List<DesignPlanRecommendedProduct> getListByPlanIdAndGroupId(Integer planRecommendedId,
			Integer productGroupId);
	
	public List<DesignProductResult> getPlanProductList(Integer recommendedPlanId);

	/**
	 * 得到推荐方案产品列表以及额外的信息
	 * 
	 * @author huangsongbo
	 * @param planRecommendedId
	 * @return
	 */
	public List<DesignPlanRecommendedProduct> getListByRecommendedId(Integer planRecommendedId);

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

	public int costTypeListCount(DesignPlanProduct designPlanProduct);

	public List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct);

	public List<ProductsCost> costList(ProductsCostType productsCostType);

	public List<ProductCostDetail> costDetail(ProductsCost cost);

	/**
	 * 得到推荐方案被删除的背景墙方位信息的list
	 * 
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public List<String> getDeleteWallOrientationList(Integer planRecommendedId);
	
	/**
	 * 得到墙体方位list
	 * 
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	List<String> getWallOrientationList(Integer id);

	/**
	 * 获取推荐方案中的所有背景墙信息
	 * @param recommendedPlanId
	 * @return
	 */
	public List<DesignPlanRecommendedProduct> getBeijingProductInfo(Integer recommendedPlanId);

	/**
	 * 判断推荐方案有没有删除该类型的产品
	 * 
	 * @author huangsongbo
	 * @param planRecommendedId
	 * @param smallTypeValuekey
	 * @return
	 */
	public boolean getIsDeleteByProductType(Integer planRecommendedId,
			String smallTypeValuekey);

	List<UnityPlanProductRecommended> getUnityPlanProductRecommendedList(Integer recommendedId);
	List<UnityPlanProductRecommended> getUnityPlanProductRecommendedListByMatch(List<Integer> idList, List<UnityPlanProductRecommended> unityPlanProductRecommendedList);
	List<UnityPlanProductRecommended> getUnityPlanProductRecommendedList(List<Integer> idList);
}
