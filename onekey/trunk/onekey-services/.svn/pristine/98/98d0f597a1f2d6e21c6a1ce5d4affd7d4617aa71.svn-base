package com.nork.onekeydesign.dao;

import java.util.List;

import com.nork.onekeydesign.model.vo.UnityPlanProductRecommended;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.onekeydesign.model.DesignPlanProduct;
import com.nork.onekeydesign.model.DesignPlanRecommendedProduct;
import com.nork.onekeydesign.model.DesignPlanRecommendedProductResult;
import com.nork.onekeydesign.model.ProductCostDetail;
import com.nork.onekeydesign.model.ProductsCost;
import com.nork.onekeydesign.model.ProductsCostType;
import com.nork.product.model.result.DesignProductResult;

@Repository
@Transactional
public interface DesignPlanRecommendedProductMapperV2 {

	 int insertSelective(DesignPlanRecommendedProduct designPlanRecommendedProduct);
		
	 int updateByPrimaryKeySelective(DesignPlanRecommendedProduct designPlanRecommendedProduct);
	
	 int deleteByPrimaryKey(Integer id);
	
	 DesignPlanRecommendedProduct selectByPrimaryKey(Integer id);
	
	 List<DesignPlanRecommendedProduct> selectList(DesignPlanRecommendedProduct designPlanRecommendedProduct);

	 int selectCount(DesignPlanRecommendedProduct designPlanRecommendedProduct);

	void batchAdd(List<DesignPlanRecommendedProduct> recommendedProductlist);

	List<DesignPlanRecommendedProductResult> getListLeftProduct(DesignPlanRecommendedProduct dprp);

	int getListLeftProductCount(DesignPlanRecommendedProduct dprp);
	
	/**
	 * 通过推荐id 删除产品
	 * @param planRecommendedId
	 */
	void deletedByPlanRecommendedId(int planRecommendedId);
	
 	/**
	 * 根据推荐方案id查询方案推荐中存在的组合的主产品列表
	 * 
	 * @author huangsongbo
	 * @param id 推荐方案id
	 * @return
	 */
	List<DesignPlanRecommendedProduct> getMainProductListByPlanRecommendedId(@Param("id") Integer id);

	List<DesignPlanRecommendedProduct> getListByPlanIdAndGroupId(@Param("planRecommendedId") Integer planRecommendedId, @Param("productGroupId") Integer productGroupId);
	
	List<DesignProductResult> getPlanProductList(@Param("recommendedPlanId") Integer recommendedPlanId);

	List<DesignPlanRecommendedProduct> getListByRecommendedId(@Param("planRecommendedId") Integer planRecommendedId);

	int costTypeListCount(DesignPlanProduct designPlanProduct);

	List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct);

	List<ProductsCost> costList(ProductsCostType productsCostType);

	List<ProductCostDetail> costDetail(ProductsCost cost);

	List<String> getWallOrientationList(@Param("id") Integer id);

	List<DesignPlanRecommendedProduct> getBeijingProductInfo(@Param("recommendedPlanId") Integer recommendedPlanId);

	int getCountByPlanIdAndProductTypeValue(
			@Param("planRecommendedId") Integer planRecommendedId,
			@Param("bigTypeValue") Integer bigTypeValue, 
			@Param("smallTypeValue") Integer smallTypeValue);

	int getCountByPlanIdAndProductTypeValueList(
			@Param("planRecommendedId") Integer planRecommendedId,
			@Param("bigTypeValue") Integer bigTypeValue,
			@Param("smallTypeValueList") List<Integer> smallTypeValueList
			);

	List<UnityPlanProductRecommended> selectUnityPlanProductRecommendedList(@Param("recommendedId") Integer recommendedId);

	List<UnityPlanProductRecommended> selectUnityPlanProductRecommendedListByMatch(@Param("idList") List<Integer> idList);
}
