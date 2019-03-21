package com.sandu.service.solution.impl;

import com.sandu.api.solution.model.DesignPlanRecommendedProduct;
import com.sandu.api.solution.service.DesignPlanRecommendedProductService;
import com.sandu.service.solution.dao.DesignPlanRecommendedProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Roc Chen
 * @Description 推荐方案产品 基础服务
 * @Date:Created Administrator in 16:49 2017/12/21 0021
 * @Modified By:
 */
@Service("designPlanRecommendedProductService")
public class DesignPlanRecommendedProductServiceImpl implements DesignPlanRecommendedProductService {

    @Autowired
    DesignPlanRecommendedProductMapper designPlanRecommendedProductMapper;

    /**
     * 根据id物理删除数据
     * @param id 主键
     * @return 受影响行数
     */
    @Override
    public int deleteDesignPlanRecommendedProduct(long id){
        return designPlanRecommendedProductMapper.deleteByPrimaryKey(id);
    }

    /**
     * Selective新增数据
     * @param record 对象
     * @return 新增的数据的id
     */
    @Override
    public long addDesignPlanRecommendedProduct(DesignPlanRecommendedProduct record){
        int result = designPlanRecommendedProductMapper.insertSelective(record);
        if(result > 0){
            return record.getId();
        }
        return 0L;
    }

    /**
     * 根据id查询数据
     * @param id 主键
     * @return 对象数据
     */
    @Override
    public DesignPlanRecommendedProduct getDesignPlanRecommendedProduct(long id){
        return designPlanRecommendedProductMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据id Selective修改数据
     * @param record 对象
     * @return 受影响的行数
     */
    @Override
    public int updateDesignPlanRecommendedProduct(DesignPlanRecommendedProduct record){
        return designPlanRecommendedProductMapper.updateByPrimaryKeySelective(record);
    }


    /**
     * 根据id逻辑删除数据
     * @param id 主键
     * @return 受影响的行数
     */
    @Override
    public int deleteLogicDesignPlanRecommendedProduct(long id){
        return designPlanRecommendedProductMapper.deleteLogicByPrimaryKey(id);
    }

    /**
     * Selective 获取集合数据
     * @param record 对象
     * @return list数据集合
     */
    @Override
    public List<DesignPlanRecommendedProduct> listDesignPlanRecommendedProduct(DesignPlanRecommendedProduct record){
        return designPlanRecommendedProductMapper.selectListSelective(record);
    }

    @Override
    public void batchAddDesignPlanProducts(List<DesignPlanRecommendedProduct> products, String planId) {
        if (products.size() > 0) {
            designPlanRecommendedProductMapper.batchAddDesignPlanProducts(products, planId);
        }
    }

    @Override
    public List<DesignPlanRecommendedProduct> listByPlanId(Integer planId) {
        return designPlanRecommendedProductMapper.listByPlanId(planId);
    }

}
