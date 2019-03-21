package com.sandu.api.shop.service;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 店铺方案Service层
 * @author WangHaiLin
 * @date 2018/8/30  16:58
 */
@Component
public interface CompanyShopDesignPlanService {
    /**
     * 通过店铺Id删除属于店铺的方案列表
     * @param shopIds 店铺Id
     * @return list 方案列表
     */
    Integer deleteDesignPlanByShopId(List<Integer> shopIds);

    /**
     * 通过店铺Id查找属于店铺的方案数量
     * @param shopIds 店铺Id
     * @return list 方案列表
     */
    Integer getDesignPlanByShopId(List<Integer> shopIds);

    /**
     * 更新店铺方案的发布状态
     * @param mainShopId
     * @param shopId
     * @param status
     * @return
     */
    Integer updateMainShopDesignStatus(Integer mainShopId, Integer shopId, Integer status);
}
