package com.sandu.user.service;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 1:47 2018/8/21 0021
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */


import com.sandu.user.MediationAuthorizeAdd;
import com.sandu.user.MediationAuthorizeVo;
import com.sandu.user.UserMediationAuthorize;
import com.sandu.user.model.MediationReAuthorizeAdd;

/**
 * @author weisheng
 * @Title:
 * @Package
 * @Description:
 * @date 2018/8/21 0021PM 1:47
 */
public interface MediationAuthorizeService {


    UserMediationAuthorize getMediationAuthorizeById(Integer userId);

    int checkMediationAuthorize(MediationAuthorizeAdd mediationAuthorizeAdd, Integer userId, String userName);

    UserMediationAuthorize getMediationAuthorizeByUserId(Integer id);

    int reCheckMediationAuthorize(MediationReAuthorizeAdd mediationReAuthorizeAdd, Integer id, String name);

    String getUserSourceCompany(Integer id);
}
