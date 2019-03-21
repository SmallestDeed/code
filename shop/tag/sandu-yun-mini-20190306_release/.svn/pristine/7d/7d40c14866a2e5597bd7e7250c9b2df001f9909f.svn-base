package com.sandu.designer.model;

import com.sandu.base.model.DataEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 设计师评论信息
 * @author Administrator
 *
 */
@ApiModel(value="认证信息",description="企业/设计师认证信息")
public class DesignerComment extends DataEntity<DesignerComment>{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "设计师ID")
	private long designerId;
	@ApiModelProperty(value = "评分")
	private int score;
	@ApiModelProperty(value = "评论内容")
	private String commentContent;
	@ApiModelProperty(value = "标签ID集合")
	private String tagIds;
	@ApiModelProperty(value = "评论人ID")
	private long userId;
	@ApiModelProperty(value = "评论人账号")
	private String userName;
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public long getDesignerId() {
		return designerId;
	}
	public void setDesignerId(long designerId) {
		this.designerId = designerId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getTagIds() {
		return tagIds;
	}
	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}

