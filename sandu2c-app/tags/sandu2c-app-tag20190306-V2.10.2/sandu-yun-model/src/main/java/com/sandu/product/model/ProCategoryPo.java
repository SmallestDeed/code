package com.sandu.product.model;

import java.io.Serializable;
import java.util.List;
/*产品分类封装对象*/
public class ProCategoryPo implements Serializable{
	private Integer id;
	private Integer pid;
	private String name;
	private Integer level;
	private String code;
	private String longCode;
	private List<ProCategoryPo> categories;
	private Integer companyMainCategory;
	private String picPath;

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public Integer getCompanyMainCategory() {
		return companyMainCategory;
	}

	public void setCompanyMainCategory(Integer companyMainCategory) {
		this.companyMainCategory = companyMainCategory;
	}

	public List<ProCategoryPo> getCategories() {
		return categories;
	}
	public void setCategories(List<ProCategoryPo> categories) {
		this.categories = categories;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLongCode() {
		return longCode;
	}
	public void setLongCode(String longCode) {
		this.longCode = longCode;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
}
