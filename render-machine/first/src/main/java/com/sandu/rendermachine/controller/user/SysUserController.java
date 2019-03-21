package com.sandu.rendermachine.controller.user;

import com.sandu.jwt.Jwt;
import com.sandu.rendermachine.common.constant.SystemCommonConstant;
import com.sandu.rendermachine.common.properties.AppProperties;
import com.sandu.rendermachine.common.util.ResDistributeUtils;
import com.sandu.rendermachine.common.util.Utils;
import com.sandu.rendermachine.model.response.ResponseEnvelope;
import com.sandu.rendermachine.model.user.LoginUser;
import com.sandu.rendermachine.model.user.SysUser;
import com.sandu.rendermachine.service.user.SysUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.SocketException;
import java.util.*;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 1:59 2018/4/20 0020
 * @Modified By:
 */
@RequestMapping("/online/web/system/sysUser")
public class SysUserController {

    //服务化地址集合
    private static final String APP_SERVITIZATION_URLS = Utils.getValueByFileKey(AppProperties.APP, AppProperties.APP_SERVITIZATION_URLS,"");
    //服务化地址key
    private static final String SERVER_KEY = "serverKey";
    //服务化地址url
    private static final String SERVER_URL = "serverUrl";
    // 获取配置文件 tmg.properties */
    private final static ResourceBundle app = ResourceBundle.getBundle("app");
    private final static ResourceBundle webSocket = ResourceBundle.getBundle("config/webSocket");
    //渲染机调取服务路径
    private final String RENDERTASKSERVERURL = app.getString("app.render.task.server.url");
    private final String RESOURCESURL = app.getString("app.resources.url");
    private final String SITENAME = Utils.getValue("app.server.siteName","nork");
    private final String SITEKEY = Utils.getValue("app.server.siteKey", "online");
    private static List<SysUser.ResourcesUrl> resourcesUrls = getResourcesUrls();

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/app/loginForAuto")
    @ResponseBody
    public ResponseEnvelope<SysUser> loginForAuto(SysUser sysUser, String terminalImei, String mediaType,
                                                  HttpServletRequest request, HttpServletResponse response,
                                                  Integer forceLogin, Integer userId) throws SocketException {
        ResponseEnvelope<SysUser> res = null;
        //验证白名单
        boolean flag = sysUserService.checkWhiteList(terminalImei);
        if (!flag ) {
            return new ResponseEnvelope<SysUser>(false,"白名单数据异常",sysUser.getMsgId());
        }
        SysUser user = sysUserService.get(userId);
        //拿到UserId 查用户信息。
        if(StringUtils.isEmpty(mediaType)) {
            mediaType = "3";
        }
        if (user != null) {
            String appWebSocketMessage = webSocket.getString("app.webSocket.message");
            String appWebSocketPayOrder = webSocket.getString("app.webSocket.payOrder");
            user.setWebSocketMessage(appWebSocketMessage);
            user.setPayCallBackServer(appWebSocketPayOrder);
			/* 保存心跳间隔信息 */
            Integer heartbeatTime = Integer.valueOf(Utils.getValue("heartbeatTime", "120"));
            user.setHeartbeatTime(heartbeatTime);
            user.setServerUrl(RENDERTASKSERVERURL);
            user.setResourcesUrl(RESOURCESURL);
            user.setSitekey(SITEKEY);
            user.setSiteName(SITENAME);
            //用token加密app用来解密的key

            // 设置分布式域名配置
            user.setResourcesUrls(resourcesUrls);
            //获取服务化集合配置
            user.setServitizationList(this.getServitizationUrls());

            String userKey = UUID.randomUUID().toString();
            Map<String , Object> payload=new HashMap<String, Object>();
            Date date=new Date();
            payload.put("uid", user.getId());//用户ID
            payload.put("uname", user.getNickName());//
            payload.put("mtype", mediaType);//mediatype
            payload.put("uphone", user.getMobile());//
            payload.put("appKey", userKey);//
            payload.put("ukey", userKey);
            payload.put("utype", user.getUserType());
            payload.put("signflat", SystemCommonConstant.LOGIN_FROM_PC);
            payload.put("iat", date.getTime());//生成时间
            payload.put("rsource","auto"); //请求来源 （自动渲染）
            payload.put("ext",date.getTime()+1000*60*60*SystemCommonConstant.USER_TIME_OUT_HOUR);//过期时间6小时
            String token= Jwt.createToken(payload);
            user.setToken(token);

            user = sysUserService.EencryptKey(user);

            LoginUser loginUser = user.toLoginUser();
            loginUser.setMediaType(mediaType);
            loginUser.setUserKey(userKey);
            //Cache 用户登录信息
//			if (Utils.enableRedisCache()) {
//				SysUserCacher.cacheTheLoginUserInfo(loginUser, SystemCommonConstant.USER_TIME_OUT_HOUR*60*60); //单位为秒
//			}

            res = new ResponseEnvelope<SysUser>(user, sysUser.getMsgId(), true);
        }

        return res;
    }

    /**
     * 得到分布式资源存储对应的域名配置
     * @author huangsongbo
     * @return
     */
    private static List<SysUser.ResourcesUrl> getResourcesUrls() {
        Map<String, String> map = ResDistributeUtils.urlDistributeMap;
        Map<String, List<String>> mapTemp = new HashMap<String, List<String>>();
        for(String modelName : map.keySet()) {
            String domain = map.get(modelName);
            if(mapTemp.containsKey(domain)) {
                List<String> keyList = mapTemp.get(domain);
                keyList.remove(modelName);
                keyList.add(modelName);
                mapTemp.put(domain, keyList);
            }else {
                List<String> keyList = new ArrayList<String>();
                keyList.add(modelName);
                mapTemp.put(domain, keyList);
            }
        }

        // *组装List<ResourcesUrl> ->start
        List<SysUser.ResourcesUrl> resourcesUrlList = new ArrayList<SysUser.ResourcesUrl>();
        for(String domain : mapTemp.keySet()) {
            SysUser.ResourcesUrl resourcesUrl = new SysUser().new ResourcesUrl();
            resourcesUrl.setDomain(domain);
            resourcesUrl.setModelName(mapTemp.get(domain));
            resourcesUrlList.add(resourcesUrl);
        }
        // *组装List<ResourcesUrl> ->end

        return resourcesUrlList;
    }

    //获取服务化地址
    private List<Map<String,String>> getServitizationUrls(){
        List<Map<String,String>> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(APP_SERVITIZATION_URLS)) {
            Map<String,String> map = null;
            JSONArray jsonArray = JSONArray.fromObject(APP_SERVITIZATION_URLS);
            if (jsonArray != null && jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    map = new HashMap<>();
                    JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                    map.put(SERVER_KEY, jsonObj.getString(SERVER_KEY));
                    map.put(SERVER_URL,jsonObj.getString(SERVER_URL));
                    list.add(map);
                }
            }
        }
        return list;
    }
}
