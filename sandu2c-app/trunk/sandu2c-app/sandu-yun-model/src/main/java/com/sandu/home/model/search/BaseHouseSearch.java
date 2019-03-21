package com.sandu.home.model.search;

import com.sandu.home.model.BaseHouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Title: BaseHouseSearch.java
 * @Package com.sandu.business.model
 * @Description:业务-户型查询对象
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 11:53:51
 */
public class BaseHouseSearch extends BaseHouse implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 系统编码-模糊查询
     **/
    private String sch_SysCode_;
    /**
     * 系统编码-左模糊查询
     **/
    private String sch_SysCode;
    /**
     * 系统编码-右模糊查询
     **/
    private String schSysCode_;
    /**
     * 系统编码-区间查询-开始字符串
     **/
    private String sysCodeStart;
    /**
     * 系统编码-区间查询-结束字符串
     **/
    private String sysCodeEnd;
    /**
     * 创建者-模糊查询
     **/
    private String sch_Creator_;
    /**
     * 创建者-左模糊查询
     **/
    private String sch_Creator;
    /**
     * 创建者-右模糊查询
     **/
    private String schCreator_;
    /**
     * 创建者-区间查询-开始字符串
     **/
    private String creatorStart;
    /**
     * 创建者-区间查询-结束字符串
     **/
    private String creatorEnd;
    /**
     * 创建时间-区间查询-开始时间
     **/
    private Date gmtCreateStart;
    /**
     * 创建时间-区间查询-结束时间
     **/
    private Date gmtCreateEnd;
    /**
     * 修改人-模糊查询
     **/
    private String sch_Modifier_;
    /**
     * 修改人-左模糊查询
     **/
    private String sch_Modifier;
    /**
     * 修改人-右模糊查询
     **/
    private String schModifier_;
    /**
     * 修改人-区间查询-开始字符串
     **/
    private String modifierStart;
    /**
     * 修改人-区间查询-结束字符串
     **/
    private String modifierEnd;
    /**
     * 修改时间-区间查询-开始时间
     **/
    private Date gmtModifiedStart;
    /**
     * 修改时间-区间查询-结束时间
     **/
    private Date gmtModifiedEnd;
    /**
     * 户型编码-模糊查询
     **/
    private String sch_HouseCode_;
    /**
     * 户型编码-左模糊查询
     **/
    private String sch_HouseCode;
    /**
     * 户型编码-右模糊查询
     **/
    private String schHouseCode_;
    /**
     * 户型编码-区间查询-开始字符串
     **/
    private String houseCodeStart;
    /**
     * 户型编码-区间查询-结束字符串
     **/
    private String houseCodeEnd;
    /**
     * 户型简称-模糊查询
     **/
    private String sch_HouseShortName_;
    /**
     * 户型简称-左模糊查询
     **/
    private String sch_HouseShortName;
    /**
     * 户型简称-右模糊查询
     **/
    private String schHouseShortName_;
    /**
     * 户型简称-区间查询-开始字符串
     **/
    private String houseShortNameStart;
    /**
     * 户型简称-区间查询-结束字符串
     **/
    private String houseShortNameEnd;
    /**
     * 户型全称-模糊查询
     **/
    private String sch_HouseName_;
    /**
     * 户型全称-左模糊查询
     **/
    private String sch_HouseName;
    /**
     * 户型全称-右模糊查询
     **/
    private String schHouseName_;
    /**
     * 户型全称-区间查询-开始字符串
     **/
    private String houseNameStart;
    /**
     * 户型全称-区间查询-结束字符串
     **/
    private String houseNameEnd;
    /**
     * 户型描述-模糊查询
     **/
    private String sch_HouseDesc_;
    /**
     * 户型描述-左模糊查询
     **/
    private String sch_HouseDesc;
    /**
     * 户型描述-右模糊查询
     **/
    private String schHouseDesc_;
    /**
     * 户型描述-区间查询-开始字符串
     **/
    private String houseDescStart;
    /**
     * 户型描述-区间查询-结束字符串
     **/
    private String houseDescEnd;
    /**
     * 总面积-模糊查询
     **/
    private String sch_TotalArea_;
    /**
     * 总面积-左模糊查询
     **/
    private String sch_TotalArea;
    /**
     * 总面积-右模糊查询
     **/
    private String schTotalArea_;
    /**
     * 总面积-区间查询-开始字符串
     **/
    private String totalAreaStart;
    /**
     * 总面积-区间查询-结束字符串
     **/
    private String totalAreaEnd;
    /**
     * 总楼层-模糊查询
     **/
    private String sch_TotalFloors_;
    /**
     * 总楼层-左模糊查询
     **/
    private String sch_TotalFloors;
    /**
     * 总楼层-右模糊查询
     **/
    private String schTotalFloors_;
    /**
     * 总楼层-区间查询-开始字符串
     **/
    private String totalFloorsStart;
    /**
     * 总楼层-区间查询-结束字符串
     **/
    private String totalFloorsEnd;
    /**
     * 销售期数-模糊查询
     **/
    private String sch_CurrentPeriod_;
    /**
     * 销售期数-左模糊查询
     **/
    private String sch_CurrentPeriod;
    /**
     * 销售期数-右模糊查询
     **/
    private String schCurrentPeriod_;
    /**
     * 销售期数-区间查询-开始字符串
     **/
    private String currentPeriodStart;
    /**
     * 销售期数-区间查询-结束字符串
     **/
    private String currentPeriodEnd;
    /**
     * 户型高度-模糊查询
     **/
    private String sch_HouseHigh_;
    /**
     * 户型高度-左模糊查询
     **/
    private String sch_HouseHigh;
    /**
     * 户型高度-右模糊查询
     **/
    private String schHouseHigh_;
    /**
     * 户型高度-区间查询-开始字符串
     **/
    private String houseHighStart;
    /**
     * 户型高度-区间查询-结束字符串
     **/
    private String houseHighEnd;
    /**
     * 适用楼层-模糊查询
     **/
    private String sch_ApplyFloors_;
    /**
     * 适用楼层-左模糊查询
     **/
    private String sch_ApplyFloors;
    /**
     * 适用楼层-右模糊查询
     **/
    private String schApplyFloors_;
    /**
     * 适用楼层-区间查询-开始字符串
     **/
    private String applyFloorsStart;
    /**
     * 适用楼层-区间查询-结束字符串
     **/
    private String applyFloorsEnd;
    /**
     * 户型结构-模糊查询
     **/
    private String sch_HouseLayout_;
    /**
     * 户型结构-左模糊查询
     **/
    private String sch_HouseLayout;
    /**
     * 户型结构-右模糊查询
     **/
    private String schHouseLayout_;
    /**
     * 户型结构-区间查询-开始字符串
     **/
    private String houseLayoutStart;
    /**
     * 户型结构-区间查询-结束字符串
     **/
    private String houseLayoutEnd;
    /**
     * 室结构类型-模糊查询
     **/
    private String sch_RoomLayout_;
    /**
     * 室结构类型-左模糊查询
     **/
    private String sch_RoomLayout;
    /**
     * 室结构类型-右模糊查询
     **/
    private String schRoomLayout_;
    /**
     * 室结构类型-区间查询-开始字符串
     **/
    private String roomLayoutStart;
    /**
     * 室结构类型-区间查询-结束字符串
     **/
    private String roomLayoutEnd;
    /**
     * 厅结构类型-模糊查询
     **/
    private String sch_OfficeLayout_;
    /**
     * 厅结构类型-左模糊查询
     **/
    private String sch_OfficeLayout;
    /**
     * 厅结构类型-右模糊查询
     **/
    private String schOfficeLayout_;
    /**
     * 厅结构类型-区间查询-开始字符串
     **/
    private String officeLayoutStart;
    /**
     * 厅结构类型-区间查询-结束字符串
     **/
    private String officeLayoutEnd;
    /**
     * 过道结构类型-模糊查询
     **/
    private String sch_WayLayout_;
    /**
     * 过道结构类型-左模糊查询
     **/
    private String sch_WayLayout;
    /**
     * 过道结构类型-右模糊查询
     **/
    private String schWayLayout_;
    /**
     * 过道结构类型-区间查询-开始字符串
     **/
    private String wayLayoutStart;
    /**
     * 过道结构类型-区间查询-结束字符串
     **/
    private String wayLayoutEnd;
    /**
     * 是否存在对称户型-模糊查询
     **/
    private String sch_IsExistSymmetry_;
    /**
     * 是否存在对称户型-左模糊查询
     **/
    private String sch_IsExistSymmetry;
    /**
     * 是否存在对称户型-右模糊查询
     **/
    private String schIsExistSymmetry_;
    /**
     * 是否存在对称户型-区间查询-开始字符串
     **/
    private String isExistSymmetryStart;
    /**
     * 是否存在对称户型-区间查询-结束字符串
     **/
    private String isExistSymmetryEnd;
    /**
     * 对称户型组ids-模糊查询
     **/
    private String sch_SymmetryIds_;
    /**
     * 对称户型组ids-左模糊查询
     **/
    private String sch_SymmetryIds;
    /**
     * 对称户型组ids-右模糊查询
     **/
    private String schSymmetryIds_;
    /**
     * 对称户型组ids-区间查询-开始字符串
     **/
    private String symmetryIdsStart;
    /**
     * 对称户型组ids-区间查询-结束字符串
     **/
    private String symmetryIdsEnd;
    /**
     * 是否合并-模糊查询
     **/
    private String sch_IsMerge_;
    /**
     * 是否合并-左模糊查询
     **/
    private String sch_IsMerge;
    /**
     * 是否合并-右模糊查询
     **/
    private String schIsMerge_;
    /**
     * 是否合并-区间查询-开始字符串
     **/
    private String isMergeStart;
    /**
     * 是否合并-区间查询-结束字符串
     **/
    private String isMergeEnd;
    /**
     * 合并户型组ids-模糊查询
     **/
    private String sch_MergeIds_;
    /**
     * 合并户型组ids-左模糊查询
     **/
    private String sch_MergeIds;
    /**
     * 合并户型组ids-右模糊查询
     **/
    private String schMergeIds_;
    /**
     * 合并户型组ids-区间查询-开始字符串
     **/
    private String mergeIdsStart;
    /**
     * 合并户型组ids-区间查询-结束字符串
     **/
    private String mergeIdsEnd;
    /**
     * 户型类型-模糊查询
     **/
    private String sch_HouseCommonCode_;
    /**
     * 户型类型-左模糊查询
     **/
    private String sch_HouseCommonCode;
    /**
     * 户型类型-右模糊查询
     **/
    private String schHouseCommonCode_;
    /**
     * 户型类型-区间查询-开始字符串
     **/
    private String houseCommonCodeStart;
    /**
     * 户型类型-区间查询-结束字符串
     **/
    private String houseCommonCodeEnd;
    /**
     * 户型标示-模糊查询
     **/
    private String sch_HouseTypeCode_;
    /**
     * 户型标示-左模糊查询
     **/
    private String sch_HouseTypeCode;
    /**
     * 户型标示-右模糊查询
     **/
    private String schHouseTypeCode_;
    /**
     * 户型标示-区间查询-开始字符串
     **/
    private String houseTypeCodeStart;
    /**
     * 户型标示-区间查询-结束字符串
     **/
    private String houseTypeCodeEnd;
    /**
     * 户型标签-模糊查询
     **/
    private String sch_HouseTag_;
    /**
     * 户型标签-左模糊查询
     **/
    private String sch_HouseTag;
    /**
     * 户型标签-右模糊查询
     **/
    private String schHouseTag_;
    /**
     * 户型标签-区间查询-开始字符串
     **/
    private String houseTagStart;
    /**
     * 户型标签-区间查询-结束字符串
     **/
    private String houseTagEnd;
    /**
     * 户型状态-模糊查询
     **/
    private String sch_HouseStatus_;
    /**
     * 户型状态-左模糊查询
     **/
    private String sch_HouseStatus;
    /**
     * 户型状态-右模糊查询
     **/
    private String schHouseStatus_;
    /**
     * 户型状态-区间查询-开始字符串
     **/
    private String houseStatusStart;
    /**
     * 户型状态-区间查询-结束字符串
     **/
    private String houseStatusEnd;
    /**
     * 处理状态-模糊查询
     **/
    private String sch_DealStatus_;
    /**
     * 处理状态-左模糊查询
     **/
    private String sch_DealStatus;
    /**
     * 处理状态-右模糊查询
     **/
    private String schDealStatus_;
    /**
     * 处理状态-区间查询-开始字符串
     **/
    private String dealStatusStart;
    /**
     * 处理状态-区间查询-结束字符串
     **/
    private String dealStatusEnd;
    /**
     * 是否审核-模糊查询
     **/
    private String sch_IsReview_;
    /**
     * 是否审核-左模糊查询
     **/
    private String sch_IsReview;
    /**
     * 是否审核-右模糊查询
     **/
    private String schIsReview_;
    /**
     * 是否审核-区间查询-开始字符串
     **/
    private String isReviewStart;
    /**
     * 是否审核-区间查询-结束字符串
     **/
    private String isReviewEnd;
    /**
     * 字符备用1-模糊查询
     **/
    private String sch_AreaCode_;
    /**
     * 字符备用1-左模糊查询
     **/
    private String sch_AreaCode;
    /**
     * 字符备用1-右模糊查询
     **/
    private String schAreaCode_;
    /**
     * 字符备用1-区间查询-开始字符串
     **/
    private String areaCodeStart;
    /**
     * 字符备用1-区间查询-结束字符串
     **/
    private String areaCodeEnd;
    /**
     * 字符备用2-模糊查询
     **/
    private String sch_Att1_;
    /**
     * 字符备用2-左模糊查询
     **/
    private String sch_Att1;
    /**
     * 字符备用2-右模糊查询
     **/
    private String schAtt1_;
    /**
     * 字符备用2-区间查询-开始字符串
     **/
    private String att1Start;
    /**
     * 字符备用2-区间查询-结束字符串
     **/
    private String att1End;
    /**
     * 字符备用2-模糊查询
     **/
    private String sch_AreaLongCode_;
    /**
     * 字符备用2-左模糊查询
     **/
    private String sch_AreaLongCode;
    /**
     * 字符备用2-右模糊查询
     **/
    private String schAreaLongCode_;
    /**
     * 字符备用2-区间查询-开始字符串
     **/
    private String areaLongCodeStart;
    /**
     * 字符备用2-区间查询-结束字符串
     **/
    private String areaLongCodeEnd;
    /**
     * 字符备用3-模糊查询
     **/
    private String sch_HouseKind_;
    /**
     * 字符备用3-左模糊查询
     **/
    private String sch_HouseKind;
    /**
     * 字符备用3-右模糊查询
     **/
    private String schHouseKind_;
    /**
     * 字符备用3-区间查询-开始字符串
     **/
    private String houseKindStart;
    /**
     * 字符备用3-区间查询-结束字符串
     **/
    private String houseKindEnd;
    /**
     * 字符备用4-模糊查询
     **/
    private String sch_Att4_;
    /**
     * 字符备用4-左模糊查询
     **/
    private String sch_Att4;
    /**
     * 字符备用4-右模糊查询
     **/
    private String schAtt4_;
    /**
     * 字符备用4-区间查询-开始字符串
     **/
    private String att4Start;
    /**
     * 字符备用4-区间查询-结束字符串
     **/
    private String att4End;
    /**
     * 字符备用5-模糊查询
     **/
    private String sch_Att5_;
    /**
     * 字符备用5-左模糊查询
     **/
    private String sch_Att5;
    /**
     * 字符备用5-右模糊查询
     **/
    private String schAtt5_;
    /**
     * 字符备用5-区间查询-开始字符串
     **/
    private String att5Start;
    /**
     * 字符备用5-区间查询-结束字符串
     **/
    private String att5End;
    /**
     * 字符备用6-模糊查询
     **/
    private String sch_Att6_;
    /**
     * 字符备用6-左模糊查询
     **/
    private String sch_Att6;
    /**
     * 字符备用6-右模糊查询
     **/
    private String schAtt6_;
    /**
     * 字符备用6-区间查询-开始字符串
     **/
    private String att6Start;
    /**
     * 字符备用6-区间查询-结束字符串
     **/
    private String att6End;
    /**
     * 时间备用1-区间查询-开始时间
     **/
    private Date dateAtt1Start;
    /**
     * 时间备用1-区间查询-结束时间
     **/
    private Date dateAtt1End;
    /**
     * 时间备用2-区间查询-开始时间
     **/
    private Date dateAtt2Start;
    /**
     * 时间备用2-区间查询-结束时间
     **/
    private Date dateAtt2;
    /**
     * 备注-模糊查询
     **/
    private String sch_Remark_;
    /**
     * 备注-左模糊查询
     **/
    private String sch_Remark;
    /**
     * 备注-右模糊查询
     **/
    private String schRemark_;
    /**
     * 备注-区间查询-开始字符串
     **/
    private String remarkStart;
    /**
     * 备注-区间查询-结束字符串
     **/
    private String remarkEnd;
    private String areaCode_p;
    private String areaCode_c;
    private String areaCode_a;
    //户型编码
    private String houseDoorCode;
    private Integer isClose;

    /**
     * 户型id
     * add by yangz
     * 2018年1月17日16:07:18
     */
    private Integer houseId;

    public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

	@Override
    public String getHouseDoorCode() {
        return houseDoorCode;
    }

    @Override
    public void setHouseDoorCode(String houseDoorCode) {
        this.houseDoorCode = houseDoorCode;
    }

    public Integer getIsClose() {
        return isClose;
    }

    public void setIsClose(Integer isClose) {
        this.isClose = isClose;
    }

    private List<Integer> livingIds = new ArrayList<Integer>();


    public List<Integer> getLivingIds() {
        return livingIds;
    }

    public void setLivingIds(List<Integer> livingIds) {
        this.livingIds = livingIds;
    }

    @Override
    public String getAreaCode_p() {
        return areaCode_p;
    }

    @Override
    public void setAreaCode_p(String areaCode_p) {
        this.areaCode_p = areaCode_p;
    }

    @Override
    public String getAreaCode_c() {
        return areaCode_c;
    }

    @Override
    public void setAreaCode_c(String areaCode_c) {
        this.areaCode_c = areaCode_c;
    }

    @Override
    public String getAreaCode_a() {
        return areaCode_a;
    }

    @Override
    public void setAreaCode_a(String areaCode_a) {
        this.areaCode_a = areaCode_a;
    }

    public String getSch_SysCode_() {
        return sch_SysCode_;
    }

    public void setSch_SysCode_(String sch_SysCode_) {
        this.sch_SysCode_ = sch_SysCode_;
    }

    public String getSch_SysCode() {
        return sch_SysCode;
    }

    public void setSch_SysCode(String sch_SysCode) {
        this.sch_SysCode = sch_SysCode;
    }

    public String getSchSysCode_() {
        return schSysCode_;
    }

    public void setSchSysCode_(String schSysCode_) {
        this.schSysCode_ = schSysCode_;
    }

    public String getSysCodeStart() {
        return sysCodeStart;
    }

    public void setSysCodeStart(String sysCodeStart) {
        this.sysCodeStart = sysCodeStart;
    }

    public String getSysCodeEnd() {
        return sysCodeEnd;
    }

    public void setSysCodeEnd(String sysCodeEnd) {
        this.sysCodeEnd = sysCodeEnd;
    }

    public String getSch_Creator_() {
        return sch_Creator_;
    }

    public void setSch_Creator_(String sch_Creator_) {
        this.sch_Creator_ = sch_Creator_;
    }

    public String getSch_Creator() {
        return sch_Creator;
    }

    public void setSch_Creator(String sch_Creator) {
        this.sch_Creator = sch_Creator;
    }

    public String getSchCreator_() {
        return schCreator_;
    }

    public void setSchCreator_(String schCreator_) {
        this.schCreator_ = schCreator_;
    }

    public String getCreatorStart() {
        return creatorStart;
    }

    public void setCreatorStart(String creatorStart) {
        this.creatorStart = creatorStart;
    }

    public String getCreatorEnd() {
        return creatorEnd;
    }

    public void setCreatorEnd(String creatorEnd) {
        this.creatorEnd = creatorEnd;
    }

    public Date getGmtCreateStart() {
        return gmtCreateStart;
    }

    public void setGmtCreateStart(Date gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
    }

    public Date getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public void setGmtCreateEnd(Date gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
    }

    public String getSch_Modifier_() {
        return sch_Modifier_;
    }

    public void setSch_Modifier_(String sch_Modifier_) {
        this.sch_Modifier_ = sch_Modifier_;
    }

    public String getSch_Modifier() {
        return sch_Modifier;
    }

    public void setSch_Modifier(String sch_Modifier) {
        this.sch_Modifier = sch_Modifier;
    }

    public String getSchModifier_() {
        return schModifier_;
    }

    public void setSchModifier_(String schModifier_) {
        this.schModifier_ = schModifier_;
    }

    public String getModifierStart() {
        return modifierStart;
    }

    public void setModifierStart(String modifierStart) {
        this.modifierStart = modifierStart;
    }

    public String getModifierEnd() {
        return modifierEnd;
    }

    public void setModifierEnd(String modifierEnd) {
        this.modifierEnd = modifierEnd;
    }

    public Date getGmtModifiedStart() {
        return gmtModifiedStart;
    }

    public void setGmtModifiedStart(Date gmtModifiedStart) {
        this.gmtModifiedStart = gmtModifiedStart;
    }

    public Date getGmtModifiedEnd() {
        return gmtModifiedEnd;
    }

    public void setGmtModifiedEnd(Date gmtModifiedEnd) {
        this.gmtModifiedEnd = gmtModifiedEnd;
    }

    public String getSch_HouseCode_() {
        return sch_HouseCode_;
    }

    public void setSch_HouseCode_(String sch_HouseCode_) {
        this.sch_HouseCode_ = sch_HouseCode_;
    }

    public String getSch_HouseCode() {
        return sch_HouseCode;
    }

    public void setSch_HouseCode(String sch_HouseCode) {
        this.sch_HouseCode = sch_HouseCode;
    }

    public String getSchHouseCode_() {
        return schHouseCode_;
    }

    public void setSchHouseCode_(String schHouseCode_) {
        this.schHouseCode_ = schHouseCode_;
    }

    public String getHouseCodeStart() {
        return houseCodeStart;
    }

    public void setHouseCodeStart(String houseCodeStart) {
        this.houseCodeStart = houseCodeStart;
    }

    public String getHouseCodeEnd() {
        return houseCodeEnd;
    }

    public void setHouseCodeEnd(String houseCodeEnd) {
        this.houseCodeEnd = houseCodeEnd;
    }

    public String getSch_HouseShortName_() {
        return sch_HouseShortName_;
    }

    public void setSch_HouseShortName_(String sch_HouseShortName_) {
        this.sch_HouseShortName_ = sch_HouseShortName_;
    }

    public String getSch_HouseShortName() {
        return sch_HouseShortName;
    }

    public void setSch_HouseShortName(String sch_HouseShortName) {
        this.sch_HouseShortName = sch_HouseShortName;
    }

    public String getSchHouseShortName_() {
        return schHouseShortName_;
    }

    public void setSchHouseShortName_(String schHouseShortName_) {
        this.schHouseShortName_ = schHouseShortName_;
    }

    public String getHouseShortNameStart() {
        return houseShortNameStart;
    }

    public void setHouseShortNameStart(String houseShortNameStart) {
        this.houseShortNameStart = houseShortNameStart;
    }

    public String getHouseShortNameEnd() {
        return houseShortNameEnd;
    }

    public void setHouseShortNameEnd(String houseShortNameEnd) {
        this.houseShortNameEnd = houseShortNameEnd;
    }

    public String getSch_HouseName_() {
        return sch_HouseName_;
    }

    public void setSch_HouseName_(String sch_HouseName_) {
        this.sch_HouseName_ = sch_HouseName_;
    }

    public String getSch_HouseName() {
        return sch_HouseName;
    }

    public void setSch_HouseName(String sch_HouseName) {
        this.sch_HouseName = sch_HouseName;
    }

    public String getSchHouseName_() {
        return schHouseName_;
    }

    public void setSchHouseName_(String schHouseName_) {
        this.schHouseName_ = schHouseName_;
    }

    public String getHouseNameStart() {
        return houseNameStart;
    }

    public void setHouseNameStart(String houseNameStart) {
        this.houseNameStart = houseNameStart;
    }

    public String getHouseNameEnd() {
        return houseNameEnd;
    }

    public void setHouseNameEnd(String houseNameEnd) {
        this.houseNameEnd = houseNameEnd;
    }

    public String getSch_HouseDesc_() {
        return sch_HouseDesc_;
    }

    public void setSch_HouseDesc_(String sch_HouseDesc_) {
        this.sch_HouseDesc_ = sch_HouseDesc_;
    }

    public String getSch_HouseDesc() {
        return sch_HouseDesc;
    }

    public void setSch_HouseDesc(String sch_HouseDesc) {
        this.sch_HouseDesc = sch_HouseDesc;
    }

    public String getSchHouseDesc_() {
        return schHouseDesc_;
    }

    public void setSchHouseDesc_(String schHouseDesc_) {
        this.schHouseDesc_ = schHouseDesc_;
    }

    public String getHouseDescStart() {
        return houseDescStart;
    }

    public void setHouseDescStart(String houseDescStart) {
        this.houseDescStart = houseDescStart;
    }

    public String getHouseDescEnd() {
        return houseDescEnd;
    }

    public void setHouseDescEnd(String houseDescEnd) {
        this.houseDescEnd = houseDescEnd;
    }

    public String getSch_TotalArea_() {
        return sch_TotalArea_;
    }

    public void setSch_TotalArea_(String sch_TotalArea_) {
        this.sch_TotalArea_ = sch_TotalArea_;
    }

    public String getSch_TotalArea() {
        return sch_TotalArea;
    }

    public void setSch_TotalArea(String sch_TotalArea) {
        this.sch_TotalArea = sch_TotalArea;
    }

    public String getSchTotalArea_() {
        return schTotalArea_;
    }

    public void setSchTotalArea_(String schTotalArea_) {
        this.schTotalArea_ = schTotalArea_;
    }

    public String getTotalAreaStart() {
        return totalAreaStart;
    }

    public void setTotalAreaStart(String totalAreaStart) {
        this.totalAreaStart = totalAreaStart;
    }

    public String getTotalAreaEnd() {
        return totalAreaEnd;
    }

    public void setTotalAreaEnd(String totalAreaEnd) {
        this.totalAreaEnd = totalAreaEnd;
    }

    public String getSch_TotalFloors_() {
        return sch_TotalFloors_;
    }

    public void setSch_TotalFloors_(String sch_TotalFloors_) {
        this.sch_TotalFloors_ = sch_TotalFloors_;
    }

    public String getSch_TotalFloors() {
        return sch_TotalFloors;
    }

    public void setSch_TotalFloors(String sch_TotalFloors) {
        this.sch_TotalFloors = sch_TotalFloors;
    }

    public String getSchTotalFloors_() {
        return schTotalFloors_;
    }

    public void setSchTotalFloors_(String schTotalFloors_) {
        this.schTotalFloors_ = schTotalFloors_;
    }

    public String getTotalFloorsStart() {
        return totalFloorsStart;
    }

    public void setTotalFloorsStart(String totalFloorsStart) {
        this.totalFloorsStart = totalFloorsStart;
    }

    public String getTotalFloorsEnd() {
        return totalFloorsEnd;
    }

    public void setTotalFloorsEnd(String totalFloorsEnd) {
        this.totalFloorsEnd = totalFloorsEnd;
    }

    public String getSch_CurrentPeriod_() {
        return sch_CurrentPeriod_;
    }

    public void setSch_CurrentPeriod_(String sch_CurrentPeriod_) {
        this.sch_CurrentPeriod_ = sch_CurrentPeriod_;
    }

    public String getSch_CurrentPeriod() {
        return sch_CurrentPeriod;
    }

    public void setSch_CurrentPeriod(String sch_CurrentPeriod) {
        this.sch_CurrentPeriod = sch_CurrentPeriod;
    }

    public String getSchCurrentPeriod_() {
        return schCurrentPeriod_;
    }

    public void setSchCurrentPeriod_(String schCurrentPeriod_) {
        this.schCurrentPeriod_ = schCurrentPeriod_;
    }

    public String getCurrentPeriodStart() {
        return currentPeriodStart;
    }

    public void setCurrentPeriodStart(String currentPeriodStart) {
        this.currentPeriodStart = currentPeriodStart;
    }

    public String getCurrentPeriodEnd() {
        return currentPeriodEnd;
    }

    public void setCurrentPeriodEnd(String currentPeriodEnd) {
        this.currentPeriodEnd = currentPeriodEnd;
    }

    public String getSch_HouseHigh_() {
        return sch_HouseHigh_;
    }

    public void setSch_HouseHigh_(String sch_HouseHigh_) {
        this.sch_HouseHigh_ = sch_HouseHigh_;
    }

    public String getSch_HouseHigh() {
        return sch_HouseHigh;
    }

    public void setSch_HouseHigh(String sch_HouseHigh) {
        this.sch_HouseHigh = sch_HouseHigh;
    }

    public String getSchHouseHigh_() {
        return schHouseHigh_;
    }

    public void setSchHouseHigh_(String schHouseHigh_) {
        this.schHouseHigh_ = schHouseHigh_;
    }

    public String getHouseHighStart() {
        return houseHighStart;
    }

    public void setHouseHighStart(String houseHighStart) {
        this.houseHighStart = houseHighStart;
    }

    public String getHouseHighEnd() {
        return houseHighEnd;
    }

    public void setHouseHighEnd(String houseHighEnd) {
        this.houseHighEnd = houseHighEnd;
    }

    public String getSch_ApplyFloors_() {
        return sch_ApplyFloors_;
    }

    public void setSch_ApplyFloors_(String sch_ApplyFloors_) {
        this.sch_ApplyFloors_ = sch_ApplyFloors_;
    }

    public String getSch_ApplyFloors() {
        return sch_ApplyFloors;
    }

    public void setSch_ApplyFloors(String sch_ApplyFloors) {
        this.sch_ApplyFloors = sch_ApplyFloors;
    }

    public String getSchApplyFloors_() {
        return schApplyFloors_;
    }

    public void setSchApplyFloors_(String schApplyFloors_) {
        this.schApplyFloors_ = schApplyFloors_;
    }

    public String getApplyFloorsStart() {
        return applyFloorsStart;
    }

    public void setApplyFloorsStart(String applyFloorsStart) {
        this.applyFloorsStart = applyFloorsStart;
    }

    public String getApplyFloorsEnd() {
        return applyFloorsEnd;
    }

    public void setApplyFloorsEnd(String applyFloorsEnd) {
        this.applyFloorsEnd = applyFloorsEnd;
    }

    public String getSch_HouseLayout_() {
        return sch_HouseLayout_;
    }

    public void setSch_HouseLayout_(String sch_HouseLayout_) {
        this.sch_HouseLayout_ = sch_HouseLayout_;
    }

    public String getSch_HouseLayout() {
        return sch_HouseLayout;
    }

    public void setSch_HouseLayout(String sch_HouseLayout) {
        this.sch_HouseLayout = sch_HouseLayout;
    }

    public String getSchHouseLayout_() {
        return schHouseLayout_;
    }

    public void setSchHouseLayout_(String schHouseLayout_) {
        this.schHouseLayout_ = schHouseLayout_;
    }

    public String getHouseLayoutStart() {
        return houseLayoutStart;
    }

    public void setHouseLayoutStart(String houseLayoutStart) {
        this.houseLayoutStart = houseLayoutStart;
    }

    public String getHouseLayoutEnd() {
        return houseLayoutEnd;
    }

    public void setHouseLayoutEnd(String houseLayoutEnd) {
        this.houseLayoutEnd = houseLayoutEnd;
    }

    public String getSch_RoomLayout_() {
        return sch_RoomLayout_;
    }

    public void setSch_RoomLayout_(String sch_RoomLayout_) {
        this.sch_RoomLayout_ = sch_RoomLayout_;
    }

    public String getSch_RoomLayout() {
        return sch_RoomLayout;
    }

    public void setSch_RoomLayout(String sch_RoomLayout) {
        this.sch_RoomLayout = sch_RoomLayout;
    }

    public String getSchRoomLayout_() {
        return schRoomLayout_;
    }

    public void setSchRoomLayout_(String schRoomLayout_) {
        this.schRoomLayout_ = schRoomLayout_;
    }

    public String getRoomLayoutStart() {
        return roomLayoutStart;
    }

    public void setRoomLayoutStart(String roomLayoutStart) {
        this.roomLayoutStart = roomLayoutStart;
    }

    public String getRoomLayoutEnd() {
        return roomLayoutEnd;
    }

    public void setRoomLayoutEnd(String roomLayoutEnd) {
        this.roomLayoutEnd = roomLayoutEnd;
    }

    public String getSch_OfficeLayout_() {
        return sch_OfficeLayout_;
    }

    public void setSch_OfficeLayout_(String sch_OfficeLayout_) {
        this.sch_OfficeLayout_ = sch_OfficeLayout_;
    }

    public String getSch_OfficeLayout() {
        return sch_OfficeLayout;
    }

    public void setSch_OfficeLayout(String sch_OfficeLayout) {
        this.sch_OfficeLayout = sch_OfficeLayout;
    }

    public String getSchOfficeLayout_() {
        return schOfficeLayout_;
    }

    public void setSchOfficeLayout_(String schOfficeLayout_) {
        this.schOfficeLayout_ = schOfficeLayout_;
    }

    public String getOfficeLayoutStart() {
        return officeLayoutStart;
    }

    public void setOfficeLayoutStart(String officeLayoutStart) {
        this.officeLayoutStart = officeLayoutStart;
    }

    public String getOfficeLayoutEnd() {
        return officeLayoutEnd;
    }

    public void setOfficeLayoutEnd(String officeLayoutEnd) {
        this.officeLayoutEnd = officeLayoutEnd;
    }

    public String getSch_WayLayout_() {
        return sch_WayLayout_;
    }

    public void setSch_WayLayout_(String sch_WayLayout_) {
        this.sch_WayLayout_ = sch_WayLayout_;
    }

    public String getSch_WayLayout() {
        return sch_WayLayout;
    }

    public void setSch_WayLayout(String sch_WayLayout) {
        this.sch_WayLayout = sch_WayLayout;
    }

    public String getSchWayLayout_() {
        return schWayLayout_;
    }

    public void setSchWayLayout_(String schWayLayout_) {
        this.schWayLayout_ = schWayLayout_;
    }

    public String getWayLayoutStart() {
        return wayLayoutStart;
    }

    public void setWayLayoutStart(String wayLayoutStart) {
        this.wayLayoutStart = wayLayoutStart;
    }

    public String getWayLayoutEnd() {
        return wayLayoutEnd;
    }

    public void setWayLayoutEnd(String wayLayoutEnd) {
        this.wayLayoutEnd = wayLayoutEnd;
    }

    public String getSch_IsExistSymmetry_() {
        return sch_IsExistSymmetry_;
    }

    public void setSch_IsExistSymmetry_(String sch_IsExistSymmetry_) {
        this.sch_IsExistSymmetry_ = sch_IsExistSymmetry_;
    }

    public String getSch_IsExistSymmetry() {
        return sch_IsExistSymmetry;
    }

    public void setSch_IsExistSymmetry(String sch_IsExistSymmetry) {
        this.sch_IsExistSymmetry = sch_IsExistSymmetry;
    }

    public String getSchIsExistSymmetry_() {
        return schIsExistSymmetry_;
    }

    public void setSchIsExistSymmetry_(String schIsExistSymmetry_) {
        this.schIsExistSymmetry_ = schIsExistSymmetry_;
    }

    public String getIsExistSymmetryStart() {
        return isExistSymmetryStart;
    }

    public void setIsExistSymmetryStart(String isExistSymmetryStart) {
        this.isExistSymmetryStart = isExistSymmetryStart;
    }

    public String getIsExistSymmetryEnd() {
        return isExistSymmetryEnd;
    }

    public void setIsExistSymmetryEnd(String isExistSymmetryEnd) {
        this.isExistSymmetryEnd = isExistSymmetryEnd;
    }

    public String getSch_SymmetryIds_() {
        return sch_SymmetryIds_;
    }

    public void setSch_SymmetryIds_(String sch_SymmetryIds_) {
        this.sch_SymmetryIds_ = sch_SymmetryIds_;
    }

    public String getSch_SymmetryIds() {
        return sch_SymmetryIds;
    }

    public void setSch_SymmetryIds(String sch_SymmetryIds) {
        this.sch_SymmetryIds = sch_SymmetryIds;
    }

    public String getSchSymmetryIds_() {
        return schSymmetryIds_;
    }

    public void setSchSymmetryIds_(String schSymmetryIds_) {
        this.schSymmetryIds_ = schSymmetryIds_;
    }

    public String getSymmetryIdsStart() {
        return symmetryIdsStart;
    }

    public void setSymmetryIdsStart(String symmetryIdsStart) {
        this.symmetryIdsStart = symmetryIdsStart;
    }

    public String getSymmetryIdsEnd() {
        return symmetryIdsEnd;
    }

    public void setSymmetryIdsEnd(String symmetryIdsEnd) {
        this.symmetryIdsEnd = symmetryIdsEnd;
    }

    public String getSch_IsMerge_() {
        return sch_IsMerge_;
    }

    public void setSch_IsMerge_(String sch_IsMerge_) {
        this.sch_IsMerge_ = sch_IsMerge_;
    }

    public String getSch_IsMerge() {
        return sch_IsMerge;
    }

    public void setSch_IsMerge(String sch_IsMerge) {
        this.sch_IsMerge = sch_IsMerge;
    }

    public String getSchIsMerge_() {
        return schIsMerge_;
    }

    public void setSchIsMerge_(String schIsMerge_) {
        this.schIsMerge_ = schIsMerge_;
    }

    public String getIsMergeStart() {
        return isMergeStart;
    }

    public void setIsMergeStart(String isMergeStart) {
        this.isMergeStart = isMergeStart;
    }

    public String getIsMergeEnd() {
        return isMergeEnd;
    }

    public void setIsMergeEnd(String isMergeEnd) {
        this.isMergeEnd = isMergeEnd;
    }

    public String getSch_MergeIds_() {
        return sch_MergeIds_;
    }

    public void setSch_MergeIds_(String sch_MergeIds_) {
        this.sch_MergeIds_ = sch_MergeIds_;
    }

    public String getSch_MergeIds() {
        return sch_MergeIds;
    }

    public void setSch_MergeIds(String sch_MergeIds) {
        this.sch_MergeIds = sch_MergeIds;
    }

    public String getSchMergeIds_() {
        return schMergeIds_;
    }

    public void setSchMergeIds_(String schMergeIds_) {
        this.schMergeIds_ = schMergeIds_;
    }

    public String getMergeIdsStart() {
        return mergeIdsStart;
    }

    public void setMergeIdsStart(String mergeIdsStart) {
        this.mergeIdsStart = mergeIdsStart;
    }

    public String getMergeIdsEnd() {
        return mergeIdsEnd;
    }

    public void setMergeIdsEnd(String mergeIdsEnd) {
        this.mergeIdsEnd = mergeIdsEnd;
    }

    public String getSch_HouseCommonCode_() {
        return sch_HouseCommonCode_;
    }

    public void setSch_HouseCommonCode_(String sch_HouseCommonCode_) {
        this.sch_HouseCommonCode_ = sch_HouseCommonCode_;
    }

    public String getSch_HouseCommonCode() {
        return sch_HouseCommonCode;
    }

    public void setSch_HouseCommonCode(String sch_HouseCommonCode) {
        this.sch_HouseCommonCode = sch_HouseCommonCode;
    }

    public String getSchHouseCommonCode_() {
        return schHouseCommonCode_;
    }

    public void setSchHouseCommonCode_(String schHouseCommonCode_) {
        this.schHouseCommonCode_ = schHouseCommonCode_;
    }

    public String getHouseCommonCodeStart() {
        return houseCommonCodeStart;
    }

    public void setHouseCommonCodeStart(String houseCommonCodeStart) {
        this.houseCommonCodeStart = houseCommonCodeStart;
    }

    public String getHouseCommonCodeEnd() {
        return houseCommonCodeEnd;
    }

    public void setHouseCommonCodeEnd(String houseCommonCodeEnd) {
        this.houseCommonCodeEnd = houseCommonCodeEnd;
    }

    public String getSch_HouseTypeCode_() {
        return sch_HouseTypeCode_;
    }

    public void setSch_HouseTypeCode_(String sch_HouseTypeCode_) {
        this.sch_HouseTypeCode_ = sch_HouseTypeCode_;
    }

    public String getSch_HouseTypeCode() {
        return sch_HouseTypeCode;
    }

    public void setSch_HouseTypeCode(String sch_HouseTypeCode) {
        this.sch_HouseTypeCode = sch_HouseTypeCode;
    }

    public String getSchHouseTypeCode_() {
        return schHouseTypeCode_;
    }

    public void setSchHouseTypeCode_(String schHouseTypeCode_) {
        this.schHouseTypeCode_ = schHouseTypeCode_;
    }

    public String getHouseTypeCodeStart() {
        return houseTypeCodeStart;
    }

    public void setHouseTypeCodeStart(String houseTypeCodeStart) {
        this.houseTypeCodeStart = houseTypeCodeStart;
    }

    public String getHouseTypeCodeEnd() {
        return houseTypeCodeEnd;
    }

    public void setHouseTypeCodeEnd(String houseTypeCodeEnd) {
        this.houseTypeCodeEnd = houseTypeCodeEnd;
    }

    public String getSch_HouseTag_() {
        return sch_HouseTag_;
    }

    public void setSch_HouseTag_(String sch_HouseTag_) {
        this.sch_HouseTag_ = sch_HouseTag_;
    }

    public String getSch_HouseTag() {
        return sch_HouseTag;
    }

    public void setSch_HouseTag(String sch_HouseTag) {
        this.sch_HouseTag = sch_HouseTag;
    }

    public String getSchHouseTag_() {
        return schHouseTag_;
    }

    public void setSchHouseTag_(String schHouseTag_) {
        this.schHouseTag_ = schHouseTag_;
    }

    public String getHouseTagStart() {
        return houseTagStart;
    }

    public void setHouseTagStart(String houseTagStart) {
        this.houseTagStart = houseTagStart;
    }

    public String getHouseTagEnd() {
        return houseTagEnd;
    }

    public void setHouseTagEnd(String houseTagEnd) {
        this.houseTagEnd = houseTagEnd;
    }

    public String getSch_HouseStatus_() {
        return sch_HouseStatus_;
    }

    public void setSch_HouseStatus_(String sch_HouseStatus_) {
        this.sch_HouseStatus_ = sch_HouseStatus_;
    }

    public String getSch_HouseStatus() {
        return sch_HouseStatus;
    }

    public void setSch_HouseStatus(String sch_HouseStatus) {
        this.sch_HouseStatus = sch_HouseStatus;
    }

    public String getSchHouseStatus_() {
        return schHouseStatus_;
    }

    public void setSchHouseStatus_(String schHouseStatus_) {
        this.schHouseStatus_ = schHouseStatus_;
    }

    public String getHouseStatusStart() {
        return houseStatusStart;
    }

    public void setHouseStatusStart(String houseStatusStart) {
        this.houseStatusStart = houseStatusStart;
    }

    public String getHouseStatusEnd() {
        return houseStatusEnd;
    }

    public void setHouseStatusEnd(String houseStatusEnd) {
        this.houseStatusEnd = houseStatusEnd;
    }

    public String getSch_DealStatus_() {
        return sch_DealStatus_;
    }

    public void setSch_DealStatus_(String sch_DealStatus_) {
        this.sch_DealStatus_ = sch_DealStatus_;
    }

    public String getSch_DealStatus() {
        return sch_DealStatus;
    }

    public void setSch_DealStatus(String sch_DealStatus) {
        this.sch_DealStatus = sch_DealStatus;
    }

    public String getSchDealStatus_() {
        return schDealStatus_;
    }

    public void setSchDealStatus_(String schDealStatus_) {
        this.schDealStatus_ = schDealStatus_;
    }

    public String getDealStatusStart() {
        return dealStatusStart;
    }

    public void setDealStatusStart(String dealStatusStart) {
        this.dealStatusStart = dealStatusStart;
    }

    public String getDealStatusEnd() {
        return dealStatusEnd;
    }

    public void setDealStatusEnd(String dealStatusEnd) {
        this.dealStatusEnd = dealStatusEnd;
    }

    public String getSch_IsReview_() {
        return sch_IsReview_;
    }

    public void setSch_IsReview_(String sch_IsReview_) {
        this.sch_IsReview_ = sch_IsReview_;
    }

    public String getSch_IsReview() {
        return sch_IsReview;
    }

    public void setSch_IsReview(String sch_IsReview) {
        this.sch_IsReview = sch_IsReview;
    }

    public String getSchIsReview_() {
        return schIsReview_;
    }

    public void setSchIsReview_(String schIsReview_) {
        this.schIsReview_ = schIsReview_;
    }

    public String getIsReviewStart() {
        return isReviewStart;
    }

    public void setIsReviewStart(String isReviewStart) {
        this.isReviewStart = isReviewStart;
    }

    public String getIsReviewEnd() {
        return isReviewEnd;
    }

    public void setIsReviewEnd(String isReviewEnd) {
        this.isReviewEnd = isReviewEnd;
    }

    public String getSch_AreaCode_() {
        return sch_AreaCode_;
    }

    public void setSch_AreaCode_(String sch_AreaCode_) {
        this.sch_AreaCode_ = sch_AreaCode_;
    }

    public String getSch_AreaCode() {
        return sch_AreaCode;
    }

    public void setSch_AreaCode(String sch_AreaCode) {
        this.sch_AreaCode = sch_AreaCode;
    }

    public String getSchAreaCode_() {
        return schAreaCode_;
    }

    public void setSchAreaCode_(String schAreaCode_) {
        this.schAreaCode_ = schAreaCode_;
    }

    public String getAreaCodeStart() {
        return areaCodeStart;
    }

    public void setAreaCodeStart(String areaCodeStart) {
        this.areaCodeStart = areaCodeStart;
    }

    public String getAreaCodeEnd() {
        return areaCodeEnd;
    }

    public void setAreaCodeEnd(String areaCodeEnd) {
        this.areaCodeEnd = areaCodeEnd;
    }

    public String getSch_Att1_() {
        return sch_Att1_;
    }

    public void setSch_Att1_(String sch_Att1_) {
        this.sch_Att1_ = sch_Att1_;
    }

    public String getSch_Att1() {
        return sch_Att1;
    }

    public void setSch_Att1(String sch_Att1) {
        this.sch_Att1 = sch_Att1;
    }

    public String getSchAtt1_() {
        return schAtt1_;
    }

    public void setSchAtt1_(String schAtt1_) {
        this.schAtt1_ = schAtt1_;
    }

    public String getAtt1Start() {
        return att1Start;
    }

    public void setAtt1Start(String att1Start) {
        this.att1Start = att1Start;
    }

    public String getAtt1End() {
        return att1End;
    }

    public void setAtt1End(String att1End) {
        this.att1End = att1End;
    }

    public String getSch_AreaLongCode_() {
        return sch_AreaLongCode_;
    }

    public void setSch_AreaLongCode_(String sch_AreaLongCode_) {
        this.sch_AreaLongCode_ = sch_AreaLongCode_;
    }

    public String getSch_AreaLongCode() {
        return sch_AreaLongCode;
    }

    public void setSch_AreaLongCode(String sch_AreaLongCode) {
        this.sch_AreaLongCode = sch_AreaLongCode;
    }

    public String getSchAreaLongCode_() {
        return schAreaLongCode_;
    }

    public void setSchAreaLongCode_(String schAreaLongCode_) {
        this.schAreaLongCode_ = schAreaLongCode_;
    }

    public String getAreaLongCodeStart() {
        return areaLongCodeStart;
    }

    public void setAreaLongCodeStart(String areaLongCodeStart) {
        this.areaLongCodeStart = areaLongCodeStart;
    }

    public String getAreaLongCodeEnd() {
        return areaLongCodeEnd;
    }

    public void setAreaLongCodeEnd(String areaLongCodeEnd) {
        this.areaLongCodeEnd = areaLongCodeEnd;
    }

    public String getSch_HouseKind_() {
        return sch_HouseKind_;
    }

    public void setSch_HouseKind_(String sch_HouseKind_) {
        this.sch_HouseKind_ = sch_HouseKind_;
    }

    public String getSch_HouseKind() {
        return sch_HouseKind;
    }

    public void setSch_HouseKind(String sch_HouseKind) {
        this.sch_HouseKind = sch_HouseKind;
    }

    public String getSchHouseKind_() {
        return schHouseKind_;
    }

    public void setSchHouseKind_(String schHouseKind_) {
        this.schHouseKind_ = schHouseKind_;
    }

    public String getHouseKindStart() {
        return houseKindStart;
    }

    public void setHouseKindStart(String houseKindStart) {
        this.houseKindStart = houseKindStart;
    }

    public String getHouseKindEnd() {
        return houseKindEnd;
    }

    public void setHouseKindEnd(String houseKindEnd) {
        this.houseKindEnd = houseKindEnd;
    }

    public String getSch_Att4_() {
        return sch_Att4_;
    }

    public void setSch_Att4_(String sch_Att4_) {
        this.sch_Att4_ = sch_Att4_;
    }

    public String getSch_Att4() {
        return sch_Att4;
    }

    public void setSch_Att4(String sch_Att4) {
        this.sch_Att4 = sch_Att4;
    }

    public String getSchAtt4_() {
        return schAtt4_;
    }

    public void setSchAtt4_(String schAtt4_) {
        this.schAtt4_ = schAtt4_;
    }

    public String getAtt4Start() {
        return att4Start;
    }

    public void setAtt4Start(String att4Start) {
        this.att4Start = att4Start;
    }

    public String getAtt4End() {
        return att4End;
    }

    public void setAtt4End(String att4End) {
        this.att4End = att4End;
    }

    public String getSch_Att5_() {
        return sch_Att5_;
    }

    public void setSch_Att5_(String sch_Att5_) {
        this.sch_Att5_ = sch_Att5_;
    }

    public String getSch_Att5() {
        return sch_Att5;
    }

    public void setSch_Att5(String sch_Att5) {
        this.sch_Att5 = sch_Att5;
    }

    public String getSchAtt5_() {
        return schAtt5_;
    }

    public void setSchAtt5_(String schAtt5_) {
        this.schAtt5_ = schAtt5_;
    }

    public String getAtt5Start() {
        return att5Start;
    }

    public void setAtt5Start(String att5Start) {
        this.att5Start = att5Start;
    }

    public String getAtt5End() {
        return att5End;
    }

    public void setAtt5End(String att5End) {
        this.att5End = att5End;
    }

    public String getSch_Att6_() {
        return sch_Att6_;
    }

    public void setSch_Att6_(String sch_Att6_) {
        this.sch_Att6_ = sch_Att6_;
    }

    public String getSch_Att6() {
        return sch_Att6;
    }

    public void setSch_Att6(String sch_Att6) {
        this.sch_Att6 = sch_Att6;
    }

    public String getSchAtt6_() {
        return schAtt6_;
    }

    public void setSchAtt6_(String schAtt6_) {
        this.schAtt6_ = schAtt6_;
    }

    public String getAtt6Start() {
        return att6Start;
    }

    public void setAtt6Start(String att6Start) {
        this.att6Start = att6Start;
    }

    public String getAtt6End() {
        return att6End;
    }

    public void setAtt6End(String att6End) {
        this.att6End = att6End;
    }

    public Date getDateAtt1Start() {
        return dateAtt1Start;
    }

    public void setDateAtt1Start(Date dateAtt1Start) {
        this.dateAtt1Start = dateAtt1Start;
    }

    public Date getDateAtt1End() {
        return dateAtt1End;
    }

    public void setDateAtt1End(Date dateAtt1End) {
        this.dateAtt1End = dateAtt1End;
    }

    public Date getDateAtt2Start() {
        return dateAtt2Start;
    }

    public void setDateAtt2Start(Date dateAtt2Start) {
        this.dateAtt2Start = dateAtt2Start;
    }

    @Override
    public Date getDateAtt2() {
        return dateAtt2;
    }

    @Override
    public void setDateAtt2(Date dateAtt2) {
        this.dateAtt2 = dateAtt2;
    }

    public String getSch_Remark_() {
        return sch_Remark_;
    }

    public void setSch_Remark_(String sch_Remark_) {
        this.sch_Remark_ = sch_Remark_;
    }

    public String getSch_Remark() {
        return sch_Remark;
    }

    public void setSch_Remark(String sch_Remark) {
        this.sch_Remark = sch_Remark;
    }

    public String getSchRemark_() {
        return schRemark_;
    }

    public void setSchRemark_(String schRemark_) {
        this.schRemark_ = schRemark_;
    }

    public String getRemarkStart() {
        return remarkStart;
    }

    public void setRemarkStart(String remarkStart) {
        this.remarkStart = remarkStart;
    }

    public String getRemarkEnd() {
        return remarkEnd;
    }

    public void setRemarkEnd(String remarkEnd) {
        this.remarkEnd = remarkEnd;
    }

    public String livingName;

    @Override
    public String getLivingName() {
        return livingName;
    }

    @Override
    public void setLivingName(String livingName) {
        this.livingName = livingName;
    }


}
