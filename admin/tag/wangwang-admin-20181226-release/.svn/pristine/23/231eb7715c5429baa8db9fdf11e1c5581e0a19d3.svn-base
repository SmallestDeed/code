package com.sandu.service.solution.dao;

import com.sandu.api.solution.model.DesignPlanRecommendedProduct;
import com.sandu.api.solution.model.po.DesignPlanProductDisplayPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Roc Chen
 * @Description 推荐设计方案产品 Dao
 * @Date:Created Administrator in 20:51 2017/12/20 0020
 * @Modified By:
 */
@Repository
public interface DesignPlanRecommendedProductMapper {
    /**
     * delete one
     * @param id id
     * @return 1, 0
     */
    int deleteByPrimaryKey(Long id);

    /**
     * insert
     * @param  record record
     * @return 1, 0
     */
    int insertSelective(DesignPlanRecommendedProduct record);

    /**
     * get one
     * @param id id
     * @return one
     */
    DesignPlanRecommendedProduct selectByPrimaryKey(Long id);

    /**
     * update
     * @param record record
     * @return 1, 0
     */
    int updateByPrimaryKeySelective(DesignPlanRecommendedProduct record);


    /**
     * logic delete
     * @param id id
     * @return 1 0
     */
    int deleteLogicByPrimaryKey(Long id);

    /**
     * list
     * @param record query
     * @return list
     */
    List<DesignPlanRecommendedProduct> selectListSelective(DesignPlanRecommendedProduct record);

    /**
     * 切换方案产品显示
     * @param displayBO display
     */
    void toggleDisplay(DesignPlanProductDisplayPO displayBO);

    /**
     * 获取方案产品
     * @param planId 方案
     * @return 产品
     */
    @Select("select * from design_plan_recommended_product where plan_recommended_id = #{planId}" )
    List<DesignPlanRecommendedProduct> listByPlanId(@Param("planId") Integer planId);

    void batchAddDesignPlanProducts(@Param("products") List<DesignPlanRecommendedProduct> products,@Param("planId")String planId);
}