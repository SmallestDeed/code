package com.sandu.api.solution.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Roc Chen
 * @Description 草图设计方案 基础Bean
 * @Date:Created Administrator in 20:51 2017/12/20 0020
 * @Modified By:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesignPlan implements Serializable {
    private static final long serialVersionUID = 4629955434426505508L;
    /**
     *
     */
    private Long id;

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
     * 方案图片
     */
    private Integer picId;

    /**
     *
     */
    private Integer model3dId;

    /**
     *
     */
    private Integer modelU3dId;

    /**
     * 方案模型
     */
    private Integer modelId;

    /**
     * 配置文件
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
     *
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
     *
     */
    private String designTemplateId;

    /**
     *
     */
    private Integer ipadModelU3dId;

    /**
     *
     */
    private Integer iosModelU3dId;

    /**
     *
     */
    private Integer androidModelU3dId;

    /**
     *
     */
    private Integer macbookpcModelU3dId;

    /**
     *
     */
    private Integer pcModelU3dId;

    /**
     *
     */
    private Integer webModelU3dId;

    /**
     *
     */
    private Integer mediaType;

    /**
     *
     */
    private Integer eveningFileId;

    /**
     *
     */
    private Integer dawnFileId;

    /**
     *
     */
    private Integer nightFileId;

    /**
     *
     */
    private Integer shareTotal;

    /**
     *
     */
    private Integer isOpen;

    /**
     *
     */
    private Integer draftState;

    /**
     *
     */
    private Integer baimoState;

    /**
     *
     */
    private Integer stuffFinishState;

    /**
     *
     */
    private Integer decorateFinishState;

    /**
     *
     */
    private Integer isChange;

    /**
     * 是否装修过
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

    /**
     * 户型ID
     */
    private Integer houseId;

    /**
     * 小区ID
     */
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
     * 设计方案发布时间 （需要通过发布时间进行排序）
     */
    private Date releaseTime;

    /**
     * 特效配置
     */
    private String effectsConfig;

    /**
     * 是否默认推荐
     */
    private Integer isRecommended;

    /**
     * 是否支持一键装修
     */
    private Integer isDefaultDecorate;

    /**
     * 方案编号
     */
    private String planNumber;

    /**
     * 表明该设计由进入渲染场景时创建的临时方案，0代表不可见，1代表可见
     */
    private Integer visible;

    /**
     * 设计方案副本id
     */
    private Long designSceneId;

    /**
     * 和副本比较，判断场景是否发生改变
     */
    private Long sceneModified;

    /**
     * 设计方案类型(0:普通设计方案;1:一件装修设计方案)
     */
    private Integer planType;

    /**
     * 推荐方案ID
     */
    private Long recommendedPlanId;

    /**
     * 用户创建的方案来源(0为DIY方案，1为一键替换方案，3为改造方案)
     */
    private Integer businessType;

    /**
     * 拼花产品集合
     */
    private String spellingFlowerProduct;

    /**
     * 拼花信息文件id
     */
    private Integer spellingFlowerFileId;

    /**
     *
     */
    private Integer designplanRenderPicIds;


    private Long platformId;


}