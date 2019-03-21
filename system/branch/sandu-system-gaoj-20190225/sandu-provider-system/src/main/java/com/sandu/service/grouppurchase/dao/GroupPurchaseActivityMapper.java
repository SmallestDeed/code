package com.sandu.service.grouppurchase.dao;

import com.sandu.api.grouppurchase.input.GroupPurchaseActivityQuery;
import com.sandu.api.grouppurchase.model.GroupPurchaseActivity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupPurchaseActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupPurchaseActivity record);

    int insertSelective(GroupPurchaseActivity record);

    GroupPurchaseActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupPurchaseActivity record);

    int updateByPrimaryKey(GroupPurchaseActivity record);

    List<GroupPurchaseActivity> findAll(GroupPurchaseActivityQuery query);

    /**
     * 修改活动状态
     * @param id
     * @param activityStatus
     * @return
     */
    int updateStatus(@Param("id") String id,@Param("activityStatus") String activityStatus);

    GroupPurchaseActivity getById(@Param("id") Long id);

    List<GroupPurchaseActivity> listExpireGroupActivity();
}