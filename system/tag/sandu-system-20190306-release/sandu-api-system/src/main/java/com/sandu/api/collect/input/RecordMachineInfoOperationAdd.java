package com.sandu.api.collect.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class RecordMachineInfoOperationAdd implements Serializable{

    private static final long serialVersionUID = -1991650966217549944L;

    /**
     * APP请求头信息
     */
    @NotNull(message = "APP请求头信息不能为空")
    private String userAgent;

    /**
     * APP名称
     */
    @NotNull(message = "APP名称不能为空")
    private String appName;

    /**
     * APP版本
     */
    @NotNull(message = "APP版本不能为空")
    private String appVersion;

    /**
     * 请求IP地址
     */
    @NotNull(message = "请求IP地址不能为空")
    private String appIp;

    /**
     * 手机系统
     */
    @NotNull(message = "手机系统不能为空")
    private String os;

    /**
     * 手机系统版本
     */
    @NotNull(message = "手机系统版本不能为空")
    private String osVersion;

    @ApiModelProperty(value = "手机品牌", required = true)
    @NotNull(message = "手机品牌不能为空")
    private String phoneBrand;

    /**
     * 手机型号
     */
    @ApiModelProperty(value = "手机型号", required = true)
    @NotNull(message = "手机型号不能为空")
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
     * 1:激活;2:注册;3:登录
     */
    @NotNull(message = "业务记录类型")
    private Integer recordType;

    /**
     * 用户Id
     */
    private Integer userId;

}