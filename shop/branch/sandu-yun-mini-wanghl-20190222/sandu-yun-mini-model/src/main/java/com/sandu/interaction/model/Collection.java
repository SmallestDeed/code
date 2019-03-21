package com.sandu.interaction.model;


import com.sandu.base.model.DataEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 收藏信息
 * @author Administrator
 *
 */
@ApiModel(value="收藏信息",description="收藏信息")
public class Collection extends DataEntity<Collection>{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "收藏类别")
    private int type;
	@ApiModelProperty(value = "收藏对象ID")
	private long objId;
	@ApiModelProperty(value = "用户ID")
	private long userId;
	@ApiModelProperty(value = "收藏对象名称")
	private String objName;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getObjId() {
		return objId;
	}
	public void setObjId(long objId) {
		this.objId = objId;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
}

