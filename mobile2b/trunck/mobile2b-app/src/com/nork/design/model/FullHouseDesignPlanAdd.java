package com.nork.design.model;

import java.io.Serializable;
import java.util.List;

/**
 * 制作全屋方案数据模型
 */
public class FullHouseDesignPlanAdd implements Serializable {
    /**
     * 全屋方案名称
     */
    private String designPlanName;
    /**
     * 方案风格ID
     */
    private Integer designPlanStyleId;
    /**
     * 客餐厅方案ID集合
     */
    private List<Integer> livingDiningRoom;
    /**
     * 卧室方案ID集合
     */
    private List<Integer> bedroom;
    /**
     * 厨房方案ID集合
     */
    private List<Integer> kitchen;
    /**
     * 卫生间方案ID集合
     */
    private List<Integer> toilet;
    /**
     * 书房方案ID集合
     */
    private List<Integer> schoolroom;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 全屋方案原方案id
     */
    private Integer fullHousePlanSourceId;

    /**
     * 户型ID
     */
    private Integer houseId;

    public Integer getFullHousePlanSourceId() {
        return fullHousePlanSourceId;
    }

    public void setFullHousePlanSourceId(Integer fullHousePlanSourceId) {
        this.fullHousePlanSourceId = fullHousePlanSourceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDesignPlanName() {
        return designPlanName;
    }

    public void setDesignPlanName(String designPlanName) {
        this.designPlanName = designPlanName;
    }

    public Integer getDesignPlanStyleId() {
        return designPlanStyleId;
    }

    public void setDesignPlanStyleId(Integer designPlanStyleId) {
        this.designPlanStyleId = designPlanStyleId;
    }

    public List<Integer> getLivingDiningRoom() {
        return livingDiningRoom;
    }

    public void setLivingDiningRoom(List<Integer> livingDiningRoom) {
        this.livingDiningRoom = livingDiningRoom;
    }

    public List<Integer> getBedroom() {
        return bedroom;
    }

    public void setBedroom(List<Integer> bedroom) {
        this.bedroom = bedroom;
    }

    public List<Integer> getKitchen() {
        return kitchen;
    }

    public void setKitchen(List<Integer> kitchen) {
        this.kitchen = kitchen;
    }

    public List<Integer> getToilet() {
        return toilet;
    }

    public void setToilet(List<Integer> toilet) {
        this.toilet = toilet;
    }

    public List<Integer> getSchoolroom() {
        return schoolroom;
    }

    public void setSchoolroom(List<Integer> schoolroom) {
        this.schoolroom = schoolroom;
    }

    @Override
    public String toString() {
        return "FullHouseDesignPlanAdd{" +
                "designPlanName='" + designPlanName + '\'' +
                ", designPlanStyleId=" + designPlanStyleId +
                ", livingDiningRoom=" + livingDiningRoom +
                ", bedroom=" + bedroom +
                ", kitchen=" + kitchen +
                ", toilet=" + toilet +
                ", schoolroom=" + schoolroom +
                ", userId=" + userId +
                ", fullHousePlanSourceId=" + fullHousePlanSourceId +
                '}';
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }
}
