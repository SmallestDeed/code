package com.sandu.api.groupproducct.service;

import com.sandu.api.groupproducct.model.GroupProductDetails;

import java.util.List;

/**
 * @author Sandu
 */
public interface GroupProductDetailsService {
    int insertList(List<GroupProductDetails> list);

    int deleteByGroupIds(List<Integer> ids);

    List<Integer> getProductIdsByGroupId(Integer id);

    boolean alterMainProduct(Integer id, Integer mainProductId);

    Integer getMainProductIdWithGroupId(Integer groupId);
}
