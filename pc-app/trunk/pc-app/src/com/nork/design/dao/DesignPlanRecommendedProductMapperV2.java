package com.nork.design.dao;

import java.util.List;

import com.nork.design.model.output.UnityPlanProductRecommended;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.DesignPlanProduct;
import com.nork.design.model.DesignPlanRecommendedProduct;
import com.nork.design.model.DesignPlanRecommendedProductResult;
import com.nork.design.model.ProductCostDetail;
import com.nork.design.model.ProductsCost;
import com.nork.design.model.ProductsCostType;
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

	List<String> findListByRecommendedPlanId(@Param("recommendedPlanId") Integer recommendedPlanId);

	List<DesignPlanRecommendedProduct> findNoGroupProductUniqueIdList();

	List<DesignPlanRecommendedProduct> findRecommendedGroupProductByPlanRecommendedId(@Param("recommendedPlanId") Integer recommendedPlanId);

	List<UnityPlanProductRecommended> selectUnityPlanProductRecommendedList(@Param("recommendedId") Integer recommendedId);
}
