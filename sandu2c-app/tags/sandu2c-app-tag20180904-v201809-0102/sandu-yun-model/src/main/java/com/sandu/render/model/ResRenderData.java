package com.sandu.render.model;

import com.sandu.pano.model.SingleSceneVo;

import java.io.Serializable;

public class ResRenderData implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //渲染类型
    private Integer renderingType;

    //封面图片
    private String picPath;

    //资源详细信息
    private SingleSceneVo singleSceneVo;

    public SingleSceneVo getSingleSceneVo() {
        return singleSceneVo;
    }

    public void setSingleSceneVo(SingleSceneVo singleSceneVo) {
        this.singleSceneVo = singleSceneVo;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Integer getRenderingType() {

        return renderingType;
    }

    public void setRenderingType(Integer renderingType) {
        this.renderingType = renderingType;
    }
}
