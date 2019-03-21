package com.sandu.service.company.impl;

import com.google.common.collect.Lists;
import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.model.PlatformTypeBO;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.common.constant.kechuang.PlatformType;
import com.sandu.service.company.dao.BaseCompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseCompanyServiceImpl implements BaseCompanyService {

    @Autowired
    private BaseCompanyMapper baseCompanyMapper;

    @Override
    public List<BaseCompany> listCompany() {
        return baseCompanyMapper.listCompany();
    }

    @Override
    public List<PlatformTypeBO> listPlatform() {
        List<PlatformTypeBO> platformTypeBOS = new ArrayList<>();

        List<PlatformType> platformTypes = Lists.newArrayList(PlatformType.values());
        for (PlatformType platform : platformTypes) {
            platformTypeBOS.add(new PlatformTypeBO(platform.getType(), platform.getName()));
        }

        return platformTypeBOS;
    }
}
