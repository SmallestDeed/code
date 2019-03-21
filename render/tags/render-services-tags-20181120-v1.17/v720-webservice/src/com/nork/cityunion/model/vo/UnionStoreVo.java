package com.nork.cityunion.model.vo;

import java.io.Serializable;

/**
 * 同店联盟方案相关信息
 * @author 陈名
 * 2018/1/17
 */
public class UnionStoreVo implements Serializable {
    private static final long serialVersionUID = 1L;
  

    /**门店信息id**/
    private Integer storefrontId;

    /**  店面名称  **/
    private String name;
    
    /**  联系人  **/
    private String contact;
    
    /**  电话  **/
    private String phone;
    
    /**  地址  **/
    private String address;
    
    /**  是否显示logo(0否/1是)  **/
    private Integer isDisplayed;
 
    /** 人气值 （联盟方案访问次数）**/
    private Integer popularityValue;
    
    /** 品牌LOGO **/
    private String brandLogoPath;

    /** 发布方案名称 **/
    private String releaseName;

    /** 用户Id **/
    private Integer userId;

    /** 公司名称 **/
    private String companyName;

    /** 公司LOGO **/
    private String companyLogoPath;
    /**用户logo**/
    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getStorefrontId() {
        return storefrontId;
    }

    public void setStorefrontId(Integer storefrontId) {
        this.storefrontId = storefrontId;
    }

    public String getCompanyLogoPath() {
        return companyLogoPath;
    }

    public void setCompanyLogoPath(String companyLogoPath) {
        this.companyLogoPath = companyLogoPath;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getReleaseName() {
      return releaseName;
    }

    public void setReleaseName(String releaseName) {
      this.releaseName = releaseName;
    }

    public String getContact() {
      return contact;
    }

    public void setContact(String contact) {
      this.contact = contact;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPhone() {
      return phone;
    }

    public void setPhone(String phone) {
      this.phone = phone;
    }

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public Integer getIsDisplayed() {
      return isDisplayed;
    }

    public void setIsDisplayed(Integer isDisplayed) {
      this.isDisplayed = isDisplayed;
    }

    public Integer getPopularityValue() {
      return popularityValue;
    }

    public void setPopularityValue(Integer popularityValue) {
      this.popularityValue = popularityValue;
    }

    public String getBrandLogoPath() {
      return brandLogoPath;
    }

    public void setBrandLogoPath(String brandLogoPath) {
      this.brandLogoPath = brandLogoPath;
    }

   
 
    
    
    
}
