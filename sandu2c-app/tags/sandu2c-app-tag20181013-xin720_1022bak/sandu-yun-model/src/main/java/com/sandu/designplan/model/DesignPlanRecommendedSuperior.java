package com.sandu.designplan.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * @author Administrator
 */
@Data
public class DesignPlanRecommendedSuperior implements Serializable{
    private static final long serialVersionUID = 4799074866714941426L;
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
     * 方案ID
     */
    private Integer designPlanRecommendedId;

    /**
     * 排序
     */
    private Integer ordering;

    /**
     * 空间类型
     */
    private Integer spaceType;

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


}