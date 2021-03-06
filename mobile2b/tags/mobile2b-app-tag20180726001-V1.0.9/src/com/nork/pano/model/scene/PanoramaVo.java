package com.nork.pano.model.scene;

import com.nork.common.model.LoginUser;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.design.model.ProductsCostType;
import com.nork.system.model.SysUser;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
public class PanoramaVo {
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
    /**平台id*/
    private Integer platformId;

    //
    private LoginUser loginUser;

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
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

    public LoginUser getLoginUser()
    {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser)
    {
        this.loginUser = loginUser;
    }
}
