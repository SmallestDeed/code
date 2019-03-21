package com.sandu.service.base.impl;

import com.google.common.collect.Lists;
import com.sandu.api.base.model.BasePlatform;
import com.sandu.api.base.service.BasePlatformService;
import com.sandu.service.base.dao.BasePlatformMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kono on 2018/6/1 0001.
 */
@Slf4j
@Service("basePlatformService")
public class BasePlatformServiceImpl implements BasePlatformService {

    @Autowired
    private BasePlatformMapper basePlatformMapper;

    @Override
    public BasePlatform getBasePlatform(String platformCode) {
        return basePlatformMapper.selectPlatformInfoByPlatformCode(platformCode);
    }

    @Override
    public BasePlatform findOneByPlatformCode(String code) {
        String logPrefixFunction = "function:BasePlatformServiceImpl." + "findOneByPlatformCode -> ";

        // 参数验证 ->start
        if(StringUtils.isEmpty(code)) {
            log.error(logPrefixFunction + "StringUtils.isEmpty(code) = true");
            return null;
        }
        // 参数验证 ->end

        List<BasePlatform> basePlatformList = basePlatformMapper.findAllByPlatformCode(code);
        if(basePlatformList == null || basePlatformList.size() == 0) {
            return null;
        }else {
            return basePlatformList.get(0);
        }
    }
}
