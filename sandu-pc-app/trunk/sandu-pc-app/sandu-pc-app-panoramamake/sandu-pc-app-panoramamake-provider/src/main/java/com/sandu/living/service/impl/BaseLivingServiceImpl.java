package com.sandu.living.service.impl;

import com.sandu.living.dao.BaseLivingMapper;
import com.sandu.living.model.input.BaseLivingSearch;
import com.sandu.living.model.output.BaseLivingVo;
import com.sandu.living.service.BaseLivingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("baseLivingService")
@Transactional
@Slf4j
public class BaseLivingServiceImpl implements BaseLivingService {

    @Autowired
    private BaseLivingMapper baseLivingMapper;

    /**
     * 获取小区数量
     * @param baseLivingSearch
     * @return
     */
    @Override
    public int count(BaseLivingSearch baseLivingSearch) {
        return baseLivingMapper.count(baseLivingSearch);
    }

    /**
     * 获取小区列表
     * @param baseLivingSearch
     * @return
     */
    @Override
    public List<BaseLivingVo> list(BaseLivingSearch baseLivingSearch) {
        return baseLivingMapper.list(baseLivingSearch);
    }
}
