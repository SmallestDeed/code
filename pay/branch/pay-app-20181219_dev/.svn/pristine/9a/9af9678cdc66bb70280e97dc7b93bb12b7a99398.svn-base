package com.sandu.user.service;

import com.sandu.home.model.BaseHouse;

import java.util.List;

/**
 * 用户金融服务---(包含用户度币，可用户型数或其他类资产服务)
 *
 * @date 20171125
 * @auth pengxuangang
 */
public interface UserFinanceService {

    /**
     * 查询用户可使用户型数
     * @param userId    用户ID
     * @return
     */
    Integer queryUserValidHouseCount(Integer userId);

    /**
     * 查询用户已购买户型数
     * @param userId    用户ID
     * @return
     */
    Integer queryUserAlreadyBoughtHouseCount(Integer userId);

    /**
     * 查询用户已使用户型数
     * @param userId    用户ID
     * @return
     */
    Integer queryUserUsedHouseCount(Integer userId);

    /**
     * 查询用户已使用户型ID
     * @return
     */
    List<Integer> queryUserUsedHouseId(Integer userId);

    /**
     * 查询用户已使用户型列表
     * @return List<BaseHouse>
     */
    List<BaseHouse> queryUserUsedHouseDetailList(BaseHouse baseHouse);

    /**
     * 检查用户户型是否可用(已购买的户型不算入户型数计算)
     * @param userId        用户ID
     * @param houseId       户型ID
     * @return (注意:若houseId为空则户型可用验证始终返回true)
     */
    boolean checkUserHouseIsAbleUse(Integer userId, Integer houseId);
}
