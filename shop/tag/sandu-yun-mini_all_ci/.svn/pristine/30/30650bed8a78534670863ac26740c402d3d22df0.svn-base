package com.sandu.interaction.model.query;

import java.math.BigInteger;

import com.sandu.base.model.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 点赞查询条件
 * @author Administrator
 *
 */
@ApiModel(value = "点赞查询条件", description = "点赞查询条件")
public class LikeQuery extends BaseQuery<LikeQuery>{
	private static final long serialVersionUID = -321721345530697147L;
	@ApiModelProperty(value = "点赞类别")
	private int likeType;
	@ApiModelProperty(value = "被点赞的对象ID")
	private BigInteger objId;
	@ApiModelProperty(value = "用户ID")
	private BigInteger userId;
	public int getLikeType() {
		return likeType;
	}
	public BigInteger getObjId() {
		return objId;
	}
	public void setObjId(BigInteger objId) {
		this.objId = objId;
	}
	public void setLikeType(int likeType) {
		this.likeType = likeType;
	}
	public BigInteger getUserId() {
		return userId;
	}
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
}
