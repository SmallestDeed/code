package com.sandu.api.base.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class SysUser implements Serializable {
    private Long id;

    private String userName;

    private String nickName;

    private String mobile;

    private String password;

    private String email;

    private Integer userType;

    private Integer sex;

    private String job;

    private Integer areaId;

    private Integer picId;

    private Integer mediaType;

    private String sysCode;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String appKey;

    private String token;

    private String att3;

    private String att4;

    private String att5;

    private String att6;

    private Date dateAtt1;

    private Date dateAtt2;

    private Integer groupId;

    private Integer numAtt2;

    private BigDecimal numAtt3;

    private BigDecimal numAtt4;

    private String remark;

    private String realName;

    private String qq;

    private String companyTel;

    private String companyAddress;

    private String specialityValue;

    private String intro;

    private Integer emailVerifyState;

    private Integer mobileVerifyState;

    private Integer idcardVerifyState;

    private Integer level;

    private Date applicationDate;

    private String verifyUser;

    private Date verifyDate;

    private String areaLongCode;

    private String reasonRejected;

    private String brandIds;

    private String userImei;

    private BigDecimal consumAmount;

    private BigDecimal balanceAmount;

    private String address;

    private Integer networkCardRestrict;

    private Integer existsMobile;

    private Date mobileOpenedDate;

    private Date mobileClosedDate;

    private Integer usableHouseNumber;

    private Integer visitorsRenderTimes;

    private Integer businessAdministrationId;

    private Integer companyId;

    private Byte franchiserAccountType;

    private Integer franchiserGroupId;

    private Byte passwordUpdateFlag;

    private Integer verifyFlag;

    private Integer consumIntegral;

    private Integer balanceIntegral;

    private Integer postType;

    private Long lastLoginCompanyId;

    private Integer isLoginBefore;

    private Integer platformType;

    private String openId;

    private String appId;

    private Long miniProgramCompanyId;

    private Date firstLoginTime;

    private Integer useType;

    private Integer validTime;

    private Date failureTime;

    private Byte servicesFlag;

    private Byte userSource;

    private Boolean showSanduPlan;

    private String streetCode;

    private String provinceCode;

    private String cityCode;

    private String areaCode;

    private String uuid;

    private String headPic;
}