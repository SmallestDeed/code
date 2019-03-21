package com.nork.pano.model.output;

import com.nork.cityunion.model.vo.UnionStoreVo;
import com.nork.common.util.Utils;

import java.io.Serializable;
import java.util.List;

/**
 * 同店联盟方案分享场景信息Vo
 * Created by chenm on 2018/9/30.
 */
public class UnionStoreSingleDataVo implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**  联系人  **/
    private String contactName;
    /**  电话  **/
    private String contactPhone;
    /** 用户 id **/
    private Integer userId;
    /**用户logo**/
    private String logo;
    /** 分享标题 **/
    private String shareTitle;
    /** 用户自定义分享描述 **/
    private String desc ;
    /** 人气值 （联盟方案访问次数）popularityValue**/
    private Integer pv;
    /** 发布方案名称 **/
    private String releaseName;
    /** 企业店铺**/
    private Integer companyShopId;
    /** 控制场景中功能按钮显示的值 1,2,3,4,5**/
    private Integer viewControlType;
    /** 联盟门店ID **/
    private Integer unionGroupId;
    /** 优惠活动ID **/
    private Integer unionSpecialOfferId;
    /** 企业类型 **/
    private Integer companyTypeValue;
    /** 主图方案封面图 **/
    private String coverPicPath;
    /** 方案名称 **/
    private String planName;
    /** 场景信息 **/
    private List<PanoramaDataVo> details ;

    /**废弃的参数 begin**/
   /* *//** 公司名称 **//*
    private String companyName;
    *//** 公司LOGO **//*
    private String companyLogoPath;
    *//**  店面名称  **//*
    private String name;
    *//**  地址  **//*
    private String address;
    *//**  是否显示logo(0否/1是)  **//*
    private Integer isDisplayed;
    *//** 品牌LOGO **//*
    private String brandLogoPath;*/
    /**废弃的参数 end**/

    public String getCoverPicPath() {
        return coverPicPath;
    }

    public void setCoverPicPath(String coverPicPath) {
        this.coverPicPath = coverPicPath;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyTypeValue() {
        return companyTypeValue;
    }

    public void setCompanyTypeValue(Integer companyTypeValue) {
        this.companyTypeValue = companyTypeValue;
    }
    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getUnionGroupId() {
        return unionGroupId;
    }

    public void setUnionGroupId(Integer unionGroupId) {
        this.unionGroupId = unionGroupId;
    }

    public Integer getUnionSpecialOfferId() {
        return unionSpecialOfferId;
    }

    public void setUnionSpecialOfferId(Integer unionSpecialOfferId) {
        this.unionSpecialOfferId = unionSpecialOfferId;
    }

    public String getLogo() {
        return logo;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Integer getViewControlType() {
        return viewControlType;
    }

    public void setViewControlType(Integer viewControlType) {
        this.viewControlType = viewControlType;
    }

    public Integer getCompanyShopId() {
        return companyShopId;
    }

    public void setCompanyShopId(Integer companyShopId) {
        this.companyShopId = companyShopId;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getPopularityValue() {
        return pv;
    }

    public void setPopularityValue(Integer popularityValue) {
        this.pv = popularityValue;
    }


    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public List<PanoramaDataVo> getDetails() {
        return details;
    }

    public void setDetails(List<PanoramaDataVo> details) {
        this.details = details;
    }
}
