package com.sandu.search.controller.product;

import com.sandu.common.LoginContext;
import com.sandu.search.common.constant.*;
import com.sandu.search.common.tools.*;
import com.sandu.search.entity.common.PageVo;
import com.sandu.search.entity.elasticsearch.constant.AggregationConstant;
import com.sandu.search.entity.elasticsearch.dco.MultiMatchField;
import com.sandu.search.entity.elasticsearch.dco.ValueOffset;
import com.sandu.search.entity.elasticsearch.dco.ValueRange;
import com.sandu.search.entity.elasticsearch.index.ProductIndexMappingData;
import com.sandu.search.entity.elasticsearch.po.ProductPo;
import com.sandu.search.entity.elasticsearch.response.SearchObjectResponse;
import com.sandu.search.entity.elasticsearch.search.product.ProductSearchAggregationVo;
import com.sandu.search.entity.elasticsearch.search.product.ProductSearchMatchVo;
import com.sandu.search.entity.product.universal.vo.BrandAndNameProductVo;
import com.sandu.search.entity.product.vo.ProductCategoryVo;
import com.sandu.search.entity.response.universal.UniversalSearchResultResponse;
import com.sandu.search.entity.user.LoginUser;
import com.sandu.search.exception.ProductSearchException;
import com.sandu.search.service.product.ProductSearchService;
import com.sandu.search.storage.company.BrandMetaDataStorage;
import com.sandu.search.storage.company.CompanyMetaDataStorage;
import com.sandu.search.storage.design.DesignPlanProductMetaDataStorage;
import com.sandu.search.storage.product.ProductCategoryMetaDataStorage;
import com.sandu.search.storage.system.SystemDictionaryMetaDataStorage;
import com.sandu.search.strategy.ProductReplaceSearchFullPraveStrategy;
import com.sandu.search.strategy.ProductReplaceSearchHeightStrategy;
import com.sandu.search.strategy.ProductTypeMatchStrategy;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.max.ParsedMax;
import org.elasticsearch.search.aggregations.metrics.min.ParsedMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * [通用版]替换产品接口
 *
 * @date 20171222
 * @auth pengxuangang
 */
@Slf4j
@RestController
@EnableAutoConfiguration
@RequestMapping("/universal/product/replace")
public class ToBProductReplaceController {

    private final static String CLASS_LOG_PREFIX = "[通用版]替换产品接口:";

    //发布状态已选中标识
    private static final int SELECTED_POUTAWAY_FLAG = 1;

    private final DomainUtil domainUtil;
    private final HttpServletRequest request;
    private final BrandMetaDataStorage brandMetaDataStorage;
    private final ProductSearchService productSearchService;
    private final CompanyMetaDataStorage companyMetaDataStorage;
    private final ProductTypeMatchStrategy productTypeMatchStrategy;
    private final ProductCategoryMetaDataStorage productCategoryMetaDataStorage;
    private final SystemDictionaryMetaDataStorage systemDictionaryMetaDataStorage;
    private final DesignPlanProductMetaDataStorage designPlanProductMetaDataStorage;

    @Autowired
    public ToBProductReplaceController(DomainUtil domainUtil, HttpServletRequest request, BrandMetaDataStorage brandMetaDataStorage, ProductSearchService productSearchService, CompanyMetaDataStorage companyMetaDataStorage, ProductTypeMatchStrategy productTypeMatchStrategy, ProductCategoryMetaDataStorage productCategoryMetaDataStorage, SystemDictionaryMetaDataStorage systemDictionaryMetaDataStorage, DesignPlanProductMetaDataStorage designPlanProductMetaDataStorage) {
        this.domainUtil = domainUtil;
        this.request = request;
        this.brandMetaDataStorage = brandMetaDataStorage;
        this.productSearchService = productSearchService;
        this.companyMetaDataStorage = companyMetaDataStorage;
        this.productTypeMatchStrategy = productTypeMatchStrategy;
        this.productCategoryMetaDataStorage = productCategoryMetaDataStorage;
        this.systemDictionaryMetaDataStorage = systemDictionaryMetaDataStorage;
        this.designPlanProductMetaDataStorage = designPlanProductMetaDataStorage;
    }


    /**
     * 产品替换搜索列表[ProductBaseConditionVo]
     *
     * @param searchKeyword           搜索关键字
     * @param productTypeValue        产品大类
     * @param productTypeSmallValue   产品小类
     * @param start                   数据起始数
     * @param dataSize                单页数据条数
     * @param productCategoryId       产品分类ID
     * @param productCategoryLongCode 产品分类长编码
     * @param designPlanProductId     设计方案产品ID
     * @param designPlanType          设计方案类型(Desc: com.sandu.search.common.constant.DesignPlanType)
     * @param productId               产品ID(用于新增柜类产品长度过滤)
     * @param putawayFlag             PC端蓝标标识(选中后内部用户仅能查看已发布产品)0:未选中, 1:选中
     * @param templateZoneFlag        样板房天花区域标识(用于天花类型属性判断) 0:无区域信息, 1:有区域信息
     * @param ceilingRandomPatchFlowerFlag 天花随机拼花标识(0:无, 1:有)
     * @param msgId                   消息ID(原样返回)
     * @return
     */
    @RequestMapping("/list")
    UniversalSearchResultResponse queryProductReplaceByProductCondition(@RequestParam(required = false) String searchKeyword,
                                                                        Integer productTypeValue,
                                                                        Integer productTypeSmallValue,
                                                                        Integer start,
                                                                        Integer dataSize,
                                                                        Integer productCategoryId,
                                                                        String productCategoryLongCode,
                                                                        Integer designPlanProductId,
                                                                        @RequestParam(required = false) Integer designPlanType,
                                                                        Integer productId,
                                                                        @RequestParam(required = false) Integer putawayFlag,
                                                                        @RequestParam(required = false) Integer templateZoneFlag,
                                                                        @RequestParam(required = false) Integer ceilingRandomPatchFlowerFlag,
                                                                        @RequestParam(required = false) Integer msgId) {
        //参数转换
        putawayFlag = null == putawayFlag ? 0 : putawayFlag;
        templateZoneFlag = null == templateZoneFlag ? 0 : templateZoneFlag;
        ceilingRandomPatchFlowerFlag = null == ceilingRandomPatchFlowerFlag ? 0 : ceilingRandomPatchFlowerFlag;
        productTypeValue = (null == productTypeValue) ? 0 : productTypeValue;
        productTypeSmallValue = (null == productTypeSmallValue) ? 0 : productTypeSmallValue;
        designPlanProductId = (null == designPlanProductId) ? 0 : designPlanProductId;

        //传输对象
        ProductSearchMatchVo productSearchMatchVo = new ProductSearchMatchVo();

        /******************************************* 登录信息 *************************************/
        //获取用户登录信息
        LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);

