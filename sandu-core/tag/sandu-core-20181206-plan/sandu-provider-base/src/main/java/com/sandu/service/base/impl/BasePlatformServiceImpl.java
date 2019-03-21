package com.sandu.service.base.impl;

import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.base.service.BasePlatformService;
import com.sandu.service.base.dao.BasePlatformMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kono on 2018/6/1 0001.
 */
@Service("basePlatformService")
public class BasePlatformServiceImpl implements BasePlatformService {

    @Autowired
    private BasePlatformMapper basePlatformMapper;

    @Override
    public BasePlatform getBasePlatform(String platformCode) {
        return basePlatformMapper.selectPlatformInfoByPlatformCode(platformCode);
    }
}
