package com.sandu.service.shopOffline.impl;

import com.sandu.api.shopOffline.service.CompanyShopOfflineService;
import com.sandu.service.shopOffline.dao.CompanyShopOfflineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WangHaiLin
 * @date 2018/12/8  16:19
 */
@Service("companyShopOfflineService")
public class CompanyShopOfflineServiceImpl implements CompanyShopOfflineService {

    @Autowired
    private CompanyShopOfflineDao companyShopOfflineDao;

    @Override
    public Integer checkShopName(String shopName) {
        return companyShopOfflineDao.checkShopName(shopName);
    }
}
