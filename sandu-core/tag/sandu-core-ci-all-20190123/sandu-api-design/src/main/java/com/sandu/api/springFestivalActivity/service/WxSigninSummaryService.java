package com.sandu.api.springFestivalActivity.service;

import com.sandu.api.springFestivalActivity.model.WxSigninSummary;
import com.sandu.api.springFestivalActivity.output.LotteryWheelVo;
import com.sandu.api.user.model.SysUser;
import org.apache.ibatis.annotations.Param;

public interface WxSigninSummaryService {
    WxSigninSummary getBySelective(WxSigninSummary wxSigninSummary);

    int insert(WxSigninSummary wxSigninSummary);

    int update(WxSigninSummary wxSigninSummary);

    int decrOnceLotteryCount(Long activityId, Long userId);


    /**
     * 功能描述: 获取春节抽奖转盘需要的值
     *
     * @param activityId
     * @param sysUser
     * @return LotteryWheelVo
     * @throws
     * @author gaoj
     * @date 2019/1/24 19:19
     */
    LotteryWheelVo getLotteryWheelNeedData(Long activityId, SysUser sysUser);
}
