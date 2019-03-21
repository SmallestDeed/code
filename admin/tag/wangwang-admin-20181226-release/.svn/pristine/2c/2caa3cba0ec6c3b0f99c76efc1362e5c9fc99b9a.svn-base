package com.sandu.service.solution.aop;


import com.sandu.api.solution.constant.CopyType;
import com.sandu.api.solution.service.DesignPlanRecommendedService;
import com.sandu.service.solution.dao.DesignPlanCopyLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author by bvvy
 * @date 2018/4/28
 */
//@Aspect
@Component
@Slf4j
public class RemoveShareAspect {

    @Resource
    private DesignPlanCopyLogMapper designPlanCopyLogMapper;

    @Resource
    private DesignPlanRecommendedService designPlanRecommendedService;


//    @Pointcut("execution(* com.sandu.service.solution.impl.biz.DesignPlanBizServiceImpl.handleCancelShelf*(..)) ||" +
//            " execution(* com.sandu.service.solution.impl.DesignPlanRecommendedServiceImpl.handleOffShelf(..))")
    public void offShelfPointcut() {

    }


//    @After(value = "offShelfPointcut() && args(planId,*)")
    public void afterOffShelf(JoinPoint jp, Integer planId) {
//        log.debug("the off share plan id is {}", planId);
//        boolean flag = designPlanRecommendedService.getIsValidSharePlan(planId);
//        log.debug("the valid share plan flag is {}", flag);
//        if (flag) {
//            //获取分享后变成的方案
//            List<Integer> planIds = designPlanCopyLogMapper.listDeliveredPlanIds(planId, CopyType.SHARE.getCode());
//            //删除这些方案
//            planIds.forEach(designPlanRecommendedService::deleteDesignPlanRecommended);
//            //清除记录
//            designPlanCopyLogMapper.deleteBySourceId(planId, CopyType.SHARE.getCode());
//        }


    }

}
