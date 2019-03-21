package com.nork.pano.model;


import java.io.Serializable;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/9/30
 * @since : sandu_yun_1.0
 */
public class RenderTaskState implements Serializable{


    private static final long serialVersionUID = -4693249954535919954L;
    private Integer id;
    private Integer planId;
    private Integer templateId;
    private Integer fullHousePlanId;
    private Integer newFullHousePlanId;
    private Integer businessId;
    private Integer mainTaskId;
    private Integer houseId;
    private Integer spaceFunctionId;
    private Integer planHouseType;
    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getFullHousePlanId() {
        return fullHousePlanId;
    }

    public void setFullHousePlanId(Integer fullHousePlanId) {
        this.fullHousePlanId = fullHousePlanId;
    }

    public Integer getNewFullHousePlanId() {
        return newFullHousePlanId;
    }

    public void setNewFullHousePlanId(Integer newFullHousePlanId) {
        this.newFullHousePlanId = newFullHousePlanId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getMainTaskId() {
        return mainTaskId;
    }

    public void setMainTaskId(Integer mainTaskId) {
        this.mainTaskId = mainTaskId;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getSpaceFunctionId() {
        return spaceFunctionId;
    }

    public void setSpaceFunctionId(Integer spaceFunctionId) {
        this.spaceFunctionId = spaceFunctionId;
    }

    public Integer getPlanHouseType() {
        return planHouseType;
    }

    public void setPlanHouseType(Integer planHouseType) {
        this.planHouseType = planHouseType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
