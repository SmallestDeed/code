package com.sandu.service.system.impl;

import com.sandu.api.system.service.BaseAreaService;
import com.sandu.service.system.dao.BaseAreaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("baseAreaService")
public class BaseAreaServiceImpl implements BaseAreaService {

    @Autowired
    private BaseAreaDao baseAreaDao;

    @Override
    public String getAreaName(Integer areaId) {
        return baseAreaDao.selectAreaName(areaId);
    }

    @Override
    public String selectByAreaCode(String provinceCode) {
        return baseAreaDao.selectByAreaCode(provinceCode);
    }
}
