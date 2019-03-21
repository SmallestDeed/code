package com.sandu.api.base.service;


import com.sandu.api.base.input.BasePlatformUpdate;
import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.user.model.LoginUser;
import com.sandu.api.user.output.BasePlatformVO;
import com.sandu.api.user.output.UserPlatformDetailVO;

import java.util.List;
import java.util.Set;

public interface BasePlatformService {

    /**
     * 根据meidaType查找平台信息
     *
     * @param platformCode
     * @return
     */
    BasePlatform queryByPlatformCode(String platformCode);

    List<BasePlatform> queryList();

    List<BasePlatformVO> listPlatform(Integer userId);

    Integer updateUserPlatform(BasePlatformUpdate platform, LoginUser loginUser);

    UserPlatformDetailVO getUserPlatform(Integer userId);

    List<BasePlatform> queryByIds(Set<Long> platformIds);


    /**
     * 根据meidaType查找平台Id
     *
     * @param platformCode
     * @return
     */
    Integer getIdByPlatformCode(String platformCode);

}
