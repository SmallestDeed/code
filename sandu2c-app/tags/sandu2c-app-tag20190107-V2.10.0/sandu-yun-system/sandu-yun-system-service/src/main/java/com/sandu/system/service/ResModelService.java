package com.sandu.system.service;

import com.sandu.system.model.ResModel;
import com.sandu.system.model.search.ResModelSearch;

import java.util.List;

/**
 * @version V1.0
 * @Title: ResModelService.java
 * @Package com.sandu.system.service
 * @Description:系统-模型资源库Service
 * @createAuthor pandajun
 * @CreateDate 2015-05-19 16:05:22
 */
public interface ResModelService {
    /**
     * 新增数据
     *
     * @param resModel
     * @return int
     */
    int add(ResModel resModel);

    /**
     * 更新数据
     *
     * @param resModel
     * @return int
     */
    int update(ResModel resModel);

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
     * @return ResModel
     */
    ResModel get(Integer id);

    /**
     * 所有数据
     *
     * @param resModel
     * @return List<ResModel>
     */
    List<ResModel> getList(ResModel resModel);

    /**
     * 获取数据数量
     *
     * @return int
     */
    int getCount(ResModelSearch resModelSearch);

    /**
     * 分页获取数据
     *
     * @return List<ResModel>
     */
    List<ResModel> getPaginatedList(ResModelSearch resModeltSearch);
}
