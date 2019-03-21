package com.sandu.service.act.dao;

import java.util.List;

import com.sandu.api.act.model.WxActBargainInviteRecordActDayAggregated;

public interface WxActBargainInviteRecordActDayAggregatedMapper {
  
    int insert(WxActBargainInviteRecordActDayAggregated record);
    
    WxActBargainInviteRecordActDayAggregated selectById(String id);

    int updateById(WxActBargainInviteRecordActDayAggregated record);

	List<WxActBargainInviteRecordActDayAggregated> selectList(WxActBargainInviteRecordActDayAggregated query);

}