package com.nork.design.service;

import java.util.List;

import com.nork.design.model.DesignPlanBrand;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.product.model.BaseBrand;
 
 

public interface DesignPlanBrandService {

	public int add(DesignPlanBrand designPlanBrand);
	
	public int update(DesignPlanBrand designPlanBrand);
	
	public int delete(Integer id);
	
	public DesignPlanBrand get(Integer id);
	
	public List<DesignPlanBrand> getList(DesignPlanBrand designPlanBrand);

	public int getCount(DesignPlanBrand designPlanBrand);

	/**
	 * 方案推荐总条数
	 * @param designPlanBrand
	 * @param brandIds 必传
	 * @return
	 */
	public Integer getPlanRecommendedCount(DesignPlanBrand designPlanBrand);

	/**
	 * 方案推荐数据
	 * @param brandIds 必传
	 * @param designPlanBrand
	 * @return
	 */
	public List<DesignPlanRecommendedResult> getPlanRecommendedList(DesignPlanBrand designPlanBrand);

 

	/**
	 * 通过推荐方案id  查询 绑定的品牌
	 * @param parseInt
	 * @return
	 */
	public List<BaseBrand> getListByPlanRecommendedId(int planRecommendedId);

	/**
	 * 批量新增 zhaobl
	 * @param planBrandList
	 */
	public void batchAdd(List<DesignPlanBrand> planBrandList);
 
}
