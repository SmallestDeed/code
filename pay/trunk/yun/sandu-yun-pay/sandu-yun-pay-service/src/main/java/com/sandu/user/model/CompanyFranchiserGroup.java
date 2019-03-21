package com.sandu.user.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 企业经销商账号组表对应的实体类
 *
 * @Author yzw
 * @Date 2018/2/5 16:13
 */
public class CompanyFranchiserGroup implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 经销商(主账号)id
     */
    private Integer franchiserId;

    /**
     * 公共度币
     */
    private BigDecimal commonalityIntegral;

    /**
     * 消费的总度币
     */
    private BigDecimal consumeIntegral;

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

    private static final long serialVersionUID = 1L;

    public CompanyFranchiserGroup() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFranchiserId() {
        return franchiserId;
    }

    public void setFranchiserId(Integer franchiserId) {
        this.franchiserId = franchiserId;
    }

    public BigDecimal getCommonalityIntegral() {
        return commonalityIntegral;
    }

    public void setCommonalityIntegral(BigDecimal commonalityIntegral) {
        this.commonalityIntegral = commonalityIntegral;
    }

    public Integer getModifierUserId() {
        return modifierUserId;
    }

    public void setModifierUserId(Integer modifierUserId) {
        this.modifierUserId = modifierUserId;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode == null ? null : sysCode.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getConsumeIntegral() {
        return consumeIntegral;
    }

    public void setConsumeIntegral(BigDecimal consumeIntegral) {
        this.consumeIntegral = consumeIntegral;
    }
}
