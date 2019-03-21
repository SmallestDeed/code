package com.nork.product.model;


import com.nork.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;


/**
 * @version V1.0
 * @Title: CompanyUnion.java
 * @Package com.nork.companyunion.model
 * @Description:异业联盟-异业联盟表
 * @createAuthor pandajun
 * @CreateDate 2017-12-01 11:12:33
 */
public class CompanyUnion extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 联盟名称
     **/
    private String name;
    /**
     * 联盟企业数量
     **/
    private Integer companyNumber;
    /**
     * 生效时间
     **/
    private Date beginTime;
    /**
     * 失效时间
     **/
    private Date endTime;
    /**
     * 状态（0:暂停，1:激活）
     **/
    private Integer status;
    /**
     * 备注
     **/
    private String remark;

    /**
     * 系统编码
     **/
    private String sysCode;
    /**
     * 创建者
     **/
    private String creator;
    /**
     * 创建时间
     **/
    private Date gmtCreate;
    /**
     * 修改人
     **/
    private String modifier;
    /**
     * 修改时间
     **/
    private Date gmtModified;
    /**
     * 是否删除
     **/
    private Integer isDeleted;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(Integer companyNumber) {
        this.companyNumber = companyNumber;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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
        this.modifier = modifier;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyUnion that = (CompanyUnion) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (companyNumber != null ? !companyNumber.equals(that.companyNumber) : that.companyNumber != null)
            return false;
        if (beginTime != null ? !beginTime.equals(that.beginTime) : that.beginTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (sysCode != null ? !sysCode.equals(that.sysCode) : that.sysCode != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null) return false;
        if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
        if (gmtModified != null ? !gmtModified.equals(that.gmtModified) : that.gmtModified != null) return false;
        return isDeleted != null ? isDeleted.equals(that.isDeleted) : that.isDeleted == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (companyNumber != null ? companyNumber.hashCode() : 0);
        result = 31 * result + (beginTime != null ? beginTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (sysCode != null ? sysCode.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
        result = 31 * result + (gmtModified != null ? gmtModified.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CompanyUnion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", companyNumber=" + companyNumber +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", sysCode='" + sysCode + '\'' +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
