package com.nork.product.model;

import com.nork.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;

public class CompanyFranchiserGroup extends Mapper implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 经销商所属企业id
     */
    private Integer companyId;

    /**
     * 经销商(主账号)id
     */
    private Integer franchiserId;

    /**
     * 公共积分
     */
    private Double commonalityIntegral;

    private Double consumeIntegral;

    public Double getConsumeIntegral() {
        return consumeIntegral;
    }

    public void setConsumeIntegral(Double consumeIntegral) {
        this.consumeIntegral = consumeIntegral;
    }

    /**
     * last操作人id
     */
    private Integer modifierUserId;

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
     * 是否删除：0,未删除；1,已删除
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String remark;

    /**
     * company_franchiser_group
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 经销商所属企业id
     * @return company_id 经销商所属企业id
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 经销商所属企业id
     * @param companyId 经销商所属企业id
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 经销商(主账号)id
     * @return franchiser_id 经销商(主账号)id
     */
    public Integer getFranchiserId() {
        return franchiserId;
    }

    /**
     * 经销商(主账号)id
     * @param franchiserId 经销商(主账号)id
     */
    public void setFranchiserId(Integer franchiserId) {
        this.franchiserId = franchiserId;
    }

    /**
     * 公共积分
     * @return commonality_integral 公共积分
     */
    public Double getCommonalityIntegral() {
        return commonalityIntegral;
    }

    /**
     * 公共积分
     * @param commonalityIntegral 公共积分
     */
    public void setCommonalityIntegral(Double commonalityIntegral) {
        this.commonalityIntegral = commonalityIntegral;
    }

    /**
     * last操作人id
     * @return modifier_user_id last操作人id
     */
    public Integer getModifierUserId() {
        return modifierUserId;
    }

    /**
     * last操作人id
     * @param modifierUserId last操作人id
     */
    public void setModifierUserId(Integer modifierUserId) {
        this.modifierUserId = modifierUserId;
    }

    /**
     * 系统编码
     * @return sys_code 系统编码
     */
    public String getSysCode() {
        return sysCode;
    }

    /**
     * 系统编码
     * @param sysCode 系统编码
     */
    public void setSysCode(String sysCode) {
        this.sysCode = sysCode == null ? null : sysCode.trim();
    }

    /**
     * 创建者
     * @return creator 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     * @param creator 创建者
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 创建时间
     * @return gmt_create 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 创建时间
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 修改人
     * @return modifier 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 修改人
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 修改时间
     * @return gmt_modified 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 修改时间
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 是否删除：0,未删除；1,已删除
     * @return is_deleted 是否删除：0,未删除；1,已删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除：0,未删除；1,已删除
     * @param isDeleted 是否删除：0,未删除；1,已删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}