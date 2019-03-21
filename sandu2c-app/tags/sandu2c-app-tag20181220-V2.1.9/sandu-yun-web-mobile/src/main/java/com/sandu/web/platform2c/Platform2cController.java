package com.sandu.web.platform2c;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.sandu.cache.service.RedisService;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.StringUtils;
import com.sandu.platform.BasePlatform;
import com.sandu.system.service.BasePlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/v1/tocmobile/platform2c")
public class Platform2cController {
    private final static Logger logger = LoggerFactory.getLogger(Platform2cController.class);
    private final static String CLASS_LOG_PREFIX = "[平台基本服务]";
    private final static Gson GSON = new Gson();

    private final static String MOBILE2C_PLATFORM_CODE = "mobile2c";
    private final static String BASE_PLATFORM_INFO = "basePlatformInfo";

    @Autowired
    private RedisService redisService;
    @Autowired
    private BasePlatformService basePlatformService;

    /**
     * 获取平台详情接口
     *
     * @throws Exception
     */
    @RequestMapping(value = "/platform2cDetails", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope platform2cDetails(HttpServletRequest request,
                                              HttpServletResponse response) {

        String basePlatformInfo = redisService.getMap(BASE_PLATFORM_INFO, MOBILE2C_PLATFORM_CODE);

        if (StringUtils.isNotEmpty(basePlatformInfo)) {
            BasePlatform basePlatform = GSON.fromJson(basePlatformInfo,
                    BasePlatform.class);
            return new ResponseEnvelope<>(true, "success", basePlatform);
        } else {
            BasePlatform basePlatform = basePlatformService.getBasePlatform(MOBILE2C_PLATFORM_CODE);
            if (null != basePlatform) {
                redisService.addMap(BASE_PLATFORM_INFO, MOBILE2C_PLATFORM_CODE, GSON.toJson(basePlatform));
                return new ResponseEnvelope<>(true, "success", basePlatform);
            }
        }

        return new ResponseEnvelope<>(false, "平台不存在");
    }
}
