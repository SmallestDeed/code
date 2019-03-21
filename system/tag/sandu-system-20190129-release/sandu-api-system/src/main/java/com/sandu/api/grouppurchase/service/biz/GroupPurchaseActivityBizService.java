package com.sandu.api.grouppurchase.service.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.grouppurchase.input.GroupPurchaseActivityAdd;
import com.sandu.api.grouppurchase.input.GroupPurchaseActivityQuery;
import com.sandu.api.grouppurchase.input.GroupPurchaseActivityUpdate;
import com.sandu.api.grouppurchase.model.GroupPurchaseActivity;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 16:15 2018/12/6
 */
public interface GroupPurchaseActivityBizService {

    /**
     * 新增活动
     * @param input
     * @return
     */
    int create(GroupPurchaseActivityAdd input);

    /**
     * 查询活动列表
     * @param query
     * @return
     */
    PageInfo<GroupPurchaseActivity> query(GroupPurchaseActivityQuery query);

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
     * 获取详情
     * @param id
     * @return
     */
    GroupPurchaseActivity getByActivityId(int id);

    /**
     * 修改活动
     * @param update
     * @return
     */
    int updateActivity(GroupPurchaseActivityUpdate update);
}
