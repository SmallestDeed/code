package com.sandu.user.dao;


import com.sandu.user.model.UserInvite;
import com.sandu.user.model.input.UserInviteAdd;
import com.sandu.user.model.view.UserInviteInfoVo;
import com.sandu.user.model.view.UserInviteTopListVo;

import java.util.List;

public interface UserInviteMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(UserInvite record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(UserInvite record);

    /**
     *
     * @mbggenerated
     */
    UserInvite selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserInvite record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserInvite record);



    List<UserInviteTopListVo> selectAllInviteList(UserInviteAdd userInviteAdd);

    UserInvite selectUserInviteInfoByFid(int fid);

    List<UserInviteInfoVo> selectUserInviteInfoByInviteId(UserInviteAdd userInviteAdd);

    int selectAllInviteCount();

    int selectUserInviteInfoCountByInviteId(UserInviteAdd userInviteAdd);
}