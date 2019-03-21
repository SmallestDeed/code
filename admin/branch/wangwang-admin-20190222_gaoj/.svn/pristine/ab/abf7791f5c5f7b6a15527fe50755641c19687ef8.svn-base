package com.sandu.api.platform.service;

import com.sandu.api.groupproducct.model.GroupProduct;
import com.sandu.api.platform.model.PlatformGroupRel;

import java.util.List;

/**
 * @author Sandu
 */
public interface PlatformGroupRelService {
    /**
     * 插入组合-平台中间记录
     * @param rels
     * @return
     */
    int insertRels(List<PlatformGroupRel> rels);

    /**
     * 取消分配删除记录/上下架为更新
     *
     * @param rels
     * @return
     */
    int updateSelectedRelsByGroupId(List<PlatformGroupRel> rels);

    /**
     * 删除组合根据组合IDs
     * @param groupIds
     * @return
     */
    int deleteByGroupIds(List<Integer> groupIds);

    /**
     * 获取未分配的组合ID
     *
     * @param groupIds
     * @return
     */
    List<Integer> getNotAllotGroupIds(List<Integer> groupIds);

    /**
     * 根据组合ID获取组合详情
     * @param groupId
     * @return
     */
    GroupProduct getInfoById(Integer groupId);
}
