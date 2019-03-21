package com.nork.pano.model.scene;

import com.nork.common.util.Utils;
import com.nork.system.model.bo.SysDictionaryBo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
public class PanoramaVo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 设计方案ID **/
    private Integer planId;
    /** 三度logo **/
    private String logoPath;
    /** 渲染用户ID **/
    private Integer userId;
    /** 渲染用户昵称 **/
    private String userName;
    /** 渲染用户类型 **/
    private Integer userType;
    /** 公司名称 **/
    private String companyName;
    /** 公司LOGO **/
    private String companyLogoPath;
    /** 房型类型 **/
    private String houseTypeName;
    /** 标题 **/
    private String title;
    /** 描述 **/
    private String desc = Utils.getPropertyName("config/share","wx.share.desc","我在三度云享家分享了一款装修设计，点击查看全景漫游");
    /** 缩略图url **/
    private String thumbUrl;
    /** 窗口占比 **/
    private String windowsPercent;
    /**
     * 副本id
     */
    private Integer designPlanRenderSceneId;
    
    /**
     * 推荐方案id
     */
    private Integer designPlanRecommendedId;
    /**
     * 自动渲染得到的方案的id
     * @return
     */
    private Integer oneKeyDesignPlanId;
    
    /** 当前场景图id **/
    private Integer renderPicId;

    /**    异业联盟品牌ID集合*/
    private List<Integer> brandList;
    //平台id
    private Integer platformId;
    //公司id
    private Integer companyId;
    //可见产品范围大小类
    private List<SysDictionaryBo> sysDictionaryBOList;
    //本公司品牌id集合
    private List<Integer> ownBrandIdList;
    //是否三度公司 0:不是, 1:是
    private Integer isSandu = 0;

    private String platformCode;

    private String platformBussinessType;//平台业务类型
    // 720方案来源(1:方案模块，2:店铺方案（可能方案对应几个店铺，需要传具体的店铺id），3:我的方案)
    private Integer planSource;
    // 店铺id(店铺方案的时候需使用店铺id查询店铺相关信息)
    private Integer shopId;

    /** 用户/店铺联系电话 **/
    private String phone;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPlanSource() {
        return planSource;
    }

    public void setPlanSource(Integer planSource) {
        this.planSource = planSource;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public List<Integer> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Integer> brandList) {
        this.brandList = brandList;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<SysDictionaryBo> getSysDictionaryBOList() {
        return sysDictionaryBOList;
    }

    public void setSysDictionaryBOList(List<SysDictionaryBo> sysDictionaryBOList) {
        this.sysDictionaryBOList = sysDictionaryBOList;
    }

    public List<Integer> getOwnBrandIdList() {
        return ownBrandIdList;
    }

    public void setOwnBrandIdList(List<Integer> ownBrandIdList) {
        this.ownBrandIdList = ownBrandIdList;
    }

    public Integer getIsSandu() {
        return isSandu;
    }

    public void setIsSandu(Integer isSandu) {
        this.isSandu = isSandu;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getPlatformBussinessType() {
        return platformBussinessType;
    }

    public void setPlatformBussinessType(String platformBussinessType) {
        this.platformBussinessType = platformBussinessType;
    }

    public Integer getRenderPicId() {
      return renderPicId;
    }

    public void setRenderPicId(Integer renderPicId) {
      this.renderPicId = renderPicId;
    }

    public Integer getOneKeyDesignPlanId() {
		return oneKeyDesignPlanId;
	}

	public void setOneKeyDesignPlanId(Integer oneKeyDesignPlanId) {
		this.oneKeyDesignPlanId = oneKeyDesignPlanId;
	}

	public Integer getDesignPlanRenderSceneId() {
		return designPlanRenderSceneId;
	}

	public void setDesignPlanRenderSceneId(Integer designPlanRenderSceneId) {
		this.designPlanRenderSceneId = designPlanRenderSceneId;
	}

	public Integer getDesignPlanRecommendedId() {
		return designPlanRecommendedId;
	}

	public void setDesignPlanRecommendedId(Integer designPlanRecommendedId) {
		this.designPlanRecommendedId = designPlanRecommendedId;
	}

	public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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

    public String getHouseTypeName() {
        return houseTypeName;
    }

    public void setHouseTypeName(String houseTypeName) {
        this.houseTypeName = houseTypeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getWindowsPercent() {
        return windowsPercent;
    }

    public void setWindowsPercent(String windowsPercent) {
        this.windowsPercent = windowsPercent;
    }
}
