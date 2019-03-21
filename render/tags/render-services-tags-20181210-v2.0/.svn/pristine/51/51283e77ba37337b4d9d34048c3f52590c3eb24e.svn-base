package com.nork.pano.model.output;


import com.nork.pano.model.roam.Roam;

import java.io.Serializable;
import java.util.List;

public class DesignPlanStoreReleaseDetailsVo implements Serializable{

    /** 方案类型 **/
    private Integer designPlanType;
    /** 方案ID **/
    private Integer designPlanId;
    /** 推荐方案ID **/
    private Integer planRecommendedId;
    /** 打组方案主ID **/
    private Integer groupPrimaryId;
    /** 空间类型ID **/
    private Integer spaceFunctionId;
    /** 空间类型 **/
    private String spaceType;
    /** 渲染类型（720、多点、图片、视频） **/
    private Integer renderingType;
    /** 资源ID **/
    private Integer resourceId;
    /** 资源路径 **/
    private String resourcePath;
    /** 是否为主图 **/
    private Integer isMain;
    /** 资源缩略图 **/
    private String thumbPicPath;
    /** 行走的热点坐标信息 **/
    private List<Roam> roamList;
    /** 穿透的热点坐标信息 **/
    private List<CoordinateVo> coordinateList;
    /** 是否为切片资源 **/
    private Integer isShear = IsShear.NO;

    public class IsShear{
        public static final int YES = 1;// 是
        public static final int NO = 0;// 否
    }

    /** 样板房uuid:3d导航图中对应样板房的唯一标示 **/
    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /** 来源类型（该渲染图来自的方案类型）用于查看费用清单 **/
    private Integer planSourceType;

    public class PlanSourceType{
        /**
         * 草图方案
         */
        public static final int DESIGN_PLAN = 1;
        /**
         * 效果图方案
         */
        public static final int DESIGNSCENE_PLAN = 2;
        /**
         * 推荐方案
         */
        public static final int RECOMMENDED_PLAN = 3;
    }
    /** 渲染图来源的方案的Id,方便前端传值 **/
    private Integer sourcePlanId;

    public Integer getPlanRecommendedId() {
        return planRecommendedId;
    }

    public void setPlanRecommendedId(Integer planRecommendedId) {
        this.planRecommendedId = planRecommendedId;
    }

    public Integer getGroupPrimaryId() {
        return groupPrimaryId;
    }

    public void setGroupPrimaryId(Integer groupPrimaryId) {
        this.groupPrimaryId = groupPrimaryId;
    }

    public Integer getSpaceFunctionId() {
        return spaceFunctionId;
    }

    public void setSpaceFunctionId(Integer spaceFunctionId) {
        this.spaceFunctionId = spaceFunctionId;
    }

    public Integer getSourcePlanId() {
        return sourcePlanId;
    }

    public void setSourcePlanId(Integer sourcePlanId) {
        this.sourcePlanId = sourcePlanId;
    }

    public Integer getPlanSourceType() {
        return planSourceType;
    }

    public void setPlanSourceType(Integer planSourceType) {
        this.planSourceType = planSourceType;
    }

    public Integer getDesignPlanType() {
        return designPlanType;
    }

    public void setDesignPlanType(Integer designPlanType) {
        this.designPlanType = designPlanType;
    }

    public Integer getDesignPlanId() {
        return designPlanId;
    }

    public void setDesignPlanId(Integer designPlanId) {
        this.designPlanId = designPlanId;
    }

    public String getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType;
    }

    public Integer getRenderingType() {
        return renderingType;
    }

    public void setRenderingType(Integer renderingType) {
        this.renderingType = renderingType;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    public String getThumbPicPath() {
        return thumbPicPath;
    }

    public void setThumbPicPath(String thumbPicPath) {
        this.thumbPicPath = thumbPicPath;
    }

    public List<Roam> getRoamList() {
        return roamList;
    }

    public void setRoamList(List<Roam> roamList) {
        this.roamList = roamList;
    }

    public List<CoordinateVo> getCoordinateList() {
        return coordinateList;
    }

    public void setCoordinateList(List<CoordinateVo> coordinateList) {
        this.coordinateList = coordinateList;
    }

    public Integer getIsShear() {
        return isShear;
    }

    public void setIsShear(Integer isShear) {
        this.isShear = isShear;
    }
}
