package com.sandu.system.model;

import com.sandu.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: ResPic.java
 * @Package com.sandu.system.model
 * @Description:系统-图片资源库
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 16:06:59
 */
public class ResPic extends Mapper implements Serializable {
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
     * 图片高
     **/
    private String picHigh;
    /**
     * 图片后缀
     **/
    private String picSuffix;
    /**
     * 图片级别
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
     * key标识（多个）
     **/
    private String fileKeys;
    /**
     * 标识IDs
     **/
    private String businessIds;
    /**
     * 图片对应的缩略图id信息:small_pic_info
     **/
    private String smallPicInfo;
    /**
     * 渲染图视角
     **/
    private Integer viewPoint;
    /**
     * 渲染图场景
     **/
    private Integer scene;
    /**
     * 时间备用1
     **/
    private Date dateAtt1;
    /**
     * 时间备用2
     **/
    private Date dateAtt2;

    /**
     * 整数备用2
     **/
    private Integer numAtt2;
    /**
     * 数字备用1
     **/
    private Double numAtt3;
    /**
     * 归档标志
     **/
    private Double numAtt4;
    /**
     * 备注
     **/
    private String remark;

    /*添加三个字段:三个字段能获得file_type信息*/
    private String firstMenu;

    private String secondMenu;

    private String thirdMenuP;

    private String OriginalPicPath;
    //记录图片文件排序序号
    private Integer sequence;

    //ipad缩略图路径
    private String ipadThumbnailPath;

    //web缩略图路径
    private String webThumbnailPath;
    /**
     * 渲染图片类型
     **/
    private String renderingType;
    /**
     * 全景路径
     **/
    private String panoPath;
    /**
     * 原图ID。缩略图时使用
     **/
    private Integer baseRenderId;

    private Integer sysTaskPicId;
    //产品主键ID
    private Integer productId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSysTaskPicId() {
        return sysTaskPicId;
    }

    public void setSysTaskPicId(Integer sysTaskPicId) {
        this.sysTaskPicId = sysTaskPicId;
    }

    public String getIpadThumbnailPath() {
        return ipadThumbnailPath;
    }

    public void setIpadThumbnailPath(String ipadThumbnailPath) {
        this.ipadThumbnailPath = ipadThumbnailPath;
    }

    public String getWebThumbnailPath() {
        return webThumbnailPath;
    }

