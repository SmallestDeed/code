package com.sandu.search.initialize;

import com.google.gson.reflect.TypeToken;
import com.sandu.search.common.constant.IndexInfoQueryConfig;
import com.sandu.search.common.tools.EntityCopyUtils;
import com.sandu.search.common.tools.EntityUtil;
import com.sandu.search.common.tools.JsonUtil;
import com.sandu.search.entity.elasticsearch.constant.IndexConstant;
import com.sandu.search.entity.elasticsearch.constant.TypeConstant;
import com.sandu.search.entity.elasticsearch.dbobject.ProductStyleDBPo;
import com.sandu.search.entity.elasticsearch.dbobject.ProductTextureDBPo;
import com.sandu.search.entity.elasticsearch.dto.IndexRequestDTO;
import com.sandu.search.entity.elasticsearch.index.ProductIndexMappingData;
import com.sandu.search.entity.elasticsearch.po.ProductCategoryPo;
import com.sandu.search.entity.elasticsearch.po.ProductPo;
import com.sandu.search.entity.elasticsearch.po.metadate.CompanyPo;
import com.sandu.search.entity.elasticsearch.po.product.ProductPlatformData;
import com.sandu.search.entity.elasticsearch.response.BulkStatistics;
import com.sandu.search.exception.ProductIndexException;
import com.sandu.search.exception.ElasticSearchException;
import com.sandu.search.service.elasticsearch.ElasticSearchService;
import com.sandu.search.service.index.ProductIndexService;
import com.sandu.search.storage.company.BrandMetaDataStorage;
import com.sandu.search.storage.company.CompanyMetaDataStorage;
import com.sandu.search.storage.product.*;
import com.sandu.search.storage.resource.ResPicMetaDataStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 搜索引擎产品分类索引初始化
 *
 * @date 20171212
 * @auth pengxuangang
 */
@Slf4j
@Component
public class ProductIndex {

    private final static String CLASS_LOG_PREFIX = "初始化搜索引擎产品分类索引:";

    private final ElasticSearchService elasticSearchService;
    private final BrandMetaDataStorage brandMetaDataStorage;
    private final ResPicMetaDataStorage resPicMetaDataStorage;
    private final CompanyMetaDataStorage companyMetaDataStorage;
    private final ProductStyleMetaDataStorage productStyleMetaDataStorage;
    private final ProductIndexService productIndexService;
    private final ProductTextureMetaDataStorage productTextureMetaDataStorage;
    private final ProductPlatformMetaDataStorage productPlatformMetaDataStorage;
    private final ProductCategoryMetaDataStorage productCategoryMetaDataStorage;
    private final ProductAttributeMetaDataStorage productAttributeMetaDataStorage;
    private final ProductUsageCountMetaDataStorage productUsageCountMetaDataStorage;
    private final ProductCategoryRelMetaDataStorage productCategoryRelMetaDataStorage;

    @Autowired
    public ProductIndex(ElasticSearchService elasticSearchService, BrandMetaDataStorage brandMetaDataStorage, ResPicMetaDataStorage resPicMetaDataStorage, CompanyMetaDataStorage companyMetaDataStorage, ProductStyleMetaDataStorage productStyleMetaDataStorage, ProductIndexService productIndexService, ProductTextureMetaDataStorage productTextureMetaDataStorage, ProductPlatformMetaDataStorage productPlatformMetaDataStorage, ProductCategoryMetaDataStorage productCategoryMetaDataStorage, ProductAttributeMetaDataStorage productAttributeMetaDataStorage, ProductUsageCountMetaDataStorage productUsageCountMetaDataStorage, ProductCategoryRelMetaDataStorage productCategoryRelMetaDataStorage) {
        this.elasticSearchService = elasticSearchService;
        this.brandMetaDataStorage = brandMetaDataStorage;
        this.resPicMetaDataStorage = resPicMetaDataStorage;
        this.companyMetaDataStorage = companyMetaDataStorage;
        this.productStyleMetaDataStorage = productStyleMetaDataStorage;
        this.productIndexService = productIndexService;
        this.productTextureMetaDataStorage = productTextureMetaDataStorage;
        this.productPlatformMetaDataStorage = productPlatformMetaDataStorage;
        this.productCategoryMetaDataStorage = productCategoryMetaDataStorage;
        this.productAttributeMetaDataStorage = productAttributeMetaDataStorage;
        this.productUsageCountMetaDataStorage = productUsageCountMetaDataStorage;
        this.productCategoryRelMetaDataStorage = productCategoryRelMetaDataStorage;
    }



