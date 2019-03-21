package com.sandu.designplan.service.impl;

import com.sandu.designplan.dao.DesignPlanMapper;
import com.sandu.designplan.model.DesignPlan;
import com.sandu.designplan.service.DesignPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @version V1.0
 * @Title: DesignPlanServiceImpl.java
 * @Package com.sandu.design.service.impl
 * @Description:设计模块-设计方案ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-07-03 17:09:51
 */
@Service("designPlanService")
public class DesignPlanServiceImpl implements DesignPlanService {

    private static Logger logger = LoggerFactory.getLogger(DesignPlanServiceImpl.class);
    @Autowired
    private DesignPlanMapper designPlanMapper;

    /**
     * 新增数据
     *
     * @param designPlan
     * @return int
     */
    @Override
    public int add(DesignPlan designPlan) {
        designPlanMapper.insertSelective(designPlan);
        return designPlan.getId();
    }

    /**
     * 更新数据
     *
     * @param designPlan
     * @return int
     */
    @Override
    public int update(DesignPlan designPlan) {
        /*删除 进入该样板房的缓存*/
        return designPlanMapper.updateByPrimaryKeySelective(designPlan);
    }

    @Override
    public void delete(Integer id) {
        designPlanMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return DesignPlan
     */
    @Override
    public DesignPlan get(Integer id) {
        return designPlanMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param designPlan
     * @return List<DesignPlan>
     */
    @Override
    public List<DesignPlan> getList(DesignPlan designPlan) {
        return designPlanMapper.selectList(designPlan);
    }

}
