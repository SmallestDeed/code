package com.sandu.system.service;

import com.sandu.banner.model.ResBannerPic;
import org.springframework.stereotype.Component;

/**
 * 广告--图片资源服务接口
 * @author WangHaiLin
 * @date 2018/5/14  14:14
 */
@Component
public interface ResBannerPicService {

    /**
     * 通过图片Id查找图片资源
     * @param picId 图片Id
     * @return  ResBannerPic 查询结果
     */
    ResBannerPic getBannerPicById(int picId);
}
