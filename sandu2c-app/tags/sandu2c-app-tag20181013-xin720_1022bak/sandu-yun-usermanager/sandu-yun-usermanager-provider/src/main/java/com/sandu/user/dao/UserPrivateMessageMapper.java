package com.sandu.user.dao;



import com.sandu.user.model.UserPrivateMessage;
import com.sandu.user.model.input.UserPrivateMessageAdd;
import com.sandu.user.model.view.UserPrivateMessageVo;

import java.util.List;

public interface UserPrivateMessageMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(UserPrivateMessage record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(UserPrivateMessage record);

    /**
     *
     * @mbggenerated
     */
    UserPrivateMessage selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserPrivateMessage record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserPrivateMessage record);

    List<UserPrivateMessageVo> selectUserPrivateMessageList(UserPrivateMessageAdd userPrivateMessageAdd);

    List<UserPrivateMessageVo> selectUserPrivateMessageInfoList(UserPrivateMessageAdd userPrivateMessageAdd);

    Integer delUserPrivateMessage(UserPrivateMessageAdd userPrivateMessageAdd);
}