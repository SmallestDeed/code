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
 * @datetime 10/23/2018 2:54 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TouTiaoReportClick implements Serializable {

    /**
     * 广告计划 id
     * 值为： __AID__
     */
    private String adid;

    /**
     * 广告创意 id
     * 值为： __CID__
     */
    private String cid;

    /**
     * 广告投放位置
     * 1=头条信息流、3=详情页、11=段子 信息流
     * 值为： __CSITE__
     */
    private String csite;

    /**
     * 创意样式: 2=小图模式 3=大图式 4=组图模式 5=视频
     * 值为： __CTYPE__
     */
    private String ctype;

    /**
     * ⽤户终端的 MAC 地址
     * 保留分隔符 ":",(采用获取原始值)取 md5sum 摘要（备注：入网硬件地址）
     * 值为： __MAC1__
     */
    private String mac;

    /**
     * user_agent (urlencode 编码)
     * 值为： __UA1__
     */
    private String ua;

    /**
     * 用户终端的IMEI,15 位数字
     * 安卓广告唯一标识,imei 双卡手机可能有两个
     * 值为： __IMEI__
     */
    private String imei;

    /**
     * 用户终端 AndroidID
     * 安卓硬件设备唯一标识
     * 值为： __ANDROIDID1__
     */
    private String androidid;

    /**
     * 用户终端的 UUID
     * 安卓手机系统生成的设备 ID
     * 值为： __UUID__
     */
    private String uuid;

    /**
     * iOS 手机广告唯一标识
     * 值为： __IDFA__
     */
    private String idfa;

    /**
     * iOS UDID
     * IOS 手机硬件唯一标识，一般情况下取不到
     * 值为： __UDID__
     */
    private String udid;

    /**
     * IOS 手机用软件生成的一个可变的替代 UDID 的标识，通过第三方的
     * Open UDID SDK 生成
     * 值为： __OPENUDID__
     */
    private String openudid;

    /**
     * 客户端操作系统的类型： 0–Android； 1–iOS 2– WP；  3-Others
     * 值为： __OS__
     */
    private Integer os;

    /**
     * 的用户终端的公共 IP 地址
     * A.B.C.D(4 段点分)
     */
    private String ip;

    /**
     * 客户端触发监测的时间
     * UTC 时间戳
     * 值为： __TS__
     */
    private Long timestamp;

    /**
     * 转化跟踪ID
     * 值为： __CONVERT_ID__
     */
    private String convert_id;

    /**
     * 激活回调地址
     * 值为： __CALLBACK_URL__
     */
    private String callback_url;

    /**
     * 激活回调参数
     * 值为： __CALLBACK_PARAM__
     */
    private String callback;

    /**
     * 签名
     */
    private String sign;
}