        if (null == loginUser) {
            log.warn(CLASS_LOG_PREFIX + "获取用户信息未空，用户未登录。");
            return new UniversalSearchResultResponse(false, CLASS_LOG_PREFIX + "获取用户信息未空，用户未登录。", msgId);
        }

        //厂商/经销商ID，用以以上两类用户确定企业经营范围
        int companyId = loginUser.getBusinessAdministrationId();
        //用户类型(1:内部, 2:外部)--内部用户可查看多个状态下的产品，外部用户只能查看已发布产品
        Integer userType = loginUser.getUserType();
        //用户ID(用于计算用户已使用产品次数)
        Integer userId = loginUser.getId();
        //用户菜单权限
        List<String> roleCodeList = loginUser.getRoleCodeList();
        log.info(CLASS_LOG_PREFIX + "当前登录用户数据---companyId:{}, userType:{}, userId:{}, roleCodeList:{}.", new String[]{
                companyId + "",
                userType + "",
                userId + "",
                null == roleCodeList ? null : JsonUtil.toJson(roleCodeList)
        });

        //平台来源标识
        String platformCode = RequestHeaderUtil.getPlatformIdByRequest(request);
        //接口平台限制
        if (StringUtils.isEmpty(platformCode)
                || (!PlatformConstant.PLATFORM_CODE_TOB_PC.equals(platformCode)
                    && !PlatformConstant.PLATFORM_CODE_TOB_MOBILE.equals(platformCode))
                    && !PlatformConstant.PLATFORM_CODE_TOC_SITE.equals(platformCode)
                    && !PlatformConstant.PLATFORM_CODE_MINI_PROGRAM.equals(platformCode)) {
            log.warn(CLASS_LOG_PREFIX + "平台标识无效:{}.", platformCode);
            return new UniversalSearchResultResponse(false, CLASS_LOG_PREFIX + "平台标识无效:" + platformCode, msgId);
        }

        //产品搜索聚合字段
        List<ProductSearchAggregationVo> productSearchAggregationVoList = new ArrayList<>();

        //小程序标识，因为三度云享家会去掉小程序平台标识，会导致无法识别为小程序从而进行聚合数据
        boolean isMiniProgram = false;
        //公司信息获取
        if (PlatformConstant.PLATFORM_CODE_TOC_SITE.equals(platformCode)) {
            //品牌网站HOST解析
            String domainName = DomainUtil.getDomainNameByHost(request);
            log.info(CLASS_LOG_PREFIX + "公司域名-domainName:{}", domainName);

            if (StringUtils.isEmpty(domainName)) {
                log.info(CLASS_LOG_PREFIX + "品牌网站HOST解析失败 domainname is null!");
                return new UniversalSearchResultResponse(false, CLASS_LOG_PREFIX + "品牌网站HOST解析失败 domainname is null!:", msgId);
            }

            //获取为空则直接获取公司--(小程序有公司但没有公司域名)
            companyId = companyMetaDataStorage.getCompanyIdByDomainName(domainName);
            log.info(CLASS_LOG_PREFIX + "Brand2C公司ID获取完成! DomainName:{}, CompanyId:{}.", domainName, companyId);

            //限制为厂商用户类型
            userType = UserTypeConstant.USER_ROLE_TYPE_COMPANY;
        } else if (PlatformConstant.PLATFORM_CODE_MINI_PROGRAM.equals(platformCode)) {
            //小程序
            isMiniProgram = true;
            //小程序获取公司ID
            companyId = domainUtil.getCompanyIdInMiniProgramByRequest(request);
            log.info(CLASS_LOG_PREFIX + "小程序获取公司ID为:{}.", companyId);

            //小程序增加搜索结果三级分类ID聚合
            productSearchAggregationVoList.add(new ProductSearchAggregationVo(
                    AggregationConstant.THREE_LEVEL_CATEGORY_AGGREGATION,
                    QueryConditionField.QUERY_CONDITION_FIELD_PRODUCT_THIRDLEVEL_CATEGORYID,
                    100
            ));
        }

        //小程序三度云享家应用需单独处理--去掉品牌限制|平台限制
        if (companyMetaDataStorage.getSanduCompanyId() == companyId && PlatformConstant.PLATFORM_CODE_MINI_PROGRAM.equals(platformCode)) {
            log.info(CLASS_LOG_PREFIX + "已识别为三度云享家公司，移除品牌&平台限制! CompanyId:{}.", companyId);
            //品牌限制
            companyId = 0;
            userType = UserTypeConstant.USER_ROLE_TYPE_NORMAL;
            //平台限制
            platformCode = null;
            //标识公司-三度公司可无需检查是否保密状态
            productSearchMatchVo.setSanduCompany(true);
        }

