package com.sandu.resource.model.vo;

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
public class ResPicVo extends BaseVo<ResPicVo> {
	private static final long serialVersionUID = 8905128368999092144L;
	@ApiModelProperty(value = "图片名称")
	private String picName;
	@ApiModelProperty(value = "图片路径")
	private String picPath;
	@ApiModelProperty(value = "图片浏览次数")
	private int viewPoint;
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
	
	
}
