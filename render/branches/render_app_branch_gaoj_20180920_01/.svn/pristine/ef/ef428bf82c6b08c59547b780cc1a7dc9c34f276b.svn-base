package com.nork.render.controller;

import com.nork.cache.service.RedisService;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanSummaryInfo;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignPlanSummaryInfoService;
import com.nork.pano.model.scene.PanoramaVo;
import com.nork.render.model.ResRenderData;
import com.nork.system.service.ResRenderPicService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/v1/miniprogram/render/renderpic")
public class ResRenderPicController {

    private static final Logger logger = LoggerFactory.getLogger(ResRenderPicController.class.getName());
    private final static String CLASS_LOG_PREFIX = "[方案资源服务]";
    private final static String DESIGN_PLAN_VR_VIEW_NUM_MAP = "CustomerDesignPlanVrViewNumMap";
    private static final String DESIGN_PLAN_VR_VIEW_NUM_SYNCHRONIZE_MAP = "CustomerDesignPlanVrViewNumSynchronizeMap";

    @Autowired
    private ResRenderPicService resRenderPicService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DesignPlanSummaryInfoService designPlanSummaryInfoService;
    @Autowired
    private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;

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
        long vrViewNum = 0;
        Integer recommendedType = 1;

        Map<String, List<ResRenderData>> map = null;
        if (designPlanRecommendedId != null && designPlanRecommendedId.intValue() > 0) {
            synchronized (ResRenderPicController.class){
                //从缓存中获取方案720浏览数,获取不到从数据库查找,再存入缓存
                String designPlanLikeNum = redisService.getMap(DESIGN_PLAN_VR_VIEW_NUM_MAP, designPlanRecommendedId + "");

                if (StringUtils.isEmpty(designPlanLikeNum)) {
                    DesignPlanSummaryInfo summaryInfo = designPlanSummaryInfoService.get(designPlanRecommendedId);
                    if (null != summaryInfo) {
                        if (summaryInfo.getVrViewNum() != null){
                            vrViewNum = (long) summaryInfo.getVrViewNum();
                        }
                    }
                    redisService.addMap(DESIGN_PLAN_VR_VIEW_NUM_MAP, designPlanRecommendedId + "", vrViewNum + "");
                }
                vrViewNum = redisService.mapIncrby(DESIGN_PLAN_VR_VIEW_NUM_MAP, designPlanRecommendedId + "", 1L);
                //把720浏览数存到同步的缓存中
                redisService.addMap(DESIGN_PLAN_VR_VIEW_NUM_SYNCHRONIZE_MAP, designPlanRecommendedId + "", vrViewNum + "");
            }
            map = resRenderPicService.getAllResRenderPic(designPlanRecommendedId, ResRenderPicService.renderResEnum.designPlanRecommended);

            // 推荐方案返回是样板方案还是一件方案
            DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(designPlanRecommendedId);
            if (designPlanRecommended == null) {
                return new ResponseEnvelope(false,"查询到推荐方案为空，请检查参数 designPlanRecommendedId");
            }
            recommendedType = designPlanRecommended.getRecommendedType();
        } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
            map = resRenderPicService.getAllResRenderPic(designPlanRenderSceneId, ResRenderPicService.renderResEnum.designPlanRenderScene);
        } else {
            return new ResponseEnvelope(false, "参数方案ID为空！");
        }

        Map<String, Object> objectMap = new HashMap<>(map.size() + 1);
        objectMap.putAll(map);
        objectMap.put("pv", vrViewNum);
        objectMap.put("recommendedType",recommendedType);
        return new ResponseEnvelope(true, objectMap);
    }

}
