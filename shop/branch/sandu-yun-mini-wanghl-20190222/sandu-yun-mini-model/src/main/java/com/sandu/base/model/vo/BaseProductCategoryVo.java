package com.sandu.base.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 产品分类输出
 * @author Administrator
 *
 */
@ApiModel(value = "产品分类输出", description = "产品分类输出")
public class BaseProductCategoryVo extends BaseVo<BaseProductCategoryVo>{
	private static final long serialVersionUID = -1657707028053319609L;
	@ApiModelProperty(value = "分类名称")
	private String name;
	@ApiModelProperty(value = "父级ID")
	private long pid;
	@ApiModelProperty(value = "分类级别")
	private int level;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
