package com.sandu.service.groupproduct.impl;

import com.sandu.api.groupproducct.model.GroupProductDetails;
import com.sandu.api.groupproducct.service.GroupProductDetailsService;
import com.sandu.constant.Punctuation;
import com.sandu.service.groupproduct.dao.GroupProductDetailsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Sandu
 */
@Service
public class GroupProductDetailsServiceImpl implements GroupProductDetailsService {
    @Autowired
    private GroupProductDetailsDao groupProductDetailsDao;

    @Override
    public int insertList(List<GroupProductDetails> list) {
        list.forEach(groupProductDetailsDao::insertSelective);
        return 1;
    }

    @Override
    public int deleteByGroupIds(List<Integer> ids) {
        if (ids.isEmpty()) {
            return 0;
        }
        return groupProductDetailsDao.deleteByGroupIds(ids);
    }

    @Override
    public List<Integer> getProductIdsByGroupId(Integer id) {
        String productIds = groupProductDetailsDao.getProductIdsByGroupId(id);
        if (StringUtils.isBlank(productIds)) {
            return Collections.emptyList();
        }
        return Arrays.stream(productIds.split(Punctuation.COMMA))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    @Override
    public boolean alterMainProduct(Integer id, Integer mainProductId) {
        boolean flag;
        flag = groupProductDetailsDao.defaultMainProduct(id) > 0;
        flag &= groupProductDetailsDao.alterMainProduct(id, mainProductId) > 0;
        return flag;
    }

    @Override
    public Integer getMainProductIdWithGroupId(Integer groupId) {
        return groupProductDetailsDao.getMainProductIdWithGroupId(groupId);
    }
}
