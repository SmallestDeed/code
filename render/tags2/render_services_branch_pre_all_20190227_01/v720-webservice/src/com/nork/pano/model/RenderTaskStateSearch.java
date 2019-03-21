package com.nork.pano.model;


import java.io.Serializable;
import java.util.List;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/9/30
 * @since : sandu_yun_1.0
 */

public class RenderTaskStateSearch implements Serializable{


    private static final long serialVersionUID = -7295458991158169311L;
    private Integer fullPlanHouseId;

    private Integer newFullPlanHouseId;

    private List<Integer> templateIds;

    public Integer getFullPlanHouseId() {
        return fullPlanHouseId;
    }

    public void setFullPlanHouseId(Integer fullPlanHouseId) {
        this.fullPlanHouseId = fullPlanHouseId;
    }

    public Integer getNewFullPlanHouseId() {
        return newFullPlanHouseId;
    }

    public void setNewFullPlanHouseId(Integer newFullPlanHouseId) {
        this.newFullPlanHouseId = newFullPlanHouseId;
    }

    public List <Integer> getTemplateIds() {
        return templateIds;
    }

    public void setTemplateIds(List <Integer> templateIds) {
        this.templateIds = templateIds;
    }
}
