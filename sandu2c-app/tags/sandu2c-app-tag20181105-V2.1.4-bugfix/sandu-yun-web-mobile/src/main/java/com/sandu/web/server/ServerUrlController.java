 
package com.sandu.web.server;

import com.google.gson.Gson;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.Utils;
import com.sandu.system.model.ServerUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/v1/tocmobile/serverurl")
public class ServerUrlController {

	private final static Gson gson = new Gson();
    private final static Logger logger = LoggerFactory.getLogger(ServerUrlController.class);
    private final static String CLASS_LOG_PREFIX = "[获取URL服务]";

    /**
     * 获取资源服务 url
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getServerUrl")
    public Object getServerUrl(HttpServletRequest request) {
    	Map<String, Object> map = new HashMap<>();
    	ServerUrl serverUrl = new ServerUrl();
    	String serverKey = Utils.getValue("app.servers.url", "");
    	logger.info(CLASS_LOG_PREFIX + "开始获取ServerUrl-getServerUrl:{}");
    	if (serverKey != null) {
    		String urls[] = serverKey.split(",");
    		for (int i = 0; i < urls.length; i++) {
    			String urlKey = urls[i];
    			String urlVal =  Utils.getValue(urls[i], "","serverurl");
    			map.put(urlKey, urlVal);
//				System.out.println( Utils.getValue(urls[i], "","serverurl"));
			}
    		
    		String urlJson = Utils.mapConvertJson(map);
    		serverUrl.setServerUrl(urlJson);
    		logger.info(CLASS_LOG_PREFIX + "获取serverUrl完毕-urlJson->:{}"+urlJson.toString());
    		System.out.println(urlJson);
		}else {
			logger.info(CLASS_LOG_PREFIX + "获取serverUrl失败:{}");
			return new ResponseEnvelope(false,"数据错误！");
		}
		return new ResponseEnvelope(true,"success",serverUrl);
    }
    
  
}