package com.nork.mobile.controller;

import com.google.gson.Gson;
import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.model.ResponseEnvelope;
import com.nork.system.controller.PlatformController;
import com.nork.system.model.BasePlatform;
import com.nork.system.service.BasePlatformService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/{style}/mobile/platform2b")
public class MobilePlatformController {
    private final static Logger logger = LoggerFactory.getLogger(PlatformController.class);
    private final static String CLASS_LOG_PREFIX = "[平台基本服务]";

    private static final Gson GSON = new Gson();

    public static final String MOBILE2B_PLATFORM_CODE = "mobile2b";
    public static final String BASE_PLATFORM_INFO = "basePlatformInfo";

    @Autowired
    private BasePlatformService basePlatformService;

    /**
     * 获取平台详情接口
     *
     * @throws Exception
     */
    @RequestMapping(value = "/platform2bDetails", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope platform2cDetails(HttpServletRequest request, HttpServletResponse response) {

        String basePlatformInfo = JedisUtils.getMap(BASE_PLATFORM_INFO, MOBILE2B_PLATFORM_CODE);

        BasePlatform basePlatform;
        if (StringUtils.isNotEmpty(basePlatformInfo)) {
            basePlatform = GSON.fromJson(basePlatformInfo, BasePlatform.class);
        } else {
            basePlatform = basePlatformService.getBasePlatform(MOBILE2B_PLATFORM_CODE);
            if (null == basePlatform) {
                return new ResponseEnvelope(false, "平台不存在");
            }
            JedisUtils.setMap(BASE_PLATFORM_INFO, MOBILE2B_PLATFORM_CODE, GSON.toJson(basePlatform));
        }
        return new ResponseEnvelope(true, basePlatform);
    }
}
