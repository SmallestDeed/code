package com.sandu.service.grouppurchase.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.grouppurchase.input.GroupPurchaseOpenQuery;
import com.sandu.api.grouppurchase.model.GroupPurchaseOpen;
import com.sandu.api.grouppurchase.service.GroupPurchaseOpenService;
import com.sandu.service.grouppurchase.dao.GroupPurchaseOpenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sandu
 * @ClassName GroupPurchaseOpenServiceImpl
 * @date 2018/11/6
 */
@Service("groupPurchaseOpenService")
public class GroupPurchaseOpenServiceImpl implements GroupPurchaseOpenService {

    @Autowired
    private GroupPurchaseOpenMapper groupPurchaseOpenMapper;

    @Override
    public PageInfo<GroupPurchaseOpen> getOpenByActivityId(GroupPurchaseOpenQuery query) {
        if (query.getPage() != null && query.getLimit() != null) {
            PageHelper.startPage(query.getPage(), query.getLimit());
        }
        return new PageInfo<>(groupPurchaseOpenMapper.query(query));
    }
}
