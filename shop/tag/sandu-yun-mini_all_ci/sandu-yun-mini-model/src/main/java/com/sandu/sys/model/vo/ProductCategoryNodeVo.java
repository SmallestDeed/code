package com.sandu.sys.model.vo;

import java.util.List;

import com.sandu.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 产品分类节点信息
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "产品分类节点信息", description = "产品分类节点信息")
public class ProductCategoryNodeVo extends BaseVo<ProductCategoryNodeVo> {

	private static final long serialVersionUID = 4197704670345873473L;
	@ApiModelProperty(value = "子级分类")
	private List<ProductCategoryVo> childs;
	@ApiModelProperty(value = "分类名称")
	private String name;
	@ApiModelProperty(value = "父级ID")
	private long pid;
	@ApiModelProperty(value = "分类级别")
	private int level;

	public List<ProductCategoryVo> getChilds() {
		return childs;
	}

	public void setChilds(List<ProductCategoryVo> childs) {
		this.childs = childs;
	}

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
