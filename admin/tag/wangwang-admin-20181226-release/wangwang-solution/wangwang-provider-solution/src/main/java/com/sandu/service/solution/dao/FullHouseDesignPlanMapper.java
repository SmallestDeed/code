package com.sandu.service.solution.dao;


import com.sandu.api.solution.input.CompanyShopDesignPlanAdd;
import com.sandu.api.solution.input.FullHouseDesignPlanQuery;
import com.sandu.api.solution.model.CompanyShopDesignPlan;
import com.sandu.api.solution.model.FullHouseDesignPlan;
import com.sandu.api.solution.model.bo.FullHouseDesignPlanBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 15:18 2018/8/22
 */

@Repository
public interface FullHouseDesignPlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FullHouseDesignPlan record);

    int insertSelective(FullHouseDesignPlan record);

    FullHouseDesignPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FullHouseDesignPlan record);

    int updateByPrimaryKey(FullHouseDesignPlan record);

    List<FullHouseDesignPlanBO> selectListSelective(FullHouseDesignPlanQuery query);

    int batchUpdatePlanSecrecy(@Param("planIds")List<Integer> updateIds, @Param("secrecyFlag")Integer secrecyFlag);

    List<FullHouseDesignPlanBO> listShareDesignPlan(FullHouseDesignPlanQuery query);

    /**
     * 查看详情
     * @param planId
     * @return
     */
    List<FullHouseDesignPlanBO> getBaseInfo(@Param("planId") Integer planId);

    /**
     * 根据店铺查询全屋方案
     * @param shopId
     * @return
     */
    List<CompanyShopDesignPlan> storeFullHouseByShopId(@Param("shopId") Integer shopId);

    /**
     * 取消发布
     * @param shopId
     * @param planIds
     * @return
     */
    Integer cancelPublish(@Param("shopId") Integer shopId, @Param("planIds") List<Integer> planIds);

    /**
     * 发布
     * @param add
     * @return
     */
    Integer publish(CompanyShopDesignPlanAdd add);

    int updateSalePrice(@Param("id") Long id, @Param("salePrice")Double salePrice, @Param("salePriceChargeType")Integer salePriceChargeType);

    int updatePlanPrice(@Param("id")Long id, @Param("planPrice")Double planPrice, @Param("chargeType")Integer chargeType);
}