package com.sandu.fullhouse.dao;

import com.sandu.designplan.model.*;
import com.sandu.designplan.vo.MydecorationPlanVo;
import com.sandu.designplan.vo.PlanDecoratePriceBO;
import com.sandu.fullhouse.input.FullHouseDesignPlanListQuery;
import com.sandu.fullhouse.model.FullHouseDesignPlan;

import com.sandu.render.model.AutoRenderTask;
import org.apache.ibatis.annotations.Param;
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

    Integer getFavoriteFullHousePlanCount(DesignPlanRecommended designPlanRecommended);

    List<DesignPlanRecommendedResult> getFavoriteFullHousePlan(DesignPlanRecommended designPlanRecommended);

	Integer getHouseIdByFullPlanId(@Param("fullPlanId") Integer fullPlanId);

    int selectMydecorationPlanCount(@Param("userId") Integer userId);

    List<MydecorationPlanVo> selectMydecorationPlanList(MydecorationPlanAdd mydecorationPlanAdd);

    List<MydecorationPlanVo> selectMyPlanList(MydecorationPlanAdd mydecorationPlanAdd);

    int selectMyPlanCount(Integer userId);

    int updateDesignPlanRenderScene(DesignPlanRenderScene designPlanRenderScene);

    int updateFullHouseDesignPlan(FullHouseDesignPlan fullHouseDesignPlan);

    int delDesignPlanRenderScene(@Param("id") Integer id, @Param("planId")Integer planId);

    int delFullHouseDesignPlan(@Param("id") Integer id,@Param("planId")  Integer planId);

    int updateAutoRenderTask( AutoRenderTask autoRenderTask);

    int updateAutoRenderTaskState(AutoRenderTask autoRenderTask);

    int delAutoRenderTask(@Param("taskId") Integer taskId);

    int delAutoRenderTaskState(@Param("taskId")Integer taskId);

    List<ResRenderPic> selectResRenderCoverPic(@Param("sceneIds") List<Integer> sceneIds);

    int delAutoRenderTaskStateByMainTask(Integer taskId);

    int delAutoRenderTaskByMainTask(Integer taskId);

    int updateAutoRenderTaskByMainTaskId(AutoRenderTask autoRenderTask);

    int updateAutoRenderTaskStateByMainTaskId(AutoRenderTask autoRenderTask);

    List<DesignPlanRecommendedResult> getSuperiorFullHousePlanList(@Param("userId")Integer userId, @Param("list") List<Integer> fullHouseSuperiorPlanIdList);
}