        /******************************************* 白膜信息 *************************************/
        //白膜产品对象
        ProductIndexMappingData whiteMembraneProduct = null;
        //全铺长度
        int productFullPaveLength = 0;
        //获取白膜产品(后面可能需要白膜产品的信息做筛选)
        if (0 != designPlanProductId) {
            int productWhiteMembraneId = designPlanProductMetaDataStorage.getWhiteMembraneProductIdById(designPlanProductId, null == designPlanType ? DesignPlanType.TEMP_DESIGN_PLAN_TYPE : designPlanType);
            log.info(CLASS_LOG_PREFIX + "获取白膜产品(后面可能需要白膜产品的信息做筛选)完成!designPlanProductId:{}, productWhiteMembraneId:{},designPlanType:{}.", new Integer[]{
                    designPlanProductId,
                    productWhiteMembraneId,
                    designPlanType});
            if (0 != productWhiteMembraneId) {
                try {
                    whiteMembraneProduct = productSearchService.searchProductById(productWhiteMembraneId);
                } catch (ProductSearchException e) {
                    log.error(CLASS_LOG_PREFIX + "获取白膜产品(后面可能需要白膜产品的信息做筛选)失败!ProductSearchException:{}", e);
                    return new UniversalSearchResultResponse(false, CLASS_LOG_PREFIX + "获取白膜产品(后面可能需要白膜产品的信息做筛选)失败!ProductSearchException:" + e, msgId);
                }
            }

            //根据产品编码检查产品是否为白膜产品(区分用户是在设计方案场景中新增产品[无白膜]还是替换产品[有白膜])
            if (null != productId && 0 != productId && (null == whiteMembraneProduct || !whiteMembraneProduct.getProductCode().startsWith("baimo_"))) {
                //不是白膜则根据产品ID来查询
                try {
                    whiteMembraneProduct =  productSearchService.searchProductById(productId);
                } catch (ProductSearchException e) {
                    log.error(CLASS_LOG_PREFIX + "获取白膜产品(后面可能需要白膜产品的信息做筛选)失败!ProductSearchException:{}", e);
                    return new UniversalSearchResultResponse(false, CLASS_LOG_PREFIX + "获取白膜产品(后面可能需要白膜产品的信息做筛选)失败!ProductSearchException:" + e, msgId);
                }
                //长度转为白膜全铺长度
                if (null != whiteMembraneProduct && 0 < whiteMembraneProduct.getProductLength()) {
                    whiteMembraneProduct.setProductFullPaveLength(whiteMembraneProduct.getProductLength());
                }
            }
        }

        /************************* 构造分页对象 **************************************/
        PageVo pageVo = new PageVo();
        if (null == start) {
            start = IndexInfoQueryConfig.DEFAULT_SEARCH_DATA_START;
        }
        if (null == dataSize) {
            dataSize = IndexInfoQueryConfig.DEFAULT_SEARCH_DATA_SIZE;
        }
        pageVo.setStart(start);
        pageVo.setPageSize(dataSize);

        /************************* 构造产品搜索条件 **************************************/
        //单值多字段匹配
        List<MultiMatchField> multiMatchFieldList = new ArrayList<>();
        //产品分类长编码列表
        List<String> productCategoryLongCodeList = new ArrayList<>();

        //构造产品搜索对象--搜索关键字(产品编码>产品品牌名>产品名>产品型号名)
        if (!StringUtils.isEmpty(searchKeyword)) {
            List<String> fidleNameList = Arrays.asList(
                    QueryConditionField.QUERY_CONDITION_FIELD_PRODUCT_CODE,
                    QueryConditionField.QUERY_CONDITION_FIELD_PRODUCT_BRAND_NAME,
                    QueryConditionField.QUERY_CONDITION_FIELD_PRODUCT_NAME,
                    QueryConditionField.QUERY_CONDITION_FIELD_PRODUCT_MODEL_NUMBER);
            multiMatchFieldList.add(new MultiMatchField(searchKeyword, fidleNameList));
        }

        //构造产品搜索对象--产品大类
        if (0 != productTypeValue) {
            productSearchMatchVo.setProductTypeValue(productTypeValue);
        }

        //样板房天花区域标识
        /*20180524移除此次版本不发布需求
        if (ProductTypeValue.PRODUCT_TYPE_VALUE_CEILING == productTypeValue) {
            //无区域信息仅搜索墙面生成天花
            productSearchMatchVo.setTemplateZoneStatus(productSearchMatchVo.ONLY_WALL_FACE_ZONE);
            if (null != templateZoneFlag && 1 == templateZoneFlag) {
                //搜索所有天花
                productSearchMatchVo.setTemplateZoneStatus(productSearchMatchVo.ALL_TEMPLATE_ZONE);
            }
        }*/

        //构造产品搜索对象--随机拼花
        if (ProductPo.HAVE_CEILING_RANDOM_PATCH_FLOWER == ceilingRandomPatchFlowerFlag) {
            productSearchMatchVo.setCeilingRandomPatchFlowerFlag(ProductPo.HAVE_CEILING_RANDOM_PATCH_FLOWER);
        }

