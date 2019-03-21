package com.sandu.api.basesupplydemand.service.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.basesupplydemand.input.BasesupplydemandAdd;
import com.sandu.api.basesupplydemand.input.BasesupplydemandQuery;
import com.sandu.api.basesupplydemand.input.BasesupplydemandUpdate;
import com.sandu.api.basesupplydemand.model.Basesupplydemand;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * supply_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-20 10:46
 */
public interface BaseSupplydemandBizService {

    /**
     * 创建
     *
     * @param input
     * @return
     */
    int create(BasesupplydemandAdd input);

    /**
     * 更新
     *
     * @param input
     * @return
     */
    int update(BasesupplydemandUpdate input);

    /**
     * 删除
     */
    int delete(String basesupplydemandId);

    /**
     * 通过ID获取详情
     *
     * @param basesupplydemandId
     * @return
     */
    Basesupplydemand getById(int basesupplydemandId);

    /**
     * 查询列表
     *
     * @param query
     * @return
     */
    PageInfo<Basesupplydemand> query(BasesupplydemandQuery query);

    /**
     * 置顶
     * @param basesupplydemandId
     * @param topId
     * @return
     */
    int baseSupplyToTop(String basesupplydemandId, String topId);

    /**
     * 刷新
     * @param basesupplydemandId
     * @return
     */
    int baseSupplyToRefresh(String basesupplydemandId, String topId);
}
