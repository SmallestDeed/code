package com.sandu.decorate.service.impl;

import com.sandu.decorate.dao.PlanDecoratePriceMapper;
import com.sandu.decorate.input.PlanDecoratePriceQuery;
import com.sandu.decorate.model.PlanDecoratePrice;
import com.sandu.decorate.service.PlanDecoratePriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-plan
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Aug-08 15:37
 */
@Slf4j
@Service("planDecoratePriceService")
public class PlanDecoratePriceServiceImpl implements PlanDecoratePriceService {

    @Autowired
    private PlanDecoratePriceMapper planDecoratePriceMapper;

    @Override
    public int insert(PlanDecoratePrice planDecoratePrice) {
        int result = planDecoratePriceMapper.insert(planDecoratePrice);
        if (result > 0) {
            return planDecoratePrice.getId();
        }
        return 0;
    }

    @Override
    public int update(PlanDecoratePrice planDecoratePrice) {
        return planDecoratePriceMapper.updateByPrimaryKey(planDecoratePrice);
    }

    @Override
    public int delete(Set<Integer> planDecoratePriceIds) {
        return planDecoratePriceMapper.deleteByPrimaryKey(planDecoratePriceIds);
    }

    @Override
    public PlanDecoratePrice getById(int planDecoratePriceId) {
        return planDecoratePriceMapper.selectByPrimaryKey(planDecoratePriceId);
    }

    @Override
    public List<PlanDecoratePrice> findAll(PlanDecoratePriceQuery query) {
        return planDecoratePriceMapper.findAll(query);
    }

    @Override
    public int insertBatch(List<PlanDecoratePrice> insertList) {
        return planDecoratePriceMapper.insertBatch(insertList);
    }

    @Override
    public int updateBatch(List<PlanDecoratePrice> updateList) {
        return planDecoratePriceMapper.updateBatch(updateList);
    }

    @Override
    public int deleteByRenderSceneId(Integer renderSceneId) {
        return planDecoratePriceMapper.deleteByRenderSceneId(renderSceneId);
    }
}