        //构造产品搜索对象--产品小类---根据产品大小类匹配产品小类规则
        //若无大小类则通过分类编码查询出大小类(品牌过滤会通过小类来区分)---会重装入大类小类
        if (0 == productTypeValue && 0 == productTypeSmallValue && !StringUtils.isEmpty(productCategoryLongCode)) {
            //大小类集合
            List[] productTypeListResult = productCategoryMetaDataStorage.getProductTypeByProductCode(productCategoryLongCode);
            if (null != productTypeListResult && 2 == productTypeListResult.length) {
                //大类
                productTypeValue = (Integer) productTypeListResult[0].get(0);
                //装入对象--大类
                productSearchMatchVo.setProductTypeValue(productTypeValue);
                //小类
                productSearchMatchVo.setProductTypeSmallValueList(productTypeListResult[1]);
            }
        } else {
            //普通大小类过滤
            List<Integer> productSmallTypeList = productTypeMatchStrategy.matchProductSmallTypeRule(productTypeValue, productTypeSmallValue);
            if (null != productSmallTypeList && 0 < productSmallTypeList.size()) {
                productSearchMatchVo.setProductTypeSmallValueList(productSmallTypeList);
            }
        }

        if (0 < companyId) {
            //构造产品搜索对象--公司品牌ID
            //厂商-经销商-小程序普通用户
            if (UserTypeConstant.USER_ROLE_TYPE_COMPANY == userType
                    || UserTypeConstant.USER_ROLE_TYPE_DEALER == userType
                    || (isMiniProgram && UserTypeConstant.USER_ROLE_TYPE_NORMAL == userType)) {
                //品牌列表
                List<Integer> companyBrandIdList;
                //厂商-小程序普通用户
                if (UserTypeConstant.USER_ROLE_TYPE_COMPANY == userType
                        || (isMiniProgram && UserTypeConstant.USER_ROLE_TYPE_NORMAL == userType)) {
                    //公司品牌
                    companyBrandIdList = brandMetaDataStorage.queryBrandIdListByCompanyIdList(Collections.singletonList(companyId));
                } else {
                    //经销商品牌
                    companyBrandIdList = companyMetaDataStorage.queryDealerBranIdListByDealerId(companyId);
                }

                if (null != companyBrandIdList && 0 < companyBrandIdList.size()) {
                    //公司品牌限制
                    productSearchMatchVo.setCompanyBrandIdList(companyBrandIdList);
                    log.info(CLASS_LOG_PREFIX + "查询企业/经销商品牌列表完成! CompanyId/DealerId:{}, companyBrandIdList:{}.", companyId, JsonUtil.toJson(companyBrandIdList));
                }

                //构造产品搜索对象--公司产品可见范围
                //获取公司产品可见范围
                String companyProductVisibilityRange = companyMetaDataStorage.getCompanyProductVisibilityRangeByCompanyId(companyId);
                if (!StringUtils.isEmpty(companyProductVisibilityRange)) {
                    //公司产品可见范围ID列表--产品分类表ID
                    String[] companyProductVisibilityRangeIds = companyProductVisibilityRange.split(",");
                    //分类编码列表
                    List<String> categoryCodeList = productCategoryMetaDataStorage.queryCategoryCodeByCategoryIds(companyProductVisibilityRangeIds);
                    //小类列表--排除非同产品大类的小类ID列表(并将字典valuekey转换为字典值)
                    List<Integer> productSmallTypeList = systemDictionaryMetaDataStorage.excludeProductSmallTypeByKeyList(categoryCodeList, productTypeValue);
                    productSearchMatchVo.setCompanyProductVisibilityRangeIdList(productSmallTypeList);

                    //公司可见范围大小类详情
                    Map<Integer, List<Integer>> companyAliveTypeMap = systemDictionaryMetaDataStorage.queryProductTypeByKeyList(categoryCodeList);
                    if (null != companyAliveTypeMap && 0 < companyAliveTypeMap.size()) {
                        productSearchMatchVo.setCompanyAliveTypeMap(companyAliveTypeMap);
                    }
                }
            }
        }

        //构造产品搜索对象--产品发布状态
        List<Integer> putawayStatusList = new ArrayList<>(4);
        //默认只能查询已发布------------普通用户-设计公司-装修公司-学校不涉及到品牌概念，只要是已发布，都可见
        putawayStatusList.add(ProductPo.PUTAWAYSTATE_ALEARY_PUBLISH);

        if (UserTypeConstant.USER_ROLE_TYPE_INNER == userType && SELECTED_POUTAWAY_FLAG != putawayFlag) {
            //内部用户可查询出已上架和测试中和发布中产品
            putawayStatusList.add(ProductPo.PUTAWAYSTATE_UP_SHELVES);
            putawayStatusList.add(ProductPo.PUTAWAYSTATE_TESTING);
            putawayStatusList.add(ProductPo.PUTAWAYSTATE_PUBLICHING);
            //内部用户标识
            productSearchMatchVo.setInnerUser(true);
        }
        productSearchMatchVo.setPutawayStatusList(putawayStatusList);

        //构造产品搜索对象--产品库权限
        if (PlatformConstant.PLATFORM_CODE_TOB_PC.equals(platformCode) && null != roleCodeList && 0 < roleCodeList.size()) {
            //检查用户是否有权限
            for (String roleCode : roleCodeList) {
                if (MenuAuthorityCode.PRODUCT_LIBRARY.equals(roleCode)) {
                    //有权限则不需要平台过滤
                    productSearchMatchVo.setProductLibraryAuth(true);
                    break;
                }
            }
        }

        //构造产品搜索对象--平台编码
        if (UserTypeConstant.USER_ROLE_TYPE_INNER != userType || !PlatformConstant.PLATFORM_CODE_TOB_PC.equals(platformCode)) {
            //平台过滤仅对非内部用户生效且非PC2B平台
            productSearchMatchVo.setPlatformCode(platformCode);
        }

        //构造产品搜索对象--产品分类ID列表
        if (null != productCategoryId) {
            productSearchMatchVo.setProductCategoryIdList(Collections.singletonList(productCategoryId));
        }

