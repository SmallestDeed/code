package com.sandu.service.company.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.company.model.BaseCompanyMiniProgramTemplateMsg;
import com.sandu.api.company.service.BaseCompanyMiniProgramTemplateMsgService;
import com.sandu.service.company.dao.BaseCompanyMiniProgramTemplateMsgMapper;


@Service("baseCompanyMiniProgramTemplateMsgService")
public class BaseCompanyMiniProgramTempateMsgServiceImpl implements BaseCompanyMiniProgramTemplateMsgService {

    @Autowired
    private BaseCompanyMiniProgramTemplateMsgMapper miniProgramTemplateMsgMapper;
            

    @Override
	public BaseCompanyMiniProgramTemplateMsg getMiniProgramTempateMsg(String appId, Integer templateType) {
		// TODO Auto-generated method stub
		return miniProgramTemplateMsgMapper.selectByAppidAndType(appId, templateType);
	}
	
}
