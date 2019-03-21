package com.sandu.service.act2.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sandu.api.act2.model.RedPacketConfig;

@Repository
public interface RedPacketConfigMapper {
    
    int insert(RedPacketConfig entity);
    
    RedPacketConfig selectById(String id);

    List<RedPacketConfig> selectList(RedPacketConfig entity);

    int updateById(RedPacketConfig entity);
}