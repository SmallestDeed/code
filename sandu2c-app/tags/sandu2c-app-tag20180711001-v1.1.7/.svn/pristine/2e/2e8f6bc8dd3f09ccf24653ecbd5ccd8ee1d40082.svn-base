package com.sandu.designtemplate.service;

import com.sandu.design.model.DesignTemplet;
import com.sandu.design.model.search.DesignTempletSearch;

import java.util.List;

/**
 * @version V1.0
 * @Title: DesignTempletService.java
 * @Package com.sandu.design.service
 * @Description:设计模块-设计方案样板房表Service
 * @createAuthor pandajun
 * @CreateDate 2015-07-05 14:47:35
 */
public interface DesignTempletService {
    /**
     * 新增数据
     *
     * @param designTemplet
     * @return int
     */
    int add(DesignTemplet designTemplet);

    /**
     * 更新数据
     *
     * @param designTemplet
     * @return int
     */
    int update(DesignTemplet designTemplet);

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
     * @return DesignTemplet
     */
    DesignTemplet get(Integer id);

    /**
     * 获取数据数量
     *
     * @return int
     */
    int getCount(DesignTempletSearch designTempletSearch);

    /**
     * 分页获取数据
     *
     * @return List<DesignTemplet>
     */
    List<DesignTemplet> getPaginatedList(DesignTempletSearch designTemplettSearch);
}
