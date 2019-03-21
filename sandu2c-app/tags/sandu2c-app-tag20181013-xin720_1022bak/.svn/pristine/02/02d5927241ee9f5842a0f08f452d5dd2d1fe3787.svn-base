package com.sandu.order.service.impl;

import com.sandu.order.ActivityUserCouponOperateLog;
import com.sandu.order.dao.CouponOperateLogMapper;
import com.sandu.order.service.CouponOperateLogService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * 优惠卷操作记录 service 实现层
 * @author WangHaiLin
 * @date 2018/8/1  14:24
 */
@Service("couponOperateLogService")
public class CouponOperatorLogServiceImpl implements CouponOperateLogService {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(CouponOperatorLogServiceImpl.class);

    @Autowired
    private CouponOperateLogMapper couponOperateLogMapper;

    /**
     * 新增优惠间操作记录
     * @param operateLog 入参
     * @return int 结果
     */
    @Override
    public int insertOperatorLog(ActivityUserCouponOperateLog operateLog) {
        logger.info("新增优惠卷操作记录入参:{}",operateLog);
        int result = couponOperateLogMapper.insert(operateLog);
        logger.info("新增优惠卷操作记录结果:{}",result);
        return result;
    }
}
