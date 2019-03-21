package com.sandu.product.model.search;

import com.sandu.product.model.BaseBrand;

import java.io.Serializable;
import java.util.Date;

/**
 * @version V1.0
 * @Title: BaseBrandSearch.java
 * @Package com.sandu.product.model
 * @Description:产品-品牌表查询对象
 * @createAuthor pandajun
 * @CreateDate 2015-06-16 10:03:47
 */
public class BaseBrandSearch extends BaseBrand implements Serializable {
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
     * 品牌编码-模糊查询
     **/
    private String sch_BrandCode_;
    /**
     * 品牌编码-左模糊查询
     **/
    private String sch_BrandCode;
    /**
     * 品牌编码-右模糊查询
     **/
    private String schBrandCode_;
    /**
     * 品牌编码-区间查询-开始字符串
     **/
    private String brandCodeStart;
    /**
     * 品牌编码-区间查询-结束字符串
     **/
    private String brandCodeEnd;
    /**
     * 品牌名称-模糊查询
     **/
    private String sch_BrandName_;
    /**
     * 英文简称-模糊查询
     **/
    private String sch_brandReferred_;

    public String getSch_brandReferred_() {
        return sch_brandReferred_;
    }

    public void setSch_brandReferred_(String sch_brandReferred_) {
        this.sch_brandReferred_ = sch_brandReferred_;
    }

    /**
     * 品牌名称-左模糊查询
     **/
    private String sch_BrandName;
    /**
     * 品牌名称-右模糊查询
     **/
    private String schBrandName_;
    /**
     * 品牌名称-区间查询-开始字符串
     **/
    private String brandNameStart;
    /**
     * 品牌名称-区间查询-结束字符串
     **/
    private String brandNameEnd;
    /**
     * 品牌logo-模糊查询
     **/
    private String sch_BrandLogo_;
    /**
     * 品牌logo-左模糊查询
     **/
    private String sch_BrandLogo;
    /**
     * 品牌logo-右模糊查询
     **/
    private String schBrandLogo_;
    /**
     * 品牌logo-区间查询-开始字符串
     **/
    private String brandLogoStart;
    /**
     * 品牌logo-区间查询-结束字符串
     **/
    private String brandLogoEnd;
    /**
     * 品牌介绍-模糊查询
     **/
    private String sch_BrandDesc_;
    /**
     * 品牌介绍-左模糊查询
     **/
    private String sch_BrandDesc;
    /**
     * 品牌介绍-右模糊查询
     **/
    private String schBrandDesc_;
    /**
     * 品牌介绍-区间查询-开始字符串
     **/
    private String brandDescStart;
    /**
     * 品牌介绍-区间查询-结束字符串
     **/
    private String brandDescEnd;
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
     * 字符备用3-模糊查询
     **/
    private String sch_Att3_;
    /**
     * 字符备用3-左模糊查询
     **/
    private String sch_Att3;
    /**
     * 字符备用3-右模糊查询
     **/
    private String schAtt3_;
    /**
     * 字符备用3-区间查询-开始字符串
     **/
    private String att3Start;
    /**
     * 字符备用3-区间查询-结束字符串
     **/
    private String att3End;
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
    private Date dateAtt2End;
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

    /**
     * 用户类型，不同的类型查询的结果不一样
     **/
    private Integer userType;
    /*不同的 环境 查询的结果不一样*/
    private String versionType;


    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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

    public String getSch_BrandCode_() {
        return sch_BrandCode_;
    }

    public void setSch_BrandCode_(String sch_BrandCode_) {
        this.sch_BrandCode_ = sch_BrandCode_;
    }

    public String getSch_BrandCode() {
        return sch_BrandCode;
    }

    public void setSch_BrandCode(String sch_BrandCode) {
        this.sch_BrandCode = sch_BrandCode;
    }

    public String getSchBrandCode_() {
        return schBrandCode_;
    }

    public void setSchBrandCode_(String schBrandCode_) {
        this.schBrandCode_ = schBrandCode_;
    }

    public String getBrandCodeStart() {
        return brandCodeStart;
    }

    public void setBrandCodeStart(String brandCodeStart) {
        this.brandCodeStart = brandCodeStart;
    }

