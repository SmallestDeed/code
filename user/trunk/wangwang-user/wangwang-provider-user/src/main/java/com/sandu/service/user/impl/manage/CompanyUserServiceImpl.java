package com.sandu.service.user.impl.manage;

import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.user.input.UserManageSearch;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.api.user.service.manage.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("companyUserService")
public class CompanyUserServiceImpl extends BaseUserManageServiceImpl implements UserManageService {

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected String createNickName(Long companyId) {
        return super.creatVendorANDCompanyUserCode(companyId);
    }


    @Override
    protected void setBusinessAdministrationId(SysUser sysUser, Long franchiserId) {
        sysUser.setBusinessAdministrationId(sysUser.getCompanyId());
    }

    @Override
    protected void setUserType(UserManageSearch search) {

    }

    @Override
    protected void setCompanyId(UserManageSearch search) {

    }
}
