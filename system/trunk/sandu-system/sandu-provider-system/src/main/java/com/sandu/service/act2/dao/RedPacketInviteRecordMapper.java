package com.sandu.service.act2.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sandu.api.act2.model.RedPacketInviteRecord;

@Repository
public interface RedPacketInviteRecordMapper {
    
    int insert(RedPacketInviteRecord entity);
    
    RedPacketInviteRecord selectById(String id);

    List<RedPacketInviteRecord> selectList(RedPacketInviteRecord entity);

    int updateById(RedPacketInviteRecord entity);
}