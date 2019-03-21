package com.sandu.web.pano.controller;

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.PlatformConstant;
import com.sandu.design.model.ProductsCostType;
import com.sandu.designplan.model.DesignPlan;
import com.sandu.designplan.model.DesignPlanRecommended;
import com.sandu.designplan.model.DesignPlanRenderScene;
import com.sandu.designplan.service.DesignPlanRecommendedService;
import com.sandu.designplan.service.DesignPlanRenderSceneService;
import com.sandu.designplan.service.DesignPlanService;
import com.sandu.pano.model.scene.PanoramaVo;
import com.sandu.pano.service.PanoramaService;
import com.sandu.platform.BasePlatform;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.service.BaseCompanyService;
import com.sandu.product.service.MobileDesignPlanProductService;
import com.sandu.system.service.BasePlatformService;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.UserSO;
import com.sandu.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @Title: ExpController.java
 * @Package com.sandu.demo.controller
 * @Description:演示模块-参考例子Controller
 * @createAuthor pandajun
 * @CreateDate 2015-05-17 20:11:49
 */
@Controller
@Slf4j
@RequestMapping("v1/miniprogram/pages/vr720")
public class PanoramaController {
    private final static Gson gson = new Gson();
    private final static Logger logger = LoggerFactory.getLogger(PanoramaController.class);
    private final static String CLASS_LOG_PREFIX = "[720场景产品清单列表服务]";

    @Value("${miniprogram.platform.code}")
    private String miniprogramPlatformCode;
    @Value("${sanducloudhome.company.code}")
    private String companyCode;

    private static final String DICTIONARY_PRODUCT_TYPE = "productType";

    @Autowired
    private PanoramaService panoramaService;
    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private MobileDesignPlanProductService mobileDesignPlanProductService;
    @Autowired
    private BasePlatformService basePlatformService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private DesignPlanRecommendedService designPlanRecommendedService;
    @Autowired
    private DesignPlanRenderSceneService designPlanRenderSceneService;
    @Autowired
    private DesignPlanService designPlanService;


    /**
     * 720场景产品清单列表
     */
    @RequestMapping(value = "/getproductscost")
    @ResponseBody
    public ResponseEnvelope list(@ModelAttribute("PanoramaVo") PanoramaVo panoramaVo, HttpServletRequest request,
                                 HttpServletResponse response) {
        @SuppressWarnings("rawtypes")
        ResponseEnvelope res = null;
        List<ProductsCostType> list = null;
        List<Integer> brandList = new ArrayList <>();
        String platformCode = request.getHeader("Platform-Code");
        Integer checkUser = panoramaVo.getCheckUser()!=null?panoramaVo.getCheckUser():1;
        if (platformCode == null)
            return new ResponseEnvelope(false,"没有获取到Platform-Code");

        log.info("获取请求头编码:"+platformCode);
        BaseCompany baseCompany = null;
        try {
            //查询是否是访问公司
            switch(platformCode)
            {
                case "miniProgram":
                    baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Custom-Referer"));
                    break;
                case "brand2c":
                default:
                    baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Referer"));
                    if (baseCompany == null){
                        baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Custom-Referer"));
                    }
                    log.info("获取miniProgram或brand2c公司信息:"+baseCompany==null?null:gson.toJson(baseCompany));
                    break;
                case "mobile2b":
                    LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

                    if (loginUser == null)
                    {
                        return new ResponseEnvelope(false,"用户未登陆!!!");
                    }

                    SysUser user = sysUserService.get(loginUser.getId());
                    baseCompany = baseCompanyService.get(user.getBusinessAdministrationId());
                    log.info("获取mobile2b公司信息:"+baseCompany==null?null:gson.toJson(baseCompany));
                    break;
                case PlatformConstant.PLATFORM_CODE_TOB_PC:
                    SysUser sysUser = this.getUserByPanoramaVo(panoramaVo);
                    if(sysUser == null){
                        return new ResponseEnvelope(false,"未找到用户信息!");
                    }
                    panoramaVo.setUserId(sysUser.getId());
                    if(sysUser.getCompanyId() != null && sysUser.getCompanyId() > 0 ){
                        baseCompany = baseCompanyService.get(sysUser.getCompanyId());
                    }else if(sysUser.getMiniProgramCompanyId() != null && sysUser.getMiniProgramCompanyId() >0) {
                        //兼容一下......
                        baseCompany = baseCompanyService.get(sysUser.getMiniProgramCompanyId());
                    }
                    break;
            }
            if (baseCompany == null){
                return new ResponseEnvelope(false, "未获取到公司");
            }
            if (baseCompany.getId() == 2501 && "miniProgram".equals(platformCode)) {
                platformCode = "selectDecoration";
            }

            panoramaVo.setBaseCompany(baseCompany);
            panoramaVo.setCompanyId(baseCompany.getId());
            panoramaVo.setPlatformCode(platformCode);

            UserSO userSo = null;
            if(checkUser.intValue() == 1) {
                LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
                logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
                if (loginUser != null) {
                    userSo = new UserSO();
                    userSo.setUserId(loginUser.getId());
                    panoramaVo.setUserId(loginUser.getId());
                }
            }

            //获取当前平台
            BasePlatform basePlatform = basePlatformService.getBasePlatform(platformCode);
            if (basePlatform == null) {
                return new ResponseEnvelope(false, "未知的平台");
            }
            panoramaVo.setPlatformBussinessType(basePlatform.getPlatformBussinessType());
            log.info("获取到平台信息:"+gson.toJson(basePlatform));
            panoramaVo.setPlatformId(basePlatform.getId());

            /*三度云享家分类下的所有产品可见，平台过滤*/
            logger.info("miniProgram/getproductscost ---- companyCode="+companyCode+"&baseCompany.getCompanyCode()"+baseCompany.getCompanyCode());
            if (companyCode.equals(baseCompany.getCompanyCode())) {
                panoramaVo.setIsSandu(1);
            }else {
                // Add by Steve;
                List brandIds = null;
                if(PlatformConstant.PLATFORM_CODE_MINI_PROGRAM.equalsIgnoreCase(platformCode)) {
                    brandIds = baseCompanyService.getEnableBrandIdsByAppId(baseCompany.getAppId());
//                }else if(PlatformConstant.PLATFORM_CODE_TOB_MOBILE.equalsIgnoreCase(platformCode)) {
//                    brandIds = baseCompanyService.getBrandIdListByCompanyId(baseCompany.getId(), loginUser, PlatformConstant.PLATFORM_CODE_TOB_MOBILE);
                }
                if (brandIds != null && brandIds.size() > 0) {
                    panoramaVo.setBrandList(brandIds);
                }
            }

            switch (platformCode) {
                case "miniProgram":
                case "brand2c":
                case "mobile2c":
                case "selectDecoration":
                default:
                    list = mobileDesignPlanProductService.getDesignPlanProductList2C(panoramaVo);
                    break;
                case "mobile2b":
                case PlatformConstant.PLATFORM_CODE_TOB_PC:
                    list = mobileDesignPlanProductService.getDesignPlanProductList2B(panoramaVo);
                    break;
            }

//            logger.info(CLASS_LOG_PREFIX + "720场景产品清单列表:{}", gson.toJson(list));
            Integer totalCount = 0;
            if (null != list && list.size() > 0) {
                totalCount = list.size();
            } else {
                return new ResponseEnvelope(true, "success", "此案例没有任何产品");
            }
            res = new ResponseEnvelope(true, "success", list, totalCount);
        } catch (Exception e) {
            logger.error(CLASS_LOG_PREFIX + "720场景产品清单列表:{}, Exception:{}.", gson.toJson(list), e.getMessage());
            res = new ResponseEnvelope(false, "产品清单显示失败");
            e.printStackTrace();
        }

        return res;
    }

