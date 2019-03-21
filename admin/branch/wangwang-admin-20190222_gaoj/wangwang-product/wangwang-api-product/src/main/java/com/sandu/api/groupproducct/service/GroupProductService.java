package com.sandu.api.groupproducct.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.groupproducct.input.GroupQuery;
import com.sandu.api.groupproducct.model.GroupProduct;
import com.sandu.api.groupproducct.model.bo.GroupProductListBO;

import java.util.List;
import java.util.Map;

/**
 * @author Sandu
 */
public interface GroupProductService {
    int deleteByIds(List<Integer> ids);

    GroupProduct getInfoById(Integer id);

    PageInfo<GroupProductListBO> queryList(GroupQuery groupQuery);

    int updateGroupProduct (GroupProduct groupProduct);

    PageInfo<GroupProduct> getInfoByIds(List<Integer> notAllotIds);

    PageInfo<GroupProductListBO> query2bList(GroupQuery query);

    PageInfo<GroupProductListBO> query2cList(GroupQuery query);

    GroupProduct getInfo2bById(Integer groupId);

    GroupProduct getInfo2cById(Integer groupId);

    int addGroup(GroupProduct group);

    boolean updateGroupSecrecyByIds(List<Integer> ids, Integer secrecy);

    String getMaxId();

    Map<String,String> listGroupPutStatusByGroupIds(List<Integer> collect);
}
