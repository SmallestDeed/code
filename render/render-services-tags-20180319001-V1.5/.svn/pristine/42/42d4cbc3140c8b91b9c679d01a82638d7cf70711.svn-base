package com.nork.user.service.impl;

import com.google.gson.Gson;
import com.nork.user.dao.UserFinanceMapper;
import com.nork.user.service.UserFinanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户金融服务---(包含用户积分，可用户型数或其他类资产服务)
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
    public boolean checkUserHouseIsAbleUse(Integer userId, Integer houseId) {

        if (null == userId || null == houseId) {
            logger.warn(CLASS_LOG_PREFIX + "检查用户户型是否可用(已购买的户型不算入户型数计算),userId:{},houseId:{}.", userId, houseId);
            return false;
        }

        logger.info(CLASS_LOG_PREFIX + "检查用户户型是否可用(已购买的户型不算入户型数计算),userId:{},houseId:{}.", userId, houseId);
        boolean isAbleUse = userFinanceMapper.checkUserHouseIsAbleUse(userId, houseId);
        logger.info(CLASS_LOG_PREFIX + "检查用户户型是否可用(已购买的户型不算入户型数计算)完成!,isAbleUse:{}.", isAbleUse);

        return isAbleUse;
    }
}