        //构造产品搜索对象--产品分类长编码
        if (!StringUtils.isEmpty(productCategoryLongCode)) {
            //查询出符合条件分类
            List<String> categoryCodeList = productCategoryMetaDataStorage.queryAllCategoryCodeByCodeName(productCategoryLongCode);
            productCategoryLongCodeList.addAll(categoryCodeList);
        }

        //构造产品搜索对象--白模全铺长度
        if (null != whiteMembraneProduct) {
            //产品白膜全铺长度
            productFullPaveLength = whiteMembraneProduct.getProductFullPaveLength();
            if (0 < productFullPaveLength) {
                //适配白膜全铺长度策略
                ValueOffset fullPaveLengthValueOffset = ProductReplaceSearchFullPraveStrategy.adaptationFullPraveLength(productTypeValue, productTypeSmallValue, productFullPaveLength);
                if (null == fullPaveLengthValueOffset) {
                    //如果查询有特殊白膜过滤小类则增加白膜过滤
                    List<Integer> productTypeSmallValueList = productSearchMatchVo.getProductTypeSmallValueList();
                    if (null != productTypeSmallValueList && 0 < productTypeSmallValueList.size()) {
                        //遍历小类,对比产品大小类-如果有匹配的，则新增全铺长度过滤
                        for (Integer nowProductTypeSmallValue : productTypeSmallValueList) {
                            switch ((productTypeValue + "-" + nowProductTypeSmallValue)) {
                                case ProductTypeValue.PRODUCT_TYPE_VALUE_CABINET + "-" + ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_DIY_CLOTHES_CABINET:
                                    fullPaveLengthValueOffset = ProductReplaceSearchFullPraveStrategy.adaptationFullPraveLength(ProductTypeValue.PRODUCT_TYPE_VALUE_CABINET, ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_DIY_CLOTHES_CABINET, productFullPaveLength);
                                    break;
                                case ProductTypeValue.PRODUCT_TYPE_VALUE_BATHROOM + "-" + ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_DIY_CABINET_BATHROOM:
                                    fullPaveLengthValueOffset = ProductReplaceSearchFullPraveStrategy.adaptationFullPraveLength(ProductTypeValue.PRODUCT_TYPE_VALUE_BATHROOM, ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_DIY_CABINET_BATHROOM, productFullPaveLength);
                                    break;
                                case ProductTypeValue.PRODUCT_TYPE_VALUE_BATHROOM + "-" + ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_DIY_MIRROR_BATHROOM:
                                    fullPaveLengthValueOffset = ProductReplaceSearchFullPraveStrategy.adaptationFullPraveLength(ProductTypeValue.PRODUCT_TYPE_VALUE_BATHROOM, ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_DIY_MIRROR_BATHROOM, productFullPaveLength);
                                    break;
                            }
                        }
                    }
                }

                productSearchMatchVo.setFullPaveLengthValueOffset(fullPaveLengthValueOffset);
            }
        }

        //构造产品搜索对象--高度
        if (null != whiteMembraneProduct) {
            //产品白膜高度
            int productWhiteMembraneHeight = whiteMembraneProduct.getProductHeight();
            if (0 < productWhiteMembraneHeight) {
                ValueRange productHeightValueRange = ProductReplaceSearchHeightStrategy.adaptationProductHeight(productTypeValue, productTypeSmallValue, productWhiteMembraneHeight);
                if (null != productHeightValueRange) {
                    productSearchMatchVo.setProductHeightValueRange(productHeightValueRange);
                }
            }

            //构造产品搜索对象--产品属性--匹配度计分排序
            Map<String, String> productFilterAttributeMap = whiteMembraneProduct.getProductFilterAttributeMap();
            if (null != productFilterAttributeMap && 0 < productFilterAttributeMap.size()) {
                productSearchMatchVo.setProductFilterAttributeMap(productFilterAttributeMap);
            }

            //构造产品聚合对象--产品属性--计分排序
            Map<String, String> productOrderAttributeMap = whiteMembraneProduct.getProductOrderAttributeMap();
            if (null != productOrderAttributeMap && 0 < productOrderAttributeMap.size()) {
                productSearchMatchVo.setProductOrderAttributeMap(productOrderAttributeMap);
            }

            //构造产品聚合对象--产品属性--产品使用数排序
            if (null != userId && 0 < userId) {
                productSearchMatchVo.setUserId(userId);
            }
        }

        //当前实际搜索小类(用户搜索的可能是白膜小类)
        productSearchMatchVo.setNowProductTypeSmallValue(productTypeMatchStrategy.getProductOriginSmallType(productTypeValue, productTypeSmallValue));

        //装回对象
        productSearchMatchVo.setMultiMatchFieldList(multiMatchFieldList);
        productSearchMatchVo.setProductCategoryLongCodeList(productCategoryLongCodeList);


        /************************* 搜索产品 ************************/
        SearchObjectResponse searchObjectResponse;
        try {
            searchObjectResponse = productSearchService.searchProduct(productSearchMatchVo, productSearchAggregationVoList, pageVo);
        } catch (ProductSearchException e) {
            log.error(CLASS_LOG_PREFIX + "搜索产品失败:ProductSearchException:{}.", e);
            return new UniversalSearchResultResponse(false, CLASS_LOG_PREFIX + "搜索产品失败:" + e, msgId);
        }

