package com.sandu.user.dao;


import com.sandu.user.model.MediationBankInfo;
import com.sandu.user.model.view.MediationBankInfoListVo;

import java.util.List;

public interface MediationBankInfoMapper {

    List<MediationBankInfo> selectMediationBankInfoByBankNumber(String bankNumber);

    List<MediationBankInfo> selectMediationBankInfoByUserId(Integer userId);

    void insertMediationBankInfo(MediationBankInfo mediationBankInfo);

    MediationBankInfo selectMediationBankInfoById(Integer id);

    void updateMediationBankInfoById(MediationBankInfo mediationBankInfo);

    List<MediationBankInfoListVo> selectMediationBankInfoListByUserId(Integer userId);

    List<MediationBankInfo> selectMediationBankHistoryByUserId(Integer userId);
}