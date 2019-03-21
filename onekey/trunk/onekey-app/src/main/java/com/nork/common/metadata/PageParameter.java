package com.nork.common.metadata;

import java.util.List;

import com.google.common.collect.Lists;

/***
 * 分页查询参数
 * @author qiu.jun
 * @date 2016-05-12
 *
 */
public class PageParameter {
	private int pageSize;
	private int pageIndex;
	private List<QueryParameter> lstParameter;
	private String isInternalUser;
	
	public String getIsInternalUser() {
		return isInternalUser;
	}

	public void setIsInternalUser(String isInternalUser) {
		this.isInternalUser = isInternalUser;
	}
	public PageParameter() {
		lstParameter = Lists.newArrayList();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public List<QueryParameter> getLstParameter() {
		return lstParameter;
	}

	public void setLstParameter(List<QueryParameter> lstParameter) {
		this.lstParameter = lstParameter;
	}

}
