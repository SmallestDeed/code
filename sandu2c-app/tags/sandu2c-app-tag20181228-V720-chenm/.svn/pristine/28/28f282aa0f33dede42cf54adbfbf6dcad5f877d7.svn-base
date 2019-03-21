package com.sandu.node.service.impl;

import com.google.gson.Gson;
import com.sandu.cache.service.RedisService;
import com.sandu.node.model.NodeInfo;
import com.sandu.node.model.NodeInfoDetail;
import com.sandu.node.model.UserNodeInfoRel;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.service.NodeDataService;
import com.sandu.system.service.NodeInfoBizService;
import com.sandu.system.service.SysDictionaryService;
import com.sandu.user.model.SysUser;
import com.sandu.user.service.SysUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sandu.node.constant.NodeInfoConstant;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Log4j2
@Service("nodeInfoBizService")
public class NodeInfoBizServiceImpl implements NodeInfoBizService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private NodeDataService nodeDataService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private SysUserService sysUserService;

    private static final String NODE_INFO_BIZ_LOG_PREFIX = "[节点业务服务]:";
    private static final Gson gson = new Gson();

    @Override
    public synchronized boolean synchronizeDataToRedis() {
        // redis锁
        String key = NodeInfoConstant.NODE_INFO_DATA_SYNCHRONIZED_THREAD_KEY;
        String value = UUID.randomUUID().toString();
        // NX：如果key已经存在则set失败
        // EX/PX：EX表示第五个参数的单位是秒，PX表示毫秒
        // 第五个参数表示key的存在时间
        if (redisService.set(key, value, "NX", "EX", 300)){
            // 同步数据
            new Thread(() -> this.syncToRedis(key, value)).start();
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean incrementDataToDatabase() {
        // redis锁
        long startTime = System.currentTimeMillis();
        String key = NodeInfoConstant.NODE_INFO_DATA_SYNCHRONIZED_THREAD_KEY;
        String value = UUID.randomUUID().toString();
        // NX：如果key已经存在则set失败
        // EX/PX：EX表示第五个参数的单位是秒，PX表示毫秒
        // 第五个参数表示key的存在时间
        if (redisService.set(key, value, "NX", "EX", 600)){
            // 同步数据
            this.syncToDatabase();
            // 释放redis锁
            if (value.equals(redisService.get(key))) {
                redisService.del(key);
            }
            log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步缓存数据到数据库完成，用时{}ms", System.currentTimeMillis() - startTime);
            return true;
        }
        return false;
    }

    @Override
    public Integer updateUserNodeRelStatus(Integer userId, Integer nodeId, Integer contentId, Byte nodeType, Byte detailType, Byte status) {
        if (userId == null || nodeId == null || contentId == null || nodeType == null || detailType == null || status == null) {
            throw new RuntimeException("缺失参数！！！请确认传参是否正确！！！");
        }
        // redis锁
        long startTime = System.currentTimeMillis();
        String key = userId + "," + nodeId + "," + detailType;
        String value = UUID.randomUUID().toString();
        while (!redisService.set(key, value, "NX", "PX", 200L)) {
            if (System.currentTimeMillis() - startTime > 250) {
                throw new RuntimeException("操作失败");
            }
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                throw new RuntimeException("操作失败", e);
            }
        }
        // 设置用户-节点状态，更新节点详情的值
        String unKey = detailType + ":" + userId + ":" + nodeId;
        String unBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_USER_NODE_INFO_REL + getBucketId(unKey, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
        String nnKey = detailType + ":" + nodeId;
        String nnBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_NODE_INFO_DETAIL + getBucketId(nnKey, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
        String ucKey = userId + ":" + nodeType + ":" + detailType;
        String ucBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_USER_CONTENT_REL + getBucketId(ucKey, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
        redisService.setHSet(unBucketKey, unKey, status + "");
        String contentIds = redisService.getHSet(ucBucketKey, ucKey);
        if (status == 1) {
            if (contentIds != null && ("," + contentIds + ",").contains("," + contentId + ",")) {
                log.info(NODE_INFO_BIZ_LOG_PREFIX + "已有该内容，contentId:{},nodeType:{},userId:{}", contentId, nodeType, userId);
            } else {
                StringBuilder newContentIds = new StringBuilder(contentId + ",");
                newContentIds.append(contentIds == null ? "" : contentIds);
                redisService.setHSet(ucBucketKey, ucKey, newContentIds.toString());
                // 计数值加1，将内容添加至用户-内容列表
                redisService.mapIncrby(nnBucketKey, nnKey, 1L);
            }
        } else {
            if (contentIds != null && ("," + contentIds + ",").contains("," + contentId + ",")) {
                String[] contentIdList = contentIds.split(",");
                StringBuilder newContentIds = new StringBuilder();
                for (int i = 0; i < contentIdList.length; i++) {
                    if (contentIdList[i] != null && !"".equals(contentIdList[i])) {
                        if (!(contentId + "").equals(contentIdList[i])) {
                            newContentIds.append(contentIdList[i] + ",");
                        }
                    }
                }
                redisService.setHSet(ucBucketKey, ucKey, newContentIds.toString());
                // 计数值减1，将内容从用户-内容列表移除
                redisService.mapIncrby(nnBucketKey, nnKey, -1L);
            } else {
                log.info(NODE_INFO_BIZ_LOG_PREFIX + "没有该内容，contentId:{},nodeType:{},userId:{}", contentId, nodeType, userId);
            }
        }
        // 添加到同步set中
        redisService.addSet(NodeInfoConstant.NODE_INFO_DATA_SYNCHRONIZED_SET, userId + ":" + nodeId);
        String num = redisService.getHSet(nnBucketKey, nnKey);
        // 释放redis锁
        if (value.equals(redisService.get(key))) {
            redisService.del(key);
        }
        if (num == null || "".equals(num)) {
            return 0;
        }
        return Integer.parseInt(num);
    }

    @Override
    public Integer updateNodeInfoDetailValue(Integer userId, Integer nodeId, Byte nodeType, Byte detailType, Byte status) {
        if (nodeId == null || nodeType == null || detailType == null || status == null || userId == null) {
            throw new RuntimeException("缺失参数！！！请确认传参是否正确！！！");
        }
        // 节点详情key和桶key
        String nnKey = detailType + ":" + nodeId;
        String nnBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_NODE_INFO_DETAIL + getBucketId(nnKey, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
        if (status == 1) {
            // 计数值加1
            redisService.mapIncrby(nnBucketKey, nnKey, 1L);
        } else if (status == 0) {
            // 计数值减1
            redisService.mapIncrby(nnBucketKey, nnKey, -1L);
        }
        // 添加到同步set中
        redisService.addSet(NodeInfoConstant.NODE_INFO_DATA_SYNCHRONIZED_SET, userId + ":" + nodeId);
        return Integer.parseInt(redisService.getHSet(nnBucketKey, nnKey));
    }

    @Override
    public Integer updateNodeInfoDetailValue(Integer userId, Integer nodeId, Byte nodeType, Byte detailType, Byte status, boolean db) {
        if (db) {
            if (status == 1) {
                nodeDataService.increaseNodeInfoDetailByUnique(nodeId, detailType, 1);
            } else if (status == 0) {
                nodeDataService.increaseNodeInfoDetailByUnique(nodeId, detailType, -1);
            }
        }
        return this.updateNodeInfoDetailValue(userId, nodeId, nodeType, detailType, status);
    }

    @Override
    public <T> List<T> getNodeData(List<T> contents, Byte nodeType, Integer userId) {
        if (contents == null || nodeType == null || userId == null) {
            throw new RuntimeException("缺失参数！！！请确认传参是否正确！！！");
        }
        // 查询所有的节点详情类型
        List<SysDictionary> sysDictionaryList = this.getSysDictionaryListByType(NodeInfoConstant.NODE_INFO_DETAIL_TYPE);
        for (T content : contents) {
            // 获取内容ID
            Class clz = content.getClass();
            Integer contentId = (Integer) this.invokeMethod("getId", clz, content);
            if (contentId == null) {
                continue;
            }
            // 获取map
            Map<String, Object> map = this.getNodeData(sysDictionaryList, nodeType, contentId, userId);
            if (map != null && map.size() > 0) {
                Set<Map.Entry<String, Object>> entrySet = map.entrySet();
                for (Map.Entry<String, Object> entry : entrySet) {
                    // 反射注入数据
                    this.invokeMethod(entry.getKey(), clz, content, entry.getValue());
                }
            }
        }
        return contents;
    }

    @Override
    public Map<String, Object> getNodeData(Integer contentId, Byte nodeType, Integer userId) {
        if (contentId == null || nodeType == null || userId == null) {
            throw new RuntimeException("缺失参数！！！请确认传参是否正确！！！");
        }
        // 查询所有的节点详情类型
        List<SysDictionary> sysDictionaryList = this.getSysDictionaryListByType(NodeInfoConstant.NODE_INFO_DETAIL_TYPE);
        return this.getNodeData(sysDictionaryList, nodeType, contentId, userId);
    }

    @Override
    public Integer registerNodeInfo(Integer contentId, Byte nodeType) {
        if (contentId == null || nodeType == null) {
            throw new RuntimeException("缺失参数！！！请确认传参是否正确！！！");
        }
        // 通过内容ID和节点类型获取节点ID
        String cnKey = nodeType + ":" + contentId;
        String cnBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_NODE_INFO + getBucketId(cnKey, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
        String nodeIdStr = redisService.getHSet(cnBucketKey, cnKey);
        if (nodeIdStr == null || "".equals(nodeIdStr)) {
            // 如果在缓存中获取不到节点ID，则在数据库中创建节点，并添加到缓存中
            NodeInfo nodeInfo = new NodeInfo();
            Date date = new Date();
            nodeInfo.setContentId(contentId);
            nodeInfo.setNodeType(nodeType);
            nodeInfo.setUuid(UUID.randomUUID().toString());
            nodeInfo.setCreator("system");
            nodeInfo.setGmtCreate(date);
            nodeInfo.setModifier("system");
            nodeInfo.setGmtModified(date);
            nodeInfo.setIsDeleted(new Byte("0"));
            nodeInfo.setRemark("自动生成节点");
            nodeDataService.insertNodeInfo(nodeInfo);
            if (nodeInfo.getId() == null) {
                throw new RuntimeException("节点ID为空");
            }
            redisService.setHSet(cnBucketKey, cnKey, nodeInfo.getId() + "");
            return nodeInfo.getId();
        } else {
            return Integer.parseInt(nodeIdStr);
        }
    }

    @Override
    public List<Integer> getContentIdList(Integer userId, Byte nodeType, Byte detailType) {
        if (userId == null || nodeType == null || detailType == null) {
            throw new RuntimeException("缺失参数！！！请确认传参是否正确！！！");
        }
        // 获取当前用户下计数的所有内容ID
        String ucKey = userId + ":" + nodeType + ":" + detailType;
        String ucBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_USER_CONTENT_REL + getBucketId(ucKey, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
        String contentIds = redisService.getHSet(ucBucketKey, ucKey);
        if (contentIds == null || "".equals(contentIds)) {
            return null;
        }
        // 将ids字段转换成List返回
        String[] contentIdList = contentIds.split(",");
        List<Integer> list = new ArrayList<>(contentIdList.length);
        for (int i = 0; i < contentIdList.length; i++) {
            String contentIdStr = contentIdList[i];
            if (contentIdStr == null || "".equals(contentIdStr)) {
                continue;
            }
            list.add(Integer.parseInt(contentIdStr));
        }
        return list;
    }

    @Override
    public Boolean isNodeInfoExists(Integer contentId, Byte nodeType) {
        // 从缓存中获取节点数据
        String cnKey = nodeType + ":" + contentId;
        String cnBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_NODE_INFO + getBucketId(cnKey, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
        String nodeIdStr = redisService.getHSet(cnBucketKey, cnKey);
        if (nodeIdStr == null || "".equals(nodeIdStr)) {
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setContentId(contentId);
            nodeInfo.setNodeType(nodeType);
            List<NodeInfo> list = nodeDataService.getNodeInfo(nodeInfo);
            if (list == null || list.size() <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean isNodeInfoDetailExists(Integer nodeId, Byte detailType) {
        // 从缓存中获取节点详情数据
        String nnKey = detailType + ":" + nodeId;
        String nnBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_NODE_INFO_DETAIL + getBucketId(nnKey, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
        String valueStr = redisService.getHSet(nnBucketKey, nnKey);
        if (valueStr == null || "".equals(valueStr)) {
            NodeInfoDetail nodeInfoDetail = new NodeInfoDetail();
            nodeInfoDetail.setNodeId(nodeId);
            nodeInfoDetail.setDetailType(detailType);
            List<NodeInfoDetail> list = nodeDataService.getNodeInfoDetail(nodeInfoDetail);
            if (list == null || list.size() <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean isUserNodeInfoRelExists(Integer userId, Integer nodeId, Byte detailType) {
        // 从缓存中获取用户-节点数据
        String unKey = detailType + ":" + userId + ":" + nodeId;
        String unBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_USER_NODE_INFO_REL + getBucketId(unKey, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
        String statusStr = redisService.getHSet(unBucketKey, unKey);
        if (statusStr == null || "".equals(statusStr)) {
            UserNodeInfoRel userNodeInfoRel = new UserNodeInfoRel();
            userNodeInfoRel.setNodeId(nodeId);
            userNodeInfoRel.setDetailType(detailType);
            userNodeInfoRel.setUserId(userId);
            List<UserNodeInfoRel> list = nodeDataService.getUserNodeInfoRel(userNodeInfoRel);
            if (list == null || list.size() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * created by zhangchengda
     * 2018/11/13 17:06
     * 同步数据库数据到缓存中
     */
    private void syncToRedis(String key, String value) {
        long startTime = System.currentTimeMillis();
        // 同步version，删除旧的key
        this.syncBaseData();
        // 同步节点数据都缓存中
        this.synchronizeNodeInfo();
        // 同步用户节点关系到缓存中
        this.synchronizeUserNodeInfoRel();
        // 同步节点详情数据到缓存中
        this.synchronizeNodeInfoDetail();
        // 同步用户-内容列表到缓存中
        this.synchronizeUserContent();
        // 释放redis锁
        if (value.equals(redisService.get(key))) {
            redisService.del(key);
        }
        log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步数据库数据到缓存完成，用时{}ms", System.currentTimeMillis() - startTime);
    }

    /**
     * created by zhangchengda
     * 2018/11/13 17:06
     * 同步缓存数据到数据库中
     */
    private void syncToDatabase() {
        if (this.isDataSecurity()) {
            // 获取同步集合
            Set<String> syncSet = redisService.getSet(NodeInfoConstant.NODE_INFO_DATA_SYNCHRONIZED_SET);
            // 循环，同步数据到数据库
            syncSet.forEach(str -> {
                // 储存的字符串为："userId,nodeId"
                String[] strs = str.split(":");
                int userId = Integer.parseInt(strs[0]);
                int nodeId = Integer.parseInt(strs[1]);
                try {
                    this.incrementDataToDatabase(userId, nodeId);
                } catch (NoSuchAlgorithmException e) {
                    log.error(NODE_INFO_BIZ_LOG_PREFIX + "更新数据到数据库失败，userId:{},nodeId:{}", userId, nodeId, e);
                }
            });
            // 删除缓存中的同步集合
            redisService.del(NodeInfoConstant.NODE_INFO_DATA_SYNCHRONIZED_SET);
        }
    }

    /**
     * created by zhangchengda
     * 2018/11/13 17:06
     * 获取内容的计数数据
     */
    private Map<String, Object> getNodeData(List<SysDictionary> sysDictionaryList, Byte nodeType, Integer contentId, Integer userId) {
        // 返回结果
        Map<String, Object> map = new HashMap<>(sysDictionaryList.size() * 2);
        for (SysDictionary sysDictionary : sysDictionaryList) {
            // 首字母大写
            Integer detailType = sysDictionary.getValue();
            StringBuilder fieldNameBuilder = new StringBuilder();
            fieldNameBuilder.append(sysDictionary.getValuekey().substring(0, 1).toUpperCase()).append(sysDictionary.getValuekey().substring(1));
            String fieldName = fieldNameBuilder.toString();
            // 通过内容ID和节点类型获取节点ID
            String cnKey = nodeType + ":" + contentId;
            String cnBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_NODE_INFO + getBucketId(cnKey, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
            String nodeIdStr = redisService.getHSet(cnBucketKey, cnKey);
            if (nodeIdStr == null) {
                return map;
            }
            Integer nodeId = Integer.parseInt(nodeIdStr);
            // 从缓存中获取用户-节点数据
            String unKey = detailType + ":" + userId + ":" + nodeId;
            String unBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_USER_NODE_INFO_REL + getBucketId(unKey, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
            String statusStr = redisService.getHSet(unBucketKey, unKey);
            if (statusStr != null && !"".equals(statusStr)) {
                map.put("setIs" + fieldName, Integer.parseInt(statusStr));
            }
            // 从缓存中获取节点详情数据
            String nnKey = detailType + ":" + nodeId;
            String nnBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_NODE_INFO_DETAIL + getBucketId(nnKey, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
            String valueStr = redisService.getHSet(nnBucketKey, nnKey);
            if (valueStr != null && !"".equals(valueStr)) {
                map.put("set" + fieldName + "Num", Integer.parseInt(valueStr));
            }
        }
        return map;
    }

    /**
     * created by zhangchengda
     * 2018/11/9 14:01
     * 同步内容-节点信息到缓存中
     * 内容-节点数据结构
     * key=(cn:bucketId) => value={ key=(nodeType:contentId) => value=nodeId }
     */
    private void synchronizeNodeInfo() {
        log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步内容-节点数据开始");
        BaseParams baseParams = new BaseParams();
        while (!baseParams.endLoop) {
            // 从数据库里查询数据
            List<NodeInfo> nodeInfoList = nodeDataService.getNodeInfo(baseParams.start, NodeInfoConstant.SYNCHRONIZE_DATA_SIZE);
            if (nodeInfoList != null && nodeInfoList.size() > 0) {
                log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步内容-节点数据,start:{},size:{}", baseParams.start, nodeInfoList.size());
                // 记录总数
                baseParams.totalNum += nodeInfoList.size();
                // 查询结果小于设置的每次数据同步条数时结束循环
                if (nodeInfoList.size() < NodeInfoConstant.SYNCHRONIZE_DATA_SIZE) {
                    baseParams.endLoop = true;
                }
                // 设置起始位置
                baseParams.start += nodeInfoList.size();
                // 桶-map
                Map<String, Map<String, String>> bucketMap = new HashMap<>();
                // 循环导入数据到缓存
                for (int i = 0; i < nodeInfoList.size(); i++) {
                    NodeInfo nodeInfo = nodeInfoList.get(i);
                    try {
                        // 设置key=nodeType:contentId
                        String key = nodeInfo.getNodeType() + ":" + nodeInfo.getContentId();
                        // 生成bucketId
                        String bucketIdKey = NodeInfoConstant.BUCKET_KEY_PREFIX_NODE_INFO + this.getBucketId(key, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
                        // 导入缓存,每1000条批量导入一次
                        this.setHSetEveryThousandData(baseParams.totalBucketMap, bucketMap, bucketIdKey, key, nodeInfo.getId() + "", i, nodeInfoList);
                        // 成功数+1
                        baseParams.successNum += 1;
                    } catch (Exception e) {
                        try {
                            if ((i + 1) % 1000 == 0 || (i == nodeInfoList.size() - 1)) {
                                redisService.setHSet(bucketMap);
                                bucketMap.clear();
                            }
                        } catch (Exception ex) {
                            log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步内容-节点数据失败，start:{},size:{}", baseParams.start, nodeInfoList.size());
                            log.error("", ex);
                        }
                    }
                }
            } else {
                baseParams.endLoop = true;
            }
        }
        long endTime = System.currentTimeMillis();
        log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步内容-节点数据结束，用时：{}ms，成功数：{}条，总数：{}条",
                endTime - baseParams.startTime, baseParams.successNum, baseParams.totalNum);
        baseParams.destroy();
    }

    /**
     * created by zhangchengda
     * 2018/12/03 10:49
     * 1.在redis中记录数据的版本号，当redis宕机重启时，通过版本号来判断redis中的数据是否安全
     * 2.删除旧的KEY
     */
    private void syncBaseData() {
        log.info(NODE_INFO_BIZ_LOG_PREFIX + "----------------开始删除旧的KEY");
        redisService.set(NodeInfoConstant.NODE_INFO_DATA_VERSION_KEY, NodeInfoConstant.NODE_INFO_DATA_VERSION_VALUE + "");
        redisService.del(NodeInfoConstant.NODE_INFO_DATA_SYNCHRONIZED_SET);
        long num = redisService.delMatch(NodeInfoConstant.BUCKET_KEY_PREFIX_NODE_INFO + "*");
        num += redisService.delMatch(NodeInfoConstant.BUCKET_KEY_PREFIX_NODE_INFO_DETAIL + "*");
        num += redisService.delMatch(NodeInfoConstant.BUCKET_KEY_PREFIX_USER_CONTENT_REL + "*");
        num += redisService.delMatch(NodeInfoConstant.BUCKET_KEY_PREFIX_USER_NODE_INFO_REL + "*");
        log.info(NODE_INFO_BIZ_LOG_PREFIX + "----------------------删除完成，删除数：{}", num);
    }

    /**
     * created by zhangchengda
     * 2018/11/9 14:57
     * 同步用户-节点信息到缓存中
     * 用户-节点数据结构
     * key=(un:bucketId) => value={ key=(detailType:userId:nodeId) => value=status }
     */
    private void synchronizeUserNodeInfoRel() {
        log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步用户-节点数据开始");
        BaseParams baseParams = new BaseParams();
        while (!baseParams.endLoop) {
            // 从数据库里查询数据
            List<UserNodeInfoRel> userNodeInfoRelList = nodeDataService.getUserNodeInfoRel(baseParams.start, NodeInfoConstant.SYNCHRONIZE_DATA_SIZE);
            if (userNodeInfoRelList != null && userNodeInfoRelList.size() > 0) {
                log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步用户-节点数据,start:{},size:{}", baseParams.start, userNodeInfoRelList.size());
                // 记录总数
                baseParams.totalNum += userNodeInfoRelList.size();
                // 查询结果小于设置的每次数据同步条数时结束循环
                if (userNodeInfoRelList.size() < NodeInfoConstant.SYNCHRONIZE_DATA_SIZE) {
                    baseParams.endLoop = true;
                }
                // 设置起始位置
                baseParams.start += userNodeInfoRelList.size();
                // 桶-map
                Map<String, Map<String, String>> bucketMap = new HashMap<>();
                // 循环导入数据到缓存
                for (int i = 0; i < userNodeInfoRelList.size(); i++) {
                    UserNodeInfoRel userNodeInfoRel = userNodeInfoRelList.get(i);
                    try {
                        // 设置key=detailType:userId:nodeId
                        String key = userNodeInfoRel.getDetailType() + ":" + userNodeInfoRel.getUserId() + ":" + userNodeInfoRel.getNodeId();
                        // 生成bucketId
                        String bucketIdKey = NodeInfoConstant.BUCKET_KEY_PREFIX_USER_NODE_INFO_REL + this.getBucketId(key, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
                        // 导入缓存,每1000条批量导入一次
                        this.setHSetEveryThousandData(baseParams.totalBucketMap, bucketMap, bucketIdKey, key, userNodeInfoRel.getStatus() + "", i, userNodeInfoRelList);
                        // 成功数+1
                        baseParams.successNum += 1;
                    } catch (Exception e) {
                        try {
                            if ((i + 1) % 1000 == 0 || (i == userNodeInfoRelList.size() - 1)) {
                                redisService.setHSet(bucketMap);
                                bucketMap.clear();
                            }
                        } catch (Exception ex) {
                            log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步用户-节点数据失败，start:{},size:{}", baseParams.start, userNodeInfoRelList.size());
                            log.error("", ex);
                        }
                    }
                }
            } else {
                baseParams.endLoop = true;
            }
        }
        long endTime = System.currentTimeMillis();
        log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步用户-节点数据结束，用时：{}ms，成功数：{}条，总数：{}条",
                endTime - baseParams.startTime, baseParams.successNum, baseParams.totalNum);
        baseParams.destroy();
    }

    /**
     * created by zhangchengda
     * 2018/11/9 15:14
     * 同步节点-节点详情信息到缓存中
     * 节点-节点详情数据结构
     * key=(nn:bucketId) => value={ key=(detailType:nodeId) => value = value }
     */
    private void synchronizeNodeInfoDetail() {
        log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步节点-节点详情数据开始");
        BaseParams baseParams = new BaseParams();
        while (!baseParams.endLoop) {
            // 从数据库里查询数据
            List<NodeInfoDetail> nodeInfoDetailList = nodeDataService.getNodeInfoDetail(baseParams.start, NodeInfoConstant.SYNCHRONIZE_DATA_SIZE);
            if (nodeInfoDetailList != null && nodeInfoDetailList.size() > 0) {
                log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步节点-节点详情数据,start:{},size:{}", baseParams.start, nodeInfoDetailList.size());
                // 记录总数
                baseParams.totalNum += nodeInfoDetailList.size();
                // 查询结果小于设置的每次数据同步条数时结束循环
                if (nodeInfoDetailList.size() < NodeInfoConstant.SYNCHRONIZE_DATA_SIZE) {
                    baseParams.endLoop = true;
                }
                // 设置起始位置
                baseParams.start += nodeInfoDetailList.size();
                // 桶-map
                Map<String, Map<String, String>> bucketMap = new HashMap<>();
                // 循环导入数据到缓存
                for (int i = 0; i < nodeInfoDetailList.size(); i++) {
                    NodeInfoDetail nodeInfoDetail = nodeInfoDetailList.get(i);
                    try {
                        // 设置key=detailType:nodeId
                        String key = nodeInfoDetail.getDetailType() + ":" + nodeInfoDetail.getNodeId();
                        // 生成bucketId
                        String bucketIdKey = NodeInfoConstant.BUCKET_KEY_PREFIX_NODE_INFO_DETAIL + this.getBucketId(key, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
                        // 导入缓存,每1000条批量导入一次
                        this.setHSetEveryThousandData(baseParams.totalBucketMap, bucketMap, bucketIdKey, key, nodeInfoDetail.getValue() + "", i, nodeInfoDetailList);
                        // 成功数+1
                        baseParams.successNum += 1;
                    } catch (Exception e) {
                        try {
                            if ((i + 1) % 1000 == 0 || (i == nodeInfoDetailList.size() - 1)) {
                                redisService.setHSet(bucketMap);
                                bucketMap.clear();
                            }
                        } catch (Exception ex) {
                            log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步节点-节点详情数据失败，start:{},size:{}", baseParams.start, nodeInfoDetailList.size());
                            log.error("", ex);
                        }
                    }
                }
            } else {
                baseParams.endLoop = true;
            }
        }
        long endTime = System.currentTimeMillis();
        log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步节点-节点详情数据结束，用时：{}ms，成功数：{}条，总数：{}条",
                endTime - baseParams.startTime, baseParams.successNum, baseParams.totalNum);
        baseParams.destroy();
    }

    /**
     * created by zhangchengda
     * 2018/11/13 10:54
     * 同步用户-内容列表数据到缓存中
     * 用户-内容列表数据结构
     * key=(uc:bucketId) => value={ key=(userId:nodeType:detailType) => value = contentIds }
     */
    private void synchronizeUserContent() {
        log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步用户-内容列表数据开始");
        BaseParams baseParams = new BaseParams();
        // 查询所有的节点类型
        List<SysDictionary> nodeInfoDictList = this.getSysDictionaryListByType(NodeInfoConstant.NODE_INFO_TYPE);
        // 查询所有的节点类型
        List<SysDictionary> nodeInfoDetailDictList = this.getSysDictionaryListByType(NodeInfoConstant.NODE_INFO_DETAIL_TYPE);

        for (SysDictionary nodeInfoDict : nodeInfoDictList) {
            for (SysDictionary nodeInfoDetailDict : nodeInfoDetailDictList) {
                baseParams.endLoop = false;
                baseParams.start = 0;
                while (!baseParams.endLoop) {
                    int nodeType = nodeInfoDict.getValue();
                    int detailType = nodeInfoDetailDict.getValue();
                    // 从数据库里查询数据
                    Map<String, String> userContentList = nodeDataService.getUserContent(baseParams.start, NodeInfoConstant.SYNCHRONIZE_DATA_SIZE, nodeType, detailType);
                    if (userContentList != null && userContentList.size() > 0) {
                        log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步用户-内容列表数据,start:{},size:{},nodeType:{},detailType:{}",
                                baseParams.start, userContentList.size(), nodeType, detailType);
                        // 记录总数
                        baseParams.totalNum += userContentList.size();
                        // 查询结果小于设置的每次数据同步条数时结束循环
                        if (userContentList.size() < NodeInfoConstant.SYNCHRONIZE_DATA_SIZE) {
                            baseParams.endLoop = true;
                        }
                        // 设置起始位置
                        baseParams.start += userContentList.size();
                        // 桶-map
                        Map<String, Map<String, String>> bucketMap = new HashMap<>();
                        // 循环导入数据到缓存
                        Set<Map.Entry<String, String>> entrySet = userContentList.entrySet();
                        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
                        int i = 0;
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> entry = iterator.next();
                            try {
                                i++;
                                String userId = entry.getKey();
                                String contentIds = entry.getValue();
                                // 设置key=userId:nodeType:detailType
                                String key = userId + ":" + nodeType + ":" + detailType;
                                // 生成bucketId
                                String bucketIdKey = NodeInfoConstant.BUCKET_KEY_PREFIX_USER_CONTENT_REL + this.getBucketId(key, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);
                                // 导入缓存,每1000条批量导入一次
                                this.setHSetEveryThousandData(baseParams.totalBucketMap, bucketMap, bucketIdKey, key, contentIds, i, iterator);
                                // 成功数+1
                                baseParams.successNum += 1;
                            } catch (Exception e) {
                                try {
                                    if (i % 1000 == 0 || iterator.hasNext() == false) {
                                        redisService.setHSet(bucketMap);
                                        bucketMap.clear();
                                    }
                                } catch (Exception ex) {
                                    log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步用户-内容列表数据失败，start:{},size:{}", baseParams.start, userContentList.size());
                                    log.error("", ex);
                                }
                            }
                        }
                    } else {
                        baseParams.endLoop = true;
                    }
                }
            }
        }
        long endTime = System.currentTimeMillis();
        log.info(NODE_INFO_BIZ_LOG_PREFIX + "同步用户-内容列表数据结束，用时：{}ms，成功数：{}条，总数：{}条",
                endTime - baseParams.startTime, baseParams.successNum, baseParams.totalNum);
        baseParams.destroy();
    }

    /**
     * created by zhangchengda
     * 2018/11/9 11:28
     * 将Key转换为特定长度的BucketId
     *
     * @param key 键
     * @param bit 位数，例：假如大约有1亿数据，最接近1亿的是2的27次方，则bit=26
     * @return
     */
    private byte[] getBucketId(byte[] key, int bit) throws NoSuchAlgorithmException {
        // 获取MD5散列值
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        mdInst.update(key);
        byte[] md5 = mdInst.digest();
        // byte数组存放BucketId
        byte[] bucketId = new byte[(bit - 1) / 7 + 1];
        // 01111111111...11111110b
        int a = (int) Math.pow(2, bit % 7) - 2;
        md5[bucketId.length - 1] = (byte) (md5[bucketId.length - 1] & a);
        System.arraycopy(md5, 0, bucketId, 0, bucketId.length);
        // 将非ASCII码转换为ASCII码(ASCII码在0~127之间）
        for (int i = 0; i < bucketId.length; i++) {
            if (bucketId[i] < 0) {
                bucketId[i] &= 127;
            }
        }
        return bucketId;
    }

    /**
     * created by zhangchengda
     * 2018/11/12 11:52
     * 将Key转换为特定长度的BucketId
     *
     * @param key 键
     * @param bit 位数，例：假如大约有1亿数据，最接近1亿的是2的27次方，则bit=26
     * @return
     */
    private String getBucketId(String key, int bit) {
        try {
            return new String(this.getBucketId(key.getBytes(), bit));
        } catch (NoSuchAlgorithmException e) {
            log.error(NODE_INFO_BIZ_LOG_PREFIX + "获取BucketId异常! key:{},bit:{}", key, bit, e);
            throw new RuntimeException("获取BucketId异常!");
        }
    }

    /**
     * created by zhangchengda
     * 2018/11/9 18:37
     * 根据用户ID和节点ID更新数据到数据库中
     *
     * @param userId 用户ID
     * @param nodeId 节点ID
     * @throws NoSuchAlgorithmException
     */
    private void incrementDataToDatabase(int userId, int nodeId) throws NoSuchAlgorithmException {
        // 查询所有的节点详情类型
        SysDictionary sysDictionaryQuery = new SysDictionary();
        sysDictionaryQuery.setType(NodeInfoConstant.NODE_INFO_DETAIL_TYPE);
        List<SysDictionary> sysDictionaryList = sysDictionaryService.getList(sysDictionaryQuery);
        // 查询用户
        SysUser user = sysUserService.get(userId);
        // 更新数据，更新两张表：user_node_info_rel 和 node_info_detail
        for (SysDictionary sysDictionary : sysDictionaryList) {
            Integer detailType = sysDictionary.getValue();
            // 更新user_node_info_rel表
            if(user != null) {
                this.updateUserNodeInfoRel(detailType, user, nodeId);
            }
            // 更新node_info_detail表
            this.updateNodeInfoDetail(detailType, nodeId);
        }
    }

    /**
     * created by zhangchengda
     * 2018/11/12 15:16
     * 用过反射调用方法
     *
     * @param methodName 方法名
     * @param clz        类
     * @param object     实例
     * @param params     方法参数
     * @param <T>
     * @return
     */
    private <T> Object invokeMethod(String methodName, Class<T> clz, Object object, Object... params) {
        try {
            Method method;
            if (params != null && params.length > 0) {
                Class[] classes = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    classes[i] = params[i].getClass();
                }
                method = clz.getMethod(methodName, classes);
                return method.invoke(object, params);
            } else {
                method = clz.getMethod(methodName);
                return method.invoke(object, params);
            }
        } catch (NoSuchMethodException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("方法调用异常!");
        }
    }

    /**
     * created by zhangchengda
     * 2018/11/12 15:54
     * 更新用户-节点关联数据
     *
     * @param detailType 类型（点赞、收藏等，数据字典里的value）
     * @param user       用户
     * @param nodeId     节点ID
     * @return
     * @throws NoSuchAlgorithmException
     */
    private int updateUserNodeInfoRel(Integer detailType, SysUser user, Integer nodeId) throws NoSuchAlgorithmException {
        Integer userId = user.getId();
        // 从缓存中获取用户-节点数据
        String unKey = detailType + ":" + userId + ":" + nodeId;
        String unBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_USER_NODE_INFO_REL +
                new String(getBucketId(unKey.getBytes(), NodeInfoConstant.BUCKET_KEY_BIT_LENGTH));
        String statusStr = redisService.getHSet(unBucketKey, unKey);
        // 向数据库中更新or插入数据
        if (statusStr != null && !"".equals(statusStr)) {
            Integer status = Integer.parseInt(statusStr);
            UserNodeInfoRel userNodeInfoRel = new UserNodeInfoRel();
            userNodeInfoRel.setUserId(userId);
            userNodeInfoRel.setDetailType(detailType.byteValue());
            userNodeInfoRel.setNodeId(nodeId);
            userNodeInfoRel.setStatus(status.byteValue());
            if (user != null) {
                userNodeInfoRel.setModifier(user.getUserName() == null ? user.getNickName() : user.getUserName());
                userNodeInfoRel.setGmtModified(new Date());
            }
            int successNum = nodeDataService.updateUserNodeInfoRelByUnique(userNodeInfoRel);
            if (successNum <= 0) {
                userNodeInfoRel.setUuid(UUID.randomUUID().toString());
                userNodeInfoRel.setCreator(userNodeInfoRel.getModifier());
                userNodeInfoRel.setGmtCreate(userNodeInfoRel.getGmtModified());
                userNodeInfoRel.setIsDeleted(new Byte("0"));
                successNum = nodeDataService.insertUserNodeInfoRel(userNodeInfoRel);
                if (successNum <= 0) {
                    log.info(NODE_INFO_BIZ_LOG_PREFIX + "插入用户-节点数据失败，userNodeInfoRel:{}", gson.toJson(userNodeInfoRel));
                }
            }
            return successNum;
        } else {
            log.info(NODE_INFO_BIZ_LOG_PREFIX + "更新失败，statusStr为空！userId:{}, detailType:{}, nodeId:{}", userId, detailType, nodeId);
            return 0;
        }
    }

    /**
     * created by zhangchengda
     * 2018/11/12 15:55
     * 更新节点详情关联数据
     *
     * @param detailType 类型（点赞、收藏等，数据字典里的value）
     * @param nodeId     节点ID
     * @return
     * @throws NoSuchAlgorithmException
     */
    private int updateNodeInfoDetail(Integer detailType, Integer nodeId) throws NoSuchAlgorithmException {
        // 从缓存中获取节点详情数据
        String nnKey = detailType + ":" + nodeId;
        String nnBucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_NODE_INFO_DETAIL +
                new String(getBucketId(nnKey.getBytes(), NodeInfoConstant.BUCKET_KEY_BIT_LENGTH));
        String valueStr = redisService.getHSet(nnBucketKey, nnKey);
        // 向数据库中更新or插入数据
        if (valueStr != null && !"".equals(valueStr)) {
            Integer value = Integer.parseInt(valueStr);
            NodeInfoDetail nodeInfoDetail = new NodeInfoDetail();
            nodeInfoDetail.setNodeId(nodeId);
            nodeInfoDetail.setDetailType(detailType.byteValue());
            nodeInfoDetail.setValue(value);
            nodeInfoDetail.setModifier("system");
            nodeInfoDetail.setGmtModified(new Date());
            int successNum = nodeDataService.updateNodeInfoDetailByUnique(nodeInfoDetail);
            if (successNum <= 0) {
                nodeInfoDetail.setUuid(UUID.randomUUID().toString());
                nodeInfoDetail.setCreator("system");
                nodeInfoDetail.setGmtCreate(nodeInfoDetail.getGmtModified());
                nodeInfoDetail.setIsDeleted(new Byte("0"));
                nodeInfoDetail.setRemark("自动同步缓存数据到数据库");
                successNum = nodeDataService.insertNodeInfoDetail(nodeInfoDetail);
                if (successNum <= 0) {
                    log.info(NODE_INFO_BIZ_LOG_PREFIX + "插入用户详情数据失败，nodeInfoDetail:{}", gson.toJson(nodeInfoDetail));
                }
            }
            return successNum;
        } else {
            log.info(NODE_INFO_BIZ_LOG_PREFIX + "更新失败，valueStr为空！detailType:{}, nodeId:{}", detailType, nodeId);
            return 0;
        }
    }

    /**
     * created by zhangchengda
     * 2018/11/13 10:22
     * 通过type查询数据字典集合
     *
     * @param type 类型
     * @return
     */
    private List<SysDictionary> getSysDictionaryListByType(String type) {
        SysDictionary sysDictionaryQuery = new SysDictionary();
        sysDictionaryQuery.setType(type);
        List<SysDictionary> sysDictionaryList = sysDictionaryService.getList(sysDictionaryQuery);
        return sysDictionaryList;
    }

    /**
     * created by zhangchengda
     * 2018/11/13 14:18
     * 装填桶数据
     *
     * @param totalBucketMap 所有的桶数据
     * @param bucketMap      当前的5000条桶数据
     * @param bucketIdKey    桶ID
     * @param key            键
     * @param value          值
     * @return
     */
    private Map<String, Map<String, String>> getBucketMap(Map<String, Map<String, String>> totalBucketMap,
                                                          Map<String, Map<String, String>> bucketMap,
                                                          String bucketIdKey,
                                                          String key,
                                                          String value) {
        if (totalBucketMap.containsKey(bucketIdKey)) {
            bucketMap.put(bucketIdKey, totalBucketMap.get(bucketIdKey));
        }
        Map<String, String> kvMap = bucketMap.get(bucketIdKey);
        if (kvMap == null) {
            kvMap = new HashMap<>();
        }
        kvMap.put(key, value);
        bucketMap.put(bucketIdKey, kvMap);
        totalBucketMap.put(bucketIdKey, kvMap);
        return bucketMap;
    }

    /**
     * created by zhangchengda
     * 2018/11/13 14:18
     * 导入缓存,每1000条批量导入一次
     *
     * @param totalBucketMap 所有的桶数据
     * @param bucketMap      当前的5000条桶数据
     * @param bucketIdKey    桶ID
     * @param key            键
     * @param value          值
     * @param i              当前的迭代的指针位置
     * @param iterator       迭代器
     * @return
     */
    private void setHSetEveryThousandData(Map<String, Map<String, String>> totalBucketMap,
                                          Map<String, Map<String, String>> bucketMap,
                                          String bucketIdKey,
                                          String key,
                                          String value,
                                          int i,
                                          Iterator<Map.Entry<String, String>> iterator) {
        bucketMap = this.getBucketMap(totalBucketMap, bucketMap, bucketIdKey, key, value);
        if (i % 1000 == 0 || iterator.hasNext() == false) {
            redisService.setHSet(bucketMap);
            bucketMap.clear();
        }
    }

    /**
     * created by zhangchengda
     * 2018/11/13 14:18
     * 导入缓存,每1000条批量导入一次
     *
     * @param totalBucketMap 所有的桶数据
     * @param bucketMap      当前的5000条桶数据
     * @param bucketIdKey    桶ID
     * @param key            键
     * @param value          值
     * @param i              当前的迭代的指针位置
     * @param list           当前数据集合
     * @return
     */
    private <T> void setHSetEveryThousandData(Map<String, Map<String, String>> totalBucketMap,
                                              Map<String, Map<String, String>> bucketMap,
                                              String bucketIdKey,
                                              String key,
                                              String value,
                                              int i,
                                              List<T> list) {
        bucketMap = this.getBucketMap(totalBucketMap, bucketMap, bucketIdKey, key, value);
        if ((i + 1) % 1000 == 0 || (i == list.size() - 1)) {
            redisService.setHSet(bucketMap);
            bucketMap.clear();
        }
    }

    /**
     * created by zhangchengda
     * 2018/12/03 10:54
     * 判断redis中的数据是否安全
     */
    private boolean isDataSecurity() {
        String versionStr = redisService.get(NodeInfoConstant.NODE_INFO_DATA_VERSION_KEY);
        if (versionStr == null || "".equals(versionStr)) {
            return false;
        }
        try {
            int version = Integer.parseInt(versionStr);
            if (version != NodeInfoConstant.NODE_INFO_DATA_VERSION_VALUE) {
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error(NODE_INFO_BIZ_LOG_PREFIX + "同步节点数据到数据库异常!!!");
            return false;
        }
    }

    /**
     * 基础数据
     */
    private class BaseParams {
        // 同步用户-内容列表数据开始时间
        long startTime = System.currentTimeMillis();
        // 数据库查询起始位置
        int start = 0;
        // 是否结束循环标识
        boolean endLoop = false;
        // 成功数
        int successNum = 0;
        // 总数
        int totalNum = 0;
        // 所有桶-map
        Map<String, Map<String, String>> totalBucketMap = new HashMap<>();

        private BaseParams() {
        }

        private void destroy() {
            totalBucketMap = null;
        }
    }
}
