package com.sandu.search.service.index.impl;

import com.sandu.search.common.constant.IndexInfoQueryConfig;
import com.sandu.search.dao.GroupProductIndexDao;
import com.sandu.search.entity.elasticsearch.po.GroupProductPO;
import com.sandu.search.exception.GroupProductIndexException;
import com.sandu.search.service.index.GroupProductIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分组产品索引服务
 *
 * @date 20171225
 * @auth zhengyoucai
 */
@Slf4j
@Service("groupProductIndexService")
public class GroupProductIndexServiceImpl implements GroupProductIndexService {

    private final static String CLASS_LOG_PREFIX = "分类产品索引服务:";

    private final GroupProductIndexDao groupProductIndexDao;

    @Autowired
    public GroupProductIndexServiceImpl(GroupProductIndexDao groupProductIndexDao) {
        this.groupProductIndexDao = groupProductIndexDao;
    }

    @Override
    public List<GroupProductPO> queryGroupProductList(int start, int limit) throws GroupProductIndexException {
        //初始化数据条数
        if (0 == limit) {
            limit = IndexInfoQueryConfig.DEFAULT_QUERY_PRODUCTPOINFO_LIMIT;
        }

        //查询组合产品信息
        log.info(CLASS_LOG_PREFIX + "正在查询组合产品信息第{}-{}条.", start, (start + limit));
        List<GroupProductPO> groupProductPOList;
        try {
            groupProductPOList = groupProductIndexDao.queryGroupProductList(start, limit);
        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + "获取组合产品数据第{}-{}条失败,Exception:" + e, start, (start + limit));
            throw new GroupProductIndexException(CLASS_LOG_PREFIX + "获取组合产品数据第" + start + "-" + (start + limit) + "条失败,Exception:" + e);
        }
        log.info(CLASS_LOG_PREFIX + "查询组合产品信息完成,List<GroupProductPO>长度:{}.", null == groupProductPOList ? 0 : groupProductPOList.size());

        return groupProductPOList;
    }

    @Override
    public List<GroupProductPO> queryGroupProductListByIdList(List<Integer> groupProductIdList) throws GroupProductIndexException {
        if (null == groupProductIdList || 0 >= groupProductIdList.size()) {
            return null;
        }

        List<GroupProductPO> groupProductList;
        try {
            groupProductList = groupProductIndexDao.queryGroupProductListByIdList(groupProductIdList);
        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + "根据组合产品id集合获取组合数据异常，exception:{}", e);
            throw new GroupProductIndexException(CLASS_LOG_PREFIX + "通过id集合获取组合产品数据异常：exception：" + e);
        }
        log.info(CLASS_LOG_PREFIX + "根据组合产品id集合获取组合数据完成，共获取组合个数：{}", null == groupProductList ? 0 : groupProductIdList.size());

        return groupProductList;

    }
}
