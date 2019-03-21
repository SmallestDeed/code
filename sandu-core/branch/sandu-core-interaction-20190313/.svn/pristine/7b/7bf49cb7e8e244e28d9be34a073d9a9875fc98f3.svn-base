package com.sandu.service.springFestivalActivity.dao;

import com.sandu.api.springFestivalActivity.model.WxRedPacketSummary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface WxRedPacketSummaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxRedPacketSummary record);

    int insertSelective(WxRedPacketSummary record);

    WxRedPacketSummary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxRedPacketSummary record);

    int updateByPrimaryKey(WxRedPacketSummary record);

    WxRedPacketSummary getBySignInDate(@Param("activityId") Long activityId, @Param("signInDate") Date signInDate);

    int updateByRedPacketRemainNum(WxRedPacketSummary wxRedPacketSummary);
}