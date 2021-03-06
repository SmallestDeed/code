package com.sandu.web.product.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nork.common.model.LoginUser;
import com.sandu.cache.service.RedisService;
import com.sandu.common.LoginContext;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.util.PlatformConstant;
import com.sandu.platform.BasePlatform;
import com.sandu.product.model.*;
import com.sandu.product.model.result.SearchProductGroupDetail;
import com.sandu.product.model.result.SearchProductGroupResult;
import com.sandu.product.service.*;
import com.sandu.system.model.ResModel;
import com.sandu.system.model.ResPic;
import com.sandu.system.service.BasePlatformService;
import com.sandu.system.service.ResModelService;
import com.sandu.system.service.ResPicService;
import com.sandu.user.model.SysUser;
import com.sandu.user.model.UserSO;
import com.sandu.user.service.SysUserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @version V1.0
 * @Title: BaseProductController.java
 * @Package com.sandu.product.controller
 * @Description:产品模块-产品库Controller
 * @createAuthor pandajun
 * @CreateDate 2015-06-15 17:01:37
 */
@Controller
@RequestMapping("/v1/miniprogram/product/baseproduct")
public class BaseProductController {
    private final static Gson gson = new Gson();
    private final static String CLASS_LOG_PREFIX = "[产品基础服务]:";
    private final static Logger logger = LoggerFactory.getLogger(BaseProductController.class);

    @Value("${miniprogram.platform.code}")
    private String miniprogramPlatformCode;

    @Value("${sanducloudhome.company.code}")
    private String companyCode;

    @Autowired
    private BaseProductService baseProductService;
    @Autowired
    private GroupProductService groupProductService;
    @Autowired
    private ProductCategoryRelService productCategoryRelService;
    @Autowired
    private BaseCompanyService baseCompanyService;
    @Autowired
    private BaseProductStyleService baseProductStyleService;
    @Autowired
    private BaseBrandService baseBrandService;
    @Autowired
    private GroupProductCollectService groupProductCollectService;
    @Autowired
    private ProductAttributeService productAttributeService;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private ResModelService resModelService;
    @Autowired
    private GroupProductDetailsService groupProductDetailsService;
    @Autowired
    private UserProductCollectService userProductCollectService;
    @Autowired
    private BasePlatformService basePlatformService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SysUserService sysUserService;


    /**
     * 产品详情接口
     *
     * @throws Exception
     */
    @RequestMapping(value = "/productdetails")
    @ResponseBody
    public ResponseEnvelope productDetailsWeb(@ModelAttribute("baseProduct") BaseProduct baseProduct,
                                              HttpServletRequest request, HttpServletResponse response) {
        String platformCode = request.getHeader("Platform-Code");
        if (null == baseProduct || null == baseProduct.getId() || 0 == baseProduct.getId()) {
            return new ResponseEnvelope(false, "产品ID为为空");
        }
        UserSO userSo = new UserSO();
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser != null) {
            userSo = new UserSO();
            if (loginUser != null) {
                userSo.setUserId(loginUser.getId());
            }
        }
        userSo.setPlatformCode(platformCode);
        BaseCompany baseCompany;
        switch(platformCode)
        {
            case "miniProgram":
            case "brand2c":
            default:
                baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Referer"));
                if (baseCompany == null)
                    baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Custom-Referer"));
                break;
            case "mobile2b":
                if (loginUser == null) return new ResponseEnvelope(false,"用户未登陆!!!");
                SysUser user = sysUserService.get(loginUser.getId());
                baseCompany = baseCompanyService.get(user.getBusinessAdministrationId());
                break;
        }
        if(!Objects.equals(PlatformConstant.PLATFORM_CODE_TOB_PC,platformCode)){
            if (baseCompany == null)
                return new ResponseEnvelope(false, "未获取到公司");
            if (companyCode.equals(baseCompany.getCompanyCode())&&"miniProgram".equals(platformCode)) {
                baseProduct.setIsSandu(1);
            }
            baseProduct.setCompanyId(baseCompany.getId());
        }

