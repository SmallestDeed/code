package com.sandu.user.service.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 2:00 2018/8/21 0021
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */


import com.nork.common.model.LoginUser;
import com.sandu.user.MediationAuthorizeAdd;
import com.sandu.user.UserMediationAuthorize;
import com.sandu.user.dao.MediationAuthorizeMapper;
import com.sandu.user.dao.MediationBankInfoMapper;
import com.sandu.user.model.MediationBankInfo;
import com.sandu.user.model.MediationReAuthorizeAdd;
import com.sandu.user.model.input.MediationBankInfoAdd;
import com.sandu.user.model.view.MediationBankInfoListVo;
import com.sandu.user.service.MediationAuthorizeService;
import com.sandu.user.service.MediationBankInfoService;
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
@Service("mediationBankInfoService")
public class MediationBankInfoServiceImpl implements MediationBankInfoService{

    @Autowired
    private MediationBankInfoMapper mediationBankInfoMapper;

    @Override
    public MediationBankInfo getMediationBankInfoByBankNumber(String bankNumber) {
        List<MediationBankInfo> mediationBankInfoList = mediationBankInfoMapper.selectMediationBankInfoByBankNumber(bankNumber);
        if(mediationBankInfoList == null || mediationBankInfoList.size() == 0){
            return null;
        }
        return mediationBankInfoList.get(0);
    }

    @Override
    public MediationBankInfo getMediationBankInfoByUserId(Integer userId) {
        List<MediationBankInfo> mediationBankInfoList = mediationBankInfoMapper.selectMediationBankInfoByUserId(userId);
        if(mediationBankInfoList == null || mediationBankInfoList.size() == 0){
            return null;
        }
        return mediationBankInfoList.get(0);
    }

    @Override
    public int addMediationBankInfo(MediationBankInfoAdd mediationBankInfoAdd,LoginUser loginUser) {
        MediationBankInfo mediationBankInfo = this.builder(mediationBankInfoAdd,loginUser);

        mediationBankInfoMapper.insertMediationBankInfo(mediationBankInfo);

        return mediationBankInfo.getId().intValue();
    }

    @Override
    public int reAddMediationBankInfo(MediationBankInfoAdd mediationBankInfoAdd, LoginUser loginUser, MediationBankInfo mediationBankInfoByUserId) {
        MediationBankInfo mediationBankInfo = this.builder(mediationBankInfoAdd,loginUser,mediationBankInfoByUserId);

        mediationBankInfoMapper.insertMediationBankInfo(mediationBankInfo);

        return mediationBankInfo.getId().intValue();
    }

    @Override
    public MediationBankInfo getMediationBankInfoById(Integer id) {
        return mediationBankInfoMapper.selectMediationBankInfoById(id);
    }

    @Override
    public int updateMediationBankInfoById(Integer id,LoginUser loginUser ) {
        MediationBankInfo mediationBankInfo = new MediationBankInfo();
        mediationBankInfo.setId(id);
        mediationBankInfo.setIsUnbind(1);
        mediationBankInfo.setGmtModified(new Date());
        mediationBankInfo.setModifier(loginUser.getName());
        mediationBankInfoMapper.updateMediationBankInfoById(mediationBankInfo);

        return mediationBankInfo.getId().intValue();

    }

    @Override
    public List<MediationBankInfoListVo> getMediationBankInfoListByUserId(Integer userId) {
        return mediationBankInfoMapper.selectMediationBankInfoListByUserId(userId);
    }

    @Override
    public MediationBankInfo getMediationBankHistoryByUserId(Integer userId) {
        List<MediationBankInfo> mediationBankInfoList = mediationBankInfoMapper.selectMediationBankHistoryByUserId(userId);
        if(mediationBankInfoList == null || mediationBankInfoList.size() == 0){
            return null;
        }
        return mediationBankInfoList.get(0);

    }


    private MediationBankInfo builder(MediationBankInfoAdd mediationBankInfoAdd, LoginUser loginUser, MediationBankInfo mediationBankInfoByUserId) {

        MediationBankInfo mediationBankInfo = new MediationBankInfo();
        Date date = new Date();
        mediationBankInfo.setBankName(mediationBankInfoAdd.getBankName());
        mediationBankInfo.setBankNameInfo(mediationBankInfoAdd.getBankNameInfo());
        mediationBankInfo.setBankNumber(mediationBankInfoAdd.getBankNumber());
        mediationBankInfo.setCardName(mediationBankInfoByUserId.getCardName());
        mediationBankInfo.setCardNumber(mediationBankInfoByUserId.getCardNumber());
        mediationBankInfo.setCreator(loginUser.getName());
        mediationBankInfo.setGmtCreate(date);
        mediationBankInfo.setModifier(loginUser.getName());
        mediationBankInfo.setGmtModified(date);
        mediationBankInfo.setPreMobile(mediationBankInfoAdd.getPreMobile());
        mediationBankInfo.setUserId(loginUser.getId());
        mediationBankInfo.setIsUnbind(0);
        mediationBankInfo.setIsDeleted(0);

        return mediationBankInfo;

    }

    private MediationBankInfo builder(MediationBankInfoAdd mediationBankInfoAdd,LoginUser loginUser) {
        MediationBankInfo mediationBankInfo = new MediationBankInfo();
        Date date = new Date();
        mediationBankInfo.setBankName(mediationBankInfoAdd.getBankName());
        mediationBankInfo.setBankNameInfo(mediationBankInfoAdd.getBankNameInfo());
        mediationBankInfo.setBankNumber(mediationBankInfoAdd.getBankNumber());
        mediationBankInfo.setCardName(mediationBankInfoAdd.getCardName());
        mediationBankInfo.setCardNumber(mediationBankInfoAdd.getCardNumber());
        mediationBankInfo.setCreator(loginUser.getName());
        mediationBankInfo.setGmtCreate(date);
        mediationBankInfo.setModifier(loginUser.getName());
        mediationBankInfo.setGmtModified(date);
        mediationBankInfo.setPreMobile(mediationBankInfoAdd.getPreMobile());
        mediationBankInfo.setUserId(loginUser.getId());
        mediationBankInfo.setIsUnbind(0);
        mediationBankInfo.setIsDeleted(0);

        return mediationBankInfo;
    }
}
