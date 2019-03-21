package com.sandu.service.bake.dao;

import com.sandu.api.house.model.DrawBakeRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawBakeRecordMapper {

    Integer insertSelective(DrawBakeRecord record);
}
