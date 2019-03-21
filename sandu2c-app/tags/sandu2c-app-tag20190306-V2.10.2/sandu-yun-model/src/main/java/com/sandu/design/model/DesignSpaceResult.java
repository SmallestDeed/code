package com.sandu.design.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DesignSpaceResult implements Serializable {

    /*样板房Id*/
    private Integer templetId;
    /*样板房名称*/
    private String designName;
    /*样板房编码*/
    private String designCode;
    /*样板房图片路径*/
    private String picPath;
    /*空间名称*/
    private String spaceName;
    /*空间面积*/
    private String spaceAreas;
    /*空间描述*/
    private String spaceDesc;
    /*样板间渲染图*/
    private String renderPicIds;
    /*样板间渲染图集合*/
    private List<String> renderPicList = new ArrayList<String>();
    /*收藏状态*/
    private Integer collectState;
    /*房间类型value*/
//	private Integer houseTypeValue;
    /*设计风格value*/
//	private Integer designStyleValue;
    private String designDesc;


    public String getDesignDesc() {
        return designDesc;
    }

    public void setDesignDesc(String designDesc) {
        this.designDesc = designDesc;
    }


    public Integer getTempletId() {
        return templetId;
    }

    public void setTempletId(Integer templetId) {
        this.templetId = templetId;
    }

    public String getDesignName() {
        return designName;
    }

    public void setDesignName(String designName) {
        this.designName = designName;
    }

    public String getDesignCode() {
        return designCode;
    }

    public void setDesignCode(String designCode) {
        this.designCode = designCode;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getSpaceAreas() {
        return spaceAreas;
    }

    public void setSpaceAreas(String spaceAreas) {
        this.spaceAreas = spaceAreas;
    }

    public String getSpaceDesc() {
        return spaceDesc;
    }

    public void setSpaceDesc(String spaceDesc) {
        this.spaceDesc = spaceDesc;
    }

    public String getRenderPicIds() {
        return renderPicIds;
    }

    public void setRenderPicIds(String renderPicIds) {
        this.renderPicIds = renderPicIds;
    }

    public List<String> getRenderPicList() {
        return renderPicList;
    }

    public void setRenderPicList(List<String> renderPicList) {
        this.renderPicList = renderPicList;
    }

    public Integer getCollectState() {
        return collectState;
    }

    public void setCollectState(Integer collectState) {
        this.collectState = collectState;
    }

}
