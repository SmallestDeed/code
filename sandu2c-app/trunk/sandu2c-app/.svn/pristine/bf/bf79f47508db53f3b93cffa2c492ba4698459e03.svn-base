package com.sandu.product.service;


import com.sandu.common.model.ResponseEnvelope;
import com.sandu.product.model.CollectCatalog;
import com.sandu.product.model.search.CollectCatalogSearch;
import com.sandu.user.model.LoginUser;

import java.util.List;


public interface CollectCatalogService {

    ResponseEnvelope queryCollectCatalogList(CollectCatalogSearch collectCatalogSearch, LoginUser loginUser);

    ResponseEnvelope deleteCollectCatalog(CollectCatalog collectCatalog);

    /**
     * 新增数据
     *
     * @param collectCatalog
     * @return int
     */
    int add(CollectCatalog collectCatalog);

    /**
     * 更新数据
     *
     * @param collectCatalog
     * @return int
     */
    int update(CollectCatalog collectCatalog);

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
     * @return CollectCatalog
     */
    CollectCatalog get(Integer id);

    /**
     * 所有数据
     *
     * @param collectCatalog
     * @return List<CollectCatalog>
     */
    List<CollectCatalog> getList(CollectCatalog collectCatalog);

    /**
     * 获取数据数量
     *
     * @return int
     */
    int getCount(CollectCatalogSearch collectCatalogSearch);

    /**
     * 分页获取数据
     *
     * @return List<CollectCatalog>
     */
    List<CollectCatalog> getPaginatedList(CollectCatalogSearch collectCatalogtSearch);
}
