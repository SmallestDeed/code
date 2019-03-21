package com.sandu.system.service;

import com.sandu.node.model.NodeInfo;
import com.sandu.node.model.NodeInfoDetail;
import com.sandu.node.model.UserNodeInfoRel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface NodeDataService {
    /**
     * created by zhangchengda
     * 2018/11/8 20:52
     * 查询节点信息数据
     *
     * @param start 开始位置
     * @param size  数据条数
     * @return
     */
    List<NodeInfo> getNodeInfo(Integer start, Integer size);

    /**
     * created by zhangchengda
     * 2018/11/8 20:52
     * 查询节点信息详情数据
     *
     * @param start 开始位置
     * @param size  数据条数
     * @return
     */
    List<NodeInfoDetail> getNodeInfoDetail(Integer start, Integer size);

    /**
     * created by zhangchengda
     * 2018/11/8 20:52
     * 查询用户节点关系数据
     *
     * @param start 开始位置
     * @param size  数据条数
     * @return
     */
    List<UserNodeInfoRel> getUserNodeInfoRel(Integer start, Integer size);

    /**
     * created by zhangchengda
     * 2018/11/12 9:56
     * 根据用户ID、类型、节点ID组成的唯一索引更新数据
     *
     * @param userNodeInfoRel 更新数据对象
     */
    Integer updateUserNodeInfoRelByUnique(UserNodeInfoRel userNodeInfoRel);

    /**
     * created by zhangchengda
     * 2018/11/12 10:06
     * 根据类型、节点ID组成的唯一索引更新数据
     *
     * @param nodeInfoDetail 更新数据对象
     * @return
     */
    Integer updateNodeInfoDetailByUnique(NodeInfoDetail nodeInfoDetail);

    /**
     * created by zhangchengda
     * 2018/11/12 10:31
     * 插入用户-节点关系数据
     *
     * @param userNodeInfoRel 用户-节点数据对象
     * @return
     */
    Integer insertUserNodeInfoRel(UserNodeInfoRel userNodeInfoRel);

    /**
     * created by zhangchengda
     * 2018/11/12 10:41
     * 插入节点详情数据
     *
     * @param nodeInfoDetail 节点详情数据对象
     * @return
     */
    Integer insertNodeInfoDetail(NodeInfoDetail nodeInfoDetail);

    /**
     * created by zhangchengda
     * 2018/11/12 14:27
     * 生成节点数据
     *
     * @param nodeInfo 数据对象
     * @return
     */
    Integer insertNodeInfo(NodeInfo nodeInfo);

    /**
     * created by zhangchengda
     * 2018/11/12 18:35
     * 查询用户-内容列表数据
     *
     * @param start      起始位置
     * @param size       查询条数
     * @param nodeType   查询的节点类型
     * @param detailType 详情类型
     * @return
     */
    Map<String, String> getUserContent(Integer start, Integer size, Integer nodeType, Integer detailType);

    /**
     * created by zhangchengda
     * 2018/12/1 21:24
     * 根据动态参数获取节点数据
     *
     * @param nodeInfo 查询参数
     * @return
     */
    List<NodeInfo> getNodeInfo(NodeInfo nodeInfo);

    /**
     * created by zhangchengda
     * 2018/12/1 21:24
     * 根据动态参数获取节点详情数据
     *
     * @param nodeInfoDetail 查询参数
     * @return
     */
    List<NodeInfoDetail> getNodeInfoDetail(NodeInfoDetail nodeInfoDetail);

    /**
     * created by zhangchengda
     * 2018/12/1 21:24
     * 根据动态参数获取用户节点关系数据
     *
     * @param userNodeInfoRel 查询参数
     * @return
     */
    List<UserNodeInfoRel> getUserNodeInfoRel(UserNodeInfoRel userNodeInfoRel);

    /**
     * created by zhangchengda
     * 2018/12/19 18:51
     * 增加节点详情数据
     *
     * @param nodeId     节点ID
     * @param detailType 详情类型
     * @param num        数值
     * @return
     */
    Integer increaseNodeInfoDetailByUnique(Integer nodeId, Byte detailType, Integer num);
}
