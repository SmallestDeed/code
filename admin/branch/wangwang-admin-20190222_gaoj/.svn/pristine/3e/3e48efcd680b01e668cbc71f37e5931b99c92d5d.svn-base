package com.sandu.service.basesupplydemand.dao;

import com.sandu.api.basesupplydemand.input.BasesupplydemandQuery;
import com.sandu.api.basesupplydemand.model.Basesupplydemand;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
@Repository
public interface BasesupplydemandMapper {
    int insert(Basesupplydemand basesupplydemand);

    int updateByPrimaryKey(Basesupplydemand basesupplydemand);

    int deleteByPrimaryKey(@Param("basesupplydemandIds") Set<Integer> basesupplydemandIds);

    Basesupplydemand selectByPrimaryKey(@Param("basesupplydemandId") int basesupplydemandId);

    List<Basesupplydemand> findAll(BasesupplydemandQuery query);

    /**
     * 置顶
     * @param basesupplydemandId
     * @param topId
     * @return
     */
    int baseSupplyToTop(@Param("id") String basesupplydemandId, @Param("topId") String topId);

    /**
     * 刷新
     * @param basesupplydemandId
     * @return
     */
    int baseSupplyToRefresh(@Param("basesupplydemandId") String basesupplydemandId);


}
