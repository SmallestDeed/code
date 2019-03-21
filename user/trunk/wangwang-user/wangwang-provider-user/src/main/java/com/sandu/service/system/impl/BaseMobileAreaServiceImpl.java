package com.sandu.service.system.impl;

import com.sandu.api.system.model.BaseMobileArea;
import com.sandu.api.system.service.BaseMobileAreaService;
import com.sandu.service.system.dao.BaseMobileAreaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("baseMobileAreaService")
public class BaseMobileAreaServiceImpl implements BaseMobileAreaService{

    @Autowired
    private BaseMobileAreaDao baseMobileAreaDao;

    @Override
    public BaseMobileArea queryBaseMobileAreaByMobilePrefix(String mobilePrefix) {
        return baseMobileAreaDao.queryBaseMobileAreaByMobilePrefix(mobilePrefix);
    }
}
