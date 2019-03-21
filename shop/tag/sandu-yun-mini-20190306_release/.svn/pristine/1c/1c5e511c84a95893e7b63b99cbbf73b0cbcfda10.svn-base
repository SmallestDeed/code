package com.sandu.interaction.model.query;

import java.math.BigInteger;

import com.sandu.base.model.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 评论查询条件
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "评论查询条件", description = "评论查询条件")
public class CommentQuery extends BaseQuery<CommentQuery> {
	private static final long serialVersionUID = 1896768780564608090L;
	@ApiModelProperty(value = "评论类别")
	private int type;
	@ApiModelProperty(value = "评论对象ID")
	private BigInteger objId;
	@ApiModelProperty(value = "评论用户ID")
	private BigInteger userId;
	
	public BigInteger getObjId() {
		return objId;
	}

	public void setObjId(BigInteger objId) {
		this.objId = objId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
