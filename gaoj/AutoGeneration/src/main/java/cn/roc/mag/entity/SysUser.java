package cn.roc.mag.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SysUser {
    /**
     * 
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 移动
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 职业
     */
    private String job;

    /**
     * 区域
     */
    private Integer areaId;

    /**
     * 
     */
    private Integer picId;

    /**
     * 媒介类型
     */
    private Integer mediaType;

    /**
     * 
     */
    private String sysCode;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 
     */
    private Date gmtCreate;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 
     */
    private String appKey;

    /**
     * 字符备用2
     */
    private String token;

    /**
     * 字符备用3
     */
    private String att3;

    /**
     * 字符备用4
     */
    private String att4;

    /**
     * 字符备用5
     */
    private String att5;

    /**
     * 字符备用6
     */
    private String att6;

    /**
     * 时间备用1
     */
    private Date dateAtt1;

    /**
     * 时间备用2
     */
    private Date dateAtt2;

    /**
     * 组织id
     */
    private Integer groupId;

    /**
     * 整数备用2
     */
    private Integer numAtt2;

    /**
     * 数字备用1
     */
    private BigDecimal numAtt3;

    /**
     * 数字备用2
     */
    private BigDecimal numAtt4;

    /**
     * 备注
     */
    private String remark;

    /**
     * 真实名字
     */
    private String realName;

    /**
     * QQ号码
     */
    private String qq;

    /**
     * 公司电话
     */
    private String companyTel;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 
     */
    private String specialityValue;

    /**
     * 
     */
    private String intro;

    /**
     * 
     */
    private Integer emailVerifyState;

    /**
     * 
     */
    private Integer mobileVerifyState;

    /**
     * 
     */
    private Integer idcardVerifyState;

    /**
     * 
     */
    private Integer level;

    /**
     * 
     */
    private Date applicationDate;

    /**
     * 
     */
    private String verifyUser;

    /**
     * 
     */
    private Date verifyDate;

    /**
     * 
     */
    private String areaLongCode;

    /**
     * 
     */
    private String reasonRejected;

    /**
     * 
     */
    private String brandIds;

    /**
     * 
     */
    private String userImei;

    /**
     * 
     */
    private BigDecimal consumAmount;

    /**
     * 
     */
    private BigDecimal balanceAmount;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 网卡校验限制(1:允许所有类型设备登录，2:所有网卡均不允许登录，3:仅允许PCI网卡登录，4:仅允许USB网卡登录，5:取消设备限制)
     */
    private Integer networkCardRestrict;

    /**
     * 是否开通移动端(0:未开通，1：已开通)
     */
    private Integer existsMobile;

    /**
     * 移动端开通时间
     */
    private Date mobileOpenedDate;

    /**
     * 移动端截止时间
     */
    private Date mobileClosedDate;

    /**
     * 可用户型套数
     */
    private Integer usableHouseNumber;

    /**
     * 游客可渲染次数，默认5次
     */
    private Integer visitorsRenderTimes;

    /**
     * 企业/经销商id
     */
    private Integer businessAdministrationId;

    /**
     * 经销商账号类型：0(散)、1(主账号)、2(子账号)
     */
    private Byte franchiserAccountType;

    /**
     * 经销商账号组id
     */
    private Integer franchiserGroupId;

    /**
     * 是否需要更新密码(0:不需要更新,1:需要更新)
     */
    private Byte passwordUpdateFlag;

    /**
     * 是否确认(0:未确认、1:已确认)
     */
    private Integer verifyFlag;

    /**
     * 消费总积分
     */
    private Integer consumIntegral;

    /**
     * 账号积分
     */
    private Integer balanceIntegral;

    /**
     * 上一次登录企业id
     */
    private Long lastLoginCompanyId;

    /**
     * 工作岗位类型
     */
    private Integer postType;

    /**
     * 是否有登录过:0,未登录;1,已经登录过
     */
    private Integer isLoginBefore;

    /**
     * 平台类型:1:C端用户,2:B端用户
     */
    private Integer platformType;

    /**
     * 用户微信openid
     */
    private String openId;

    /**
     * 微信小程序用户对应企业id
     */
    private Long miniProgramCompanyId;

    /**
     * 首次登录时间
     */
    private Date firstLoginTime;

    /**
     * 使用类型（0：试用、1：正式）
     */
    private Integer useType;

    /**
     * 有效时长（月/天）
     */
    private Integer validTime;

    /**
     * 失效时间
     */
    private Date failureTime;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户名
     * @return user_name 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 昵称
     * @return nick_name 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 昵称
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * 移动
     * @return mobile 移动
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 移动
     * @param mobile 移动
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 邮箱
     * @return email 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 用户类型
     * @return user_type 用户类型
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 用户类型
     * @param userType 用户类型
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 性别
     * @return sex 性别
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 性别
     * @param sex 性别
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 职业
     * @return job 职业
     */
    public String getJob() {
        return job;
    }

    /**
     * 职业
     * @param job 职业
     */
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    /**
     * 区域
     * @return area_id 区域
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * 区域
     * @param areaId 区域
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    /**
     * 
     * @return pic_id 
     */
    public Integer getPicId() {
        return picId;
    }

    /**
     * 
     * @param picId 
     */
    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    /**
     * 媒介类型
     * @return media_type 媒介类型
     */
    public Integer getMediaType() {
        return mediaType;
    }

    /**
     * 媒介类型
     * @param mediaType 媒介类型
     */
    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * 
     * @return sys_code 
     */
    public String getSysCode() {
        return sysCode;
    }

    /**
     * 
     * @param sysCode 
     */
    public void setSysCode(String sysCode) {
        this.sysCode = sysCode == null ? null : sysCode.trim();
    }

    /**
     * 创建者
     * @return creator 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     * @param creator 创建者
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 
     * @return gmt_create 
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 
     * @param gmtCreate 
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 修改人
     * @return modifier 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 修改人
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 修改时间
     * @return gmt_modified 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 修改时间
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 是否删除
     * @return is_deleted 是否删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除
     * @param isDeleted 是否删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 
     * @return app_key 
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * 
     * @param appKey 
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    /**
     * 字符备用2
     * @return token 字符备用2
     */
    public String getToken() {
        return token;
    }

    /**
     * 字符备用2
     * @param token 字符备用2
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * 字符备用3
     * @return att3 字符备用3
     */
    public String getAtt3() {
        return att3;
    }

    /**
     * 字符备用3
     * @param att3 字符备用3
     */
    public void setAtt3(String att3) {
        this.att3 = att3 == null ? null : att3.trim();
    }

    /**
     * 字符备用4
     * @return att4 字符备用4
     */
    public String getAtt4() {
        return att4;
    }

    /**
     * 字符备用4
     * @param att4 字符备用4
     */
    public void setAtt4(String att4) {
        this.att4 = att4 == null ? null : att4.trim();
    }

    /**
     * 字符备用5
     * @return att5 字符备用5
     */
    public String getAtt5() {
        return att5;
    }

    /**
     * 字符备用5
     * @param att5 字符备用5
     */
    public void setAtt5(String att5) {
        this.att5 = att5 == null ? null : att5.trim();
    }

    /**
     * 字符备用6
     * @return att6 字符备用6
     */
    public String getAtt6() {
        return att6;
    }

    /**
     * 字符备用6
     * @param att6 字符备用6
     */
    public void setAtt6(String att6) {
        this.att6 = att6 == null ? null : att6.trim();
    }

    /**
     * 时间备用1
     * @return date_att1 时间备用1
     */
    public Date getDateAtt1() {
        return dateAtt1;
    }

    /**
     * 时间备用1
     * @param dateAtt1 时间备用1
     */
    public void setDateAtt1(Date dateAtt1) {
        this.dateAtt1 = dateAtt1;
    }

    /**
     * 时间备用2
     * @return date_att2 时间备用2
     */
    public Date getDateAtt2() {
        return dateAtt2;
    }

    /**
     * 时间备用2
     * @param dateAtt2 时间备用2
     */
    public void setDateAtt2(Date dateAtt2) {
        this.dateAtt2 = dateAtt2;
    }

    /**
     * 组织id
     * @return group_id 组织id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 组织id
     * @param groupId 组织id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 整数备用2
     * @return num_att2 整数备用2
     */
    public Integer getNumAtt2() {
        return numAtt2;
    }

    /**
     * 整数备用2
     * @param numAtt2 整数备用2
     */
    public void setNumAtt2(Integer numAtt2) {
        this.numAtt2 = numAtt2;
    }

    /**
     * 数字备用1
     * @return num_att3 数字备用1
     */
    public BigDecimal getNumAtt3() {
        return numAtt3;
    }

    /**
     * 数字备用1
     * @param numAtt3 数字备用1
     */
    public void setNumAtt3(BigDecimal numAtt3) {
        this.numAtt3 = numAtt3;
    }

    /**
     * 数字备用2
     * @return num_att4 数字备用2
     */
    public BigDecimal getNumAtt4() {
        return numAtt4;
    }

    /**
     * 数字备用2
     * @param numAtt4 数字备用2
     */
    public void setNumAtt4(BigDecimal numAtt4) {
        this.numAtt4 = numAtt4;
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 真实名字
     * @return real_name 真实名字
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 真实名字
     * @param realName 真实名字
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * QQ号码
     * @return qq QQ号码
     */
    public String getQq() {
        return qq;
    }

    /**
     * QQ号码
     * @param qq QQ号码
     */
    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    /**
     * 公司电话
     * @return company_tel 公司电话
     */
    public String getCompanyTel() {
        return companyTel;
    }

    /**
     * 公司电话
     * @param companyTel 公司电话
     */
    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel == null ? null : companyTel.trim();
    }

    /**
     * 公司地址
     * @return company_address 公司地址
     */
    public String getCompanyAddress() {
        return companyAddress;
    }

    /**
     * 公司地址
     * @param companyAddress 公司地址
     */
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    /**
     * 
     * @return speciality_value 
     */
    public String getSpecialityValue() {
        return specialityValue;
    }

    /**
     * 
     * @param specialityValue 
     */
    public void setSpecialityValue(String specialityValue) {
        this.specialityValue = specialityValue == null ? null : specialityValue.trim();
    }

    /**
     * 
     * @return intro 
     */
    public String getIntro() {
        return intro;
    }

    /**
     * 
     * @param intro 
     */
    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    /**
     * 
     * @return email_verify_state 
     */
    public Integer getEmailVerifyState() {
        return emailVerifyState;
    }

    /**
     * 
     * @param emailVerifyState 
     */
    public void setEmailVerifyState(Integer emailVerifyState) {
        this.emailVerifyState = emailVerifyState;
    }

    /**
     * 
     * @return mobile_verify_state 
     */
    public Integer getMobileVerifyState() {
        return mobileVerifyState;
    }

    /**
     * 
     * @param mobileVerifyState 
     */
    public void setMobileVerifyState(Integer mobileVerifyState) {
        this.mobileVerifyState = mobileVerifyState;
    }

    /**
     * 
     * @return idcard_verify_state 
     */
    public Integer getIdcardVerifyState() {
        return idcardVerifyState;
    }

    /**
     * 
     * @param idcardVerifyState 
     */
    public void setIdcardVerifyState(Integer idcardVerifyState) {
        this.idcardVerifyState = idcardVerifyState;
    }

    /**
     * 
     * @return LEVEL 
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 
     * @param level 
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 
     * @return application_date 
     */
    public Date getApplicationDate() {
        return applicationDate;
    }

    /**
     * 
     * @param applicationDate 
     */
    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    /**
     * 
     * @return verify_user 
     */
    public String getVerifyUser() {
        return verifyUser;
    }

    /**
     * 
     * @param verifyUser 
     */
    public void setVerifyUser(String verifyUser) {
        this.verifyUser = verifyUser == null ? null : verifyUser.trim();
    }

    /**
     * 
     * @return verify_date 
     */
    public Date getVerifyDate() {
        return verifyDate;
    }

    /**
     * 
     * @param verifyDate 
     */
    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    /**
     * 
     * @return area_long_code 
     */
    public String getAreaLongCode() {
        return areaLongCode;
    }

    /**
     * 
     * @param areaLongCode 
     */
    public void setAreaLongCode(String areaLongCode) {
        this.areaLongCode = areaLongCode == null ? null : areaLongCode.trim();
    }

    /**
     * 
     * @return reason_rejected 
     */
    public String getReasonRejected() {
        return reasonRejected;
    }

    /**
     * 
     * @param reasonRejected 
     */
    public void setReasonRejected(String reasonRejected) {
        this.reasonRejected = reasonRejected == null ? null : reasonRejected.trim();
    }

    /**
     * 
     * @return brand_ids 
     */
    public String getBrandIds() {
        return brandIds;
    }

    /**
     * 
     * @param brandIds 
     */
    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds == null ? null : brandIds.trim();
    }

    /**
     * 
     * @return user_imei 
     */
    public String getUserImei() {
        return userImei;
    }

    /**
     * 
     * @param userImei 
     */
    public void setUserImei(String userImei) {
        this.userImei = userImei == null ? null : userImei.trim();
    }

    /**
     * 
     * @return consum_amount 
     */
    public BigDecimal getConsumAmount() {
        return consumAmount;
    }

    /**
     * 
     * @param consumAmount 
     */
    public void setConsumAmount(BigDecimal consumAmount) {
        this.consumAmount = consumAmount;
    }

    /**
     * 
     * @return balance_amount 
     */
    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    /**
     * 
     * @param balanceAmount 
     */
    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    /**
     * 详细地址
     * @return address 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 详细地址
     * @param address 详细地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 网卡校验限制(1:允许所有类型设备登录，2:所有网卡均不允许登录，3:仅允许PCI网卡登录，4:仅允许USB网卡登录，5:取消设备限制)
     * @return network_card_restrict 网卡校验限制(1:允许所有类型设备登录，2:所有网卡均不允许登录，3:仅允许PCI网卡登录，4:仅允许USB网卡登录，5:取消设备限制)
     */
    public Integer getNetworkCardRestrict() {
        return networkCardRestrict;
    }

    /**
     * 网卡校验限制(1:允许所有类型设备登录，2:所有网卡均不允许登录，3:仅允许PCI网卡登录，4:仅允许USB网卡登录，5:取消设备限制)
     * @param networkCardRestrict 网卡校验限制(1:允许所有类型设备登录，2:所有网卡均不允许登录，3:仅允许PCI网卡登录，4:仅允许USB网卡登录，5:取消设备限制)
     */
    public void setNetworkCardRestrict(Integer networkCardRestrict) {
        this.networkCardRestrict = networkCardRestrict;
    }

    /**
     * 是否开通移动端(0:未开通，1：已开通)
     * @return exists_mobile 是否开通移动端(0:未开通，1：已开通)
     */
    public Integer getExistsMobile() {
        return existsMobile;
    }

    /**
     * 是否开通移动端(0:未开通，1：已开通)
     * @param existsMobile 是否开通移动端(0:未开通，1：已开通)
     */
    public void setExistsMobile(Integer existsMobile) {
        this.existsMobile = existsMobile;
    }

    /**
     * 移动端开通时间
     * @return mobile_opened_date 移动端开通时间
     */
    public Date getMobileOpenedDate() {
        return mobileOpenedDate;
    }

    /**
     * 移动端开通时间
     * @param mobileOpenedDate 移动端开通时间
     */
    public void setMobileOpenedDate(Date mobileOpenedDate) {
        this.mobileOpenedDate = mobileOpenedDate;
    }

    /**
     * 移动端截止时间
     * @return mobile_closed_date 移动端截止时间
     */
    public Date getMobileClosedDate() {
        return mobileClosedDate;
    }

    /**
     * 移动端截止时间
     * @param mobileClosedDate 移动端截止时间
     */
    public void setMobileClosedDate(Date mobileClosedDate) {
        this.mobileClosedDate = mobileClosedDate;
    }

    /**
     * 可用户型套数
     * @return usable_house_number 可用户型套数
     */
    public Integer getUsableHouseNumber() {
        return usableHouseNumber;
    }

    /**
     * 可用户型套数
     * @param usableHouseNumber 可用户型套数
     */
    public void setUsableHouseNumber(Integer usableHouseNumber) {
        this.usableHouseNumber = usableHouseNumber;
    }

    /**
     * 游客可渲染次数，默认5次
     * @return visitors_render_times 游客可渲染次数，默认5次
     */
    public Integer getVisitorsRenderTimes() {
        return visitorsRenderTimes;
    }

    /**
     * 游客可渲染次数，默认5次
     * @param visitorsRenderTimes 游客可渲染次数，默认5次
     */
    public void setVisitorsRenderTimes(Integer visitorsRenderTimes) {
        this.visitorsRenderTimes = visitorsRenderTimes;
    }

    /**
     * 企业/经销商id
     * @return business_administration_id 企业/经销商id
     */
    public Integer getBusinessAdministrationId() {
        return businessAdministrationId;
    }

    /**
     * 企业/经销商id
     * @param businessAdministrationId 企业/经销商id
     */
    public void setBusinessAdministrationId(Integer businessAdministrationId) {
        this.businessAdministrationId = businessAdministrationId;
    }

    /**
     * 经销商账号类型：0(散)、1(主账号)、2(子账号)
     * @return franchiser_account_type 经销商账号类型：0(散)、1(主账号)、2(子账号)
     */
    public Byte getFranchiserAccountType() {
        return franchiserAccountType;
    }

    /**
     * 经销商账号类型：0(散)、1(主账号)、2(子账号)
     * @param franchiserAccountType 经销商账号类型：0(散)、1(主账号)、2(子账号)
     */
    public void setFranchiserAccountType(Byte franchiserAccountType) {
        this.franchiserAccountType = franchiserAccountType;
    }

    /**
     * 经销商账号组id
     * @return franchiser_group_id 经销商账号组id
     */
    public Integer getFranchiserGroupId() {
        return franchiserGroupId;
    }

    /**
     * 经销商账号组id
     * @param franchiserGroupId 经销商账号组id
     */
    public void setFranchiserGroupId(Integer franchiserGroupId) {
        this.franchiserGroupId = franchiserGroupId;
    }

    /**
     * 是否需要更新密码(0:不需要更新,1:需要更新)
     * @return password_update_flag 是否需要更新密码(0:不需要更新,1:需要更新)
     */
    public Byte getPasswordUpdateFlag() {
        return passwordUpdateFlag;
    }

    /**
     * 是否需要更新密码(0:不需要更新,1:需要更新)
     * @param passwordUpdateFlag 是否需要更新密码(0:不需要更新,1:需要更新)
     */
    public void setPasswordUpdateFlag(Byte passwordUpdateFlag) {
        this.passwordUpdateFlag = passwordUpdateFlag;
    }

    /**
     * 是否确认(0:未确认、1:已确认)
     * @return verify_flag 是否确认(0:未确认、1:已确认)
     */
    public Integer getVerifyFlag() {
        return verifyFlag;
    }

    /**
     * 是否确认(0:未确认、1:已确认)
     * @param verifyFlag 是否确认(0:未确认、1:已确认)
     */
    public void setVerifyFlag(Integer verifyFlag) {
        this.verifyFlag = verifyFlag;
    }

    /**
     * 消费总积分
     * @return consum_integral 消费总积分
     */
    public Integer getConsumIntegral() {
        return consumIntegral;
    }

    /**
     * 消费总积分
     * @param consumIntegral 消费总积分
     */
    public void setConsumIntegral(Integer consumIntegral) {
        this.consumIntegral = consumIntegral;
    }

    /**
     * 账号积分
     * @return balance_integral 账号积分
     */
    public Integer getBalanceIntegral() {
        return balanceIntegral;
    }

    /**
     * 账号积分
     * @param balanceIntegral 账号积分
     */
    public void setBalanceIntegral(Integer balanceIntegral) {
        this.balanceIntegral = balanceIntegral;
    }

    /**
     * 上一次登录企业id
     * @return last_login_company_id 上一次登录企业id
     */
    public Long getLastLoginCompanyId() {
        return lastLoginCompanyId;
    }

    /**
     * 上一次登录企业id
     * @param lastLoginCompanyId 上一次登录企业id
     */
    public void setLastLoginCompanyId(Long lastLoginCompanyId) {
        this.lastLoginCompanyId = lastLoginCompanyId;
    }

    /**
     * 工作岗位类型
     * @return post_type 工作岗位类型
     */
    public Integer getPostType() {
        return postType;
    }

    /**
     * 工作岗位类型
     * @param postType 工作岗位类型
     */
    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    /**
     * 是否有登录过:0,未登录;1,已经登录过
     * @return is_login_before 是否有登录过:0,未登录;1,已经登录过
     */
    public Integer getIsLoginBefore() {
        return isLoginBefore;
    }

    /**
     * 是否有登录过:0,未登录;1,已经登录过
     * @param isLoginBefore 是否有登录过:0,未登录;1,已经登录过
     */
    public void setIsLoginBefore(Integer isLoginBefore) {
        this.isLoginBefore = isLoginBefore;
    }

    /**
     * 平台类型:1:C端用户,2:B端用户
     * @return platform_type 平台类型:1:C端用户,2:B端用户
     */
    public Integer getPlatformType() {
        return platformType;
    }

    /**
     * 平台类型:1:C端用户,2:B端用户
     * @param platformType 平台类型:1:C端用户,2:B端用户
     */
    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    /**
     * 用户微信openid
     * @return open_id 用户微信openid
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 用户微信openid
     * @param openId 用户微信openid
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * 微信小程序用户对应企业id
     * @return mini_program_company_id 微信小程序用户对应企业id
     */
    public Long getMiniProgramCompanyId() {
        return miniProgramCompanyId;
    }

    /**
     * 微信小程序用户对应企业id
     * @param miniProgramCompanyId 微信小程序用户对应企业id
     */
    public void setMiniProgramCompanyId(Long miniProgramCompanyId) {
        this.miniProgramCompanyId = miniProgramCompanyId;
    }

    /**
     * 首次登录时间
     * @return first_login_time 首次登录时间
     */
    public Date getFirstLoginTime() {
        return firstLoginTime;
    }

    /**
     * 首次登录时间
     * @param firstLoginTime 首次登录时间
     */
    public void setFirstLoginTime(Date firstLoginTime) {
        this.firstLoginTime = firstLoginTime;
    }

    /**
     * 使用类型（0：试用、1：正式）
     * @return use_type 使用类型（0：试用、1：正式）
     */
    public Integer getUseType() {
        return useType;
    }

    /**
     * 使用类型（0：试用、1：正式）
     * @param useType 使用类型（0：试用、1：正式）
     */
    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    /**
     * 有效时长（月/天）
     * @return valid_time 有效时长（月/天）
     */
    public Integer getValidTime() {
        return validTime;
    }

    /**
     * 有效时长（月/天）
     * @param validTime 有效时长（月/天）
     */
    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    /**
     * 失效时间
     * @return failure_time 失效时间
     */
    public Date getFailureTime() {
        return failureTime;
    }

    /**
     * 失效时间
     * @param failureTime 失效时间
     */
    public void setFailureTime(Date failureTime) {
        this.failureTime = failureTime;
    }
}