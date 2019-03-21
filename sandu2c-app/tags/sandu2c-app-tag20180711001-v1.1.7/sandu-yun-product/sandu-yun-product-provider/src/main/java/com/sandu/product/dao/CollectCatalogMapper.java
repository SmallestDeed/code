package com.sandu.product.dao;

import com.sandu.product.model.CollectCatalog;
import com.sandu.product.model.search.CollectCatalogSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version V1.0
 * @Title: CollectCatalogMapper.java
 * @Package com.sandu.product.dao
 * @Description:产品管理-收藏目录表Mapper
 * @createAuthor pandajun
 * @CreateDate 2016-07-01 10:46:26
 */
@Repository
public interface CollectCatalogMapper {
    int insertSelective(CollectCatalog record);

    int updateByPrimaryKeySelective(CollectCatalog record);

    int deleteByPrimaryKey(Integer id);

    CollectCatalog selectByPrimaryKey(Integer id);

    int selectCount(CollectCatalogSearch collectCatalogSearch);

    List<CollectCatalog> selectPaginatedList(
            CollectCatalogSearch collectCatalogSearch);

    List<CollectCatalog> selectList(CollectCatalog collectCatalog);
}
