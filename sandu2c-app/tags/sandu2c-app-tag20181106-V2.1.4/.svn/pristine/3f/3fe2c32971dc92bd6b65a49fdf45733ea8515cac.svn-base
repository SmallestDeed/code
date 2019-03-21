package com.sandu.web.pano.controller;

import com.google.gson.Gson;
import com.nork.common.model.LoginUser;
import com.sandu.common.LoginContext;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.design.model.ProductsCostType;
import com.sandu.pano.model.scene.PanoramaVo;
import com.sandu.platform.BasePlatform;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.service.BaseCompanyService;
import com.sandu.product.service.MobileDesignPlanProductService;
import com.sandu.system.service.BasePlatformService;
import com.sandu.user.model.UserSO;
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
@RequestMapping("/v1/tocmobile/pages/vr720")
public class PanoramaController {
    private final static Gson gson = new Gson();
    private final static Logger logger = LoggerFactory.getLogger(PanoramaController.class);
    private final static String CLASS_LOG_PREFIX = "[720场景产品清单列表服务]";
    private static final String DICTIONARY_PRODUCT_TYPE = "productType";

    @Value("${mobile2c.platform.code}")
    private String mobile2cPlatformCode;

    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private BasePlatformService basePlatformService;
    @Autowired
    private MobileDesignPlanProductService mobileDesignPlanProductService;


    /**
     * 720场景产品清单列表
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getproductscost")
    @ResponseBody
    public ResponseEnvelope list(@ModelAttribute("PanoramaVo") PanoramaVo panoramaVo, HttpServletRequest request,
                                 HttpServletResponse response) {
        ResponseEnvelope res = null;
        List<ProductsCostType> list = null;
//        try {
//            List<SysDictionaryBo> sysDictionaryBOList = null;
            //查询是否是访问公司
            BaseCompany baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Referer"));
            if (baseCompany == null) {
                baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Custom-Referer"));
                if (baseCompany == null) {
                    return new ResponseEnvelope(false, "未获取到公司");
                }
            }
            panoramaVo.setCompanyId(baseCompany.getId());
//            if (null != baseCompany) {
//                //查询联盟
//                List<Integer> brandList = baseBrandService.queryBrandListByCompanyId(baseCompany.getId());
//                panoramaVo.setOwnBrandIdList(brandList);
//                List<Integer> brandList2 = baseBrandService.getAllBrandIdsByCompanyId(baseCompany.getId());
//                if (brandList2 != null && brandList2.size() > 0) {
//                    for (Integer brandId : brandList2) {
//                        brandList.add(brandId);
//                    }
//                }
//                panoramaVo.setBrandList(brandList);
//                panoramaVo.setCompanyId(baseCompany.getId());
//
//                String productVisibilityRange = baseCompany.getProductVisibilityRange();
//                if (null != productVisibilityRange && !"".equals(productVisibilityRange)) {
//                    //将可见产品范围转成list
//                    List<Integer> visibilityRangeIdList = Utils.getIntegerListFromStringList(productVisibilityRange);
//                    //根据可见范围查询到所有可见的分类编码
//                    List<String> visibilityRangeCodeList = baseProductService.getCodeListByIdList(visibilityRangeIdList);
//                    //根据所有可见范围的code获得数据字典的大小类list
//                    String type = DICTIONARY_PRODUCT_TYPE;
//                    sysDictionaryBOList = sysDictionaryService.getTypeValueByValueKeys(type,visibilityRangeCodeList);
//                }
//            }else{
//                return new ResponseEnvelope(false,"未获取到该公司");
//            }
//            panoramaVo.setSysDictionaryBOList(sysDictionaryBOList);
            UserSO userSo = null;
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            logger.info(CLASS_LOG_PREFIX + "获取登录用户信息:getUserFromCache:{}.", null == loginUser ? null : loginUser.toString());
            if (loginUser != null) {
                userSo = new UserSO();
                userSo.setUserId(loginUser.getId());
                panoramaVo.setUserId(loginUser.getId());
            }

            //从缓存中获取当前平台
            BasePlatform basePlatform = basePlatformService.getBasePlatform(mobile2cPlatformCode);
            if (basePlatform == null) {
                return new ResponseEnvelope(false, "未知的平台");
            }
            panoramaVo.setPlatformId(basePlatform.getId());

            list = mobileDesignPlanProductService.getDesignPlanProductList2C(panoramaVo);
//            list = panoramaService.getProductsCost(panoramaVo, userSo);
            logger.info(CLASS_LOG_PREFIX + "720场景产品清单列表:{}", gson.toJson(list));
            Integer totalCount = 0;
            if (null != list && list.size() > 0) {
                totalCount = list.size();
            } else {
                return new ResponseEnvelope(true, "success", "此案例没有相关公司品牌下的产品");
            }
            res = new ResponseEnvelope(true, "success", list, totalCount);
//        } catch (Exception e) {
//            logger.error(CLASS_LOG_PREFIX + "720场景产品清单列表:{}, Exception:{}.", gson.toJson(list), e.getMessage());
//            res = new ResponseEnvelope(false, "产品清单显示失败");
//            e.printStackTrace();
//        }

        return res;
    }


}