    /**
     * 同步产品信息数据
     */
    public void syncProductInfoData() {

        log.info(CLASS_LOG_PREFIX + "开始索引产品分类数据........");
        //开始时间
        long startTime = System.currentTimeMillis();

        //数据查询初始位
        int start = 0;
        //每次数据量
        int limit = IndexInfoQueryConfig.DEFAULT_QUERY_PRODUCTPOINFO_LIMIT;

        //是否继续处理
        boolean isContinueHandler = true;
        //总数据量
        int totalProductCount = 0;
        //总索引量
        int totalIndexCount = 0;
        //异常数据
        int totalExceptionCount = 0;

        while (isContinueHandler) {
            List<ProductPo> productPoList;
            /********************************** 查询产品信息 *********************************/
            //更新全量数据
            try {
                productPoList = productIndexService.queryProductPoList(start, limit);
            } catch (ProductIndexException e) {
                log.error(CLASS_LOG_PREFIX + "查询产品信息失败:ProductIndexException:{}", e);
                return;
            }

            //递增start下标
            start = start + limit;

            //无数据中断操作
            if (null == productPoList || 0 == productPoList.size()) {
                log.info(CLASS_LOG_PREFIX + "查询产品分类息数据为空：start:{},limit:{}.", start, limit);
                return;
            }
            //数据不足指定数据量表示已查询出最后一条数据,终止循环
            if (productPoList.size() < IndexInfoQueryConfig.DEFAULT_QUERY_PRODUCTPOINFO_LIMIT) {
                isContinueHandler = false;
            }

            //索引产品数据
            int successIndexCount = indexProdcutData(productPoList);

            //累加数据量
            totalProductCount += productPoList.size();
            totalIndexCount += successIndexCount;
            totalExceptionCount += productPoList.size() - successIndexCount;
        }

        log.info(CLASS_LOG_PREFIX + "索引所有产品分类数据完成!!!产品数据量:{}, 索引数据量:{},失败数:{},共耗时:{}ms", new String[]{
                totalProductCount + "",
                totalIndexCount + "",
                totalExceptionCount + "",
                (System.currentTimeMillis() - startTime) + ""
        });
    }

