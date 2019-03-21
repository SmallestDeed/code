package com.sandu.api.banner.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.banner.input.MiniProIndexDialogAdd;
import com.sandu.api.banner.input.MiniProIndexDialogUpdate;
import com.sandu.api.banner.model.MiniProIndexDialog;
import com.sandu.commons.LoginUser;

public interface MiniProIndexDialogService {
    int addSXWIndexDialog(MiniProIndexDialogAdd miniProIndexDialogAdd, LoginUser loginUser);

    PageInfo<MiniProIndexDialog> getDialogList(String dialogCode, Integer page, Integer limit);

    MiniProIndexDialog getEnableDialog(String dialogCode);

    int updateDialog(MiniProIndexDialogUpdate update,LoginUser loginUser);

    int changeBannerIsEnable(Integer dialogId, LoginUser loginUser);

    int deletedDialog(Integer dialogId, LoginUser loginUser);
}
