package com.sandu.api.solution.model;

import java.util.Date;

public class CompanyShopDesignPlan {
    /**
     * 
     */
    private Long id;

    /**
     * 店铺id
     */
    private Integer shopId;

    /**
     * 方案id
     */
    private Integer planId;

    /**
     * 方案类型  1普通  2智能 3全屋
     */
    private Integer planRecommendedType;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否删除:0、未删除；1、已删除
     */
    private Integer isDeleted;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 店铺id
     * @return shop_id 店铺id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 店铺id
     * @param shopId 店铺id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 方案id
     * @return plan_id 方案id
     */
    public Integer getPlanId() {
        return planId;
    }

    /**
     * 方案id
     * @param planId 方案id
     */
    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    /**
     * 方案类型  1普通  2智能
     * @return plan_recommended_type 方案类型  1普通  2智能
     */
    public Integer getPlanRecommendedType() {
        return planRecommendedType;
    }

    /**
     * 方案类型  1普通  2智能
     * @param planRecommendedType 方案类型  1普通  2智能
     */
    public void setPlanRecommendedType(Integer planRecommendedType) {
        this.planRecommendedType = planRecommendedType;
    }

    /**
     * 创建者
     * @return creator 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     * @param creator 创建者
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 创建时间
     * @return gmt_create 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 创建时间
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 修改人
     * @return modifier 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 修改人
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 修改时间
     * @return gmt_modified 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 修改时间
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 是否删除:0、未删除；1、已删除
     * @return is_deleted 是否删除:0、未删除；1、已删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除:0、未删除；1、已删除
     * @param isDeleted 是否删除:0、未删除；1、已删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public CompanyShopDesignPlan clone(){
        CompanyShopDesignPlan companyShopDesignPlan = new CompanyShopDesignPlan();
        companyShopDesignPlan.setGmtModified((Date) this.gmtModified.clone());
        companyShopDesignPlan.setGmtCreate((Date) this.gmtCreate.clone());
        companyShopDesignPlan.setModifier(this.modifier);
        companyShopDesignPlan.setCreator(this.creator);
        companyShopDesignPlan.setShopId(this.shopId);
        companyShopDesignPlan.setIsDeleted(this.isDeleted);
        companyShopDesignPlan.setPlanId(this.planId);
        companyShopDesignPlan.setId(this.id);
        companyShopDesignPlan.setPlanRecommendedType(this.planRecommendedType);
        return companyShopDesignPlan;
    }
}