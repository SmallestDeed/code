package com.sandu.api.grouppurchase.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.grouppurchase.input.GroupPurchaseActivityQuery;
import com.sandu.api.grouppurchase.model.GroupPurchaseActivity;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 16:09 2018/12/6
 */
public interface GroupPurchaseActivityService {
    /**
     * 新增活动
     * @param activity
     * @return
     */
    int create(GroupPurchaseActivity activity);

    /**
     * 查询活动列表
     * @param query
     * @return
     */
    PageInfo<GroupPurchaseActivity> findAll(GroupPurchaseActivityQuery query);

    /**
     * 修改活动状态
     * @param id
     * @param activityStatus
     * @return
     */
    int updateStatus(String id, String activityStatus);

    /**
     * 删除活动
     * @param id
     * @return
     */
    int deleteActivity(String id);

    /**
     * 获取活动详情
     * @param id
     * @return
     */
    GroupPurchaseActivity getByActivityId(int id);

    /**
     * 修改活动
     * @param activity
     * @return
     */
    int update(GroupPurchaseActivity activity);
}
