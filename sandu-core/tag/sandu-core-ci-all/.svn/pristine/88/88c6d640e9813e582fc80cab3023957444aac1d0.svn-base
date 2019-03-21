package com.sandu.api.springFestivalActivity.output;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: UserSignInRecordVo
 * @Auther: gaoj
 * @Date: 2019/1/19 15:59
 * @Description:
 * @Version 1.0
 */
@Data
public class UserSignInRecordVo implements Serializable {

    /****************************** 和WxUserSignin相同的字段 ****************************/
    /** 活动ID(关联wx_spring_activity) **/
    private Long activityId;
    /** 用户ID **/
    private Long userId;
    /** 签到日期(年-月-日) **/
    private Date signinDate;
    /** 签到日期(精确到天) **/
    private Integer signinDay;
    /** 领取的红包金额 **/
    private BigDecimal redPacketNum;
    /** 红包领取状态(0-待领取;1-已领取) **/
    private Byte receiveStatus;
    /** 红包领取时间 **/
    private Date receiveTime;
    /** 是否具有抽奖机会(0-否;1-是) **/
    private Byte haveLotteryChance;
    /** 有抽奖机会的时候，是否已经兑换抽奖机会(0-否;1-是) **/
    private Byte isLotteryFlag;

    /********************************** 拓展的字段 ******************************/

    /** 对应日期是否签到(0-否;1-是) **/
    private byte isSignIn;
}
