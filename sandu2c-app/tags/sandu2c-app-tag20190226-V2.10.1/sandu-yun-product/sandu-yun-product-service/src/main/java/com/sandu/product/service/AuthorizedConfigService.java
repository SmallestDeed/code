package com.sandu.product.service;


import com.sandu.product.model.AuthorizedConfig;
import com.sandu.product.model.search.AuthorizedConfigSearch;

import java.util.List;


/**
 * @version V1.0
 * @Title: AuthorizedConfigService.java
 * @Package com.sandu.product.service
 * @Description:产品-授权配置Service
 * @createAuthor pandajun
 * @CreateDate 2016-04-27 14:07:34
 */
public interface AuthorizedConfigService {
    /**
     * 新增数据
     *
     * @param authorizedConfig
     * @return int
     */
    int add(AuthorizedConfig authorizedConfig);

    /**
     * 更新数据
     *
     * @param authorizedConfig
     * @return int
     */
    int update(AuthorizedConfig authorizedConfig);

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
     * @return AuthorizedConfig
     */
    AuthorizedConfig get(Integer id);

    /**
     * 所有数据
     *
     * @param authorizedConfig
     * @return List<AuthorizedConfig>
     */
    List<AuthorizedConfig> getList(AuthorizedConfig authorizedConfig);

    /**
     * 获取数据数量
     *
     * @param authorizedConfigSearch
     * @return int
     */
    int getCount(AuthorizedConfigSearch authorizedConfigSearch);

    /**
     * 分页获取数据
     *
     * @param authorizedConfigtSearch
     * @return List<AuthorizedConfig>
     */
    List<AuthorizedConfig> getPaginatedList(AuthorizedConfigSearch authorizedConfigtSearch);

    /**
     * 查找该用户绑定的序列号
     *
     * @param userId
     * @return
     */
    List<AuthorizedConfig> findAllByUserId(Integer userId);

    List<AuthorizedConfig> selectList(AuthorizedConfig authorizedConfig);

    /**
     * 去重复(品牌,大类,小类)
     *
     * @param authorizedConfig
     * @author huangsongbo
     */
    void dealWithRepeat(AuthorizedConfig authorizedConfig);
}
