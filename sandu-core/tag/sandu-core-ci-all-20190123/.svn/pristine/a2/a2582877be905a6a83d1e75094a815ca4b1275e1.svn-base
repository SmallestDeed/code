package com.sandu.api.designplan.service;

import com.sandu.api.base.common.LoginUser;
import com.sandu.api.designplan.input.PlanInput;
import com.sandu.api.designplan.input.PlanRenderSceneInput;
import com.sandu.api.designplan.model.DesignPlanRenderScene;
import com.sandu.api.designplan.output.DesignPlanRenderSceneVo;
import com.sandu.api.designplan.output.SingleDesignPlanVo;
import com.sandu.api.designplan.output.SinglePlanRenderSceneVo;

import java.util.List;

public interface DesignPlanRenderSceneService {
    int deleteByPrimaryKey(Long id);

    int insert(DesignPlanRenderScene record);

    int insertSelective(DesignPlanRenderScene record);

    DesignPlanRenderScene selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DesignPlanRenderScene record);

    int updateByPrimaryKey(DesignPlanRenderScene record);

    DesignPlanRenderSceneVo getDesignPlanRenderSceneList(Integer fullHousePlanId, Integer designTemplateId);

    List<SingleDesignPlanVo> getDesignPlanList(Integer demandId, Integer id);

    SinglePlanRenderSceneVo creatNewFullHousePlan(List<PlanRenderSceneInput> planRenderSceneInputList, List<PlanInput> planInputList, LoginUser loginUser, String planName, Integer planId, Integer houseId, String authorization);

    DesignPlanRenderSceneVo getDesignPlanRenderSceneList(Integer planId);

    SinglePlanRenderSceneVo creatNewDesignPlan(List<PlanRenderSceneInput> planRenderSceneInputList, List<PlanInput> planInputList, LoginUser loginUser, String authorization);
}
