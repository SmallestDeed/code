package com.sandu.api.user.service.manage;

import com.github.pagehelper.PageInfo;
import com.sandu.api.user.input.UserAdd;
import com.sandu.api.user.input.UserEdit;
import com.sandu.api.user.input.UserManageSearch;
import com.sandu.api.user.model.UserManageDTO;

public interface UserManageService {

    PageInfo<UserManageDTO> getUserList(UserManageSearch search);

    boolean addUser(UserAdd userAdd,Long userId);

    void editUser(UserEdit userEdit, Long userId);
}
