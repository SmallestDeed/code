package com.sandu.base.model;

import com.sandu.company.model.CompanyShop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 产品风格
 * @author Administrator
 *
 */
@ApiModel(value="产品风格",description="产品风格")
public class BaseProductStyle  extends DataEntity<BaseProductStyle>{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "父类Id")
    private long pid;
	@ApiModelProperty(value = "风格名称")
    private String name;
	@ApiModelProperty(value = "唯一标识编码")
    private String code;
	@ApiModelProperty(value = "方便搜索的编码")
    private String longCode;
	@ApiModelProperty(value = "风格对应的数字值")
    private int value;
	@ApiModelProperty(value = "节点类别 0:最底层节点 1:目录")
    private int isLeaf;
	@ApiModelProperty(value = "目录等级")
    private int level;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLongCode() {
		return longCode;
	}
	public void setLongCode(String longCode) {
		this.longCode = longCode;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}

