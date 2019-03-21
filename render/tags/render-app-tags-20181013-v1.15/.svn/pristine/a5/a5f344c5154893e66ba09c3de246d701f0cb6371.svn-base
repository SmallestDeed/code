package com.nork.pano.controller;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.JsonUtil;
import com.nork.common.util.StringUtils;
import com.nork.pano.model.output.DesignPlanStoreReleaseVo;
import com.nork.pano.service.DesignPlanStoreReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 720分享制作
 */
@Controller
@RequestMapping("/v1/panorama")
public class DesignPlanStoreReleaseController {

    private static Logger logger = LoggerFactory.getLogger(DesignPlanStoreReleaseController.class);

    @Autowired
    private DesignPlanStoreReleaseService designPlanStoreReleaseService;

    /**
     * 获取720分享
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/get")
    @ResponseBody
    public Object get(@RequestParam String uuid){
        if( StringUtils.isBlank(uuid) ){
            new ResponseEnvelope<>(false, "");
        }
        System.out.println("get请求---------uuid：" + uuid);
        DesignPlanStoreReleaseVo designPlanStoreReleaseVo = null;
        try {
            designPlanStoreReleaseVo = designPlanStoreReleaseService.getPanorama(uuid);
            System.out.println("designPlanStoreReleaseVo：" + JsonUtil.toJson(designPlanStoreReleaseVo));
            // 更新浏览量
            designPlanStoreReleaseService.updatePv(uuid);
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
        System.out.println("designPlanStoreReleaseService.updatePv uuid：" + uuid);
        return new ResponseEnvelope<DesignPlanStoreReleaseVo>(true,designPlanStoreReleaseVo);
    }
}
