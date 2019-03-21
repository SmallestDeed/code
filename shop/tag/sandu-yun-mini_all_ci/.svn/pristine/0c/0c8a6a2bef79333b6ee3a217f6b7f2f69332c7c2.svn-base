package com.sandu.base.model.query;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/***
 * 查询实体基类
 * @author Administrator
 *
 * @param <T>
 */
public class BaseQuery <T> implements Serializable{
	private static final long serialVersionUID = -8721978133590075844L;
	private int pageNo = 1; // 当前页码
	private int pageSize =20; // 页面大小，设置为“-1”表示不进行分页（分页无效）
	private int start=0;
	private String orderBy;
	/***
	 * 排序方式  0:升序 1：降序
	 */
	@ApiModelProperty(value = "排序方式 0:升序 1：降序")
	private int sortType=1;
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getStart() {
		int index=pageNo-1;
		if(index<0)
			index=0;
		start=index*pageSize;
		return start;
	}
 
	
	public int getSortType() {
		return sortType;
	}
	public void setSortType(int sortType) {
		this.sortType = sortType;
	}

	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final int DEL_FLAG_NORMAL = 0;
	public static final int DEL_FLAG_DELETE = 1;
	public static final int DEL_FLAG_AUDIT = 2;
	
}
