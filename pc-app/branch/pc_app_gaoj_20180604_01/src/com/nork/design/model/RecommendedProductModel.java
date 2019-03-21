package com.nork.design.model;

import java.io.Serializable;

/**
 * Created by kono on 2018/2/2 0002.
 */
public class RecommendedProductModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer groupId;

    private String planGroupId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getPlanGroupId() {
        return planGroupId;
    }

    public void setPlanGroupId(String planGroupId) {
        this.planGroupId = planGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecommendedProductModel that = (RecommendedProductModel) o;

        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        return planGroupId != null ? planGroupId.equals(that.planGroupId) : that.planGroupId == null;
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (planGroupId != null ? planGroupId.hashCode() : 0);
        return result;
    }
}