    public String getBrandCodeEnd() {
        return brandCodeEnd;
    }

    public void setBrandCodeEnd(String brandCodeEnd) {
        this.brandCodeEnd = brandCodeEnd;
    }

    public String getSch_BrandName_() {
        return sch_BrandName_;
    }

    public void setSch_BrandName_(String sch_BrandName_) {
        this.sch_BrandName_ = sch_BrandName_;
    }

    public String getSch_BrandName() {
        return sch_BrandName;
    }

    public void setSch_BrandName(String sch_BrandName) {
        this.sch_BrandName = sch_BrandName;
    }

    public String getSchBrandName_() {
        return schBrandName_;
    }

    public void setSchBrandName_(String schBrandName_) {
        this.schBrandName_ = schBrandName_;
    }

    public String getBrandNameStart() {
        return brandNameStart;
    }

    public void setBrandNameStart(String brandNameStart) {
        this.brandNameStart = brandNameStart;
    }

    public String getBrandNameEnd() {
        return brandNameEnd;
    }

    public void setBrandNameEnd(String brandNameEnd) {
        this.brandNameEnd = brandNameEnd;
    }

    public String getSch_BrandLogo_() {
        return sch_BrandLogo_;
    }

    public void setSch_BrandLogo_(String sch_BrandLogo_) {
        this.sch_BrandLogo_ = sch_BrandLogo_;
    }

    public String getSch_BrandLogo() {
        return sch_BrandLogo;
    }

    public void setSch_BrandLogo(String sch_BrandLogo) {
        this.sch_BrandLogo = sch_BrandLogo;
    }

    public String getSchBrandLogo_() {
        return schBrandLogo_;
    }

    public void setSchBrandLogo_(String schBrandLogo_) {
        this.schBrandLogo_ = schBrandLogo_;
    }

    public String getBrandLogoStart() {
        return brandLogoStart;
    }

    public void setBrandLogoStart(String brandLogoStart) {
        this.brandLogoStart = brandLogoStart;
    }

    public String getBrandLogoEnd() {
        return brandLogoEnd;
    }

    public void setBrandLogoEnd(String brandLogoEnd) {
        this.brandLogoEnd = brandLogoEnd;
    }

    public String getSch_BrandDesc_() {
        return sch_BrandDesc_;
    }

    public void setSch_BrandDesc_(String sch_BrandDesc_) {
        this.sch_BrandDesc_ = sch_BrandDesc_;
    }

    public String getSch_BrandDesc() {
        return sch_BrandDesc;
    }

    public void setSch_BrandDesc(String sch_BrandDesc) {
        this.sch_BrandDesc = sch_BrandDesc;
    }

    public String getSchBrandDesc_() {
        return schBrandDesc_;
    }

    public void setSchBrandDesc_(String schBrandDesc_) {
        this.schBrandDesc_ = schBrandDesc_;
    }

    public String getBrandDescStart() {
        return brandDescStart;
    }

    public void setBrandDescStart(String brandDescStart) {
        this.brandDescStart = brandDescStart;
    }

    public String getBrandDescEnd() {
        return brandDescEnd;
    }

    public void setBrandDescEnd(String brandDescEnd) {
        this.brandDescEnd = brandDescEnd;
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

    public String getSch_Att3_() {
        return sch_Att3_;
    }

    public void setSch_Att3_(String sch_Att3_) {
        this.sch_Att3_ = sch_Att3_;
    }

    public String getSch_Att3() {
        return sch_Att3;
    }

    public void setSch_Att3(String sch_Att3) {
        this.sch_Att3 = sch_Att3;
    }

    public String getSchAtt3_() {
        return schAtt3_;
    }

    public void setSchAtt3_(String schAtt3_) {
        this.schAtt3_ = schAtt3_;
    }

    public String getAtt3Start() {
        return att3Start;
    }

    public void setAtt3Start(String att3Start) {
        this.att3Start = att3Start;
    }

    public String getAtt3End() {
        return att3End;
    }

    public void setAtt3End(String att3End) {
        this.att3End = att3End;
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

    public Date getDateAtt2End() {
        return dateAtt2End;
    }

    public void setDateAtt2End(Date dateAtt2End) {
        this.dateAtt2End = dateAtt2End;
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
