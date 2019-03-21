package com.sandu.service.volume.room.dao;

import com.sandu.api.volume.room.model.DrawResLfFile;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawResLfFileMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DrawResLfFile record);

    DrawResLfFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrawResLfFile record);
}