        //搜索结果为空时以下分类需要二次搜索
        if (null == searchObjectResponse || 0 == searchObjectResponse.getHitTotal()) {
            //搜索背景墙-形象墙-餐厅墙-电视墙-沙发墙-床头墙为空需再次搜索
            if (ProductTypeValue.PRODUCT_TYPE_VALUE_WALL == productTypeValue
                    && (ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_BACKGROUND_WALL == productTypeSmallValue || ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_ORIGIN_BACKGROUND_WALL == productTypeSmallValue
                    || ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_IMAGE_WALL == productTypeSmallValue || ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_ORIGIN_IMAGE_WALL == productTypeSmallValue
                    || ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_DINING_WALL == productTypeSmallValue || ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_ORIGIN_DINING_WALL == productTypeSmallValue
                    || ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_TV_WALL == productTypeSmallValue || ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_ORIGIN_TV_WALL == productTypeSmallValue
                    || ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_SOFA_WALL == productTypeSmallValue || ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_ORIGIN_SOFA_WALL == productTypeSmallValue
                    || ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_BEDSIDE_WALL == productTypeSmallValue || ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_ORIGIN_BEDSIDE_WALL == productTypeSmallValue)) {
                //去掉全铺长度过滤
                productSearchMatchVo.setFullPaveLengthValueOffset(null);
                //增加产品长度最小值聚合
                ProductSearchAggregationVo productSearchAggregationVo = new ProductSearchAggregationVo();
                productSearchAggregationVo.setAggregationType(ProductSearchAggregationVo.MIN_VALUE_AGGREGATION_TYPE);
                productSearchAggregationVo.setAggregationName(AggregationConstant.PRODUCT_LENGTH_MIN_AGGREGATION);
                productSearchAggregationVo.setAggregationFieldName(QueryConditionField.QUERY_CONDITION_FIELD_PRODUCT_LENGTH);

                //搜索出结果中的长度最小值
                try {
                    searchObjectResponse = productSearchService.searchProduct(productSearchMatchVo, Collections.singletonList(productSearchAggregationVo), pageVo);
                } catch (ProductSearchException e) {
                    log.error(CLASS_LOG_PREFIX + "搜索产品失败:ProductSearchException:{}.", e);
                    return new UniversalSearchResultResponse(false, CLASS_LOG_PREFIX + "搜索产品失败:" + e, msgId);
                }

                if (null == searchObjectResponse || 0 == searchObjectResponse.getHitTotal() || null == searchObjectResponse.getHitResult()) {
                    //未搜索到数据
                    log.info(CLASS_LOG_PREFIX + "未搜索到数据.....ProductSearchMatchVo:{}, pageVo:{}", productSearchMatchVo.toString(), pageVo.toString());
                    return new UniversalSearchResultResponse(true, "ok", msgId);
                }

                //搜索出结果后重定义长度最小值范围
                Aggregations searchAggs = searchObjectResponse.getSearchAggs();
                if (null != searchAggs) {
                    //聚合结果集
                    List<Aggregation> aggregationList = searchAggs.asList();
                    if (null != aggregationList && 0 < aggregationList.size()) {
                        for (Aggregation aggregation : aggregationList) {
                            if (AggregationConstant.PRODUCT_LENGTH_MIN_AGGREGATION.equals(aggregation.getName()) && aggregation instanceof ParsedMin) {
                                //最小值
                                int minValue = (int) ((ParsedMin) aggregation).value();
                                log.info(CLASS_LOG_PREFIX + "背景墙-形象墙-餐厅墙-电视墙-沙发墙-床头墙结果中长度最小值为:{}.", minValue);
                                //重构造产品长度范围
                                productSearchMatchVo.setFullPaveLengthValueOffset(
                                        //左右偏移10cm(Range:不等于minValue - minValue+20)
                                        new ValueOffset(
                                                minValue + 10,
                                                10,
                                                ValueOffset.NORMAL_FULL_PAVE_OFFSET_TYPE,
                                                ValueOffset.ONLY_INCLUDE_UPPER
                                        ));
                                //搜索
                                try {
                                    searchObjectResponse = productSearchService.searchProduct(productSearchMatchVo, null, pageVo);
                                } catch (ProductSearchException e) {
                                    log.error(CLASS_LOG_PREFIX + "搜索产品失败:ProductSearchException:{}.", e);
                                    return new UniversalSearchResultResponse(false, CLASS_LOG_PREFIX + "搜索产品失败:" + e, msgId);
                                }
                                break;
                            }
                        }
                    }
                }
            //门框二次搜索--全铺长度不为空时
            } else if (ProductTypeValue.PRODUCT_TYPE_VALUE_WALL == productTypeValue
                    && 0 != productFullPaveLength
                    && (ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_DOOR_FRAME_WALL == productTypeSmallValue
                            || ProductSmallTypeValue.PRODUCT_SMALL_TYPE_VALUE_ORIGIN_DOOR_FRAME_WALL == productTypeSmallValue
                    )) {

                //去掉全铺长度过滤
                productSearchMatchVo.setFullPaveLengthValueOffset(null);
                //增加产品长度最小值聚合
                ProductSearchAggregationVo productSearchMinAggregationVo = new ProductSearchAggregationVo();
                productSearchMinAggregationVo.setAggregationType(ProductSearchAggregationVo.MIN_VALUE_AGGREGATION_TYPE);
                productSearchMinAggregationVo.setAggregationName(AggregationConstant.PRODUCT_LENGTH_MIN_AGGREGATION);
                productSearchMinAggregationVo.setAggregationFieldName(QueryConditionField.QUERY_CONDITION_FIELD_PRODUCT_LENGTH);

                //增加产品长度最大值聚合
                ProductSearchAggregationVo productSearchMaxAggregationVo = new ProductSearchAggregationVo();
                productSearchMaxAggregationVo.setAggregationType(ProductSearchAggregationVo.MAX_VALUE_AGGREGATION_TYPE);
                productSearchMaxAggregationVo.setAggregationName(AggregationConstant.PRODUCT_LENGTH_MAX_AGGREGATION);
                productSearchMaxAggregationVo.setAggregationFieldName(QueryConditionField.QUERY_CONDITION_FIELD_PRODUCT_LENGTH);

                //搜索出结果中的长度最小值
                try {
                    searchObjectResponse = productSearchService.searchProduct(productSearchMatchVo, Arrays.asList(productSearchMinAggregationVo, productSearchMaxAggregationVo), pageVo);
                } catch (ProductSearchException e) {
                    log.error(CLASS_LOG_PREFIX + "搜索产品失败:ProductSearchException:{}.", e);
                    return new UniversalSearchResultResponse(false, CLASS_LOG_PREFIX + "搜索产品失败:" + e, msgId);
                }

                if (null == searchObjectResponse || 0 == searchObjectResponse.getHitTotal() || null == searchObjectResponse.getHitResult()) {
                    //未搜索到数据
                    log.info(CLASS_LOG_PREFIX + "未搜索到数据.....ProductSearchMatchVo:{}, pageVo:{}", productSearchMatchVo.toString(), pageVo.toString());
                    return new UniversalSearchResultResponse(true, "ok", msgId);
                }

                //搜索出结果后重定义长度最小值和最大值范围
                Aggregations searchAggs = searchObjectResponse.getSearchAggs();

                //最小最大值
                int minValue = 0;
                int maxValue = 0;

                if (null != searchAggs) {
                    //聚合结果集
                    List<Aggregation> aggregationList = searchAggs.asList();
                    if (null != aggregationList && 0 < aggregationList.size()) {
                        for (Aggregation aggregation : aggregationList) {
                            if (AggregationConstant.PRODUCT_LENGTH_MIN_AGGREGATION.equals(aggregation.getName()) && aggregation instanceof ParsedMin) {
                                //最小值
                                minValue = (int) ((ParsedMin) aggregation).value();
                            } else if (AggregationConstant.PRODUCT_LENGTH_MAX_AGGREGATION.equals(aggregation.getName()) && aggregation instanceof ParsedMax) {
                                //最大值
                                maxValue = (int) ((ParsedMax) aggregation).value();
                            }
                        }
                    }
                }

                //检查最靠近最小还是最靠近最大
                log.info(CLASS_LOG_PREFIX + "结果集中最小值为:{}，最大值为:{},当前全铺长度:{}.", new Integer[]{
                          minValue
                        , maxValue
                        , productFullPaveLength
                });
                //默认接近最小值
                boolean nearMin = true;
                if (Math.abs(productFullPaveLength - minValue) - Math.abs(productFullPaveLength - maxValue) > 0) {
                    nearMin = false;
                }

                //重构造产品长度范围
                ValueOffset valueOffset = new ValueOffset(
                        nearMin ? minValue : maxValue,
                        0,
                        ValueOffset.NO_FULL_PAVE_OFFSET_TYPE);
                productSearchMatchVo.setFullPaveLengthValueOffset(valueOffset);
                log.info(CLASS_LOG_PREFIX + "二次搜索全铺长度范围:{}.", valueOffset);

                //搜索
                try {
                    searchObjectResponse = productSearchService.searchProduct(productSearchMatchVo, null, pageVo);
                } catch (ProductSearchException e) {
                    log.error(CLASS_LOG_PREFIX + "搜索产品失败:ProductSearchException:{}.", e);
                    return new UniversalSearchResultResponse(false, CLASS_LOG_PREFIX + "搜索产品失败:" + e, msgId);
                }

            }
        }

