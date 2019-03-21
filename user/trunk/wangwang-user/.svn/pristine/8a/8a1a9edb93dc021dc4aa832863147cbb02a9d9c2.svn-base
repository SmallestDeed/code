package com.sandu.service.user.impl.manage;

import com.sandu.api.user.input.UserAdd;
import com.sandu.api.user.input.UserManageSearch;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.manage.UserManageService;
import org.springframework.stereotype.Service;


@Service("overAllUserManageService")
public class OverAllUserManageServiceImpl extends BaseUserManageServiceImpl implements UserManageService {

    @Override
    protected String createNickName(Long companyId) {
        return null;
    }

    @Override
    protected void setBusinessAdministrationId(SysUser sysUser, Long franchiserId) {
        sysUser.setBusinessAdministrationId(sysUser.getCompanyId());
    }

    @Override
    protected void setCompanyId(UserManageSearch search) {

    }

    @Override
    protected void setUserType(UserManageSearch search) {

    }
}
