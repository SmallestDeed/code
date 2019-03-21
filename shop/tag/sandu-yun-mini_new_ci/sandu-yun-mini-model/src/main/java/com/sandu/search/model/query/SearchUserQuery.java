package com.sandu.search.model.query;

import com.sandu.base.model.query.BaseQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="热门搜索",description="热门搜索")
public class SearchUserQuery extends BaseQuery<SearchUserQuery>{

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "用户ID")
	private long userId;
	@ApiModelProperty(value = "取排序中的前几条")
	private int topN;
	@ApiModelProperty(value = "搜索关键词")
	private String searchKey;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getTopN() {
		return topN;
	}
	public void setTopN(int topN) {
		this.topN = topN;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	
}
