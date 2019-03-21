package com.sandu.search.service.index.impl;

import com.sandu.search.common.constant.IndexInfoQueryConfig;
import com.sandu.search.common.tools.JsonUtil;
import com.sandu.search.dao.ProductIndexDao;
import com.sandu.search.entity.elasticsearch.po.ProductPo;
import com.sandu.search.exception.ProductIndexException;
import com.sandu.search.service.index.ProductIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类产品索引服务
 *
 * @date 20171212
 * @auth pengxuangang
 */
@Slf4j
@Service("productIndexService")
public class ProductIndexServiceImpl implements ProductIndexService {

    private final static String CLASS_LOG_PREFIX = "分类产品索引服务:";

    private final ProductIndexDao productIndexDao;

    @Autowired
    public ProductIndexServiceImpl(ProductIndexDao productIndexDao) {
        this.productIndexDao = productIndexDao;
    }

    @Override
    public List<ProductPo> queryProductPoList(int start, int limit) throws ProductIndexException {

        //初始化数据条数
        if (0 == limit) {
            limit = IndexInfoQueryConfig.DEFAULT_QUERY_PRODUCTPOINFO_LIMIT;
        }

        //查询产品信息
        log.info(CLASS_LOG_PREFIX + "正在查询产品信息第{}-{}条.", start, (start + limit));
        List<ProductPo> productPoList;
        try {
            productPoList = productIndexDao.queryProductPoList(start, limit);
        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + "获取产品数据第{}-{}条失败,Exception:" + e, start, (start + limit));
            throw new ProductIndexException(CLASS_LOG_PREFIX + "获取产品数据第" + start + "-" + (start + limit) + "条失败,Exception:" + e);
        }
        log.info(CLASS_LOG_PREFIX + "查询产品信息完成,List<ProductPo>长度:{}.", productPoList.size());

        return productPoList;
    }


    @Override
    public List<ProductPo> queryProductPoListByProductIdList(List<Integer> productIdList) throws ProductIndexException {
        if (null == productIdList || 0 >= productIdList.size()) {
            return null;
        }

        //查询产品信息
        log.info(CLASS_LOG_PREFIX + "正在查询产品信息,productIdList:{}.", JsonUtil.toJson(productIdList));
        List<ProductPo> productPoList;
        try {
            productPoList = productIndexDao.queryProductPoListByProductIdList(productIdList);
        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + "获取产品信息失败,productIdList:{}, Exception:{}.", JsonUtil.toJson(productIdList), e);
            throw new ProductIndexException(CLASS_LOG_PREFIX + "获取产品信息失败,Exception:" + e);
        }
        log.info(CLASS_LOG_PREFIX + "查询产品信息完成,List<ProductPo>长度:{}.", productPoList.size());

        return productPoList;
    }
}
