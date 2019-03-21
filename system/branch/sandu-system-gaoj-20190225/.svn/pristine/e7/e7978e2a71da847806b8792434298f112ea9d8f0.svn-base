package com.sandu.service.grouppurchase.dao;


import com.sandu.api.grouppurchase.input.GroupPurchaseOpenQuery;
import com.sandu.api.grouppurchase.model.GroupPurchaseOpen;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupPurchaseOpenMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupPurchaseOpen record);

    int insertSelective(GroupPurchaseOpen record);

    GroupPurchaseOpen selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupPurchaseOpen record);

    int updateByPrimaryKey(GroupPurchaseOpen record);

    List<GroupPurchaseOpen> query(GroupPurchaseOpenQuery query);

    List<GroupPurchaseOpen> listExpirePurchaseOpenWithActivityIds(@Param("collect") List<Long> collect);
}