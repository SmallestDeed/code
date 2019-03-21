package com.sandu.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MiniProgramShareRecord implements Serializable{
    private static final long serialVersionUID = -2438563368891386359L;
    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 分享人Id
     */
    private Integer userId;

    /**
     * 接受者Id
     */
    private Integer fid;

    /**
     * 分享程序标识
     */
    private String shareSign;

    /**
     * 1:微信朋友圈,2:微信好友,3:QQ好友,4:QQ空间,5:微博,6:贴吧,7:豆瓣,8:知乎,9:微头条,10:简书
     */
    private Integer shareType;

    /**
     * 分享状态0:未注册,1:已注册
     */
    private Integer status;

    /**
     * 分享时间
     */
    private Date shareTime;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 是否删除（0:否，1:是）
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String remark;

}