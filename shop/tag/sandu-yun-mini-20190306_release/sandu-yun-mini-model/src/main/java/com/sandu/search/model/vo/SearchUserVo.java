package com.sandu.search.model.vo;

import com.sandu.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户历史搜索", description = "用户历史搜索")
public class SearchUserVo extends BaseVo<SearchUserVo> {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "一级分类ID")
	private int firstCategoryId;
	@ApiModelProperty(value = "二级分类ID")
	private int categoryId;
	@ApiModelProperty(value = "关键词")
	private String searchKey;
	@ApiModelProperty(value = "用户ID")
	private long userId;
	@ApiModelProperty(value = "搜索次数")
	private int searchCount;
	
	public int getFirstCategoryId() {
		return firstCategoryId;
	}
	public void setFirstCategoryId(int firstCategoryId) {
		this.firstCategoryId = firstCategoryId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
	
	
}
