package com.sandu.api.basesupplydemand.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.basesupplydemand.input.BasesupplydemandQuery;
import com.sandu.api.basesupplydemand.model.Basesupplydemand;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * supply_demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-20 10:46
 */

@Repository
public interface BaseSupplydemandService {

    /**
     * 插入
     *
     * @param basesupplydemand
     * @return
     */
    int insert(Basesupplydemand basesupplydemand);

    /**
     * 更新
     *
     * @param basesupplydemand
     * @return
     */
    int update(Basesupplydemand basesupplydemand);

    /**
     * 删除
     *
     * @param basesupplydemandIds
     * @return
     */
    int delete(Set<Integer> basesupplydemandIds);

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
     * @return
     */
    PageInfo<Basesupplydemand> findAll(BasesupplydemandQuery query);

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
