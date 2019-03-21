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


@Service("franchiserUserManageService")
public class FranchiserUserManageServiceImpl extends BaseUserManageServiceImpl implements UserManageService {

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected String createNickName(Long companyId) {
        StringBuffer commanyCodePrefix = new StringBuffer();//前缀
        StringBuffer commanyCodeSuffix = new StringBuffer();//后缀

        BaseCompany baseCompany = baseCompanyService.queryById(companyId);

        commanyCodePrefix.append(baseCompany.getCompanyCode()+"F0000");
        commanyCodePrefix.append("A");

        String commanyCodePrefixS = commanyCodePrefix.toString()+"%";
        String companyCodeMax = this.createFranchiserAccountCode(commanyCodePrefixS, StringUtils.isNotEmpty(companyId + "")?Integer.parseInt(companyId + ""):0);  /**获取当前前缀的最大序列号**/
        Integer commanyCode = 0;
        if (StringUtils.isNotEmpty(companyCodeMax)) {
            commanyCode = Integer.parseInt(companyCodeMax.substring(commanyCodePrefixS.length()-1,companyCodeMax.length()));
            commanyCode ++;
            commanyCodeSuffix.append(commanyCode);
        } else {
            commanyCodeSuffix.append("1");
        }

        int k = commanyCodeSuffix.length();
        for (int i = 0 ; i < 5 - k ; i++) {
            commanyCodeSuffix.insert(0,"0");
        }

        return commanyCodePrefix+""+commanyCodeSuffix;
    }

    public String createFranchiserAccountCode(String commanyCodePrefix,Integer businessAdministrationId){
        return sysUserService.getMaxFranchiserAccountCode(commanyCodePrefix,Long.parseLong(businessAdministrationId + ""));
    }

    @Override
    protected void setBusinessAdministrationId(SysUser sysUser, Long franchiserId) {
        sysUser.setBusinessAdministrationId(franchiserId);
    }

    @Override
    protected void setCompanyId(UserManageSearch search) {
        search.setCompanyId(Objects.nonNull(search.getFranchiserId()) ? search.getFranchiserId().intValue() : search.getCompanyId());
    }

    @Override
    protected void setUserType(UserManageSearch search) {
        if(Objects.isNull(search.getUserType())){
            search.setUserType(3);
        }
    }
}
