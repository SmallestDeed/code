package com.nork.home.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DrawBaseHouse implements Serializable
{
    private Long id;

    private String sysCode;

    private Long userId;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String houseCode;

    private String houseShortName;

    private String houseName;

    private Integer picRes1Id;

    private Integer picRes2Id;

    private Integer picRes3Id;

    private Integer picRes4Id;

    private String houseDesc;

    private Integer livingId;

    private String totalArea;

    private String totalFloors;

    private String currentPeriod;

    private String houseHigh;

    private String applyFloors;

    private String houseLayout;

    private String roomLayout;

    private String officeLayout;

    private String wayLayout;

    private String isExistSymmetry;

    private String symmetryIds;

    private String isMerge;

    private String mergeIds;

    private Integer mergeNewId;

    private Integer picResId;

    private Integer isAll;

    private String houseCommonCode;

    private String houseTypeCode;

    private String houseTag;

    private String houseStatus;

    private String dealStatus;

    private String isReview;

    private String att1;

    private String areaLongCode;

    private String houseKind;

    private String att4;

    private String att5;

    private String att6;

    private Date dateAtt1;

    private Date dateAtt2;

    private Integer resetState;

    private Integer numAtt2;

    private BigDecimal numAtt3;

    private BigDecimal numAtt4;

    private String remark;

    private String areaCode;

    private Integer isPublic;

    private String houseDoorCode;

    private String houseSpaceNum;

    private Long baseHouseId;

    private String rejectReason;

    private Long snapPicId;

    private Short origin;

    private Long restoreFileId;

    private Long perfectUserId;

    private Short dataType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode == null ? null : sysCode.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode == null ? null : houseCode.trim();
    }

    public String getHouseShortName() {
        return houseShortName;
    }

    public void setHouseShortName(String houseShortName) {
        this.houseShortName = houseShortName == null ? null : houseShortName.trim();
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName == null ? null : houseName.trim();
    }

    public Integer getPicRes1Id() {
        return picRes1Id;
    }

    public void setPicRes1Id(Integer picRes1Id) {
        this.picRes1Id = picRes1Id;
    }

    public Integer getPicRes2Id() {
        return picRes2Id;
    }

    public void setPicRes2Id(Integer picRes2Id) {
        this.picRes2Id = picRes2Id;
    }

    public Integer getPicRes3Id() {
        return picRes3Id;
    }

    public void setPicRes3Id(Integer picRes3Id) {
        this.picRes3Id = picRes3Id;
    }

    public Integer getPicRes4Id() {
        return picRes4Id;
    }

    public void setPicRes4Id(Integer picRes4Id) {
        this.picRes4Id = picRes4Id;
    }

    public String getHouseDesc() {
        return houseDesc;
    }

    public void setHouseDesc(String houseDesc) {
        this.houseDesc = houseDesc == null ? null : houseDesc.trim();
    }

    public Integer getLivingId() {
        return livingId;
    }

    public void setLivingId(Integer livingId) {
        this.livingId = livingId;
    }

    public String getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(String totalArea) {
        this.totalArea = totalArea == null ? null : totalArea.trim();
    }

    public String getTotalFloors() {
        return totalFloors;
    }

    public void setTotalFloors(String totalFloors) {
        this.totalFloors = totalFloors == null ? null : totalFloors.trim();
    }

    public String getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(String currentPeriod) {
        this.currentPeriod = currentPeriod == null ? null : currentPeriod.trim();
    }

    public String getHouseHigh() {
        return houseHigh;
    }

    public void setHouseHigh(String houseHigh) {
        this.houseHigh = houseHigh == null ? null : houseHigh.trim();
    }

    public String getApplyFloors() {
        return applyFloors;
    }

    public void setApplyFloors(String applyFloors) {
        this.applyFloors = applyFloors == null ? null : applyFloors.trim();
    }

    public String getHouseLayout() {
        return houseLayout;
    }

    public void setHouseLayout(String houseLayout) {
        this.houseLayout = houseLayout == null ? null : houseLayout.trim();
    }

    public String getRoomLayout() {
        return roomLayout;
    }

    public void setRoomLayout(String roomLayout) {
        this.roomLayout = roomLayout == null ? null : roomLayout.trim();
    }

    public String getOfficeLayout() {
        return officeLayout;
    }

    public void setOfficeLayout(String officeLayout) {
        this.officeLayout = officeLayout == null ? null : officeLayout.trim();
    }

    public String getWayLayout() {
        return wayLayout;
    }

    public void setWayLayout(String wayLayout) {
        this.wayLayout = wayLayout == null ? null : wayLayout.trim();
    }

    public String getIsExistSymmetry() {
        return isExistSymmetry;
    }

    public void setIsExistSymmetry(String isExistSymmetry) {
        this.isExistSymmetry = isExistSymmetry == null ? null : isExistSymmetry.trim();
    }

    public String getSymmetryIds() {
        return symmetryIds;
    }

    public void setSymmetryIds(String symmetryIds) {
        this.symmetryIds = symmetryIds == null ? null : symmetryIds.trim();
    }

    public String getIsMerge() {
        return isMerge;
    }

    public void setIsMerge(String isMerge) {
        this.isMerge = isMerge == null ? null : isMerge.trim();
    }

    public String getMergeIds() {
        return mergeIds;
    }

    public void setMergeIds(String mergeIds) {
        this.mergeIds = mergeIds == null ? null : mergeIds.trim();
    }

    public Integer getMergeNewId() {
        return mergeNewId;
    }

    public void setMergeNewId(Integer mergeNewId) {
        this.mergeNewId = mergeNewId;
    }

    public Integer getPicResId() {
        return picResId;
    }

    public void setPicResId(Integer picResId) {
        this.picResId = picResId;
    }

    public Integer getIsAll() {
        return isAll;
    }

    public void setIsAll(Integer isAll) {
        this.isAll = isAll;
    }

    public String getHouseCommonCode() {
        return houseCommonCode;
    }

    public void setHouseCommonCode(String houseCommonCode) {
        this.houseCommonCode = houseCommonCode == null ? null : houseCommonCode.trim();
    }

    public String getHouseTypeCode() {
        return houseTypeCode;
    }

    public void setHouseTypeCode(String houseTypeCode) {
        this.houseTypeCode = houseTypeCode == null ? null : houseTypeCode.trim();
    }

    public String getHouseTag() {
        return houseTag;
    }

    public void setHouseTag(String houseTag) {
        this.houseTag = houseTag == null ? null : houseTag.trim();
    }

    public String getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(String houseStatus) {
        this.houseStatus = houseStatus == null ? null : houseStatus.trim();
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus == null ? null : dealStatus.trim();
    }

    public String getIsReview() {
        return isReview;
    }

    public void setIsReview(String isReview) {
        this.isReview = isReview == null ? null : isReview.trim();
    }

    public String getAtt1() {
        return att1;
    }

    public void setAtt1(String att1) {
        this.att1 = att1 == null ? null : att1.trim();
    }

    public String getAreaLongCode() {
        return areaLongCode;
    }

    public void setAreaLongCode(String areaLongCode) {
        this.areaLongCode = areaLongCode == null ? null : areaLongCode.trim();
    }

    public String getHouseKind() {
        return houseKind;
    }

    public void setHouseKind(String houseKind) {
        this.houseKind = houseKind == null ? null : houseKind.trim();
    }

    public String getAtt4() {
        return att4;
    }

    public void setAtt4(String att4) {
        this.att4 = att4 == null ? null : att4.trim();
    }

    public String getAtt5() {
        return att5;
    }

    public void setAtt5(String att5) {
        this.att5 = att5 == null ? null : att5.trim();
    }

    public String getAtt6() {
        return att6;
    }

    public void setAtt6(String att6) {
        this.att6 = att6 == null ? null : att6.trim();
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

    public Integer getResetState() {
        return resetState;
    }

    public void setResetState(Integer resetState) {
        this.resetState = resetState;
    }

    public Integer getNumAtt2() {
        return numAtt2;
    }

    public void setNumAtt2(Integer numAtt2) {
        this.numAtt2 = numAtt2;
    }

    public BigDecimal getNumAtt3() {
        return numAtt3;
    }

    public void setNumAtt3(BigDecimal numAtt3) {
        this.numAtt3 = numAtt3;
    }

    public BigDecimal getNumAtt4() {
        return numAtt4;
    }

    public void setNumAtt4(BigDecimal numAtt4) {
        this.numAtt4 = numAtt4;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public String getHouseDoorCode() {
        return houseDoorCode;
    }

    public void setHouseDoorCode(String houseDoorCode) {
        this.houseDoorCode = houseDoorCode == null ? null : houseDoorCode.trim();
    }

    public String getHouseSpaceNum() {
        return houseSpaceNum;
    }

    public void setHouseSpaceNum(String houseSpaceNum) {
        this.houseSpaceNum = houseSpaceNum == null ? null : houseSpaceNum.trim();
    }

    public Long getBaseHouseId() {
        return baseHouseId;
    }

    public void setBaseHouseId(Long baseHouseId) {
        this.baseHouseId = baseHouseId;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
    }

    public Long getSnapPicId() {
        return snapPicId;
    }

    public void setSnapPicId(Long snapPicId) {
        this.snapPicId = snapPicId;
    }

    public Short getOrigin() {
        return origin;
    }

    public void setOrigin(Short origin) {
        this.origin = origin;
    }

    public Long getRestoreFileId() {
        return restoreFileId;
    }

    public void setRestoreFileId(Long restoreFileId) {
        this.restoreFileId = restoreFileId;
    }

    public Long getPerfectUserId() {
        return perfectUserId;
    }

    public void setPerfectUserId(Long perfectUserId) {
        this.perfectUserId = perfectUserId;
    }

    public Short getDataType() {
        return dataType;
    }

    public void setDataType(Short dataType) {
        this.dataType = dataType;
    }
}