        if (null == searchObjectResponse || 0 == searchObjectResponse.getHitTotal() || null == searchObjectResponse.getHitResult()) {
            //未搜索到数据
            log.info(CLASS_LOG_PREFIX + "未搜索到数据.....ProductSearchMatchVo:{}, pageVo:{}", productSearchMatchVo.toString(), pageVo.toString());
            return new UniversalSearchResultResponse(true, "ok", msgId);
        }

        //格式化返回数据
        List<ProductIndexMappingData> productList = (List<ProductIndexMappingData>) searchObjectResponse.getHitResult();

        /************************* 产品数据排序 ************************/
        //根据产品属性排序对数据进行排序
        if (null != productList && null != whiteMembraneProduct) {
            //白膜产品排序属性
            Map<String, String> whiteMembraneOrderAttributeMap = whiteMembraneProduct.getProductOrderAttributeMap();
            if (null != whiteMembraneOrderAttributeMap && 0 < whiteMembraneOrderAttributeMap.size()) {
                productList = ProductDataOrderUtil.productOrderByAttribute(productList, whiteMembraneOrderAttributeMap);
            }
        }

        //根据产品小类进行排序
        //根据当前小类查询小类实际小类(有的小类是白膜小类，所以需要转换)
        Integer nowProductTypeSmallValue = productSearchMatchVo.getNowProductTypeSmallValue();
        if (null != nowProductTypeSmallValue && 0 < nowProductTypeSmallValue && null != productList) {
            //是当前小类
            List<ProductIndexMappingData> includeNowProductList = new ArrayList<>();
            //非当前小类
            List<ProductIndexMappingData> excludeNowProductList = new ArrayList<>();

            //将产品分类是当前小类和非当前小类两部分
            for (ProductIndexMappingData product : productList) {
                if (nowProductTypeSmallValue.equals(product.getProductTypeSmallValue())) {
                    includeNowProductList.add(product);
                } else {
                    excludeNowProductList.add(product);
                }
            }
            //组合数据
            includeNowProductList.addAll(excludeNowProductList);
            //返回数据对象
            productList = includeNowProductList;
        }

