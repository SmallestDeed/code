package com.nork.pano.model.scene;

import com.nork.common.util.Utils;
import com.nork.product.model.BaseBrand;
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
    
    /** 同城联盟同组选择品牌列表 **/
    private List<BaseBrand>  unionGroupBrands;

    /** 用户/店铺联系电话 **/
    private String phone;
    // 720方案来源(1:方案模块，2:店铺方案（可能方案对应几个店铺，需要传具体的店铺id），3:我的方案)
    private Integer planSource;
    // 店铺id
    private Integer shopId;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getPlanSource() {
        return planSource;
    }

    public void setPlanSource(Integer planSource) {
        this.planSource = planSource;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<BaseBrand> getUnionGroupBrands() {
      return unionGroupBrands;
    }

    public void setUnionGroupBrands(List<BaseBrand> unionGroupBrands) {
      this.unionGroupBrands = unionGroupBrands;
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