    public void setWebThumbnailPath(String webThumbnailPath) {
        this.webThumbnailPath = webThumbnailPath;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getFirstMenu() {
        return firstMenu;
    }

    public void setFirstMenu(String firstMenu) {
        this.firstMenu = firstMenu;
    }

    public String getSecondMenu() {
        return secondMenu;
    }

    public void setSecondMenu(String secondMenu) {
        this.secondMenu = secondMenu;
    }

    public String getThirdMenuP() {
        return thirdMenuP;
    }

    public void setThirdMenuP(String thirdMenuP) {
        this.thirdMenuP = thirdMenuP;
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

    public String getPicHigh() {
        return picHigh;
    }

    public void setPicHigh(String picHigh) {
        this.picHigh = picHigh;
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


    public Integer getScene() {
        return scene;

    }

    public void setScene(Integer scene) {
        this.scene = scene;
    }

    public Integer getViewPoint() {
        return viewPoint;
    }

    public void setViewPoint(Integer viewPoint) {
        this.viewPoint = viewPoint;
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

    public String getOriginalPicPath() {
        return OriginalPicPath;
    }

    public void setOriginalPicPath(String originalPicPath) {
        OriginalPicPath = originalPicPath;
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
        ResPic other = (ResPic) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getSysTaskPicId() == null ? other.getSysTaskPicId() == null : this.getSysTaskPicId().equals(other.getSysTaskPicId()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getPicCode() == null ? other.getPicCode() == null : this.getPicCode().equals(other.getPicCode()))
                && (this.getPicName() == null ? other.getPicName() == null : this.getPicName().equals(other.getPicName()))
                && (this.getPicFileName() == null ? other.getPicFileName() == null : this.getPicFileName().equals(other.getPicFileName()))
                && (this.getPicType() == null ? other.getPicType() == null : this.getPicType().equals(other.getPicType()))
                && (this.getPicSize() == null ? other.getPicSize() == null : this.getPicSize().equals(other.getPicSize()))
                && (this.getPicWeight() == null ? other.getPicWeight() == null : this.getPicWeight().equals(other.getPicWeight()))
                && (this.getPicHigh() == null ? other.getPicHigh() == null : this.getPicHigh().equals(other.getPicHigh()))
                && (this.getPicSuffix() == null ? other.getPicSuffix() == null : this.getPicSuffix().equals(other.getPicSuffix()))
                && (this.getPicLevel() == null ? other.getPicLevel() == null : this.getPicLevel().equals(other.getPicLevel()))
                && (this.getPicFormat() == null ? other.getPicFormat() == null : this.getPicFormat().equals(other.getPicFormat()))
                && (this.getPicPath() == null ? other.getPicPath() == null : this.getPicPath().equals(other.getPicPath()))
                && (this.getPicDesc() == null ? other.getPicDesc() == null : this.getPicDesc().equals(other.getPicDesc()))
                && (this.getPicOrdering() == null ? other.getPicOrdering() == null : this.getPicOrdering().equals(other.getPicOrdering()))

                && (this.getFileKeys() == null ? other.getFileKeys() == null : this.getFileKeys().equals(other.getFileKeys()))
                && (this.getBusinessIds() == null ? other.getBusinessIds() == null : this.getBusinessIds().equals(other.getBusinessIds()))
                && (this.getSmallPicInfo() == null ? other.getSmallPicInfo() == null : this.getSmallPicInfo().equals(other.getSmallPicInfo()))
                && (this.getViewPoint() == null ? other.getViewPoint() == null : this.getViewPoint().equals(other.getViewPoint()))
                && (this.getScene() == null ? other.getScene() == null : this.getScene().equals(other.getScene()))
                && (this.getDateAtt1() == null ? other.getDateAtt1() == null : this.getDateAtt1().equals(other.getDateAtt1()))
                && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
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
        result = prime * result + ((getSysTaskPicId() == null) ? 0 : getSysTaskPicId().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getPicCode() == null) ? 0 : getPicCode().hashCode());
        result = prime * result + ((getPicName() == null) ? 0 : getPicName().hashCode());
        result = prime * result + ((getPicFileName() == null) ? 0 : getPicFileName().hashCode());
        result = prime * result + ((getPicType() == null) ? 0 : getPicType().hashCode());
        result = prime * result + ((getPicSize() == null) ? 0 : getPicSize().hashCode());
        result = prime * result + ((getPicWeight() == null) ? 0 : getPicWeight().hashCode());
        result = prime * result + ((getPicHigh() == null) ? 0 : getPicHigh().hashCode());
        result = prime * result + ((getPicSuffix() == null) ? 0 : getPicSuffix().hashCode());
        result = prime * result + ((getPicLevel() == null) ? 0 : getPicLevel().hashCode());
        result = prime * result + ((getPicFormat() == null) ? 0 : getPicFormat().hashCode());
        result = prime * result + ((getPicPath() == null) ? 0 : getPicPath().hashCode());
        result = prime * result + ((getPicDesc() == null) ? 0 : getPicDesc().hashCode());
        result = prime * result + ((getPicOrdering() == null) ? 0 : getPicOrdering().hashCode());
        result = prime * result + ((getFileKeys() == null) ? 0 : getFileKeys().hashCode());
        result = prime * result + ((getBusinessIds() == null) ? 0 : getBusinessIds().hashCode());
        result = prime * result + ((getSmallPicInfo() == null) ? 0 : getSmallPicInfo().hashCode());
        result = prime * result + ((getViewPoint() == null) ? 0 : getViewPoint().hashCode());
        result = prime * result + ((getScene() == null) ? 0 : getScene().hashCode());
        result = prime * result + ((getDateAtt1() == null) ? 0 : getDateAtt1().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getNumAtt2() == null) ? 0 : getNumAtt2().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    /**
     * 获取对象的copy
     **/
    public ResPic copy() {
        ResPic obj = new ResPic();
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setSysTaskPicId(this.sysTaskPicId);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setPicCode(this.picCode);
        obj.setPicName(this.picName);
        obj.setPicFileName(this.picFileName);
        obj.setPicType(this.picType);
        obj.setPicSize(this.picSize);
        obj.setPicWeight(this.picWeight);
        obj.setPicHigh(this.picHigh);
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
        obj.setDateAtt1(this.dateAtt1);
        obj.setDateAtt2(this.dateAtt2);
        obj.setBusinessId(this.businessId);
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
        map.put("sysTaskPicId", this.sysTaskPicId);
        map.put("gmtCreate", this.gmtCreate);
        map.put("modifier", this.modifier);
        map.put("gmtModified", this.gmtModified);
        map.put("isDeleted", this.isDeleted);
        map.put("picCode", this.picCode);
        map.put("picName", this.picName);
        map.put("picFileName", this.picFileName);
        map.put("picType", this.picType);
        map.put("picSize", this.picSize);
        map.put("picWeight", this.picWeight);
        map.put("picHigh", this.picHigh);
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
        map.put("dateAtt1", this.dateAtt1);
        map.put("dateAtt2", this.dateAtt2);
        map.put("businessId", this.businessId);
        map.put("numAtt2", this.numAtt2);
        map.put("numAtt3", this.numAtt3);
        map.put("numAtt4", this.numAtt4);
        map.put("remark", this.remark);

        return map;
    }

    private Integer businessId;

    private String fileKey;

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    private String resIds;

    public String getResIds() {
        return resIds;
    }

    public void setResIds(String resIds) {
        this.resIds = resIds;
    }

    private List<Integer> resIdList;

    public List<Integer> getResIdList() {
        return resIdList;
    }

    public void setResIdList(List<Integer> resIdList) {
        this.resIdList = resIdList;
    }

    public String getRenderingType() {
        return renderingType;
    }

    public void setRenderingType(String renderingType) {
        this.renderingType = renderingType;
    }

    public String getPanoPath() {
        return panoPath;
    }

    public void setPanoPath(String panoPath) {
        this.panoPath = panoPath;
    }

    public Integer getBaseRenderId() {
        return baseRenderId;
    }

    public void setBaseRenderId(Integer baseRenderId) {
        this.baseRenderId = baseRenderId;
    }

    @Override
    public String toString() {
        return "ResPic{" +
                "id=" + id +
                ", sysCode='" + sysCode + '\'' +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                ", isDeleted=" + isDeleted +
                ", picCode='" + picCode + '\'' +
                ", picName='" + picName + '\'' +
                ", picFileName='" + picFileName + '\'' +
                ", picType='" + picType + '\'' +
                ", picSize=" + picSize +
                ", picWeight='" + picWeight + '\'' +
                ", picHigh='" + picHigh + '\'' +
                ", picSuffix='" + picSuffix + '\'' +
                ", picLevel='" + picLevel + '\'' +
                ", picFormat='" + picFormat + '\'' +
                ", picPath='" + picPath + '\'' +
                ", picDesc='" + picDesc + '\'' +
                ", picOrdering='" + picOrdering + '\'' +
                ", fileKeys='" + fileKeys + '\'' +
                ", businessIds='" + businessIds + '\'' +
                ", smallPicInfo='" + smallPicInfo + '\'' +
                ", viewPoint=" + viewPoint +
                ", scene=" + scene +
                ", dateAtt1=" + dateAtt1 +
                ", dateAtt2=" + dateAtt2 +
                ", numAtt2=" + numAtt2 +
                ", numAtt3=" + numAtt3 +
                ", numAtt4=" + numAtt4 +
                ", remark='" + remark + '\'' +
                ", firstMenu='" + firstMenu + '\'' +
                ", secondMenu='" + secondMenu + '\'' +
                ", thirdMenuP='" + thirdMenuP + '\'' +
                ", OriginalPicPath='" + OriginalPicPath + '\'' +
                ", sequence=" + sequence +
                ", ipadThumbnailPath='" + ipadThumbnailPath + '\'' +
                ", webThumbnailPath='" + webThumbnailPath + '\'' +
                ", renderingType='" + renderingType + '\'' +
                ", panoPath='" + panoPath + '\'' +
                ", baseRenderId=" + baseRenderId +
                ", sysTaskPicId=" + sysTaskPicId +
                ", productId=" + productId +
                ", businessId=" + businessId +
                ", fileKey='" + fileKey + '\'' +
                ", resIds='" + resIds + '\'' +
                ", resIdList=" + resIdList +
                '}';
    }
}
