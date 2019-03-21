package com.sandu.fullhouse.dao;

import com.sandu.designplan.model.DesignPlanRecommendedResult;
import com.sandu.designplan.model.PlanRecommendedListModel;
import com.sandu.designplan.vo.PlanDecoratePriceBO;
import com.sandu.fullhouse.input.FullHouseDesignPlanListQuery;
import com.sandu.fullhouse.model.FullHouseDesignPlan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FullHouseDesignPlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FullHouseDesignPlan record);

    int insertSelective(FullHouseDesignPlan record);

    FullHouseDesignPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FullHouseDesignPlan record);

    int updateByPrimaryKey(FullHouseDesignPlan record);

    Integer getFullHousePlanCount(FullHouseDesignPlanListQuery fullHouseDesignPlanListQuery);

    List<DesignPlanRecommendedResult> getFullHousePlanList(FullHouseDesignPlanListQuery fullHouseDesignPlanListQuery);

    List<PlanDecoratePriceBO> getPlanDecoratePrice(Integer designPlanRecommendId);

    List<PlanDecoratePriceBO> getPlanDecoratePriceByFullHouseId(Integer fullHousePlanId);

    int updateViewNumberPlusOne(Integer id);
}