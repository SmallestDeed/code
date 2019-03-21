package com.sandu.base.model;

import com.google.gson.annotations.Expose;
import com.sandu.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: BaseArea.java
 * @Package com.sandu.system.model
 * @Description:系统-行政区域
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 15:31:09
 */
public class BaseArea extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    @Expose
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
    @Expose
    private Integer isDeleted;
    /**
     * 区域编码
     **/
    @Expose
    private String areaCode;
    /**
     * 区域名称
     **/
    @Expose
    private String areaName;
    /**
     * 级别
     **/
    @Expose
    private Integer levelId;
    /**
     * 父级id
     **/
    @Expose
    private String pid;
    /**
     * 邮政编码
     **/
    @Expose
    private String zipCode;
    /**
     * 首字母
     **/
    @Expose
    private String firstLetter;
    /**
     * 排序
     **/
    @Expose
    private Integer ordering;
    /**
     * 字符备用1
     **/
    @Expose
    private String longCode;
    /**
     * 字符备用2
     **/
    private String att2;
    /**
     * 字符备用3
     **/
    private String att3;
    /**
     * 字符备用4
     **/
    private String att4;
    /**
     * 字符备用5
     **/
    private String att5;
    /**
     * 字符备用6
     **/
    private String att6;
    /**
     * 时间备用1
     **/
    private Date dateAtt1;
    /**
     * 时间备用2
     **/
    private Date dateAtt2;
    /**
     * 整数备用1
     **/
    private Integer numAtt1;
    /**
     * 整数备用2
     **/
    private Integer numAtt2;
    /**
     * 数字备用1
     **/
    private Double numAtt3;
    /**
     * 数字备用2
     **/
    private Double numAtt4;
    /**
     * 备注
     **/
    @Expose
    private String remark;
    //用户名全拼音
    @Expose
    private String pinyin;
    
    // 下级区域
    @Expose
    private List<BaseArea> lowerArea;

    //当前页面(default=0)
    private int currentPage = 0;

    //页面大小(default=10)
    private int pageSize = 10;

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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public String getLongCode() {
        return longCode;
    }

    public void setLongCode(String longCode) {
        this.longCode = longCode;
    }

    public String getAtt2() {
        return att2;
    }

    public void setAtt2(String att2) {
        this.att2 = att2;
    }

    public String getAtt3() {
        return att3;
    }

    public void setAtt3(String att3) {
        this.att3 = att3;
    }

    public String getAtt4() {
        return att4;
    }

    public void setAtt4(String att4) {
        this.att4 = att4;
    }

    public String getAtt5() {
        return att5;
    }

    public void setAtt5(String att5) {
        this.att5 = att5;
    }

    public String getAtt6() {
        return att6;
    }

    public void setAtt6(String att6) {
        this.att6 = att6;
    }

    public Date getDateAtt1() {
        return dateAtt1;
    }

    public void setDateAtt1(Date dateAtt1) {
        this.dateAtt1 = dateAtt1;
    }

    public Date getDateAtt2() {
        return dateAtt2;
    }

    public void setDateAtt2(Date dateAtt2) {
        this.dateAtt2 = dateAtt2;
    }

    public Integer getNumAtt1() {
        return numAtt1;
    }

    public void setNumAtt1(Integer numAtt1) {
        this.numAtt1 = numAtt1;
    }

    public Integer getNumAtt2() {
        return numAtt2;
    }

    public void setNumAtt2(Integer numAtt2) {
        this.numAtt2 = numAtt2;
    }

    public Double getNumAtt3() {
        return numAtt3;
    }

    public void setNumAtt3(Double numAtt3) {
        this.numAtt3 = numAtt3;
    }

    public Double getNumAtt4() {
        return numAtt4;
    }

    public void setNumAtt4(Double numAtt4) {
        this.numAtt4 = numAtt4;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BaseArea other = (BaseArea) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getAreaCode() == null ? other.getAreaCode() == null : this.getAreaCode().equals(other.getAreaCode()))
                && (this.getAreaName() == null ? other.getAreaName() == null : this.getAreaName().equals(other.getAreaName()))
                && (this.getLevelId() == null ? other.getLevelId() == null : this.getLevelId().equals(other.getLevelId()))
                && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
                && (this.getZipCode() == null ? other.getZipCode() == null : this.getZipCode().equals(other.getZipCode()))
                && (this.getFirstLetter() == null ? other.getFirstLetter() == null : this.getFirstLetter().equals(other.getFirstLetter()))
                && (this.getOrdering() == null ? other.getOrdering() == null : this.getOrdering().equals(other.getOrdering()))
                && (this.getLongCode() == null ? other.getLongCode() == null : this.getLongCode().equals(other.getLongCode()))
                && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
                && (this.getAtt3() == null ? other.getAtt3() == null : this.getAtt3().equals(other.getAtt3()))
                && (this.getAtt4() == null ? other.getAtt4() == null : this.getAtt4().equals(other.getAtt4()))
                && (this.getAtt5() == null ? other.getAtt5() == null : this.getAtt5().equals(other.getAtt5()))
                && (this.getAtt6() == null ? other.getAtt6() == null : this.getAtt6().equals(other.getAtt6()))
                && (this.getDateAtt1() == null ? other.getDateAtt1() == null : this.getDateAtt1().equals(other.getDateAtt1()))
                && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
                && (this.getNumAtt1() == null ? other.getNumAtt1() == null : this.getNumAtt1().equals(other.getNumAtt1()))
                && (this.getNumAtt2() == null ? other.getNumAtt2() == null : this.getNumAtt2().equals(other.getNumAtt2()))
                && (this.getNumAtt3() == null ? other.getNumAtt3() == null : this.getNumAtt3().equals(other.getNumAtt3()))
                && (this.getNumAtt4() == null ? other.getNumAtt4() == null : this.getNumAtt4().equals(other.getNumAtt4()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getAreaCode() == null) ? 0 : getAreaCode().hashCode());
        result = prime * result + ((getAreaName() == null) ? 0 : getAreaName().hashCode());
        result = prime * result + ((getLevelId() == null) ? 0 : getLevelId().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getZipCode() == null) ? 0 : getZipCode().hashCode());
        result = prime * result + ((getFirstLetter() == null) ? 0 : getFirstLetter().hashCode());
        result = prime * result + ((getOrdering() == null) ? 0 : getOrdering().hashCode());
        result = prime * result + ((getLongCode() == null) ? 0 : getLongCode().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getAtt3() == null) ? 0 : getAtt3().hashCode());
        result = prime * result + ((getAtt4() == null) ? 0 : getAtt4().hashCode());
        result = prime * result + ((getAtt5() == null) ? 0 : getAtt5().hashCode());
        result = prime * result + ((getAtt6() == null) ? 0 : getAtt6().hashCode());
        result = prime * result + ((getDateAtt1() == null) ? 0 : getDateAtt1().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getNumAtt1() == null) ? 0 : getNumAtt1().hashCode());
        result = prime * result + ((getNumAtt2() == null) ? 0 : getNumAtt2().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    /**
     * 获取对象的copy
     **/
    public BaseArea copy() {
        BaseArea obj = new BaseArea();
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setAreaCode(this.areaCode);
        obj.setAreaName(this.areaName);
        obj.setLevelId(this.levelId);
        obj.setPid(this.pid);
        obj.setZipCode(this.zipCode);
        obj.setFirstLetter(this.firstLetter);
        obj.setOrdering(this.ordering);
        obj.setLongCode(this.longCode);
        obj.setAtt2(this.att2);
        obj.setAtt3(this.att3);
        obj.setAtt4(this.att4);
        obj.setAtt5(this.att5);
        obj.setAtt6(this.att6);
        obj.setDateAtt1(this.dateAtt1);
        obj.setDateAtt2(this.dateAtt2);
        obj.setNumAtt1(this.numAtt1);
        obj.setNumAtt2(this.numAtt2);
        obj.setNumAtt3(this.numAtt3);
        obj.setNumAtt4(this.numAtt4);
        obj.setRemark(this.remark);

        return obj;
    }

    /**
     * 获取对象的map
     **/
    public Map toMap() {
        Map map = new HashMap();
        map.put("sysCode", this.sysCode);
        map.put("creator", this.creator);
        map.put("gmtCreate", this.gmtCreate);
        map.put("modifier", this.modifier);
        map.put("gmtModified", this.gmtModified);
        map.put("isDeleted", this.isDeleted);
        map.put("areaCode", this.areaCode);
        map.put("areaName", this.areaName);
        map.put("levelId", this.levelId);
        map.put("pid", this.pid);
        map.put("zipCode", this.zipCode);
        map.put("firstLetter", this.firstLetter);
        map.put("ordering", this.ordering);
        map.put("longCode", this.longCode);
        map.put("att2", this.att2);
        map.put("att3", this.att3);
        map.put("att4", this.att4);
        map.put("att5", this.att5);
        map.put("att6", this.att6);
        map.put("dateAtt1", this.dateAtt1);
        map.put("dateAtt2", this.dateAtt2);
        map.put("numAtt1", this.numAtt1);
        map.put("numAtt2", this.numAtt2);
        map.put("numAtt3", this.numAtt3);
        map.put("numAtt4", this.numAtt4);
        map.put("remark", this.remark);

        return map;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

	@Override
	public String toString() {
		return "BaseArea [id=" + id + ", sysCode=" + sysCode + ", creator=" + creator + ", gmtCreate=" + gmtCreate
				+ ", modifier=" + modifier + ", gmtModified=" + gmtModified + ", isDeleted=" + isDeleted + ", areaCode="
				+ areaCode + ", areaName=" + areaName + ", levelId=" + levelId + ", pid=" + pid + ", zipCode=" + zipCode
				+ ", firstLetter=" + firstLetter + ", ordering=" + ordering + ", longCode=" + longCode + ", att2="
				+ att2 + ", att3=" + att3 + ", att4=" + att4 + ", att5=" + att5 + ", att6=" + att6 + ", dateAtt1="
				+ dateAtt1 + ", dateAtt2=" + dateAtt2 + ", numAtt1=" + numAtt1 + ", numAtt2=" + numAtt2 + ", numAtt3="
				+ numAtt3 + ", numAtt4=" + numAtt4 + ", remark=" + remark + ", pinyin=" + pinyin + ", currentPage="
				+ currentPage + ", pageSize=" + pageSize + "]";
	}

	public List<BaseArea> getLowerArea()
	{
		return lowerArea;
	}

	public void setLowerArea(List<BaseArea> lowerArea)
	{
		this.lowerArea = lowerArea;
	}

    
}
