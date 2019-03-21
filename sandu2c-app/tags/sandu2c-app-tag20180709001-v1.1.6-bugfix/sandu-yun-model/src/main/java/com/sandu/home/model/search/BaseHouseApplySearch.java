package com.sandu.home.model.search;

import com.sandu.home.model.BaseHouseApply;

import java.io.Serializable;
import java.util.Date;

/**
 * @version V1.0
 * @Title: BaseHouseApplySearch.java
 * @Package com.sandu.home.model
 * @Description:户型房型-户型申请表查询对象
 * @createAuthor pandajun
 * @CreateDate 2016-10-13 11:45:31
 */
public class BaseHouseApplySearch extends BaseHouseApply implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 申请时间-区间查询-开始时间
     **/
    private Date applyTimeStart;
    /**
     * 申请时间-区间查询-结束时间
     **/
    private Date applyTimeEnd;
    /**
     * 描述-模糊查询
     **/
    private String sch_Description_;
    /**
     * 描述-左模糊查询
     **/
    private String sch_Description;
    /**
     * 描述-右模糊查询
     **/
    private String schDescription_;
    /**
     * 描述-区间查询-开始字符串
     **/
    private String descriptionStart;
    /**
     * 描述-区间查询-结束字符串
     **/
    private String descriptionEnd;
    /**
     * 城市信息-模糊查询
     **/
    private String sch_CityInfo_;
    /**
     * 城市信息-左模糊查询
     **/
    private String sch_CityInfo;
    /**
     * 城市信息-右模糊查询
     **/
    private String schCityInfo_;
    /**
     * 城市信息-区间查询-开始字符串
     **/
    private String cityInfoStart;
    /**
     * 城市信息-区间查询-结束字符串
     **/
    private String cityInfoEnd;
    /**
     * 小区信息-模糊查询
     **/
    private String sch_LivingInfo_;
    /**
     * 小区信息-左模糊查询
     **/
    private String sch_LivingInfo;
    /**
     * 小区信息-右模糊查询
     **/
    private String schLivingInfo_;
    /**
     * 小区信息-区间查询-开始字符串
     **/
    private String livingInfoStart;
    /**
     * 小区信息-区间查询-结束字符串
     **/
    private String livingInfoEnd;
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
     * 字符备用1-模糊查询
     **/
    private String sch_Att1_;
    /**
     * 字符备用1-左模糊查询
     **/
    private String sch_Att1;
    /**
     * 字符备用1-右模糊查询
     **/
    private String schAtt1_;
    /**
     * 字符备用1-区间查询-开始字符串
     **/
    private String att1Start;
    /**
     * 字符备用1-区间查询-结束字符串
     **/
    private String att1End;
    /**
     * 字符备用2-模糊查询
     **/
    private String sch_Att2_;
    /**
     * 字符备用2-左模糊查询
     **/
    private String sch_Att2;
    /**
     * 字符备用2-右模糊查询
     **/
    private String schAtt2_;
    /**
     * 字符备用2-区间查询-开始字符串
     **/
    private String att2Start;
    /**
     * 字符备用2-区间查询-结束字符串
     **/
    private String att2End;
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

    public Date getApplyTimeStart() {
        return applyTimeStart;
    }

    public void setApplyTimeStart(Date applyTimeStart) {
        this.applyTimeStart = applyTimeStart;
    }

    public Date getApplyTimeEnd() {
        return applyTimeEnd;
    }

    public void setApplyTimeEnd(Date applyTimeEnd) {
        this.applyTimeEnd = applyTimeEnd;
    }

    public String getSch_Description_() {
        return sch_Description_;
    }

    public void setSch_Description_(String sch_Description_) {
        this.sch_Description_ = sch_Description_;
    }

    public String getSch_Description() {
        return sch_Description;
    }

    public void setSch_Description(String sch_Description) {
        this.sch_Description = sch_Description;
    }

    public String getSchDescription_() {
        return schDescription_;
    }

    public void setSchDescription_(String schDescription_) {
        this.schDescription_ = schDescription_;
    }

    public String getDescriptionStart() {
        return descriptionStart;
    }

    public void setDescriptionStart(String descriptionStart) {
        this.descriptionStart = descriptionStart;
    }

    public String getDescriptionEnd() {
        return descriptionEnd;
    }

    public void setDescriptionEnd(String descriptionEnd) {
        this.descriptionEnd = descriptionEnd;
    }

    public String getSch_CityInfo_() {
        return sch_CityInfo_;
    }

    public void setSch_CityInfo_(String sch_CityInfo_) {
        this.sch_CityInfo_ = sch_CityInfo_;
    }

    public String getSch_CityInfo() {
        return sch_CityInfo;
    }

    public void setSch_CityInfo(String sch_CityInfo) {
        this.sch_CityInfo = sch_CityInfo;
    }

    public String getSchCityInfo_() {
        return schCityInfo_;
    }

    public void setSchCityInfo_(String schCityInfo_) {
        this.schCityInfo_ = schCityInfo_;
    }

    public String getCityInfoStart() {
        return cityInfoStart;
    }

    public void setCityInfoStart(String cityInfoStart) {
        this.cityInfoStart = cityInfoStart;
    }

    public String getCityInfoEnd() {
        return cityInfoEnd;
    }

    public void setCityInfoEnd(String cityInfoEnd) {
        this.cityInfoEnd = cityInfoEnd;
    }

    public String getSch_LivingInfo_() {
        return sch_LivingInfo_;
    }

    public void setSch_LivingInfo_(String sch_LivingInfo_) {
        this.sch_LivingInfo_ = sch_LivingInfo_;
    }

    public String getSch_LivingInfo() {
        return sch_LivingInfo;
    }

    public void setSch_LivingInfo(String sch_LivingInfo) {
        this.sch_LivingInfo = sch_LivingInfo;
    }

    public String getSchLivingInfo_() {
        return schLivingInfo_;
    }

    public void setSchLivingInfo_(String schLivingInfo_) {
        this.schLivingInfo_ = schLivingInfo_;
    }

    public String getLivingInfoStart() {
        return livingInfoStart;
    }

    public void setLivingInfoStart(String livingInfoStart) {
        this.livingInfoStart = livingInfoStart;
    }

    public String getLivingInfoEnd() {
        return livingInfoEnd;
    }

    public void setLivingInfoEnd(String livingInfoEnd) {
        this.livingInfoEnd = livingInfoEnd;
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

    public String getSch_Att2_() {
        return sch_Att2_;
    }

    public void setSch_Att2_(String sch_Att2_) {
        this.sch_Att2_ = sch_Att2_;
    }

    public String getSch_Att2() {
        return sch_Att2;
    }

    public void setSch_Att2(String sch_Att2) {
        this.sch_Att2 = sch_Att2;
    }

    public String getSchAtt2_() {
        return schAtt2_;
    }

    public void setSchAtt2_(String schAtt2_) {
        this.schAtt2_ = schAtt2_;
    }

    public String getAtt2Start() {
        return att2Start;
    }

    public void setAtt2Start(String att2Start) {
        this.att2Start = att2Start;
    }

    public String getAtt2End() {
        return att2End;
    }

    public void setAtt2End(String att2End) {
        this.att2End = att2End;
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

}
