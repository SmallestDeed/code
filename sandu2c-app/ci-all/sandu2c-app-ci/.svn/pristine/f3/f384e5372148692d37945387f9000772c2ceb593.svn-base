package com.sandu.wx.config;

import com.google.common.collect.Maps;
import com.sandu.wx.handler.LogHandler;
import com.sandu.wx.handler.MsgHandler;
import com.sandu.wx.handler.SubscribeHandler;
import com.sandu.wx.handler.UnsubscribeHandler;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.EventType;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * wechat mp configuration
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfiguration {
    private LogHandler logHandler;
    private MsgHandler msgHandler;
    private UnsubscribeHandler unsubscribeHandler;
    private SubscribeHandler subscribeHandler;

    private WxMpProperties properties;

    private static Map<String, WxMpMessageRouter> routers = Maps.newHashMap();
    private static Map<String, WxMpService> mpServices = Maps.newHashMap();

    @Autowired
    public WxMpConfiguration(LogHandler logHandler, MsgHandler msgHandler, UnsubscribeHandler unsubscribeHandler,
                             SubscribeHandler subscribeHandler, WxMpProperties properties) {
        this.logHandler = logHandler;
        this.msgHandler = msgHandler;
        this.unsubscribeHandler = unsubscribeHandler;
        this.subscribeHandler = subscribeHandler;
        this.properties = properties;
    }

    public static Map<String, WxMpMessageRouter> getRouters() {
        return routers;
    }

    public static Map<String, WxMpService> getMpServices() {
        return mpServices;
    }

    @Bean
    public Object services() {

        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(this.properties.getAppId());
        configStorage.setSecret(this.properties.getSecret());
        configStorage.setToken(this.properties.getToken());
        configStorage.setAesKey(this.properties.getAesKey());
        WxMpService service = new WxMpServiceImpl();
        service.setWxMpConfigStorage(configStorage);
        routers.put(this.properties.getAppId(), this.newRouter(service));
        mpServices.put(this.properties.getAppId(), service);
        return Boolean.TRUE;
    }

    private WxMpMessageRouter newRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

        /* rule()返回一个该路由器下的新的WxMpMessageRouterRule规则类。
           async(false)设置该规则为同步
           msgType(XmlMsgType.EVENT)设置该规则匹配的消息类型为event
           event(MenuButtonType.VIEW)设置该规则匹配的事件类型为VIEW
           handler(this.subscribeHandler)设置该规则的消息处理器
           end()匹配该规则后，不会匹配其他规则*/
        // 关注事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(EventType.SUBSCRIBE).handler(this.subscribeHandler)
                .end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(EventType.UNSUBSCRIBE)
                .handler(this.unsubscribeHandler).end();


        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }

}
