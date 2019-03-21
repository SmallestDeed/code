package com.sandu.designplan.model;

import java.io.Serializable;

/**
 * 发布设计方案
 *
 * @author Steve
 */
public class ReleaseDesignPlanModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    String brandIds; /* 品牌ids */
    String styleId; /* 风格id */
    /**
     * 副本缩略图id
     **/
    String thumbId;
    String isRelease; /* 设计方案是否发布 0 否 1是 */
    String planNumber; /* 方案编号 */
    String recommendedType;
    String PlanRecommendedId;


    public String getPlanRecommendedId() {
        return PlanRecommendedId;
    }

    public void setPlanRecommendedId(String planRecommendedId) {
        PlanRecommendedId = planRecommendedId;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getThumbId() {
        return thumbId;
    }

    public void setThumbId(String thumbId) {
        this.thumbId = thumbId;
    }

    public String getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(String isRelease) {
        this.isRelease = isRelease;
    }

    public String getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(String planNumber) {
        this.planNumber = planNumber;
    }

    public String getRecommendedType() {
        return recommendedType;
    }

    public void setRecommendedType(String recommendedType) {
        this.recommendedType = recommendedType;
    }


}
