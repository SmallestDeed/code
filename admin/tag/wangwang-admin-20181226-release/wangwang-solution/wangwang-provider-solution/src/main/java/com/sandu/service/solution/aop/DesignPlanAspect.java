package com.sandu.service.solution.aop;

import com.sandu.api.solution.constant.CopyType;
import com.sandu.api.solution.service.DesignPlanRecommendedService;
import com.sandu.service.solution.dao.DesignPlanCopyLogMapper;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author  by bvvy
 * 删除方案是触发
 */
@Component
@Aspect
public class DesignPlanAspect {

    @Resource
    private DesignPlanCopyLogMapper designPlanCopyLogMapper;

    @Resource
    private DesignPlanRecommendedService designPlanRecommendedService;

    @Pointcut("execution(* com.sandu.service.solution.impl.DesignPlanRecommendedServiceImpl.delete*(..))")
    public void deletePointcut() {
    }

    @After("deletePointcut() && args(id)")
    public void afterDelete(long id) {
        designPlanCopyLogMapper.deleteByPlanId(id, CopyType.DELIVER.getCode());
        //获取分享后变成的方案
        List<Integer> planIds = designPlanCopyLogMapper.listDeliveredPlanIds(Long.valueOf(id).intValue(), CopyType.SHARE.getCode(),1);
        //删除这些方案
        planIds.forEach(designPlanRecommendedService::deleteDesignPlanRecommended);
        //清除记录
        designPlanCopyLogMapper.deleteBySourceId(id, CopyType.SHARE.getCode(),1);
    }

}
