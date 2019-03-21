package com.sandu.service.dictionary.impl.biz;

import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.api.dictionary.service.biz.DictionaryBizService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.commons.constant.BusinessTypeToUserTypeConstant;
import com.sandu.commons.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author chenqiang
 * @Description 数据字典 逻辑 业务层
 * @Date 2018/6/28 0028 11:35
 * @Modified By
 */
@Slf4j
@Service("dictionaryBizService")
public class DictionaryBizServiceImpl implements DictionaryBizService{

    @Resource
    private SysUserService sysUserService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    /**
     * 通过类型和values获取名称
     * @auth chenqiang
     * @param userId 用户id
     * @param userType 用户类型
     * @return DictionaryTypeListVO 列表
     */
    public boolean checkUserType(String userId,Integer userType){

        if(StringUtils.isEmpty(userId)||null==userType)
            return false;

        //2.查询用户企业信息
        SysUser sysUser = sysUserService.get(Integer.valueOf(userId));
        Long companyId = sysUser.getBusinessAdministrationId();
        if(null == companyId){
            log.error("该用户没有所属企业");
            return false;
        }

        //3.查询用户所属企业对应用户类型列表
        BaseCompany baseCompany = baseCompanyService.selectByPrimaryKey(companyId);
        String userTypes = BusinessTypeToUserTypeConstant.getValueByKey(baseCompany.getBusinessType().toString());
        List<Integer> userTypeList = StringUtil.getListByString(userTypes);
        if(null != userTypeList && userTypeList.size() > 0){
            for (Integer integer : userTypeList) {
                //校验是否是正确的用户类型
                if(userType.equals(integer)){
                    return true;
                }
            }
        }else{
            log.error("该用户所属企业没有对应用户类型");
            return false;
        }

        log.error("该用户类型不在所属企业对应用户类型中");
        return false;
    }

}
