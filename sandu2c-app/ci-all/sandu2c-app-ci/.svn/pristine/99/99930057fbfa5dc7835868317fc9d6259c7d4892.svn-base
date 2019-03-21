package com.sandu.web.system.controller;

import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.system.service.NodeInfoBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/miniprogram/system/nodeInfo")
public class NodeInfoController {
    @Autowired
    private NodeInfoBizService nodeInfoBizService;

    @RequestMapping("/setStatus")
    public ResponseEnvelope setStatus(@RequestParam("contentId") Integer contentId,
                                      @RequestParam("nodeType") Byte nodeType,
                                      @RequestParam("detailType") Byte detailType,
                                      @RequestParam("status") Byte status){
        // 校验用户登录
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null){
            return new ResponseEnvelope(false, "请登录!");
        }
        Integer userId = loginUser.getId();
        // 获取节点ID
        Integer nodeId = nodeInfoBizService.registerNodeInfo(contentId, nodeType);
        // 处理操作
        Integer num = nodeInfoBizService.updateUserNodeRelStatus(userId, nodeId, contentId, nodeType, detailType, status);
        return new ResponseEnvelope(true, "操作成功!", num);
    }

    @RequestMapping("/syncNodeData")
    public ResponseEnvelope syncNodeData(){
        boolean flag = nodeInfoBizService.synchronizeDataToRedis();
        if (flag){
            return new ResponseEnvelope(true, "节点数据正在同步，请稍等，详情请看日志!");
        }else {
            return new ResponseEnvelope(true, "正在同步节点数据，请勿重复调用!");
        }
    }

    @RequestMapping("/syncToDatabase")
    public ResponseEnvelope syncToDatabase(){
        boolean flag = nodeInfoBizService.incrementDataToDatabase();
        if (flag){
            return new ResponseEnvelope(true, "正在同步数据到数据库，请稍等，详情请看日志!");
        }else {
            return new ResponseEnvelope(true, "正在同步数据到数据库，请勿重复调用!");
        }
    }
}
