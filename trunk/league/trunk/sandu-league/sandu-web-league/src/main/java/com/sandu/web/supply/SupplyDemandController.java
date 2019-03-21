package com.sandu.web.supply;


import com.sandu.api.commom.JsonListModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/4/27 0027PM 5:37
 */

@RestController
@RequestMapping("/v1/supplydemand")
public class SupplyDemandController {
    private final static String CLASS_LOG_PREFIX = "[供求基础服务]";



    @RequestMapping(value = "/getallsupplydemandinfo")
    @ResponseBody
    public JsonListModel getAllSupplyDemandInfo(HttpServletRequest request) {


        return null;

    }
}
