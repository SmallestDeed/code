package com.sandu.sys.model.query;

import com.sandu.base.model.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/***
 * 数据字典查询条件
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "数据字典查询条件", description = "数据字典查询条件")
public class SysDictionaryQuery extends BaseQuery<SysDictionaryQuery>{
	private static final long serialVersionUID = 4321323858563205776L;
	@ApiModelProperty(value = "类型")
	private String type;
	@ApiModelProperty(value = "值")
	private Integer value;
	@ApiModelProperty(value = "集合值")
	private List<Integer> valueList;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public List<Integer> getValueList() {
		return valueList;
	}

	public void setValueList(List<Integer> valueList) {
		this.valueList = valueList;
	}
}
