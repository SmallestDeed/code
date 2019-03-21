package com.nork.design.model;

import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description 组合方案累列表
 * @Date 2018/8/13 0013 13:54
 * @Modified By
 */
public class DesignRenderGroup implements Serializable {
    private static final long serialVersionUID = 9152044547563731132L;

    private Integer id;             //方案id
    private String planCode;        //方案编码
    private String planName;        //方案名称
    private String isChecked = "0"; //是否选中(0:未选中、1：选中)
    private String groupPrimaryId;  //主方案id
    private String applySpaceAreas; //适用面积

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getGroupPrimaryId() {
        return groupPrimaryId;
    }

    public void setGroupPrimaryId(String groupPrimaryId) {
        this.groupPrimaryId = groupPrimaryId;
    }

    public String getApplySpaceAreas() {
        return applySpaceAreas;
    }

    public void setApplySpaceAreas(String applySpaceAreas) {
        this.applySpaceAreas = applySpaceAreas;
    }
}
