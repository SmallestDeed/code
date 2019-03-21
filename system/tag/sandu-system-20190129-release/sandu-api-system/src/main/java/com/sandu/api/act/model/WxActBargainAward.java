package com.sandu.api.act.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * award
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-11-20 14:25
 */
@Data
public class WxActBargainAward implements Serializable {

    
    /**  */
    private String id;
    /** 活动id */
    private String actId;
    /** 报名id */
    private String registrationId;
    /** 兑奖人open_id */
    private String openId;
    /** 兑奖人昵称 */
    private String nickname;
    /** 收货人 */
    private String receiver;
    /** 手机号 */
    private String mobile;
    /** 详细地址 */
    private String address;
    /** 创建时间 */
    private Date gmtCreate;
    /** 微信appid */
    private String appId;
    /** 是否删除：0未删除、1已删除 */
    private Integer isDeleted;
    
}
