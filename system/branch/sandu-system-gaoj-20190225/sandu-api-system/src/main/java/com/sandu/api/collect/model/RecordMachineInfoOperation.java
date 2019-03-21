package com.sandu.api.collect.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RecordMachineInfoOperation implements Serializable{
    private static final long serialVersionUID = 3557077942509392077L;
    /**
     * 
     */
    private Long id;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 公司Id
     */
    private Integer companyId;

    /**
     * APP请求头信息
     */
    private String userAgent;

    /**
     * APP名称
     */
    private String appName;

    /**
     * APP版本
     */
    private String appVersion;

    /**
     * 请求IP地址
     */
    private String appIp;

    /**
     * 手机系统
     */
    private String os;

    /**
     * 手机系统版本
     */
    private String osVersion;

    /**
     * 手机品牌
     */
    private String phoneBrand;

    /**
     * 手机型号
     */
    private String phoneModel;

    /**
     * 手机MAC地址
     */
    private String mac;

    /**
     * 手机idfa
     */
    private String idfa;

    /**
     * 手机AndroidID
     */
    private String androidId;

    /**
     * 手机的IMEI
     */
    private String imei;

    /**
     * 手机的uuid
     */
    private String uuid;

    /**
     * 手机Open UDID
     */
    private String openId;

    /**
     * 手机udid
     */
    private String udid;

    /**
     * 手机屏幕宽度
     */
    private String screenWidth;

    /**
     * 手机屏幕高度
     */
    private String screenHeight;

    /**
     * 1:激活;2:注册
     */
    private Integer recordType;

    /**
     * 系统编码
     */
    private String sysCode;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
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
     * 备注
     */
    private String remark;

}