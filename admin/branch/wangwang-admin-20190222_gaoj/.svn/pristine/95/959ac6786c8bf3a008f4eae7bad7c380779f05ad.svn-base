package com.sandu.service.solution.dao;


import com.sandu.api.solution.model.DesignPlan2bPlatform;
import com.sandu.api.solution.model.DesignPlan2cPlatform;
import com.sandu.api.solution.model.DesignPlanRecommended;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * DesignPlan2bPlatformMapper class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Repository
public interface DesignPlan2bPlatformMapper {
    /**
     * 删除
     *
     * @param id id
     * @return 1  0
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 添加
     *
     * @param record 记录
     * @return 1,  0
     */
    int insertSelective(DesignPlan2bPlatform record);

    /**
     * 获取一个
     *
     * @param id id
     * @return 单个
     */
    DesignPlan2bPlatform selectByPrimaryKey(Long id);


    /**
     * 逻辑删除
     *
     * @param id id
     * @return int
     */
    int deleteLogicByPrimaryKey(Long id);

    /**
     * 更新
     *
     * @param record 记录
     * @return 1 0
     */
    int updateByPrimaryKeySelective(DesignPlan2bPlatform record);


    /**
     * 列表
     *
     * @param record 记录
     * @return list
     */
    List<DesignPlan2bPlatform> selectListSelective(DesignPlan2bPlatform record);

    /**
     * 获取单个上架状态
     *
     * @param planId   方案id
     * @param planType 方案类型 1,2 一键公开
     * @return 列表
     */
    List<DesignPlan2bPlatform> listByPlanIdAndType(@Param("planId") Long planId, @Param("planType") Integer planType);

    /**
     * 根据id查询platform信息
     *
     * @param planIds
     * @return
     */
    List<DesignPlan2bPlatform> queryByPlanId(@Param("planIds") List<Integer> planIds);

    /**
     * 根据方案和渠道删除方案信息
     * @param planId
     * @param platformIds
     * @return
     */
	int deleteByPlanId(@Param("planIds")List<String> planId,@Param("platformIds")List<String> platformIds);

    List<DesignPlan2bPlatform> selectByPlanIdAndFormId(@Param("planId")Integer planId, @Param("platformId")Integer platformId, @Param("planType")Integer planType);

	void insertRelListIfNotExist(List<DesignPlan2bPlatform> toList);

}