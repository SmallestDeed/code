package com.sandu.pano.model;

import com.sandu.design.model.ProductsCostType;
import com.sandu.pano.model.roam.Roam;
import com.sandu.pano.model.scene.PanoramaVo;
import com.sandu.pano.model.scene.Scene;

import java.io.Serializable;
import java.util.List;

public class SingleSceneVo implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** 用户昵称、企业LOGO、企业名称等基本信息 **/
    private PanoramaVo panoramaVo;


    /** 资源访问url **/
    private String resourceUrl;
    /** 设计方案渲染原图地址**/
    private String picPath;
    /** 设计方案渲染缩略图地址**/
    private String thumbnailPicPath;
    //多点720资源对象
    private List<Roam> roamList;

    public List<Roam> getRoamList() {
        return roamList;
    }

    public void setRoamList(List<Roam> roamList) {
        this.roamList = roamList;
    }

    public String getThumbnailPicPath() {
        return thumbnailPicPath;
    }

    public void setThumbnailPicPath(String thumbnailPicPath) {
        this.thumbnailPicPath = thumbnailPicPath;
    }

    public PanoramaVo getPanoramaVo() {
        return panoramaVo;
    }

    public void setPanoramaVo(PanoramaVo panoramaVo) {
        this.panoramaVo = panoramaVo;
    }


    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
