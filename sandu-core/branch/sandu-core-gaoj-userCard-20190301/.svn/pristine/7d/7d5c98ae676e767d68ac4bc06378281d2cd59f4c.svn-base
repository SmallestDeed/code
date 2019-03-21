package com.sandu.api.base.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserCardTransmitRecord implements Serializable{
    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 用户id，与sys_user表中的主键关联
     */
    private Integer userId;

    /**
     * 用户电子名片id，与user_card表中的主键关联
     */
    private Integer userCardId;

    /**
     * 1:转发微信好友,2:转发微信朋友圈,3:转发名片海报
     */
    private Byte transmitType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改时间，自动更新
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 逻辑删除字段,0:正常 1:已删除
     */
    private Byte isDelete;

    /**
     * 备注
     */
    private String remark;
}