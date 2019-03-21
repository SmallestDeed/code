package com.sandu.pay.user.service.impl;

import com.google.gson.Gson;
import com.sandu.home.model.BaseHouse;
import com.sandu.pay.user.dao.UserFinanceMapper;
import com.sandu.user.service.UserFinanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户金融服务---(包含用户度币，可用户型数或其他类资产服务)
 *
 * @date 20171125
 * @auth pengxuangang
 */
@Service("userFinanceService")
public class UserFinanceServiceImpl implements UserFinanceService {

    private final static Gson GSON = new Gson();
    private final static String CLASS_LOG_PREFIX = "[用户金融服务]:";
    private final static Logger logger = LoggerFactory.getLogger(UserFinanceServiceImpl.class);

    @Autowired
    private UserFinanceMapper userFinanceMapper;

    @Override
    public Integer queryUserValidHouseCount(Integer userId) {
        if (null == userId || userId <= 0) {
            logger.warn(CLASS_LOG_PREFIX + "查询用户可使用户型数失败,userid is null.");
            return null;
        }

        logger.info(CLASS_LOG_PREFIX + "查询用户可使用户型数:userId:{}", userId);
        Integer validHouseCount = userFinanceMapper.queryUserValidHouseCount(userId);
        logger.info(CLASS_LOG_PREFIX + "查询用户可使用户型数完成!->userId:{}, validHouseCount:{}", userId, validHouseCount);
        return validHouseCount;
    }

    @Override
    public Integer queryUserAlreadyBoughtHouseCount(Integer userId) {
        if (null == userId || userId <= 0) {
            logger.warn(CLASS_LOG_PREFIX + "查询用户已购买户型数失败,userid is null.");
            return null;
        }

        logger.info(CLASS_LOG_PREFIX + "查询用户已购买户型数:userId:{}", userId);
        Integer alreadyBoughtHouseCount = userFinanceMapper.queryUserAlreadyBoughtHouseCount(userId);
        logger.info(CLASS_LOG_PREFIX + "查询用户已购买户型数完成!->userId:{}, alreadyBoughtHouseCount:{}", userId, alreadyBoughtHouseCount);
        return alreadyBoughtHouseCount;
    }

    @Override
    public Integer queryUserUsedHouseCount(Integer userId) {
        if (null == userId || userId <= 0) {
            logger.warn(CLASS_LOG_PREFIX + "查询用户已使用户型数失败,userid is null.");
            return null;
        }

        logger.info(CLASS_LOG_PREFIX + "查询用户已使用户型数:userId:{}", userId);
        Integer userUsedHouseCount = userFinanceMapper.queryUserUsedHouseCount(userId);
        logger.info(CLASS_LOG_PREFIX + "查询用户已使用户型数完成!->userId:{}, userUsedHouseCount:{}", userId, userUsedHouseCount);
        return userUsedHouseCount;
    }

    @Override
    public List<Integer> queryUserUsedHouseId(Integer userId) {
        if (null == userId || userId <= 0) {
            logger.warn(CLASS_LOG_PREFIX + "查询用户已使用户型失败,userid is null.");
            return null;
        }

        logger.info(CLASS_LOG_PREFIX + "查询用户已使用户型ID:userId:{}", userId);
        List<Integer> userUsedHouseIdList = userFinanceMapper.queryUserUsedHouseId(userId);
        logger.info(CLASS_LOG_PREFIX + "查询用户已使用户型ID完成!->userId:{}, validHouseCount:{}", userId, GSON.toJson(userUsedHouseIdList));
        return userUsedHouseIdList;
    }

    @Override
    public List<BaseHouse> queryUserUsedHouseDetailList(BaseHouse baseHouse) {
        if (null == baseHouse.getUserId() || baseHouse.getUserId() <= 0) {
            logger.warn(CLASS_LOG_PREFIX + "查询用户已使用户型列表失败,userid is null.");
            return null;
        }

        logger.info(CLASS_LOG_PREFIX + "查询用户已使用户型列表:userId:{}", baseHouse.getUserId());
        List<BaseHouse> baseHouseList = userFinanceMapper.queryUserUsedHouseDetailList(baseHouse);
        logger.info(CLASS_LOG_PREFIX + "查询用户已使用户型列表完成!->userId:{}, baseHouseList:{}", baseHouse.getUserId(), GSON.toJson(baseHouseList));
        return baseHouseList;
    }

    @Override
    public boolean checkUserHouseIsAbleUse(Integer userId, Integer houseId) {

        if (null == userId) {
            logger.warn(CLASS_LOG_PREFIX + "检查用户户型是否可用(已购买的户型不算入户型数计算),userId:{},houseId:{}.", userId, houseId);
            return false;
        }

        //若houseId为空则户型可用验证始终返回true
        if (null == houseId || 0 == houseId) {
            logger.info(CLASS_LOG_PREFIX + "检查用户户型是否可用(已购买的户型不算入户型数计算---houseId为空，户型一直可用。),userId:{},houseId:{}.", userId, houseId);
            return true;
        }

        logger.info(CLASS_LOG_PREFIX + "检查用户户型是否可用(已购买的户型不算入户型数计算),userId:{},houseId:{}.", userId, houseId);
        boolean isAbleUse = userFinanceMapper.checkUserHouseIsAbleUse(userId, houseId);
        logger.info(CLASS_LOG_PREFIX + "检查用户户型是否可用(已购买的户型不算入户型数计算)完成!,isAbleUse:{}.", isAbleUse);

        return isAbleUse;
    }
}
