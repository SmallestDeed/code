package com.sandu.designer.model;


import com.sandu.base.model.DataEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 设计师作品
 * @author Administrator
 *
 */
@ApiModel(value="设计师作品",description="设计师作品")
public class DesignerWorks extends DataEntity<DesignerWorks>{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "设计师ID")
	private long designerId;
	@ApiModelProperty(value = "作品种类 1:一键方案 2:样板间 3:成功案例")
	private int worksType;
	@ApiModelProperty(value = "作品名称")
	private String worksName;
	@ApiModelProperty(value = "作品文件类别 1:图片 2:视频")
	private int worksFileType;
	@ApiModelProperty(value = "作品文件路径")
	private String worksFileUrl;
	@ApiModelProperty(value = "点赞数")
	private int likeCount;
	@ApiModelProperty(value = "作品描述")
	private String worksDesc;

	public long getDesignerId() {
		return designerId;
	}

	public void setDesignerId(long designerId) {
		this.designerId = designerId;
	}

	public int getWorksType() {
		return worksType;
	}

	public void setWorksType(int worksType) {
		this.worksType = worksType;
	}

	public String getWorksName() {
		return worksName;
	}

	public void setWorksName(String worksName) {
		this.worksName = worksName;
	}

	public int getWorksFileType() {
		return worksFileType;
	}

	public void setWorksFileType(int worksFileType) {
		this.worksFileType = worksFileType;
	}

	public String getWorksFileUrl() {
		return worksFileUrl;
	}

	public void setWorksFileUrl(String worksFileUrl) {
		this.worksFileUrl = worksFileUrl;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getWorksDesc() {
		return worksDesc;
	}

	public void setWorksDesc(String worksDesc) {
		this.worksDesc = worksDesc;
	}
}
