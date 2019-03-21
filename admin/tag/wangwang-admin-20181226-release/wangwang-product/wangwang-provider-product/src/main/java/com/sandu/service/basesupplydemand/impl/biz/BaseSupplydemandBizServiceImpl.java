package com.sandu.service.basesupplydemand.impl.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.sandu.api.basesupplydemand.input.BasesupplydemandAdd;
import com.sandu.api.basesupplydemand.input.BasesupplydemandQuery;
import com.sandu.api.basesupplydemand.input.BasesupplydemandUpdate;
import com.sandu.api.basesupplydemand.model.Basesupplydemand;
import com.sandu.api.basesupplydemand.service.BaseSupplydemandService;
import com.sandu.api.basesupplydemand.service.biz.BaseSupplydemandBizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * supply_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-20 10:46
 */
@Slf4j
@Service(value = "baseSupplydemandBizService")
public class BaseSupplydemandBizServiceImpl implements BaseSupplydemandBizService {

    @Autowired
    private BaseSupplydemandService baseSupplydemandService;

    @Override
    public int create(BasesupplydemandAdd input) {

        Basesupplydemand basesupplydemand = new Basesupplydemand();
        BeanUtils.copyProperties(input, basesupplydemand);

        return baseSupplydemandService.insert(basesupplydemand);
    }

    @Override
    public int update(BasesupplydemandUpdate input) {
        Basesupplydemand basesupplydemand = new Basesupplydemand();

        BeanUtils.copyProperties(input, basesupplydemand);
        //转换原字段ID
//        basesupplydemand.setId(input.getBasesupplydemandId());
        return baseSupplydemandService.update(basesupplydemand);
    }

    @Override
    public int delete(String basesupplydemandId) {
        if (Strings.isNullOrEmpty(basesupplydemandId)) {
            return 0;
        }

        Set<Integer> basesupplydemandIds = new HashSet<>();
        List<String> list = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(basesupplydemandId));
        list.stream().forEach(id -> basesupplydemandIds.add(Integer.valueOf(id)));

        if (basesupplydemandIds.size() == 0) {
            return 0;
        }
        return baseSupplydemandService.delete(basesupplydemandIds);
    }

    @Override
    public Basesupplydemand getById(int basesupplydemandId) {
        return baseSupplydemandService.getById(basesupplydemandId);
    }

    @Override
    public PageInfo<Basesupplydemand> query(BasesupplydemandQuery query) {
        return baseSupplydemandService.findAll(query);
    }

    @Override
    public int baseSupplyToTop(String basesupplydemandId, String topId) {
        return baseSupplydemandService.baseSupplyToTop(basesupplydemandId, topId);
    }

    @Override
    public int baseSupplyToRefresh(String basesupplydemandId, String topId) {
        return baseSupplydemandService.baseSupplyToRefresh(basesupplydemandId, topId);
    }
}
