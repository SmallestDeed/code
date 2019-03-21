package com.nork.render.controller;

import com.nork.cache.service.RedisService;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.service.CompanyDesignPlanIncomeService;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.design.service.DesignPlanSummaryInfoService;
import com.nork.home.service.SpaceCommonService;
import com.nork.pano.model.scene.PanoramaVo;
import com.nork.pano.service.PanoramaService;
import com.nork.product.service.BaseCompanyService;
import com.nork.product.service.CompanyShopService;
import com.nork.render.model.HeadMessage;
import com.nork.render.model.ResRenderData;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResRenderPicService;
import com.sandu.node.constant.NodeInfoConstant;
import com.sandu.system.service.NodeInfoBizService;
import com.nork.system.service.SysDictionaryService;
import com.sandu.common.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/v1/miniprogram/render/renderpic")
public class ResRenderPicController {

    private static final Logger logger = LoggerFactory.getLogger(ResRenderPicController.class.getName());
    private final static String CLASS_LOG_PREFIX = "[方案资源服务]";
    private final static String DESIGN_PLAN_VR_VIEW_NUM_MAP = "CustomerDesignPlanVrViewNumMap";
    private static final String DESIGN_PLAN_VR_VIEW_NUM_SYNCHRONIZE_MAP = "CustomerDesignPlanVrViewNumSynchronizeMap";
    private static final Integer SANDU_COMPANY_ID = 2501;//三度公司id，为了少查一次sql，就先写死了id为常量
    //随选网appid
    private static final String WX_SMALLl_APPID = Utils.getPropertyName("config/share","wx.small.share.appid","wx42e6b214e6cdaed3");
    //三度总店appid
    private static final String WX_SMALL_SANDU_APPID = Utils.getPropertyName("config/share","wx.small.sandu.appid","wx0d37f598e1028825");

    @Autowired
    private ResRenderPicService resRenderPicService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DesignPlanSummaryInfoService designPlanSummaryInfoService;
    @Autowired
    private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private CompanyShopService companyShopService;
    @Autowired
    private NodeInfoBizService nodeInfoBizService;
    @Autowired
    private SpaceCommonService spaceCommonService;
    @Autowired
    private DesignPlanRenderSceneService designPlanRenderSceneService;
    @Autowired
    private CompanyDesignPlanIncomeService companyDesignPlanIncomeService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private PanoramaService panoramaService;

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
        HeadMessage headMessage = new HeadMessage();
        String platformType = request.getHeader("Platform-Code");
        //小程序appid
        String domain = Utils.getDomainNameByHost(request);
        //用户
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if(loginUser == null || loginUser.getId() == null){
            logger.error("getrenderres 用户信息为空!");
        }

        DesignPlanRecommended designPlanRecommended = null;
        Map<String, List<ResRenderData>> map = null;
        if (designPlanRecommendedId != null && designPlanRecommendedId.intValue() > 0) {
            // 浏览量加1
            Integer nodeId = nodeInfoBizService.registerNodeInfo(designPlanRecommendedId, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_RECOMMENDED);
            vrViewNum = nodeInfoBizService.updateNodeInfoDetailValue(0,
                    nodeId,
                    NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_RECOMMENDED,
                    NodeInfoConstant.SYSTEM_DICTIONARY_DETAIL_TYPE_VIEW,
                    (byte)1);
            Map<String, Object> nodeInfoMap = nodeInfoBizService.getNodeData(designPlanRecommendedId, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_RECOMMENDED, 0);
            vrViewNum += (nodeInfoMap.get("setVirtualViewNum") == null ? 0 : (Integer)nodeInfoMap.get("setVirtualViewNum"));

            map = resRenderPicService.getAllResRenderPic(designPlanRecommendedId, ResRenderPicService.renderResEnum.designPlanRecommended);
            // 推荐方案返回是样板方案还是一件方案
            designPlanRecommended = designPlanRecommendedServiceV2.get(designPlanRecommendedId);
            if (designPlanRecommended == null) {
                return new ResponseEnvelope(false, "查询到推荐方案为空，请检查参数 designPlanRecommendedId");
            }
        } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
            map = resRenderPicService.getAllResRenderPic(designPlanRenderSceneId, ResRenderPicService.renderResEnum.designPlanRenderScene);
        } else {
            return new ResponseEnvelope(false, "参数方案ID为空！");
        }
        //平台类型
        String platformTypeStr = null;
        //获取方案分享显示相关信息
        if(Objects.equals("miniProgram",platformType)){
            if(!Utils.isBlank(domain) &&(Objects.equals(WX_SMALLl_APPID,domain) || Objects.equals(WX_SMALL_SANDU_APPID,domain))) {
                //兼容之前的错误逻辑,当appid 为wx42e6b214e6cdaed3或wx0d37f598e1028825 都认为来源是随选网
                platformTypeStr = "selectDecoration";
            }else{
                platformTypeStr = "companyMiniprogram";
            }
        }else if(Objects.equals("mobile2b",platformType)){
            platformTypeStr = "mobile2b";
        }else{
            platformTypeStr = "others";
        }
        headMessage = resRenderPicService.getSceneHeadMessage(panoramaVo,headMessage,loginUser != null?loginUser.getId():null,platformTypeStr);
        Map<String, Object> objectMap = new HashMap<>(map.size() + 1);
        objectMap.putAll(map);
        objectMap.put("pv", vrViewNum);
        objectMap.put("recommendedType",recommendedType);
        objectMap.put("headMessage",headMessage);
        return new ResponseEnvelope(true, objectMap);
    }

}
