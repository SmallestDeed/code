package com.sandu.service.grouppurchase.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.grouppurchase.input.GroupPurchaseActivityQuery;
import com.sandu.api.grouppurchase.model.GroupPurchaseActivity;
import com.sandu.api.grouppurchase.service.GroupPurchaseActivityService;
import com.sandu.service.grouppurchase.dao.GroupPurchaseActivityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 16:10 2018/12/6
 */
@Slf4j
@Service("groupPurchaseActivityService")
public class GroupPurchaseActivityServiceImpl implements GroupPurchaseActivityService {

    @Autowired
    private GroupPurchaseActivityMapper groupPurchaseActivityMapper;

    @Override
    public int create(GroupPurchaseActivity activity) {
        return groupPurchaseActivityMapper.insertSelective(activity);
    }

    @Override
    public PageInfo<GroupPurchaseActivity> findAll(GroupPurchaseActivityQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        return new PageInfo<>(groupPurchaseActivityMapper.findAll(query));
    }

    @Override
    public int updateStatus(String id, String activityStatus) {
        return groupPurchaseActivityMapper.updateStatus(id,activityStatus);
    }

    @Override
    public int deleteActivity(String id) {
        return groupPurchaseActivityMapper.deleteByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public GroupPurchaseActivity getByActivityId(int id) {
        return groupPurchaseActivityMapper.selectByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public int update(GroupPurchaseActivity activity) {
        return groupPurchaseActivityMapper.updateByPrimaryKeySelective(activity);
    }
}
