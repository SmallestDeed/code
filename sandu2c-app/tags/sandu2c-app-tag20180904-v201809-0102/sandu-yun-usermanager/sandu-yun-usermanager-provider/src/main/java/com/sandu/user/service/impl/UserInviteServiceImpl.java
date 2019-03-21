package com.sandu.user.service.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 8:48 2018/5/29 0029
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.user.dao.UserInviteMapper;
import com.sandu.user.model.UserInvite;
import com.sandu.user.model.input.UserInviteAdd;
import com.sandu.user.model.view.UserInviteInfoVo;
import com.sandu.user.model.view.UserInviteTopListVo;
import com.sandu.user.service.UserInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author weisheng
 * @Title: 用户邀请服务
 * @Package
 * @Description:
 * @date 2018/5/29 0029PM 8:48
 */
@Service("userInviteService")
public class UserInviteServiceImpl implements UserInviteService {


    @Autowired
    private UserInviteMapper userInviteMapper;


    @Override
    public Integer saveUserInviteInfo(UserInviteAdd userInviteAdd) {

        //封装参数保存到数据库
        UserInvite userInvite = new UserInvite();
        userInvite.setInviteId(userInviteAdd.getInviteId());
        userInvite.setFid(userInviteAdd.getFid());
        userInvite.setRegisterTime(userInviteAdd.getRegisterTime());
        userInvite.setInviteTime(userInviteAdd.getInviteTime());
        userInvite.setShareSign(userInviteAdd.getShareSign());
        userInvite.setShareType(userInviteAdd.getShareType());//'1:微信朋友圈,2:微信好友,3:QQ好友,4:QQ空间,5:微博',
        userInvite.setStatus(1); //'邀请状态0:未注册,1:已注册'
        userInviteMapper.insertSelective(userInvite);
        return userInvite.getId();
    }

    @Override
    public List<UserInviteTopListVo> getAllInviteList(UserInviteAdd userInviteAdd) {
        return userInviteMapper.selectAllInviteList(userInviteAdd);
    }

    @Override
    public int getAllInviteCount() {
        return userInviteMapper.selectAllInviteCount();
    }


    @Override
    public UserInvite getUserInviteInfoByFid(int fid) {
        return userInviteMapper.selectUserInviteInfoByFid(fid);
    }

    @Override
    public List<UserInviteInfoVo> getUserInviteInfoByInviteId(UserInviteAdd userInviteAdd) {
        return userInviteMapper.selectUserInviteInfoByInviteId(userInviteAdd);
    }


    @Override
    public int getUserInviteInfoCountByInviteId(UserInviteAdd userInviteAdd) {
        return userInviteMapper.selectUserInviteInfoCountByInviteId(userInviteAdd);
    }

    @Override
    public int saveWXUserInviteInfo(String openId, List<String> strList) {
        //封装参数保存到数据库
        UserInvite userInvite = new UserInvite();
        userInvite.setInviteId(Integer.parseInt(strList.get(0)));
        userInvite.setFid(0);
        userInvite.setRegisterTime(new Date());
        userInvite.setInviteTime(new Date(Long.parseLong(strList.get(2))));
        userInvite.setShareSign("WXMP");
        userInvite.setShareType(Integer.parseInt(strList.get(1)));//'1:微信朋友圈,2:微信好友,3:QQ好友,4:QQ空间,5:微博',
        userInvite.setStatus(1); //'邀请状态0:未注册,1:已注册'
        userInvite.setRemark(openId);
        userInviteMapper.insertSelective(userInvite);


        return userInvite.getId().intValue();
    }

    @Override
    public int delUserInviteByRemark(String openId) {
        return userInviteMapper.updateUserInviteByRemark(openId);
    }

}
