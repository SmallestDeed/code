package com.sandu.system.model;

import com.sandu.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Title: SysDictionary.java
 * @Package com.sandu.system.model
 * @Description:系统管理-数据字典
 * @createAuthor pandajun
 * @CreateDate 2015-05-26 11:45:04
 */
public class SysDictionary extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
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
    private Integer isDeleted;
    /**
     * 类型
     **/
    private String type;
    /**
     * 唯一标示
     **/
    private String valuekey;
    /**
     * 值
     **/
    private Integer value;

    private String appType;
    /**
     * 字符串类型值
     **/
    private String contactValue;

    private String timeConsuming;

    private String bigValuekey;

    private Integer bigValue;

    public String getBigValuekey() {
        return bigValuekey;
    }

    public void setBigValuekey(String bigValuekey) {
        this.bigValuekey = bigValuekey;
    }

    public Integer getBigValue() {
        return bigValue;
    }

    public void setBigValue(Integer bigValue) {
        this.bigValue = bigValue;
    }

    public String getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(String timeConsuming) {
        this.timeConsuming = timeConsuming;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    private Integer smallValue;

    public Integer getSmallValue() {
        return smallValue;
    }

    public void setSmallValue(Integer smallValue) {
        this.smallValue = smallValue;
    }

    /**
     * 名称
     **/
    private String name;
    /**
     * 排序
     **/
    private Integer ordering;
    /**
     * 字符备用1
     **/
    private String att1;
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
     * 字符备用6
     **/
    private String att7;
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
    private Integer picId;
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
    /*att1~att6说明信息*/
    private String att1Info;
    private String att2Info;
    private String att3Info;
    private String att4Info;
    private String att5Info;
    private String att6Info;
    private String att7Info;

    /* 是否显示U3D模型 0不显示,1显示 */
    private Integer showU3dModel;

    public Integer getShowU3dModel() {
        return showU3dModel;
    }

    public void setShowU3dModel(Integer showU3dModel) {
        this.showU3dModel = showU3dModel;
    }

    public String getAtt7() {
        return att7;
    }

    public void setAtt7(String att7) {
        this.att7 = att7;
    }

    public String getAtt7Info() {
        return att7Info;
    }

    public void setAtt7Info(String att7Info) {
        this.att7Info = att7Info;
    }

    public String getAtt1Info() {
        return att1Info;
    }

    public void setAtt1Info(String att1Info) {
        this.att1Info = att1Info;
    }

    public String getAtt2Info() {
        return att2Info;
    }

    public void setAtt2Info(String att2Info) {
        this.att2Info = att2Info;
    }

    public String getAtt3Info() {
        return att3Info;
    }

    public void setAtt3Info(String att3Info) {
        this.att3Info = att3Info;
    }

    public String getAtt4Info() {
        return att4Info;
    }

    public void setAtt4Info(String att4Info) {
        this.att4Info = att4Info;
    }

    public String getAtt5Info() {
        return att5Info;
    }

    public void setAtt5Info(String att5Info) {
        this.att5Info = att5Info;
    }

    public String getAtt6Info() {
        return att6Info;
    }

    public void setAtt6Info(String att6Info) {
        this.att6Info = att6Info;
    }

    //关联图片的path
    private String picPath;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValuekey() {
        return valuekey;
    }

    public void setValuekey(String valuekey) {
        this.valuekey = valuekey;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public String getAtt1() {
        return att1;
    }

    public void setAtt1(String att1) {
        this.att1 = att1;
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

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
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
        SysDictionary other = (SysDictionary) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getValuekey() == null ? other.getValuekey() == null : this.getValuekey().equals(other.getValuekey()))
                && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getOrdering() == null ? other.getOrdering() == null : this.getOrdering().equals(other.getOrdering()))
                && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
                && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
                && (this.getAtt3() == null ? other.getAtt3() == null : this.getAtt3().equals(other.getAtt3()))
                && (this.getAtt4() == null ? other.getAtt4() == null : this.getAtt4().equals(other.getAtt4()))
                && (this.getAtt5() == null ? other.getAtt5() == null : this.getAtt5().equals(other.getAtt5()))
                && (this.getAtt6() == null ? other.getAtt6() == null : this.getAtt6().equals(other.getAtt6()))
                && (this.getDateAtt1() == null ? other.getDateAtt1() == null : this.getDateAtt1().equals(other.getDateAtt1()))
                && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
                && (this.getNumAtt1() == null ? other.getNumAtt1() == null : this.getNumAtt1().equals(other.getNumAtt1()))
                && (this.getPicId() == null ? other.getPicId() == null : this.getPicId().equals(other.getPicId()))
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
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getValuekey() == null) ? 0 : getValuekey().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getOrdering() == null) ? 0 : getOrdering().hashCode());
        result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getAtt3() == null) ? 0 : getAtt3().hashCode());
        result = prime * result + ((getAtt4() == null) ? 0 : getAtt4().hashCode());
        result = prime * result + ((getAtt5() == null) ? 0 : getAtt5().hashCode());
        result = prime * result + ((getAtt6() == null) ? 0 : getAtt6().hashCode());
        result = prime * result + ((getDateAtt1() == null) ? 0 : getDateAtt1().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getNumAtt1() == null) ? 0 : getNumAtt1().hashCode());
        result = prime * result + ((getPicId() == null) ? 0 : getPicId().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    /**
     * 获取对象的copy
     **/
    public SysDictionary copy() {
        SysDictionary obj = new SysDictionary();
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setType(this.type);
        obj.setValuekey(this.valuekey);
        obj.setValue(this.value);
        obj.setName(this.name);
        obj.setOrdering(this.ordering);
        obj.setAtt1(this.att1);
        obj.setAtt2(this.att2);
        obj.setAtt3(this.att3);
        obj.setAtt4(this.att4);
        obj.setAtt5(this.att5);
        obj.setAtt6(this.att6);
        obj.setDateAtt1(this.dateAtt1);
        obj.setDateAtt2(this.dateAtt2);
        obj.setNumAtt1(this.numAtt1);
        obj.setPicId(this.picId);
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
        map.put("type", this.type);
        map.put("valuekey", this.valuekey);
        map.put("value", this.value);
        map.put("name", this.name);
        map.put("ordering", this.ordering);
        map.put("att1", this.att1);
        map.put("att2", this.att2);
        map.put("att3", this.att3);
        map.put("att4", this.att4);
        map.put("att5", this.att5);
        map.put("att6", this.att6);
        map.put("dateAtt1", this.dateAtt1);
        map.put("dateAtt2", this.dateAtt2);
        map.put("numAtt1", this.numAtt1);
        map.put("picId", this.picId);
        map.put("numAtt3", this.numAtt3);
        map.put("numAtt4", this.numAtt4);
        map.put("remark", this.remark);

        return map;
    }

    @Override
    public String toString() {
        return "SysDictionary{" +
                "id=" + id +
                ", sysCode='" + sysCode + '\'' +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                ", isDeleted=" + isDeleted +
                ", type='" + type + '\'' +
                ", valuekey='" + valuekey + '\'' +
                ", value=" + value +
                ", appType='" + appType + '\'' +
                ", contactValue='" + contactValue + '\'' +
                ", timeConsuming='" + timeConsuming + '\'' +
                ", bigValuekey='" + bigValuekey + '\'' +
                ", bigValue=" + bigValue +
                ", smallValue=" + smallValue +
                ", name='" + name + '\'' +
                ", ordering=" + ordering +
                ", att1='" + att1 + '\'' +
                ", att2='" + att2 + '\'' +
                ", att3='" + att3 + '\'' +
                ", att4='" + att4 + '\'' +
                ", att5='" + att5 + '\'' +
                ", att6='" + att6 + '\'' +
                ", att7='" + att7 + '\'' +
                ", dateAtt1=" + dateAtt1 +
                ", dateAtt2=" + dateAtt2 +
                ", numAtt1=" + numAtt1 +
                ", picId=" + picId +
                ", numAtt3=" + numAtt3 +
                ", numAtt4=" + numAtt4 +
                ", remark='" + remark + '\'' +
                ", att1Info='" + att1Info + '\'' +
                ", att2Info='" + att2Info + '\'' +
                ", att3Info='" + att3Info + '\'' +
                ", att4Info='" + att4Info + '\'' +
                ", att5Info='" + att5Info + '\'' +
                ", att6Info='" + att6Info + '\'' +
                ", att7Info='" + att7Info + '\'' +
                ", showU3dModel=" + showU3dModel +
                ", picPath='" + picPath + '\'' +
                '}';
    }
}
