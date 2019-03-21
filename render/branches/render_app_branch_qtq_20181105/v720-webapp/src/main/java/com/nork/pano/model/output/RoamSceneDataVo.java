package com.nork.pano.model.output;

import com.nork.common.util.Utils;

import java.io.Serializable;
import java.util.List;

/**
 * 全景图漫游VO
 * @author  chenm
 * @Date 2018.09.28
 */
public class RoamSceneDataVo implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;
    /** 用户头像 **/
    private String logo;
    /** 渲染用户ID **/
    private Integer userId;
    /**  联系人  **/
    private String contactName;
    /**电话号码**/
    private String contactPhone;
    /** 公司名称 **/
    private String companyName;
    /** 公司LOGO **/
    private String companyLogoPath;
    /** 企业店铺**/
    private Integer companyShopId;
    /** 控制场景中功能按钮显示的值 1,2,3,4,5**/
    private Integer viewControlType;
    /** 访问量**/
    private Integer pv;
    /** 用户自定义分享文案(描述) **/
 /*   private String title;*/
    /** 场景内显示标题**/
    private String shareTitle;
    /** 用户自定义分享描述  **/
    private String desc ;
    /** 场景信息 **/
    private List<PanoramaDataVo> details ;


    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
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

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogoPath() {
        return companyLogoPath;
    }

    public void setCompanyLogoPath(String companyLogoPath) {
        this.companyLogoPath = companyLogoPath;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<PanoramaDataVo> getDetails() {
        return details;
    }

    public void setDetails(List<PanoramaDataVo> details) {
        this.details = details;
    }

}
