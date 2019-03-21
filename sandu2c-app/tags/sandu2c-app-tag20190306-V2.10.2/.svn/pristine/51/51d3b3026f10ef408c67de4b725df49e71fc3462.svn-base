package com.sandu.base.service;


import com.sandu.base.model.BaseLiving;
import com.sandu.base.model.search.BaseLivingSearch;

import java.util.List;


/**
 * @version V1.0
 * @Title: BaseLivingService.java
 * @Package com.sandu.system.service
 * @Description:系统-小区Service
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 14:41:11
 */
public interface BaseLivingService {
    /**
     * 新增数据
     *
     * @param baseLiving
     * @return int
     */
    int add(BaseLiving baseLiving);

    /**
     * 更新数据
     *
     * @param baseLiving
     * @return int
     */
    int update(BaseLiving baseLiving);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return BaseLiving
     */
    BaseLiving get(Integer id);

    /**
     * 所有数据
     *
     * @param baseLiving
     * @return List<BaseLiving>
     */
    List<BaseLiving> getList(BaseLiving baseLiving);

    /**
     * 获取数据数量
     *
     * @return int
     */
    int getCount(BaseLivingSearch baseLivingSearch);

    /**
     * 分页获取数据
     *
     * @return List<BaseLiving>
     */
    List<BaseLiving> getPaginatedList(
            BaseLivingSearch baseLivingtSearch);

    /**
     * 根据省市，小区名查询小区
     *
     * @param baseLivingSearch
     * @return
     */
    List<BaseLiving> getLivingByName(BaseLivingSearch baseLivingSearch);
}
