package com.sandu.api.solution.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Roc Chen
 * @Description 样板房设计方案产品 基础Bean
 * @Date:Created Administrator in 20:51 2017/12/20 0020
 * @Modified By:
 */
@Data
public class DesignTempletProduct implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 位置文本信息id
     */
    private Integer locationFileId;

    /**
     * 产品顺序
     */
    private String productSequence;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 方案id
     */
    private Integer designTempletId;

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
     * 字符备用1
     */
    private String att1;

    /**
     * 字符备用2
     */
    private String att2;

    /**
     * 整数备用1
     */
    private Integer numa1;

    /**
     * 整数备用2
     */
    private Integer numa2;

    /**
     * 备注
     */
    private String remark;

    /**
     *
     */
    private String posIndexPath;

    /**
     *
     */
    private Integer initProductId;

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
     * 并没有什么卵用
     */
    private String timeid;

    /**
     * 样板房组合ID
     */
    private String planGroupId;

    /**
     * 绑定父产品Id
     */
    private String bindParentProductid;

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
     * 记录删除数据标识
     */
    private String delDataMark;

    /**
     * 墙体方位
     */
    private String wallOrientation;

    /**
     * 墙体类型
     */
    private String wallType;

}