package com.sandu.api.springFestivalActivity.service;

import com.sandu.api.springFestivalActivity.model.WxUserCard;

import java.util.List;

public interface WxUserCardService {
    List<WxUserCard> selectSelective(WxUserCard record, Integer start, Integer limit);

    Integer insertSelective(WxUserCard record);

    WxUserCard selectByPrimaryKey(Long id);

    Integer updateByPrimaryKeySelective(WxUserCard record);

    List<WxUserCard> getTodayCardList(Long activityId);
}
