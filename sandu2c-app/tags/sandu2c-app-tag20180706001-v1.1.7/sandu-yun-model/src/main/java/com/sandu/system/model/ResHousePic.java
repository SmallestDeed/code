package com.sandu.system.model;

import com.sandu.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: ResHousePic.java
 * @Package com.sandu.system.model
 * @Description:系统模块-户型、空间图片资源表
 * @createAuthor pandajun
 * @CreateDate 2016-08-13 16:34:09
 */
public class ResHousePic extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private List<Integer> resIdList;

    public List<Integer> getResIdList() {
        return resIdList;
    }

    public void setResIdList(List<Integer> resIdList) {
        this.resIdList = resIdList;
    }

    /**
     * 图片编码
     **/
    private String picCode;
    /**
     * 图片名称
     **/
    private String picName;
    /**
     * 图片文件名称
     **/
    private String picFileName;
    /**
     * 图片类型
     **/
    private String picType;
    /**
     * 图片大小
     **/
    private Integer picSize;
    /**
     * 图片长
     **/
    private String picWeight;
    /**
     * 图片宽
     **/
    private String picHigh;
    /**
     * 图片后缀
     **/
    private String picSuffix;
    /**
     * 图片等级
     **/
    private String picLevel;
    /**
     * 图片格式
     **/
    private String picFormat;
    /**
     * 图片路径
     **/
    private String picPath;
    /**
     * 图片描述
     **/
    private String picDesc;
    /**
     * 图片排序
     **/
    private String picOrdering;
    /**
     * 无
     **/
    private String fileKey;
    /**
     * 无
     **/
    private String fileKeys;
    /**
     * 主键
     **/
    private String businessIds;
    /**
     * 主键
     **/
    private int businessId;
    /**
     * 无
     **/
    private String smallPicInfo;
    /**
     * 无
     **/
    private Integer viewPoint;
    /**
     * 无
     **/
    private Integer scene;
    /**
     * 无
     **/
    private Integer sequence;
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
     * 字符备用1
     **/
    private String att1;
    /**
     * 字符备用2
     **/
    private String att2;
    /**
     * 整数备用1
     **/
    private Integer numa1;
    /**
     * 整数备用2
     **/
    private Integer numa2;
    /**
     * 备注
     **/
    private String remark;

    private String resIds;

    private String thumbnailPath;
    private String largeThumbnailPath;

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getLargeThumbnailPath() {
        return largeThumbnailPath;
    }

    public void setLargeThumbnailPath(String largeThumbnailPath) {
        this.largeThumbnailPath = largeThumbnailPath;
    }

    public String getResIds() {
        return resIds;
    }

    public void setResIds(String resIds) {
        this.resIds = resIds;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }


    public String getPicHigh() {
        return picHigh;
    }

    public void setPicHigh(String picHigh) {
        this.picHigh = picHigh;
    }

    public String getPicCode() {
        return picCode;
    }

    public void setPicCode(String picCode) {
        this.picCode = picCode;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicFileName() {
        return picFileName;
    }

    public void setPicFileName(String picFileName) {
        this.picFileName = picFileName;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public Integer getPicSize() {
        return picSize;
    }

    public void setPicSize(Integer picSize) {
        this.picSize = picSize;
    }

    public String getPicWeight() {
        return picWeight;
    }

    public void setPicWeight(String picWeight) {
        this.picWeight = picWeight;
    }

    public String getPicSuffix() {
        return picSuffix;
    }

    public void setPicSuffix(String picSuffix) {
        this.picSuffix = picSuffix;
    }

    public String getPicLevel() {
        return picLevel;
    }

    public void setPicLevel(String picLevel) {
        this.picLevel = picLevel;
    }

    public String getPicFormat() {
        return picFormat;
    }

    public void setPicFormat(String picFormat) {
        this.picFormat = picFormat;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicDesc() {
        return picDesc;
    }

    public void setPicDesc(String picDesc) {
        this.picDesc = picDesc;
    }

    public String getPicOrdering() {
        return picOrdering;
    }

    public void setPicOrdering(String picOrdering) {
        this.picOrdering = picOrdering;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileKeys() {
        return fileKeys;
    }

    public void setFileKeys(String fileKeys) {
        this.fileKeys = fileKeys;
    }

    public String getBusinessIds() {
        return businessIds;
    }

    public void setBusinessIds(String businessIds) {
        this.businessIds = businessIds;
    }

    public String getSmallPicInfo() {
        return smallPicInfo;
    }

    public void setSmallPicInfo(String smallPicInfo) {
        this.smallPicInfo = smallPicInfo;
    }

    public Integer getViewPoint() {
        return viewPoint;
    }

    public void setViewPoint(Integer viewPoint) {
        this.viewPoint = viewPoint;
    }

    public Integer getScene() {
        return scene;
    }

    public void setScene(Integer scene) {
        this.scene = scene;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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

    public Integer getNuma1() {
        return numa1;
    }

    public void setNuma1(Integer numa1) {
        this.numa1 = numa1;
    }

    public Integer getNuma2() {
        return numa2;
    }

    public void setNuma2(Integer numa2) {
        this.numa2 = numa2;
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
        ResHousePic other = (ResHousePic) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getPicCode() == null ? other.getPicCode() == null : this.getPicCode().equals(other.getPicCode()))
                && (this.getPicName() == null ? other.getPicName() == null : this.getPicName().equals(other.getPicName()))
                && (this.getThumbnailPath() == null ? other.getThumbnailPath() == null : this.getThumbnailPath().equals(other.getThumbnailPath()))
                && (this.getLargeThumbnailPath() == null ? other.getLargeThumbnailPath() == null : this.getLargeThumbnailPath().equals(other.getLargeThumbnailPath()))
                && (this.getPicFileName() == null ? other.getPicFileName() == null : this.getPicFileName().equals(other.getPicFileName()))
                && (this.getPicType() == null ? other.getPicType() == null : this.getPicType().equals(other.getPicType()))
                && (this.getPicSize() == null ? other.getPicSize() == null : this.getPicSize().equals(other.getPicSize()))
                && (this.getPicWeight() == null ? other.getPicWeight() == null : this.getPicWeight().equals(other.getPicWeight()))
                && (this.getPicSuffix() == null ? other.getPicSuffix() == null : this.getPicSuffix().equals(other.getPicSuffix()))
                && (this.getPicLevel() == null ? other.getPicLevel() == null : this.getPicLevel().equals(other.getPicLevel()))
                && (this.getPicFormat() == null ? other.getPicFormat() == null : this.getPicFormat().equals(other.getPicFormat()))
                && (this.getPicPath() == null ? other.getPicPath() == null : this.getPicPath().equals(other.getPicPath()))
                && (this.getPicDesc() == null ? other.getPicDesc() == null : this.getPicDesc().equals(other.getPicDesc()))
                && (this.getPicOrdering() == null ? other.getPicOrdering() == null : this.getPicOrdering().equals(other.getPicOrdering()))
                && (this.getFileKey() == null ? other.getFileKey() == null : this.getFileKey().equals(other.getFileKey()))
                && (this.getFileKeys() == null ? other.getFileKeys() == null : this.getFileKeys().equals(other.getFileKeys()))
                && (this.getBusinessIds() == null ? other.getBusinessIds() == null : this.getBusinessIds().equals(other.getBusinessIds()))
                && (this.getSmallPicInfo() == null ? other.getSmallPicInfo() == null : this.getSmallPicInfo().equals(other.getSmallPicInfo()))
                && (this.getViewPoint() == null ? other.getViewPoint() == null : this.getViewPoint().equals(other.getViewPoint()))
                && (this.getScene() == null ? other.getScene() == null : this.getScene().equals(other.getScene()))
                && (this.getSequence() == null ? other.getSequence() == null : this.getSequence().equals(other.getSequence()))
                && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
                && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
                && (this.getNuma1() == null ? other.getNuma1() == null : this.getNuma1().equals(other.getNuma1()))
                && (this.getNuma2() == null ? other.getNuma2() == null : this.getNuma2().equals(other.getNuma2()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPicCode() == null) ? 0 : getPicCode().hashCode());
        result = prime * result + ((getPicName() == null) ? 0 : getPicName().hashCode());
        result = prime * result + ((getThumbnailPath() == null) ? 0 : getThumbnailPath().hashCode());
        result = prime * result + ((getLargeThumbnailPath() == null) ? 0 : getLargeThumbnailPath().hashCode());
        result = prime * result + ((getPicFileName() == null) ? 0 : getPicFileName().hashCode());
        result = prime * result + ((getPicType() == null) ? 0 : getPicType().hashCode());
        result = prime * result + ((getPicSize() == null) ? 0 : getPicSize().hashCode());
        result = prime * result + ((getPicWeight() == null) ? 0 : getPicWeight().hashCode());
        result = prime * result + ((getPicSuffix() == null) ? 0 : getPicSuffix().hashCode());
        result = prime * result + ((getPicLevel() == null) ? 0 : getPicLevel().hashCode());
        result = prime * result + ((getPicFormat() == null) ? 0 : getPicFormat().hashCode());
        result = prime * result + ((getPicPath() == null) ? 0 : getPicPath().hashCode());
        result = prime * result + ((getPicDesc() == null) ? 0 : getPicDesc().hashCode());
        result = prime * result + ((getPicOrdering() == null) ? 0 : getPicOrdering().hashCode());
        result = prime * result + ((getFileKey() == null) ? 0 : getFileKey().hashCode());
        result = prime * result + ((getFileKeys() == null) ? 0 : getFileKeys().hashCode());
        result = prime * result + ((getBusinessIds() == null) ? 0 : getBusinessIds().hashCode());
        result = prime * result + ((getSmallPicInfo() == null) ? 0 : getSmallPicInfo().hashCode());
        result = prime * result + ((getViewPoint() == null) ? 0 : getViewPoint().hashCode());
        result = prime * result + ((getScene() == null) ? 0 : getScene().hashCode());
        result = prime * result + ((getSequence() == null) ? 0 : getSequence().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getNuma1() == null) ? 0 : getNuma1().hashCode());
        result = prime * result + ((getNuma2() == null) ? 0 : getNuma2().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    /**
     * 获取对象的copy
     **/
    public ResHousePic copy() {
        ResHousePic obj = new ResHousePic();
        obj.setPicCode(this.picCode);
        obj.setPicName(this.picName);
        obj.setThumbnailPath(this.thumbnailPath);
        obj.setLargeThumbnailPath(this.largeThumbnailPath);
        obj.setPicFileName(this.picFileName);
        obj.setPicType(this.picType);
        obj.setPicSize(this.picSize);
        obj.setPicWeight(this.picWeight);
        obj.setPicSuffix(this.picSuffix);
        obj.setPicLevel(this.picLevel);
        obj.setPicFormat(this.picFormat);
        obj.setPicPath(this.picPath);
        obj.setPicDesc(this.picDesc);
        obj.setPicOrdering(this.picOrdering);
        obj.setFileKey(this.fileKey);
        obj.setFileKeys(this.fileKeys);
        obj.setBusinessIds(this.businessIds);
        obj.setSmallPicInfo(this.smallPicInfo);
        obj.setViewPoint(this.viewPoint);
        obj.setScene(this.scene);
        obj.setSequence(this.sequence);
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setAtt1(this.att1);
        obj.setAtt2(this.att2);
        obj.setNuma1(this.numa1);
        obj.setNuma2(this.numa2);
        obj.setRemark(this.remark);

        return obj;
    }

    /**
     * 获取对象的map
     **/
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("picCode", this.picCode);
        map.put("picName", this.picName);
        map.put("thumbnailPath", this.thumbnailPath);
        map.put("largeThumbnailPath", this.largeThumbnailPath);
        map.put("picFileName", this.picFileName);
        map.put("picType", this.picType);
        map.put("picSize", this.picSize);
        map.put("picWeight", this.picWeight);
        map.put("picSuffix", this.picSuffix);
        map.put("picLevel", this.picLevel);
        map.put("picFormat", this.picFormat);
        map.put("picPath", this.picPath);
        map.put("picDesc", this.picDesc);
        map.put("picOrdering", this.picOrdering);
        map.put("fileKey", this.fileKey);
        map.put("fileKeys", this.fileKeys);
        map.put("businessIds", this.businessIds);
        map.put("smallPicInfo", this.smallPicInfo);
        map.put("viewPoint", this.viewPoint);
        map.put("scene", this.scene);
        map.put("sequence", this.sequence);
        map.put("sysCode", this.sysCode);
        map.put("creator", this.creator);
        map.put("gmtCreate", this.gmtCreate);
        map.put("modifier", this.modifier);
        map.put("gmtModified", this.gmtModified);
        map.put("isDeleted", this.isDeleted);
        map.put("att1", this.att1);
        map.put("att2", this.att2);
        map.put("numa1", this.numa1);
        map.put("numa2", this.numa2);
        map.put("remark", this.remark);

        return map;
    }

}
