package com.sandu.api.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandu
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 2973800131086029070L;
    /**  */
    private Integer id;
    /** 用户名 */
    private String userName;
    /** 昵称 */
    private String nickName;
    /** 移动 */
    private String mobile;
    /** 密码 */
    private String password;
    /** 邮箱 */
    private String email;
    /** 用户类型 */
    private Integer userType;
    /** 性别 */
    private Integer sex;
    /** 职业 */
    private String job;
    /** 区域 */
    private Integer areaId;
    /**  */
    private Integer picId;
    /** 媒介类型 */
    private Integer mediaType;
    /**  */
    private String sysCode;
    /** 创建者 */
    private String creator;
    /**  */
    private Date gmtCreate;
    /** 修改人 */
    private String modifier;
    /** 修改时间 */
    private Date gmtModified;
    /** 是否删除 */
    private Integer isDeleted;
    /**  */
    private String appKey;
    /** 字符备用2 */
    private String token;
    /** 字符备用3 */
    private String att3;
    /** 字符备用4 */
    private String att4;
    /** 字符备用5 */
    private String att5;
    /** 字符备用6 */
    private String att6;
    /** 时间备用1 */
    private Date dateAtt1;
    /** 时间备用2 */
    private Date dateAtt2;
    /** 组织id */
    private Integer groupId;
    /** 整数备用2 */
    private Integer numAtt2;
    /** 数字备用1 */
    private String numAtt3;
    /** 数字备用2 */
    private String numAtt4;
    /** 备注 */
    private String remark;
    /** 真实名字 */
    private String realName;
    /** QQ号码 */
    private String qq;
    /** 公司电话 */
    private String companyTel;
    /** 公司地址 */
    private String companyAddress;
    /**  */
    private String specialityValue;
    /**  */
    private String intro;
    /**  */
    private Integer emailVerifyState;
    /**  */
    private Integer mobileVerifyState;
    /**  */
    private Integer idcardVerifyState;
    /**  */
    private Integer level;
    /**  */
    private Date applicationDate;
    /**  */
    private String verifyUser;
    /**  */
    private Date verifyDate;
    /**  */
    private String areaLongCode;
    /**  */
    private String reasonRejected;
    /**  */
    private String brandIds;
    /**  */
    private String userImei;
    /**  */
    private String consumAmount;
    /**  */
    private String balanceAmount;
    /** 详细地址 */
    private String address;
    /** 网卡校验限制(1:允许所有类型设备登录，2:所有网卡均不允许登录，3:仅允许PCI网卡登录，4:仅允许USB网卡登录，5:取消设备限制) */
    private Integer networkCardRestrict;
    /** 是否开通移动端(0:未开通，1：已开通) */
    private Integer existsMobile;
    /** 移动端开通时间 */
    private String mobileOpenedDate;
    /** 移动端截止时间 */
    private String mobileClosedDate;
    /** 可用户型套数 */
    private Integer usableHouseNumber;
    /** 游客可渲染次数，默认5次 */
    private Integer visitorsRenderTimes;
    /** 企业/经销商id */
    private Integer businessAdministrationId;
    /** 用户企业id */
    private Integer companyId;
    /** 经销商账号类型：0(散)、1(主账号)、2(子账号) */
    private Integer franchiserAccountType;
    /** 经销商账号组id */
    private Integer franchiserGroupId;
    /** 是否需要更新密码(0:不需要更新,1:需要更新) */
    private Integer passwordUpdateFlag;
    /** 是否确认(0:未确认、1:已确认) */
    private Integer verifyFlag;
    /** 消费总积分 */
    private Integer consumIntegral;
    /** 账号积分 */
    private Integer balanceIntegral;
    /** 工作岗位类型 */
    private Integer postType;
    /** 上一次登录企业id */
    private Integer lastLoginCompanyId;
    /** 是否有登录过:0,未登录;1,已经登录过 */
    private Integer isLoginBefore;
    /** 平台类型:1:C端用户,2:B端用户 */
    private Integer platformType;
    /** 用户微信openid */
    private String openId;
    /** 企业微信appid */
    private String appId;
    /** 微信小程序用户对应企业id */
    private Integer miniProgramCompanyId;
    /** 首次登录时间 */
    private Date firstLoginTime;
    /** 使用类型（0：试用、1：正式） */
    private Integer useType;
    /** 有效时长（月/天） */
    private Integer validTime;
    /** 失效时间 */
    private Date failureTime;
    /** 业务类型:0为普通生成账号,1为套餐购买生成的账号 */
    private Integer servicesFlag;
    /** 用户来源 */
    private Integer userSource;
    /** 是否展示三度一键方案：0:否,1:是 */
    private Integer showSanduPlan;
    /** 街道编码 */
    private String streetCode;
    /** 省编码 */
    private String provinceCode;
    /** 市编码 */
    private String cityCode;
    /** 区域编码 */
    private String areaCode;
    /** uuid */
    private String uuid;


}
