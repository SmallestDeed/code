package com.sandu.sys.model;

import com.sandu.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 数据字典
 * @author Administrator
 *
 */
@ApiModel(value="数据字典",description="数据字典")
public class SysDictionary extends DataEntity<SysDictionary>{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "类型")
	private String type;
	@ApiModelProperty(value = "唯一标示Key")
    private String valuekey;
	@ApiModelProperty(value = "值")
    private int value;
	@ApiModelProperty(value = "名称")
    private String name;
	@ApiModelProperty(value = "排序")
    private int ordering;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValuekey() {
		return valuekey;
	}
	public void setValuekey(String valuekey) {
		this.valuekey = valuekey;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
}

