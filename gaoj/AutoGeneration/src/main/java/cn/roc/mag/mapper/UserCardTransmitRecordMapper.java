package cn.roc.mag.mapper;

import cn.roc.mag.entity.UserCardTransmitRecord;

public interface UserCardTransmitRecordMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(UserCardTransmitRecord record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(UserCardTransmitRecord record);

    /**
     *
     * @mbggenerated
     */
    UserCardTransmitRecord selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserCardTransmitRecord record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserCardTransmitRecord record);
}