package com.sandu.service.area.impl;

import com.sandu.api.area.model.BaseMobileArea;
import com.sandu.api.area.service.BaseMobileAreaService;
import com.sandu.service.area.dao.BaseMobileAreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WangHaiLin
 * @date 2018/7/19  11:09
 */
@Service("baseMobileAreaService")
public class BaseMobileAreaServiceImpl implements BaseMobileAreaService {

    @Autowired
    private BaseMobileAreaMapper baseMobileAreaMapper;

    @Override
    public BaseMobileArea getAreaByMobile(String mobilePrefix) {
        return  baseMobileAreaMapper.getAreaByMobile(mobilePrefix);
    }
}
