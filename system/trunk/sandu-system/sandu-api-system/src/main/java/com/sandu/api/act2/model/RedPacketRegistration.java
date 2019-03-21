package com.sandu.api.act2.model;

import java.io.Serializable;
import java.util.Date;

public class RedPacketRegistration implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 活动未开始
	 */
	public static String STATUS_CODE_ACT_UNBEGIN = "ACT_UNBEGIN";
	
	/**
	 * 活动进行中
	 */
	public static String STATUS_CODE_ACT_ONGOING= "ACT_ONGOING";
	
	/**
	 * 活动已结束
	 */
	public static String STATUS_CODE_ACT_ENDED = "ACT_ENDED";
	
	/**
	 * 未邀请
	 */
	public static Integer INVITE_STATUS_UNINVITE = 0;
	/**
	 * 已邀请
	 */
	public static Integer INVITE_STATUS_INVITED = 10;
	
	
	/**
	 * 未领奖
	 */
	public static Integer AWARDS_STATUS_UNAWRD = 0;

	/**
	 * 已领奖
	 */
	public static Integer AWARDS_STATUS_AWARDED = 20;
	
	
    private String id;

    private String actId;

    private String openId;

    private String nickname;

    private String headPic;

    private String mobile;

    private Date joinTime;

    private Integer inviteStatus;

    private Integer awardsStatus;

    private Integer awardsCount;

    private Double awardsAmountSum;

    private Integer inviteRecordCount;

    private String appId;

    private Long companyId;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(Integer inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public Integer getAwardsStatus() {
        return awardsStatus;
    }

    public void setAwardsStatus(Integer awardsStatus) {
        this.awardsStatus = awardsStatus;
    }

    public Integer getAwardsCount() {
        return awardsCount;
    }

    public void setAwardsCount(Integer awardsCount) {
        this.awardsCount = awardsCount;
    }

    public Double getAwardsAmountSum() {
        return awardsAmountSum;
    }

    public void setAwardsAmountSum(Double awardsAmountSum) {
        this.awardsAmountSum = awardsAmountSum;
    }

    public Integer getInviteRecordCount() {
        return inviteRecordCount;
    }

    public void setInviteRecordCount(Integer inviteRecordCount) {
        this.inviteRecordCount = inviteRecordCount;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}