package com.sandu.api.customer.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class CustomerBaseInfo implements Serializable {

    private static final long serialVersionUID = 1431552982608568050L;
    private Long id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 公司id
     */
    private Integer companyId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 客户等级(0-普通客户(0-8分);1-活跃客户(8-15);2-潜在客户(15-25);3-忠实客户(25以上))
     */
    private Integer level;
    
    private Double score;
    
    /**
     *分配状态(0-未分配;1-已分配;2-跟进中;3-跟进完成;4-无效)
     */
    private Integer alotStatus;
    /**
     * 分配操作人(自动分配写1,人工分配写登录人id)
     */
    private Integer alotUserId;
    /**
     * 分配经销商企业ID
     */
    private Integer channelCompanyId;
    /**
     * 分配时间
     */
    private LocalDateTime alotTime;

    /**
     * 分配类型(0-自动;1-手动)
     */
    private Integer alotType;
    /**
     * 是否删除(0:正常，1：删除)
     */
    private Integer isDeleted;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;
    /**
     * 省code
     */
    private String provinceCode;
    /**
     * 市code
     */
    private String cityCode;
    /**
     * 区code
     */
    private String areaCode;
    /**
     * 街道code
     */
    private String streetCode;
    /**
     * 街道code
     */
    private String address;
    /**
     * 跟进人id
     */
    private Integer followUpUserId;

}