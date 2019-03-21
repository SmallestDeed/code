package com.sandu.api.groupproducct.service.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.groupproducct.input.GroupProductManagerUpdate;
import com.sandu.api.groupproducct.input.GroupPutUpdate;
import com.sandu.api.groupproducct.input.GroupQuery;
import com.sandu.api.groupproducct.model.GroupProduct;
import com.sandu.api.groupproducct.model.bo.GroupProductInfoBO;
import com.sandu.api.groupproducct.model.bo.GroupProductListBO;
import com.sandu.api.groupproducct.model.po.GroupProductUpdatePO;

import java.util.List;

/**
 * @author Sandu
 */
public interface GroupProductBizService {
    /**
     * 产品组合查询
     *
     * @param query
     * @return
     */
    PageInfo<GroupProductListBO> queryGroup(GroupQuery query, String queryType);

    /**
     * 组合分配/取消分配
     *
     * @param groupIds
     * @param allotTo  分配到的平台(channel/online)
     * @return
     */
    boolean allot(List<Integer> groupIds, String allotTo);

    /**
     * 上架
     *
     * @param groupIds
     * @param upToPlatformIds 线上上架到的平台ID
     * @param platformType    操作的类型,渠道上架/线上上架 (channel/online)
     * @return
     */
    boolean putUp(List<Integer> groupIds, String upToPlatformIds, String platformType);

    /**
     * 下架
     *
     * @param groupIds
     * @param downToPlatformIds 线上下架的平台ID
     * @param platformType
     * @return
     */
    boolean putDown(List<Integer> groupIds, String downToPlatformIds, String platformType);

    /**
     * 产品组合详情
     *
     * @param groupId
     * @param type 查询类型:库/渠道/线上
     * @return
     */
    GroupProductInfoBO getGroupInfo(Integer groupId,String type);

    /**
     *  更新组合
     * @param update
     * @return
     */
    boolean updateGroup(GroupProductUpdatePO update);

    Integer addGroup(GroupProduct group, List<Integer> productIds, Integer mainProduct);

    int deleteGroupByIds(List<Integer> ids);

    boolean updateGroupSecrecyByIds(List<Integer> ids, Integer secrecy);

    Boolean putGroupInLibrary(GroupPutUpdate put);
}
