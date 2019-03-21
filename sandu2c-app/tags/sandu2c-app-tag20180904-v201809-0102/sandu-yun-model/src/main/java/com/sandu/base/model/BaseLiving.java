package com.sandu.base.model;

import com.sandu.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Title: BaseLiving.java
 * @Package com.sandu.system.model
 * @Description:系统-小区
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 14:41:11
 */
public class BaseLiving extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String houseGmtCreate;

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
    /**
     * 小区类型
     **/
    private String livingType;
    /**
     * 小区编码
     **/
    private String livingCode;
    /**
     * 小区名称
     **/
    private String livingName;
    /**
     * 小区描述
     **/
    private String livingDesc;
    /**
     * 归属区域
     **/
    private String areaId;
    /**
     * 建筑面积
     **/
    private String buildArea;
    /**
     * 占地面积
     **/
    private String coverArea;
    /**
     * 总建筑面积
     **/
    private String grossFloorArea;
    /**
     * 绿化率
     **/
    private String greeningRate;
    /**
     * 预售时间
     **/
    private Date presaleDate;
    /**
     * 开盘时间
     **/
    private Date openingDate;
    /**
     * 开工时间
     **/
    private Date startDate;
    /**
     * 竣工时间
     **/
    private Date finishDate;
    /**
     * 总期数
     **/
    private Integer totalPeriod;
    /**
     * 冗余字段,区域长longCode
     **/
    private String areaLongCode;
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
    private String remark;
    //区域
    private String areaStr;
    //小区类型
    private String livingTypeName;

    /**
     * 所有户型数量
     */
    private Integer baseHouseNum;

    /**
     * 已经完成户型数量
     */
    private Integer baseHouseCompleteNum;

    /**
     * 未完成户型数量
     */
    private Integer baseHouseUnfinishedNum;

    /**
     * 地址名
     */
    private String areaName;

    private String year;

    String startDateString;

    Date houseMinGmtCreate;

    //当前页面(default=0)
    private int currentPage = 0;

    //页面大小(default=10)
    private int pageSize = 10;

    public String getHouseGmtCreate() {
        return houseGmtCreate;
    }

    public void setHouseGmtCreate(String houseGmtCreate) {
        this.houseGmtCreate = houseGmtCreate;
    }

    public Date getHouseMinGmtCreate() {
        return houseMinGmtCreate;
    }

    public void setHouseMinGmtCreate(Date houseMinGmtCreate) {
        this.houseMinGmtCreate = houseMinGmtCreate;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getBaseHouseCompleteNum() {
        return baseHouseCompleteNum;
    }

    public void setBaseHouseCompleteNum(Integer baseHouseCompleteNum) {
        this.baseHouseCompleteNum = baseHouseCompleteNum;
    }

    public Integer getBaseHouseUnfinishedNum() {
        return baseHouseUnfinishedNum;
    }

    public void setBaseHouseUnfinishedNum(Integer baseHouseUnfinishedNum) {
        this.baseHouseUnfinishedNum = baseHouseUnfinishedNum;
    }

    public Integer getBaseHouseNum() {
        return baseHouseNum;
    }

    public void setBaseHouseNum(Integer baseHouseNum) {
        this.baseHouseNum = baseHouseNum;
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

    public String getLivingType() {
        return livingType;
    }

    public void setLivingType(String livingType) {
        this.livingType = livingType;
    }

    public String getLivingCode() {
        return livingCode;
    }

    public void setLivingCode(String livingCode) {
        this.livingCode = livingCode;
    }

    public String getLivingName() {
        return livingName;
    }

    public void setLivingName(String livingName) {
        this.livingName = livingName;
    }

    public String getLivingDesc() {
        return livingDesc;
    }

    public void setLivingDesc(String livingDesc) {
        this.livingDesc = livingDesc;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getBuildArea() {
        return buildArea;
    }

    public void setBuildArea(String buildArea) {
        this.buildArea = buildArea;
    }

    public String getCoverArea() {
        return coverArea;
    }

    public void setCoverArea(String coverArea) {
        this.coverArea = coverArea;
    }

    public String getGrossFloorArea() {
        return grossFloorArea;
    }

    public void setGrossFloorArea(String grossFloorArea) {
        this.grossFloorArea = grossFloorArea;
    }

    public String getGreeningRate() {
        return greeningRate;
    }

    public void setGreeningRate(String greeningRate) {
        this.greeningRate = greeningRate;
    }

    public Date getPresaleDate() {
        return presaleDate;
    }

    public void setPresaleDate(Date presaleDate) {
        this.presaleDate = presaleDate;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(Integer totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public String getAreaLongCode() {
        return areaLongCode;
    }

    public void setAreaLongCode(String areaLongCode) {
        this.areaLongCode = areaLongCode;
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

    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }


    public String getLivingTypeName() {
        return livingTypeName;
    }

    public void setLivingTypeName(String livingTypeName) {
        this.livingTypeName = livingTypeName;
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
        BaseLiving other = (BaseLiving) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getLivingType() == null ? other.getLivingType() == null : this.getLivingType().equals(other.getLivingType()))
                && (this.getLivingCode() == null ? other.getLivingCode() == null : this.getLivingCode().equals(other.getLivingCode()))
                && (this.getLivingName() == null ? other.getLivingName() == null : this.getLivingName().equals(other.getLivingName()))
                && (this.getLivingDesc() == null ? other.getLivingDesc() == null : this.getLivingDesc().equals(other.getLivingDesc()))
                && (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
                && (this.getBuildArea() == null ? other.getBuildArea() == null : this.getBuildArea().equals(other.getBuildArea()))
                && (this.getCoverArea() == null ? other.getCoverArea() == null : this.getCoverArea().equals(other.getCoverArea()))
                && (this.getGrossFloorArea() == null ? other.getGrossFloorArea() == null : this.getGrossFloorArea().equals(other.getGrossFloorArea()))
                && (this.getGreeningRate() == null ? other.getGreeningRate() == null : this.getGreeningRate().equals(other.getGreeningRate()))
                && (this.getPresaleDate() == null ? other.getPresaleDate() == null : this.getPresaleDate().equals(other.getPresaleDate()))
                && (this.getOpeningDate() == null ? other.getOpeningDate() == null : this.getOpeningDate().equals(other.getOpeningDate()))
                && (this.getStartDate() == null ? other.getStartDate() == null : this.getStartDate().equals(other.getStartDate()))
                && (this.getFinishDate() == null ? other.getFinishDate() == null : this.getFinishDate().equals(other.getFinishDate()))
                && (this.getTotalPeriod() == null ? other.getTotalPeriod() == null : this.getTotalPeriod().equals(other.getTotalPeriod()))
                && (this.getAreaLongCode() == null ? other.getAreaLongCode() == null : this.getAreaLongCode().equals(other.getAreaLongCode()))
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
        result = prime * result + ((getLivingType() == null) ? 0 : getLivingType().hashCode());
        result = prime * result + ((getLivingCode() == null) ? 0 : getLivingCode().hashCode());
        result = prime * result + ((getLivingName() == null) ? 0 : getLivingName().hashCode());
        result = prime * result + ((getLivingDesc() == null) ? 0 : getLivingDesc().hashCode());
        result = prime * result + ((getAreaId() == null) ? 0 : getAreaId().hashCode());
        result = prime * result + ((getBuildArea() == null) ? 0 : getBuildArea().hashCode());
        result = prime * result + ((getCoverArea() == null) ? 0 : getCoverArea().hashCode());
        result = prime * result + ((getGrossFloorArea() == null) ? 0 : getGrossFloorArea().hashCode());
        result = prime * result + ((getGreeningRate() == null) ? 0 : getGreeningRate().hashCode());
        result = prime * result + ((getPresaleDate() == null) ? 0 : getPresaleDate().hashCode());
        result = prime * result + ((getOpeningDate() == null) ? 0 : getOpeningDate().hashCode());
        result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
        result = prime * result + ((getFinishDate() == null) ? 0 : getFinishDate().hashCode());
        result = prime * result + ((getTotalPeriod() == null) ? 0 : getTotalPeriod().hashCode());
        result = prime * result + ((getAreaLongCode() == null) ? 0 : getAreaLongCode().hashCode());
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
    public BaseLiving copy() {
        BaseLiving obj = new BaseLiving();
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setLivingType(this.livingType);
        obj.setLivingCode(this.livingCode);
        obj.setLivingName(this.livingName);
        obj.setLivingDesc(this.livingDesc);
        obj.setAreaId(this.areaId);
        obj.setBuildArea(this.buildArea);
        obj.setCoverArea(this.coverArea);
        obj.setGrossFloorArea(this.grossFloorArea);
        obj.setGreeningRate(this.greeningRate);
        obj.setPresaleDate(this.presaleDate);
        obj.setOpeningDate(this.openingDate);
        obj.setStartDate(this.startDate);
        obj.setFinishDate(this.finishDate);
        obj.setTotalPeriod(this.totalPeriod);
        obj.setAreaLongCode(this.areaLongCode);
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
        map.put("livingType", this.livingType);
        map.put("livingCode", this.livingCode);
        map.put("livingName", this.livingName);
        map.put("livingDesc", this.livingDesc);
        map.put("areaId", this.areaId);
        map.put("buildArea", this.buildArea);
        map.put("coverArea", this.coverArea);
        map.put("grossFloorArea", this.grossFloorArea);
        map.put("greeningRate", this.greeningRate);
        map.put("presaleDate", this.presaleDate);
        map.put("openingDate", this.openingDate);
        map.put("startDate", this.startDate);
        map.put("finishDate", this.finishDate);
        map.put("totalPeriod", this.totalPeriod);
        map.put("areaLongCode", this.areaLongCode);
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

	@Override
	public String toString() {
		return "BaseLiving [id=" + id + ", houseGmtCreate=" + houseGmtCreate + ", sysCode=" + sysCode + ", creator="
				+ creator + ", gmtCreate=" + gmtCreate + ", modifier=" + modifier + ", gmtModified=" + gmtModified
				+ ", isDeleted=" + isDeleted + ", livingType=" + livingType + ", livingCode=" + livingCode
				+ ", livingName=" + livingName + ", livingDesc=" + livingDesc + ", areaId=" + areaId + ", buildArea="
				+ buildArea + ", coverArea=" + coverArea + ", grossFloorArea=" + grossFloorArea + ", greeningRate="
				+ greeningRate + ", presaleDate=" + presaleDate + ", openingDate=" + openingDate + ", startDate="
				+ startDate + ", finishDate=" + finishDate + ", totalPeriod=" + totalPeriod + ", areaLongCode="
				+ areaLongCode + ", att2=" + att2 + ", att3=" + att3 + ", att4=" + att4 + ", att5=" + att5 + ", att6="
				+ att6 + ", dateAtt1=" + dateAtt1 + ", dateAtt2=" + dateAtt2 + ", numAtt1=" + numAtt1 + ", numAtt2="
				+ numAtt2 + ", numAtt3=" + numAtt3 + ", numAtt4=" + numAtt4 + ", remark=" + remark + ", areaStr="
				+ areaStr + ", livingTypeName=" + livingTypeName + ", baseHouseNum=" + baseHouseNum
				+ ", baseHouseCompleteNum=" + baseHouseCompleteNum + ", baseHouseUnfinishedNum="
				+ baseHouseUnfinishedNum + ", areaName=" + areaName + ", year=" + year + ", startDateString="
				+ startDateString + ", houseMinGmtCreate=" + houseMinGmtCreate + ", currentPage=" + currentPage
				+ ", pageSize=" + pageSize + "]";
	}
    
    
}
