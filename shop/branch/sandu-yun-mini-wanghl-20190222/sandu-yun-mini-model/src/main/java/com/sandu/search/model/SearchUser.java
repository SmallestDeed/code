package com.sandu.search.model;

import com.sandu.base.model.DataEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/****
 * 用户历史搜索
 * @author Administrator
 *
 */
@ApiModel(value = "用户历史搜索", description = "用户历史搜索")
public class SearchUser extends DataEntity<SearchUser>{
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
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
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
	public int getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}

}
