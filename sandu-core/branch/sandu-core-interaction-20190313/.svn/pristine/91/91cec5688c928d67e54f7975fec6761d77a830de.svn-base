package com.sandu.api.base.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserCardAccessRecord implements Serializable{
    private static final long serialVersionUID = 3561303447422603323L;
    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 用户名片ID
     */
    private Integer userCardId;

    /**
     * 访问用户id
     */
    private Integer userId;

    /**
     * 1:转发微信好友,2:转发微信朋友圈,3:名片海报
     */
    private Integer accessType;

    /**
     * 访问记录
     */
    private String accessLogInfo;

    /**
     * 0:未读 1:已读
     */
    private int isRead;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新者
     */
    private String modifier;

    /**
     * 修改时间，自动更新
     */
    private Date gmtModified;

    /**
     * 逻辑删除字段,0:正常 1:已删除
     */
    private int isDeleted;

    /**
     * 备注
     */
    private String remark;



}