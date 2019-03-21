package com.nork.render.controller;

import com.nork.cache.service.RedisService;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.model.CompanyDesignPlanIncome;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.DesignPlanSummaryInfo;
import com.nork.design.service.CompanyDesignPlanIncomeService;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.design.service.DesignPlanRenderSceneService;
import com.nork.design.service.DesignPlanSummaryInfoService;
import com.nork.home.service.SpaceCommonService;
import com.nork.pano.model.scene.PanoramaVo;
import com.nork.product.model.BaseCompany;
import com.nork.product.model.CompanyShop;
import com.nork.product.service.BaseCompanyService;
import com.nork.product.service.CompanyShopService;
import com.nork.render.model.HeadMessage;
import com.nork.render.model.ResRenderData;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResRenderPicService;
import com.sandu.node.constant.NodeInfoConstant;
import com.sandu.system.service.NodeInfoBizService;
import com.nork.system.service.SysDictionaryService;
import com.sandu.common.LoginContext;
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
import java.util.*;

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
        //空间id
        Integer spaceCommonId = null;
        //方案封面图id
        Integer coverPicId = null;

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
            recommendedType = designPlanRecommended.getRecommendedType();
            coverPicId = designPlanRecommended.getCoverPicId();
            // 获取方案来源，处理头像相关信息
            Integer planSource = panoramaVo.getPlanSource();
            if (planSource != null && planSource != 0) {
                if (PLAN_MODULE_SOURCE.equals(planSource)) {
                    /**
                     * 显示企业第一个创建的发布到随选网的店铺信息
                     */
                    if(designPlanRecommended.getCompanyId() != null){
                        CompanyShop companyShop = null;
                        companyShop = companyShopService.getCompanyShopByCompanyId(designPlanRecommended.getCompanyId());
                        if(companyShop != null){
                            headMessage.setShopId(companyShop.getId());
                            headMessage.setHeadName(companyShop.getShopName());
                            headMessage.setHeadPhoneNumber(companyShop.getContactPhone());
                            if(companyShop.getLogoPicId() != null){
                                ResPic logoPic = resPicService.get(companyShop.getLogoPicId());
                                headMessage.setHeadPath(logoPic != null ? logoPic.getPicPath():null);
                            }
                        }else{
                            //显示店铺信息
                            BaseCompany company = baseCompanyService.get(designPlanRecommended.getCompanyId());
                            if(company != null){
                                headMessage.setHeadName(company.getCompanyName());
                                headMessage.setHeadPhoneNumber(company.getCompanyPhone());
                                if(company.getCompanyLogo() != null){
                                    ResPic logoPic = resPicService.get(company.getCompanyLogo());
                                    headMessage.setHeadPath(logoPic != null ? logoPic.getPicPath():null);
                                }
                            }
                        }
                    }

                } else if (PLAN_SHOP_SOURCE.equals(planSource)) {
                    // 如果是店铺的推荐方案，则显示对应店铺的头像信息，并需要点击跳转对应店铺
                    Integer shopId = panoramaVo.getShopId();
                    if (shopId != null || shopId != 0) {
                        CompanyShop shop = baseCompanyService.getHeadMessageOfShop(shopId);
                        if (shop != null) {
                            headMessage.setShopId(shop.getId());
                            headMessage.setHeadName(shop.getShopName());
                            headMessage.setHeadPath(shop.getShopLogoPath());
                        }
                    }
                }
                //增加参数，用于分享场景装进我家
                headMessage.setGroupPrimaryId(designPlanRecommended.getGroupPrimaryId());
                headMessage.setPlanRecommendedId(designPlanRecommendedId);
                spaceCommonId = designPlanRecommended.getSpaceCommonId();
            }
        } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
            DesignPlanRenderScene planRenderScene = designPlanRenderSceneService.get(designPlanRenderSceneId);
            coverPicId = planRenderScene.getCoverPicId();
            spaceCommonId = planRenderScene != null ? planRenderScene.getSpaceCommonId() : null;
            map = resRenderPicService.getAllResRenderPic(designPlanRenderSceneId, ResRenderPicService.renderResEnum.designPlanRenderScene);
        } else {
            return new ResponseEnvelope(false, "参数方案ID为空！");
        }

        //空间信息
        if(spaceCommonId != null && spaceCommonId > 0){
            Integer functionId = spaceCommonService.getSpaceFunctionIdBySpaceCommonId(spaceCommonId);
            headMessage.setSpaceFunctionId(functionId);
            //空间类型
            SysDictionary houseTypeDictionary = sysDictionaryService.findOneByTypeAndValue("housetype",functionId);
            headMessage.setHouseTypeName(houseTypeDictionary != null ? houseTypeDictionary.getName() : null);
        }
        //封面图
        if(coverPicId != null){
            ResRenderPic coverPic = resRenderPicService.get(coverPicId);
            headMessage.setCoverPath(coverPic != null ? coverPic.getPicPath() : null);
        }
        //场景内显示内容控制
        headMessage.setCopyRightPermission(0);
        if(Objects.equals("miniProgram",platformType)){
            if(!Utils.isBlank(domain) &&(Objects.equals(WX_SMALLl_APPID,domain) || Objects.equals(WX_SMALL_SANDU_APPID,domain))) {
                //兼容之前的错误逻辑,当appid 为wx42e6b214e6cdaed3或wx0d37f598e1028825 都认为来源是随选网
                //随选网
                if (designPlanRecommendedId != null && designPlanRecommendedId > 0) {

                    if (Objects.equals(Constants.RECOMMENDED_TYPE_ONE_KEY_PUB, designPlanRecommended.getRecommendedType())) {
                        headMessage.setViewControlType(1);
                        //一键装修方案校验是否需收费和用户是否付费
                        if (Objects.equals(1, designPlanRecommended.getChargeType())) {
                            headMessage.setCopyRightPermission(1);
                            headMessage.setPlanPrice(designPlanRecommended.getPlanPrice());
                            if(loginUser != null && loginUser.getId() != null){
                                CompanyDesignPlanIncome incomesearch = new CompanyDesignPlanIncome();
                                incomesearch.setPlanId(designPlanRecommended.getId());
                                incomesearch.setBuyerId(loginUser.getId().longValue());
                                incomesearch.setPlanType(1);
                                incomesearch.setIsDeleted(0);
                                int count = companyDesignPlanIncomeService.getCount(incomesearch);
                                if (count <= 0) {
                                    headMessage.setHavePurchased(0);
                                    headMessage.setViewControlType(8);
                                }else{
                                    headMessage.setHavePurchased(1);
                                    headMessage.setCopyRightPermission(1);
                                }
                            }

                        }else{
                            headMessage.setCopyRightPermission(0);
                        }
                    }else{
                        //样板方案为6
                        headMessage.setViewControlType(6);
                    }
                } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
                    headMessage.setCopyRightPermission(0);
                    headMessage.setViewControlType(6);
                }
            }else{
                //企业小程序
                if (designPlanRecommendedId != null && designPlanRecommendedId > 0) {
                    headMessage.setViewControlType(9);
                } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
                    headMessage.setViewControlType(2);
                }
            }
        }else if(Objects.equals("mobile2b",platformType)){
            //移动B端
            if (designPlanRecommendedId != null && designPlanRecommendedId > 0) {
                if (Objects.equals(Constants.RECOMMENDED_TYPE_ONE_KEY_PUB, designPlanRecommended.getRecommendedType())) {
                    //是否为中介用户 TODO:先写死
                    if(loginUser != null && Objects.equals(11,loginUser.getUserType())){
                        headMessage.setViewControlType(10);
                    }else{
                        headMessage.setViewControlType(9);
                    }
                }else{
                    headMessage.setViewControlType(9);
                }
            } else if (designPlanRenderSceneId != null && designPlanRenderSceneId > 0) {
                headMessage.setViewControlType(2);
            }
        }else{
            //防止出错
            headMessage.setViewControlType(4);
        }

        Map<String, Object> objectMap = new HashMap<>(map.size() + 1);
        objectMap.putAll(map);
        objectMap.put("pv", vrViewNum);
        objectMap.put("recommendedType",recommendedType);
        objectMap.put("headMessage",headMessage);
        return new ResponseEnvelope(true, objectMap);
    }

}
