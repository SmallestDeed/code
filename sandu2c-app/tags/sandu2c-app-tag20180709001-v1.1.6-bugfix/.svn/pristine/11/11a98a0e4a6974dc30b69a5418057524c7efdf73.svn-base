package com.sandu.base.service.impl;

import com.sandu.banner.model.ResBannerPic;
import com.sandu.base.dao.ResBannerPicMapper;
import com.sandu.system.service.ResBannerPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 广告-图片资源Service实现
 * @author WangHaiLin
 * @date 2018/5/14  14:19
 */
@Service("resBannerPicService")
public class ResBannerPicServiceImpl implements ResBannerPicService {

    @Autowired
    private ResBannerPicMapper resBannerPicMapper;

    @Override
    public ResBannerPic getBannerPicById(int picId) {
        return resBannerPicMapper.selectPicById(picId);
    }
}
