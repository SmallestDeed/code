package com.nork.user.service;

/**
 * 用户金融服务---(包含用户积分，可用户型数或其他类资产服务)
 * @date 2017/12/11
 * @auth hw
 */
public interface UserFinanceService {

    /**
     * 检查用户户型是否可用(已购买的户型不算入户型数计算)
     * @param userId        用户ID
     * @param houseId       户型ID
     * @return
     */
    boolean checkUserHouseIsAbleUse(Integer userId, Integer houseId);
}
