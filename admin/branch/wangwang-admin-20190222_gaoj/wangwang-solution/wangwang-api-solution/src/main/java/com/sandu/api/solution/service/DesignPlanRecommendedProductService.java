package com.sandu.api.solution.service;

import com.sandu.api.solution.model.DesignPlanRecommendedProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author bvvy
 * @date 2017/12/21 0021
 */
public interface DesignPlanRecommendedProductService {
    /**
     * 根据id物理删除数据
     *
     * @param id 主键
     * @return 受影响行数
     */
    int deleteDesignPlanRecommendedProduct(long id);

    /**
     * Selective新增数据
     *
     * @param record 对象
     * @return 新增的数据的id
     */
    long addDesignPlanRecommendedProduct(DesignPlanRecommendedProduct record);

    /**
     * 根据id查询数据
     *
     * @param id 主键
     * @return 对象数据
     */
    DesignPlanRecommendedProduct getDesignPlanRecommendedProduct(long id);

    /**
     * 根据id Selective修改数据
     *
     * @param record 对象
     * @return 受影响的行数
     */
    int updateDesignPlanRecommendedProduct(DesignPlanRecommendedProduct record);

    /**
     * 根据id逻辑删除数据
     *
     * @param id 主键
     * @return 受影响的行数
     */
    int deleteLogicDesignPlanRecommendedProduct(long id);

    /**
     * Selective 获取集合数据
     *
     * @param record 对象
     * @return list数据集合
     */
    List<DesignPlanRecommendedProduct> listDesignPlanRecommendedProduct(DesignPlanRecommendedProduct record);

    /**
     * 批量插入
     * @param products 批量
     * @param planId planid
     */
    void batchAddDesignPlanProducts(List<DesignPlanRecommendedProduct> products, String planId);

    /**
     * 方案的产品
     * @param planId planid
     * @return product
     */
    List<DesignPlanRecommendedProduct> listByPlanId(@Param("planId") Integer planId);
}