        //获取当前平台
        BasePlatform basePlatform = basePlatformService.getBasePlatform(platformCode);
        if (basePlatform == null) {
            return new ResponseEnvelope(false, "未知的平台");
        }
        baseProduct.setPlatformId(basePlatform.getId());
        baseProduct.setPlatformBussinessType(basePlatform.getPlatformBussinessType());
        logger.info(CLASS_LOG_PREFIX + "根据产品ID查询产品详情:{}", baseProduct.toString());
        ProducDetail productDetail = baseProductService.getProducDetail(baseProduct, userSo);
        logger.info(CLASS_LOG_PREFIX + "根据产品ID查询产品详情完成:{}", gson.toJson(productDetail));
        if (null == productDetail) {
            return new ResponseEnvelope(false, "该产品不存在");
        }
        if (null != baseProduct.getParentProductId()) {
            productDetail.setPlanProductId(baseProduct.getParentProductId());
        }
        return new ResponseEnvelope(true, "success", productDetail);
    }

    /**
     * 组合产品详情接口
     *
     * @throws Exception
     */
    @RequestMapping(value = "/groupproductdetails", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getGroupProductDetails(@RequestParam(required = false) Integer gruopProductId,
                                                   HttpServletRequest request, HttpServletResponse response) {
        if (null == gruopProductId) {
            return new ResponseEnvelope(false, "组合产品ID缺失");
        }
        GroupProduct groupProduct = groupProductService.getMoreInfoById(gruopProductId);
        if (groupProduct == null) {
            return new ResponseEnvelope(false, "找不到该组合产品:" + gruopProductId);
        }
        SearchProductGroupResult groupProductResult = new SearchProductGroupResult();
        SearchProductGroupDetail groupDetail = null;
        List<SearchProductGroupDetail> details = new ArrayList<>();
        // 风格信息 ->start
        if (StringUtils.isNotBlank(groupProduct.getProductStyleIdInfo())) {
            JSONObject styleJson = JSONObject.fromObject(groupProduct.getProductStyleIdInfo());
            List<String> styleInfoList = baseProductStyleService.getProductStyleInfo(styleJson);
            StringBuffer stringBuffer = new StringBuffer("");
            for (String str : styleInfoList) {
                stringBuffer.append(str + "  ");
            }
            groupProductResult.setProductStyle(stringBuffer.toString());
            groupProductResult.setProductStyleInfoList(styleInfoList);
        }
        // 风格信息 ->end


        groupProductResult.setGroupConfig(groupProduct.getLocation());
        groupProductResult.setGroupId(groupProduct.getId());
        groupProductResult.setGroupCode(groupProduct.getGroupCode());
        groupProductResult.setGroupName(groupProduct.getGroupName());
        // 组合品牌,风格,描述信息,封面url->start
        groupProductResult.setBrandId(groupProduct.getBrandId());
        groupProductResult.setBrandName(groupProduct.getBrandName());
        groupProductResult.setStyleName(groupProduct.getStyleName());
        groupProductResult.setRemark(groupProduct.getRemark());
        groupProductResult.setPicPath(groupProduct.getPicPath());
        if (groupProduct.getProductType() == null || "".equals(groupProduct.getProductType())) {
            groupProductResult.setProductType("无");
        } else {
            groupProductResult.setProductType(groupProduct.getProductType());
        }
        // 组合品牌,风格,描述信息,封面url->end

		/* 当前登录人是否已经收藏该产品组合 */
        /*
		 * UserSO userSo = userSessionService.getUserSessionObjectBySessionId(request.
		 * getRequestedSessionId(), request.getSession().getMaxInactiveInterval());
		 */
        UserSO userSo = null;
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser != null) {
            userSo = new UserSO();
            userSo.setUserId(loginUser.getId());
        }
        Integer count = 0;
        if (null != userSo && userSo.getUserId() > 0) {
            GroupProductCollectSearch groupProductCollectSearch = new GroupProductCollectSearch();
            groupProductCollectSearch.setUserId(userSo.getUserId());
            groupProductCollectSearch.setGroupId(groupProduct.getId());
            groupProductCollectService.getCount(groupProductCollectSearch);
        }

        if (count > 0) {
            groupProductResult.setIsFavorite(1);
        } else {
            groupProductResult.setIsFavorite(0);
        }
        Double totalPrice = 0.00;
		/* 查询 组合产品明细列表 */
        GroupProductDetails groupProductDetails = new GroupProductDetails();
        groupProductDetails.setGroupId(groupProduct.getId());
        groupProductDetails.setOrder(" sorting ");
        List<GroupProductDetails> gpdList = groupProductDetailsService.getList(groupProductDetails);

        for (GroupProductDetails detailEntity : gpdList) {
            BaseProduct baseProduct = baseProductService.get(detailEntity.getProductId());

            groupDetail = new SearchProductGroupDetail();

			/* 在组合产品查询列表 中 填充产品属性 */
            if (detailEntity.getProductId() > 0) {
                Map<String, String> map = new HashMap<String, String>();
                map = productAttributeService.getPropertyMap(detailEntity.getProductId());
                groupDetail.setPropertyMap(map);
            }

            if (baseProduct != null) {
                groupDetail.setProductId(detailEntity.getProductId());
                int isMain = detailEntity.getIsMain() == null ? 0 : detailEntity.getIsMain();
                groupDetail.setIsMainProduct(isMain);
                Double salePrice = baseProduct.getSalePrice() == null ? 0 : baseProduct.getSalePrice();
				/* totalPrice 组合总的价格 */
                totalPrice = totalPrice + salePrice;
                if (baseProduct.getPicId() != null && baseProduct.getPicId() > 0) {
                    ResPic resPic = resPicService.get(baseProduct.getPicId());
                    groupDetail.setPicPath(resPic == null ? "" : resPic.getPicPath());
                }

				/* 媒介类型.如果没有值，则表示为web前端（2） */
                String modelId = baseProductService.getU3dModelId("", baseProduct);
                if (StringUtils.isNotBlank(modelId)) {
                    ResModel resModel = resModelService.get(Integer.valueOf(modelId));
                    if (resModel != null) {
                        File modelFile = new File(resModel.getModelPath());
                        if (modelFile.exists()) {
                            groupDetail.setU3dModelPath(resModel.getModelPath());
                        }
                    }
                }

            }
            details.add(groupDetail);
        }
        groupProductResult.setGroupProductDetails(details);
        Double groupPrice = groupProduct.getGroupPrice() == null ? 0 : groupProduct.getGroupPrice();
        if (groupPrice == 0) {
            groupProductResult.setGroupPrice(totalPrice);
        } else {
            groupProductResult.setGroupPrice(groupPrice);
        }

        return new ResponseEnvelope(true, "success", groupProductResult);
    }

    /**
     * 获取厂商下的产品类目
     *
     * @throws Exception
     */
    @RequestMapping(value = "/productcategory")
    @ResponseBody
    public ResponseEnvelope getAllProductCategory(HttpServletRequest request) {
        // 查询是否是访问公司
        BaseCompany baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Referer"));
        if (baseCompany == null) {
            baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Custom-Referer"));
        }

        if (null == baseCompany) {
            return new ResponseEnvelope(false, "没有获取到厂商信息");
        }

        /*三度云享家公司查询所有产品分类*/
        if(companyCode.equals(baseCompany.getCompanyCode())){
            baseCompany.setId(0);
        }

        List<List<ProCategoryPo>> categories = null;
        //从缓存中获取公司可见产品范围信息
        String productCategoryStr = redisService.getMap("BaseProductCategory", "MiniProgramProductCategory"+baseCompany.getId());
        if(!StringUtils.isEmpty(productCategoryStr)){
            Type categoryType = new TypeToken<List<List<ProCategoryPo>>>() {}.getType();
            categories = gson.fromJson(productCategoryStr, categoryType);
        }else {
            logger.info(CLASS_LOG_PREFIX + "根据公司分类查询分类--companyId:{}", null == baseCompany ? null : baseCompany.getId());
            categories = baseProductService.getAllProductCategory(baseCompany.getId());
            if(categories.get(0)!=null && categories.get(0).size()>0){
                redisService.addMap("BaseProductCategory","MiniProgramProductCategory"+baseCompany.getId(),gson.toJson(categories));
            }
        }

        if (categories.get(0)==null) {
            return new ResponseEnvelope(false, "该公司没有关联任何类目");
        }
        return new ResponseEnvelope(true, "success", categories);
    }

    /**
     * 根据厂商和分类查询产品接口
     *
     * @param request
     * @param productCategoryRel
     * @return Object
     */
    @RequestMapping(value = "/searchallproduct")
    @ResponseBody
    public ResponseEnvelope searchallproduct(@ModelAttribute ProductCategoryRel productCategoryRel, PageModel pageModel,
                                             HttpServletRequest request, HttpServletResponse response) {
        String msg = "";
        int total = 0;
        BaseCompany baseCompany;
        List<CategoryProductResult> list = null;
        if (StringUtils.isBlank(productCategoryRel.getCategoryCode())) {
            msg = "参数categoryCode不能为空";
            return new ResponseEnvelope(false, msg);
        }
        if (null != pageModel && 0 != pageModel.getPageSize()) {
            productCategoryRel.setStart(pageModel.getStart());
            productCategoryRel.setLimit(pageModel.getPageSize());
        } else {
            productCategoryRel.setLimit(PageModel.DEFAULT_PAGE_PAGESIZE);
        }
        //获取当前平台
        BasePlatform basePlatform = basePlatformService.getBasePlatform(miniprogramPlatformCode);
        if (basePlatform == null) {
            return new ResponseEnvelope(false, "未知的平台");
        }
        productCategoryRel.setPlatformId(basePlatform.getId());
        baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Referer"));
        if (baseCompany == null) {
            baseCompany = baseCompanyService.getCompanyByDomainUrl(request.getHeader("Custom-Referer"));
        }
        // 处理多个分类
        if (productCategoryRel.getCategoryCode().contains(",")) {
            String[] arr = productCategoryRel.getCategoryCode().split(",");
            productCategoryRel.setCategoryIdList(Arrays.asList(arr));
        } else {
            productCategoryRel.setCategoryCode("." + productCategoryRel.getCategoryCode() + ".");
        }

        if (null != productCategoryRel.getBrandId() && productCategoryRel.getBrandId() != 0) {
            List<Integer> brandList = new ArrayList<>();
            brandList.add(productCategoryRel.getBrandId());
            productCategoryRel.setBrandIds(brandList);
        }
//        else {
//            // 查询是否是访问公司
//            if (null != baseCompany) {
//                // 查询公司品牌
//                List<Integer> brandList = baseBrandService.queryBrandListByCompanyId(baseCompany.getId());
//                if (null != brandList && !brandList.isEmpty()) {
//                    productCategoryRel.setBrandIds(brandList);
//                } else {
//                    return new ResponseEnvelope(true, "该公司下没有关联品牌");
//                }
//                 /*三度云享家分类下的所有产品可见，不进行品牌过滤*/
//                if(companyCode.equals(baseCompany.getCompanyCode())){
//                    productCategoryRel.setBrandIds(null);
//                }
//
//            }
//        }

        //WEIXIN-123, Update by steve
        if(!companyCode.equals(baseCompany.getCompanyCode())){
            List<Integer> enableBrandIdList = null;
            if(PlatformConstant.PLATFORM_CODE_MINI_PROGRAM.equalsIgnoreCase(basePlatform.getPlatformCode())) {
               enableBrandIdList = baseCompanyService.getEnableBrandIdsByAppId(baseCompany.getAppId());
            }else {
                enableBrandIdList = baseCompanyService.getBrandIdListByCompanyId(baseCompany.getId(), null,
                        basePlatform.getPlatformCode());

            }
            if (enableBrandIdList != null && enableBrandIdList.size() > 0) {
                productCategoryRel.setBrandIds(enableBrandIdList);
            }
        }


        logger.info(CLASS_LOG_PREFIX + "根据公司分类查询分类下产品列表总记录数:{}", productCategoryRel.toString());
        total = productCategoryRelService.findAllProductCountByCode(productCategoryRel);
        logger.info(CLASS_LOG_PREFIX + "根据公司分类查询分类下产品列表总记录数完成:{}", total);
        if (total > 0) {
            UserSO userSo = null;
            LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
            if (loginUser != null) {
                userSo = new UserSO();
                userSo.setUserId(loginUser.getId());
            }
            list = productCategoryRelService.findAllProductResultByCode(productCategoryRel, userSo);
        } else {
            return new ResponseEnvelope(true, "该分类下没有产品");
        }

        return new ResponseEnvelope(true, "success", list, total);
    }

    /**
     * 根据产品ID查询出所属分类
     *
     * @param request
     * @param productIds
     * @return Object
     */
    @RequestMapping(value = "/searchproductcategory", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope searchproductcategory(@RequestParam String productIds, HttpServletRequest request,
                                                  HttpServletResponse response) {
        if (null == productIds || productIds.isEmpty()) {
            return new ResponseEnvelope<>(false, "缺少产品ID参数");
        }
        List<Integer> idList = new ArrayList<>();
        if (productIds.contains(",")) {
            String[] arr = productIds.split(",");
            for (String str : arr) {
                idList.add(new Integer(str));
            }
        } else {
            idList.add(new Integer(productIds));
        }
        List<ProCategoryPo> categoryList = productCategoryRelService.findProductCategoryByProductId(idList);
        if (null == categoryList || categoryList.size() == 0) {
            return new ResponseEnvelope<>(false, "这个产品没有关联任何分类");
        }
        return new ResponseEnvelope<>(true, "success", categoryList);
    }


    /**
     * 我的收藏列表根据产品ID查询出所属分类
     *
     * @param request
     * @param request
     * @return Object
     */
    @RequestMapping(value = "/searchfavoriteproductcategory", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope searchproductcategory(HttpServletRequest request,
                                                  HttpServletResponse response) {
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
        if (loginUser == null) {
            return new ResponseEnvelope(false, "请登录!");
        }
        CollectionQueryModel model = new CollectionQueryModel();
        model.setLoginUser(loginUser);
        List<Integer> idList = userProductCollectService.collectionList2(model);
        if (idList == null || idList.isEmpty()) {
            return new ResponseEnvelope<>(false, "该用户没有收藏任何产品");
        }
        List<ProCategoryPo> categoryList = productCategoryRelService.findProductCategoryByProductId(idList);
        if (null == categoryList || categoryList.size() == 0) {
            return new ResponseEnvelope<>(false, "这个产品没有关联任何分类");
        }
        return new ResponseEnvelope<>(true, "success", categoryList);
    }


    /**
     * 获取第三级分类获取第四级和第五级分类
     *
     * @throws Exception
     */
    @RequestMapping(value = "/exactproductcategory",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEnvelope getExactProductCategory(@RequestParam(required = true) Integer pid,HttpServletRequest request) {

        List<ProCategoryPo> categoryPoList = baseProductService.getExactProductCategory(pid);
        if(categoryPoList==null||categoryPoList.size()==0){
            return  new ResponseEnvelope(false,"无法获取到第四五级分类信息");
        }

        return new ResponseEnvelope<>(true, "success", categoryPoList);
    }



}
