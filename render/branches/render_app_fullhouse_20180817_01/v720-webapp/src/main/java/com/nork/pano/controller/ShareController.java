package com.nork.pano.controller;

import com.nork.common.cache.CacheManager;
import com.nork.common.cache.Cacher;
import com.nork.common.pano.share.Sign;
import com.nork.common.util.HttpClient;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/7/17.
 */
@Controller
@RequestMapping(value = "/{style}/share")
public class ShareController {

    Logger logger = Logger.getLogger(ShareController.class);

    private static final String appid = Utils.getPropertyName("config/share","wx.share.appid","");
    private static final String app_secret = Utils.getPropertyName("config/share","wx.share.app_secret","");
    HttpClient httpClient = null;
    Cacher cacher = null;

    /**
     * 获取微信配置信息
     * @return
     */
    @RequestMapping(value = "/getWXConfig")
    @ResponseBody
    public Object getWXConfig(HttpServletRequest request, HttpServletResponse response, String url){
        String access_token = "";
        String jspia_ticket = "";
        String access_token_url = Utils.getPropertyName("config/share","wx.share.access_token_url","");
        String jsapi_ticket_url = Utils.getPropertyName("config/share", "wx.share.jsapi_ticket_url", "");
        JSONObject jsonObject = new JSONObject();
        String responseBody = "";
        Object cacheObj = null;
        if( httpClient == null ) {
            httpClient = HttpClient.getInstance();
        }
        if( cacher == null ){
            cacher = CacheManager.getInstance().getCacher();
        }
        // 获取access_token
        cacheObj = cacher.get("ACCESS_TOKEN");
        if( cacheObj != null ){
            access_token = cacheObj.toString();
            //logger.debug("=========access_token from redis:"+access_token);
        }else {
            access_token_url = access_token_url + "&appid=" + appid + "&secret=" + app_secret;
            logger.info("=========access_token_url:"+access_token_url);
            responseBody = httpClient.sendHttpGet(access_token_url);
            if( StringUtils.isBlank(responseBody) ){
                return null;
            }
            jsonObject = JSONObject.fromObject(responseBody);
            if (jsonObject != null && StringUtils.isNotBlank(jsonObject.getString("access_token"))) {
                access_token = jsonObject.getString("access_token");
                //logger.info("=========access_token from httpclient:"+access_token);
                // 放到redis缓存中
                cacher.set("ACCESS_TOKEN", access_token, 7000);
            }
        }

        // 获取jsapi_ticket
        if( StringUtils.isNotBlank(access_token) ){
            cacheObj = cacher.get("JSAPI_TICKET");
            //logger.debug("=========cacher.get(\"JSAPI_TICKET\"):"+cacher.get("JSAPI_TICKET"));
            if( cacheObj != null ){
                jspia_ticket = cacheObj.toString();
            }else {
                jsapi_ticket_url = jsapi_ticket_url + "&access_token=" + access_token;
                //logger.info("=========jsapi_ticket_url:"+jsapi_ticket_url);
                responseBody = httpClient.sendHttpGet(jsapi_ticket_url);
                if( StringUtils.isBlank(responseBody) ){
                    return null;
                }
                jsonObject = JSONObject.fromObject(responseBody);
                jspia_ticket = jsonObject.getString("ticket");
                // 放到redis中
                cacher.set("JSAPI_TICKET", jspia_ticket, 7000);
            }
        }

        // 生成签名相关信息
        if( StringUtils.isNotBlank(jspia_ticket) ){
            return Sign.sign(jspia_ticket, url);
        }
        return null;
    }

}
