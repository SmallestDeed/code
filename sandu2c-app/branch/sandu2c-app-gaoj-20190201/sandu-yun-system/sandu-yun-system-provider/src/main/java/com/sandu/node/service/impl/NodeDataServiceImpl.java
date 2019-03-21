package com.sandu.node.service.impl;

import com.sandu.node.dao.NodeInfoDetailMapper;
import com.sandu.node.dao.NodeInfoMapper;
import com.sandu.node.dao.UserNodeInfoRelMapper;
import com.sandu.node.model.NodeInfo;
import com.sandu.node.model.NodeInfoDetail;
import com.sandu.node.model.UserNodeInfoRel;
import com.sandu.system.service.NodeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("nodeDataService")
public class NodeDataServiceImpl implements NodeDataService {
    @Autowired
    private NodeInfoMapper nodeInfoMapper;
    @Autowired
    private NodeInfoDetailMapper nodeInfoDetailMapper;
    @Autowired
    private UserNodeInfoRelMapper userNodeInfoRelMapper;

    @Override
    public List<NodeInfo> getNodeInfo(Integer start, Integer size) {
        return nodeInfoMapper.getNodeInfo(start, size);
    }

    @Override
    public List<NodeInfoDetail> getNodeInfoDetail(Integer start, Integer size) {
        return nodeInfoDetailMapper.getNodeInfoDetail(start, size);
    }

    @Override
    public List<UserNodeInfoRel> getUserNodeInfoRel(Integer start, Integer size) {
        return userNodeInfoRelMapper.getUserNodeInfoRel(start, size);
    }

    @Override
    public Integer updateUserNodeInfoRelByUnique(UserNodeInfoRel userNodeInfoRel) {
        return userNodeInfoRelMapper.updateUserNodeInfoRelByUnique(userNodeInfoRel);
    }

    @Override
    public Integer updateNodeInfoDetailByUnique(NodeInfoDetail nodeInfoDetail) {
        return nodeInfoDetailMapper.updateNodeInfoDetailByUnique(nodeInfoDetail);
    }

    @Override
    public Integer insertUserNodeInfoRel(UserNodeInfoRel userNodeInfoRel) {
        return userNodeInfoRelMapper.insertSelective(userNodeInfoRel);
    }

    @Override
    public Integer insertNodeInfoDetail(NodeInfoDetail nodeInfoDetail) {
        return nodeInfoDetailMapper.insertSelective(nodeInfoDetail);
    }

    @Override
    public Integer insertNodeInfo(NodeInfo nodeInfo) {
        return nodeInfoMapper.insertSelective(nodeInfo);
    }

    @Override
    public Map<String, String> getUserContent(Integer start, Integer size, Integer nodeType, Integer detailType) {
        List<Map<String, Object>> list = userNodeInfoRelMapper.getUserContent(start, size, nodeType, detailType);
        Map<String, String> map = new HashMap<>(list.size());
        for (Map<String, Object> stringStringMap : list) {
            map.put(stringStringMap.get("user_id").toString(), stringStringMap.get("content_ids").toString());
        }
        return map;
    }

    @Override
    public List<NodeInfo> getNodeInfo(NodeInfo nodeInfo) {
        return nodeInfoMapper.getNodeInfoSelective(nodeInfo);
    }

    @Override
    public List<NodeInfoDetail> getNodeInfoDetail(NodeInfoDetail nodeInfoDetail) {
        return nodeInfoDetailMapper.getNodeInfoDetailSelective(nodeInfoDetail);
    }

    @Override
    public List<UserNodeInfoRel> getUserNodeInfoRel(UserNodeInfoRel userNodeInfoRel) {
        return userNodeInfoRelMapper.getUserNodeInfoRelSelective(userNodeInfoRel);
    }

    @Override
    public Integer increaseNodeInfoDetailByUnique(Integer nodeId, Byte detailType, Integer num) {
        return nodeInfoDetailMapper.increaseNodeInfoDetailByUnique(nodeId, detailType, num);
    }
}
