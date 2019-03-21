package com.sandu.api.customer.service;

import com.sandu.api.area.output.BaseAreaBo;
import org.springframework.stereotype.Component;

@Component
public interface CustomerCommonService {
    /**
     * 查询用户区域
     * SELECT area_long_code FROM base_house -- 小区地址;
     * SELECT * FROM mall_user_address -- 收货地址
     * SELECT * FROM sys_user  -- 微信定位地址;
     * SELECT * FROM `base_mobile_area`; -- 手机定位地址，带手机前七位去查
     * @param userId
     * @return
     */
    BaseAreaBo queryAreaInfoByUserId(Integer userId);

}
