package com.sandu.render.model;

import java.io.Serializable;
import java.util.List;

/**
 * 已渲染数据格式
 *
 * @date 20171106
 * @auth pengxuangang
 *
 */
public class ReplaceRenderData implements Serializable{

    private static final long serialVersionUID = -1085534775668463851L;


    //渲染类型---运营网站对应1,4,6,8
    private Integer renderType;
    //封面图片
    private String coverUrl;
    //图片类型URL
    private List<String> picList;
    //720场景URL
    private String sence720Url;
    //多点720场景URL
    private String multePointSence720Url;
    //视频URL
    private String videoUrl;

    public Integer getRenderType() {
        return renderType;
    }

    public void setRenderType(Integer renderType) {
        this.renderType = renderType;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public String getSence720Url() {
        return sence720Url;
    }

    public void setSence720Url(String sence720Url) {
        this.sence720Url = sence720Url;
    }

    public String getMultePointSence720Url() {
        return multePointSence720Url;
    }

    public void setMultePointSence720Url(String multePointSence720Url) {
        this.multePointSence720Url = multePointSence720Url;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "AlearyRenderData{" +
                "renderType=" + renderType +
                ", coverUrl='" + coverUrl + '\'' +
                ", picList=" + picList +
                ", sence720Url='" + sence720Url + '\'' +
                ", multePointSence720Url='" + multePointSence720Url + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }
}
