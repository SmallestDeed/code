package com.sandu.home.model;

import com.sandu.common.model.Mapper;
import com.sandu.design.model.DesignTemplet;

import java.io.Serializable;
import java.util.*;

/**
 * @version V1.0
 * @Title: SpaceCommon.java
 * @Package com.sandu.home.model
 * @Description:户型房型-通用空间表
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 15:48:39
 */
public class SpaceCommon extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer hideId;

    public Integer getHideId() {
        return hideId;
    }

    public void setHideId(Integer hideId) {
        this.hideId = hideId;
    }

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
     * 通用房型编码
     **/
    private String spaceCode;
    /**
     * 通用房型名称
     **/
    private String spaceName;
    /**
     * 空间功能类型
     **/
    private Integer spaceFunctionId;
    /**
     * 主体长度
     **/
    private String mainLength;
    /**
     * 主体宽度
     **/
    private String mainWidth;
    /**
     * 空间面积
     **/
    private String spaceAreas;
    /**
     * 空间形状
     **/
    private String spaceShape;
    /**
     * 门的位置类型
     **/
    private String doorLocationType;
    /**
     * 门的位置id
     **/
    private Integer doorLocationId;
    /**
     * 窗的位置值
     **/
    private Integer windowLocationValue;
    /**
     * 空间描述
     **/
    private String spaceDesc;
    /**
     * 位置数组
     **/
    private String locationArrays;
    /**
     * 是否包含过道
     **/
    private Integer isIncludeWay;
    /**
     * 过道位置
     **/
    private String wayLocation;
    /**
     * 使用次数
     **/
    private Integer userNum;
    /**
     * 搜索次数
     **/
    private Integer searchNum;
    /**
     * 空间计数
     **/
    private Integer spaceNum;
    /**
     * 占总空间的比例
     **/
    private String spacePercent;
    /**
     * 来源id
     **/
    private Integer datasourceId;
    /**
     * 空间户型图id
     **/
    private Integer picId;
    //空间户型缩略图id
    private Integer picSmallId;
    //空间户型图原图
    private String picPath;
    //空间户型图缩略图
    private String picSmallPath;
    /**
     * 模型id
     **/
    private Integer modelId;
    /*状态:
     * 0:未发布
     * 1:发布
     * 2:禁用*/
    private Integer status;
    /**
     * 3d模型id
     **/
    private String model3dId;
    /**
     * u3d模型id
     **/
    private String modelU3dId;
    /**
     * u3d模型id
     **/
    private String windowsPcU3dModelId;
    /**
     * u3d模型id
     **/
    private String macBookPcU3dModelId;
    /**
     * u3d模型id
     **/
    private String ipadU3dModelId;
    /**
     * u3d模型id
     **/
    private String iosU3dModelId;
    /**
     * u3d模型id
     **/
    private String androidU3dModelId;
    /**
     * 3d俯视图
     **/
    private String view3dPic;
    //3d俯视图缩略图id
    private Integer view3dPicSmallId;
    /**
     * 3d俯视图原图
     **/
    private String view3dPicPath;
    /**
     * 3d俯视图缩略图
     **/
    private String view3dSmallPicPath;
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
    private Date publishModified;
    /**
     * 时间备用2
     **/
    private Date dateAtt2;
    /**
     * 风格id
     **/
    private Integer styleId;
    /**
     * 整数备用2
     **/
    private Integer cadPicId;
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
    private String cadAddress;
    /*平面图对应缩略图id*/
    private Integer viewPlanSmallId;
    /*空间俯视平面图path*/
    private String viewPlanPath;
    /*空间俯视平面图对应缩略图path*/
    private String viewPlanSmallPath;

    private String cadPicPath;

    private String cadPicSmallPath;

    /*条件查询上架状态(多个)*/
    private List<Integer> putawayStates;

    private List<Integer> statusList;
    /**
     * 场景模型文件
     **/
    private Integer scene3dId;
    /*空间类型黑名单:查询过滤用*/
    private Set<Integer> blackList;
    /*查询过滤条件:houseId*/
    private Integer houseId;
    /*查询过滤条件:userType*/
    private Integer userType;
    private Integer livingId;
    //是否公开：1：公开、2：未公开
    private Integer openStatus;

    //白天灯光u3d模型
    private Integer daylightU3dModelId;

    //黄昏灯光u3d模型
    private Integer dusklightU3dModelId;

    //黑夜灯光u3d模型
    private Integer nightlightU3dModelId;

    /*1为外网  2  为内网    默认为外网*/
    private String versionType;

    //U3D编码
    private String spaceAnalyzeCode;

    private Integer spaceShapeInt;

    //U3D新编码
    private String spaceU3dCode;

    /*空间布局列表*/
    private List<DesignTemplet> designTemplets;


    public List<DesignTemplet> getDesignTemplets() {
        return designTemplets;
    }

    public void setDesignTemplets(List<DesignTemplet> designTemplets) {
        this.designTemplets = designTemplets;
    }

    public String getSpaceU3dCode() {
        return spaceU3dCode;
    }

    public void setSpaceU3dCode(String spaceU3dCode) {
        this.spaceU3dCode = spaceU3dCode;
    }

    public Integer getSpaceShapeInt() {
        return spaceShapeInt;
    }

    public void setSpaceShapeInt(Integer spaceShapeInt) {
        this.spaceShapeInt = spaceShapeInt;
    }

    public String getSpaceAnalyzeCode() {
        return spaceAnalyzeCode;
    }

    public void setSpaceAnalyzeCode(String spaceAnalyzeCode) {
        this.spaceAnalyzeCode = spaceAnalyzeCode;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    public Integer getDaylightU3dModelId() {
        return daylightU3dModelId;
    }

    public void setDaylightU3dModelId(Integer daylightU3dModelId) {
        this.daylightU3dModelId = daylightU3dModelId;
    }

    public Integer getDusklightU3dModelId() {
        return dusklightU3dModelId;
    }

    public void setDusklightU3dModelId(Integer dusklightU3dModelId) {
        this.dusklightU3dModelId = dusklightU3dModelId;
    }

    public Integer getNightlightU3dModelId() {
        return nightlightU3dModelId;
    }

    public void setNightlightU3dModelId(Integer nightlightU3dModelId) {
        this.nightlightU3dModelId = nightlightU3dModelId;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public Integer getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Integer openStatus) {
        this.openStatus = openStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Set<Integer> getBlackList() {
        return blackList;
    }

    public void setBlackList(Set<Integer> blackList) {
        this.blackList = blackList;
    }

    public List<Integer> getPutawayStates() {
        return putawayStates;
    }

    public void setPutawayStates(List<Integer> putawayStates) {
        this.putawayStates = putawayStates;
    }

    public String getCadPicPath() {
        return cadPicPath;
    }

    public void setCadPicPath(String cadPicPath) {
        this.cadPicPath = cadPicPath;
    }

    public String getCadPicSmallPath() {
        return cadPicSmallPath;
    }

    public void setCadPicSmallPath(String cadPicSmallPath) {
        this.cadPicSmallPath = cadPicSmallPath;
    }

    public String getViewPlanPath() {
        return viewPlanPath;
    }

    public void setViewPlanPath(String viewPlanPath) {
        this.viewPlanPath = viewPlanPath;
    }

    public String getViewPlanSmallPath() {
        return viewPlanSmallPath;
    }

    public void setViewPlanSmallPath(String viewPlanSmallPath) {
        this.viewPlanSmallPath = viewPlanSmallPath;
    }

    public Integer getViewPlanSmallId() {
        return viewPlanSmallId;
    }

    public void setViewPlanSmallId(Integer viewPlanSmallId) {
        this.viewPlanSmallId = viewPlanSmallId;
    }

    public String getCadAddress() {
        return cadAddress;
    }

    public void setCadAddress(String cadAddress) {
        this.cadAddress = cadAddress;
    }

    /**
     * 是否是标准空间
     **/
    private Integer isStandardSpace;
    /**
     * pid 子标准空间
     **/
    private Integer pid;
    /**
     * 总使用量
     **/
    private Integer totalUsageAmount;


    //空间功能类型名称
    private String spaceFunctionName;
    //门位置
    private String doorLocationName;

    //面积范围
    private String areaRange;
    //空间类型
    private String type;

    private Integer totalSpaceNum;

    //统计子空间数量
    private Integer subspaceCount;
    //子空间归属空间编码
    private String standardSpaceCode;
    // 标准空间类型
    private String standardSpaceType;
    //空间形状info
    private String spaceShapeName;
    //子标准空间的子空间数量
    private Integer subStandardSpaceCount;
    //是否有白模产品 1 有 0 无
    private Integer isBmProduct;
    //俯视平面图(多张)
    private String viewPlanIds;

    public Integer getLivingId() {
        return livingId;
    }

    public void setLivingId(Integer livingId) {
        this.livingId = livingId;
    }

    public String getViewPlanIds() {
        return viewPlanIds;
    }

    public void setViewPlanIds(String viewPlanIds) {
        this.viewPlanIds = viewPlanIds;
    }

    public Integer getIsBmProduct() {
        return isBmProduct;
    }

    public void setIsBmProduct(Integer isBmProduct) {
        this.isBmProduct = isBmProduct;
    }

    public Integer getSubStandardSpaceCount() {
        return subStandardSpaceCount;
    }

    public void setSubStandardSpaceCount(Integer subStandardSpaceCount) {
        this.subStandardSpaceCount = subStandardSpaceCount;
    }

    public String getSpaceShapeName() {
        return spaceShapeName;
    }

    public void setSpaceShapeName(String spaceShapeName) {
        this.spaceShapeName = spaceShapeName;
    }

    public Integer getView3dPicSmallId() {
        return view3dPicSmallId;
    }

    public void setView3dPicSmallId(Integer view3dPicSmallId) {
        this.view3dPicSmallId = view3dPicSmallId;
    }

    public Integer getPicSmallId() {
        return picSmallId;
    }

    public void setPicSmallId(Integer picSmallId) {
        this.picSmallId = picSmallId;
    }

    public String getPicSmallPath() {
        return picSmallPath;
    }

    public void setPicSmallPath(String picSmallPath) {
        this.picSmallPath = picSmallPath;
    }

    public String getView3dPicPath() {
        return view3dPicPath;
    }

    public void setView3dPicPath(String view3dPicPath) {
        this.view3dPicPath = view3dPicPath;
    }

    public String getView3dSmallPicPath() {
        return view3dSmallPicPath;
    }

    public void setView3dSmallPicPath(String view3dSmallPicPath) {
        this.view3dSmallPicPath = view3dSmallPicPath;
    }

    public String getStandardSpaceType() {
        return standardSpaceType;
    }

    public void setStandardSpaceType(String standardSpaceType) {
        this.standardSpaceType = standardSpaceType;
    }

    public String getStandardSpaceCode() {
        return standardSpaceCode;
    }

    public void setStandardSpaceCode(String standardSpaceCode) {
        this.standardSpaceCode = standardSpaceCode;
    }

    public Integer getSubspaceCount() {
        return subspaceCount;
    }

    public void setSubspaceCount(Integer subspaceCount) {
        this.subspaceCount = subspaceCount;
    }

    public Integer getTotalSpaceNum() {
        return totalSpaceNum;
    }

    public void setTotalSpaceNum(Integer totalSpaceNum) {
        this.totalSpaceNum = totalSpaceNum;
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

    public String getSpaceCode() {
        return spaceCode;
    }

    public void setSpaceCode(String spaceCode) {
        this.spaceCode = spaceCode;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public Integer getSpaceFunctionId() {
        return spaceFunctionId;
    }

    public void setSpaceFunctionId(Integer spaceFunctionId) {
        this.spaceFunctionId = spaceFunctionId;
    }

    public String getMainLength() {
        return mainLength;
    }

    public void setMainLength(String mainLength) {
        this.mainLength = mainLength;
    }

    public String getMainWidth() {
        return mainWidth;
    }

    public void setMainWidth(String mainWidth) {
        this.mainWidth = mainWidth;
    }

    public String getSpaceAreas() {
        return spaceAreas;
    }

    public void setSpaceAreas(String spaceAreas) {
        this.spaceAreas = spaceAreas;
    }

    public String getSpaceShape() {
        return spaceShape;
    }

    public void setSpaceShape(String spaceShape) {
        this.spaceShape = spaceShape;
    }

    public String getDoorLocationType() {
        return doorLocationType;
    }

    public void setDoorLocationType(String doorLocationType) {
        this.doorLocationType = doorLocationType;
    }

    public Integer getDoorLocationId() {
        return doorLocationId;
    }

    public void setDoorLocationId(Integer doorLocationId) {
        this.doorLocationId = doorLocationId;
    }

    public Integer getWindowLocationValue() {
        return windowLocationValue;
    }

    public void setWindowLocationValue(Integer windowLocationValue) {
        this.windowLocationValue = windowLocationValue;
    }

    public String getSpaceDesc() {
        return spaceDesc;
    }

    public void setSpaceDesc(String spaceDesc) {
        this.spaceDesc = spaceDesc;
    }

    public String getLocationArrays() {
        return locationArrays;
    }

    public void setLocationArrays(String locationArrays) {
        this.locationArrays = locationArrays;
    }

    public Integer getIsIncludeWay() {
        return isIncludeWay;
    }

    public void setIsIncludeWay(Integer isIncludeWay) {
        this.isIncludeWay = isIncludeWay;
    }

    public String getWayLocation() {
        return wayLocation;
    }

    public void setWayLocation(String wayLocation) {
        this.wayLocation = wayLocation;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getSearchNum() {
        return searchNum;
    }

    public void setSearchNum(Integer searchNum) {
        this.searchNum = searchNum;
    }

    public Integer getSpaceNum() {
        return spaceNum;
    }

    public void setSpaceNum(Integer spaceNum) {
        this.spaceNum = spaceNum;
    }

    public String getSpacePercent() {
        return spacePercent;
    }

    public void setSpacePercent(String spacePercent) {
        this.spacePercent = spacePercent;
    }

    public Integer getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(Integer datasourceId) {
        this.datasourceId = datasourceId;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getModel3dId() {
        return model3dId;
    }

    public void setModel3dId(String model3dId) {
        this.model3dId = model3dId;
    }

    public String getModelU3dId() {
        return modelU3dId;
    }

    public void setModelU3dId(String modelU3dId) {
        this.modelU3dId = modelU3dId;
    }

    public String getWindowsPcU3dModelId() {
        return windowsPcU3dModelId;
    }

    public void setWindowsPcU3dModelId(String windowsPcU3dModelId) {
        this.windowsPcU3dModelId = windowsPcU3dModelId;
    }

    public String getMacBookPcU3dModelId() {
        return macBookPcU3dModelId;
    }

    public void setMacBookPcU3dModelId(String macBookPcU3dModelId) {
        this.macBookPcU3dModelId = macBookPcU3dModelId;
    }

    public String getIpadU3dModelId() {
        return ipadU3dModelId;
    }

    public void setIpadU3dModelId(String ipadU3dModelId) {
        this.ipadU3dModelId = ipadU3dModelId;
    }

    public String getIosU3dModelId() {
        return iosU3dModelId;
    }

    public void setIosU3dModelId(String iosU3dModelId) {
        this.iosU3dModelId = iosU3dModelId;
    }

    public String getAndroidU3dModelId() {
        return androidU3dModelId;
    }

    public void setAndroidU3dModelId(String androidU3dModelId) {
        this.androidU3dModelId = androidU3dModelId;
    }

    public String getView3dPic() {
        return view3dPic;
    }

    public void setView3dPic(String view3dPic) {
        this.view3dPic = view3dPic;
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

    public Date getPublishModified() {
        return publishModified;
    }

    public void setPublishModified(Date publishModified) {
        this.publishModified = publishModified;
    }

    public Date getDateAtt2() {
        return dateAtt2;
    }

    public void setDateAtt2(Date dateAtt2) {
        this.dateAtt2 = dateAtt2;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public Integer getCadPicId() {
        return cadPicId;
    }

    public void setCadPicId(Integer cadPicId) {
        this.cadPicId = cadPicId;
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

    public Integer getIsStandardSpace() {
        return isStandardSpace;
    }

    public void setIsStandardSpace(Integer isStandardSpace) {
        this.isStandardSpace = isStandardSpace;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getTotalUsageAmount() {
        return totalUsageAmount;
    }

    public void setTotalUsageAmount(Integer totalUsageAmount) {
        this.totalUsageAmount = totalUsageAmount;
    }

    public String getSpaceFunctionName() {
        return spaceFunctionName;
    }

    public void setSpaceFunctionName(String spaceFunctionName) {
        this.spaceFunctionName = spaceFunctionName;
    }

    public String getDoorLocationName() {
        return doorLocationName;
    }

    public void setDoorLocationName(String doorLocationName) {
        this.doorLocationName = doorLocationName;
    }


    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }


    public String getAreaRange() {
        return areaRange;
    }

    public void setAreaRange(String areaRange) {
        this.areaRange = areaRange;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getScene3dId() {
        return scene3dId;
    }

    public void setScene3dId(Integer scene3dId) {
        this.scene3dId = scene3dId;
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
        SpaceCommon other = (SpaceCommon) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getSpaceCode() == null ? other.getSpaceCode() == null : this.getSpaceCode().equals(other.getSpaceCode()))
                && (this.getSpaceName() == null ? other.getSpaceName() == null : this.getSpaceName().equals(other.getSpaceName()))
                && (this.getSpaceFunctionId() == null ? other.getSpaceFunctionId() == null : this.getSpaceFunctionId().equals(other.getSpaceFunctionId()))
                && (this.getMainLength() == null ? other.getMainLength() == null : this.getMainLength().equals(other.getMainLength()))
                && (this.getMainWidth() == null ? other.getMainWidth() == null : this.getMainWidth().equals(other.getMainWidth()))
                && (this.getSpaceAreas() == null ? other.getSpaceAreas() == null : this.getSpaceAreas().equals(other.getSpaceAreas()))
                && (this.getSpaceShape() == null ? other.getSpaceShape() == null : this.getSpaceShape().equals(other.getSpaceShape()))
                && (this.getDoorLocationType() == null ? other.getDoorLocationType() == null : this.getDoorLocationType().equals(other.getDoorLocationType()))
                && (this.getDoorLocationId() == null ? other.getDoorLocationId() == null : this.getDoorLocationId().equals(other.getDoorLocationId()))
                && (this.getSpaceDesc() == null ? other.getSpaceDesc() == null : this.getSpaceDesc().equals(other.getSpaceDesc()))
                && (this.getLocationArrays() == null ? other.getLocationArrays() == null : this.getLocationArrays().equals(other.getLocationArrays()))
                && (this.getIsIncludeWay() == null ? other.getIsIncludeWay() == null : this.getIsIncludeWay().equals(other.getIsIncludeWay()))
                && (this.getWayLocation() == null ? other.getWayLocation() == null : this.getWayLocation().equals(other.getWayLocation()))
                && (this.getUserNum() == null ? other.getUserNum() == null : this.getUserNum().equals(other.getUserNum()))
                && (this.getSearchNum() == null ? other.getSearchNum() == null : this.getSearchNum().equals(other.getSearchNum()))
                && (this.getSpaceNum() == null ? other.getSpaceNum() == null : this.getSpaceNum().equals(other.getSpaceNum()))
                && (this.getSpacePercent() == null ? other.getSpacePercent() == null : this.getSpacePercent().equals(other.getSpacePercent()))
                && (this.getDatasourceId() == null ? other.getDatasourceId() == null : this.getDatasourceId().equals(other.getDatasourceId()))
                && (this.getPicId() == null ? other.getPicId() == null : this.getPicId().equals(other.getPicId()))
                && (this.getModelId() == null ? other.getModelId() == null : this.getModelId().equals(other.getModelId()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getModel3dId() == null ? other.getModel3dId() == null : this.getModel3dId().equals(other.getModel3dId()))
                && (this.getModelU3dId() == null ? other.getModelU3dId() == null : this.getModelU3dId().equals(other.getModelU3dId()))
                && (this.getView3dPic() == null ? other.getView3dPic() == null : this.getView3dPic().equals(other.getView3dPic()))
                && (this.getAtt4() == null ? other.getAtt4() == null : this.getAtt4().equals(other.getAtt4()))
                && (this.getAtt5() == null ? other.getAtt5() == null : this.getAtt5().equals(other.getAtt5()))
                && (this.getAtt6() == null ? other.getAtt6() == null : this.getAtt6().equals(other.getAtt6()))
                && (this.getPublishModified() == null ? other.getPublishModified() == null : this.getPublishModified().equals(other.getPublishModified()))
                && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
                && (this.getStyleId() == null ? other.getStyleId() == null : this.getStyleId().equals(other.getStyleId()))
                && (this.getCadPicId() == null ? other.getCadPicId() == null : this.getCadPicId().equals(other.getCadPicId()))
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
        result = prime * result + ((getSpaceCode() == null) ? 0 : getSpaceCode().hashCode());
        result = prime * result + ((getSpaceName() == null) ? 0 : getSpaceName().hashCode());
        result = prime * result + ((getSpaceFunctionId() == null) ? 0 : getSpaceFunctionId().hashCode());
        result = prime * result + ((getMainLength() == null) ? 0 : getMainLength().hashCode());
        result = prime * result + ((getMainWidth() == null) ? 0 : getMainWidth().hashCode());
        result = prime * result + ((getSpaceAreas() == null) ? 0 : getSpaceAreas().hashCode());
        result = prime * result + ((getSpaceShape() == null) ? 0 : getSpaceShape().hashCode());
        result = prime * result + ((getDoorLocationType() == null) ? 0 : getDoorLocationType().hashCode());
        result = prime * result + ((getDoorLocationId() == null) ? 0 : getDoorLocationId().hashCode());
        result = prime * result + ((getSpaceDesc() == null) ? 0 : getSpaceDesc().hashCode());
        result = prime * result + ((getLocationArrays() == null) ? 0 : getLocationArrays().hashCode());
        result = prime * result + ((getIsIncludeWay() == null) ? 0 : getIsIncludeWay().hashCode());
        result = prime * result + ((getWayLocation() == null) ? 0 : getWayLocation().hashCode());
        result = prime * result + ((getUserNum() == null) ? 0 : getUserNum().hashCode());
        result = prime * result + ((getSearchNum() == null) ? 0 : getSearchNum().hashCode());
        result = prime * result + ((getSpaceNum() == null) ? 0 : getSpaceNum().hashCode());
        result = prime * result + ((getSpacePercent() == null) ? 0 : getSpacePercent().hashCode());
        result = prime * result + ((getDatasourceId() == null) ? 0 : getDatasourceId().hashCode());
        result = prime * result + ((getPicId() == null) ? 0 : getPicId().hashCode());
        result = prime * result + ((getModelId() == null) ? 0 : getModelId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getModel3dId() == null) ? 0 : getModel3dId().hashCode());
        result = prime * result + ((getModelU3dId() == null) ? 0 : getModelU3dId().hashCode());
        result = prime * result + ((getView3dPic() == null) ? 0 : getView3dPic().hashCode());
        result = prime * result + ((getAtt4() == null) ? 0 : getAtt4().hashCode());
        result = prime * result + ((getAtt5() == null) ? 0 : getAtt5().hashCode());
        result = prime * result + ((getAtt6() == null) ? 0 : getAtt6().hashCode());
        result = prime * result + ((getPublishModified() == null) ? 0 : getPublishModified().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getStyleId() == null) ? 0 : getStyleId().hashCode());
        result = prime * result + ((getCadPicId() == null) ? 0 : getCadPicId().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());

        return result;
    }

    /**
     * 获取对象的copy
     **/
    public SpaceCommon copy() {
        SpaceCommon obj = new SpaceCommon();
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setSpaceCode(this.spaceCode);
        obj.setSpaceName(this.spaceName);
        obj.setSpaceFunctionId(this.spaceFunctionId);
        obj.setMainLength(this.mainLength);
        obj.setMainWidth(this.mainWidth);
        obj.setSpaceAreas(this.spaceAreas);
        obj.setSpaceShape(this.spaceShape);
        obj.setDoorLocationType(this.doorLocationType);
        obj.setDoorLocationId(this.doorLocationId);
        obj.setSpaceDesc(this.spaceDesc);
        obj.setLocationArrays(this.locationArrays);
        obj.setIsIncludeWay(this.isIncludeWay);
        obj.setWayLocation(this.wayLocation);
        obj.setUserNum(this.userNum);
        obj.setSearchNum(this.searchNum);
        obj.setSpaceNum(this.spaceNum);
        obj.setSpacePercent(this.spacePercent);
        obj.setDatasourceId(this.datasourceId);
        obj.setPicId(this.picId);
        obj.setModelId(this.modelId);
        obj.setStatus(this.status);
        obj.setModel3dId(this.model3dId);
        obj.setModelU3dId(this.modelU3dId);
        obj.setView3dPic(this.view3dPic);
        obj.setAtt4(this.att4);
        obj.setAtt5(this.att5);
        obj.setAtt6(this.att6);
        obj.setPublishModified(this.publishModified);
        obj.setDateAtt2(this.dateAtt2);
        obj.setStyleId(this.styleId);
        obj.setCadPicId(this.cadPicId);
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
        map.put("spaceCode", this.spaceCode);
        map.put("spaceName", this.spaceName);
        map.put("spaceFunctionId", this.spaceFunctionId);
        map.put("mainLength", this.mainLength);
        map.put("mainWidth", this.mainWidth);
        map.put("spaceAreas", this.spaceAreas);
        map.put("spaceShape", this.spaceShape);
        map.put("doorLocationType", this.doorLocationType);
        map.put("doorLocationId", this.doorLocationId);
        map.put("spaceDesc", this.spaceDesc);
        map.put("locationArrays", this.locationArrays);
        map.put("isIncludeWay", this.isIncludeWay);
        map.put("wayLocation", this.wayLocation);
        map.put("userNum", this.userNum);
        map.put("searchNum", this.searchNum);
        map.put("spaceNum", this.spaceNum);
        map.put("spacePercent", this.spacePercent);
        map.put("datasourceId", this.datasourceId);
        map.put("picId", this.picId);
        map.put("modelId", this.modelId);
        map.put("status", this.status);
        map.put("att1", this.model3dId);
        map.put("att2", this.modelU3dId);
        map.put("view3dPic", this.view3dPic);
        map.put("att4", this.att4);
        map.put("att5", this.att5);
        map.put("att6", this.att6);
        map.put("publishModified", this.publishModified);
        map.put("dateAtt2", this.dateAtt2);
        map.put("numAtt1", this.styleId);
        map.put("cadPicId", this.cadPicId);
        map.put("numAtt3", this.numAtt3);
        map.put("numAtt4", this.numAtt4);
        map.put("remark", this.remark);

        return map;
    }

    @Override
    public String toString() {
        return "SpaceCommon{" +
                "id=" + id +
                ", hideId=" + hideId +
                ", sysCode='" + sysCode + '\'' +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                ", isDeleted=" + isDeleted +
                ", spaceCode='" + spaceCode + '\'' +
                ", spaceName='" + spaceName + '\'' +
                ", spaceFunctionId=" + spaceFunctionId +
                ", mainLength='" + mainLength + '\'' +
                ", mainWidth='" + mainWidth + '\'' +
                ", spaceAreas='" + spaceAreas + '\'' +
                ", spaceShape='" + spaceShape + '\'' +
                ", doorLocationType='" + doorLocationType + '\'' +
                ", doorLocationId=" + doorLocationId +
                ", windowLocationValue=" + windowLocationValue +
                ", spaceDesc='" + spaceDesc + '\'' +
                ", locationArrays='" + locationArrays + '\'' +
                ", isIncludeWay=" + isIncludeWay +
                ", wayLocation='" + wayLocation + '\'' +
                ", userNum=" + userNum +
                ", searchNum=" + searchNum +
                ", spaceNum=" + spaceNum +
                ", spacePercent='" + spacePercent + '\'' +
                ", datasourceId=" + datasourceId +
                ", picId=" + picId +
                ", picSmallId=" + picSmallId +
                ", picPath='" + picPath + '\'' +
                ", picSmallPath='" + picSmallPath + '\'' +
                ", modelId=" + modelId +
                ", status=" + status +
                ", model3dId='" + model3dId + '\'' +
                ", modelU3dId='" + modelU3dId + '\'' +
                ", windowsPcU3dModelId='" + windowsPcU3dModelId + '\'' +
                ", macBookPcU3dModelId='" + macBookPcU3dModelId + '\'' +
                ", ipadU3dModelId='" + ipadU3dModelId + '\'' +
                ", iosU3dModelId='" + iosU3dModelId + '\'' +
                ", androidU3dModelId='" + androidU3dModelId + '\'' +
                ", view3dPic='" + view3dPic + '\'' +
                ", view3dPicSmallId=" + view3dPicSmallId +
                ", view3dPicPath='" + view3dPicPath + '\'' +
                ", view3dSmallPicPath='" + view3dSmallPicPath + '\'' +
                ", att4='" + att4 + '\'' +
                ", att5='" + att5 + '\'' +
                ", att6='" + att6 + '\'' +
                ", publishModified=" + publishModified +
                ", dateAtt2=" + dateAtt2 +
                ", styleId=" + styleId +
                ", cadPicId=" + cadPicId +
                ", numAtt3=" + numAtt3 +
                ", numAtt4=" + numAtt4 +
                ", remark='" + remark + '\'' +
                ", cadAddress='" + cadAddress + '\'' +
                ", viewPlanSmallId=" + viewPlanSmallId +
                ", viewPlanPath='" + viewPlanPath + '\'' +
                ", viewPlanSmallPath='" + viewPlanSmallPath + '\'' +
                ", cadPicPath='" + cadPicPath + '\'' +
                ", cadPicSmallPath='" + cadPicSmallPath + '\'' +
                ", putawayStates=" + putawayStates +
                ", statusList=" + statusList +
                ", scene3dId=" + scene3dId +
                ", blackList=" + blackList +
                ", houseId=" + houseId +
                ", userType=" + userType +
                ", livingId=" + livingId +
                ", openStatus=" + openStatus +
                ", daylightU3dModelId=" + daylightU3dModelId +
                ", dusklightU3dModelId=" + dusklightU3dModelId +
                ", nightlightU3dModelId=" + nightlightU3dModelId +
                ", versionType='" + versionType + '\'' +
                ", spaceAnalyzeCode='" + spaceAnalyzeCode + '\'' +
                ", spaceShapeInt=" + spaceShapeInt +
                ", spaceU3dCode='" + spaceU3dCode + '\'' +
                ", isStandardSpace=" + isStandardSpace +
                ", pid=" + pid +
                ", totalUsageAmount=" + totalUsageAmount +
                ", spaceFunctionName='" + spaceFunctionName + '\'' +
                ", doorLocationName='" + doorLocationName + '\'' +
                ", areaRange='" + areaRange + '\'' +
                ", type='" + type + '\'' +
                ", totalSpaceNum=" + totalSpaceNum +
                ", subspaceCount=" + subspaceCount +
                ", standardSpaceCode='" + standardSpaceCode + '\'' +
                ", standardSpaceType='" + standardSpaceType + '\'' +
                ", spaceShapeName='" + spaceShapeName + '\'' +
                ", subStandardSpaceCount=" + subStandardSpaceCount +
                ", isBmProduct=" + isBmProduct +
                ", viewPlanIds='" + viewPlanIds + '\'' +
                '}';
    }
}
