package com.sandu.service.user.impl.manage;
import com.sandu.api.base.model.BaseCompany;
import com.sandu.api.base.service.BaseCompanyService;
import com.sandu.api.user.input.UserManageSearch;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.api.user.service.manage.UserManageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service("vendorUserManageService")
public class VendorUserManageServiceImpl extends BaseUserManageServiceImpl implements UserManageService {

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
    protected void setCompanyId(UserManageSearch search) {

    }

    @Override
    protected void setUserType(UserManageSearch search) {
        if(Objects.isNull(search.getUserType())){
            search.setUserType(2);
        }
    }
}
