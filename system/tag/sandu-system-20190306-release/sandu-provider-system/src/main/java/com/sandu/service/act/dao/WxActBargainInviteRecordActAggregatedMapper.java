package com.sandu.service.act.dao;

import java.util.List;

import com.sandu.api.act.model.WxActBargainInviteRecordActAggregated;

public interface WxActBargainInviteRecordActAggregatedMapper {

    int insert(WxActBargainInviteRecordActAggregated record);

    WxActBargainInviteRecordActAggregated selectById(String id);

    int updateById(WxActBargainInviteRecordActAggregated record);

	List<WxActBargainInviteRecordActAggregated> selectList(WxActBargainInviteRecordActAggregated query);
    
}