package com.nork.pano.controller;

import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.StringUtils;
import com.nork.pano.model.output.DesignPlanStoreReleaseVo;
import com.nork.pano.service.DesignPlanStoreReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 720分享制作
 */
@Controller
@RequestMapping("/v1/panorama")
public class DesignPlanStoreReleaseController {

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
        DesignPlanStoreReleaseVo designPlanStoreReleaseVo = designPlanStoreReleaseService.getPanorama(uuid);
        // 更新浏览量
        designPlanStoreReleaseService.updatePv(uuid);
        return new ResponseEnvelope<DesignPlanStoreReleaseVo>(true,designPlanStoreReleaseVo);
    }
}
