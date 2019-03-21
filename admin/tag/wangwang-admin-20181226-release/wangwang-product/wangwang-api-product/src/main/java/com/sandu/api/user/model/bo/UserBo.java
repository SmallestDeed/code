package com.sandu.api.user.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandu
 */
@Data
public class UserBo implements Serializable {
    /** */
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

    /** */
    private Integer picId;

    /**
     * 媒介类型
     */
    private Integer mediaType;

    /** */
    private String sysCode;

    /**
     * 创建者
     */
    private String creator;

    /** */
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

    /** */
    private String appKey;

    /**
     * 字符备用2
     */
    private String token;


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

    /** */
    private String specialityValue;

    /** */
    private String intro;

    /** */
    private Integer emailVerifyState;

    /** */
    private Integer mobileVerifyState;

    /** */
    private Integer idcardVerifyState;

    /** */
    private Integer level;

    /** */
    private Date applicationDate;

    /** */
    private String verifyUser;

    /** */
    private Date verifyDate;

    /** */
    private String areaLongCode;

    /** */
    private String reasonRejected;

    /** */
    private String brandIds;

    /** */
    private String userImei;

    /** */
    private Double consumAmount;

    /** */
    private Double balanceAmount;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 消费总积分
     */
    private Integer consumIntegral;

    /**
     * 账号积分
     */
    private Integer balanceIntegral;

    /**
     * 可用户型套数
     */
    private Integer usableHouseNumber;

    /**
     * 网卡校验限制(1:允许所有类型网卡登录，2:所有网卡均不允许登录，3:仅允许PCI网卡登录，4:仅允许USB网卡登录，5:取消设备限制)
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
     * 游客可渲染次数，默认5次
     */
    private Integer visitorsRenderTimes;
    private String picPath;

    private Integer companyId;

    private Integer businessAdministrationId;

    private String uuid;
}
