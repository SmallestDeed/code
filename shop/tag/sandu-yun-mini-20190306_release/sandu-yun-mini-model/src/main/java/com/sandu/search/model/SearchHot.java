package com.sandu.search.model;

import com.sandu.base.model.DataEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 热门搜索
 * @author Administrator
 *
 */
@ApiModel(value = "热门搜索", description = "热门搜索")
public class SearchHot extends DataEntity<SearchHot>{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "一级分类ID")
	private int firstCategoryId;
	@ApiModelProperty(value = "二级分类ID")
	private int categoryId;
	@ApiModelProperty(value = "关键词")
	private String searchKey;
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
	public int getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
	
}
