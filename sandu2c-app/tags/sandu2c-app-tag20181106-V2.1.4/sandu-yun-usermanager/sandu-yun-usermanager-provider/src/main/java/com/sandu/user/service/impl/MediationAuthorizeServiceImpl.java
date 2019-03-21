package com.sandu.user.service.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 2:00 2018/8/21 0021
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */


import com.sandu.user.MediationAuthorizeAdd;
import com.sandu.user.MediationAuthorizeVo;
import com.sandu.user.UserMediationAuthorize;
import com.sandu.user.dao.MediationAuthorizeMapper;
import com.sandu.user.model.MediationReAuthorizeAdd;
import com.sandu.user.service.MediationAuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/8/21 0021PM 2:00
 */
@Service("mediationAuthorizeService")
public class MediationAuthorizeServiceImpl implements MediationAuthorizeService {

    @Autowired
    private MediationAuthorizeMapper mediationAuthorizeMapper;

    @Override
    public UserMediationAuthorize getMediationAuthorizeById(Integer id) {
        return mediationAuthorizeMapper.selectMediationAuthorizeById(id);
    }

    @Override
    public int checkMediationAuthorize(MediationAuthorizeAdd mediationAuthorizeAdd,Integer userId,String userName) {

            UserMediationAuthorize userMediationAuthorize = this.builder(mediationAuthorizeAdd,userId,userName);
             mediationAuthorizeMapper.insertMediationAuthorize(userMediationAuthorize);
            return userMediationAuthorize.getId().intValue();

    }

    @Override
    public UserMediationAuthorize getMediationAuthorizeByUserId(Integer id) {
        List<UserMediationAuthorize> userMediationAuthorizeList = mediationAuthorizeMapper.selectMediationAuthorizeByUserId(id);
        if(userMediationAuthorizeList == null || userMediationAuthorizeList.size() ==0 ){
            return null;
        }
        return userMediationAuthorizeList.get(0);
    }

    @Override
    public int reCheckMediationAuthorize(MediationReAuthorizeAdd mediationReAuthorizeAdd, Integer id, String name) {
        UserMediationAuthorize userMediationAuthorize = this.reCheckbuilder(mediationReAuthorizeAdd,id,name);
        mediationAuthorizeMapper.updateMediationReAuthorize(userMediationAuthorize);
        return userMediationAuthorize.getId().intValue();
    }

    private UserMediationAuthorize builder(MediationAuthorizeAdd mediationAuthorizeAdd,Integer userId,String userName) {
        UserMediationAuthorize userMediationAuthorize = new UserMediationAuthorize();
        Date date = new Date();
        userMediationAuthorize.setUserId(userId);
        userMediationAuthorize.setCardName(mediationAuthorizeAdd.getCardName());
        userMediationAuthorize.setCardNumber(mediationAuthorizeAdd.getCardNumber());
        userMediationAuthorize.setCardType(mediationAuthorizeAdd.getCardType());
        userMediationAuthorize.setCreateTime(date);
        userMediationAuthorize.setCardPicId(mediationAuthorizeAdd.getCardPicId());
        userMediationAuthorize.setGmtModified(date);
        userMediationAuthorize.setCreator(userName);
        userMediationAuthorize.setModifier(userName);
        userMediationAuthorize.setIsDeleted(0);
        return userMediationAuthorize;
    }


    private UserMediationAuthorize reCheckbuilder(MediationReAuthorizeAdd mediationReAuthorizeAdd,Integer userId,String userName) {
        UserMediationAuthorize userMediationAuthorize = new UserMediationAuthorize();
        Date date = new Date();
        userMediationAuthorize.setId(mediationReAuthorizeAdd.getAuthorizeId());
        userMediationAuthorize.setUserId(userId);
        userMediationAuthorize.setCardName(mediationReAuthorizeAdd.getCardName());
        userMediationAuthorize.setCardNumber(mediationReAuthorizeAdd.getCardNumber());
        userMediationAuthorize.setCardType(mediationReAuthorizeAdd.getCardType());
        userMediationAuthorize.setCardPicId(mediationReAuthorizeAdd.getCardPicId());
        userMediationAuthorize.setGmtModified(date);
        userMediationAuthorize.setModifier(userName);
        return userMediationAuthorize;
    }

    @Override
    public String getUserSourceCompany(Integer userId) {
        return mediationAuthorizeMapper.getUserSourceCompany(userId);
    }
}
