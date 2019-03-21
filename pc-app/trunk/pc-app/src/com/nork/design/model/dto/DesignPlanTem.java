package com.nork.design.model.dto;

import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/9/26 0026 8:30
 * @Modified By
 */
public class DesignPlanTem implements Serializable {
    private static final long serialVersionUID = -6385593250313388669L;

    private Integer planId;
    private String filePath;


    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
