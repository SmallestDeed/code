package com.sandu.interaction.model;

import com.sandu.base.model.DataEntity;
import com.sandu.company.model.CompanyShopActivity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 点赞信息
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "点赞信息", description = "点赞信息")
public class Like extends DataEntity<Like> {
	private static final long serialVersionUID = -6643201945494159981L;
	@ApiModelProperty(value = "点赞类别")
	private int likeType;
	@ApiModelProperty(value = "被点赞的对象ID")
	private long objId;
	@ApiModelProperty(value = "用户ID")
	private long userId;
	@ApiModelProperty(value = "用户名")
	private String userName;
	@ApiModelProperty(value = "点赞状态(0:取消 1:点赞)")
	private int status;
	@ApiModelProperty(value = "点赞备注")
	private String remark;

	public int getLikeType() {
		return likeType;
	}

	public void setLikeType(int likeType) {
		this.likeType = likeType;
	}

	public long getObjId() {
		return objId;
	}

	public void setObjId(long objId) {
		this.objId = objId;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
