package com.sandu.api.fullhouse.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 全屋方案表数据模型
 */
@Data
public class FullHouseDesignPlan implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 通用唯一识别码
     */
    private String uuid;
    /**
     * 全屋方案编码
     */
    private String planCode;
    /**
     * 全屋方案名称
     */
    private String planName;
    /**
     * 全屋方案风格ID
     */
    private Integer planStyleId;
    /**
     * 全屋方案风格
     */
    private String planStyleName;
    /**
     * 方案缩略图ID
     */
    private Integer planPicId;
    /**
     * 方案描述
     */
    private String planDescribe;
    /**
     * 公司ID
     */
    private Integer companyId;
    /**
     * 品牌ID
     */
    private String brandIds;
    /**
     * 设计师ID
     */
    private Integer userId;
    /**
     * 方案来源类型(1:内部制作,2:装进我家,3:交付,4:分享,5:复制)
     */
    private Integer sourceType;
    /**
     * 来源方案ID
     */
    private Integer sourcePlanId;
    /**
     * 方案公开状态(0:未公开,1:已公开)
     */
    private Integer openState;
    /**
     * 720 UUID
     */
    private String vrResourceUuid;
    /**
     * 方案版本
     */
    private Integer version;
    /**
     * 方案公开时间
     */
    private Date openTime;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改者
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
     * 复制的方案是否被修改(0:没有修改,1:已修改)
     */
    private Integer isUpdate;

    /**
     * 聊天消息id
     */
    private String msgId;
}