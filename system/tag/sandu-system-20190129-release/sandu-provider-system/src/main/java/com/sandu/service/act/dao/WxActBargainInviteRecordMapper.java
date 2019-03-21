package com.sandu.service.act.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.act.input.WxActBargainInviteRecordQuery;
import com.sandu.api.act.model.WxActBargainInviteRecord;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * inviteRecord
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:36
 */
@Repository
public interface WxActBargainInviteRecordMapper {
	
    void insertWxActBargainInviteRecord(WxActBargainInviteRecord inviteRecord);

    int updateWxActBargainInviteRecordById(WxActBargainInviteRecord inviteRecord);

    int deleteWxActBargainInviteRecordById(@Param("inviteRecordId") String inviteRecordId);

    WxActBargainInviteRecord selectWxActBargainInviteRecordById(@Param("inviteRecordId") String inviteRecordId);

    List<WxActBargainInviteRecord> selectList(WxActBargainInviteRecordQuery queryEntity);
    
}
