package com.sandu.api.act.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * inviteRecord
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-11-20 14:36
 */
@Data
public class WxActBargainInviteRecord implements Serializable {

    
    /** 记录id */
    private String id;
    /** 报名id */
    private String registrationId;
    /** 砍价人open_id */
    private String openId;
    /** 砍价人昵称 */
    private String nickname;
    /** 活动参与人头像 */
    private String headPic;
    /** 砍掉价格 */
    private Double cutPrice;
    /** 砍后价格 */
    private Double remainPrice;
    /** 微信appid */
    private String appId;
    /** 创建时间 */
    private Date gmtCreate;
    /** 是否删除：0未删除、1已删除 */
    private Integer isDeleted;
    
}
