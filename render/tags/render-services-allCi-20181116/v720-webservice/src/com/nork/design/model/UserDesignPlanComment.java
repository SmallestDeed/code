package com.nork.design.model;

import java.io.Serializable;
import java.util.Date;

public class UserDesignPlanComment implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;
	/** 用户id **/
	private Integer userId;
	/** 方案设计id **/
	private Integer designPlanId;

	public Integer getDesignPlanId() {
		return designPlanId;
	}

	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
	}

	// 头像
	private Integer pic;

	private Integer id;
	/** 评论内容 **/
	private String content;
	/** 讨论方案 **/
	private String discussionprogram;
	/** 时间 **/
	/** 创建时间 **/
	private Date gmtCreate;

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	private String date;
	private String tomorrow;

	private String picPath;

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getTomorrow() {
		return tomorrow;
	}

	public void setTomorrow(String tomorrow) {
		this.tomorrow = tomorrow;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPic() {
		return pic;
	}

	public void setPic(Integer pic) {
		this.pic = pic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDiscussionprogram() {
		return discussionprogram;
	}

	public void setDiscussionprogram(String discussionprogram) {
		this.discussionprogram = discussionprogram;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
