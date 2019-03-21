package com.nork.pano.model.output;


import com.nork.pano.model.roam.Roam;

import java.io.Serializable;
import java.util.List;

public class DesignPlanStoreReleaseDetailsVo implements Serializable{

    /** 方案类型 **/
    private Integer designPlanType;
    /** 方案ID **/
    private Integer designPlanId;
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
