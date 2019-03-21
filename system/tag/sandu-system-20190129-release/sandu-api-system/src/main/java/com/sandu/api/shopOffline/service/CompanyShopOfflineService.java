package com.sandu.api.shopOffline.service;

import org.springframework.stereotype.Component;

/**
 * @author WangHaiLin
 * @date 2018/12/8  16:07
 */
@Component
public interface CompanyShopOfflineService {
    /**
     * 校验名称是否重复
     * @param shopName 店铺名称
     * @return int 查询结果
     */
    Integer checkShopName(String shopName);
}
