package com.sandu.service.fix.dao;

import com.sandu.api.fix.model.DrawFixCupboardRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawFixCupboardRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DrawFixCupboardRecord record);

    DrawFixCupboardRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrawFixCupboardRecord record);
}