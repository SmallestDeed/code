package com.sandu.service.base.dao;

import com.sandu.api.base.model.InteractiveZoneMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractiveZoneMsgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InteractiveZoneMsg record);

    int insertSelective(InteractiveZoneMsg record);

    InteractiveZoneMsg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InteractiveZoneMsg record);

    int updateByPrimaryKeyWithBLOBs(InteractiveZoneMsg record);

    int updateByPrimaryKey(InteractiveZoneMsg record);

    List<InteractiveZoneMsg> getList(@Param("interactiveZoneMsg") InteractiveZoneMsg interactiveZoneMsg,
                                     @Param("start") Integer start,
                                     @Param("limit") Integer limit);

    Integer getListCount(InteractiveZoneMsg interactiveZoneMsg);
}