    @RequestMapping(value = "/testList")
    @ResponseBody
    public ResponseEnvelope testList() {
        PanoramaVo panoramaVo = new PanoramaVo();
        panoramaVo.setDesignPlanRenderSceneId(48200);
        panoramaVo.setPlatformId(14);
        panoramaVo.setUserId(1480);
        panoramaVo.setCompanyId(1003);
        panoramaVo.setPlatformBussinessType("2c");
        BaseCompany baseCompany = baseCompanyService.get(1003);
        panoramaVo.setBaseCompany(baseCompany);
        List brandIds = baseCompanyService.getEnableBrandIdsByAppId(baseCompany.getAppId());
        panoramaVo.setBrandList(brandIds);
        List list = mobileDesignPlanProductService.getDesignPlanProductList2C(panoramaVo);
        return new ResponseEnvelope(true,list);
    }

    private SysUser getUserByPanoramaVo(PanoramaVo panoramaVo){

        if(panoramaVo == null){
            return null;
        }
        Integer userId = null;
        if(panoramaVo.getDesignPlanRecommendedId() != null && panoramaVo.getDesignPlanRecommendedId() > 0 ){
            DesignPlanRecommended planRecommend = designPlanRecommendedService.get(panoramaVo.getDesignPlanRecommendedId());
            if(planRecommend == null){
                logger.error("找不到该推荐方案,id:" + panoramaVo.getDesignPlanRecommendedId());
                return null;
            }
            userId = planRecommend.getUserId();
        }else if(panoramaVo.getDesignPlanRenderSceneId() != null && panoramaVo.getDesignPlanRenderSceneId() > 0){
            DesignPlanRenderScene planRenderScene = designPlanRenderSceneService.get(panoramaVo.getDesignPlanRenderSceneId());
            if(planRenderScene == null){
                logger.error("找不到该效果图方案,id：" + panoramaVo.getDesignPlanRenderSceneId());
            }
            userId = planRenderScene.getUserId();
        }else if(panoramaVo.getPlanId() != null && panoramaVo.getPlanId() > 0){
            DesignPlan designPlan = designPlanService.get(panoramaVo.getPlanId());
            if(designPlan == null){
                logger.error("找不到该设计方案，id:" + panoramaVo.getPlanId());
            }
            userId = designPlan.getUserId();
        }
        if(userId == null){
            logger.error("未找到方案相关用户信息，panoramaVo:" + panoramaVo.toString());
            return null;
        }
        SysUser sysUser = sysUserService.get(userId);
        return sysUser;
    }
}
