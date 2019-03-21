package com.sandu.api.company.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BaseCompanyMiniProgramTemplateMsg implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 渲染消息
	 */
	public static final Integer TEMPLATE_TYPE_RENDER=1;
	/**
	 * 砍价任务完成消息
	 */
	public static final Integer TEMPLATE_TYPE_ACT_BARGAIN_REG_COMPLETE=2;
	
	/**
	 * 砍价进度消息
	 */
	public static final Integer TEMPLATE_TYPE_ACT_BARGAIN_REG_PROGRESS=3;

	/**
	 * 申请户型处理通知
	 */
	public static final Integer TEMPLATE_TYPE_APPLY_BASE_HOUSE_ADVICE=4;
	
	
	/**
	 * 通知领取红包消息
	 */
	public static final Integer TEMPLATE_TYPE_ACT_RED_PACKET_RECEIVE=5;

	/**
	 * 通知转盘可抽奖消息
	 */
	public static final Integer TEMPLATE_TYPE_ACT_WHEEL_LOTTERY_RECEIVE=7;

	/**
	 * 通知有可领取的任务消息
	 */
	public static final Integer TEMPLATE_TYPE_ACT_HAVE_RECEIVED_TASK_RECEIVE=8;

	/**
	 * 通知有领取的卡片消息
	 */
	public static final Integer TEMPLATE_TYPE_ACT_HAVE_RECEIVED_CARD_RECEIVE=9;

	/**
	 * 通知邀请好友成功获得拼图的消息
	 */
	public static final Integer TEMPLATE_TYPE_ACT_GIVE_ME_FIVE_SUCCESS_RECEIVE=10;
	
	
	private Long id;
	/**
	 * 企业微信appid
	 */
	private String appid; 
	/**
	 * 模板类型(1:渲染模板消息,2:...)
	 */
	private Integer templateType;
	
	/**
	 * 微信小程序后台配置的模板id
	 */
	private String templateId;
	
	/**
	 * 跳转页面
	 */
	private String page;
	
	private String creator;
	
	private Date gmtCreate;
	
	private String modifier;
	
	private Date gmtModified;
	
	private Integer isDeleted;

}
