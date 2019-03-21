package com.sandu.service.basesupplydemand.impl;

import com.sandu.api.basesupplydemand.model.SupplyDemandCategory;
import com.sandu.api.basesupplydemand.service.SupplyDemandCategoryService;
import com.sandu.service.basesupplydemand.dao.SupplyDemandCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * supply_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-20 10:46
 */
@Slf4j
@Service("supplyDemandCategoryService")
public class SupplyDemandCategoryServiceImpl implements SupplyDemandCategoryService {

    @Autowired
    private SupplyDemandCategoryMapper supplyDemandCategoryMapper;

    @Override
    public List<SupplyDemandCategory> querySupplyDemandCategory() {
        List<SupplyDemandCategory> result = supplyDemandCategoryMapper.querySupplyDemandCategory();
        Iterator<SupplyDemandCategory> iterator = result.iterator();
        while(iterator.hasNext()){
            SupplyDemandCategory category = iterator.next();
            if(category.getPid() == 0) {
                iterator.remove();
            }
        }
        return result;
    }
}
