package com.nork.pano.model;

import java.io.Serializable;
import java.util.Date;

/***
 * 720打组制作详情表
 */
public class DesignPlanStoreReleaseDetails implements Serializable {

	private static final long serialVersionUID = -8471415151618022919L;

    /** 主键ID **/
    private Integer id;
    /** 系统code **/
    private String sysCode;
    /** 创建人 **/
    private String creator;
    /** 创建时间 **/
    private Date gmtCreate;
    /** 修改人 **/
    private String modifier;
    /** 修改时间 **/
    private Date gmtModified;
    /** 是否删除 **/
    private Integer isDeleted;
    /** 备注信息 **/
    private String remark;
	/** 720打组主表ID **/
    private Integer storeReleaseId;
    /** 样板房ID **/
    private Integer designTemplateId;
    /** 方案ID **/
    private Integer designPlanId;
    /** 方案类型 **/
    private Integer designPlanType;
    /** 渲染类型（720、多点、图片、视频） **/
    private Integer renderingType;
    /** 资源ID **/
    private Integer resourceId;
    /** 是否为主资源(0:不是;1:是).打开720最先显示的资源 **/
    private Integer isMain;

    /**
     * 资源类型 1:720、2:多点、3:图片、4:视频
     */
    public class RenderingType{
        /** 普通720 **/
        public static final int PANORAMA_RENDER = 1;
        /** 多点720 **/
        public static final int ROAM_RENDER = 2;
        /** 图片 **/
        public static final int PIC = 3;
        /** 视频 **/
        public static final int VIDEO = 4;
    }

    /**
     * 是否为主资源(0:不是;1:是)
     */
    public class IsMain{
        public static final int YES = 1;
        public static final int NO = 1;
    }

    /** 推荐方案Id **/
    private Integer recommendDesignPlanId;

    public Integer getRecommendDesignPlanId() {
        return recommendDesignPlanId;
    }

    public void setRecommendDesignPlanId(Integer recommendDesignPlanId) {
        this.recommendDesignPlanId = recommendDesignPlanId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStoreReleaseId() {
        return storeReleaseId;
    }

    public void setStoreReleaseId(Integer storeReleaseId) {
        this.storeReleaseId = storeReleaseId;
    }

    public Integer getDesignTemplateId() {
        return designTemplateId;
    }

    public void setDesignTemplateId(Integer designTemplateId) {
        this.designTemplateId = designTemplateId;
    }

    public Integer getDesignPlanId() {
        return designPlanId;
    }

    public void setDesignPlanId(Integer designPlanId) {
        this.designPlanId = designPlanId;
    }

    public Integer getDesignPlanType() {
        return designPlanType;
    }

    public void setDesignPlanType(Integer designPlanType) {
        this.designPlanType = designPlanType;
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

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    @Override
    public String toString(){
        return "DesignPlanStoreReleaseDetails{" +
                "id : " + id +
                ",storeReleaseId : " + storeReleaseId +
                ",designPlanId : " + designPlanId +
                ",designPlanType : " + designPlanType +
                ",renderingType : " + renderingType +
                ",resourceId : " + resourceId +
                "}";
    }
}


