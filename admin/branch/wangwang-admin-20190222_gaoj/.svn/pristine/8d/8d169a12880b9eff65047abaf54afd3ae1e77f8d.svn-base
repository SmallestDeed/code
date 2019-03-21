package com.sandu.api.platform.service;

import com.sandu.api.platform.model.PlatformProductRel;

import java.util.List;
import java.util.Map;

/**
 * @author Sandu
 */
public interface PlatformProductRelService {


    /**
     * 新增数据
     *
     * @param platformProductRel
     * @return  int
     */
    public int add(PlatformProductRel platformProductRel);

    /**
     *    更新数据
     *
     * @param platformProductRel
     * @return  int
     */
    public int update(PlatformProductRel platformProductRel);

    /**
     *    删除数据
     *
     * @param id
     * @return  int
     */
    public int delete(Integer id);

    /**
     *    获取数据详情
     *
     * @param id
     * @return  PlatformProductRel
     */
    public PlatformProductRel get(Integer id);

    /**
     * 所有数据
     *
     * @param  platformProductRel
     * @return   List<PlatformProductRel>
     */
    public List<PlatformProductRel> getList(PlatformProductRel platformProductRel);

    /**
     * 发布产品到平台
     * @param platformId	平台ID
     * @param platformProductRelList 	产品-平台集
     * 被发布的产品集合
     */
    int issueProducts(Integer platformId, List<PlatformProductRel> platformProductRelList);


    /**
     * 区分已发布与未发布产品
     * @param platformId	要发布的平台
     * @param productIds	产品id集
     * @return	{issue:[],notIssue:[]}结构的map
     */
    Map<String,List<Integer>> checkProductIssue(Integer platformId, List<Integer> productIds);

    /**
     * 插入多个产品到多个平台
     * @param platformIds
     * @param productIds
     * @return
     */
    int issueProductsToPlatformIds(List<Integer> platformIds, List<Integer> productIds);

    /**
     * 取消某个平台上的产品发布状态
     * @param productIds
     */
    void cancelIssueProducts(List<Integer> productIds);

    /**
     * 批量更新(根据productId / platformId)
     * @param platformProductRelList
     *
     */
    void updatePlatformProductRels(List<PlatformProductRel> platformProductRelList);

    /**
     * 单个更新(根据productId / platformId)
     * @param platformProductRelList
     */
    void updatePlatformProductRel(PlatformProductRel platformProductRelList);
    /**
     * 根据平台ID/产品ID查询详情
     * @param platformId
     * @param productId
     * @return
     */
    PlatformProductRel getByProductIdAndPlatform(Integer platformId, Integer productId);

    /**
     * 获取此产品发布过的所有平台
     * @param id
     * @return
     */
    List<String> getAllIssuePlatformByProductId(Integer id);

    /**
     * 获取此产品上架过的所有平台
     * @param id
     * @return
     */
    List<String> getAllPutPlatformByProductId(Integer id);

    /**
     * 校验此产品是否已在当前平台上架
     * @param platformProductRel
     * @return
     */
    int checkProductHasIssueToPlatform(PlatformProductRel platformProductRel);

    Integer getPlatformIdByProductId(long productId);

    /**
     * 根据平台ID/产品ID删除记录
     * @param ids
     * @return
     */
    int deleteByProductIds(List<Integer> ids);

    int insertRelList(List<PlatformProductRel> collect);
    
    int insertRelListIfNotExist(List<PlatformProductRel> collect);

    List<Integer> getNotExistIds(List<Integer> listRel2b);
    
	int deleteByIdsAndPlatForm(List<Integer> ids, List<String> platformIds);

}
