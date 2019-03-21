package com.sandu.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class UserMediationAuthorize implements Serializable{
    private static final long serialVersionUID = -1779447904440197310L;
    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 证件号码
     */
    private String cardNumber;

    /**
     * 证件类型,身份证:1,户口簿:2,工作证:3,驾驶证:4
     */
    private Integer cardType;

    /**
     * 证件照片ID
     */
    private Integer cardPicId;

    /**
     * 认证状态,待申请:0,待审核:1,已认证:2,认证失败:3
     */
    private Integer status;

    /**
     * 认证时间
     */
    private Date authorizeTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 是否删除（0:否，1:是）
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String remark;


    /**
     * 证件人名称
     */
    private String cardName;


}