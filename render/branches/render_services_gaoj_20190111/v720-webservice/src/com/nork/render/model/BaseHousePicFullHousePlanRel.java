package com.nork.render.model;

import java.io.Serializable;
import java.util.Date;

public class BaseHousePicFullHousePlanRel implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 全屋户型文件ID
     */
    private Long houseGuidePicInfoId;

    /**
     * 
     */
    private Long picId;

    /**
     * 
     */
    private Long houseId;

    /**
     * 空间类型
     */
    private Short spaceType;

    /**
     * 
     */
    private Long spaceCommonId;

    /**
     * 
     */
    private Long designTempletId;

    /**
     * 
     */
    private Long fullHousePlanId;

    /**
     * 
     */
    private Long mainTaskId;

    /**
     * 空间状态，0：任务失败，1，任务成功，2,任务渲染中
     */
    private Long state;

    /**
     * 
     */
    private Long taskId;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String xCoordinate;

    /**
     * 
     */
    private String yCoordinate;

    /**
     * 
     */
    private String coordinateInfo;

    /**
     * 
     */
    private Short isDeleted;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 
     */
    private Date gmtCreate;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 全屋户型文件ID
     * @return house_guide_pic_info_id 全屋户型文件ID
     */
    public Long getHouseGuidePicInfoId() {
        return houseGuidePicInfoId;
    }

    /**
     * 全屋户型文件ID
     * @param houseGuidePicInfoId 全屋户型文件ID
     */
    public void setHouseGuidePicInfoId(Long houseGuidePicInfoId) {
        this.houseGuidePicInfoId = houseGuidePicInfoId;
    }

    /**
     * 
     * @return pic_id 
     */
    public Long getPicId() {
        return picId;
    }

    /**
     * 
     * @param picId 
     */
    public void setPicId(Long picId) {
        this.picId = picId;
    }

    /**
     * 
     * @return house_id 
     */
    public Long getHouseId() {
        return houseId;
    }

    /**
     * 
     * @param houseId 
     */
    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    /**
     * 空间类型
     * @return space_type 空间类型
     */
    public Short getSpaceType() {
        return spaceType;
    }

    /**
     * 空间类型
     * @param spaceType 空间类型
     */
    public void setSpaceType(Short spaceType) {
        this.spaceType = spaceType;
    }

    /**
     * 
     * @return space_common_id 
     */
    public Long getSpaceCommonId() {
        return spaceCommonId;
    }

    /**
     * 
     * @param spaceCommonId 
     */
    public void setSpaceCommonId(Long spaceCommonId) {
        this.spaceCommonId = spaceCommonId;
    }

    /**
     * 
     * @return design_templet_id 
     */
    public Long getDesignTempletId() {
        return designTempletId;
    }

    /**
     * 
     * @param designTempletId 
     */
    public void setDesignTempletId(Long designTempletId) {
        this.designTempletId = designTempletId;
    }

    /**
     * 
     * @return full_house_plan_id 
     */
    public Long getFullHousePlanId() {
        return fullHousePlanId;
    }

    /**
     * 
     * @param fullHousePlanId 
     */
    public void setFullHousePlanId(Long fullHousePlanId) {
        this.fullHousePlanId = fullHousePlanId;
    }

    /**
     * 
     * @return main_task_id 
     */
    public Long getMainTaskId() {
        return mainTaskId;
    }

    /**
     * 
     * @param mainTaskId 
     */
    public void setMainTaskId(Long mainTaskId) {
        this.mainTaskId = mainTaskId;
    }

    /**
     * 空间状态，0：任务失败，1，任务成功，2,任务渲染中
     * @return state 空间状态，0：任务失败，1，任务成功，2,任务渲染中
     */
    public Long getState() {
        return state;
    }

    /**
     * 空间状态，0：任务失败，1，任务成功，2,任务渲染中
     * @param state 空间状态，0：任务失败，1，任务成功，2,任务渲染中
     */
    public void setState(Long state) {
        this.state = state;
    }

    /**
     * 
     * @return task_id 
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * 
     * @param taskId 
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * 
     * @return name 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 
     * @return x_coordinate 
     */
    public String getxCoordinate() {
        return xCoordinate;
    }

    /**
     * 
     * @param xCoordinate 
     */
    public void setxCoordinate(String xCoordinate) {
        this.xCoordinate = xCoordinate == null ? null : xCoordinate.trim();
    }

    /**
     * 
     * @return y_coordinate 
     */
    public String getyCoordinate() {
        return yCoordinate;
    }

    /**
     * 
     * @param yCoordinate 
     */
    public void setyCoordinate(String yCoordinate) {
        this.yCoordinate = yCoordinate == null ? null : yCoordinate.trim();
    }

    /**
     * 
     * @return coordinate_info 
     */
    public String getCoordinateInfo() {
        return coordinateInfo;
    }

    /**
     * 
     * @param coordinateInfo 
     */
    public void setCoordinateInfo(String coordinateInfo) {
        this.coordinateInfo = coordinateInfo == null ? null : coordinateInfo.trim();
    }

    /**
     * 
     * @return is_deleted 
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * 
     * @param isDeleted 
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 创建者
     * @return creator 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     * @param creator 创建者
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 
     * @return gmt_create 
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 
     * @param gmtCreate 
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 修改人
     * @return modifier 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 修改人
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 修改时间
     * @return gmt_modified 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 修改时间
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}