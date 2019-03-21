package com.sandu.user.service;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 8:47 2018/5/29 0029
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.user.model.UserInvite;
import com.sandu.user.model.input.UserInviteAdd;
import com.sandu.user.model.view.UserInviteInfoVo;
import com.sandu.user.model.view.UserInviteTopListVo;

import java.util.List;

/**
 * @Title: 用户邀请服务
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/5/29 0029PM 8:47
 */
public interface UserInviteService {
    Integer saveUserInviteInfo(UserInviteAdd userInviteAdd);


    List<UserInviteTopListVo> getAllInviteList(UserInviteAdd userInviteAdd);

    UserInvite getUserInviteInfoByFid(int i);

    List<UserInviteInfoVo> getUserInviteInfoByInviteId(UserInviteAdd userInviteAdd);

    int getAllInviteCount();

    int getUserInviteInfoCountByInviteId(UserInviteAdd userInviteAdd);

    int saveWXUserInviteInfo(String openId, List<String> strList);

    int delUserInviteByRemark(String openId);
}
