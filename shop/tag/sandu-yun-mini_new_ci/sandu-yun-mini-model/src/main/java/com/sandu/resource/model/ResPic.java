package com.sandu.resource.model;

import com.sandu.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 图片资源
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "图片资源", description = "图片资源")
public class ResPic extends BaseVo<ResPic> {
	private static final long serialVersionUID = 8390820175670434570L;

	@ApiModelProperty(value = "图片编码")
	private String picCode;
	@ApiModelProperty(value = "图片名称")
	private String picName;
	@ApiModelProperty(value = "图片路径")
	private String picPath;
	@ApiModelProperty(value = "图片浏览次数")
	private int viewPoint;
	@ApiModelProperty(value = "业务对象ID")
	private long businessId;
	public String getPicCode() {
		return picCode;
	}
	public void setPicCode(String picCode) {
		this.picCode = picCode;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public int getViewPoint() {
		return viewPoint;
	}
	public void setViewPoint(int viewPoint) {
		this.viewPoint = viewPoint;
	}
	public long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(long businessId) {
		this.businessId = businessId;
	}
	
	
}
