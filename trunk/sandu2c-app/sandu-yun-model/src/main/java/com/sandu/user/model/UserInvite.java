package com.sandu.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInvite implements Serializable{
    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 邀请者Id
     */
    private Integer inviteId;

    /**
     * 被邀请人Id
     */
    private Integer fid;


    /**
     * 分享标识
     */
    private String shareSign;

    /**
     * 1:微信朋友圈,2:微信好友,3:QQ好友,4:QQ空间,5:微博
     */
    private Integer shareType;

    /**
     * 邀请状态0:未注册,1:已注册
     */
    private Integer status;

    /**
     * 邀请时间
     */
    private Date inviteTime;

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