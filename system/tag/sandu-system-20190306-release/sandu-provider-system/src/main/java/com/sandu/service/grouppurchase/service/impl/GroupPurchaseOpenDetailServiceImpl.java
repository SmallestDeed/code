package com.sandu.service.grouppurchase.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.grouppurchase.bo.UserPurchaseListBO;
import com.sandu.api.grouppurchase.input.GroupPurchaseOpenQuery;
import com.sandu.api.grouppurchase.model.GroupPurchaseOpenDetail;
import com.sandu.api.grouppurchase.service.GroupPurchaseOpenDetailService;
import com.sandu.service.grouppurchase.dao.GroupPurchaseOpenDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Sandu
 * @ClassName GroupPurchaseOpenServiceImpl
 * @date 2018/11/6
 */
@Service("groupPurchaseOpenDetailService")
public class GroupPurchaseOpenDetailServiceImpl implements GroupPurchaseOpenDetailService {

	@Autowired
	private GroupPurchaseOpenDetailMapper groupPurchaseOpenDetailMapper;

	@Override
	public List<UserPurchaseListBO> query(GroupPurchaseOpenQuery query) {
		if (query.getPage() != null && query.getLimit() != null) {
			PageHelper.startPage(query.getPage(), query.getLimit());
		}
		return groupPurchaseOpenDetailMapper.bizQuery(query);
	}

	@Override
	public List<GroupPurchaseOpenDetail> listByPurchaseOpenIds(List<Long> purchaseOpenIds) {
		if (purchaseOpenIds == null || purchaseOpenIds.isEmpty()) {
			return Collections.emptyList();
		}
		return groupPurchaseOpenDetailMapper.listByPurchaseOpenIds(purchaseOpenIds);
	}

	@Override
	public PageInfo<GroupPurchaseOpenDetail> getOpenDetailByOpenId(GroupPurchaseOpenQuery query) {
		if (query.getPage() != null && query.getLimit() != null) {
			PageHelper.startPage(query.getPage(), query.getLimit());
		}
		return new PageInfo<>(groupPurchaseOpenDetailMapper.getOpenDetailByOpenId(query));
	}
}
