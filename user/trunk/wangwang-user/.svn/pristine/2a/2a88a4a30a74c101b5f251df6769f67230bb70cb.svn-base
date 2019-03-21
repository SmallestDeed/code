package com.sandu.api.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserEquipment implements Serializable {

    //用户登录设备校验开关
    public static final String DEVICE_USER_CHECK_CONFIG_NAME = "isDeviceUserCheck";
    //关闭用户登录设备校验开关(不需校验)
    public static final int CLOSE_DEVICE_USER_CHECK = 0;
    //打开登录设备校验开关(需要校验)
    public static final int OPEN_DEVICE_USER_CHECK = 1;
    //网卡校验限制(1:允许所有类型网卡登录)
    public static final int ALLOW_ALL_NETWORD_CARD = 1;
    //网卡校验限制(2:所有网卡均不允许登录)
    public static final int NOT_ALLOW_ALL_NETWORD_CARD = 2;
    //网卡校验限制(3:仅允许PCI网卡登录)
    public static final int ONLY_ALLOW_PCI_NETWORD_CARD = 3;
    //网卡校验限制(4:仅允许USB网卡登录)
    public static final int ONLY_ALLOW_USB_NETWORD_CARD = 4;
    //网卡校验限制(5:取消设备限制)
    public static final int CANCEL_NETWORD_CARD_DEVICE_RESTRICT = 5;
    private static final long serialVersionUID = 1L;
    /***/
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 设备号(PCI网卡物理地址)
     */
    private String userImei;
    /**
     * 设备类型
     */
    private String equipmentType;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 是否删除
     */
    private Integer isDeleted;

    //USB网卡物理地址
    private String usbTerminalImei;

    //网卡校验限制(1:允许PCI/USB网卡登录,2:所有网卡均不允许登录,3:仅允许PCI网卡登录,4:仅允许USB网卡登录，)
    private int networkCardRestrict;

}
