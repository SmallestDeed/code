package com.sandu.api.act.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * registration
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-11-20 14:31
 */
@Data
public class WxActBargainRegistration implements Serializable {

   


	/**
	 * 任务未完成
	 */
	public static Integer COMPLETE_STATUS_UNFINISH = 0; 
	/**
	 * 任务已完成
	 */
	public static Integer COMPLETE_STATUS_FINISH = 10; 
	
	/**
	 * 未领奖
	 */
	public static Integer AWARDS_STATUS_UNAWRD = 0;
	/**
	 * 待领奖
	 */
	public static Integer AWARDS_STATUS_WAIT_AWARD = 10;
	/**
	 * 已领奖
	 */
	public static Integer AWARDS_STATUS_AWARDED = 20;
	
	/**
	 * 正常
	 */
	public static Integer EXCEPTION_STATUS_OK = 0;
	/**
	 * 无库存
	 */
	public static Integer EXCEPTION_STATUS_NO_STOCK = 10;
	
	
	/**
	 * 未邀请
	 */
	public static Integer INVITE_STATUS_UNINVITE = 0;
	/**
	 * 已邀请
	 */
	public static Integer INVITE_STATUS_INVITED = 10;
	
	/**
	 * 未装进我家
	 */
	public static Integer DECORATE_STATUS_UNDECORATE = 0;
	/**
	 * 已装进我家
	 */
	public static Integer DECORATE_STATUS_DECORATED = 10;
	
	
	/**
	 * 未发货
	 */
	public static Integer SHIPMENT_STATUS_UNDELIVERED = 0;
	
	/**
	 * 已发货
	 */
	public static Integer SHIPMENT_STATUS_DELIVERED = 10;
	
	/**
	 * 未领奖
	 */
	public static String STATUS_CODE_UNAWARD = "UNAWARD";
	/**
	 * 已领奖
	 */
	public static String STATUS_CODE_AWARDED = "AWARDED";
	/**
	 * 活动未开始
	 */
	public static String STATUS_CODE_ACT_UNBEGIN = "ACT_UNBEGIN";
	/**
	 * 活动已结束
	 */
	public static String STATUS_CODE_ACT_ENDED = "ACT_ENDED";
	/**
	 * 未邀请
	 */
	public static String STATUS_CODE_UNINVITE = "UNINVITE";
	/**
	 * 邀请中
	 */
	public static String STATUS_CODE_INVITING = "INVITING";

	
	
	
    /**  */
    private String id;
    /** 活动id */
    private String actId;
    /** 活动参与人open_id */
    private String openId;
    /** 活动参与人昵称 */
    private String nickname;
    /** 活动参与人头像 */
    private String headPic;
    /** 产品名称 */
    private String productName;
    /** 产品价格 */
    private Double productPrice;
    /** 产品价格 */
    private Double productMinPrice;
    /** 砍后价格 */
    private Double productRemainPrice;
    /** 好友砍价最低金额 */
    private Double cutMethodPriceMin;
    /** 好友砍价最高金额 */
    private Double cutMethodPriceMax;
    /** 最少邀请好友数 */
    private Integer cutMethodInviteNum;
    /** 邀请状态:0:未邀请,10:已邀请 */
    private Integer inviteStatus;
    /** 装修状态:0:未装修,10:已装修 */
    private Integer decorateStatus;
    /** 领奖状态0:未领奖,10:待领奖,20:已领奖 */
    private Integer awardsStatus;
    /** 异常状态0:正常,10:无库存; */
    private Integer exceptionStatus;
    /** 完成状态0:未完成,10:已完成; */
    private Integer completeStatus;
    /** 发货状态0:未发货,10:已发货; */
    private Integer shipmentStatus;
    /** 好友砍价金额 */
    private Integer inviteCutPriceSum;
    /** 砍价人数 */
    private Integer inviteCutRecordCount;
    /**快递公司**/
    private String carrier;
    /** 发货单号 */
    private String shipmentNo;
    /** 微信appid */
    private String appId;
    /** 创建人 */
    private String creator;
    /** 创建时间 */
    private Date gmtCreate;
    /** 修改人 */
    private String modifier;
    /** 修改时间 */
    private Date gmtModified;
    /** 是否删除：0未删除、1已删除 */
    private Integer isDeleted;
    
}
