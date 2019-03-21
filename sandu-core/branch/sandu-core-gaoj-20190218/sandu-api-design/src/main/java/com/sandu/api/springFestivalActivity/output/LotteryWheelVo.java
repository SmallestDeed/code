package com.sandu.api.springFestivalActivity.output;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: LotteryWheelVo
 * @Auther: gaoj
 * @Date: 2019/1/24 16:56
 * @Description:
 * @Version 1.0
 */
@Data
public class LotteryWheelVo implements Serializable {

    /** 已签到次数 **/
    private Integer signInCount;
    /** 剩余抽奖次数 **/
    private Integer lotteryRemainCount;
    /** 中奖概率 **/
    private Integer lotteryRate;

}
