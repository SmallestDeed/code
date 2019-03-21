package com.sandu.api.solution.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Roc Chen
 * @Description 草图设计方案产品 基础Bean
 * @Date:Created Administrator in 20:51 2017/12/20 0020
 * @Modified By:
 */
@Data
public class DesignPlanProduct implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 系统编码
     */
    private String sysCode;

    /**
     * 方案id
     */
    private Integer planId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     *
     */
    private Integer materialPicId;

    /**
     * 相机配置
     */
    private Integer locationFileId;

    /**
     * 顺序
     */
    private String productSequence;

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
     * 字符备用1
     */
    private String att1;

    /**
     * 字符备用2
     */
    private String att2;

    /**
     * 字符备用3
     */
    private String att3;

    /**
     * 字符备用4
     */
    private String att4;

    /**
     * 字符备用5
     */
    private String att5;

    /**
     * 字符备用6
     */
    private String att6;

    /**
     * 时间备用1
     */
    private Date dateAtt1;

    /**
     * 时间备用2
     */
    private Date dateAtt2;

    /**
     *
     */
    private Integer planproductId;

    /**
     *
     */
    private Integer displayStatus;

    /**
     * 数字备用1
     */
    private BigDecimal numAtt3;

    /**
     * 数字备用2
     */
    private BigDecimal numAtt4;

    /**
     * 备注
     */
    private String remark;

    /**
     *
     */
    private Integer isHide;

    /**
     *
     */
    private String posIndexPath;

    /**
     *
     */
    private Integer initProductId;

    /**
     *
     */
    private Integer isDirty;

    /**
     * 产品组合ID
     */
    private Integer productGroupId;

    /**
     * 是否是主产品
     */
    private Integer isMainProduct;

    /**
     * 挂节点名称
     */
    private String posName;

    /**
     * 设计方案组合ID
     */
    private String planGroupId;

    /**
     * 有模型的产品
     */
    private Integer modelProductId;

    /**
     * 绑定父产品Id
     */
    private String bindParentProductid;

    /**
     * 默认材质信息
     */
    private String splitTexturesChooseInfo;

    /**
     * 是组合还是结构,0:组合,1:结构
     */
    private Integer groupType;

    /**
     * 产品同小分类序号
     */
    private Integer sameProductTypeIndex;

    /**
     * 标准(1标准 0 非标准)
     */
    private Integer isStandard;

    /**
     * 中心点
     */
    private String center;

    /**
     * 区域标识
     */
    private String regionMark;

    /**
     * 款式id
     */
    private Integer styleId;

    /**
     * 尺寸代码
     */
    private String measureCode;

    /**
     * 描述(区域、尺寸代码)
     */
    private String describeInfo;

    /**
     * 序号
     */
    private Integer productIndex;

    /**
     * 是否为结构中的主产品标识（1是，0否）
     */
    private Integer isMainStructureProduct;

    /**
     * 主产品单品是否可以作为组合方式替换（1是，0否）
     */
    private Integer isGroupReplaceWay;

    /**
     * 墙体方位
     */
    private String wallOrientation;

    /**
     * 墙体类型
     */
    private String wallType;

    /**
     * 组合产品唯一标识
     */
    private String groupProductUniqueid;

    /**
     * 是否做了材质替换(0:否;>0:是taskId)
     */
    private Integer isReplaceTexture;

}