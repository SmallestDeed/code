package com.sandu.product.model;

import com.sandu.common.model.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 1:57 2018/5/23 0023
 * @Modified By:
 */
@Data
public class MobileDesignPlanProduct extends Mapper implements Serializable{
    //推荐方案或效果图方案id
    private Integer planId;
    //平台id
    private Integer platformId;
    //我的设计
    private Integer designPlanRenderSceneId;
    //推荐方案
    private Integer designPlanRecommendedId;
    // 平台类型
    private String  platformBussinessType;
    //品牌ID
    private List<Integer> brandIds;

    private Integer companyId;
}
