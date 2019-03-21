package com.sandu.designplan.model;

import com.sandu.common.model.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 方案推荐
 *
 * @author Administrator
 */
@Data
public class DesignPlanRecommended extends Mapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /*方案推荐类型  1分享  2一键装修*/
    private Integer recommendedType;
    private Integer planId;
    /**
     * 方案编码
     **/
    private String planCode;
    /**
     * 方案名称
     **/
    private String planName;
    /**
     * 设计者id
     **/
    private Integer userId;
    /**
     * 方案来源类型
     **/
    private Integer designSourceType;
    /**
     * 来源id
     **/
    private Integer designId;
    /**
     * 设计风格
     **/
    private Integer designStyleId;
    /**
     * 方案图片id
     **/
    private Integer picId;
    /**
     * 模型文件id
     **/
    private Integer model3dId;
    //	/**  模型文件id  **/
    private Integer modelU3dId;
    /**
     * 模型文件id
     **/
    private Integer modelId;
    /**
     * 配置文件id
     **/
    private Integer configFileId;
    /**
     * 空间id
     **/
    private Integer spaceCommonId;
    /**
     * 方案描述
     **/
    private String planDesc;
    /**
     * 系统编码
     **/
    private String sysCode;
    /**
     * 创建者
     **/
    private String creator;
    /**
     * 创建时间
     **/
    private Date gmtCreate;
    /**
     * 修改人
     **/
    private String modifier;
    /**
     * 修改时间
     **/
    private Date gmtModified;
    /**
     * 是否删除
     **/
    private Integer isDeleted;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 设计效果图
     **/
    private String designPlanRenderPicIds;

    private Integer spaceTypeId;

    private String spaceTypeName;

    //渲染对象
    private Map<Integer, List<String>> renderMap;
    /**
     * 样板房ID
     **/
    private Integer designTemplateId;
    private Integer webModelU3dId;
    private Integer iosModelU3dId;
    private Integer androidModelU3dId;
    private Integer pcModelU3dId;
    private Integer ipadModelU3dId;
    private Integer macBookpcModelU3dId;
    /**
     * 媒介类型
     **/
    private Integer mediaType;
    /**
     * 分享数量
     **/
    private Integer shareTotal;
    /*公开*/
    private Integer isOpen;
    //草稿状态
    private Integer draftState;
    //白模状态
    private Integer baiMoState;
    //硬装完成状态
    private Integer stuffFinishState;
    //装修完成状态
    private Integer decorateFinishState;
    //方案是否变更
    private Integer isChange;
    //设计方案是否装修过 （1 装修过，0 没装修过）
    private Integer isDecorated;
    /**
     * 方案来源
     */
    private String planSource;
    /**
     * 小区户型名称
     */
    private String residentialUnitsName;
    private Integer houseId;

    private Integer livingId;
    /**
     * 设计方案封面
     */
    private Integer coverPicId;
    /*方案推荐风格ID*/
    private Integer designRecommendedStyleId;
    /*方案推荐风格名称*/
    private String designRecommendedStyleName;
    
    private Integer platformId;
    /*Y:N;only search the internal plan, The condition is not (share,deliver)*/
    private String  onlyInternalPlan;
	/*设计方案是否发布 0 否 1是*/
    private Integer isRelease;

    private List<Integer> IsReleases;

    /*设计方案发布时间 */
    private Date releaseTime;
    /*特效配置*/
    private String effectsConfig;
    /*发布权限 0无  1有*/
    private Integer releasePermissions;
    /*复制权限 0无  1有*/
    private Integer copyPermissions;
    /*多功能查询， 如果 前后带@号，搜索品牌，把推荐给该品牌的方案筛选出来，否则只是模糊查询方案名*/
    private String multifunctionQuery;
    private String brandName;
    /*720渲染图二维码路径*/
    private String qrCode720Path;
    /*是否默认推荐  1 默认 0 非默认*/
    private Integer isRecommended;
    /*是否支持一键装修  1 支持  0不支持*/
    private Integer isDefaultDecorate;
    /*方案编号*/
    private String planNumber;
    /*多点渲染图*/
    private Integer render720MultipointPicId;
    /*全景渲染图id*/
    private Integer render720PanoramaPicId;
    /*渲染视频id*/
    private Integer render720VideoId;

    private String displayType;
    //空间形状
    private Integer spaceShape;
    /**
     * 用于列表查询 begin>>
     */
    /*
	 *品牌id
	 */
    private List<Integer> BrandIds;
    /*
     *空间类型value
     */
    private Integer SpaceFunctionId;
    /*
     *多个空间类型value
     */
    List<Integer> spaceFunctionIds;
    /*
     *  面积类型value
     */
    private String areaValue;
    /*
     * 小区名
     */
    private String livingName;
    /*
     * 是否为内部用户
     */
    private String IsInternalUser;
    /**
     * 是否为方案审核管理员
     */
    private String checkAdministrator;

    private Integer picType;

    private List<RenderPicInfo> picList = new ArrayList<RenderPicInfo>();

    private String planRecommendedUserName;

    private String spaceCode;
    private String spaceName;
    private String spaceAreas;
    /*该方案推荐的产品数量*/
    private Integer planRecommendedProductCount;
    // '是否装修完成状态 (1.未装修完成 2.已装修完成)'
    private Integer recommendedDecorateState;

    private Integer designPlanId;

    private String favoriteBid;

    private String applySpaceAreas;

    private Integer isSort;
	//根据发布时间排序
    private Integer isSortByReleaseTime;
    //根据渲染次数排序
    private Integer isSortByRenderCount;
    //公司id
    private Integer companyId;

    private List<Integer> recommendedTypes;
    //装修报价类型
    private Integer decoratePriceType;
    //装修报价区间
    private Integer decoratePriceRange;

    private Integer descFileId;

    private String enterType;

    /**
     * 识别推挤方案是否是方案组,记录主方案id
     */
    private Integer groupPrimaryId;
    
    public DesignPlan recommendedCopy() {
        DesignPlan obj = new DesignPlan();
        obj.setPlanCode(this.planCode);
        obj.setPlanName(this.planName);
        obj.setPlanSource(this.planSource);
        obj.setHouseId(this.houseId);
        obj.setLivingId(this.livingId);
        obj.setResidentialUnitsName(residentialUnitsName);
        obj.setUserId(this.userId);
        obj.setDesignSourceType(this.designSourceType);
        obj.setDesignId(this.designId);
        obj.setDesignStyleId(this.designStyleId);
        obj.setPicId(this.picId);
        obj.setModelId(this.modelId);
        obj.setModel3dId(this.model3dId);
        obj.setConfigFileId(this.configFileId);
        obj.setSpaceCommonId(this.spaceCommonId);
//	         obj.setDesignPlanRenderPicIds(this.designPlanRenderPicIds);
        obj.setPlanDesc(this.planDesc);
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setRemark(this.remark);
        obj.setDesignTemplateId(this.designTemplateId);
        obj.setIpadModelU3dId(this.ipadModelU3dId);
        obj.setIosModelU3dId(this.iosModelU3dId);
        obj.setAndroidModelU3dId(this.androidModelU3dId);
        obj.setMacBookpcModelU3dId(this.macBookpcModelU3dId);
        obj.setPcModelU3dId(this.pcModelU3dId);
        obj.setWebModelU3dId(this.webModelU3dId);
        obj.setMediaType(this.mediaType);
	        /*obj.setEveningFileId(this.eveningFileId);
 			 obj.setDawnFileId(this.dawnFileId );
	         obj.setNightFileId(this.nightFileId );*/
        obj.setShareTotal(this.shareTotal);
        obj.setIsOpen(this.isOpen);
        obj.setDraftState(this.draftState);
        obj.setBaiMoState(this.baiMoState);
        obj.setStuffFinishState(this.stuffFinishState);
        obj.setDecorateFinishState(this.decorateFinishState);
        obj.setIsChange(this.isChange);
        obj.setIsDecorated(this.isDecorated);
        obj.setPlanSource(this.planSource);
        obj.setResidentialUnitsName(this.residentialUnitsName);
        obj.setHouseId(this.houseId);
        obj.setLivingId(this.livingId);
	        /*obj.setCoverPicId(this.coverPicId);*/
        obj.setEffectsConfig(effectsConfig);
        obj.setDesignFileId(this.descFileId);
        obj.setSpaceTypeId(this.spaceTypeId);
        obj.setSpaceTypeName(this.spaceTypeName);
        return obj;
    }

}
