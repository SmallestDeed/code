package com.sandu.designplan.model;

import java.io.Serializable;

/**
 * 设计方案推荐
 */
public class DesignPlanRecommenInput implements Serializable {

    private static final long serialVersionUID = -2513029690720630891L;
    //空间类型    如：客餐厅  卧室 卫生间等
    private Integer spaceType;
    //设计方案风格ID
    private String designPlanStyleId;
    //空间面积
    private String spaceArea;
    //显示类型
    private String displayType;
    //排序  最新(new) 最热(hot)
    private String sort;
    
    //搜索条件:小区名称
    private String livingName;
    //搜索条件:方案名称
    private String planName;
    
    // 当前页码
    private int curPage;
    // 每页显示多少条记录
    private int pageSize;
    
	public Integer getSpaceType() {
		return spaceType;
	}
	public void setSpaceType(Integer spaceType) {
		this.spaceType = spaceType;
	}
	public String getDesignPlanStyleId() {
		return designPlanStyleId;
	}
	public void setDesignPlanStyleId(String designPlanStyleId) {
		this.designPlanStyleId = designPlanStyleId;
	}
	public String getSpaceArea() {
		return spaceArea;
	}
	public void setSpaceArea(String spaceArea) {
		this.spaceArea = spaceArea;
	}
	public String getDisplayType() {
		return displayType;
	}
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getLivingName() {
		return livingName;
	}
	public void setLivingName(String livingName) {
		this.livingName = livingName;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
}
