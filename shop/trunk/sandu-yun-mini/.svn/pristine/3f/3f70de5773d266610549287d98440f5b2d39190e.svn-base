package com.sandu.sys.model;

import com.sandu.base.model.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 产品分类
 * @author Administrator
 *
 */
@ApiModel(value = "产品分类", description = "产品分类")
public class ProductCategory extends BaseVo<ProductCategory>{
	private static final long serialVersionUID = 6278345477513873289L;

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
