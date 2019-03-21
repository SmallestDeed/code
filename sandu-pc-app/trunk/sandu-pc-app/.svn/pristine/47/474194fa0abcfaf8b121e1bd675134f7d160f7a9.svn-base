package com.sandu.system.service.impl;

import com.sandu.system.dao.BasePlatformMapper;
import com.sandu.system.model.BasePlatform;
import com.sandu.system.service.BasePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 4:28 2018/6/27 0027
 * @Modified By:
 */
@Service("basePlatformService")
public class BasePlatformServiceImpl implements BasePlatformService {

    @Autowired
    private BasePlatformMapper basePlatformMapper;

    @Override
    public BasePlatform getByPlatformCode(String platformCode) {
        return basePlatformMapper.getByPlatformCode(platformCode);
    }
}
