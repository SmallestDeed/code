package com.sandu.base.service.impl;

import com.sandu.banner.input.BaseBannerPositionQuery;
import com.sandu.banner.model.BaseBannerPosition;
import com.sandu.base.dao.BaseBannerPositionMapper;
import com.sandu.system.service.BaseBannerPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 广告-位置 服务实现类
 * @author WangHaiLin
 * @date 2018/5/14  11:53
 */
@Service("baseBannerPositionService")
public class BaseBannerPositionServiceImpl  implements BaseBannerPositionService{

    @Autowired
    private BaseBannerPositionMapper positionMapper;

    /**
     * 查询广告位置
     * @param query 广告位置查询入参实体
     * @return BaseBannerPosition 广告位置实体
     */
    @Override
    public BaseBannerPosition getPosition(BaseBannerPositionQuery query) {
        return positionMapper.getPositionByTypeAndCode(query);
    }
}
