package com.sandu.pay.base.service.impl;

import com.sandu.pay.base.dao.BaseCompanyMapper;
import com.sandu.pay.base.model.BaseCompany;
import com.sandu.pay.base.service.BaseCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("baseCompanyService")
public class BaseCompanyServiceImpl implements BaseCompanyService {
    private static final Logger logger = LoggerFactory.getLogger(BaseCompanyServiceImpl.class);
    @Autowired
    private BaseCompanyMapper baseCompanyMapper;


    public BaseCompany get(Long id) {
        return this.baseCompanyMapper.selectByPrimaryKey(id);
    }

    @Override
    public BaseCompany getByIdAndAppId(Long companyId, String appId) {
        return baseCompanyMapper.selectByIdAndAppId(companyId,appId);
    }
}