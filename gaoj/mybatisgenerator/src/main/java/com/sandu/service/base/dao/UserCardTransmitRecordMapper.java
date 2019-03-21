package com.sandu.service.base.dao;

import com.sandu.api.base.model.UserCardTransmitRecord;
import com.sandu.api.base.model.UserCardTransmitRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserCardTransmitRecordMapper {
    int countByExample(UserCardTransmitRecordExample example);

    int deleteByExample(UserCardTransmitRecordExample example);

    int insert(UserCardTransmitRecord record);

    int insertSelective(UserCardTransmitRecord record);

    List<UserCardTransmitRecord> selectByExample(UserCardTransmitRecordExample example);

    int updateByExampleSelective(@Param("record") UserCardTransmitRecord record, @Param("example") UserCardTransmitRecordExample example);

    int updateByExample(@Param("record") UserCardTransmitRecord record, @Param("example") UserCardTransmitRecordExample example);
}