package com.sandu.service.springFestivalActivity.dao;

import com.sandu.api.springFestivalActivity.model.WxUserCard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxUserCardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxUserCard record);

    int insertSelective(WxUserCard record);

    WxUserCard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxUserCard record);

    int updateByPrimaryKey(WxUserCard record);

    List<WxUserCard> selectSelective(@Param("record") WxUserCard record,
                                     @Param("start") Integer start,
                                     @Param("limit") Integer limit);

    List<WxUserCard> getTodayCardList(Long activityId);
}