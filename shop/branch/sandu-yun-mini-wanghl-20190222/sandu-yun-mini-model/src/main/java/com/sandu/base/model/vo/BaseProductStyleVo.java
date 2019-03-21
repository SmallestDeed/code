package com.sandu.base.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 产品风格
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "产品风格", description = "产品风格")
public class BaseProductStyleVo extends BaseVo<BaseProductStyleVo> {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "父类Id")
	private long pid;
	@ApiModelProperty(value = "风格名称")
	private String name;

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
