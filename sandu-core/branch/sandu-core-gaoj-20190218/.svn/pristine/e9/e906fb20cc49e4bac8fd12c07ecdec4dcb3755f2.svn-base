package com.sandu.service.springFestivalActivity.dao;

import com.sandu.api.springFestivalActivity.model.WxUserInviteRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxUserInviteRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxUserInviteRecord record);

    int insertSelective(WxUserInviteRecord record);

    WxUserInviteRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxUserInviteRecord record);

    int updateByPrimaryKey(WxUserInviteRecord record);

    List<WxUserInviteRecord> selectSelective(@Param("record") WxUserInviteRecord record,
                                             @Param("start") Integer start,
                                             @Param("limit") Integer limit);

    Integer selectSelectiveCount(WxUserInviteRecord record);
}