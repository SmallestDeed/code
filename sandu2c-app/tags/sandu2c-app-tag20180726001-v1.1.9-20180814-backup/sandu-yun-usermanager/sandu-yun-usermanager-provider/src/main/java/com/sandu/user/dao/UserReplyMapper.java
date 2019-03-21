package com.sandu.user.dao;


import com.sandu.user.model.UserReply;

public interface UserReplyMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(UserReply record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(UserReply record);

    /**
     *
     * @mbggenerated
     */
    UserReply selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserReply record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserReply record);
}