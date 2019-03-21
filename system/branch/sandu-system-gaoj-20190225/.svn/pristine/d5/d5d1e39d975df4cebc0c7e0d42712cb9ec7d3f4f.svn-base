package com.sandu.service.act2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.act2.model.RedPacket;
import com.sandu.api.act2.model.search.RedPacketSearch;

@Repository
public interface RedPacketMapper {
    
    int insert(RedPacket entity);

    RedPacket selectById(String id);
    
    List<RedPacket> selectList(RedPacketSearch redPacketSearch);

    int updateById(RedPacket entity);

	int updateToReduceTotalAmount(@Param("actId") String actId, @Param("packetAmount") Double packetAmount);

	void updateToIncreaseRegCount(@Param("actId") String actId);
}