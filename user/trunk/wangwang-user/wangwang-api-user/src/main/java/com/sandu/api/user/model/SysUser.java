package com.sandu.api.user.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = 4871695451822036621L;
    /**
     * 内部用户
     */
    public static final int USER_TYPE_INTERNAL = 1;
    /**
     * 厂商
     */
    public static final int USER_TYPE_COMPANY = 2;
    /**
     * 经销商
     */
    public static final int USER_TYPE_FRANCHISER = 3;
    /**
     * 设计公司
     */
    public static final int USER_TYPE_DESIGN_COMPANY = 4;
    /**
     * 设计师
     */
    public static final int USER_TYPE_DESIGNER = 5;
    /**
     * 装修公司
     */
    public static final int USER_TYPE_DECORATE_COMPANY = 6;
    /**
     * 学校(培训机构)
     */
    public static final int USER_TYPE_TRAINING_INSTITUTIONS = 7;
    /**
     * 普通用户
     */
    public static final int USER_TYPE_ORDINARY_USER = 8;
    /**
     * 游客
     */
    public static final int USER_TYPE_VISITOR = 9;
    
    /**
     * 工长
     */
    public static final int USER_TYPE_FOREMAN = 13;
    /**
     * 企业内部用户
     */
    public static final int USER_TYPE_COMPANY_INTERNAL_USER = 10;
    
    /**
     * 独立经销商
     */
    public static final int USER_TYPE_INDEPENDENT_FRANCHISER = 14;
    
    /** 经销商账号类型：0(散)**/
    public static final int FRANCHISER_ACCOUNT_TYPE_OHTER = 0;
    /** 经销商账号类型：1(主账号)**/
    public static final int FRANCHISER_ACCOUNT_TYPE_MAIN = 1;
    /** 经销商账号类型：2(子账号)**/
    public static final int FRANCHISER_ACCOUNT_TYPE_SUB = 2;
    
    //c端用户
    public static final int PLATFORM_TYPE_C=1;
    //b端用户
    public static final int PLATFORM_TYPE_B=2;
    /**
     * 主键id
     **/
    private Long id;
    /**
     * 用户名
     **/
    private String userName;
    /**
     * 昵称
     **/
    private String nickName;
    /**
     * 移动
     **/
    private String mobile;
    /**
     * 密码
     **/
    private String password;
    /**
     * 邮箱
     **/
    private String email;
    /**
     * 用户类型:(1:内部用户,2:厂商,3:经销商,4:设计公司,5:设计师,6:装修公司,7:学校(培训机构),8:普通用户)
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
     * 头像id
     **/
    private Long picId;
    /**
     * 媒介类型
     **/
    private Integer mediaType;
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
     * 申请时间
     **/
    private Date applicationDate;
    /**
     * 验证人员
     **/
    private String verifyUser;
    /**
     * 验证时间
     **/
    private Date verifyDate;
    /**
     * 区域 长编码（省市）
     **/
    private String areaLongCode;
    /**
     * 驳回原因
     **/
    private String reasonRejected;
    /**
     * 绑定品牌id
     **/
    private String brandIds;
    /**
     * 用户绑定设备号
     **/
    private String userImei;
    /**
     * 消息金额
     **/
    private Double consumAmount;
    /**
     * 余额
     **/
    private Double balanceAmount;
    /**
     * 详细地址
     **/
    private String address;
    /**
     * 消费总积分
     **/
    private Integer consumIntegral;
    /**
     * 账号积分
     **/
    private Integer balanceIntegral;
    /**
     * 可用户型套数
     **/
    private Integer usableHouseNumber;
    /**
     * 网卡校验限制(1:允许所有类型设备登录，2:所有网卡均不允许登录，3:仅允许PCI网卡登录，4:仅允许USB网卡登录，5:取消设备限制)
     **/
    private Integer networkCardRestrict;
    /**
     * 是否开通移动端(0:未开通，1：已开通)
     **/
    private Integer existsMobile;
    /**
     * 移动端开通时间
     **/
    private Date mobileOpenedDate;
    /**
     * 移动端截止时间
     **/
    private Date mobileClosedDate;
    
    /**
     * 企业/经销商id
     */
    private Long businessAdministrationId;
    
    /**
     * 经销商账号类型：0(散)、1(主账号)、2(子账号)
     */
    private Integer franchiserAccountType;
    
    /**
     * 经销商账号组id
     */
    private Integer franchiserGroupId;
    
    /**
     * 密码更新标识(0:不需要更新,1:需要更新)
     */
    private Integer passwordUpdateFlag;
    
    /**
     * 是否有登录过:0,未登录;1,已经登录过
     */
    private Integer isLoginBefore;
    
    /**
     * 平台类型:1:C端用户,2:B端用户
     */
    private Integer platformType;
    
    private String openId;
    /**
     * 企业微信appId
     */
    private String appId;

    private Long miniProgramCompanyId;
    
    /**
     * 手机短信验证码
     * 
     * */
    private String phoneCode;
    
    private Integer comfirmFlag;
    
    private Long lastLoginCompanyId;

    /**
     * 用户首次登录时间
     */
    private Date firstLoginTime;

    /**
     * 用户使用类型
     */
    private Integer useType;

    /**
     * 有效时长
     */
    private Integer validTime;

    /**
     * 失效时间
     */
    private Date failureTime;

    /**
     * 企业Id
     */
    private Long companyId;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 城市名称
     */
    private String areaName;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 业务类型:0为普通生成账号,1为套餐购买生成的账号
     */
    private Integer servicesFlag;

    private String picPath;

    /**
     * 是否展示三度一键方案
     */
    private Integer showSanduPlan;

    /**
     * 用户来源
     */
    private Integer userSource;


    /**省地区编码**/
    private String provinceCode;

    /**市地区编码**/
    private String cityCode;

    /**区地区编码**/
    private String areaCode;

    /**街道地区编码**/
    private String streetCode;

    /**账号UUID**/
    private String uuid;



    /**来源企业**/
    private Integer sourceCompany;

    /**我的邀请码**/
    private String myInvitationCode;

    /**我使用的邀请码**/
    private String usedInvitationCode;

    /**
     * 来源企业名称
     */
    private String sourceCompanyName;

    /**省的名称**/
    private String provinceName;

    /**市的名称**/
    private String cityName;
    
    private List<Integer> userTypeList;

    private String headPic;

    //套餐过期标记
    private boolean maturityFlag;

    //套餐类型 =>{} 1.使用 0.正式
    private Integer serviceType;

    //套餐名称
    private  String serviceName;
}
