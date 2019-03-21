package com.sandu.optimizeplan.service.impl;

import com.sandu.design.model.DesignPlanProduct;
import com.sandu.design.model.ProductsCost;
import com.sandu.design.model.ProductsCostType;
import com.sandu.designplan.model.DesignPlan;
import com.sandu.optimizeplan.dao.OptimizePlanMapper;
import com.sandu.optimizeplan.service.OptimizePlanService;
import com.sandu.product.model.ProductCostDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  优化方案服务
 * ------此接口在运营网站V1版本没有，因为系统架构调整，不能通过其他Service直接调用Dao方法，所以新增接口从Service调用
 *
 * @date 20171113
 * @auth pengxuangang
 *
 */
@Service("optimizePlanService")
public class OptimizePlanServiceImpl implements OptimizePlanService{

    private final static Logger logger = LoggerFactory.getLogger(OptimizePlanServiceImpl.class);

    @Autowired
    private OptimizePlanMapper optimizePlanMapper;

    @Override
    public DesignPlan getPlan(Integer id) {
        //I don't know why get the DesignPlan Object from here.Why don't to invoke from the DesignPlanService?----By pengxuangang
        
    	return optimizePlanMapper.selectByPrimaryKeyPlan(id);
    }

    @Override
    public int costTypeListCount(DesignPlanProduct designPlanProduct) {
        return optimizePlanMapper.costTypeListCount(designPlanProduct);
    }

    @Override
    public List<ProductsCostType> costTypeList(DesignPlanProduct designPlanProduct) {
        return optimizePlanMapper.costTypeList(designPlanProduct);
    }

    @Override
    public List<ProductsCost> costList(ProductsCostType productsCostType) {
        return optimizePlanMapper.costList(productsCostType);
    }

    @Override
    public List<ProductCostDetail> costDetail(ProductsCost cost) {
        return optimizePlanMapper.costDetail(cost);
    }
}
