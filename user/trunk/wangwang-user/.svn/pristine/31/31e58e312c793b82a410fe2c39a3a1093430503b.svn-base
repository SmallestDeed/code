package com.sandu.service.base.impl;

import com.sandu.api.base.model.BaseCompanyMiniProgramConfig;
import com.sandu.api.base.service.BaseCompanyMiniProgramConfigService;
import com.sandu.service.base.dao.BaseCompanyMiniProgramConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 企业微信信息Service实现类
 * @author WangHaiLin
 * @date 2018/6/22  14:21
 */
@Service("baseCompanyMiniProgramConfigService")
public class BaseCompanyMiniProgramConfigServiceImpl implements BaseCompanyMiniProgramConfigService {

    @Autowired
    private BaseCompanyMiniProgramConfigDao miniProgramConfigDao;

    @Override
    public BaseCompanyMiniProgramConfig getMiniProgramConfig(String appId) {
        if (null==appId) {
            return null;
        }
        return miniProgramConfigDao.selectByAppId(appId);
    }
}
