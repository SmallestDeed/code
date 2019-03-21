package com.sandu.interaction.model.vo;

import com.sandu.base.model.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 评论信息
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "评论信息", description = "评论信息")
public class CommentVo extends BaseVo<CommentVo> {
	private static final long serialVersionUID = 5661824126834956021L;

	@ApiModelProperty(value = "评论对象ID")
	private long objId;
	@ApiModelProperty(value = "评论对象名称")
	private String objName;
	@ApiModelProperty(value = "评分")
	private int score;
	@ApiModelProperty(value = "评论内容")
	private String commentContent;
	@ApiModelProperty(value = "评论标签ID集合")
	private String tagIds;
	@ApiModelProperty(value = "评论用户ID")
	private long userId;
	@ApiModelProperty(value = "评论用户名")
	private String userName;
	@ApiModelProperty(value = "评论者头像")
	private String headerUrl;

	public long getObjId() {
		return objId;
	}

	public void setObjId(long objId) {
		this.objId = objId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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

	public String getHeaderUrl() {
		return headerUrl;
	}

	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}
}
