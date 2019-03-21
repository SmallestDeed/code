package com.sandu.search.common.tools;

import com.sandu.search.common.constant.HeaderConstant;
import com.sandu.search.common.constant.PlatformConstant;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求头工具类
 *
 * @date 2018/6/4
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
public class RequestHeaderUtil {

    /**
     * 根据请求从头部获取平台ID
     *
     * @date 2018/6/4
     * @auth pengxuangang
     * @mail xuangangpeng@gmail.com
     */
    public static String getPlatformIdByRequest(HttpServletRequest request) {
        String platformCode = request.getHeader(HeaderConstant.PLATFORM_CODE);

        //检查是否为小程序
        String customReferer = request.getHeader(HeaderConstant.CUSTOM_REFERER);

        if (!StringUtils.isEmpty(customReferer)
                && (customReferer.startsWith("https://servicewechat.com/")
                    || customReferer.startsWith("http://servicewechat.com/"))) {
            platformCode = PlatformConstant.PLATFORM_CODE_MINI_PROGRAM;
        }

        return platformCode;
    }
}
