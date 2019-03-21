package com.sandu.api.springFestivalActivity.service;

import com.sandu.api.springFestivalActivity.model.WxSpringActivity;
import com.sandu.api.springFestivalActivity.output.DateVo;

public interface WxSpringActivityService {
    /**
     * 功能描述:
     *
     * @param id
     * @return com.sandu.api.springFestivalActivity.model.WxSpringActivity
     * @throws
     * @author gaoj
     * @date 2019/1/19
     */
    WxSpringActivity selectByPrimaryKey(Long id);

    /**
     * 更新活动数据
     *
     * @param record 更新数据
     * @return
     * @author zcd
     */
    int updateByPrimaryKeySelective(WxSpringActivity record);

    /**
     * 功能描述: 根据活动id获取活动开始和截止时间
     *
     * @param activityId
     * @return com.sandu.api.springFestivalActivity.output.DateVo
     * @throws
     * @author gaoj
     * @date 2019/1/19 18:57
     */
    DateVo getSignInDate(long activityId);

    /**
     * 功能描述: 自减签到红包总剩余金额（大多时候不适用，看sql之后再决定使不使用）
     *
     * @param wxSpringActivity
     * @return int
     * @author gaoj
     * @date 2019/1/22 17:13
     */
    int decrRedPacketRemainNum(WxSpringActivity wxSpringActivity);
}