        //产品关键字全匹配重排(产品编码>产品品牌名>产品名>产品型号名)
        if (null != productList && 0 < productList.size() && !StringUtils.isEmpty(searchKeyword)) {
            //去空格
            searchKeyword = searchKeyword.trim();

            //产品型号
            List<ProductIndexMappingData> productModelList = new ArrayList<>(productList.size());
            //匹配下标位
            int matchMakeIndex = 0;
            //遍历结果集
            for (int i = 0; i < productList.size(); i++) {
                ProductIndexMappingData productData = productList.get(i);
                //若匹配则放在前面
                if (searchKeyword.equals(null == productData.getProductModelNumber() ? null : productData.getProductModelNumber().trim())) {
                    productModelList.add(matchMakeIndex, productData);
                    //重计算下次下标位
                    ++matchMakeIndex;
                } else {
                    //不匹配则依次排序
                    productModelList.add(productData);
                }
            }

            //产品名
            List<ProductIndexMappingData> productNameList = new ArrayList<>(productModelList.size());
            //重置匹配下标位
            matchMakeIndex = 0;
            //遍历结果集
            for (int i = 0; i < productModelList.size(); i++) {
                ProductIndexMappingData productData = productModelList.get(i);
                //若匹配则放在前面
                if (searchKeyword.equals(null == productData.getProductName() ? null : productData.getProductName().trim())) {
                    productNameList.add(matchMakeIndex, productData);
                    //重计算下次下标位
                    ++matchMakeIndex;
                } else {
                    //不匹配则依次排序
                    productNameList.add(productData);
                }
            }

            //产品品牌名
            List<ProductIndexMappingData> productBrandNameList = new ArrayList<>(productNameList.size());
            //重置匹配下标位
            matchMakeIndex = 0;
            //遍历结果集
            for (int i = 0; i < productNameList.size(); i++) {
                ProductIndexMappingData productData = productNameList.get(i);
                //若匹配则放在前面
                if (searchKeyword.equals(null == productData.getProductBrandName() ? null : productData.getProductBrandName().trim())) {
                    productBrandNameList.add(matchMakeIndex, productData);
                    //重计算下次下标位
                    ++matchMakeIndex;
                } else {
                    //不匹配则依次排序
                    productBrandNameList.add(productData);
                }
            }

            //产品编码
            List<ProductIndexMappingData> productCodeList = new ArrayList<>(productBrandNameList.size());
            //重置匹配下标位
            matchMakeIndex = 0;
            //遍历结果集
            for (int i = 0; i < productBrandNameList.size(); i++) {
                ProductIndexMappingData productData = productBrandNameList.get(i);
                //若匹配则放在前面
                if (searchKeyword.equals(null == productData.getProductCode() ? null : productData.getProductCode().trim())) {
                    productCodeList.add(matchMakeIndex, productData);
                    //重计算下次下标位
                    ++matchMakeIndex;
                } else {
                    //不匹配则依次排序
                    productCodeList.add(productData);
                }
            }

            //装回对象
            productList = productCodeList;
        }

        /************************* 产品数据对象转换 ************************/
        //格式化数据
        List<BrandAndNameProductVo> brandAndNameProductVoList = new ArrayList<>();
        if (null != productList && 0 < productList.size()) {
            productList.forEach(productIndexMappingData -> {
                //复制对象
                BrandAndNameProductVo brandAndNameProductVo = EntityCopyUtils.copyData(productIndexMappingData, BrandAndNameProductVo.class);
                if (null != brandAndNameProductVo) {
                    //字段名不同对象单独处理
                    brandAndNameProductVo.setPicPath(productIndexMappingData.getProductPicPath());
                    brandAndNameProductVoList.add(brandAndNameProductVo);
                }
            });
        }

        /************************* 小程序聚合附加数据 ************************/
        Aggregations searchAggs = searchObjectResponse.getSearchAggs();
        if (isMiniProgram && null != searchAggs) {
            //三级分类ID列表
            List<Integer> thirdLevelCategoryIdList = new ArrayList<>();
            //检查聚合条件中是否有3级分类ID聚合
            for (Aggregation aggregation : searchAggs.asList()) {
                if (AggregationConstant.THREE_LEVEL_CATEGORY_AGGREGATION.equals(aggregation.getName())) {
                    List<? extends Terms.Bucket> bucketList = ((ParsedLongTerms) aggregation).getBuckets();
                    if (null != bucketList && 0 < bucketList.size()) {
                        bucketList.forEach(obj -> thirdLevelCategoryIdList.add(Integer.parseInt(obj.getKeyAsString())));
                    }
                    break;
                }
            }

            //增加3级后4,5,6级分类筛选元数据
            if (null != thirdLevelCategoryIdList && 0 < thirdLevelCategoryIdList.size()) {
                List<ProductCategoryVo> thirdLevelProductCategoryVoList = productCategoryMetaDataStorage.queryChildrenNodeDataByThirdLevelCategoryIdList(thirdLevelCategoryIdList);

                if (null != thirdLevelCategoryIdList && 0 < thirdLevelCategoryIdList.size()) {
                    log.info(CLASS_LOG_PREFIX + "小程序产品替换搜索已聚合分类数据,聚合3级分类数:{}.", thirdLevelCategoryIdList.size());
                    //构造返回对象
                    Map<String, Object> resuleMap = new HashMap<>(2);
                    resuleMap.put("obj", brandAndNameProductVoList);
                    resuleMap.put("aggs", thirdLevelProductCategoryVoList);
                    return new UniversalSearchResultResponse(true, "ok", msgId, searchObjectResponse.getHitTotal(), resuleMap);
                }
            }
        }


        return new UniversalSearchResultResponse(true, "ok", msgId, searchObjectResponse.getHitTotal(), brandAndNameProductVoList);
    }
}