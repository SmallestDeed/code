package com.sandu.web.render;

import com.google.gson.Gson;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.pano.model.scene.PanoramaVo;
import com.sandu.render.model.ResRenderData;
import com.sandu.system.service.ResRenderPicService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
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
    Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(ResRenderPicController.class.getName());
    private final static String CLASS_LOG_PREFIX = "[方案资源服务]";

    @Autowired
    private ResRenderPicService resRenderPicService;


   /* @Autowired
    private RabbitService rabbitService;*/

    /**
     * 获取设计方案的照片,单点720,多点720,视频资源
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"/getrenderres"}, method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "渲染资源列表", response = ResponseEnvelope.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "designPlanRecommendedId", value = "推荐方案ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "designPlanRenderSceneId", value = "副本方案ID", required = false, paramType = "query", dataType = "int")
    })
    public Object getRenderPic(@ModelAttribute PanoramaVo panoramaVo, HttpServletRequest request) {
        Integer designPlanRecommendedId = panoramaVo.getDesignPlanRecommendedId();
        Integer designPlanRenderSceneId = panoramaVo.getDesignPlanRenderSceneId();
        Map<String, List<ResRenderData>> map = null;
        if (designPlanRecommendedId != null && designPlanRecommendedId.intValue() > 0) {
            map = resRenderPicService.getAllResRenderPic(designPlanRecommendedId, ResRenderPicService.renderResEnum.designPlanRecommended);
        } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
            map = resRenderPicService.getAllResRenderPic(designPlanRenderSceneId, ResRenderPicService.renderResEnum.designPlanRenderScene);
        } else {
            return new ResponseEnvelope(false, "该方案无渲染资源");
        }
     /*   *//*统计推荐方案浏览次数--start*//*
        try {
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            rabbitService.sendMsg(new DesignPlanMessage(loginUser == null ? 0 : loginUser.getId(), designPlanRecommendedId, ActionType.BROWSE, PlatformType.PLATFORM_CODE_MINI_PROGRAM));
        } catch (RabbitException e) {
            logger.error(CLASS_LOG_PREFIX, e);
            e.getStackTrace();
            *//*统计推荐方案浏览次数--end*//*
        } finally {
            return new ResponseEnvelope(true, "", map);
        }*/
        return new ResponseEnvelope(true, "", map);

    }
}
