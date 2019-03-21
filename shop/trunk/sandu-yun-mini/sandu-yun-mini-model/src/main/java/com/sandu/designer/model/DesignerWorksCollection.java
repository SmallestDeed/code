package com.sandu.designer.model;


import com.sandu.base.model.DataEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 设计师作品收藏信息
 * @author Administrator
 *
 */
@ApiModel(value="设计师作品收藏信息",description="设计师作品收藏信息")
public class DesignerWorksCollection extends DataEntity<DesignerWorksCollection>{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "设计师ID")
	private long designerId;
	@ApiModelProperty(value = "作品ID")
	private long worksId;
	@ApiModelProperty(value = "用户ID")
	private long userId;
	@ApiModelProperty(value = "作品名称")
	private String worksName;
	
	public long getDesignerId() {
		return designerId;
	}
	public void setDesignerId(long designerId) {
		this.designerId = designerId;
	}
	public long getWorksId() {
		return worksId;
	}
	public void setWorksId(long worksId) {
		this.worksId = worksId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getWorksName() {
		return worksName;
	}
	public void setWorksName(String worksName) {
		this.worksName = worksName;
	}
	
}

