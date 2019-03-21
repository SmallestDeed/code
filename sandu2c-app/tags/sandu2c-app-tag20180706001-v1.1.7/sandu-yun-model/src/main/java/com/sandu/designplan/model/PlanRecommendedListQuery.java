package com.sandu.designplan.model;
import java.io.Serializable;
import java.util.List;

public class PlanRecommendedListQuery implements Serializable {

    private static final long serialVersionUID = 7233294884591382648L;
    
    //用户id
    private Integer userId;
    private Integer userType;
    //每页显示多少条记录
    private int limit;
    //开始记录数
    private int start;
    //空间类型    如：客餐厅  卧室 卫生间等
    private String spaceFunctionId;
    //空间面积
    private String areaValue;
    //设计方案风格ID
    private String designRecommendedStyleId;
    
    //搜索条件:小区名称
    private String livingName;
    //搜索条件:品牌名称
    private String brandName;
    //搜索条件:创建人
    private String creator;
    //搜索条件:方案名称
    private String planName;
    
    //排序  最新(new) 最热(hot)
    private String sort;
    //显示类型
    private String displayType;
    //方案推荐类型  1分享  2一键装修
    private Integer recommendedType;
    //设计方案是否发布 0 否 1是
    private Integer isRelease;
   
    
    
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
	public String getSpaceFunctionId() {
		return spaceFunctionId;
	}
	public void setSpaceFunctionId(String spaceFunctionId) {
		this.spaceFunctionId = spaceFunctionId;
	}
	public String getAreaValue() {
		return areaValue;
	}
	public void setAreaValue(String areaValue) {
		this.areaValue = areaValue;
	}
	public String getDesignRecommendedStyleId() {
		return designRecommendedStyleId;
	}
	public void setDesignRecommendedStyleId(String designRecommendedStyleId) {
		this.designRecommendedStyleId = designRecommendedStyleId;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDisplayType() {
		return displayType;
	}
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getRecommendedType() {
		return recommendedType;
	}
	public void setRecommendedType(Integer recommendedType) {
		this.recommendedType = recommendedType;
	}
	public Integer getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}
	public String getLivingName() {
		return livingName;
	}
	public void setLivingName(String livingName) {
		this.livingName = livingName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
}
