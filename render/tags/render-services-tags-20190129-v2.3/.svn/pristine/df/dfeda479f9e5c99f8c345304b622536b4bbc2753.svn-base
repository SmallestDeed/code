package com.nork.pano.model.input;

/**
 * 查询720场景信息入参类
 * Created by chenm on 2018/9/30.
 */
public class SceneDataSearch {

    /**
     * 720场景渲染图/业务表Id
     */
    private Integer renderId;
    /**
     * 进入场景类型
     */
    private Integer sceneType;
    /**
     * 分享标题文案
     */
    private String title;
    /**
     * 是否修改过文案,0为未改变,1为已改变
     */
    private Integer hasChanged;
    /**
     * 方案类型
     */
    private Integer  planSourceType;
    /**
     * 720方案分享的uuid（适配其他平台的请求）
     */
    private String uuid;

    /** 场景来源的二维码的类型 **/
    private Integer qrCodeType;

    public class QRCodeType {
        //pc端浏览
        public static final int QRCODE_TYPE_BROWSE = 0;
        //PC端分享的普通二维码
        public static final int QRCODE_TYPE_GENERAL = 1;
        //PC端分享的随选网二维码
        public static final int QRCODE_TYPE_SELECTDECORATION = 2;
        //PC端分享的企业小程序二维码
        public static final int QRCODE_TYPE_MINIPROGRAM = 3;
    }
    /** 平台网站名 **/
    private String platformType;
    public class PlatformType{
        //通用版
        public static final String PC2B = "pc2b";
        //随选网
        public static final String SELECTDECORATION = "selectDecoration";
        //企业微信小程序
        public static final String COMPANY_MINIPROGRAM = "companyMiniprogram";
        //移动B端
        public static final String COMPANY_MOBILE2B = "mobile2b";
        public static final String OTHERS = "others";

    }
    private Integer userId;

    /**   空间样板房的uniqueId（用于指定进入全屋方案时显示的首个空间）  **/
    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getQrCodeType() {
        return qrCodeType;
    }

    public void setQrCodeType(Integer qrCodeType) {
        this.qrCodeType = qrCodeType;
    }
    public String getPlatformType() {
        return platformType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getPlanSourceType() {
        return planSourceType;
    }

    public void setPlanSourceType(Integer planSourceType) {
        this.planSourceType = planSourceType;
    }

    public Integer getHasChanged() {
        return hasChanged;
    }

    public void setHasChanged(Integer hasChanged) {
        this.hasChanged = hasChanged;
    }

    public Integer getRenderId() {
        return renderId;
    }

    public void setRenderId(Integer renderId) {
        this.renderId = renderId;
    }

    public Integer getSceneType() {
        return sceneType;
    }

    public void setSceneType(Integer sceneType) {
        this.sceneType = sceneType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SceneDataSearch{" +
                "renderId=" + renderId +
                ", sceneType=" + sceneType +
                ", title='" + title + '\'' +
                ", hasChanged=" + hasChanged +
                ", planSourceType=" + planSourceType +
                ", uuid='" + uuid + '\'' +
                ", qrCodeType=" + qrCodeType +
                ", platformType='" + platformType + '\'' +
                ", userId=" + userId +
                ", uniqueId='" + uniqueId + '\'' +
                '}';
    }
}
