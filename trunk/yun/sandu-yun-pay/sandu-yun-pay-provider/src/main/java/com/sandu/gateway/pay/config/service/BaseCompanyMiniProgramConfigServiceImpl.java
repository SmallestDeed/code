package com.sandu.gateway.pay.config.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.gateway.pay.config.dao.BaseCompanyMiniProgramConfigMapper;
import com.sandu.gateway.pay.config.model.BaseCompanyMiniProgramConfig;


/**
 * 企业微信信息Service实现类
 * @date 2018/6/22  14:21
 */
@Service("baseCompanyMiniProgramConfigService")
public class BaseCompanyMiniProgramConfigServiceImpl implements BaseCompanyMiniProgramConfigService {

    @Autowired
    private BaseCompanyMiniProgramConfigMapper miniProgramConfigDao;

    @Override
    public BaseCompanyMiniProgramConfig getMiniProgramConfig(String appId) {
        if (StringUtils.isBlank(appId)) {
            return null;
        }
        return miniProgramConfigDao.selectByAppId(appId);
    }
}
