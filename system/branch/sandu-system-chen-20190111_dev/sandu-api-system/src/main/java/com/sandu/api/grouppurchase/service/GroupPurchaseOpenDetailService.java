package com.sandu.api.grouppurchase.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.grouppurchase.bo.UserPurchaseListBO;
import com.sandu.api.grouppurchase.input.GroupPurchaseOpenQuery;
import com.sandu.api.grouppurchase.model.GroupPurchaseOpenDetail;

import java.util.List;

/**
 * @author Sandu
 */
public interface GroupPurchaseOpenDetailService {
    List<UserPurchaseListBO> query(GroupPurchaseOpenQuery query);

    List<GroupPurchaseOpenDetail> listByPurchaseOpenIds(List<Long> purchaseOpenIds);

    PageInfo<GroupPurchaseOpenDetail> getOpenDetailByOpenId(GroupPurchaseOpenQuery query);
}
