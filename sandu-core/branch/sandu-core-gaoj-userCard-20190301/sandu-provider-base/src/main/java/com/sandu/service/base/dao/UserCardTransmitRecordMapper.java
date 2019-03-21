package com.sandu.service.base.dao;

import com.sandu.api.base.model.UserCardTransmitRecord;

public interface UserCardTransmitRecordMapper {
    /**
     *
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     */
    int insert(UserCardTransmitRecord record);

    /**
     *
     */
    int insertSelective(UserCardTransmitRecord record);

    /**
     *
     */
    UserCardTransmitRecord selectByPrimaryKey(Integer id);

    /**
     *
     */
    int updateByPrimaryKeySelective(UserCardTransmitRecord record);

    /**
     *
     */
    int updateByPrimaryKey(UserCardTransmitRecord record);
}