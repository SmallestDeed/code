package com.nork.render.controller;

import com.nork.common.model.ResponseEnvelope;
import com.nork.pano.model.scene.PanoramaVo;
import com.nork.render.model.ResRenderData;
import com.nork.system.service.ResRenderPicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/v1/miniprogram/render/renderpic")
public class ResRenderPicController {

    private static final Logger logger = LoggerFactory.getLogger(ResRenderPicController.class.getName());
    private final static String CLASS_LOG_PREFIX = "[方案资源服务]";

    @Autowired
    private ResRenderPicService resRenderPicService;

    /**
     * 获取设计方案的照片,单点720,多点720,视频资源
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"/getrenderres"}, method = RequestMethod.GET)
    @ResponseBody
    public Object getRenderPic(@ModelAttribute PanoramaVo panoramaVo, HttpServletRequest request) {

        Integer designPlanRecommendedId = panoramaVo.getDesignPlanRecommendedId();
        Integer designPlanRenderSceneId = panoramaVo.getDesignPlanRenderSceneId();

        Map<String, List<ResRenderData>> map = null;
        if (designPlanRecommendedId != null && designPlanRecommendedId.intValue() > 0) {
            map = resRenderPicService.getAllResRenderPic(designPlanRecommendedId, ResRenderPicService.renderResEnum.designPlanRecommended);
        } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
            map = resRenderPicService.getAllResRenderPic(designPlanRenderSceneId, ResRenderPicService.renderResEnum.designPlanRenderScene);
        } else {
            return new ResponseEnvelope(false, "参数方案ID为空！");
        }

        return new ResponseEnvelope(true, map);
    }

}
