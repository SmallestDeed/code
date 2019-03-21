package com.sandu.api.callback.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-system
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 10/23/2018 3:54 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordAdClick implements Serializable {

    private Integer id;

    /**
     * 上报来源：toutiao,
     */
    private String origin;

    /**
     * 设备系统
     */
    private String deviceOs;

    /**
     * 设备主 ID
     * Android 指： UUID
     * iOS 指：IDFA
     */
    private String deviceMainId;

    /**
     * 设备副 ID
     * Android 指：ANDROIDID 1
     * iOS 指：UDID
     */
    private String deviceViceId;

    /**
     * 设备通信串号
     */
    private String deviceImei;

    /**
     * 设备 MAC 地址
     */
    private String deviceMac;

    /**
     * 客户端访问 IP
     */
    private String clientIp;

    /**
     * 状态：是否已匹配激活记录(反馈给广告服务器)
     * - 0 未匹配
     * - 1 已匹配
     */
    private Integer status;

    /**
     * 上报的数据
     */
    private String reportData;

    /**
     * 创建时间
     */
    private Date gmtCreate;

}
