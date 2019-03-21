package com.sandu.design.model.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class DesignPlanPO implements Serializable {

	private static final long serialVersionUID = 1273692218319373105L;

	private Integer id;
	
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
	private Integer modelId;
	
	/**
	 * 模型文件id
	 */
	private Integer model3dId;
	
	/**
	 * 模型文件id
	 */
	private Integer modelU3dId;
	
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
	 * 设计方案是否发布 0 否 1是
	 */
	private Integer isRelease;
	
	/**
	 * 方案推荐风格ID
	 */
	private Integer designRecommendedStyleId;
	
	/**
	 * 设计方案发布时间
	 */
	private Date releaseTime;
	
	/**
	 * 特效配置
	 */
	private String effectsConfig;
	
	/**
	 * 是否默认推荐 1 默认 0 非默认
	 */
	private Integer isRecommended;
	
	/**
	 * 是否支持一键装修 1 支持 0不支持
	 */
	private Integer isDefaultDecorate;
	
	/**
	 * 方案编号
	 */
	private String planNumber;
	
	/**
	 * 该方案是否可见 0:可见，1:不可见
	 */
	private Integer visible;
	
	/**
	 * 设计方案副本id
	 */
	private Integer designSceneId;
	
	/**
	 * 场景是否发生改变(时间戳) 副本/临时
	 */
	private long sceneModified;
	
	/**
	 * 设计方案类型 0:普通设计方案 1:一件装修设计方案(创建过一件装修组合)
	 */
	private Integer planType;
	
	/**
	 * 推荐方案ID
	 */
	private Integer recommendedPlanId;
	
	/**
	 * 用户创建的方案来源类型 （0为DIY方案，1为一键替换方案，3为改造方案）
	 */
	private Integer businessType;
	
	/**
	 * 前段传来的 拼花 json 传
	 */
	private Integer spellingFlowerFileId;
	
	/**
	 * 拼花  产品ids 
	 */
	private String spellingFlowerProduct;
	
	/**
	 * 平台ID
	 */
	private Long platformId;
	
}
