/**    
 * 文件名：MqttApi.java    
 *    
 * 版本信息：    
 * 日期：2017-7-5    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.nork.common.mqtt;

import java.util.Properties;
import java.util.UUID;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.nork.common.util.Tools;

/**
 * 
 * 
 * 项目名称：timeSpace 类名称：MqttApi 类描述： 创建人：Timy.Liu 创建时间：2017-7-5 下午7:46:52
 * 修改人：Timy.Liu 修改时间：2017-7-5 下午7:46:52 修改备注：
 * 
 * @version
 * 
 */
public class MqttApi {
    private static final String GROUPID = "GID_U3d";

    private static final String TOPIC = "rendermsg";

    private static final String ADDR = "http://onsaddr-internal.aliyun.com:8080/rocketmq/nsaddr4client-internal";

    private static final String SECRETKEY = "28j00lvXqG5nKwSHcijVIbEiCdAKDC";

    private static final String ACCESSKEY = "LTAIwhr3rO6Dywl2";

    private static final String PRODUCERID = "PID_app_server";

    private static final String TIMEOUT = "3000";

    private static final String PAYORDER = "payOrder";

    private static final String RENDER = "render";

    private static final String MESSAGE = "message";

    private static Producer producer = null;
    static {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, PRODUCERID);
        properties.put(PropertyKeyConst.AccessKey, ACCESSKEY);
        properties.put(PropertyKeyConst.SecretKey, SECRETKEY);
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, TIMEOUT);
        properties.put(PropertyKeyConst.ONSAddr, ADDR);
        producer = ONSFactory.createProducer(properties);
        producer.start();
    }

    /**
     * 
     * 
     * sendRenderMsg(这里用一句话描述这个方法的作用)
     * 
     * @param @param msgContent
     * @param @param appAlias
     * 
     * @return void 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public static void sendRenderMsg(String msgContent, String appAlias) {
        sendMsg(msgContent, appAlias, RENDER);
    }

    /**
     * 
     * 
     * sendcommMsg(这里用一句话描述这个方法的作用)
     * 
     * @param @param msgContent
     * @param @param appAlias
     * 
     * @return void 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public static void sendcommMsg(String msgContent, String appAlias) {
        sendMsg(msgContent, appAlias, MESSAGE);
    }

    /**
     * 
     * 
     * sendPayMsg(这里用一句话描述这个方法的作用)
     * 
     * @param @param msgContent
     * @param @param appAlias
     * 
     * @return void 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public static void sendPayMsg(String msgContent, String appAlias) {
        sendMsg(msgContent, appAlias, PAYORDER);
    }

    /**
     * 
     * 
     * sendMsg(这里用一句话描述这个方法的作用)
     * 
     * @param @param msgContent
     * @param @param appAlias
     * @param @param messageTag
     * 
     * @return void 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    private static void sendMsg(String msgContent, String appAlias, String messageTag) {

        final Message msg = new Message(TOPIC, messageTag, msgContent.getBytes());
        msg.putUserProperties("mqttSecondTopic", "/p2p/" + getClientID(appAlias));
        msg.setKey(UUID.randomUUID().toString());
        
        Tools.fixExecutorService.execute(new Runnable() {
            
            @Override
            public void run() {
                producer.sendAsync(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        // 消费发送成功
                        //System.out.println("send message success. topic=" + sendResult.getTopic() + ", msgId="
                    }
                    
                    @Override
                    public void onException(OnExceptionContext context) {
                        // 消息发送失败--能否补发
                        //System.out.println("send message failed. topic=" + context.getTopic() + ", msgId="
                    }
                });
                // 在callback返回之前即可取得msgId。
                //System.out.println("send message async. topic=" + msg.getTopic() + ", msgId=" + msg.getMsgID()+" ,key="+msg.getKey());
            }
        });
    }

    /**
     * 
     * 
     * getClientID(这里用一句话描述这个方法的作用)
     * 
     * @param @param appId
     * @param @return
     * 
     * @return String 返回类型
     * 
     * @Exception 异常对象
     * 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    private static String getClientID(String appId) {
        return GROUPID + "@@@" + appId;
    }
  
}
