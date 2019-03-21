package com.nork.system.service.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.WebSocketUtils;
import com.nork.system.service.SysUserService;
import com.nork.system.websocket.obj.WebSocketClient;

/**
 * 检测配置服务器是否链接正常
 * Created by Administrator on 2017/3/21.
 */
@Service("validateServerConnectionService")
public class ValidateServerConnectionServiceImpl {

    Logger logger = Logger.getLogger(ValidateServerConnectionServiceImpl.class);

    private static String validateServerIpStr = Utils.getPropertyName("app","validate.server.ip","");

    private Session serverSession = null;

    @Autowired
    private SysUserService sysUserService;

    public void init(){
        if( serverSession == null ){
            WebSocketContainer container= ContainerProvider.getWebSocketContainer();
            if(container!=null) {
                try {
                    String localAddress = InetAddress.getLocalHost().getHostAddress();
                    String url = WebSocketUtils.webSocket.getString("app.webSocket.validateConnection");
                    serverSession = container.connectToServer(WebSocketClient.class, URI.create(url+localAddress));
                } catch (DeploymentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 检测配置服务器是否链接正常
     */
    public void validateServerConnection(){
        init();
        if(StringUtils.isNotBlank(validateServerIpStr) ){
            String[] validateServerIpArr = validateServerIpStr.split(",");
            if( validateServerIpArr != null && validateServerIpArr.length > 0 ){
                for( String validateServerIp : validateServerIpArr ){
                    try {
                        JSONObject contentJson = new JSONObject();
                        contentJson.accumulate("targetId",validateServerIp);
                        contentJson.accumulate("content","1");
                        serverSession.getBasicRemote().sendText(contentJson.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
