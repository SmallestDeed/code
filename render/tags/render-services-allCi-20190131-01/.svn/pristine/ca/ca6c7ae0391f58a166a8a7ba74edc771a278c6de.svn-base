package com.nork.productprops.model;

import java.io.Serializable;

/**
 * 属性(simple)
 * @author huangsongbo
 *
 */
public class ProductPropsSimple implements Serializable{

	private static final long serialVersionUID = 1L;

	/**排序属性:0;过滤属性:1*/
	private Integer isSort;
	
	/**父级code*/
	private String key;
	
	/**父级属性序号(决定属性排序)*/
	private Integer sortValue;
	
	/**该级属性值(key+value用来匹配)*/
	private Integer value;
	
	public ProductPropsSimple() {
		super();
	}

	public ProductPropsSimple(Integer isSort, String key, Integer sortValue, Integer value) {
		super();
		this.isSort = isSort;
		this.key = key;
		this.sortValue = sortValue;
		this.value = value;
	}

	public Integer getIsSort() {
		return isSort;
	}

	public void setIsSort(Integer isSort) {
		this.isSort = isSort;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getSortValue() {
		return sortValue;
	}

	public void setSortValue(Integer sortValue) {
		this.sortValue = sortValue;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
