package com.sandu.api.company.service;


import org.springframework.stereotype.Component;

import com.sandu.api.company.model.BaseCompanyMiniProgramTemplateMsg;

/**
 * 微信小程序模板消息
 */
@Component
public interface BaseCompanyMiniProgramTemplateMsgService {

    /**
     * 获取小程序配置的模板消息
     * @param appId
     * @param templateType
     * @return
     */
	BaseCompanyMiniProgramTemplateMsg getMiniProgramTempateMsg(String appId,Integer templateType);

}
