package com.sandu.api.banner.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.banner.input.ActShareImgAdminAdd;
import com.sandu.api.banner.model.ActShareImgAdmin;
import com.sandu.commons.LoginUser;

import java.util.List;

public interface ActShareImgAdminService {
    int addActShareImgAdmin(ActShareImgAdminAdd actShareImgAdminAdd, LoginUser loginUser);

    int updateActShareImgAdmin(ActShareImgAdmin actShareImgAdmin, LoginUser loginUser);

    int deltedActShareImg(Long id, LoginUser loginUser);

    PageInfo<ActShareImgAdmin> getList(Integer page, Integer limit);

    List<ActShareImgAdmin> getSXWActShareImg(Long bannerId);
}
