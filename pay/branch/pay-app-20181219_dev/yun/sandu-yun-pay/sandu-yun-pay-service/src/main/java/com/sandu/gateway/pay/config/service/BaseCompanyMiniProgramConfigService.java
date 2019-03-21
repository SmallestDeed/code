package com.sandu.gateway.pay.config.service;


import org.springframework.stereotype.Component;
import com.sandu.gateway.pay.config.model.BaseCompanyMiniProgramConfig;

/**
 * 企业微信信息Service
 */
@Component
public interface BaseCompanyMiniProgramConfigService {

    /**
     * 通过企业微信appId查询 企业微信信息
     * @param appId 企业微信Id
     * @return BaseCompanyMiniProgramConfig
     */
    BaseCompanyMiniProgramConfig getMiniProgramConfig(String appId);

}
