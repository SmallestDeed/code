package com.sandu.im.common.vo;

import java.util.List;

public class PageResultVo {

	private List list;
	private long totalCount;
	
	public PageResultVo(){
		
	}
	
	public PageResultVo(List list, long totalCount) {
		super();
		this.list = list;
		this.totalCount = totalCount;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
}
