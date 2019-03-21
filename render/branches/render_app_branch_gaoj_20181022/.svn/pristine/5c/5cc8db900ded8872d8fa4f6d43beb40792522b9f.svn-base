package com.nork.render.controller;

import com.nork.cache.service.RedisService;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanSummaryInfo;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignPlanSummaryInfoService;
import com.nork.pano.model.scene.PanoramaVo;
import com.nork.product.model.BaseCompany;
import com.nork.product.model.CompanyShop;
import com.nork.product.service.BaseCompanyService;
import com.nork.render.model.HeadMessage;
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
import java.util.StringJoiner;

@Controller
@RequestMapping("/v1/miniprogram/render/renderpic")
public class ResRenderPicController {

    private static final Logger logger = LoggerFactory.getLogger(ResRenderPicController.class.getName());
    private final static String CLASS_LOG_PREFIX = "[方案资源服务]";
    private final static String DESIGN_PLAN_VR_VIEW_NUM_MAP = "CustomerDesignPlanVrViewNumMap";
    private static final String DESIGN_PLAN_VR_VIEW_NUM_SYNCHRONIZE_MAP = "CustomerDesignPlanVrViewNumSynchronizeMap";
    private static final Integer PLAN_MODULE_SOURCE = 1;//来自推荐方案模块的方案
    private static final Integer PLAN_SHOP_SOURCE = 2;//来自店铺方案列表的方案
    private static final Integer PLAN_USER_SOURCE = 3;//用户自身的方案（效果图方案）
    private static final Integer SANDU_COMPANY_ID = 2501;//三度公司id，为了少查一次sql，就先写死了id为常量

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

            // 获取方案来源，处理头像相关信息
            Integer planSource = panoramaVo.getPlanSource();
            if (planSource != null && planSource != 0) {
                if (PLAN_MODULE_SOURCE.equals(planSource)) {
                    // 如果是方案模块的方案，则显示厂商的头像信息，三度的方案则不显示头像信息
                    Integer companyId = designPlanRecommended.getCompanyId();
                    if (!SANDU_COMPANY_ID.equals(companyId)) {
                        // 如果不为三度的方案，则显示对应方案厂商的信息，三度的则不返回头信息
                        BaseCompany company = baseCompanyService.getHeadMessageOfCompany(companyId);
                        if (company != null) {
                            headMessage.setCompanyId(company.getId());
                            headMessage.setHeadName(company.getCompanyName());
                            headMessage.setHeadPath(company.getCompanyLogoPath());
                            headMessage.setHeadPhoneNumber(company.getCompanyPhone());
                        }
                    }
                } else if (PLAN_SHOP_SOURCE.equals(planSource)) {
                    // 如果是店铺的推荐方案，则显示对应店铺的头像信息，并需要点击跳转品牌馆
                    Integer shopId = panoramaVo.getShopId();
                    if (shopId != null || shopId != 0) {
                        CompanyShop shop = baseCompanyService.getHeadMessageOfShop(shopId);
                        if (shop != null) {
                            headMessage.setShopId(shop.getShopId());
                            headMessage.setHeadName(shop.getShopName());
                            headMessage.setHeadPath(shop.getShopLogoPath());
                        }
                    }
                }
                    //如果为用户自己的方案则不会走到这里，不进行处理

            }
        } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
            map = resRenderPicService.getAllResRenderPic(designPlanRenderSceneId, ResRenderPicService.renderResEnum.designPlanRenderScene);
        } else {
            return new ResponseEnvelope(false, "参数方案ID为空！");
        }

        Map<String, Object> objectMap = new HashMap<>(map.size() + 1);
        objectMap.putAll(map);
        objectMap.put("pv", vrViewNum);
        objectMap.put("recommendedType",recommendedType);
        objectMap.put("headMessage",headMessage);
        return new ResponseEnvelope(true, objectMap);
    }

}
