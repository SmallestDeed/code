package com.nork.design.model;

import java.io.Serializable;

/**
 * 修改装进我家生成的全屋方案的数据模型
 */
public class FullHouseDesignPlanSceneUpdate implements Serializable {
    /**
     * 全屋方案ID
     */
    private Integer fullHouseId;
    /**
     * 源方案ID
     */
    private Integer sourcePlanId;
    /**
     * 新方案ID
     */
    private Integer newPlanId;
    /**
     * 空间类型ID
     */
    private Integer spaceFunctionId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 户型ID
     */
    private Integer houseId;

    public Integer getFullHouseId() {
        return fullHouseId;
    }

    public void setFullHouseId(Integer fullHouseId) {
        this.fullHouseId = fullHouseId;
    }

    public Integer getSourcePlanId() {
        return sourcePlanId;
    }

    public void setSourcePlanId(Integer sourcePlanId) {
        this.sourcePlanId = sourcePlanId;
    }

    public Integer getNewPlanId() {
        return newPlanId;
    }

    public void setNewPlanId(Integer newPlanId) {
        this.newPlanId = newPlanId;
    }

    public Integer getSpaceFunctionId() {
        return spaceFunctionId;
    }

    public void setSpaceFunctionId(Integer spaceFunctionId) {
        this.spaceFunctionId = spaceFunctionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }
}
