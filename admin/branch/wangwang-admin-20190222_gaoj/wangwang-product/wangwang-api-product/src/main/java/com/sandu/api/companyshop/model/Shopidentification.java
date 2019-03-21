package com.sandu.api.companyshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * shop_identification
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-11-22 11:50
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shopidentification implements Serializable {


    /**
     * 自增id
     */
    private Integer id;
    /**
     * 店铺ID(company_shop)
     */
    private Integer shopId;
    /**
     * 认证类型(0-企业;1-个体工商户;2-个人)
     */
    private Integer identifyType;
    /**
     * 认证名称(企业名称/商户名称/个人名称)
     */
    private String identifyName;
    /**
     * 企业法人
     */
    private String companyLegalPerson;
    /**
     * 身份证号(法人、商户、个人身份证号)
     */
    private String identifyCard;
    /**
     * 正面照
     */
    private Integer frontPicId;
    /**
     * 反面照
     */
    private Integer backendPicId;
    /**
     * 手持照
     */
    private Integer handPicId;
    /**
     * 正面照路径
     */
    private String frontPicPath;
    /**
     * 反面照路径
     */
    private String backendPicPath;
    /**
     * 手持照路径
     */
    private String handPicPath;
    /**
     * 经营类目
     */
    private String categoryIds;
    /**
     * 经营类目名称
     */
    private String categoryNames;
    /**
     * 营业执照
     */
    private String businessLicense;
    /**
     * 审核状态(0-待审核;1-已审核)
     */
    private Integer approveStatus;
    /**
     * 审核备注
     */
    private String approveRemark;
    /**
     * 审核人
     */
    private String approveUserId;
    /**
     * 审核时间
     */
    private Date approveTime;
    /**
     * 是否删除
     */
    private Integer isDeleted;
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

}
