package com.nork.design.model.input;

import com.nork.common.model.Mapper;

import java.io.Serializable;

/**
 * 推荐方案查询列表条件
 *
 * @author xiaoxc
 * @data 2018-06-05
 */
public class DesignPlanRecommendedQuery extends Mapper implements Serializable {
    private static final long serialVersionUID = 7843084305986565260L;

    //企业ID
    private Long companyId;
    //方案ID
    private String planRecommendedId;
    //户型类型
    private String houseType;
    //小区名称
    private String livingName;
    //空间面积
    private String areaValue;
    //方案风格
    private String designRecommendedStyleId;

    private String designRecommendedStyleIdTop;
    //推荐方案类型
    private String displayType;
    //方案名称
    private String planName;
    //创建者
    private String creator;
    //品牌名称
    private String brandName;
    //推荐方案审核状态
    private String planCheckState;
    //空间布局类型
    private String spaceLayoutType;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getPlanRecommendedId() {
        return planRecommendedId;
    }

    public void setPlanRecommendedId(String planRecommendedId) {
        this.planRecommendedId = planRecommendedId;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getLivingName() {
        return livingName;
    }

    public void setLivingName(String livingName) {
        this.livingName = livingName;
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

    public String getDesignRecommendedStyleIdTop() {
        return designRecommendedStyleIdTop;
    }

    public void setDesignRecommendedStyleIdTop(String designRecommendedStyleIdTop) {
        this.designRecommendedStyleIdTop = designRecommendedStyleIdTop;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPlanCheckState() {
        return planCheckState;
    }

    public void setPlanCheckState(String planCheckState) {
        this.planCheckState = planCheckState;
    }

    public String getSpaceLayoutType() {
        return spaceLayoutType;
    }

    public void setSpaceLayoutType(String spaceLayoutType) {
        this.spaceLayoutType = spaceLayoutType;
    }
}
