package com.sandu.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.base.model.BaseCompanyCustomLoginConfig;
import com.sandu.api.base.service.BaseCompanyCustomLoginConfigService;
import com.sandu.service.base.dao.BaseCompanyCustomLoginConfigDao;

/**
 * 定制企业配置信息Service实现类
 */
@Service("baseCompanyCustomLoginConfigService")
public class BaseCompanyCustomLoginConfigServiceImpl implements BaseCompanyCustomLoginConfigService {

    @Autowired
    private BaseCompanyCustomLoginConfigDao companyCustomLoginConfigDao;

     

	@Override
	public BaseCompanyCustomLoginConfig getCustomLoginConfig(Long companyId) {
		// TODO Auto-generated method stub
		return companyCustomLoginConfigDao.selectByCompanyId(companyId);
	}
}