    /**
     * 索引产品数据
     *
     * @param productPoList 产品对象
     * @return 索引成功数
     */
    public int indexProdcutData(List<ProductPo> productPoList) {

        if (null == productPoList || 0 >= productPoList.size()) {
            return 0;
        }

        //失败数
        int failCount = 0;

        log.info(CLASS_LOG_PREFIX + "批量提交数据对象,当前产品总条数:{}.", productPoList.size());
        //批量提交数据对象
        List<Object> bulkIndexList = new ArrayList<>(IndexInfoQueryConfig.DEFAULT_QUERY_PRODUCTPOINFO_LIMIT);

        /********************************** 处理产品分类信息数据 *********************************/
        for (ProductPo productPo : productPoList) {
            //定义索引数据对象
            ProductIndexMappingData productIndexMappingData = new ProductIndexMappingData();

            /*************** Field1:产品基本数据 *******************/
            productIndexMappingData.setId(productPo.getId());
            productIndexMappingData.setCeilingRandomPatchFlowerFlag(productPo.getCeilingRandomPatchFlowerFlag());
            productIndexMappingData.setSecrecyFlag(productPo.getSecrecyFlag());
            productIndexMappingData.setDataIsDelete(productPo.getDataIsDelete());
            productIndexMappingData.setProductCode(productPo.getProductCode());
            productIndexMappingData.setProductName(productPo.getProductName());
            productIndexMappingData.setProductBrandId(productPo.getProductBrandId());
            productIndexMappingData.setProductBrandName(brandMetaDataStorage.getBrandNameById(productPo.getProductBrandId()));
            productIndexMappingData.setProductFullPaveLength(productPo.getProductFullPaveLength());
            productIndexMappingData.setProductModelNumber(productPo.getProductModelNumber());
            productIndexMappingData.setProductMeasurementCode(productPo.getProductMeasurementCode());
            productIndexMappingData.setProductGroundLayoutIden(productPo.getProductGroundLayoutIden());
            //天花布局标识
            String productCeilingLayoutIdenStr = productPo.getProductCeilingLayoutIden();
            if (!StringUtils.isEmpty(productCeilingLayoutIdenStr)) {
                productIndexMappingData.setProductCeilingLayoutIdenList(Arrays.asList(productCeilingLayoutIdenStr.split(",")));
            }

            //包含的所有分类ID和分类名
            productIndexMappingData.setAllProductCategoryId(productCategoryRelMetaDataStorage.getCategoryListByProductId(productIndexMappingData.getId()));
            productIndexMappingData.setAllProductCategoryName(productCategoryMetaDataStorage.queryProductCategoryIdByProductId(productIndexMappingData.getAllProductCategoryId()));
            //默认获取第一个为当前分类
            if (null != productIndexMappingData.getAllProductCategoryId() && productIndexMappingData.getAllProductCategoryId().size() > 0) {
                Integer categoryId = productIndexMappingData.getAllProductCategoryId().get(0);
                productIndexMappingData.setProductCategoryId(categoryId);
            }

            //风格ID数据格式化
            List<Integer> styleList = new ArrayList<>();
            if (!StringUtils.isEmpty(productPo.getProductStyleId())) {
                styleList.add(productPo.getProductStyleId());
            }

            if (!StringUtils.isEmpty(productPo.getProductStyleIdArr())) {
                ProductStyleDBPo productStyleDBPo = JsonUtil.fromJson(productPo.getProductStyleIdArr(), ProductStyleDBPo.class);
                //父类风格
                if (!StringUtils.isEmpty(productStyleDBPo.getIsLeaf_1())) {
                    for (String parentStyleId : productStyleDBPo.getIsLeaf_0().split(",")) {
                        if (!StringUtils.isEmpty(parentStyleId)) {
                            List<Integer> childrenStyleIdList = productStyleMetaDataStorage.getProductStyleChildrenIdByParentId(Integer.valueOf(parentStyleId));
                            if (null != childrenStyleIdList && 0 < childrenStyleIdList.size()) {
                                styleList.addAll(childrenStyleIdList);
                            }
                        }

                    }
                }
                //子类风格
                if (!StringUtils.isEmpty(productStyleDBPo.getIsLeaf_0())) {
                    String[] childrenStyleArr = productStyleDBPo.getIsLeaf_0().split(",");
                    for (String childrenStyleId : childrenStyleArr) {
                        styleList.add(Integer.valueOf(childrenStyleId));
                    }
                }
                productIndexMappingData.setProductStyleList(styleList);
            }

            //风格名称数据格式化
            List<Integer> productStyleList = productIndexMappingData.getProductStyleList();
            if (null != productStyleList && 0 != productStyleList.size()) {
                List<String> productStyleNameList = new ArrayList<>(productStyleList.size());
                productStyleList.forEach(productStyleId -> productStyleNameList.add(productStyleMetaDataStorage.getProductStyleNameById(productStyleId)));
                productIndexMappingData.setStyleNameList(productStyleNameList);
            }

            productIndexMappingData.setProductSpecification(productPo.getProductSpecification());
            productIndexMappingData.setProductColorId(productPo.getProductColorId());
            productIndexMappingData.setProductLength(productPo.getProductLength());
            productIndexMappingData.setProductWidth(productPo.getProductWidth());
            productIndexMappingData.setProductHeight(productPo.getProductHeight());
            productIndexMappingData.setProductSalePrice(productPo.getProductSalePrice());
            productIndexMappingData.setProductSalePriceValue(productPo.getProductSalePriceValue());
            productIndexMappingData.setProductPicId(productPo.getProductPicId());

            //产品图片ID数据格式化
            if (!StringUtils.isEmpty(productPo.getProductPicArr())) {
                String[] picIdArr = productPo.getProductPicArr().split(",");
                List<Integer> picIdList = new ArrayList<>(picIdArr.length);
                for (String picId : picIdArr) {
                    picIdList.add(Integer.valueOf(picId));
                }
                productIndexMappingData.setProductPicList(picIdList);
            }

            //产品图片路径数据格式化
            Integer picId = productIndexMappingData.getProductPicId();
            if (null != picId && 0 != picId) {
                productIndexMappingData.setProductPicPath(resPicMetaDataStorage.getPicPathByPicId(picId));
            }
            List<Integer> productPicIdList = productIndexMappingData.getProductPicList();
            if (null != productPicIdList && 0 < productPicIdList.size()) {
                productIndexMappingData.setProductPicPathList(resPicMetaDataStorage.queryPicPathListByPicIdList(productPicIdList));
            }


            productIndexMappingData.setProductModelId(productPo.getProductModelId());
            productIndexMappingData.setProductDesc(productPo.getProductDesc());
            productIndexMappingData.setProductSystemCode(productPo.getProductSystemCode());
            productIndexMappingData.setProductCreateDate(productPo.getProductCreateDate());
            productIndexMappingData.setProductModifyDate(productPo.getProductModifyDate());
            productIndexMappingData.setProductPutawayDate(productPo.getProductPutawayDate());

            //产品材质ID数据格式化
            List<String> productMaterialList = new ArrayList<>();
            if (!StringUtils.isEmpty(productPo.getProductMaterialId())) {
                productMaterialList.add(productPo.getProductMaterialId());
            }
            if (!StringUtils.isEmpty(productPo.getProductMaterialIdArr())) {
                List<ProductTextureDBPo> productTextureDBPoList;
                try {
                    productTextureDBPoList = JsonUtil.fromJson(productPo.getProductMaterialIdArr(), new TypeToken<List<ProductTextureDBPo>>() {
                    }.getType());
                } catch (Exception e) {
                    log.error(CLASS_LOG_PREFIX + "产品材质数据格式化异常，字符串转对象异常:Str:{},Exception:{}", productPo.getProductMaterialIdArr(), e);
                    failCount++;
                    continue;
                }
                for (ProductTextureDBPo productTextureDBPo : productTextureDBPoList) {
                    if (!StringUtils.isEmpty(productTextureDBPo.getTextureIds())) {
                        String[] textureArr = productTextureDBPo.getTextureIds().split(",");
                        Collections.addAll(productMaterialList, textureArr);
                    }
                }
            }
            productIndexMappingData.setProductMaterialList(productMaterialList);

            //产品材质名数据格式化
            List<String> productMaterialIdList = productIndexMappingData.getProductMaterialList();
            if (null != productMaterialIdList && 0 != productMaterialIdList.size()) {
                List<String> productMaterialNameList = new ArrayList<>(productMaterialIdList.size());
                productIndexMappingData.getProductMaterialList().forEach(productMaterialId -> productMaterialNameList.add(productTextureMetaDataStorage.getProductTextureNameById(productMaterialId)));
                productIndexMappingData.setProductMaterialNameList(productMaterialNameList);
            }

            productIndexMappingData.setProductTypeValue(productPo.getProductTypeValue());
            productIndexMappingData.setProductTypeSmallValue(productPo.getProductTypeSmallValue());
            productIndexMappingData.setProductPutawayState(productPo.getProductPutawayState());
            productIndexMappingData.setProductDesignerId(productPo.getProductDesignerId());
            productIndexMappingData.setProductSeriesId(productPo.getProductSeriesId());

            /*************** Field2:产品分类数据 *******************/
            //当前分类对象
            ProductCategoryPo productCategoryPo = productCategoryMetaDataStorage.getProductCategory(productIndexMappingData.getProductCategoryId());
            if (null != productCategoryPo) {
                //Step 1: 确定当前产品分类层级

                if (null == productCategoryPo) {
                    log.warn(CLASS_LOG_PREFIX + "产品分类数据-确定当前产品分类层级-ProductCategoryPo is null.ProductIndexMappingData:{}.", productIndexMappingData.toString());
                    failCount++;
                    continue;
                }
                productIndexMappingData.setProductCategoryCode(productCategoryPo.getProductCategoryCode());
                //产品长编码列表
                productIndexMappingData.setProductCategoryLongCodeList(
                        productCategoryMetaDataStorage.queryProductCategoryLongCodeByCategoryIdList(
                                productIndexMappingData.getAllProductCategoryId()));
                productIndexMappingData.setParentCategoryId(productCategoryPo.getParentCategoryId());
                productIndexMappingData.setCategoryName(productCategoryPo.getCategoryName());
                productIndexMappingData.setCategoryLevel(productCategoryPo.getCategoryLevel());
                productIndexMappingData.setCategoryOrder(productCategoryPo.getCategoryOrder());
                productIndexMappingData.setCategorySystemCode(productCategoryPo.getCategorySystemCode());

                //Step 2: 根据不同层级装配数据
                ProductCategoryPo firstLevelProductCategoryPo, secondLevelProductCategoryPo, thirdLevelProductCategoryPo, fourthLevelProductCategoryPo;
                switch (productCategoryPo.getCategoryLevel()) {
                    case 2:
                        //二级对象
                        productIndexMappingData.setSecondLevelCategoryId(productCategoryPo.getId());
                        productIndexMappingData.setSecondLevelCategoryName(productCategoryPo.getCategoryName());

                        //一级对象
                        firstLevelProductCategoryPo = productCategoryMetaDataStorage.getProductCategory(productCategoryPo.getParentCategoryId());
                        productIndexMappingData.setFirstLevelCategoryId(firstLevelProductCategoryPo.getId());
                        productIndexMappingData.setFirstLevelCategoryName(firstLevelProductCategoryPo.getCategoryName());
                        //每级数据添加
                        productIndexMappingData.setAllLevelCategoryName(Arrays.asList(
                                productIndexMappingData.getFirstLevelCategoryName(),
                                productIndexMappingData.getSecondLevelCategoryName()));
                        break;
                    case 3:
                        //三级对象
                        productIndexMappingData.setThirdLevelCategoryId(productCategoryPo.getId());
                        productIndexMappingData.setThirdLevelCategoryName(productCategoryPo.getCategoryName());
                        //二级对象
                        secondLevelProductCategoryPo = productCategoryMetaDataStorage.getProductCategory(productCategoryPo.getParentCategoryId());
                        productIndexMappingData.setSecondLevelCategoryId(secondLevelProductCategoryPo.getId());
                        productIndexMappingData.setSecondLevelCategoryName(secondLevelProductCategoryPo.getCategoryName());
                        //一级对象
                        firstLevelProductCategoryPo = productCategoryMetaDataStorage.getProductCategory(secondLevelProductCategoryPo.getParentCategoryId());
                        productIndexMappingData.setFirstLevelCategoryId(firstLevelProductCategoryPo.getId());
                        productIndexMappingData.setFirstLevelCategoryName(firstLevelProductCategoryPo.getCategoryName());
                        //每级数据添加
                        productIndexMappingData.setAllLevelCategoryName(Arrays.asList(
                                productIndexMappingData.getFirstLevelCategoryName(),
                                productIndexMappingData.getSecondLevelCategoryName(),
                                productIndexMappingData.getThirdLevelCategoryName()));
                        break;
                    case 4:
                        //四级对象
                        productIndexMappingData.setFourthLevelCategoryId(productCategoryPo.getId());
                        productIndexMappingData.setFourthLevelCategoryName(productCategoryPo.getCategoryName());
                        //三级对象
                        thirdLevelProductCategoryPo = productCategoryMetaDataStorage.getProductCategory(productCategoryPo.getParentCategoryId());
                        productIndexMappingData.setThirdLevelCategoryId(thirdLevelProductCategoryPo.getId());
                        productIndexMappingData.setThirdLevelCategoryName(thirdLevelProductCategoryPo.getCategoryName());
                        //二级对象
                        secondLevelProductCategoryPo = productCategoryMetaDataStorage.getProductCategory(thirdLevelProductCategoryPo.getParentCategoryId());
                        productIndexMappingData.setSecondLevelCategoryId(secondLevelProductCategoryPo.getId());
                        productIndexMappingData.setSecondLevelCategoryName(secondLevelProductCategoryPo.getCategoryName());
                        //一级对象
                        firstLevelProductCategoryPo = productCategoryMetaDataStorage.getProductCategory(secondLevelProductCategoryPo.getParentCategoryId());
                        productIndexMappingData.setFirstLevelCategoryId(firstLevelProductCategoryPo.getId());
                        productIndexMappingData.setFirstLevelCategoryName(firstLevelProductCategoryPo.getCategoryName());
                        //每级数据添加
                        productIndexMappingData.setAllLevelCategoryName(Arrays.asList(
                                productIndexMappingData.getFirstLevelCategoryName(),
                                productIndexMappingData.getSecondLevelCategoryName(),
                                productIndexMappingData.getThirdLevelCategoryName(),
                                productIndexMappingData.getFourthLevelCategoryName()));
                        break;
                    case 5:
                        //五级对象
                        productIndexMappingData.setFifthLevelCategoryId(productCategoryPo.getId());
                        productIndexMappingData.setFifthLevelCategoryName(productCategoryPo.getCategoryName());
                        //四级对象
                        fourthLevelProductCategoryPo = productCategoryMetaDataStorage.getProductCategory(productCategoryPo.getParentCategoryId());
                        productIndexMappingData.setFourthLevelCategoryId(fourthLevelProductCategoryPo.getId());
                        productIndexMappingData.setFourthLevelCategoryName(fourthLevelProductCategoryPo.getCategoryName());
                        //三级对象
                        thirdLevelProductCategoryPo = productCategoryMetaDataStorage.getProductCategory(fourthLevelProductCategoryPo.getParentCategoryId());
                        productIndexMappingData.setThirdLevelCategoryId(thirdLevelProductCategoryPo.getId());
                        productIndexMappingData.setThirdLevelCategoryName(thirdLevelProductCategoryPo.getCategoryName());
                        //二级对象
                        secondLevelProductCategoryPo = productCategoryMetaDataStorage.getProductCategory(thirdLevelProductCategoryPo.getParentCategoryId());
                        productIndexMappingData.setSecondLevelCategoryId(secondLevelProductCategoryPo.getId());
                        productIndexMappingData.setSecondLevelCategoryName(secondLevelProductCategoryPo.getCategoryName());
                        //一级对象
                        firstLevelProductCategoryPo = productCategoryMetaDataStorage.getProductCategory(secondLevelProductCategoryPo.getParentCategoryId());
                        productIndexMappingData.setFirstLevelCategoryId(firstLevelProductCategoryPo.getId());
                        productIndexMappingData.setFirstLevelCategoryName(firstLevelProductCategoryPo.getCategoryName());
                        //每级数据添加
                        productIndexMappingData.setAllLevelCategoryName(Arrays.asList(
                                productIndexMappingData.getFirstLevelCategoryName(),
                                productIndexMappingData.getSecondLevelCategoryName(),
                                productIndexMappingData.getThirdLevelCategoryName(),
                                productIndexMappingData.getFourthLevelCategoryName(),
                                productIndexMappingData.getFifthLevelCategoryName()));
                        break;
                    default:
                        failCount++;
                        log.error(CLASS_LOG_PREFIX + "根据不同层级装配不同分类数据异常!未知分类层级Level:{}", productCategoryPo.getCategoryLevel());
                }
            }

            /*************** Field3:产品公司数据 *******************/
            Integer productBrandId = productIndexMappingData.getProductBrandId();
            if (null != productBrandId && 0 < productBrandId) {
                //公司ID
                Integer companyId = brandMetaDataStorage.getCompanyIdByBrandId(productBrandId);
                if (null != companyId && 0 < companyId) {
                    productIndexMappingData.setCompanyId(companyId);
                    //公司对象
                    CompanyPo companyPo = companyMetaDataStorage.getCompanyPoByCompanyId(companyId);
                    if (null != companyPo) {
                        //公司编码
                        productIndexMappingData.setCompanyCode(companyPo.getCompanyCode());
                        //公司行业
                        productIndexMappingData.setCompanyIndustry(companyPo.getCompanyIndustry());
                    }
                }
            }

            /*************** Field4:产品全平台过滤数据 *******************/
            //白膜产品移除产品平台数据检查，提高数据初始化性能
            String productCode = productPo.getProductCode();
            if (!StringUtils.isEmpty(productCode) && !productCode.startsWith("basic") && !productCode.startsWith("baimo")) {
                ProductPlatformData productPlatformData = productPlatformMetaDataStorage.queryProductPlatformByProductId(productIndexMappingData.getId());
                if (null != productPlatformData) {
                    //平台数据
                    EntityCopyUtils.copyData(productPlatformData, productIndexMappingData);
                    //平台编码列表
                    List<String> platformCodeList = new ArrayList<>(9);
                    if (null != productPlatformData.getPlatformProductToBMobile()) {
                        platformCodeList.add(productPlatformData.getPlatformProductToBMobile().getPlatformCode());
                    }
                    if (null != productPlatformData.getPlatformProductToBPc()) {
                        platformCodeList.add(productPlatformData.getPlatformProductToBPc().getPlatformCode());
                    }
                    if (null != productPlatformData.getPlatformProductToCSite()) {
                        platformCodeList.add(productPlatformData.getPlatformProductToCSite().getPlatformCode());
                    }
                    if (null != productPlatformData.getPlatformProductToCMobile()) {
                        platformCodeList.add(productPlatformData.getPlatformProductToCMobile().getPlatformCode());
                    }
                    if (null != productPlatformData.getPlatformProductSanduManager()) {
                        platformCodeList.add(productPlatformData.getPlatformProductSanduManager().getPlatformCode());
                    }
                    if (null != productPlatformData.getPlatformProductHouseDraw()) {
                        platformCodeList.add(productPlatformData.getPlatformProductHouseDraw().getPlatformCode());
                    }
                    if (null != productPlatformData.getPlatformProductMerchantsManager()) {
                        platformCodeList.add(productPlatformData.getPlatformProductMerchantsManager().getPlatformCode());
                    }
                    if (null != productPlatformData.getPlatformProductTest()) {
                        platformCodeList.add(productPlatformData.getPlatformProductTest().getPlatformCode());
                    }
                    if (null != productPlatformData.getPlatformProductU3dPlugin()) {
                        platformCodeList.add(productPlatformData.getPlatformProductU3dPlugin().getPlatformCode());
                    }
                    if (null != productPlatformData.getPlatformProductMiniProgram()) {
                        platformCodeList.add(productPlatformData.getPlatformProductMiniProgram().getPlatformCode());
                    }
                    productIndexMappingData.setPlatformCodeList(platformCodeList);
                }
            }

            /*************** Field5:产品过滤属性数据 *******************/
            Map<String, String> productFilterAttributeMap = productAttributeMetaDataStorage.getProductFilterAttributeMap(productIndexMappingData.getId());
            if (null != productFilterAttributeMap && 0 < productFilterAttributeMap.size()) {
                productIndexMappingData.setProductFilterAttributeMap(productFilterAttributeMap);
            }

            /*************** Field6:产品排序属性数据 *******************/
            Map<String, String> productOrderAttributeMap = productAttributeMetaDataStorage.getProductOrderAttributeMap(productIndexMappingData.getId());
            if (null != productOrderAttributeMap && 0 < productOrderAttributeMap.size()) {
                productIndexMappingData.setProductOrderAttributeMap(productOrderAttributeMap);
            }

            /*************** Field7:产品使用次数数据 *******************/
            //产品使用次数数据
            Map<Integer, Integer> productUsageCountMetaDataMap = productUsageCountMetaDataStorage.getProductUsageCountMetaDataMap(productIndexMappingData.getId());
            if (null != productUsageCountMetaDataMap && 0 < productUsageCountMetaDataMap.size()) {
                productIndexMappingData.setProductUsageCount(productUsageCountMetaDataMap);
            }

            //处理对象空值
            productIndexMappingData = (ProductIndexMappingData) EntityUtil.setEntityNullValueToZeroForintegerAndDouble(productIndexMappingData);

            if (null == productIndexMappingData) {
                continue;
            }

            /*
            * 创建索引对象---分类索引从1级开始建立
            * */
            IndexRequestDTO indexRequestDTO = new IndexRequestDTO(
                    IndexConstant.INDEX_PRODUCT_INFO,
                    TypeConstant.TYPE_DEFAULT,
                    productIndexMappingData.getId() + "",
                    JsonUtil.toJson(productIndexMappingData)
            );

            //加入批量对象
            bulkIndexList.add(indexRequestDTO);
        }

        //索引数据
        BulkStatistics bulkStatistics = null;
        try {
            bulkStatistics = elasticSearchService.bulk(bulkIndexList, null);
        } catch (ElasticSearchException e) {
            log.error(CLASS_LOG_PREFIX + "索引产品分类数据异常:ElasticSearchException:{}", e);
        }
        log.info(CLASS_LOG_PREFIX + "索引产品分类数据成功:成功索引数:{},无效索引数:{},BulkStatistics:{}", new String[]{
                bulkIndexList.size() + "",
                failCount + "",
                null == bulkStatistics ? null : bulkStatistics.toString()
        });

        return productPoList.size() - failCount;
    }
}
