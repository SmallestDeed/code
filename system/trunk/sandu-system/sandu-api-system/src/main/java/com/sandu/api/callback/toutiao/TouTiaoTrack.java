package com.sandu.api.callback.toutiao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-system
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 10/23/2018 4:23 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TouTiaoTrack implements Serializable {

    /**
     * 广告后台生成的点击 ID (必填)
     */
    private String callback;

    /**
     * 设备 ID (必填)
     */
    private String muid;

    /**
     * 客户端操作系统的类型: 0–Android, 1–iOS
     * (必填)
     */
    private Integer os;

    /**
     * 激活数据来源
     */
    private String source;

    /**
     * 转化发生时间
     */
    private Long conv_time;

    /**
     * 激活请求的签名
     */
    private String signature;

    /**
     * 区分回传的信息 (必填):
     * - 0：active 激活
     *
     * - 1：active_register 激活且注册
     *
     * - 2：active_pay 激活且付费
     */
    private Integer event_type;
}
