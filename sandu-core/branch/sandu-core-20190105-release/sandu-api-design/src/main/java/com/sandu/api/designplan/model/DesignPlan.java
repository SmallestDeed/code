package com.sandu.api.designplan.model;


import com.sandu.api.base.common.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @version V1.0
 * @Title: DesignPlan.java
 * @Package com.sandu.design.model
 * @Description:设计模块-设计方案
 * @createAuthor pandajun
 * @CreateDate 2015-07-03 17:09:51
 */
@Data
public class DesignPlan extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer designPlanId;
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
    private Integer modelId;
    /**
     * 模型文件id
     **/
    private Integer model3dId;
    //	/**  模型文件id  **/
    private Integer modelU3dId;
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
     * 3D空间俯视图
     **/
    private String view3dPic;
    /**
     * 样板房ID
     **/
    private Integer designTemplateId;
    /**
     * 媒介类型
     **/
    private Integer mediaType;
    /**
     * 分享数量
     **/
    private Integer shareTotal;
    /**
     * 公开
     */
    private Integer isOpen;

    private Integer webModelU3dId;
    private Integer iosModelU3dId;
    private Integer androidModelU3dId;
    private Integer pcModelU3dId;
    private Integer ipadModelU3dId;
    private Integer macBookpcModelU3dId;

    /**
     * 绑定父产品ID
     **/
    private String bindParentProductId;

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

    //区分渲染图和缩略图 （0,1）
    private Integer picType;

    private String mostType;  //最多评论 、最多分享
    private Integer collectState;//收藏状态
    private Integer likeState;//点赞状态


    /**
     * 设计方案封面
     */
    private Integer coverPicId;

    /*设计方案是否发布 0 否 1是*/
    private Integer isRelease;

    /*设计方案发布时间 */
    Date releaseTime;

    /*发布权限 0无  1有*/
    private Integer releasePermissions;

    /*复制权限 0无  1有*/
    private Integer copyPermissions;

    /*多功能查询， 如果 前后带@号，搜索品牌，把推荐给该品牌的方案筛选出来，否则只是模糊查询方案名*/
    private String multifunctionQuery;

    private String brandName;

    /*方案推荐风格ID*/
    private Integer designRecommendedStyleId;

    /*720渲染图二维码路径*/
    private String qrCode720Path;

    /*特效配置*/
    private String effectsConfig;

    /*是否默认推荐  1 默认 0 非默认*/
    private Integer isRecommended;
    /*是否支持一键装修  1 支持  0不支持*/
    private Integer isDefaultDecorate;
    /*方案编号*/
    private String planNumber;
    /**
     * 设计方案副本id
     */
    private Integer designSceneId;
    /**
     * 该方案是否可见
     * 0:可见，1:不可见
     */
    private Integer visible;
    /**
     * 场景是否发生改变(时间戳)   副本/临时
     */
    private long sceneModified;
    /**
     * 设计方案类型
     * 0:普通设计方案
     * 1:一件装修设计方案(创建过一件装修组合)
     */
    private Integer planType;


    //当前页面(default=0)
    private int currentPage = 0;

    //页面大小(default=10)
    private int pageSize = 10;
    /**
     * 作用于 vendorPlanList 方法
     */
    private List<Integer> brandIds = new ArrayList<Integer>();
    /**
     * 作用于 vendorPlanList 方法
     */
    private List<Integer> userIds = new ArrayList<Integer>();

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
     * 绑定点
     **/
    private String bindPoint;
    /*临时userId:用于数据传递*/
    private Integer userIdTemp;

    private Integer spellingFlowerFileId;
    /**
     * '是否装修完成状态 (1.未装修完成   2.已装修完成)',
     **/
    private String designPlanState;

    private Integer amount;

    //设计来源编码
    private String designSourceCode;

    //设计模式
    private String designMode;
    /**
     * 主体长度
     **/
    private String mainLength;
    /**
     * 主体宽度
     **/
    private String mainWidth;
    private String planUserName;
    /**
     * 空间功能类型
     **/
    private Integer spaceFunctionId;
    //原作者id
    private Integer authorId;
    private String planStyle;
    private String spaceCode;
    private String spaceName;
    private String spaceAreas;
    private String picPath;
    private String filePath;
    private Integer templeId;
    List<RenderPicInfo> picList = null;
    private Integer commentCount;//评论数量
    private Integer likeCount;//点赞数量
    private Integer shareCount;//分享数量
    private Integer fansConut;//关注数量

    // 推荐方案ID
    private Integer recommendedPlanId;
    //0 非父方案 1 父方案
    private Integer isParent;

    private Integer attentionState;//是否关注
    private Integer planProductCount;//方案产品数量

    public String context;

    private Integer renderType;

    private Integer spaceType;
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DesignPlan other = (DesignPlan) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getPlanCode() == null ? other.getPlanCode() == null : this.getPlanCode().equals(other.getPlanCode()))
                && (this.getPlanName() == null ? other.getPlanName() == null : this.getPlanName().equals(other.getPlanName()))
                && (this.getPlanSource() == null ? other.getPlanSource() == null : this.getPlanSource().equals(other.getPlanSource()))
                && (this.getHouseId() == null ? other.getHouseId() == null : this.getHouseId().equals(other.getHouseId()))
                && (this.getLivingId() == null ? other.getLivingId() == null : this.getLivingId().equals(other.getLivingId()))
                && (this.getResidentialUnitsName() == null ? other.getResidentialUnitsName() == null : this.getResidentialUnitsName().equals(other.getResidentialUnitsName()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getDesignSourceType() == null ? other.getDesignSourceType() == null : this.getDesignSourceType().equals(other.getDesignSourceType()))
                && (this.getDesignId() == null ? other.getDesignId() == null : this.getDesignId().equals(other.getDesignId()))
                && (this.getDesignStyleId() == null ? other.getDesignStyleId() == null : this.getDesignStyleId().equals(other.getDesignStyleId()))
                && (this.getPicId() == null ? other.getPicId() == null : this.getPicId().equals(other.getPicId()))
                && (this.getModelId() == null ? other.getModelId() == null : this.getModelId().equals(other.getModelId()))
                && (this.getModel3dId() == null ? other.getModel3dId() == null : this.getModel3dId().equals(other.getModel3dId()))
//            && (this.getModelU3dId() == null ? other.getModelU3dId() == null : this.getModelU3dId().equals(other.getModelU3dId()))
                && (this.getConfigFileId() == null ? other.getConfigFileId() == null : this.getConfigFileId().equals(other.getConfigFileId()))
                && (this.getSpaceCommonId() == null ? other.getSpaceCommonId() == null : this.getSpaceCommonId().equals(other.getSpaceCommonId()))
                && (this.getPlanDesc() == null ? other.getPlanDesc() == null : this.getPlanDesc().equals(other.getPlanDesc()))
                && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlanCode() == null) ? 0 : getPlanCode().hashCode());
        result = prime * result + ((getPlanName() == null) ? 0 : getPlanName().hashCode());
        result = prime * result + ((getPlanSource() == null) ? 0 : getPlanSource().hashCode());
        result = prime * result + ((getHouseId() == null) ? 0 : getHouseId().hashCode());
        result = prime * result + ((getLivingId() == null) ? 0 : getLivingId().hashCode());
        result = prime * result + ((getResidentialUnitsName() == null) ? 0 : getResidentialUnitsName().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getDesignSourceType() == null) ? 0 : getDesignSourceType().hashCode());
        result = prime * result + ((getDesignId() == null) ? 0 : getDesignId().hashCode());
        result = prime * result + ((getDesignStyleId() == null) ? 0 : getDesignStyleId().hashCode());
        result = prime * result + ((getPicId() == null) ? 0 : getPicId().hashCode());
        result = prime * result + ((getPlanDesc() == null) ? 0 : getPlanDesc().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    /**
     * 获取对象的copy
     **/
    public DesignPlan copy() {
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
//       obj.setModelU3dId(this.modelU3dId);
        obj.setConfigFileId(this.configFileId);
        obj.setSpaceCommonId(this.spaceCommonId);
        obj.setPlanDesc(this.planDesc);
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setRemark(this.remark);

        return obj;
    }

    public DesignPlanRecommended recommendedCopy() {
        DesignPlanRecommended obj = new DesignPlanRecommended();
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
//         obj.setDesignPlanRenderPicIds(this.designPlanRenderPicIds);
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
        return obj;
    }

    /**
     * 获取对象的map
     **/
    public Map toMap() {
        Map map = new HashMap();
        map.put("planCode", this.planCode);
        map.put("planName", this.planName);
        map.put("planSource", this.planSource);
        map.put("houseId", this.houseId);
        map.put("livingId", this.livingId);
        map.put("residentialUnitsName", this.residentialUnitsName);
        map.put("userId", this.userId);
        map.put("designSourceType", this.designSourceType);
        map.put("designId", this.designId);
        map.put("designStyleId", this.designStyleId);
        map.put("picId", this.picId);
        map.put("modelId", this.modelId);
        map.put("model3dId", this.model3dId);
//       map.put("modelU3dId",this.modelU3dId);
        map.put("configFileId", this.configFileId);
        map.put("spaceCommonId", this.spaceCommonId);
        map.put("planDesc", this.planDesc);
        map.put("sysCode", this.sysCode);
        map.put("creator", this.creator);
        map.put("gmtCreate", this.gmtCreate);
        map.put("modifier", this.modifier);
        map.put("gmtModified", this.gmtModified);
        map.put("isDeleted", this.isDeleted);
        map.put("remark", this.remark);

        return map;
    }



    @Override
    public String toString() {
        return "DesignPlan{" +
                "id=" + id +
                ", designPlanId=" + designPlanId +
                ", planCode='" + planCode + '\'' +
                ", planName='" + planName + '\'' +
                ", userId=" + userId +
                ", designSourceType=" + designSourceType +
                ", designId=" + designId +
                ", designStyleId=" + designStyleId +
                ", picId=" + picId +
                ", modelId=" + modelId +
                ", model3dId=" + model3dId +
                ", modelU3dId=" + modelU3dId +
                ", configFileId=" + configFileId +
                ", spaceCommonId=" + spaceCommonId +
                ", planDesc='" + planDesc + '\'' +
                ", sysCode='" + sysCode + '\'' +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                ", isDeleted=" + isDeleted +
                ", remark='" + remark + '\'' +
                ", view3dPic='" + view3dPic + '\'' +
                ", designTemplateId=" + designTemplateId +
                ", mediaType=" + mediaType +
                ", shareTotal=" + shareTotal +
                ", isOpen=" + isOpen +
                ", webModelU3dId=" + webModelU3dId +
                ", iosModelU3dId=" + iosModelU3dId +
                ", androidModelU3dId=" + androidModelU3dId +
                ", pcModelU3dId=" + pcModelU3dId +
                ", ipadModelU3dId=" + ipadModelU3dId +
                ", macBookpcModelU3dId=" + macBookpcModelU3dId +
                ", bindParentProductId='" + bindParentProductId + '\'' +
                ", planSource='" + planSource + '\'' +
                ", residentialUnitsName='" + residentialUnitsName + '\'' +
                ", houseId=" + houseId +
                ", livingId=" + livingId +
                ", picType=" + picType +
                ", mostType='" + mostType + '\'' +
                ", collectState=" + collectState +
                ", likeState=" + likeState +
                ", coverPicId=" + coverPicId +
                ", isRelease=" + isRelease +
                ", releaseTime=" + releaseTime +
                ", releasePermissions=" + releasePermissions +
                ", copyPermissions=" + copyPermissions +
                ", multifunctionQuery='" + multifunctionQuery + '\'' +
                ", brandName='" + brandName + '\'' +
                ", designRecommendedStyleId=" + designRecommendedStyleId +
                ", qrCode720Path='" + qrCode720Path + '\'' +
                ", effectsConfig='" + effectsConfig + '\'' +
                ", isRecommended=" + isRecommended +
                ", isDefaultDecorate=" + isDefaultDecorate +
                ", planNumber='" + planNumber + '\'' +
                ", designSceneId=" + designSceneId +
                ", visible=" + visible +
                ", sceneModified=" + sceneModified +
                ", planType=" + planType +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", brandIds=" + brandIds +
                ", userIds=" + userIds +
                ", draftState=" + draftState +
                ", baiMoState=" + baiMoState +
                ", stuffFinishState=" + stuffFinishState +
                ", decorateFinishState=" + decorateFinishState +
                ", isChange=" + isChange +
                ", isDecorated=" + isDecorated +
                ", bindPoint='" + bindPoint + '\'' +
                ", userIdTemp=" + userIdTemp +
                ", designPlanState='" + designPlanState + '\'' +
                ", amount=" + amount +
                ", designSourceCode='" + designSourceCode + '\'' +
                ", designMode='" + designMode + '\'' +
                ", mainLength='" + mainLength + '\'' +
                ", mainWidth='" + mainWidth + '\'' +
                ", planUserName='" + planUserName + '\'' +
                ", spaceFunctionId=" + spaceFunctionId +
                ", authorId=" + authorId +
                ", planStyle='" + planStyle + '\'' +
                ", spaceCode='" + spaceCode + '\'' +
                ", spaceName='" + spaceName + '\'' +
                ", spaceAreas='" + spaceAreas + '\'' +
                ", picPath='" + picPath + '\'' +
                ", filePath='" + filePath + '\'' +
                ", templeId=" + templeId +
                ", picList=" + picList +
                ", commentCount=" + commentCount +
                ", likeCount=" + likeCount +
                ", shareCount=" + shareCount +
                ", fansConut=" + fansConut +
                ", recommendedPlanId=" + recommendedPlanId +
                ", isParent=" + isParent +
                ", attentionState=" + attentionState +
                ", planProductCount=" + planProductCount +
                ", context='" + context + '\'' +
                '}';
    }
}
