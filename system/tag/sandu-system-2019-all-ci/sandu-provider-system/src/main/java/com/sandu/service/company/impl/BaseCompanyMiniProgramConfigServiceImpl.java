package com.sandu.service.company.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.company.model.BaseCompanyMiniProgramConfig;
import com.sandu.api.company.service.BaseCompanyMiniProgramConfigService;
import com.sandu.service.company.dao.BaseCompanyMiniProgramConfigMapper;

/**
 * 企业微信信息Service实现类
 * @author WangHaiLin
 * @date 2018/6/22  14:21
 */
@Service("baseCompanyMiniProgramConfigService")
public class BaseCompanyMiniProgramConfigServiceImpl implements BaseCompanyMiniProgramConfigService {

    @Autowired
    private BaseCompanyMiniProgramConfigMapper miniProgramConfigDao;

    @Override
    public BaseCompanyMiniProgramConfig getMiniProgramConfig(String appId) {
        if (null==appId) {
            return null;
        }
        return miniProgramConfigDao.selectByAppId(appId);
    }
}
