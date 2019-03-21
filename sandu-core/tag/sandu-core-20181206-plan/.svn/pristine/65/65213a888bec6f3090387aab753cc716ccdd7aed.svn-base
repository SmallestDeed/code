package com.sandu.web.product.controller;

import com.sandu.api.base.common.ResponseEnvelope;
import com.sandu.api.base.common.util.SpringContextHolder;
import com.sandu.api.base.model.BaseProductCategory;
import com.sandu.api.base.model.BaseProductCategorySearch;
import com.sandu.api.base.service.CompressImageService;
import com.sandu.job.DesignPlanJob;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) http://www.sanduspace.cn. All rights reserved.
 *
 * @author :  Steve
 * @date : 2018/11/23
 * @since : sandu_yun_1.0
 */
@Log4j2(topic = "[ImageRefreshActionController]")
@RestController
@RequestMapping("/v1/core/groupProduct")
@Api(tags = "ProductCategoryController")
public class ImageRefreshActionController {

    private static String keys = "3d990d2276917dfac04467df11fff26d";

    @Autowired
    private CompressImageService compressImageService;
    @RequestMapping(value="/compressGroupProductImage",method = RequestMethod.GET)
    public ResponseEnvelope compressGroupProductImage(@RequestParam("keys") String keys) {
        if(keys.equals(keys)){
            compressImageService.compressImage();
        }
        return new ResponseEnvelope(true, "OK");
    }

    @RequestMapping(value="/startDesignPlanJob",method = RequestMethod.GET)
    public ResponseEnvelope startDesignPlanJob(@RequestParam Map<String, String> map) {
        if (map.containsKey("running")) {
            boolean running = Boolean.parseBoolean(map.get("running"));
            DesignPlanJob.running = running;
            map.remove("running");
        }
        DesignPlanJob.map = map;
        return new ResponseEnvelope(true, "ok", map);
    }
}
