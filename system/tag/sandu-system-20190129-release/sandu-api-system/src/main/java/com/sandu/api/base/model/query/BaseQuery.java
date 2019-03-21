package com.sandu.api.base.model.query;

import java.io.Serializable;

public class BaseQuery <T> implements Serializable{
	private static final long serialVersionUID = -8721978133590075844L;
	private int pageNo = 1; // 当前页码
	private int pageSize =20; // 页面大小，设置为“-1”表示不进行分页（分页无效）
	private String orderBy;
	private boolean isAscending=false; //是否升序
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
		if(orderBy!=null && orderBy.length()>0)
		{
			if(!isAscending)
				return orderBy+" desc";
		}
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	
}
