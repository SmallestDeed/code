package com.sandu.user.service;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 1:47 2018/8/21 0021
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */


import com.nork.common.model.LoginUser;
import com.sandu.user.model.MediationBankInfo;
import com.sandu.user.model.input.MediationBankInfoAdd;
import com.sandu.user.model.view.MediationBankInfoListVo;

import java.util.List;

/**
 * @author weisheng
 * @Title:
 * @Package
 * @Description:
 * @date 2018/8/21 0021PM 1:47
 */
public interface MediationBankInfoService {

    MediationBankInfo getMediationBankInfoByBankNumber(String bankNumber);

    MediationBankInfo getMediationBankInfoByUserId(Integer id);

    int addMediationBankInfo(MediationBankInfoAdd mediationBankInfoAdd,LoginUser loginUser);

    int reAddMediationBankInfo(MediationBankInfoAdd mediationBankInfoAdd, LoginUser loginUser, MediationBankInfo mediationBankInfoByUserId);

    MediationBankInfo getMediationBankInfoById(Integer id);

    int updateMediationBankInfoById(Integer id,LoginUser loginUser );

    List<MediationBankInfoListVo> getMediationBankInfoListByUserId(Integer id);

    MediationBankInfo getMediationBankHistoryByUserId(Integer id);
}
