package com.sandu.service.springFestivalActivity.dao;

import com.sandu.api.springFestivalActivity.model.WxSigninSummary;
import com.sandu.api.springFestivalActivity.output.LotteryWheelVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WxSigninSummaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxSigninSummary record);

    int insertSelective(WxSigninSummary record);

    WxSigninSummary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxSigninSummary record);

    int updateByPrimaryKey(WxSigninSummary record);

    WxSigninSummary getBySelective(WxSigninSummary wxSigninSummary);

    int decrOnceLotteryCount(@Param("activityId") Long activityId, @Param("userId") Long userId);

    LotteryWheelVo getLotteryWheelNeedData(@Param("activityId") Long activityId, @Param("userId") Long userId);
}