package com.sandu.api.solution.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: YuXingchi
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanDecoratePrice implements Serializable {

    public final static Integer PLAN_DECORATE_PRICE_FULLHOUSE = 2;
    public final static Integer PLAN_DECORATE_PRICE_RECOMMEND = 1;

    private Integer id;

    private Integer planRecommendId;

    private Integer renderSceneId;

    private Integer fullHouseId;

    private Integer decoratePriceType;

    private Integer decoratePriceRange;

    private Integer decoratePrice;

    private Integer planType;

    private String sysCode;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String remark;

}