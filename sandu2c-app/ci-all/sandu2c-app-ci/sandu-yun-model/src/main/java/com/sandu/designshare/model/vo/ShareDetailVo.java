package com.sandu.designshare.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * describe: 组详情VO
 * creat_user: yanghz
 * creat_date: 2017-08-02
 * creat_time: 下午 02:49
 **/
public class ShareDetailVo implements Serializable{
    //缩略图地址列表(每个缩略图对应一个截图id)
    private List<GroupPicVo> picList;
    //组名
    private String groupName;
    //组描述
    private String description;
    //副本名称
    private String sceneName;
    //组的分享次数
    private int shareTimes;
    //组的创建时间
    private Date ctime;

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public List<GroupPicVo> getPicList() {
        return picList;
    }

    public void setPicList(List<GroupPicVo> picList) {
        this.picList = picList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public int getShareTimes() {
        return shareTimes;
    }

    public void setShareTimes(int shareTimes) {
        this.shareTimes = shareTimes;
    }
}
