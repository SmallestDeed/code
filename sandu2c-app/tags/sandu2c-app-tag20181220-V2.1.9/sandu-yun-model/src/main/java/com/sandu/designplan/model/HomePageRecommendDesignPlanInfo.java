package com.sandu.designplan.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/10/23
 * @since : sandu_yun_1.0
 */
@Data
public class HomePageRecommendDesignPlanInfo implements Serializable{


    private static final long serialVersionUID = 572672247525677368L;

    private List<DesignPlanRecommendedVo> spaceTypeVList3;
    private List<DesignPlanRecommendedVo> spaceTypeVList4;
    private List<DesignPlanRecommendedVo> spaceTypeVList5;
    private List<DesignPlanRecommendedVo> spaceTypeVList6;
    private List<DesignPlanRecommendedVo> spaceTypeVList7;
    private List<DesignPlanRecommendedVo> spaceTypeVList8;
    private List<DesignPlanRecommendedVo> spaceTypeVList13;


}
