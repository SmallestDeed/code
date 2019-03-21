package com.sandu.user.model.search;

import com.sandu.user.model.SysUser;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @Title: SysUserSearch.java
 * @Package com.sandu.system.model
 * @Description:系统-用户查询对象
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 12:30:46
 */
public class SysUserSearch extends SysUser implements Serializable {
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
     * 用户名-模糊查询
     **/
    private String sch_UserName_;
    /**
     * 用户名-左模糊查询
     **/
    private String sch_UserName;
    /**
     * 用户名-右模糊查询
     **/
    private String schUserName_;
    /**
     * 用户名-区间查询-开始字符串
     **/
    private String userNameStart;
    /**
     * 用户名-区间查询-结束字符串
     **/
    private String userNameEnd;
    /**
     * 昵称-模糊查询
     **/
    private String sch_NickName_;
    /**
     * 昵称-左模糊查询
     **/
    private String sch_NickName;
    /**
     * 昵称-右模糊查询
     **/
    private String schNickName_;
    /**
     * 昵称-区间查询-开始字符串
     **/
    private String nickNameStart;
    /**
     * 昵称-区间查询-结束字符串
     **/
    private String nickNameEnd;
    /**
     * 密码-模糊查询
     **/
    private String sch_Password_;
    /**
     * 密码-左模糊查询
     **/
    private String sch_Password;
    /**
     * 密码-右模糊查询
     **/
    private String schPassword_;
    /**
     * 密码-区间查询-开始字符串
     **/
    private String passwordStart;
    /**
     * 密码-区间查询-结束字符串
     **/
    private String passwordEnd;
    /**
     * 邮箱-模糊查询
     **/
    private String sch_Email_;
    /**
     * 邮箱-左模糊查询
     **/
    private String sch_Email;
    /**
     * 邮箱-右模糊查询
     **/
    private String schEmail_;
    /**
     * 邮箱-区间查询-开始字符串
     **/
    private String emailStart;
    /**
     * 邮箱-区间查询-结束字符串
     **/
    private String emailEnd;
    /**
     * 手机-模糊查询
     **/
    private String sch_Mobile_;
    /**
     * 手机-左模糊查询
     **/
    private String sch_Mobile;
    /**
     * 手机-右模糊查询
     **/
    private String schMobile;
    /**
     * 手机-区间查询-开始字符串
     **/
    private String mobileStart;
    /**
     * 手机-区间查询-结束字符串
     **/
    private String mobileEnd;
    /**
     * 职业-模糊查询
     **/
    private String sch_Job_;
    /**
     * 职业-左模糊查询
     **/
    private String sch_Job;
    /**
     * 职业-右模糊查询
     **/
    private String schJob_;
    /**
     * 职业-区间查询-开始字符串
     **/
    private String jobStart;
    /**
     * 职业-区间查询-结束字符串
     **/
    private String jobEnd;
    /**
     * 头像-模糊查询
     **/
    private String sch_Pic_;
    /**
     * 头像-左模糊查询
     **/
    private String sch_Pic;
    /**
     * 头像-右模糊查询
     **/
    private String schPic_;
    /**
     * 头像-区间查询-开始字符串
     **/
    private String picStart;
    /**
     * 头像-区间查询-结束字符串
     **/
    private String picEnd;
    /**
     * 字符备用1-模糊查询
     **/
    private String sch_AppKey_;
    /**
     * 字符备用1-左模糊查询
     **/
    private String sch_AppKey;
    /**
     * 字符备用1-右模糊查询
     **/
    private String schAppKey_;
    /**
     * 字符备用1-区间查询-开始字符串
     **/
    private String appKeyStart;
    /**
     * 字符备用1-区间查询-结束字符串
     **/
    private String appKeyEnd;
    /**
     * 字符备用2-模糊查询
     **/
    private String sch_Token_;
    /**
     * 字符备用2-左模糊查询
     **/
    private String sch_Token;
    /**
     * 字符备用2-右模糊查询
     **/
    private String schToken_;
    /**
     * 字符备用2-区间查询-开始字符串
     **/
    private String tokenStart;
    /**
     * 字符备用2-区间查询-结束字符串
     **/
    private String tokenEnd;
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
     * 字符备用3-模糊查询
     **/
    private String sch_brandIds_;
    /**
     * 字符备用3-左模糊查询
     **/
    private String sch_brandIds;
    /**
     * 字符备用3-右模糊查询
     **/
    private String schBrandIds_;
    /**
     * 字符备用3-区间查询-开始字符串
     **/
    private String brandIdsStart;
    /**
     * 字符备用3-区间查询-结束字符串
     **/
    private String brandIdsEnd;
    //groupIds
    private List<Integer> groupIds;
    //userIds
    private List<Integer> userIds;

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public List<Integer> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Integer> groupIds) {
        this.groupIds = groupIds;
    }

    public String getSch_brandIds_() {
        return sch_brandIds_;
    }

    public void setSch_brandIds_(String sch_brandIds_) {
        this.sch_brandIds_ = sch_brandIds_;
    }

    public String getSch_brandIds() {
        return sch_brandIds;
    }

    public void setSch_brandIds(String sch_brandIds) {
        this.sch_brandIds = sch_brandIds;
    }

    public String getSchBrandIds_() {
        return schBrandIds_;
    }

    public void setSchBrandIds_(String schBrandIds_) {
        this.schBrandIds_ = schBrandIds_;
    }

    public String getBrandIdsStart() {
        return brandIdsStart;
    }

    public void setBrandIdsStart(String brandIdsStart) {
        this.brandIdsStart = brandIdsStart;
    }

    public String getBrandIdsEnd() {
        return brandIdsEnd;
    }

    public void setBrandIdsEnd(String brandIdsEnd) {
        this.brandIdsEnd = brandIdsEnd;
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

    public String getSch_UserName_() {
        return sch_UserName_;
    }

    public void setSch_UserName_(String sch_UserName_) {
        this.sch_UserName_ = sch_UserName_;
    }

    public String getSch_UserName() {
        return sch_UserName;
    }

    public void setSch_UserName(String sch_UserName) {
        this.sch_UserName = sch_UserName;
    }

    public String getSchUserName_() {
        return schUserName_;
    }

    public void setSchUserName_(String schUserName_) {
        this.schUserName_ = schUserName_;
    }

    public String getUserNameStart() {
        return userNameStart;
    }

    public void setUserNameStart(String userNameStart) {
        this.userNameStart = userNameStart;
    }

    public String getUserNameEnd() {
        return userNameEnd;
    }

    public void setUserNameEnd(String userNameEnd) {
        this.userNameEnd = userNameEnd;
    }

    public String getSch_NickName_() {
        return sch_NickName_;
    }

    public void setSch_NickName_(String sch_NickName_) {
        this.sch_NickName_ = sch_NickName_;
    }

    public String getSch_NickName() {
        return sch_NickName;
    }

    public void setSch_NickName(String sch_NickName) {
        this.sch_NickName = sch_NickName;
    }

    public String getSchNickName_() {
        return schNickName_;
    }

    public void setSchNickName_(String schNickName_) {
        this.schNickName_ = schNickName_;
    }

    public String getNickNameStart() {
        return nickNameStart;
    }

    public void setNickNameStart(String nickNameStart) {
        this.nickNameStart = nickNameStart;
    }

    public String getNickNameEnd() {
        return nickNameEnd;
    }

    public void setNickNameEnd(String nickNameEnd) {
        this.nickNameEnd = nickNameEnd;
    }

    public String getSch_Password_() {
        return sch_Password_;
    }

    public void setSch_Password_(String sch_Password_) {
        this.sch_Password_ = sch_Password_;
    }

    public String getSch_Password() {
        return sch_Password;
    }

    public void setSch_Password(String sch_Password) {
        this.sch_Password = sch_Password;
    }

    public String getSchPassword_() {
        return schPassword_;
    }

    public void setSchPassword_(String schPassword_) {
        this.schPassword_ = schPassword_;
    }

    public String getPasswordStart() {
        return passwordStart;
    }

    public void setPasswordStart(String passwordStart) {
        this.passwordStart = passwordStart;
    }

    public String getPasswordEnd() {
        return passwordEnd;
    }

    public void setPasswordEnd(String passwordEnd) {
        this.passwordEnd = passwordEnd;
    }

    public String getSch_Email_() {
        return sch_Email_;
    }

    public void setSch_Email_(String sch_Email_) {
        this.sch_Email_ = sch_Email_;
    }

    public String getSch_Email() {
        return sch_Email;
    }

    public void setSch_Email(String sch_Email) {
        this.sch_Email = sch_Email;
    }

    public String getSchEmail_() {
        return schEmail_;
    }

    public void setSchEmail_(String schEmail_) {
        this.schEmail_ = schEmail_;
    }

    public String getEmailStart() {
        return emailStart;
    }

    public void setEmailStart(String emailStart) {
        this.emailStart = emailStart;
    }

    public String getEmailEnd() {
        return emailEnd;
    }

    public void setEmailEnd(String emailEnd) {
        this.emailEnd = emailEnd;
    }

    public String getSch_Job_() {
        return sch_Job_;
    }

    public void setSch_Job_(String sch_Job_) {
        this.sch_Job_ = sch_Job_;
    }

    public String getSch_Job() {
        return sch_Job;
    }

    public void setSch_Job(String sch_Job) {
        this.sch_Job = sch_Job;
    }

    public String getSchJob_() {
        return schJob_;
    }

    public void setSchJob_(String schJob_) {
        this.schJob_ = schJob_;
    }

    public String getJobStart() {
        return jobStart;
    }

    public void setJobStart(String jobStart) {
        this.jobStart = jobStart;
    }

    public String getJobEnd() {
        return jobEnd;
    }

    public void setJobEnd(String jobEnd) {
        this.jobEnd = jobEnd;
    }

    public String getSch_Pic_() {
        return sch_Pic_;
    }

    public void setSch_Pic_(String sch_Pic_) {
        this.sch_Pic_ = sch_Pic_;
    }

    public String getSch_Pic() {
        return sch_Pic;
    }

    public void setSch_Pic(String sch_Pic) {
        this.sch_Pic = sch_Pic;
    }

    public String getSchPic_() {
        return schPic_;
    }

    public void setSchPic_(String schPic_) {
        this.schPic_ = schPic_;
    }

    public String getPicStart() {
        return picStart;
    }

    public void setPicStart(String picStart) {
        this.picStart = picStart;
    }

    public String getPicEnd() {
        return picEnd;
    }

    public void setPicEnd(String picEnd) {
        this.picEnd = picEnd;
    }

    public String getSch_AppKey_() {
        return sch_AppKey_;
    }

    public void setSch_AppKey_(String sch_AppKey_) {
        this.sch_AppKey_ = sch_AppKey_;
    }

    public String getSch_AppKey() {
        return sch_AppKey;
    }

    public void setSch_AppKey(String sch_AppKey) {
        this.sch_AppKey = sch_AppKey;
    }

    public String getSchAppKey_() {
        return schAppKey_;
    }

    public void setSchAppKey_(String schAppKey_) {
        this.schAppKey_ = schAppKey_;
    }

    public String getAppKeyStart() {
        return appKeyStart;
    }

    public void setAppKeyStart(String appKeyStart) {
        this.appKeyStart = appKeyStart;
    }

    public String getAppKeyEnd() {
        return appKeyEnd;
    }

    public void setAppKeyEnd(String appKeyEnd) {
        this.appKeyEnd = appKeyEnd;
    }

    public String getSch_Token_() {
        return sch_Token_;
    }

    public void setSch_Token_(String sch_Token_) {
        this.sch_Token_ = sch_Token_;
    }

    public String getSch_Token() {
        return sch_Token;
    }

    public void setSch_Token(String sch_Token) {
        this.sch_Token = sch_Token;
    }

    public String getSchToken_() {
        return schToken_;
    }

    public void setSchToken_(String schToken_) {
        this.schToken_ = schToken_;
    }

    public String getTokenStart() {
        return tokenStart;
    }

    public void setTokenStart(String tokenStart) {
        this.tokenStart = tokenStart;
    }

    public String getTokenEnd() {
        return tokenEnd;
    }

    public void setTokenEnd(String tokenEnd) {
        this.tokenEnd = tokenEnd;
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

    public String getSch_Mobile_() {
        return sch_Mobile_;
    }

    public void setSch_Mobile_(String sch_Mobile_) {
        this.sch_Mobile_ = sch_Mobile_;
    }

    public String getSch_Mobile() {
        return sch_Mobile;
    }

    public void setSch_Mobile(String sch_Mobile) {
        this.sch_Mobile = sch_Mobile;
    }

    public String getSchMobile() {
        return schMobile;
    }

    public void setSchMobile(String schMobile) {
        this.schMobile = schMobile;
    }

    public String getMobileStart() {
        return mobileStart;
    }

    public void setMobileStart(String mobileStart) {
        this.mobileStart = mobileStart;
    }

    public String getMobileEnd() {
        return mobileEnd;
    }

    public void setMobileEnd(String mobileEnd) {
        this.mobileEnd = mobileEnd;
    }

}
