package com.sandu.home.model;

import com.sandu.common.model.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Title: BaseHouse.java
 * @Package com.sandu.business.model
 * @Description:业务-户型
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 11:53:51
 */
public class BaseHouse extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer userId;
    
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
	Integer spaceCommonStatusList[] = null;//存放空间状态的list  用于in 查询
	Integer designTempletPutawayStateList[] = null; //存放样板房状态的list  用于in 查询
	
 

	public Integer[] getSpaceCommonStatusList() {
		return spaceCommonStatusList;
	}

	public void setSpaceCommonStatusList(Integer[] spaceCommonStatusList) {
		this.spaceCommonStatusList = spaceCommonStatusList;
	}

	public Integer[] getDesignTempletPutawayStateList() {
		return designTempletPutawayStateList;
	}

	public void setDesignTempletPutawayStateList(Integer[] designTempletPutawayStateList) {
		this.designTempletPutawayStateList = designTempletPutawayStateList;
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
     * 户型编码
     **/
    private String houseCode;
    /**
     * 户型简称
     **/
    private String houseShortName;
    /**
     * 户型全称
     **/
    private String houseName;
    /**
     * 户型图
     **/
    private Integer picRes1Id;
    /**
     * CAD图
     **/
    private Integer picRes2Id;
    /**
     * 被处理可以分析的户型图
     **/
    private Integer picRes3Id;
    /**
     * 被查询去掉分析数据的户型图
     **/
    private Integer picRes4Id;
    /**
     * 户型描述
     **/
    private String houseDesc;
    /**
     * 所属小区
     **/
    private Integer livingId;
    /**
     * 总面积
     **/
    private String totalArea;
    /**
     * 总楼层
     **/
    private String totalFloors;
    /**
     * 销售期数
     **/
    private String currentPeriod;
    /**
     * 户型高度
     **/
    private String houseHigh;
    /**
     * 适用楼层
     **/
    private String applyFloors;
    /**
     * 户型结构
     **/
    private String houseLayout;
    /**
     * 室结构类型
     **/
    private String roomLayout;
    /**
     * 厅结构类型
     **/
    private String officeLayout;
    /**
     * 过道结构类型
     **/
    private String wayLayout;
    /**
     * 是否存在对称户型
     **/
    private String isExistSymmetry;
    /**
     * 对称户型组ids
     **/
    private String symmetryIds;
    /**
     * 是否合并
     **/
    private String isMerge;
    /**
     * 合并户型组ids
     **/
    private String mergeIds;
    /**
     * 合并后新的户型id
     **/
    private Integer mergeNewId;
    /**
     * 生成模拟图
     **/
    private Integer picResId;
    /**
     * 图形是否规则
     **/
    private Integer isAll;
    /**
     * 户型类型
     **/
    private String houseCommonCode;
    /**
     * 户型标示
     **/
    private String houseTypeCode;
    /**
     * 户型标签
     **/
    private String houseTag;
    /**
     * 户型状态
     **/
    private String houseStatus;
    /**
     * 处理状态
     **/
    private String dealStatus;
    /**
     * 是否审核
     **/
    private String isReview;
    /**
     * 区域字段(废弃)
     **/
    private String areaCode;
    /**
     * 开盘时间
     **/
    private String att1;
    /**
     * 区域longCode
     **/
    private String areaLongCode;
    /**
     * 户型种类
     **/
    private String houseKind;
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
     * 重置状态
     **/
    private Integer resetState;
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
    //小区名称
    private String livingName;
    //房型
    private String houseTypeStr;

    private String houseType;
    private String houseAddress;
    
    public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}
	private Integer isPublic;
    //户型编码
    private String houseDoorCode;
    //空间编码
    private String spaceCode;
    //空间功能类型
    private String spaceFunctionId;
    //空间形状
    private String spaceShape;
    private String thumbnailPath;
    private String largeThumbnailPath;

    //当前页面(default=0)
    private int currentPage = 0;

    //页面大小(default=10)
    private int pageSize = 10;


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

    public String getSpaceCode() {
        return spaceCode;
    }

    public void setSpaceCode(String spaceCode) {
        this.spaceCode = spaceCode;
    }

    public String getSpaceFunctionId() {
        return spaceFunctionId;
    }

    public void setSpaceFunctionId(String spaceFunctionId) {
        this.spaceFunctionId = spaceFunctionId;
    }

    public String getSpaceShape() {
        return spaceShape;
    }

    public void setSpaceShape(String spaceShape) {
        this.spaceShape = spaceShape;
    }

    public String getHouseDoorCode() {
        return houseDoorCode;
    }

    public void setHouseDoorCode(String houseDoorCode) {
        this.houseDoorCode = houseDoorCode;
    }

    public String getLargeThumbnailPath() {
        return largeThumbnailPath;
    }

    public void setLargeThumbnailPath(String largeThumbnailPath) {
        this.largeThumbnailPath = largeThumbnailPath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    private String province;
    private String city;
    private String area;
    private String living;
    private String livingCode;
    private String ting;
    private String shi;
    private String chufang;
    private String weishengjian;
    private String yangtai;
    private String guodao;
    private String xinghao;

    private String areaCode_p;
    private String areaCode_c;
    private String areaCode_a;
    /**
     * 关联查询出来的定位空间信息,后期再处理
     */
    private String spaceTypeStrs;

    public String getSpaceTypeStrs() {
        return spaceTypeStrs;
    }

    public void setSpaceTypeStrs(String spaceTypeStrs) {
        this.spaceTypeStrs = spaceTypeStrs;
    }

    public String getAreaCode_p() {
        return areaCode_p;
    }

    public void setAreaCode_p(String areaCode_p) {
        this.areaCode_p = areaCode_p;
    }

    public String getAreaCode_c() {
        return areaCode_c;
    }

    public void setAreaCode_c(String areaCode_c) {
        this.areaCode_c = areaCode_c;
    }

    public String getAreaCode_a() {
        return areaCode_a;
    }

    public void setAreaCode_a(String areaCode_a) {
        this.areaCode_a = areaCode_a;
    }

    public String getLivingCode() {
        return livingCode;
    }

    public void setLivingCode(String livingCode) {
        this.livingCode = livingCode;
    }

    public String getXinghao() {
        return xinghao;
    }

    public void setXinghao(String xinghao) {
        this.xinghao = xinghao;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLiving() {
        return living;
    }

    public void setLiving(String living) {
        this.living = living;
    }

    public String getTing() {
        return ting;
    }

    public void setTing(String ting) {
        this.ting = ting;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getChufang() {
        return chufang;
    }

    public void setChufang(String chufang) {
        this.chufang = chufang;
    }

    public String getWeishengjian() {
        return weishengjian;
    }

    public void setWeishengjian(String weishengjian) {
        this.weishengjian = weishengjian;
    }

    public String getYangtai() {
        return yangtai;
    }

    public void setYangtai(String yangtai) {
        this.yangtai = yangtai;
    }

    public String getGuodao() {
        return guodao;
    }

    public void setGuodao(String guodao) {
        this.guodao = guodao;
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

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public String getHouseShortName() {
        return houseShortName;
    }

    public void setHouseShortName(String houseShortName) {
        this.houseShortName = houseShortName;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
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
        this.houseDesc = houseDesc;
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
        this.totalArea = totalArea;
    }

    public String getTotalFloors() {
        return totalFloors;
    }

    public void setTotalFloors(String totalFloors) {
        this.totalFloors = totalFloors;
    }

    public String getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(String currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public String getHouseHigh() {
        return houseHigh;
    }

    public void setHouseHigh(String houseHigh) {
        this.houseHigh = houseHigh;
    }

    public String getApplyFloors() {
        return applyFloors;
    }

    public void setApplyFloors(String applyFloors) {
        this.applyFloors = applyFloors;
    }

    public String getHouseLayout() {
        return houseLayout;
    }

    public void setHouseLayout(String houseLayout) {
        this.houseLayout = houseLayout;
    }

    public String getRoomLayout() {
        return roomLayout;
    }

    public void setRoomLayout(String roomLayout) {
        this.roomLayout = roomLayout;
    }

    public String getOfficeLayout() {
        return officeLayout;
    }

    public void setOfficeLayout(String officeLayout) {
        this.officeLayout = officeLayout;
    }

    public String getWayLayout() {
        return wayLayout;
    }

    public void setWayLayout(String wayLayout) {
        this.wayLayout = wayLayout;
    }

    public String getIsExistSymmetry() {
        return isExistSymmetry;
    }

    public void setIsExistSymmetry(String isExistSymmetry) {
        this.isExistSymmetry = isExistSymmetry;
    }

    public String getSymmetryIds() {
        return symmetryIds;
    }

    public void setSymmetryIds(String symmetryIds) {
        this.symmetryIds = symmetryIds;
    }

    public String getIsMerge() {
        return isMerge;
    }

    public void setIsMerge(String isMerge) {
        this.isMerge = isMerge;
    }

    public String getMergeIds() {
        return mergeIds;
    }

    public void setMergeIds(String mergeIds) {
        this.mergeIds = mergeIds;
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
        this.houseCommonCode = houseCommonCode;
    }

    public String getHouseTypeCode() {
        return houseTypeCode;
    }

    public void setHouseTypeCode(String houseTypeCode) {
        this.houseTypeCode = houseTypeCode;
    }

    public String getHouseTag() {
        return houseTag;
    }

    public void setHouseTag(String houseTag) {
        this.houseTag = houseTag;
    }

    public String getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(String houseStatus) {
        this.houseStatus = houseStatus;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getIsReview() {
        return isReview;
    }

    public void setIsReview(String isReview) {
        this.isReview = isReview;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAtt1() {
        return att1;
    }

    public void setAtt1(String att1) {
        this.att1 = att1;
    }

    public String getAreaLongCode() {
        return areaLongCode;
    }

    public void setAreaLongCode(String areaLongCode) {
        this.areaLongCode = areaLongCode;
    }

    public String getHouseKind() {
        return houseKind;
    }

    public void setHouseKind(String houseKind) {
        this.houseKind = houseKind;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    public String getLivingName() {
        return livingName;
    }

    public void setLivingName(String livingName) {
        this.livingName = livingName;
    }


    public String getHouseTypeStr() {
        return houseTypeStr;
    }

    public void setHouseTypeStr(String houseTypeStr) {
        this.houseTypeStr = houseTypeStr;
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
        BaseHouse other = (BaseHouse) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getIsPublic() == null ? other.getIsPublic() == null : this.getIsPublic().equals(other.getIsPublic()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getHouseCode() == null ? other.getHouseCode() == null : this.getHouseCode().equals(other.getHouseCode()))
                && (this.getHouseShortName() == null ? other.getHouseShortName() == null : this.getHouseShortName().equals(other.getHouseShortName()))
                && (this.getHouseName() == null ? other.getHouseName() == null : this.getHouseName().equals(other.getHouseName()))
                && (this.getPicRes1Id() == null ? other.getPicRes1Id() == null : this.getPicRes1Id().equals(other.getPicRes1Id()))
                && (this.getPicRes2Id() == null ? other.getPicRes2Id() == null : this.getPicRes2Id().equals(other.getPicRes2Id()))
                && (this.getPicRes3Id() == null ? other.getPicRes3Id() == null : this.getPicRes3Id().equals(other.getPicRes3Id()))
                && (this.getPicRes4Id() == null ? other.getPicRes4Id() == null : this.getPicRes4Id().equals(other.getPicRes4Id()))
                && (this.getHouseDesc() == null ? other.getHouseDesc() == null : this.getHouseDesc().equals(other.getHouseDesc()))
                && (this.getLivingId() == null ? other.getLivingId() == null : this.getLivingId().equals(other.getLivingId()))
                && (this.getTotalArea() == null ? other.getTotalArea() == null : this.getTotalArea().equals(other.getTotalArea()))
                && (this.getTotalFloors() == null ? other.getTotalFloors() == null : this.getTotalFloors().equals(other.getTotalFloors()))
                && (this.getCurrentPeriod() == null ? other.getCurrentPeriod() == null : this.getCurrentPeriod().equals(other.getCurrentPeriod()))
                && (this.getHouseHigh() == null ? other.getHouseHigh() == null : this.getHouseHigh().equals(other.getHouseHigh()))
                && (this.getApplyFloors() == null ? other.getApplyFloors() == null : this.getApplyFloors().equals(other.getApplyFloors()))
                && (this.getHouseLayout() == null ? other.getHouseLayout() == null : this.getHouseLayout().equals(other.getHouseLayout()))
                && (this.getRoomLayout() == null ? other.getRoomLayout() == null : this.getRoomLayout().equals(other.getRoomLayout()))
                && (this.getOfficeLayout() == null ? other.getOfficeLayout() == null : this.getOfficeLayout().equals(other.getOfficeLayout()))
                && (this.getWayLayout() == null ? other.getWayLayout() == null : this.getWayLayout().equals(other.getWayLayout()))
                && (this.getIsExistSymmetry() == null ? other.getIsExistSymmetry() == null : this.getIsExistSymmetry().equals(other.getIsExistSymmetry()))
                && (this.getSymmetryIds() == null ? other.getSymmetryIds() == null : this.getSymmetryIds().equals(other.getSymmetryIds()))
                && (this.getIsMerge() == null ? other.getIsMerge() == null : this.getIsMerge().equals(other.getIsMerge()))
                && (this.getMergeIds() == null ? other.getMergeIds() == null : this.getMergeIds().equals(other.getMergeIds()))
                && (this.getMergeNewId() == null ? other.getMergeNewId() == null : this.getMergeNewId().equals(other.getMergeNewId()))
                && (this.getPicResId() == null ? other.getPicResId() == null : this.getPicResId().equals(other.getPicResId()))
                && (this.getIsAll() == null ? other.getIsAll() == null : this.getIsAll().equals(other.getIsAll()))
                && (this.getHouseCommonCode() == null ? other.getHouseCommonCode() == null : this.getHouseCommonCode().equals(other.getHouseCommonCode()))
                && (this.getHouseTypeCode() == null ? other.getHouseTypeCode() == null : this.getHouseTypeCode().equals(other.getHouseTypeCode()))
                && (this.getHouseTag() == null ? other.getHouseTag() == null : this.getHouseTag().equals(other.getHouseTag()))
                && (this.getHouseStatus() == null ? other.getHouseStatus() == null : this.getHouseStatus().equals(other.getHouseStatus()))
                && (this.getDealStatus() == null ? other.getDealStatus() == null : this.getDealStatus().equals(other.getDealStatus()))
                && (this.getIsReview() == null ? other.getIsReview() == null : this.getIsReview().equals(other.getIsReview()))
                && (this.getAreaCode() == null ? other.getAreaCode() == null : this.getAreaCode().equals(other.getAreaCode()))
                && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
                && (this.getAreaLongCode() == null ? other.getAreaLongCode() == null : this.getAreaLongCode().equals(other.getAreaLongCode()))
                && (this.getHouseKind() == null ? other.getHouseKind() == null : this.getHouseKind().equals(other.getHouseKind()))
                && (this.getAtt4() == null ? other.getAtt4() == null : this.getAtt4().equals(other.getAtt4()))
                && (this.getAtt5() == null ? other.getAtt5() == null : this.getAtt5().equals(other.getAtt5()))
                && (this.getAtt6() == null ? other.getAtt6() == null : this.getAtt6().equals(other.getAtt6()))
                && (this.getDateAtt1() == null ? other.getDateAtt1() == null : this.getDateAtt1().equals(other.getDateAtt1()))
                && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
                && (this.getResetState() == null ? other.getResetState() == null : this.getResetState().equals(other.getResetState()))
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
        result = prime * result + ((getIsPublic() == null) ? 0 : getIsPublic().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getHouseCode() == null) ? 0 : getHouseCode().hashCode());
        result = prime * result + ((getHouseShortName() == null) ? 0 : getHouseShortName().hashCode());
        result = prime * result + ((getHouseName() == null) ? 0 : getHouseName().hashCode());
        result = prime * result + ((getPicRes1Id() == null) ? 0 : getPicRes1Id().hashCode());
        result = prime * result + ((getPicRes2Id() == null) ? 0 : getPicRes2Id().hashCode());
        result = prime * result + ((getPicRes3Id() == null) ? 0 : getPicRes3Id().hashCode());
        result = prime * result + ((getPicRes4Id() == null) ? 0 : getPicRes4Id().hashCode());
        result = prime * result + ((getHouseDesc() == null) ? 0 : getHouseDesc().hashCode());
        result = prime * result + ((getLivingId() == null) ? 0 : getLivingId().hashCode());
        result = prime * result + ((getTotalArea() == null) ? 0 : getTotalArea().hashCode());
        result = prime * result + ((getTotalFloors() == null) ? 0 : getTotalFloors().hashCode());
        result = prime * result + ((getCurrentPeriod() == null) ? 0 : getCurrentPeriod().hashCode());
        result = prime * result + ((getHouseHigh() == null) ? 0 : getHouseHigh().hashCode());
        result = prime * result + ((getApplyFloors() == null) ? 0 : getApplyFloors().hashCode());
        result = prime * result + ((getHouseLayout() == null) ? 0 : getHouseLayout().hashCode());
        result = prime * result + ((getRoomLayout() == null) ? 0 : getRoomLayout().hashCode());
        result = prime * result + ((getOfficeLayout() == null) ? 0 : getOfficeLayout().hashCode());
        result = prime * result + ((getWayLayout() == null) ? 0 : getWayLayout().hashCode());
        result = prime * result + ((getIsExistSymmetry() == null) ? 0 : getIsExistSymmetry().hashCode());
        result = prime * result + ((getSymmetryIds() == null) ? 0 : getSymmetryIds().hashCode());
        result = prime * result + ((getIsMerge() == null) ? 0 : getIsMerge().hashCode());
        result = prime * result + ((getMergeIds() == null) ? 0 : getMergeIds().hashCode());
        result = prime * result + ((getMergeNewId() == null) ? 0 : getMergeNewId().hashCode());
        result = prime * result + ((getPicResId() == null) ? 0 : getPicResId().hashCode());
        result = prime * result + ((getIsAll() == null) ? 0 : getIsAll().hashCode());
        result = prime * result + ((getHouseCommonCode() == null) ? 0 : getHouseCommonCode().hashCode());
        result = prime * result + ((getHouseTypeCode() == null) ? 0 : getHouseTypeCode().hashCode());
        result = prime * result + ((getHouseTag() == null) ? 0 : getHouseTag().hashCode());
        result = prime * result + ((getHouseStatus() == null) ? 0 : getHouseStatus().hashCode());
        result = prime * result + ((getDealStatus() == null) ? 0 : getDealStatus().hashCode());
        result = prime * result + ((getIsReview() == null) ? 0 : getIsReview().hashCode());
        result = prime * result + ((getAreaCode() == null) ? 0 : getAreaCode().hashCode());
        result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
        result = prime * result + ((getAreaLongCode() == null) ? 0 : getAreaLongCode().hashCode());
        result = prime * result + ((getHouseKind() == null) ? 0 : getHouseKind().hashCode());
        result = prime * result + ((getAtt4() == null) ? 0 : getAtt4().hashCode());
        result = prime * result + ((getAtt5() == null) ? 0 : getAtt5().hashCode());
        result = prime * result + ((getAtt6() == null) ? 0 : getAtt6().hashCode());
        result = prime * result + ((getDateAtt1() == null) ? 0 : getDateAtt1().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getResetState() == null) ? 0 : getResetState().hashCode());
        result = prime * result + ((getNumAtt2() == null) ? 0 : getNumAtt2().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    /**
     * 获取对象的copy
     **/
    public BaseHouse copy() {
        BaseHouse obj = new BaseHouse();
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setIsPublic(this.isPublic);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setHouseCode(this.houseCode);
        obj.setHouseShortName(this.houseShortName);
        obj.setHouseName(this.houseName);
        obj.setPicRes1Id(this.picRes1Id);
        obj.setPicRes2Id(this.picRes2Id);
        obj.setPicRes3Id(this.picRes3Id);
        obj.setPicRes4Id(this.picRes4Id);
        obj.setHouseDesc(this.houseDesc);
        obj.setLivingId(this.livingId);
        obj.setTotalArea(this.totalArea);
        obj.setTotalFloors(this.totalFloors);
        obj.setCurrentPeriod(this.currentPeriod);
        obj.setHouseHigh(this.houseHigh);
        obj.setApplyFloors(this.applyFloors);
        obj.setHouseLayout(this.houseLayout);
        obj.setRoomLayout(this.roomLayout);
        obj.setOfficeLayout(this.officeLayout);
        obj.setWayLayout(this.wayLayout);
        obj.setIsExistSymmetry(this.isExistSymmetry);
        obj.setSymmetryIds(this.symmetryIds);
        obj.setIsMerge(this.isMerge);
        obj.setMergeIds(this.mergeIds);
        obj.setMergeNewId(this.mergeNewId);
        obj.setPicResId(this.picResId);
        obj.setIsAll(this.isAll);
        obj.setHouseCommonCode(this.houseCommonCode);
        obj.setHouseTypeCode(this.houseTypeCode);
        obj.setHouseTag(this.houseTag);
        obj.setHouseStatus(this.houseStatus);
        obj.setDealStatus(this.dealStatus);
        obj.setIsReview(this.isReview);
        obj.setAreaCode(this.areaCode);
        obj.setAtt1(this.att1);
        obj.setAreaLongCode(this.areaLongCode);
        obj.setHouseKind(this.houseKind);
        obj.setAtt4(this.att4);
        obj.setAtt5(this.att5);
        obj.setAtt6(this.att6);
        obj.setDateAtt1(this.dateAtt1);
        obj.setDateAtt2(this.dateAtt2);
        obj.setResetState(this.resetState);
        obj.setNumAtt2(this.numAtt2);
        obj.setNumAtt3(this.numAtt3);
        obj.setNumAtt4(this.numAtt4);
        obj.setRemark(this.remark);

        return obj;
    }

    /**
     * 获取对象的map
     **/
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sysCode", this.sysCode);
        map.put("creator", this.creator);
        map.put("isPublic", this.isPublic);
        map.put("gmtCreate", this.gmtCreate);
        map.put("modifier", this.modifier);
        map.put("gmtModified", this.gmtModified);
        map.put("isDeleted", this.isDeleted);
        map.put("houseCode", this.houseCode);
        map.put("houseShortName", this.houseShortName);
        map.put("houseName", this.houseName);
        map.put("picRes1Id", this.picRes1Id);
        map.put("picRes2Id", this.picRes2Id);
        map.put("picRes3Id", this.picRes3Id);
        map.put("picRes4Id", this.picRes4Id);
        map.put("houseDesc", this.houseDesc);
        map.put("livingId", this.livingId);
        map.put("totalArea", this.totalArea);
        map.put("totalFloors", this.totalFloors);
        map.put("currentPeriod", this.currentPeriod);
        map.put("houseHigh", this.houseHigh);
        map.put("applyFloors", this.applyFloors);
        map.put("houseLayout ", this.houseLayout);
        map.put("roomLayout", this.roomLayout);
        map.put("officeLayout", this.officeLayout);
        map.put("wayLayout", this.wayLayout);
        map.put("isExistSymmetry", this.isExistSymmetry);
        map.put("symmetryIds", this.symmetryIds);
        map.put("isMerge", this.isMerge);
        map.put("mergeIds", this.mergeIds);
        map.put("mergeNewId", this.mergeNewId);
        map.put("picResId", this.picResId);
        map.put("isAll", this.isAll);
        map.put("houseCommonCode", this.houseCommonCode);
        map.put("houseTypeCode", this.houseTypeCode);
        map.put("houseTag", this.houseTag);
        map.put("houseStatus", this.houseStatus);
        map.put("dealStatus", this.dealStatus);
        map.put("isReview", this.isReview);
        map.put("areaCode", this.areaCode);
        map.put("att1", this.att1);
        map.put("areaLongCode", this.areaLongCode);
        map.put("houseKind", this.houseKind);
        map.put("att4", this.att4);
        map.put("att5", this.att5);
        map.put("att6", this.att6);
        map.put("dateAtt1", this.dateAtt1);
        map.put("dateAtt2", this.dateAtt2);
        map.put("resetState", this.resetState);
        map.put("numAtt2", this.numAtt2);
        map.put("numAtt3", this.numAtt3);
        map.put("numAtt4", this.numAtt4);
        map.put("remark", this.remark);

        return map;
    }

    /**
     * 获取对象的map
     **/
    public BaseHouse toObj(Map<String, Object> map) {
        BaseHouse obj = new BaseHouse();
        if (map != null && map.size() > 0) {
            obj.setSysCode((String) map.get("sysCode"));
            obj.setCreator((String) map.get("creator"));
            obj.setIsPublic((Integer) map.get("isPublic"));
            obj.setGmtCreate((Date) map.get("gmtCreate"));
            obj.setModifier((String) map.get("modifier"));
            obj.setGmtModified((Date) map.get("gmtModified"));
            obj.setIsDeleted((Integer) map.get("isDeleted"));
            obj.setHouseCode((String) map.get("houseCode"));
            obj.setHouseShortName((String) map.get("houseShortName"));
            obj.setHouseName((String) map.get("houseName"));
            obj.setPicRes1Id((Integer) map.get("picRes1Id"));
            obj.setPicRes2Id((Integer) map.get("picRes2Id"));
            obj.setPicRes3Id((Integer) map.get("picRes3Id"));
            obj.setPicRes4Id((Integer) map.get("picRes4Id"));
            obj.setHouseDesc((String) map.get("houseDesc"));
            obj.setLivingId((Integer) map.get("livingId"));
            obj.setTotalArea((String) map.get("totalArea"));
            obj.setTotalFloors((String) map.get("totalFloors"));
            obj.setCurrentPeriod((String) map.get("currentPeriod"));
            obj.setHouseHigh((String) map.get("houseHigh"));
            obj.setApplyFloors((String) map.get("applyFloors"));
            obj.setHouseLayout((String) map.get("houseLayout "));
            obj.setRoomLayout((String) map.get("roomLayout"));
            obj.setOfficeLayout((String) map.get("officeLayout"));
            obj.setWayLayout((String) map.get("wayLayout"));
            obj.setIsExistSymmetry((String) map.get("isExistSymmetry"));
            obj.setSymmetryIds((String) map.get("symmetryIds"));
            obj.setIsMerge((String) map.get("isMerge"));
            obj.setMergeIds((String) map.get("mergeIds"));
            obj.setMergeNewId((Integer) map.get("mergeNewId"));
            obj.setPicResId((Integer) map.get("picResId"));
            obj.setIsAll((Integer) map.get("isAll"));
            obj.setHouseCommonCode((String) map.get("houseCommonCode"));
            obj.setHouseTypeCode((String) map.get("houseTypeCode"));
            obj.setHouseTag((String) map.get("houseTag"));
            obj.setHouseStatus((String) map.get("houseStatus"));
            obj.setDealStatus((String) map.get("dealStatus"));
            obj.setIsReview((String) map.get("isReview"));
            obj.setAreaCode((String) map.get("areaCode"));
            obj.setAtt1((String) map.get("att1"));
            obj.setAreaLongCode((String) map.get("areaLongCode"));
            obj.setHouseKind((String) map.get("houseKind"));
            obj.setAtt4((String) map.get("att4"));
            obj.setAtt5((String) map.get("att5"));
            obj.setAtt6((String) map.get("att6"));
            obj.setDateAtt1((Date) map.get("dateAtt1"));
            obj.setDateAtt2((Date) map.get("dateAtt2"));
            obj.setResetState((Integer) map.get("resetState"));
            obj.setNumAtt2((Integer) map.get("numAtt2"));
            obj.setNumAtt3((Double) map.get("numAtt3"));
            obj.setNumAtt4((Double) map.get("numAtt4"));
            obj.setRemark((String) map.get("remark"));
        }
        return obj;
    }
}
