package com.sandu.api.callback;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-system
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 10/23/2018 8:19 PM
 */
public class AdConstant {

    /**
     * 广告来源： 今日头条
     */
    public static final String ORIGIN_TOUTIAO = "toutiao";


    /**
     * 客户端系统： Android
     */
    public static final String OS_ANDROID = "android";

    /**
     * 客户端系统： iOS
     */
    public static final String OS_IOS = "ios";


    /**
     * 头条客户端系统
     */
    public static final int TOUTIAO_OS_ANDROID = 0;

    public static final int TOUTIAO_OS_IOS = 1;

    public static final int TOUTIAO_OS_WP = 2;

    public static final int TOUTIAO_OS_OTHERS = 3;

    // 激活
    public static final int TOUTIAO_EVENT_ACTIVE = 0;
    //激活且注册
    public static final int TOUTIAO_EVENT_ACTIVE_REGISTER = 1;
    //激活且付费
    public static final int TOUTIAO_EVENT_ACTIVE_PAY = 2;

    public static final int RECORD_PROCESSED = 1;
    public static final int RECORD_UNPROCESSED = 0;

    //激活回传地址
//    public static final String TOUTIAO_TRACK_URL = "http://ad.toutiao.com/track/activate/?callback={callback_param}&muid={muid}&os={os}&source={source}&conv_time={conv_time}&event_type={event_type}&signature={signature}";
    public static final String TOUTIAO_TRACK_URL = "http://ad.toutiao.com/track/activate/";

}
