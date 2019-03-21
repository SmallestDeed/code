package com.sandu.user.service.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 8:48 2018/5/29 0029
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.common.util.Utils;
import com.sandu.user.dao.UserInviteMapper;
import com.sandu.user.model.*;
import com.sandu.user.model.input.MiniProgramShareRecordAdd;
import com.sandu.user.model.input.UserInviteAdd;
import com.sandu.user.model.search.UserInviteSearch;
import com.sandu.user.model.view.UserInviteInfoVo;
import com.sandu.user.model.view.UserInviteListVo;
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

    @Override
    public List<UserInviteListVo> getMyUserInviteList(Integer inviteId,UserInviteSearch userInviteSearch) {
        return userInviteMapper.selectMyUserInviteList(inviteId,userInviteSearch.getStatus(),userInviteSearch.getStart(),userInviteSearch.getLimit());
    }

    @Override
    public int getMyUserInviteCount(Integer inviteId,UserInviteSearch userInviteSearch) {
        return userInviteMapper.selectMyUserInviteCount(inviteId,userInviteSearch.getStatus());
    }


    @Override
    public int saveUserInviteTrackInfo(UserInviteAdd userInviteAdd,String userName) {
        Date date = new Date();
        UserInviteTrack userInviteTrack = new UserInviteTrack();
        userInviteTrack.setInviteId(userInviteAdd.getInviteId());
        userInviteTrack.setFid(userInviteAdd.getFid());
        userInviteTrack.setStatus(0);
        userInviteTrack.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        userInviteTrack.setCreator(userName);
        userInviteTrack.setGmtCreate(date);
        userInviteTrack.setModifier(userName);
        userInviteTrack.setGmtModified(date);
        userInviteTrack.setIsDeleted(0);
        userInviteMapper.insertUserInviteTrack(userInviteTrack);
        return userInviteTrack.getId().intValue();
    }

    @Override
    public int saveIntermediaryCommisionInfo(UserInviteAdd userInviteAdd,String userName,int userInviteTrackId) {
        Date date = new Date();
        IntermediaryCommision intermediaryCommision = new IntermediaryCommision();
        intermediaryCommision.setAgentId(userInviteAdd.getInviteId());
        intermediaryCommision.setOwnerId(userInviteAdd.getFid());
        intermediaryCommision.setTrackId(userInviteTrackId);
        intermediaryCommision.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
        intermediaryCommision.setCreator(userName);
        intermediaryCommision.setGmtCreate(date);
        intermediaryCommision.setModifier(userName);
        intermediaryCommision.setGmtModified(date);
        intermediaryCommision.setIsDeleted(0);
        userInviteMapper.insertIntermediaryCommision(intermediaryCommision);
        return intermediaryCommision.getId().intValue();
    }

    @Override
    public int saveMiniProgramShareRecord(MiniProgramShareRecordAdd miniProgramShareRecordAdd, SysUser sysUser) {
        MiniProgramShareRecord miniProgramShareRecord = new MiniProgramShareRecord();
        miniProgramShareRecord.setUserId(miniProgramShareRecordAdd.getUserId());
        miniProgramShareRecord.setShareSign(miniProgramShareRecordAdd.getShareSign());
        miniProgramShareRecord.setShareType(miniProgramShareRecordAdd.getShareType());
        miniProgramShareRecord.setShareTime(miniProgramShareRecordAdd.getShareTime());
        miniProgramShareRecord.setFid(sysUser.getId());
        miniProgramShareRecord.setIsDeleted(0);
        miniProgramShareRecord.setRegisterTime(new Date());
        miniProgramShareRecord.setStatus(1);

        userInviteMapper.insertMiniProgramShareRecord(miniProgramShareRecord);


        return miniProgramShareRecord.getId().intValue();
    }

    @Override
    public MiniProgramShareRecord getMiniProgramShareRecordByFid(Integer id) {
        List<MiniProgramShareRecord> miniProgramShareRecordList = userInviteMapper.selectMiniProgramShareRecordByFid(id);
        if(null == miniProgramShareRecordList || miniProgramShareRecordList.size() == 0){
            return null;
        }
        return miniProgramShareRecordList.get(0);
    }

}
