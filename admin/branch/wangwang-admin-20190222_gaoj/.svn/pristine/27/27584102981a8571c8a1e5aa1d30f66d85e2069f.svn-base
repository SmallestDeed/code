package com.sandu.service.groupproduct.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.groupproducct.input.GroupQuery;
import com.sandu.api.groupproducct.model.GroupProduct;
import com.sandu.api.groupproducct.model.bo.GroupProductListBO;
import com.sandu.api.groupproducct.service.GroupProductService;
import com.sandu.base.IdToSomeBean;
import com.sandu.service.groupproduct.dao.GroupProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Sandu
 */
@Service
public class GroupProductServiceImpl implements GroupProductService {

    @Autowired
    private GroupProductDao groupProductDao;

    @Override
    public int deleteByIds(List<Integer> ids) {
        if (ids != null && ids.isEmpty()) {
            return -1;
        }
        return groupProductDao.deleteByIds(ids);
    }

    @Override
    public GroupProduct getInfoById(Integer id) {
        return groupProductDao.selectByPrimaryKey(id.longValue());
    }

    @Override
    public PageInfo<GroupProductListBO> queryList(GroupQuery groupQuery) {
        PageHelper.startPage(groupQuery.getPage(), groupQuery.getLimit());
        return new PageInfo<>(groupProductDao.queryList(groupQuery));
    }

    @Override
    public int updateGroupProduct(GroupProduct groupProduct) {
        return groupProductDao.updateByPrimaryKeySelective(groupProduct);
    }

    @Override
    public PageInfo<GroupProduct> getInfoByIds(List<Integer> notAllotIds) {
        if (notAllotIds != null && notAllotIds.isEmpty()) {
            return new PageInfo<>(Collections.emptyList());
        }
        return new PageInfo<>(groupProductDao.getInfoByIds(notAllotIds));
    }

    @Override
    public PageInfo<GroupProductListBO> query2bList(GroupQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        return new PageInfo<>(groupProductDao.queryList2b(query));
    }

    @Override
    public PageInfo<GroupProductListBO> query2cList(GroupQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        return new PageInfo<>(groupProductDao.queryList2c(query));
    }

    @Override
    public GroupProduct getInfo2bById(Integer groupId) {
        return null;
    }

    @Override
    public GroupProduct getInfo2cById(Integer groupId) {
        return null;
    }


    @Override
    public int addGroup(GroupProduct group) {
        return groupProductDao.insertSelective(group);
    }

    @Override
    public boolean updateGroupSecrecyByIds(List<Integer> ids, Integer secrecy) {
        return !ids.isEmpty() && groupProductDao.updateGroupSecrecyByIds(ids, secrecy) > 0;
    }

    @Override
    public String getMaxId() {
        return groupProductDao.getMaxId();
    }

    @Override
    public Map<String, String> listGroupPutStatusByGroupIds(List<Integer> collect) {
        if (collect.isEmpty()) {
            return Collections.emptyMap();
        }
        List<IdToSomeBean> idToSomeBeans = groupProductDao.listGroupPutStatusByGroupIds(collect);
        Map<String, String> result = new HashMap<>(idToSomeBeans.size());
        idToSomeBeans.forEach(item -> result.put(item.getId(), item.getSome()));
        return result;
    }
}
