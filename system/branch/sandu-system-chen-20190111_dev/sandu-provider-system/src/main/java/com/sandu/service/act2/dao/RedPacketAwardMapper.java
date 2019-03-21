package com.sandu.service.act2.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sandu.api.act2.model.RedPacketAward;

@Repository
public interface RedPacketAwardMapper {
	
    int insert(RedPacketAward entity);
    
    RedPacketAward selectById(String id);

    List<RedPacketAward> selectList(RedPacketAward entity);

    int updateById(RedPacketAward entity);
    
}