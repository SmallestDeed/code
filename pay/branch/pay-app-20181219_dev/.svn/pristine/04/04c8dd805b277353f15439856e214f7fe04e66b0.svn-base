package com.sandu.user.model;

import com.sandu.common.model.Mapper;

import java.io.Serializable;
import java.util.*;

/**
 * @version V1.0
 * @Title: SysUser.java
 * @Package com.sandu.system.model
 * @Description:系统-用户
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 12:30:46
 */
public class SysUser extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String webSocketServer;


    public String getWebSocketServer() {
        return webSocketServer;
    }

    public void setWebSocketServer(String webSocketServer) {
        this.webSocketServer = webSocketServer;
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
     * 用户名
     **/
    private String userName;
    /**
     * 昵称
     **/
    private String nickName;
    /**
     * 密码
     **/
    private String password;
    /**
     * 邮箱
     **/
    private String email;
    /**
     * 移动
     **/
    private String mobile;
    /**
     * 用户类型
     **/
    private Integer userType;
    /**
     * 性别
     **/
    private Integer sex;
    /**
     * 职业
     **/
    private String job;
    /**
     * 区域
     **/
    private Integer areaId;
    /**
     * 区域 长编码（省市）
     **/
    private String areaLongCode;
    /**
     * 头像id
     **/
    private Integer picId;
    /**
     * 媒介类型
     **/
    private Integer mediaType;
    /**
     * 字符备用1
     **/
    private String appKey;
    /**
     * 字符备用2
     **/
    private String token;
    /**
     * 字符备用3
     **/
    private String att3;
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
     * 组织id
     **/
    private Integer groupId;
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

    /**
     * 真实姓名
     **/
    private String realName;
    /**
     * QQ
     **/
    private String qq;
    /**
     * 公司电话
     **/
    private String companyTel;
    /**
     * 公司地址
     **/
    private String companyAddress;
    /**
     * 个人专长
     **/
    private String specialityValue;
    /**
     * 个人简介
     **/
    private String intro;
    /**
     * 申请时间
     **/
    private Date applicationDate;
    /**
     * 邮箱验证状态
     **/
    private Integer emailVerifyState;
    /**
     * 手机验证状态
     **/
    private Integer mobileVerifyState;
    /**
     * 身份证验证状态
     **/
    private Integer idcardVerifyState;
    /**
     * 等级
     **/
    private Integer level;
    /**
     * 验证人员
     **/
    private String verifyUser;
    /**
     * 验证时间
     **/
    private Date verifyDate;
    /**
     * 用户角色id集合
     **/
    private String roleIds;
    //******登录账号
    private String account;
    //******用户类型
    private String userTypeName;
    //头像路径
    private String picPath;
    private String userPic;
    /*心跳间隔*/
    private Integer heartbeatTime;
    /*登录有效时间*/
    private Long validTime;
    /*识别该用户是否在其他设备登录
     * 0:未在其他设备登录
     * 1:已在其他设备登录*/
    private Integer isLoginIn;
    /*用户登录的设备号*/
    private String terminalImei;
    /*消费总金额*/
    private Double consumAmount;
    /*账户余额*/
    private Double balanceAmount;
    /*消费总度币*/
    private Integer consumIntegral;
    /*账户度币*/
    private Integer balanceIntegral;
    /*可用户型套数*/
    private Integer usableHouseNumber;

    /** 是否开通移动端 (0:未开通，1：已开通) **/
    private Integer existsMobile;
    /** 移动端开通时间 **/
    private Date mobileOpenedDate;
    /** 移动端截止时间 **/
    private Date mobileClosedDate;

    /**
     * 网卡校验限制(1:允许所有类型设备登录，2:所有网卡均不允许登录，3:仅允许PCI网卡登录，4:仅允许USB网卡登录，5:取消设备限制)
     */
    private Integer networkCardRestrict;

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
    private Integer franchiserAccountType;

    /**
     * 经销商账号组id
     */
    private Integer franchiserGroupId;

    private String openId;

    private Integer companyId;

    /**
     * 企业appId
     */
    private String appId;

    /**
     * 公司id
     */
    private Long miniProgramCompanyId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Long getMiniProgramCompanyId() {
        return miniProgramCompanyId;
    }

    public void setMiniProgramCompanyId(Long miniProgramCompanyId) {
        this.miniProgramCompanyId = miniProgramCompanyId;
    }

    public Integer getVisitorsRenderTimes() {
        return visitorsRenderTimes;
    }

    public void setVisitorsRenderTimes(Integer visitorsRenderTimes) {
        this.visitorsRenderTimes = visitorsRenderTimes;
    }

    public Integer getNetworkCardRestrict() {
        return networkCardRestrict;
    }

    public void setNetworkCardRestrict(Integer networkCardRestrict) {
        this.networkCardRestrict = networkCardRestrict;
    }

    public Integer getExistsMobile() {
        return existsMobile;
    }

    public void setExistsMobile(Integer existsMobile) {
        this.existsMobile = existsMobile;
    }

    public Date getMobileOpenedDate() {
        return mobileOpenedDate;
    }

    public void setMobileOpenedDate(Date mobileOpenedDate) {
        this.mobileOpenedDate = mobileOpenedDate;
    }

    public Date getMobileClosedDate() {
        return mobileClosedDate;
    }

    public void setMobileClosedDate(Date mobileClosedDate) {
        this.mobileClosedDate = mobileClosedDate;
    }


    public Integer getBusinessAdministrationId() {
        return businessAdministrationId;
    }

    public void setBusinessAdministrationId(Integer businessAdministrationId) {
        this.businessAdministrationId = businessAdministrationId;
    }

    public Integer getFranchiserAccountType() {
        return franchiserAccountType;
    }

    public void setFranchiserAccountType(Integer franchiserAccountType) {
        this.franchiserAccountType = franchiserAccountType;
    }

    public Integer getFranchiserGroupId() {
        return franchiserGroupId;
    }

    public void setFranchiserGroupId(Integer franchiserGroupId) {
        this.franchiserGroupId = franchiserGroupId;
    }

    /**
     * 详细地址
     */
    String address;

    String payCallBackServer;

    String webSocketMessage;

//	String webSocketRender;

    String serverUrl;

    /*该用户是否为 方案推荐审核管理员    1 为是  */
    private Integer designPlanCheckAdministrator;

    /**
     * 文件加密key，app端用来解密
     * add by yanghz
     */
    String cryptKey;

    /**
     * app菜单列表
     **/
    List<LoginMenu> appMenuList = new ArrayList<LoginMenu>();

    /**
     * 分布式资源存储,域名配置
     */
    private List<ResourcesUrl> resourcesUrls;

    public class ResourcesUrl {

        private List<String> modelName;

        private String domain;

        public List<String> getModelName() {
            return modelName;
        }

        public void setModelName(List<String> modelName) {
            this.modelName = modelName;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

    }

    public List<ResourcesUrl> getResourcesUrls() {
        return resourcesUrls;
    }

    public void setResourcesUrls(List<ResourcesUrl> resourcesUrls) {
        this.resourcesUrls = resourcesUrls;
    }

    public List<LoginMenu> getAppMenuList() {
        return appMenuList;
    }

    public void setAppMenuList(List<LoginMenu> appMenuList) {
        this.appMenuList = appMenuList;
    }

    public String getCryptKey() {
        return cryptKey;
    }

    public void setCryptKey(String cryptKey) {
        this.cryptKey = cryptKey;
    }

    public Integer getDesignPlanCheckAdministrator() {
        return designPlanCheckAdministrator;
    }

    public void setDesignPlanCheckAdministrator(Integer designPlanCheckAdministrator) {
        this.designPlanCheckAdministrator = designPlanCheckAdministrator;
    }

    /**
     * 用作压力测试
     */
    private String designPlanId;

    public String getDesignPlanId() {
        return designPlanId;
    }

    public void setDesignPlanId(String designPlanId) {
        this.designPlanId = designPlanId;
    }

    public String getWebSocketMessage() {
        return webSocketMessage;
    }

    public void setWebSocketMessage(String webSocketMessage) {
        this.webSocketMessage = webSocketMessage;
    }

//	public String getWebSocketRender() {
//		return webSocketRender;
//	}
//
//	public void setWebSocketRender(String webSocketRender) {
//		this.webSocketRender = webSocketRender;
//	}

    public String getPayCallBackServer() {
        return payCallBackServer;
    }

    public void setPayCallBackServer(String payCallBackServer) {
        this.payCallBackServer = payCallBackServer;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTerminalImei() {
        return terminalImei;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public void setTerminalImei(String terminalImei) {
        this.terminalImei = terminalImei;
    }

    public Integer getIsLoginIn() {
        return isLoginIn;
    }

    public void setIsLoginIn(Integer isLoginIn) {
        this.isLoginIn = isLoginIn;
    }

    public Long getValidTime() {
        return validTime;
    }

    public void setValidTime(Long validTime) {
        this.validTime = validTime;
    }

    public Integer getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(Integer heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    //个人专长
    private String specialityName;
    //驳回原因
    private String reasonRejected;
    //个人资料旧手机
    private String oldMobile;
    //是否已关注
    private Integer attentionState;

    //绑定品牌id
    private String brandIds;


    //省份
    private String provinceCode;
    //市
    private String cityCode;

    /**
     * 组织ID
     */
    private Integer orgId;

    /**
     * 组织名称
     */
    private String orgName;
    /**
     * 所在地
     **/
    private String areaName;
    /**
     * 所在地
     **/
    private String jobName;

    private String serviceUrl;

    private String resourcesUrl;

    private String siteName;

    private String sitekey;
    //粉丝数量
    private Integer fansCount;
    //关注数量
    private Integer attentionCount;
    //作品数量
    private Integer worksCount;
    //访问数量
    private Integer accessCount;
    //审核1人员
    private String auditor1;
    //审核2人员
    private String auditor2;
    //审核3人员
    private String auditor3;
    /*用户绑定设备号*/
    private String userImei;

    private Integer platformType;

    private Map<String, String> resourceMap;

    /**
     * 剩余时长
     */
    private Integer remainDate;

    /**
     * 当前享受的服务信息
     */
    private String enjoyPackageInfo;

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public Integer getRemainDate() {
        return remainDate;
    }

    public void setRemainDate(Integer remainDate) {
        this.remainDate = remainDate;
    }

    public String getEnjoyPackageInfo() {
        return enjoyPackageInfo;
    }

    public void setEnjoyPackageInfo(String enjoyPackageInfo) {
        this.enjoyPackageInfo = enjoyPackageInfo;
    }

    public Map<String, String> getResourceMap() {
        return resourceMap;
    }

    public void setResourceMap(Map<String, String> resourceMap) {
        this.resourceMap = resourceMap;
    }

    public String getUserImei() {
        return userImei;
    }

    public void setUserImei(String userImei) {
        this.userImei = userImei;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    public String getOldMobile() {
        return oldMobile;
    }

    public void setOldMobile(String oldMobile) {
        this.oldMobile = oldMobile;
    }

    public String getReasonRejected() {
        return reasonRejected;
    }

    public void setReasonRejected(String reasonRejected) {
        this.reasonRejected = reasonRejected;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }


    public String getAuditor1() {
        return auditor1;
    }

    public void setAuditor1(String auditor1) {
        this.auditor1 = auditor1;
    }

    public String getAuditor2() {
        return auditor2;
    }

    public void setAuditor2(String auditor2) {
        this.auditor2 = auditor2;
    }

    public String getAuditor3() {
        return auditor3;
    }

    public void setAuditor3(String auditor3) {
        this.auditor3 = auditor3;
    }

    public Integer getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }


    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public Integer getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(Integer attentionCount) {
        this.attentionCount = attentionCount;
    }

    public Integer getWorksCount() {
        return worksCount;
    }

    public void setWorksCount(Integer worksCount) {
        this.worksCount = worksCount;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSitekey() {
        return sitekey;
    }

    public void setSitekey(String sitekey) {
        this.sitekey = sitekey;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaLongCode() {
        return areaLongCode;
    }

    public void setAreaLongCode(String areaLongCode) {
        this.areaLongCode = areaLongCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAtt3() {
        return att3;
    }

    public void setAtt3(String att3) {
        this.att3 = att3;
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getSpecialityValue() {
        return specialityValue;
    }

    public void setSpecialityValue(String specialityValue) {
        this.specialityValue = specialityValue;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Integer getEmailVerifyState() {
        return emailVerifyState;
    }

    public void setEmailVerifyState(Integer emailVerifyState) {
        this.emailVerifyState = emailVerifyState;
    }

    public Integer getMobileVerifyState() {
        return mobileVerifyState;
    }

    public void setMobileVerifyState(Integer mobileVerifyState) {
        this.mobileVerifyState = mobileVerifyState;
    }

    public Integer getIdcardVerifyState() {
        return idcardVerifyState;
    }

    public void setIdcardVerifyState(Integer idcardVerifyState) {
        this.idcardVerifyState = idcardVerifyState;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getVerifyUser() {
        return verifyUser;
    }

    public void setVerifyUser(String verifyUser) {
        this.verifyUser = verifyUser;
    }


    public Date getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getResourcesUrl() {
        return resourcesUrl;
    }

    public void setResourcesUrl(String resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
    }

    public Integer getAttentionState() {
        return attentionState;
    }

    public void setAttentionState(Integer attentionState) {
        this.attentionState = attentionState;
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
        SysUser other = (SysUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
                && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
                && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
                && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
                && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
                && (this.getUserType() == null ? other.getUserType() == null : this.getUserType().equals(other.getUserType()))
                && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
                && (this.getJob() == null ? other.getJob() == null : this.getJob().equals(other.getJob()))
                && (this.getAreaId() == null ? other.getAreaId() == null : this.getAreaId().equals(other.getAreaId()))
                && (this.getPicId() == null ? other.getPicId() == null : this.getPicId().equals(other.getPicId()))
                && (this.getMediaType() == null ? other.getMediaType() == null : this.getMediaType().equals(other.getMediaType()))
                && (this.getAppKey() == null ? other.getAppKey() == null : this.getAppKey().equals(other.getAppKey()))
                && (this.getToken() == null ? other.getToken() == null : this.getToken().equals(other.getToken()))
                && (this.getAtt3() == null ? other.getAtt3() == null : this.getAtt3().equals(other.getAtt3()))
                && (this.getAtt4() == null ? other.getAtt4() == null : this.getAtt4().equals(other.getAtt4()))
                && (this.getAtt5() == null ? other.getAtt5() == null : this.getAtt5().equals(other.getAtt5()))
                && (this.getAtt6() == null ? other.getAtt6() == null : this.getAtt6().equals(other.getAtt6()))
                && (this.getDateAtt1() == null ? other.getDateAtt1() == null : this.getDateAtt1().equals(other.getDateAtt1()))
                && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
                && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
                && (this.getNumAtt2() == null ? other.getNumAtt2() == null : this.getNumAtt2().equals(other.getNumAtt2()))
                && (this.getNumAtt3() == null ? other.getNumAtt3() == null : this.getNumAtt3().equals(other.getNumAtt3()))
                && (this.getNumAtt4() == null ? other.getNumAtt4() == null : this.getNumAtt4().equals(other.getNumAtt4()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
                && (this.getOrgName() == null ? other.getOrgName() == null : this.getOrgName().equals(other.getOrgName()))
                && (this.getBrandIds() == null ? other.getBrandIds() == null : this.getBrandIds().equals(other.getBrandIds()))
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
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getUserType() == null) ? 0 : getUserType().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getJob() == null) ? 0 : getJob().hashCode());
        result = prime * result + ((getAreaId() == null) ? 0 : getAreaId().hashCode());
        result = prime * result + ((getPicId() == null) ? 0 : getPicId().hashCode());
        result = prime * result + ((getMediaType() == null) ? 0 : getMediaType().hashCode());
        result = prime * result + ((getAppKey() == null) ? 0 : getAppKey().hashCode());
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getAtt3() == null) ? 0 : getAtt3().hashCode());
        result = prime * result + ((getAtt4() == null) ? 0 : getAtt4().hashCode());
        result = prime * result + ((getAtt5() == null) ? 0 : getAtt5().hashCode());
        result = prime * result + ((getAtt6() == null) ? 0 : getAtt6().hashCode());
        result = prime * result + ((getDateAtt1() == null) ? 0 : getDateAtt1().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getNumAtt2() == null) ? 0 : getNumAtt2().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getOrgName() == null) ? 0 : getOrgName().hashCode());
        result = prime * result + ((getBrandIds() == null) ? 0 : getBrandIds().hashCode());
        return result;
    }

    /**
     * 获取对象的copy
     **/
    public SysUser copy() {
        SysUser obj = new SysUser();
        obj.setSysCode(this.sysCode);
        obj.setCreator(this.creator);
        obj.setGmtCreate(this.gmtCreate);
        obj.setModifier(this.modifier);
        obj.setGmtModified(this.gmtModified);
        obj.setIsDeleted(this.isDeleted);
        obj.setUserName(this.userName);
        obj.setNickName(this.nickName);
        obj.setPassword(this.password);
        obj.setEmail(this.email);
        obj.setMobile(this.mobile);
        obj.setUserType(this.userType);
        obj.setSex(this.sex);
        obj.setJob(this.job);
        obj.setAreaId(this.areaId);
        obj.setPicId(this.picId);
        obj.setMediaType(this.mediaType);
        obj.setAppKey(this.appKey);
        obj.setToken(this.token);
        obj.setAtt3(this.att3);
        obj.setAtt4(this.att4);
        obj.setAtt5(this.att5);
        obj.setAtt6(this.att6);
        obj.setDateAtt1(this.dateAtt1);
        obj.setDateAtt2(this.dateAtt2);
        obj.setGroupId(this.groupId);
        obj.setNumAtt2(this.numAtt2);
        obj.setNumAtt3(this.numAtt3);
        obj.setNumAtt4(this.numAtt4);
        obj.setRemark(this.remark);
        obj.setOrgId(this.orgId);
        obj.setOrgName(this.orgName);
        obj.setBrandIds(this.brandIds);

        return obj;
    }

    /**
     * 获取对象的copy
     **/
    public LoginUser toLoginUser() {
        LoginUser loginUser = null;
        if (null != this.id) {
            //初始化对象
            loginUser = new LoginUser();
            loginUser.setId(this.id);
            loginUser.setUserType(this.userType);
            loginUser.setLoginId(this.sysCode);
            loginUser.setLoginName(this.nickName);
            loginUser.setLoginPhone(this.mobile == null ? "" : this.mobile.toString());
            loginUser.setLoginEmail(this.email);
            loginUser.setName(this.userName);
            loginUser.setAppKey(this.appKey);
            loginUser.setToken(this.token);
            loginUser.setDeviceId(this.mediaType == null ? "" : this.mediaType.toString());
            loginUser.setMsgId(this.getMsgId());
            loginUser.setGroupId(this.groupId);
            loginUser.setSex(this.sex);
            loginUser.setPicPath(this.picPath);
            loginUser.setBrandIds(this.brandIds);
        }

        return loginUser;
    }

    /**
     * 获取对象的map
     **/
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sysCode", this.sysCode);
        map.put("creator", this.creator);
        map.put("gmtCreate", this.gmtCreate);
        map.put("modifier", this.modifier);
        map.put("gmtModified", this.gmtModified);
        map.put("isDeleted", this.isDeleted);
        map.put("userName", this.userName);
        map.put("nickName", this.nickName);
        map.put("password", this.password);
        map.put("email", this.email);
        map.put("mobile", this.mobile);
        map.put("userType", this.userType);
        map.put("sex", this.sex);
        map.put("job", this.job);
        map.put("areaId", this.areaId);
        map.put("picId", this.picId);
        map.put("mediaType", this.mediaType);
        map.put("appKey", this.appKey);
        map.put("token", this.token);
        map.put("att3", this.att3);
        map.put("att4", this.att4);
        map.put("att5", this.att5);
        map.put("att6", this.att6);
        map.put("dateAtt1", this.dateAtt1);
        map.put("dateAtt2", this.dateAtt2);
        map.put("numAtt1", this.groupId);
        map.put("numAtt2", this.numAtt2);
        map.put("numAtt3", this.numAtt3);
        map.put("numAtt4", this.numAtt4);
        map.put("remark", this.remark);
        map.put("orgId", this.orgId);
        map.put("orgName", this.orgName);
        map.put("brandIds", this.brandIds);
        return map;
    }

    public Double getConsumAmount() {
        return consumAmount;
    }

    public void setConsumAmount(Double consumAmount) {
        this.consumAmount = consumAmount;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Integer getConsumIntegral() {
        return consumIntegral;
    }

    public void setConsumIntegral(Integer consumIntegral) {
        this.consumIntegral = consumIntegral;
    }

    public Integer getBalanceIntegral() {
        return balanceIntegral;
    }

    public void setBalanceIntegral(Integer balanceIntegral) {
        this.balanceIntegral = balanceIntegral;
    }

    public Integer getUsableHouseNumber() {
        return usableHouseNumber;
    }

    public void setUsableHouseNumber(Integer usableHouseNumber) {
        this.usableHouseNumber = usableHouseNumber;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", webSocketServer='" + webSocketServer + '\'' +
                ", sysCode='" + sysCode + '\'' +
                ", creator='" + creator + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                ", isDeleted=" + isDeleted +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userType=" + userType +
                ", sex=" + sex +
                ", job='" + job + '\'' +
                ", areaId=" + areaId +
                ", areaLongCode='" + areaLongCode + '\'' +
                ", picId=" + picId +
                ", mediaType=" + mediaType +
                ", appKey='" + appKey + '\'' +
                ", token='" + token + '\'' +
                ", att3='" + att3 + '\'' +
                ", att4='" + att4 + '\'' +
                ", att5='" + att5 + '\'' +
                ", att6='" + att6 + '\'' +
                ", dateAtt1=" + dateAtt1 +
                ", dateAtt2=" + dateAtt2 +
                ", groupId=" + groupId +
                ", numAtt2=" + numAtt2 +
                ", numAtt3=" + numAtt3 +
                ", numAtt4=" + numAtt4 +
                ", remark='" + remark + '\'' +
                ", realName='" + realName + '\'' +
                ", qq='" + qq + '\'' +
                ", companyTel='" + companyTel + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", specialityValue='" + specialityValue + '\'' +
                ", intro='" + intro + '\'' +
                ", applicationDate=" + applicationDate +
                ", emailVerifyState=" + emailVerifyState +
                ", mobileVerifyState=" + mobileVerifyState +
                ", idcardVerifyState=" + idcardVerifyState +
                ", level=" + level +
                ", verifyUser='" + verifyUser + '\'' +
                ", verifyDate=" + verifyDate +
                ", roleIds='" + roleIds + '\'' +
                ", account='" + account + '\'' +
                ", userTypeName='" + userTypeName + '\'' +
                ", picPath='" + picPath + '\'' +
                ", userPic='" + userPic + '\'' +
                ", heartbeatTime=" + heartbeatTime +
                ", validTime=" + validTime +
                ", isLoginIn=" + isLoginIn +
                ", terminalImei='" + terminalImei + '\'' +
                ", consumAmount=" + consumAmount +
                ", balanceAmount=" + balanceAmount +
                ", address='" + address + '\'' +
                ", payCallBackServer='" + payCallBackServer + '\'' +
                ", webSocketMessage='" + webSocketMessage + '\'' +
                ", serverUrl='" + serverUrl + '\'' +
                ", designPlanCheckAdministrator=" + designPlanCheckAdministrator +
                ", cryptKey='" + cryptKey + '\'' +
                ", appMenuList=" + appMenuList +
                ", resourcesUrls=" + resourcesUrls +
                ", designPlanId='" + designPlanId + '\'' +
                ", specialityName='" + specialityName + '\'' +
                ", reasonRejected='" + reasonRejected + '\'' +
                ", oldMobile='" + oldMobile + '\'' +
                ", attentionState=" + attentionState +
                ", brandIds='" + brandIds + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", orgId=" + orgId +
                ", orgName='" + orgName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", jobName='" + jobName + '\'' +
                ", serviceUrl='" + serviceUrl + '\'' +
                ", resourcesUrl='" + resourcesUrl + '\'' +
                ", siteName='" + siteName + '\'' +
                ", sitekey='" + sitekey + '\'' +
                ", fansCount=" + fansCount +
                ", attentionCount=" + attentionCount +
                ", worksCount=" + worksCount +
                ", accessCount=" + accessCount +
                ", auditor1='" + auditor1 + '\'' +
                ", auditor2='" + auditor2 + '\'' +
                ", auditor3='" + auditor3 + '\'' +
                ", userImei='" + userImei + '\'' +
                ", resourceMap=" + resourceMap +
                ", consumIntegral=" + consumIntegral +
                ", balanceIntegral=" + balanceIntegral +
                ", usableHouseNumber=" + usableHouseNumber +
                '}';
    }
}
