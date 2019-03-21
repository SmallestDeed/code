package com.sandu.api.banner.service.manage;

import com.sandu.api.banner.output.MiniProgramBannerVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * banner管理服务
 * @author WangHaiLin
 * @date 2018/6/22  16:55
 */
@Component
public interface BaseBannerManageService {

    /**
     * 企业小程序后台查询Banner列表
     * @param refModelId 企业Id
     * @param positionId 位置Id
     * @return list Banner集合
     */
    List<MiniProgramBannerVO> getBannerList(Integer refModelId,Integer positionId);



}
