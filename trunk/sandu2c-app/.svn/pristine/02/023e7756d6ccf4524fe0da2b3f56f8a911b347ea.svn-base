package com.sandu.user.dao;


import com.sandu.user.MediationAuthorizeAdd;
import com.sandu.user.MediationAuthorizeVo;
import com.sandu.user.UserMediationAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 1:49 2018/8/21 0021
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
@Repository
@Transactional
public interface MediationAuthorizeMapper {


    UserMediationAuthorize selectMediationAuthorizeById(Integer userId);

    int insertMediationAuthorize(UserMediationAuthorize userMediationAuthorize);



    List<UserMediationAuthorize> selectMediationAuthorizeByUserId(Integer id);

    int updateMediationReAuthorize(UserMediationAuthorize userMediationAuthorize);
}
