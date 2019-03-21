package com.sandu.base.service.impl;

import com.sandu.banner.input.BaseBannerListQuery;
import com.sandu.banner.model.BaseBannerAd;
import com.sandu.base.dao.BaseBannerAdMapper;
import com.sandu.system.service.BaseBannerAdService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基础-广告Service实现
 * @author WangHaiLin
 * @date 2018/5/11  9:44
 */
@Data
@Service("baseBannerAdService")
public class BaseBannerAdServiceImpl implements BaseBannerAdService {

    @Autowired
    private BaseBannerAdMapper baseBannerAdMapper;

    @Value("${app.server.url}")
    private String resUrl;


    @Override
    public List<BaseBannerAd> getBannerByPosition(BaseBannerListQuery query) {
        return baseBannerAdMapper.getBannerByPosition(query);
    }
}
