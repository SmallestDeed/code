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
@ApiModel(value = "收藏查询条件", description = "收藏查询条件")
public class CollectionQuery extends BaseQuery<CollectionQuery>{
	private static final long serialVersionUID = 2852475165913487442L;
	@ApiModelProperty(value = "收藏类别")
    private int type;
	@ApiModelProperty(value = "收藏对象ID")
	private BigInteger objId;
	@ApiModelProperty(value = "用户ID")
	private BigInteger userId;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
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
	
	
}
