package com.sandu.order.service;

import com.sandu.order.ActivityUserCouponOperateLog;
import org.springframework.stereotype.Component;

/**
 * 优惠卷操作记录
 * @author WangHaiLin
 * @date 2018/8/1  14:20
 */
@Component
public interface CouponOperateLogService {

    /**
     * wangHaiLin
     * 新增优惠卷操作记录
     * @param operateLog 入参
     * @return int 操作结果
     */
    int insertOperatorLog(ActivityUserCouponOperateLog operateLog);


}
