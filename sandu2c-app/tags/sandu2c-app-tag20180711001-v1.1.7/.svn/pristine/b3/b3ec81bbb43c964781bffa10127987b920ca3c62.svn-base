package com.sandu.pano.model.scene;

import com.sandu.common.util.Utils;
import com.sandu.system.model.SysDictionaryBo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
@Data
public class PanoramaVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 设计方案ID
     **/
    private Integer planId;
    /**
     * 三度logo
     **/
    private String logoPath;
    /**
     * 渲染用户ID
     **/
    private Integer userId;
    /**
     * 渲染用户昵称
     **/
    private String userName;
    /**
     * 渲染用户类型
     **/
    private Integer userType;
    /**
     * 公司名称
     **/
    private String companyName;
    /**
     * 公司LOGO
     **/
    private String companyLogoPath;
    /**
     * 房型类型
     **/
    private String houseTypeName;
    /**
     * 标题
     **/
    private String title;
    /**
     * 描述
     **/
    private String desc = Utils.getPropertyName("config/share", "wx.share.desc", "我在三度云享家分享了一款装修设计，点击查看全景漫游");
    /**
     * 缩略图url
     **/
    private String thumbUrl;
    /**
     * 窗口占比
     **/
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
     */
    private Integer oneKeyDesignPlanId;
    
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
}
