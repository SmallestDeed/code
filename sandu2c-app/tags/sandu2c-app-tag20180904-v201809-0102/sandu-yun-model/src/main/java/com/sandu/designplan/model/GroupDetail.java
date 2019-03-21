package com.sandu.designplan.model;


/**
 * Created by Administrator on 2017/8/3.
 */
public class GroupDetail {

    /** 打组名称 **/
    private String groupName;
    /** 缩略图ID **/
    private Integer thumbId;
    /** 缩略图PATH **/
    private String thumbPath;
    /** 720原图ID **/
    private Integer picId;
    /** 720原图PATH **/
    private String picPath;
    /** 缩略图描述 **/
    private String thumbDesc;
    /** 类型。单点、漫游、视频、普通高清 GroupDetailType常量类 **/
    private Integer type;
    /** 设计方案ID 获取设计方案产品等其他信息需要使用到 **/
    private Integer planId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getThumbId() {
        return thumbId;
    }

    public void setThumbId(Integer thumbId) {
        this.thumbId = thumbId;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getThumbDesc() {
        return thumbDesc;
    }

    public void setThumbDesc(String thumbDesc) {
        this.thumbDesc = thumbDesc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
