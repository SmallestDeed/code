package com.sandu.design.model.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class DesignPlanRecommendedPO implements Serializable {

	private static final long serialVersionUID = -89764894032273934L;

	private Integer id;
	
	/**
	 * 方案推荐类型  1分享  2一键装修
	 */
	private Integer recommendedType;
	
	private Integer planId;
	
	/**
	 * 方案编码
	 */
	private String planCode;
	
	/**
	 * 方案名称
	 */
	private String planName;
	
	/**
	 * 设计者id
	 */
	private Integer userId;
	
	/**
	 * 方案来源类型
	 */
	private Integer designSourceType;
	
	/**
	 * 来源id
	 */
	private Integer designId;
	
	/**
	 * 设计风格
	 */
	private Integer designStyleId;
	
	/**
	 * 方案图片id
	 */
	private Integer picId;
	
	/**
	 * 模型文件id
	 */
	private Integer model3dId;
	
	/**
	 * 模型文件id
	 */
	private Integer modelU3dId;
	
	/**
	 * 模型文件id
	 */
	private Integer modelId;
	
	/**
	 * 配置文件id
	 */
	private Integer configFileId;
	
	/**
	 * 空间id
	 */
	private Integer spaceCommonId;
	
	/**
	 * 方案描述
	 */
	private String planDesc;
	
	/**
	 * 系统编码
	 */
	private String sysCode;
	
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
	 * 是否删除
	 */
	private Integer isDeleted;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 设计效果图
	 */
	private String designPlanRenderPicIds;
	
	/**
	 * 样板房ID
	 */
	private Integer designTemplateId;
	
	private Integer ipadModelU3dId;
	
	private Integer iosModelU3dId;
	
	private Integer androidModelU3dId;
	
	private Integer macBookpcModelU3dId;
	
	private Integer pcModelU3dId;
	
	private Integer webModelU3dId;
	
	/**
	 * 媒介类型
	 */
	private Integer mediaType;
	
	private Integer eveningFileId;
	
	private Integer dawnFileId;
	
	private Integer nightFileId;
	
	/**
	 * 分享数量
	 */
	private Integer shareTotal;
	
	/**
	 * 公开
	 */
	private Integer isOpen;
	
	/**
	 * 草稿状态
	 */
	private Integer draftState;
	
	/**
	 * 白模状态
	 */
	private Integer baiMoState;
	
	/**
	 * 硬装完成状态
	 */
	private Integer stuffFinishState;
	
	/**
	 * 装修完成状态
	 */
	private Integer decorateFinishState;
	
	/**
	 * 方案是否变更
	 */
	private Integer isChange;
	
	/**
	 * 设计方案是否装修过 （1 装修过，0 没装修过）
	 */
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
	
	/**
	 * 方案推荐风格ID
	 */
	private Integer designRecommendedStyleId;
	
	/**
	 * 设计方案是否发布 0 否 1是
	 */
	private Integer isRelease;
	
	/**
	 * 设计方案发布时间
	 */
	private Date releaseTime;
	
	/**
	 * 特效配置
	 */
	private String effectsConfig;
	
	/**
	 * 是否默认推荐  1 默认 0 非默认
	 */
	private Integer isRecommended;
	
	/**
	 * 方案编号
	 */
	private String  planNumber;
	
	/**
	 * 多点渲染图
	 */
	private Integer render720MultipointPicId; 
	
	/**
	 * 全景渲染图id
	 */
	private Integer render720PanoramaPicId; 
	
	/**
	 * 渲染视频id
	 */
	private Integer render720VideoId;
	
	/**
	 * 适用于空间面积（一键装修）
	 */
	private String applySpaceAreas;
	
	/**
	 * 整理推荐封面照片大的问题，备份cover_pic_id
	 */
	private Integer bakCoverPicId;
	
	/**
	 * 拼花 json 传
	 */
	private Integer spellingFlowerFileId;
 	/**
 	 * 拼花  产品ids 
 	 */
	private String spellingFlowerProduct;
	
	/**
	 * 空间布局类型
	 */
	private String spaceLayoutType;
	
	/**
	 * 是否包含公开产品
	 */
	private String containsNotOpenProduct;
	
	/**
	 * 商家后台上下架状态 ONEKEY 一键方案   OPEN 公开方案  多个用 , 隔开 ONEKEY,OPEN
	 */
	private String shelfStatus;
	
	/**
	 * 是否包含保密产品：0否，1是
	 */
	private Integer containsSecrecyFlag;
	
	/**
	 * 关联厂商id
	 */
	private Integer companyId;

}
