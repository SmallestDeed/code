package com.nork.pano.model.qrcode;

import java.io.Serializable;
import java.util.Date;

public class SceneQrCodeInfo implements Serializable{
    private static final long serialVersionUID = -2851847700155712909L;

    /**
     * 
     */
    private Integer id;

    /**
     * 分享文案信息
     */
    private String copywritingInformation;

    /**
     * 场景类型
     */
    private Integer sceneType;

    /**
     * 渲染id
     */
    private String renderId;

    /**
     * 分享用户
     */
    private Integer userId;

    /**
     * 分享场景标题
     */
    private String shareTitle;

    /**
     * 二维码类型
     * 0(PC端浏览);1(PC端分享的普通二维码);2(PC端分享的随选网二维码);3(PC端分享的企业小程序二维码)
     */
    private Integer qrCodeType;

    /**
     * 系统编码
     */
    private String sysCode;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
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
     * 是否删除(0:未删除、1:删除)
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public Integer getQrCodeType() {
        return qrCodeType;
    }

    public void setQrCodeType(Integer qrCodeType) {
        this.qrCodeType = qrCodeType;
    }

    /**
     * 分享文案信息
     * @return copywriting_information 分享文案信息
     */
    public String getCopywritingInformation() {
        return copywritingInformation;
    }

    /**
     * 分享文案信息
     * @param copywritingInformation 分享文案信息
     */
    public void setCopywritingInformation(String copywritingInformation) {
        this.copywritingInformation = copywritingInformation == null ? null : copywritingInformation.trim();
    }

    /**
     * 场景类型
     * @return scene_type 场景类型
     */
    public Integer getSceneType() {
        return sceneType;
    }

    /**
     * 场景类型
     * @param sceneType 场景类型
     */
    public void setSceneType(Integer sceneType) {
        this.sceneType = sceneType;
    }

    /**
     * 渲染id
     * @return render_id 渲染id
     */
    public String getRenderId() {
        return renderId;
    }

    /**
     * 渲染id
     * @param renderId 渲染id
     */
    public void setRenderId(String renderId) {
        this.renderId = renderId == null ? null : renderId.trim();
    }

    /**
     * 分享用户
     * @return user_id 分享用户
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 分享用户
     * @param userId 分享用户
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 系统编码
     * @return sys_code 系统编码
     */
    public String getSysCode() {
        return sysCode;
    }

    /**
     * 系统编码
     * @param sysCode 系统编码
     */
    public void setSysCode(String sysCode) {
        this.sysCode = sysCode == null ? null : sysCode.trim();
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
     * 创建时间
     * @return gmt_create 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 创建时间
     * @param gmtCreate 创建时间
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

    /**
     * 是否删除(0:未删除、1:删除)
     * @return is_deleted 是否删除(0:未删除、1:删除)
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除(0:未删除、1:删除)
     * @param isDeleted 是否删除(0:未删除、1:删除)
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}