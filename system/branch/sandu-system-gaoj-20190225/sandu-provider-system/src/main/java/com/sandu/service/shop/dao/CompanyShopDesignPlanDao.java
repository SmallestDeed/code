package com.sandu.service.shop.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/8/30  17:03
 */
@Repository
public interface CompanyShopDesignPlanDao {
    /**
     * 通过店铺Id删除属于店铺的方案列表
     * @param shopIds 店铺Id
     * @return list 方案列表
     */
    Integer deleteDesignPlanByShopId(@Param("shopIds")List<Integer> shopIds);


    /**
     * 通过店铺Id查找属于店铺的方案数量
     * @param shopIds 店铺Id
     * @return list 方案列表
     */
    Integer getDesignPlanByShopId(@Param("shopIds")List<Integer> shopIds);

    Integer updateMainShopDesignStatus(@Param("mainShopId") Integer mainShopId,
                                       @Param("shopId") Integer shopId,
                                       @Param("status") Integer status);
}
