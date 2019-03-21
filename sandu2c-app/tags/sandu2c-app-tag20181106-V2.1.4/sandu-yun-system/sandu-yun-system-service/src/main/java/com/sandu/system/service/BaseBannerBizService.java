package com.sandu.system.service;

import com.sandu.banner.output.BaseBannerListVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基础-广告-对外服务接口
 * @author WangHaiLin
 * @date 2018/5/15  11:30
 */
@Component
public interface BaseBannerBizService {
    /**
     * 查询广告图片信息
     * @param type
     * @param positionCode
     * @param objId
     * @return
     */
    List<BaseBannerListVO> getBannerImgs(Integer type, String positionCode, Integer objId);
}
