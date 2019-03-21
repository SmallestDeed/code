package com.nork.design.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.DesignPlanBrand;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.product.model.BaseBrand;


@Repository
@Transactional
public interface DesignPlanBrandMapper {

	 int insertSelective(DesignPlanBrand designPlanBrand);
	
	 int updateByPrimaryKeySelective(DesignPlanBrand designPlanBrand);
	
	 int deleteByPrimaryKey(Integer id);
	
	 DesignPlanBrand selectByPrimaryKey(Integer id);
	
	 List<DesignPlanBrand> selectList(DesignPlanBrand designPlanBrand);

	 int selectCount(DesignPlanBrand designPlanBrand);

	/**
	 * 方案推荐总条数
	 * @param designPlanBrand
	 * @return
	 */
	 
	Integer getPlanRecommendedCount(DesignPlanBrand designPlanBrand);
	/**
	 * 方案推荐数据
	 * @param designPlanBrand
	 * @return
	 */
	List<DesignPlanRecommendedResult> getPlanRecommendedList(DesignPlanBrand designPlanBrand);
	
    /**
	 * 通过推荐方案id  查询 绑定的品牌
	 * @param parseInt
	 * @return
	 */
	List<BaseBrand> getListByPlanRecommendedId(@Param("planRecommendedId") int planRecommendedId);

	void batchAdd(List<DesignPlanBrand> planBrandList);

	void deleteByPlanId(@Param("planId") Integer planId);
}
