package com.sandu.service.solution.dao;

import com.sandu.api.solution.model.DesignPlan2cPlatform;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * DesignPlan2cPlatformMapper class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Repository
public interface DesignPlan2cPlatformMapper {
    /**
     * delete
     * @param id id
     * @return r
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param record record
     * @return 1 , 0
     */
    int insertSelective(DesignPlan2cPlatform record);

    /**
     * select
     * @param id id
     * @return record
     */
    DesignPlan2cPlatform selectByPrimaryKey(Long id);

    List<DesignPlan2cPlatform> selectByPlanIdAndFormId(@Param("planId")Integer planId, @Param("platformId")Integer platformId, @Param("planType")Integer planType);

    /**
     * update
     * @param record record
     * @return 1,  0
     */
    int updateByPrimaryKeySelective(DesignPlan2cPlatform record);


    /**
     * logic delete
     * @param id id
     * @return 1 0
     */
    int deleteLogicByPrimaryKey(Long id);


    /**
     * list
     * @param designPlan2cPlatform query
     * @return list
     */
    List<DesignPlan2cPlatform> selectListSelective(DesignPlan2cPlatform designPlan2cPlatform);

    /**
     * 公国方案类型和方案获取分配上架信息
     * @param planId 方案
     * @param planType 类型
     * @return 1, 0
     */
    List<DesignPlan2cPlatform> listByPlanIdAndType(@Param("planId") Long planId, @Param("planType") Integer planType);

    List<DesignPlan2cPlatform> queryByPlanId(@Param("planIds") List<Integer> planIds);

    /**
     * 根据方案和渠道删除方案信息
     * @param planId
     * @param platformIds
     * @return
     */
	int deleteByPlanId(@Param("planIds")List<String> planId,@Param("platformIds")List<String> platformIds);

	void insertRelListIfNotExist(List<DesignPlan2cPlatform> toList);
	
}