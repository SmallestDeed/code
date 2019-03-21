package com.nork.sync.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.nork.home.model.SpaceCommon;

/**
 * Created by Administrator on 2016/5/18.
 */
@XmlRootElement(name="SyncSpaceCommon")
@XmlAccessorType(XmlAccessType.FIELD)
public class SyncSpaceCommon {

    // 空间基础信息
    private SpaceCommon spaceCommon;
    // 空间户型图
    private ResEntity picEntity;
    // ipad缩略图
    private ResEntity iPadSmallPicEntity;
    // ipad缩略图
    private ResEntity webSmallPicEntity;
    // 3d俯视图
    private ResEntity view3dPicEntity;
    // 3d俯视图(ipad缩略图)
    private ResEntity iPadSmallView3dPicEntity;
    // 3d俯视图(web缩略图)
    private ResEntity webSmallView3dPicEntity;
    // 空间俯视平面图
    private ResEntity viewPlanPicEntity;
    // 空间俯视平面图(ipad缩略图)
    private ResEntity iPadSmallViewPlanPicEntity;
    // 空间俯视平面图(web缩略图)
    private ResEntity webSmallViewPlanPicEntity;
    // 空间cad图
    private ResEntity cadPicEntity;

    public SpaceCommon getSpaceCommon() {
        return spaceCommon;
    }

    public void setSpaceCommon(SpaceCommon spaceCommon) {
        this.spaceCommon = spaceCommon;
    }

    public ResEntity getPicEntity() {
        return picEntity;
    }

    public void setPicEntity(ResEntity picEntity) {
        this.picEntity = picEntity;
    }

    public ResEntity getiPadSmallPicEntity() {
        return iPadSmallPicEntity;
    }

    public void setiPadSmallPicEntity(ResEntity iPadSmallPicEntity) {
        this.iPadSmallPicEntity = iPadSmallPicEntity;
    }

    public ResEntity getWebSmallPicEntity() {
        return webSmallPicEntity;
    }

    public void setWebSmallPicEntity(ResEntity webSmallPicEntity) {
        this.webSmallPicEntity = webSmallPicEntity;
    }

    public ResEntity getView3dPicEntity() {
        return view3dPicEntity;
    }

    public void setView3dPicEntity(ResEntity view3dPicEntity) {
        this.view3dPicEntity = view3dPicEntity;
    }

    public ResEntity getiPadSmallView3dPicEntity() {
        return iPadSmallView3dPicEntity;
    }

    public void setiPadSmallView3dPicEntity(ResEntity iPadSmallView3dPicEntity) {
        this.iPadSmallView3dPicEntity = iPadSmallView3dPicEntity;
    }

    public ResEntity getWebSmallView3dPicEntity() {
        return webSmallView3dPicEntity;
    }

    public void setWebSmallView3dPicEntity(ResEntity webSmallView3dPicEntity) {
        this.webSmallView3dPicEntity = webSmallView3dPicEntity;
    }

    public ResEntity getiPadSmallViewPlanPicEntity() {
        return iPadSmallViewPlanPicEntity;
    }

    public void setiPadSmallViewPlanPicEntity(ResEntity iPadSmallViewPlanPicEntity) {
        this.iPadSmallViewPlanPicEntity = iPadSmallViewPlanPicEntity;
    }

    public ResEntity getWebSmallViewPlanPicEntity() {
        return webSmallViewPlanPicEntity;
    }

    public void setWebSmallViewPlanPicEntity(ResEntity webSmallViewPlanPicEntity) {
        this.webSmallViewPlanPicEntity = webSmallViewPlanPicEntity;
    }

    public ResEntity getViewPlanPicEntity() {
        return viewPlanPicEntity;
    }

    public void setViewPlanPicEntity(ResEntity viewPlanPicEntity) {
        this.viewPlanPicEntity = viewPlanPicEntity;
    }

    public ResEntity getCadPicEntity() {
        return cadPicEntity;
    }

    public void setCadPicEntity(ResEntity cadPicEntity) {
        this.cadPicEntity = cadPicEntity;
    }
}
