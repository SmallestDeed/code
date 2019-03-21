package com.nork.design.dao;

import com.nork.design.model.DesignPlanRecommended;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/6/7
 * @since : sandu_yun_1.0
 */
@Repository
public interface ReleaseDesignPlanMapper {

    List<DesignPlanRecommended> getDesignPlanRecommendedList(DesignPlanRecommended designPlanRecommended);

    List<String> getDesignPlanProductList(@Param("planId") Integer planId);

    List<DesignPlanRecommended> getIsReleasedPlan(DesignPlanRecommended designPlanRecommended);

    void markIsDeletedForCheckFailurePlan(@Param("planId") Integer planId);
}
