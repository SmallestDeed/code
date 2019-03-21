package com.sandu.system.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface NodeInfoBizService {
    /**
     * created by zhangchengda
     * 2018/11/8 18:07
     * 全量同步数据库数据到缓存中（异步调用）
     *
     * @return
     */
    boolean synchronizeDataToRedis();

    /**
     * created by zhangchengda
     * 2018/11/8 18:09
     * 增量同步数据到数据库中（异步调用）
     *
     * @return
     */
    boolean incrementDataToDatabase();

    /**
     * created by zhangchengda
     * 2018/11/8 18:09
     * 更新用户-节点关系状态
     * 同时会更新节点详情的计数值和用户-内容列表的数据
     * 需要计数功能和记录用户与该内容关系时调用此方法
     *
     * @param userId     用户ID
     * @param nodeId     节点ID
     * @param contentId  内容ID
     * @param nodeType   节点类型（取数据字典表type为"nodeType"的value）
     * @param detailType 详情类型（取数据字典表type为"detailType"的value）
     * @param status     状态
     * @return
     */
    Integer updateUserNodeRelStatus(Integer userId, Integer nodeId, Integer contentId, Byte nodeType, Byte detailType, Byte status);

    /**
     * created by zhangchengda
     * 2018/11/15 10:01
     * 更新节点详情数据
     * 不会更新用户-节点关系和用户-内容列表的数据
     * 只需要记录节点计数值，不需要记录用户与节点和内容关系时调用此方法
     *
     * @param userId     用户id
     * @param nodeId     节点ID
     * @param nodeType   节点类型（取数据字典表type为"nodeType"的value）
     * @param detailType 详情类型（取数据字典表type为"detailType"的value）
     * @param status     状态（为1时计数值加1，为0时计数值减1）
     * @return
     */
    Integer updateNodeInfoDetailValue(Integer userId, Integer nodeId, Byte nodeType, Byte detailType, Byte status);

    /**
     * created by zhangchengda
     * 2018/11/8 18:12
     * 根据内容集合和节点类型将节点状态封装进内容中
     *
     * @param contents 内容集合
     * @param nodeType 节点类型（取数据字典表type为"nodeType"的value）
     * @param userId   用户ID
     * @return
     */
    <T> List<T> getNodeData(List<T> contents, Byte nodeType, Integer userId);

    /**
     * created by zhangchengda
     * 2018/11/14 9:51
     * 通过内容ID获取节点数据，结果封装在map中
     *
     * @param contentId 内容ID
     * @param nodeType  节点类型（取数据字典表type为"nodeType"的value）
     * @param userId    用户ID
     * @return
     */
    Map<String, Object> getNodeData(Integer contentId, Byte nodeType, Integer userId);

    /**
     * created by zhangchengda
     * 2018/11/12 13:53
     * 注册节点，获取节点ID
     *
     * @param contentId 内容ID
     * @param nodeType  节点类型（取数据字典表type为"nodeType"的value）
     * @return
     */
    Integer registerNodeInfo(Integer contentId, Byte nodeType);

    /**
     * created by zhangchengda
     * 2018/11/12 17:52
     * 获取用户-节点状态为1的内容ID集合
     * 例：获取所有该用户点赞的方案ID集合
     *
     * @param userId     用户ID
     * @param nodeType   节点类型（取数据字典表type为"nodeType"的value）
     * @param detailType 详情类型（取数据字典表type为"detailType"的value）
     * @return
     */
    List<Integer> getContentIdList(Integer userId, Byte nodeType, Byte detailType);

    /**
     * created by zhangchengda
     * 2018/12/1 21:03
     * 校验节点是否存在
     *
     * @param contentId 内容ID
     * @param nodeType  节点类型（取数据字典表type为"nodeType"的value）
     * @return
     */
    Boolean isNodeInfoExists(Integer contentId, Byte nodeType);

    /**
     * created by zhangchengda
     * 2018/12/1 21:06
     * 校验节点详情是否存在
     *
     * @param nodeId     节点ID
     * @param detailType 详情类型（取数据字典表type为"detailType"的value）
     * @return
     */
    Boolean isNodeInfoDetailExists(Integer nodeId, Byte detailType);

    /**
     * created by zhangchengda
     * 2018/12/1 21:11
     * 校验用户节点关系是否存在
     *
     * @param userId     用户ID
     * @param nodeId     节点ID
     * @param detailType 详情类型（取数据字典表type为"detailType"的value）
     * @return
     */
    Boolean isUserNodeInfoRelExists(Integer userId, Integer nodeId, Byte detailType);
}
