package com.sandu.home.service.impl;

import com.sandu.home.model.SpaceCommon;
import com.sandu.home.service.SpaceCommonCacheService;
import com.sandu.home.service.SpaceCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * 通用空间缓存层
 *
 * @author qiu.jun
 * @date 2016-05-12
 */
@Service("spaceCommonCacheService")
public class SpaceCommonCacheServiceImpl implements SpaceCommonCacheService {

    @Autowired
    private SpaceCommonService spaceCommonService;

    /***
     * 获取单个通用空间
     *
     * @param id
     * @return
     */
    @Override
    public SpaceCommon get(int id) {
        return spaceCommonService.get(id);
    }

    /***
     * 获取单个通用空间
     *
     * @param id
     * @return
     */
    @Override
    public SpaceCommon get(Integer id) {
        return spaceCommonService.get(id);
    }
}
