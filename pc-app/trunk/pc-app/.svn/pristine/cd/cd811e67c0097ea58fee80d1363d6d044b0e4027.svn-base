package com.nork.design.service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlanRecommendedCheck;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.ReleaseDesignPlanModel;

import java.util.List;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/6/7
 * @since : sandu_yun_1.0
 */
public interface ReleaseDesignPlanService {

    ResponseEnvelope<DesignPlanRecommendedResult> releaseDesignPlan(ReleaseDesignPlanModel designPlanModel, LoginUser loginUser, String msgId);

    DesignPlanRecommendedCheck validateCommonInfo(DesignPlanRenderScene designPlanRenderScene, List<Integer> recommendedTypeArray);
}
