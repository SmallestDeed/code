package com.sandu.api.company.service;


import org.springframework.stereotype.Component;
import com.sandu.api.company.model.BaseCompanyMiniProgramConfig;

/**
 * 企业微信信息Service
 * @author WangHaiLin
 * @date 2018/6/22  14:17
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
