package com.sandu.service.product.impl.biz;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;
import com.sandu.api.brand.model.Brand;
import com.sandu.api.brand.service.BrandService;
import com.sandu.api.category.model.Category;
import com.sandu.api.category.model.bo.CategoryTreeNode;
import com.sandu.api.category.service.CategoryService;
import com.sandu.api.category.service.biz.CategoryBizService;
import com.sandu.api.company.model.bo.CompanyBrandBo;
import com.sandu.api.company.model.bo.CompanyWithDeliveredBO;
import com.sandu.api.company.service.CompanyService;
import com.sandu.api.company.service.biz.CompanyBizService;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.platform.model.Platform;
import com.sandu.api.platform.model.PlatformProductRel;
import com.sandu.api.platform.service.Platform2bProductRelService;
import com.sandu.api.platform.service.Platform2cProductRelService;
import com.sandu.api.platform.service.PlatformService;
import com.sandu.api.product.input.EditorProductQuery;
import com.sandu.api.product.input.ProductAdd;
import com.sandu.api.product.input.ProductSKUInfo;
import com.sandu.api.product.model.*;
import com.sandu.api.product.model.bo.*;
import com.sandu.api.product.model.po.HardProductUpdatePO;
import com.sandu.api.product.model.po.ProductQueryPO;
import com.sandu.api.product.model.po.ProductUpdatePO;
import com.sandu.api.product.service.*;
import com.sandu.api.product.service.biz.ProductBizService;
import com.sandu.api.product.service.biz.ProductCategoryRelBizService;
import com.sandu.api.queue.SyncMessage;
import com.sandu.api.queue.service.QueueService;
import com.sandu.api.resmodel.input.ResModelUpdate;
import com.sandu.api.resmodel.model.ModelAreaRel;
import com.sandu.api.resmodel.model.ModelAreaTextureRel;
import com.sandu.api.resmodel.model.ResModel;
import com.sandu.api.resmodel.model.bo.HardProductModelBO;
import com.sandu.api.resmodel.model.bo.ModelAreaBO;
import com.sandu.api.resmodel.model.bo.ModelAreaTextureRelBO;
import com.sandu.api.resmodel.service.ModelAreaRelService;
import com.sandu.api.resmodel.service.ResModelBizService;
import com.sandu.api.resmodel.service.ResModelService;
import com.sandu.api.restexture.model.ResTexture;
import com.sandu.api.restexture.model.bo.ProductTextureVO;
import com.sandu.api.restexture.service.ResTextureService;
import com.sandu.api.storage.model.ResPic;
import com.sandu.api.storage.service.ResPicService;
import com.sandu.api.user.model.bo.UserBo;
import com.sandu.api.user.service.UserService;
import com.sandu.constant.Constants;
import com.sandu.constant.Punctuation;
import com.sandu.service.product.dao.ProductDao;
import com.sandu.util.CodeUtil;
import com.sandu.util.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.sandu.api.product.input.ProductAdd.PRODUCT_TYPE_HARD;
import static com.sandu.api.product.input.ProductAdd.PRODUCT_TYPE_SOFT;
import static com.sandu.api.product.model.Product.*;
import static com.sandu.util.CodeUtil.formatCode;
import static com.sandu.util.Commoner.isNotEmpty;
import static jdk.nashorn.internal.objects.NativeMath.log;

/**
 * @author Sandu
 */
@Service("productBizService")
@Slf4j
@SuppressWarnings({"dep-ann", "JavaDoc"})
public class ProductBizServiceImpl implements ProductBizService {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CategoryBizService categoryBizService;
    private final ProductStyleService productStyleService;
    private final BaseProductStyleService baseProductStyleService;
    private final PlatformService platformService;
    private final Platform2bProductRelService platform2bProductRelService;
    private final Platform2cProductRelService platform2cProductRelService;
    private final DictionaryService dictionaryService;
    private final ProductCategoryRelService productCategoryRelService;
    private final BaseSeriesService baseSeriesService;
    private final BrandService brandService;
    private final ResModelService resModelService;
    private final CompanyBizService companyBizService;
    private final ProductCategoryRelBizService productCategoryRelBizService;
    private final ProductAttributeService productAttributeService;
    private final ResPicService resPicService;
    private final ResTextureService resTextureService;
    private final ResModelBizService resModelBizService;
    private final ModelAreaRelService modelAreaRelService;
    private final UserService userService;
    private final QueueService queueService;
    private final CompanyService companyService;
    private final ProductDao productDao;
    private final BaseGoodsSpuService baseGoodsSpuService;
    private final ProductPropService productPropService;

    @Value("${product.texTure}")
    private String productTexTure;

    private static final ThreadLocal<Map<String, Dictionary>> local = new ThreadLocal<>();

    private static final String FIX_UPDATE_MAP_KEY_PIC = "pic";
    private static final String FIX_UPDATE_MAP_KEY_PICS = "pics";

    private static final Integer SAVE_PRODUCT_FIX_THREAD = 8;


    private ThreadFactory proPool = new ThreadFactoryBuilder().setNameFormat("product biz server pool").build();
    private final ThreadPoolExecutor executorService =
            new ThreadPoolExecutor(
                    30,
                    60,
                    30,
                    TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(512),
                    proPool,
                    new ThreadPoolExecutor.AbortPolicy()
            );


//	private final ExecutorService executorService = Executors.newFixedThreadPool(30);

    @Autowired
    public ProductBizServiceImpl(
            ProductService productService,
            CategoryService categoryService,
            QueueService queueService,
            ModelAreaRelService modelAreaRelService,
            CategoryBizService categoryBizService,
            ProductDao productDao,
            ResPicService resPicService,
            ProductStyleService productStyleService,
            BaseProductStyleService baseProductStyleService,
            PlatformService platformService,
            Platform2bProductRelService platform2bProductRelService,
            ResTextureService resTextureService,
            Platform2cProductRelService platform2cProductRelService,
            BrandService brandService,
            ResModelBizService resModelBizService,
            CompanyService companyService,
            ProductPropService productPropService,
            ResModelService resModelService,
            UserService userService,
            DictionaryService dictionaryService,
            ProductAttributeService productAttributeService,
            BaseGoodsSpuService baseGoodsSpuService,
            ProductCategoryRelBizService productCategoryRelBizService,
            ProductCategoryRelService productCategoryRelService,
            BaseSeriesService baseSeriesService,
            CompanyBizService companyBizService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.queueService = queueService;
        this.modelAreaRelService = modelAreaRelService;
        this.categoryBizService = categoryBizService;
        this.productDao = productDao;
        this.resPicService = resPicService;
        this.productStyleService = productStyleService;
        this.baseProductStyleService = baseProductStyleService;
        this.platformService = platformService;
        this.platform2bProductRelService = platform2bProductRelService;
        this.resTextureService = resTextureService;
        this.platform2cProductRelService = platform2cProductRelService;
        this.brandService = brandService;
        this.resModelBizService = resModelBizService;
        this.companyService = companyService;
        this.productPropService = productPropService;
        this.resModelService = resModelService;
        this.userService = userService;
        this.dictionaryService = dictionaryService;
        this.productAttributeService = productAttributeService;
        this.baseGoodsSpuService = baseGoodsSpuService;
        this.productCategoryRelBizService = productCategoryRelBizService;
        this.productCategoryRelService = productCategoryRelService;
        this.baseSeriesService = baseSeriesService;
        this.companyBizService = companyBizService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Integer> saveProductBiz(ProductAdd add) {
        // 预处理productAdd 数据
        setProductAddInfo(add);
        // 设置产品信息
        Product product = setProductInfos(add);

        // 生成多个产品信息
        List<Long> ids = setProductSKUInfoAndSave(product, add, add.getAddProductType());
        return ids.stream().map(Long::intValue).distinct().collect(Collectors.toList());
        //        List<Product> products = setProductSKUInfoAndSave(product, add);
        // 更新产品信息相关表
        //        syncProductRelInfos(add, products);
        //        return products.stream().map(item ->
        // item.getId().intValue()).distinct().collect(Collectors.toList());
    }

    private void setProductAddInfo(ProductAdd add) {
        // 处理未选中sku信息的产品
        if (add.getProductSKUInfos() == null || add.getProductSKUInfos().isEmpty()) {
            ProductSKUInfo productSKUInfo = new ProductSKUInfo();
            productSKUInfo.setTextureId(add.getTextureId());
            productSKUInfo.setModelId(add.getModelId());
            productSKUInfo.setPropIds(new LinkedList<>());
            productSKUInfo.setProps(new LinkedList<>());
            productSKUInfo.setModelTextureInfos(new LinkedList<>());
            add.setProductSKUInfos(new LinkedList<>());
            add.getProductSKUInfos().add(productSKUInfo);
        } else {
            // 处理多个产品使用一个模型
            List<ProductSKUInfo> skus = add.getProductSKUInfos();
            // 模型产品
            if (skus.stream().anyMatch(item -> Objects.nonNull(item.getModelId()))) {
                // 剔除未关联模型的
                skus.stream()
                        .filter(item -> Objects.nonNull(item.getModelId()))
                        // 根据模型ID分组
                        .collect(Collectors.groupingBy(ProductSKUInfo::getModelId, Collectors.toList()))
                        // 获取共用同一模型ID的sku
                        .entrySet()
                        .stream()
                        .filter(item -> item.getValue().size() != 1)
                        .map(Map.Entry::getValue)
                        // 将除第一个之外的sku的模型ID置空
                        .findAny()
                        .ifPresent(list -> list.stream().skip(1).forEach(item -> item.setModelId(null)));

            } else {

                // 贴图产品 和 未选中模型/贴图的产品  不处理
                return;
            }
        }
    }

    private BaseGoodsSpu saveGoodsSpu(String smallType, Product product) {
        BaseGoodsSpu spu =
                BaseGoodsSpu.builder()
                        .gmtCreate(LocalDateTime.now())
                        .gmtModified(LocalDateTime.now())
                        .picId(product.getPicId())
                        .picIds(product.getPicIds())
                        .spuName(product.getProductName())
                        .productName(product.getProductName())
                        .productModelNumber(product.getProductModelNumber())
                        .productDesc(product.getProductDesc())
                        .isDeleted(0)
                        .smallTypeMark(smallType)
                        .companyId(product.getCompanyId())
                        .build();
        spu.setSpuCode(CodeUtil.formatCode("PA", baseGoodsSpuService.getMaxSpuId()));
        baseGoodsSpuService.insertGoodSpu(spu);
        if (null == spu) {
            return null;
        }
        Gson gson = new Gson();
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(spu.getId());
        sycMessageDoSend(SyncMessage.ACTION_UPDATE, ids);
        log.info("更新商品spu特价信息发送消息完成,商品ID列表:" + gson.toJson(ids));
        return spu;
    }

    private void sycMessageDoSend(Integer messageAction, List<Integer> ids) {
        List<Map> content =
                ids.stream()
                        .map(
                                item -> {
                                    HashMap<String, Integer> tmp = new HashMap<>(1);
                                    tmp.put("id", item);
                                    return tmp;
                                })
                        .collect(Collectors.toList());
        SyncMessage message = new SyncMessage();
        message.setAction(messageAction);
        message.setMessageId("G-" + System.currentTimeMillis());
        message.setModule(SyncMessage.GOODS_MODULE);
        message.setPlatformType("miniProgram");
        message.setObject(content);
        queueService.send(message);
    }

    private List<Long> setProductSKUInfoAndSave(
            Product productSource, ProductAdd add, String productType) {
        List<Long> resultIds = new ArrayList<>();
        if (PRODUCT_TYPE_HARD.equals(productType)) {
            // 定制类产品
            // 新增硬装产品时,先处理主产品
            Optional<ProductSKUInfo> mainSku =
                    add.getProductSKUInfos()
                            .stream()
                            .filter(item -> item.getMainProductFlag() != null && item.getMainProductFlag())
                            .findFirst();
            mainSku.orElseThrow(() -> new RuntimeException("未设置主产品,数据异常."));
            // 新增主产品,并将主产品标志和主产品ID回写到Product Template中
            this.syncHardProductBaseInfoWithMainProduct(
                    mainSku.get(), productSource, local.get().get(Dictionary.DIC_TYPE_SMALL_TYPE));
            // 新增副产品
            resultIds =
                    syncHardProductInfoWithMainProduct(
                            add.getProductSKUInfos(),
                            productSource,
                            local.get().get(Dictionary.DIC_TYPE_SMALL_TYPE),
                            add.getCategoryIds())
                            .stream()
                            .map(Integer::longValue)
                            .collect(Collectors.toList());
        } else if (PRODUCT_TYPE_SOFT.equals(productType)) {
            // 软装产品
            // 设置产品spu信息
            BaseGoodsSpu spu = saveGoodsSpu(add.getProductSmallType(), productSource);
            productSource.setGoodsSpuId(spu.getId());
            resultIds = this.syncSoftProductSKUInfoAndSave(productSource, add);
        }
        return resultIds;
    }

    private List<Long> syncSoftProductSKUInfoAndSave(Product productSource, ProductAdd add) {
        // 根据模型ID获取模型区域-贴图信息
        Set<Integer> modelIds =
                add.getProductSKUInfos()
                        .stream()
                        .map(ProductSKUInfo::getModelId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
        Map<Integer, List<ModelAreaTextureRelBO>> modelId2AreaTextures =
                resModelBizService.modelId2AreaTextures(modelIds);

        // 设置模型区域信息到sku中
        add.getProductSKUInfos()
                .forEach(item -> item.setModelTextureInfos(modelId2AreaTextures.get(item.getModelId())));
        List<ProductSKUInfo> productSKUInfos = add.getProductSKUInfos();

        String platformType = add.getPlatformType();
        List<Product> products =
                productSKUInfos
                        .stream()
                        .map(
                                item -> {
                                    Product product =
                                            setProductModelInfo(
                                                    productSource,
                                                    platformType,
                                                    item,
                                                    local.get().get(Dictionary.DIC_TYPE_SMALL_TYPE));
                                    product.setProductCode(formatCode("P", productService.getMaxId()));
                                    productService.saveProduct(product);
                                    return product;
                                })
                        .collect(Collectors.toList());
        // 同步分类,属性信息,释放模型
        this.syncProductRelInfos(add, products);
        return products.stream().map(Product::getId).collect(Collectors.toList());
    }

    /**
     * 设置产品中的模型-贴图 以及发布状态
     *
     * @param productSource
     * @param platformType
     * @param item
     * @return
     */
    private Product setProductModelInfo(
            Product productSource, String platformType, ProductSKUInfo item, Dictionary smallType) {
        Product product = Product.copyBean(productSource);
        // 设置产品发布状态
        product.setModelId(item.getModelId());
        product.setWindowsU3dmodelId(item.getModelId());
        product.setPutawayState(3);
//		if (item.getTextureId() != null || item.getModelId() != null) {
//			product.setPutawayState(3);
//		}
        // 设置产品定时器不处理标志
        if (StringUtils.isNoneBlank(platformType)) {
            product.setNumAtt3(Double.valueOf("1"));
        }
        // 关联模型/贴图信息
        if ((item.getModelTextureInfos() != null && !item.getModelTextureInfos().isEmpty())
                || item.getTextureId() != null) {
            setProductModelOrTexture(
                    product, smallType, item.getModelTextureInfos(), item.getTextureId());
        }
        return product;
    }

    private Product setProductInfos(ProductAdd add) {
        log.debug("product add:{}", add);
        Product product = new Product();
        product.setProductName(add.getName());
        product.setBrandId(add.getBrandId());
        if (add.getModelingId() != null) {
            product.setStyleId(add.getModelingId().longValue());
        }
        product.setProductSpec(add.getProductSpec());
        product.setSeriesId(add.getSeriesId());
        product.setSalePrice(add.getPrice());
        product.setAdvicePrice(add.getAdvicePrice());
        product.setSalePriceValue(add.getSaleUnionId());
        product.setProductDesc(add.getDesc());
        product.setProductLength(add.getLength());
        product.setProductWidth(add.getWidth());
        product.setProductHeight(add.getHeight());
        product.setIsCreatedTexture(add.getIsCreatedTexture());
        product.setIsComplexParquet(add.getIsComplexParquet());
        if (add.getMaterialPicIds() != null) {
            product.setMaterialPicIds(add.getMaterialPicIds());
        }
//        Map<String, Integer> typeAndSmallType = dictionaryService.getProductTypeValueAndSmallBySmallValueKey(add.getCategoryCode());
        //设置产品大小类信息
        Map<String, Dictionary> typeInfo = setProductTypeInfo(add.getProductSmallType(), product);
//        product.setProductCode(formatCode("P", productService.getMaxId()));
        product.setIsDeleted(0);
        if (add.getDefaultPicId() != null) {
            product.setPicId(Integer.parseInt(add.getDefaultPicId()));
        }
        UserBo userInfo = userService.getUserById(add.getUserId());
        product.setCreator(userInfo.getNickName());
        product.setModifier(userInfo.getNickName());
        product.setCompanyId(userInfo.getBusinessAdministrationId());
        product.setGmtCreate(new Date());
        product.setGmtModified(new Date());
        product.setAdvicePrice(add.getAdvicePrice());
        //设置产品风格
        Optional.ofNullable(add.getBaseStyleId()).ifPresent(item ->
                product.setProductStyleIdInfo(JsonParser.toJson(StyleParseBO.builder().isLeaf_0(Joiner.on(Punctuation.COMMA).join(item)).isLeaf_1("")
                        .build())));
        //设置产品款式
        Optional.ofNullable(add.getModelingId()).map(Integer::longValue).ifPresent(product::setStyleId);
        product.setProductModelNumber(add.getModelNumber());
        product.setModelId(add.getModelId());
        product.setWindowsU3dmodelId(add.getModelId());
        product.setProductModelNumber(add.getModelNumber());
        product.setHouseTypeValues("4,5,6,7,8,9,11,12,13,14,15,16");
        product.setSecrecyFlag(1);
        //设置产品发布状态
//			product.setPutawayState(0);
        product.setPutawayState(3);
//			if (add.getTextureId() != null || product.getModelId() != null) {
//				product.setPutawayState(3);
//			}
        //设置产品定时器不处理标志
        if (StringUtils.isNoneBlank(add.getPlatformType())) {
            product.setNumAtt3(Double.valueOf("1"));
        }
        Map<String, String> picHelp =
                this.fixWithPic(Integer.valueOf(add.getDefaultPicId()), add.getPicIds());
        product.setPicId(Integer.valueOf(picHelp.get(FIX_UPDATE_MAP_KEY_PIC)));
        product.setPicIds(picHelp.get(FIX_UPDATE_MAP_KEY_PICS));

        return product;
    }

    //    @Deprecated
    //    private void syncProductRelInfo(ProductAdd add, Product product) {
    //        Category category = categoryService.getCategoryByCode(add.getCategoryCode());
    //        //插入分类信息
    //        if (Objects.nonNull(add.getCategoryIds())) {
    //            updateCategoryAttrInfo(add.getCategoryIds(),
    // Collections.singletonList(product.getId().intValue()));
    //        }
    //        //更新模型绑定产品信息
    //        if (product.getModelId() != null) {
    //            resModelService.saveResModel(ResModelUpdate.builder()
    //                    .modelId(product.getModelId().longValue())
    //                    .productId(product.getId().intValue()).build());
    //        }
    //        //产品分配
    //        if (add.getPlatformType() != null &&
    // StringUtils.isNoneBlank(add.getPlatformType().trim())) {
    //            //分配渠道
    //            List<String> strings =
    // Arrays.asList(add.getPlatformType().split(Punctuation.COMMA));
    //            List<Integer> productIds = new ArrayList<>();
    //            productIds.add(product.getId().intValue());
    //            this.allotProduct(productIds, strings, Platform.DO_ALLOT);
    //        }
    //        //关联产品属性信息
    ////        List<ProductAttribute> attrs = setProductAttributesInfo(add.getProps(), product,
    // add.getCategoryCode());
    //        setSKUProductPropInfos(add.getProductSKUInfos());
    //        List<ProductAttribute> attrs = setProductAttributesInfo(add.getProps(), product,
    // add.getProductSmallType());
    //        productAttributeService.saveProductAttributeList(attrs);
    //
    //    }

    /**
     * 根据已选择的propIds拼接出PropBO对象,设置到List中.
     *
     * @param productSKUInfos
     */
    private void setSKUProductPropInfos(List<ProductSKUInfo> productSKUInfos) {
        if (productSKUInfos == null || productSKUInfos.isEmpty()) {
            return;
        }
        List<Integer> allPropIds = new LinkedList<>();
        productSKUInfos.forEach(item -> allPropIds.addAll(item.getPropIds()));
        List<ProductProp> parentAndItselfByIds = productPropService.getParentAndItselfByIds(allPropIds);
        List<ProductPropBO> collect =
                parentAndItselfByIds
                        .stream()
                        .filter(item -> item.getLevel().equals(ProductProp.PROP_LEVEL_FIFTH))
                        .map(
                                cProp -> {
                                    ProductPropBO boC =
                                            ProductPropBO.builder()
                                                    .code(cProp.getCode())
                                                    .id(cProp.getId().intValue())
                                                    .name(cProp.getName())
                                                    .build();
                                    parentAndItselfByIds
                                            .stream()
                                            .filter(pProp -> cProp.getPid().equals(pProp.getId().intValue()))
                                            .findFirst()
                                            .ifPresent(
                                                    pProp -> {
                                                        ProductPropBO boP =
                                                                ProductPropBO.builder()
                                                                        .code(pProp.getCode())
                                                                        .id(pProp.getId().intValue())
                                                                        .name(pProp.getName())
                                                                        .build();
                                                        boC.setParent(boP);
                                                    });
                                    return boC;
                                })
                        .collect(Collectors.toList());
        productSKUInfos.forEach(
                sku -> {
                    List<ProductPropBO> skuProps =
                            collect
                                    .stream()
                                    .filter(item -> sku.getPropIds().contains(item.getId()))
                                    .collect(Collectors.toList());
                    sku.setProps(skuProps);
                });
    }

    /**
     * 批量同步 产品运营分类-基本属性-对应模型状态
     *
     * @param add
     * @param products
     */
    private void syncProductRelInfos(ProductAdd add, List<Product> products) {
        List<Integer> productIds =
                products.stream().map(item -> item.getId().intValue()).collect(Collectors.toList());
        // 插入分类信息
        if (Objects.nonNull(add.getCategoryIds())) {
            updateCategoryAttrInfo(add.getCategoryIds(), productIds);
        }
        // 关联产品属性信息
        List<ProductAttribute> attrs = new LinkedList<>();
        setSKUProductPropInfos(add.getProductSKUInfos());
        for (int i = 0; add.getProductSKUInfos() != null && i < add.getProductSKUInfos().size(); i++) {
            Product product = products.get(i);
            // 更新模型绑定产品信息
            if (product.getModelId() != null) {
                resModelService.saveResModel(
                        ResModelUpdate.builder()
                                .modelId(product.getModelId().longValue())
                                .productId(product.getId().intValue())
                                .build());
            }
            // 设置产品-属性关联对象
            attrs.addAll(
                    setProductAttributesInfo(
                            add.getProductSKUInfos().get(i).getProps(), product, add.getProductSmallType()));
        }
        productAttributeService.saveProductAttributeList(attrs);
        // 产品分配
        if (StringUtils.isNoneBlank(add.getPlatformType())) {
            // 分配渠道
            List<String> strings = Arrays.asList(add.getPlatformType().split(Punctuation.COMMA));
            this.allotProduct(productIds, strings, Platform.DO_ALLOT);
        }
    }

    private void updateCategoryAttrInfo(List<Integer> categoryIds, List<Integer> productIds) {
        productCategoryRelService.deleteProductCategoryRelByProductIds(productIds);
        if (categoryIds == null || categoryIds.isEmpty()) {
            return;
        }
        //        ProductCategoryRel relMain = new ProductCategoryRel();
        //        relMain.setCategoryId(category.getId().intValue());
        //        relMain.setProductId(id);
        //        relMain.setNumAtt1(1);
        //        relMain.setGmtCreate(new Date());
        //        relMain.setGmtModified(new Date());
        //        relMain.setIsDeleted(0);
        //        productCategoryRelBizService.updateBiz(relMain);
        //        更新产品分类子节点信息
        List<ProductCategoryRel> rels = new LinkedList<>();
        categoryIds
                .stream()
                .filter(Objects::nonNull)
                .forEach(
                        categoryId ->
                                productIds.forEach(
                                        productId -> {
                                            ProductCategoryRel rel = new ProductCategoryRel();
                                            rel.setCategoryId(categoryId);
                                            rel.setProductId(productId);
                                            rel.setIsDeleted(0);
                                            rel.setGmtCreate(new Date());
                                            rel.setGmtModified(new Date());
                                            rels.add(rel);
                                        }));
        productCategoryRelService.saveProductCategoryRelList(rels);
    }

    /**
     * 校验是模型还是贴图产品
     *
     * @param smallType
     * @return
     */
    private void checkModelOrTexture(Dictionary smallType) {
        String att1Info = "";
        if (Objects.isNull(smallType)) {
            log.error("小类编码{}在数据字典不存");
        }
        if (Objects.isNull(productTexTure)) {
            log.error("参数[product.texTure]未配置");
        }
        List<String> productTextList = Arrays.asList(productTexTure.split(Punctuation.COMMA));
        att1Info = productTextList.contains(smallType.getValuekey()) ? "0" : "1";
        smallType.setAtt1Info(att1Info);
    }

    /**
     * 设置模型-贴图信息
     */
    private void setProductModelOrTexture(
            Product product,
            Dictionary smallType,
            List<ModelAreaTextureRelBO> modelInfos,
            Integer textureId) {
        // 判断是模型还是贴图
        checkModelOrTexture(smallType);
        if (Objects.isNull(smallType.getAtt1Info())) {
            log.error("---------设置产品模型异常,小类信息缺失----------");
            return;
        }
        // 更新模型贴图信息
        if (Category.CATEGORY_TYPE_MODEL.equals(Integer.parseInt(smallType.getAtt1Info()))
                && product.getModelId() != null) {
            if (modelInfos == null) {
                product.setMaterialPicIds(-1 + "");
                product.setSplitTexturesInfo("");
                return;
            }
            // 删除现存的区域中的贴图信息
            log.info("log for delete area text rel , model id  is :{}", product.getModelId());
            resModelBizService.deleteAreaTextureInfoByModelId(product.getModelId());
            // 此模型区域信息
            List<ModelAreaRel> areas = modelAreaRelService.listByModelId(product.getModelId());
            List<ModelAreaTextureRel> list = new ArrayList<>();
            modelInfos.forEach(
                    item -> {
                        ModelAreaTextureRel tmp = new ModelAreaTextureRel();
                        BeanUtils.copyProperties(item, tmp);
                        if (item.getIsDefault() == null) {
                            item.setIsDefault(0);
                        }
                        tmp.setIsDefault(item.getIsDefault().byteValue());
                        tmp.setGmtCreate(new Date());
                        tmp.setGmtModified(new Date());
                        list.add(tmp);
                    });
            // 兼容之前的模型贴图信息
            List<ProductTextureInfo> oldList = new ArrayList<>();
            Set<Integer> materialPicIds = new HashSet<>();
            // 遍历区域
            areas.forEach(
                    area -> {
                        ProductTextureInfo pti = new ProductTextureInfo();
                        StringBuilder textureIdsStr = new StringBuilder();
                        pti.setAffectTextures(new ArrayList<>());
                        // 匹配区域贴图
                        modelInfos
                                .stream()
                                .filter(item -> item.getAreaId().equals(area.getId()))
                                .forEach(
                                        item -> {
                                            textureIdsStr.append(item.getTextureId()).append(",");
                                            if (item.getIsDefault() == 1) {
                                                pti.setDefaultId(item.getTextureId());
                                            }
                                            TextureInfo textureInfo = new TextureInfo();
                                            textureInfo.setAffectPrice(item.getAffectPrice());
                                            textureInfo.setId(item.getTextureId());
                                            pti.getAffectTextures().add(textureInfo);
                                            materialPicIds.add(item.getTextureId());
                                        });
                        pti.setKey(Integer.parseInt(area.getCode()) + 1 + "");
                        pti.setName(area.getName());
                        pti.setHeight(area.getHeight());
                        pti.setWidth(area.getWidth());
                        if (textureIdsStr.length() > 0) {
                            pti.setTextureIds(textureIdsStr.substring(0, textureIdsStr.length() - 1));
                            // 初始化默认贴图
                            if (pti.getDefaultId() == null) {
                                int areaDefaultTextureId =
                                        Integer.parseInt(
                                                textureIdsStr.substring(0, textureIdsStr.indexOf(Punctuation.COMMA)));
                                // 维护model-area-texture中的记录
                                list.stream()
                                        .filter(
                                                areaTextureRel ->
                                                        areaTextureRel.getAreaId().equals(area.getId())
                                                                && areaTextureRel.getTextureId().equals(areaDefaultTextureId))
                                        .forEach(areaTextureRel -> areaTextureRel.setIsDefault(Byte.valueOf("1")));
                                // 维护产品表中的材质信息
                                pti.setDefaultId(areaDefaultTextureId);
                            }
                        }
                        oldList.add(pti);
                    });
            product.setMaterialPicIds(Joiner.on(Punctuation.COMMA).skipNulls().join(materialPicIds));
            product.setSplitTexturesInfo(JsonParser.toJson(oldList));
            // 插入新信息
            resModelBizService.saveModelTextureInfo(list);
        } else if (Category.CATEGORY_TYPE_TEXTURE.equals(Integer.parseInt(smallType.getAtt1Info()))) {
            // 设置贴图产品
            product.setWindowsU3dmodelId(-1);
            product.setMaterialPicIds(textureId + "");
        }
    }

    private List<ProductAttribute> setProductAttributesInfo(
            List<ProductPropBO> props, Product product, String smallTypeCode) {
        if (props == null || props.isEmpty()) {
            return Collections.emptyList();
        }
        List<ProductAttribute> attrs = new LinkedList<>();
        for (ProductPropBO prop : props) {
            if (!Optional.ofNullable(prop).isPresent()) {
                continue;
            }
            ProductAttribute attr = new ProductAttribute();
            attr.setProductId(product.getId().intValue());
            attr.setProductCode(product.getProductCode());
            // 三级属性
            attr.setAttributeTypeValue(smallTypeCode);
            // 四级属性
            attr.setAttributeId(prop.getParent().getId());
            attr.setAttributeName(prop.getParent().getName());
            attr.setAttributeKey(prop.getParent().getCode());
            // 五级属性
            attr.setAttributeValueId(prop.getId());
            attr.setAttributeValueName(prop.getName());
            attr.setAttributeValueKey(prop.getCode());
            // 系统属性
            attr.setGmtCreate(new Date());
            attr.setGmtModified(new Date());
            attr.setIsDeleted(0);
            attrs.add(attr);
        }
        return attrs;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateProductBiz(ProductUpdatePO update) {
        Product product = setProductBaseInfo(update, update.getProduct());
        // 内容库才可编辑以下信息
        if (Platform.PLATFORM_LIBRARY.equals(update.getEditType())) {
            // 设置产品大小类
            //            Map<String, Integer> typeAndSmallType =
            // dictionaryService.getProductTypeValueAndSmallBySmallValueKey(update.getCategoryCode());
            Map<String, Dictionary> productTypeInfo =
                    setProductTypeInfo(update.getCategoryCode(), product);

            Map<String, String> picHelp =
                    this.fixWithPic(Integer.valueOf(update.getDefaultPicId()), update.getPicIds());
            product.setPicId(Integer.valueOf(picHelp.get(FIX_UPDATE_MAP_KEY_PIC)));
            product.setPicIds(picHelp.get(FIX_UPDATE_MAP_KEY_PICS));

            product.setGmtModified(new Date());
            product.setProductModelNumber(update.getModelNumber());
            product.setModelId(update.getModelId());
            product.setProductHeight(update.getHeight());
            product.setProductWidth(update.getWidth());
            product.setProductLength(update.getLength());
            product.setMaterialPicIds(update.getMaterialPicIds());
            // 设置产品风格
            Optional.ofNullable(update.getBaseStyleId())
                    .ifPresent(
                            item ->
                                    product.setProductStyleIdInfo(
                                            JsonParser.toJson(
                                                    StyleParseBO.builder()
                                                            .isLeaf_0(Joiner.on(Punctuation.COMMA).join(item))
                                                            .isLeaf_1("")
                                                            .build())));
            // 设置产品款式
            Optional.ofNullable(update.getModelingId())
                    .map(Integer::longValue)
                    .ifPresent(product::setStyleId);
            product.setWindowsU3dmodelId(update.getModelId());
            product.setProductModelNumber(update.getModelNumber());
            product.setModifier(userService.getUserById(update.getUserId()).getUserName());
            // 设置产品发布状态
            product.setPutawayState(3);

            // 关联模型/贴图信息
            if (update.getModelTextureInfos() == null && Objects.nonNull(update.getModelId())) {
                // 存在模型ID 不存在模型贴图信息为软装产品更换模型
                // 存在模型ID 存在模型贴图信息则为硬装产品更新
                // 获取当前模型的区域贴图信息
                resModelBizService
                        .modelId2AreaTextures(Collections.singleton(update.getModelId()))
                        .entrySet()
                        .stream()
                        .findFirst()
                        .ifPresent(item -> update.setModelTextureInfos(item.getValue()));
            }
            if (update.getModelTextureInfos() != null || update.getTextureId() != null) {
                setProductModelOrTexture(
                        product,
                        productTypeInfo.get(Dictionary.DIC_TYPE_SMALL_TYPE),
                        update.getModelTextureInfos(),
                        update.getTextureId());
            }
            // 释放旧模型,更新新模型状态
            releaseModelSource(update, product);

            // 更新产品
            productService.updateProduct(product);

            // 关联产品属性信息
            productAttributeService.deleteProductAttributeByProductId(product.getId());

            List<ProductAttribute> attrs =
                    setProductAttributesInfo(update.getProps(), product, update.getCategoryCode());

            productAttributeService.saveProductAttributeList(attrs);

            // 更新产品运营树
            updateCategoryAttrInfo(
                    update.getCategoryIds(), Collections.singletonList(update.getId().intValue()));

            // 同步2c/2b状态
            syncPlatformProductRels(
                    product, Collections.singletonList(product.getId()), update.getBaseStyleId());

        } else {
            // 2b/2c更新
            syncPlatformProductRels(update, product);
        }
        return true;
    }

    /**
     * 同步产品平台信息表中的 产品信息
     *
     * @param product
     * @param productIds
     * @param baseStyleIds
     */
    private void syncPlatformProductRels(
            Product product, List<Long> productIds, List<Integer> baseStyleIds) {
        PlatformProductRel rel = new PlatformProductRel();
        rel.setProductId(product.getId().intValue());
        if (product.getAdvicePrice() != null) {
            rel.setAdvicePrice(product.getAdvicePrice());
        }
        if (product.getSalePrice() != null) {
            rel.setSalePrice(product.getSalePrice());
        }
        rel.setDescription(product.getProductDesc());
        Optional.ofNullable(baseStyleIds)
                .ifPresent(item -> rel.setStyleId(Joiner.on(Punctuation.COMMA).join(item)));
        // 设置图片
        //        Map<String, String> picHelp = this.fixWithPic(product.getPicId(),
        // product.getPicIds());
        //        rel.setPicId(Integer.valueOf(picHelp.get(FIX_UPDATE_MAP_KEY_PIC)));
        //        rel.setPicIds(picHelp.get(FIX_UPDATE_MAP_KEY_PICS));
        rel.setPicId(product.getPicId());
        rel.setPicIds(product.getPicIds());

        rel.setGmtModified(new Date());
        rel.setModifier(product.getModifier());

        // 构造对象
        List<PlatformProductRel> brels = new ArrayList<>();
        List<PlatformProductRel> crels = new ArrayList<>();
        List<Integer> platformIds2b =
                platformService.getPlatformIdsByBussinessTypes(
                        Collections.singletonList(Platform.BUSINESS_TYPE_2B));
        List<Integer> platformIds2c =
                platformService.getPlatformIdsByBussinessTypes(
                        Collections.singletonList(Platform.BUSINESS_TYPE_2C));
        for (Long productId : productIds) {
            rel.setProductId(productId.intValue());
            setPlatformProductRels(rel, brels, platformIds2b);
            setPlatformProductRels(rel, crels, platformIds2c);
        }
        platform2bProductRelService.updatePlatformProductRels(brels);
        platform2cProductRelService.updatePlatformProductRels(crels);
    }

    @Deprecated
    private void syncPlatformProductRels(ProductUpdatePO update, Product product) {
        PlatformProductRel rel = new PlatformProductRel();
        rel.setProductId(product.getId().intValue());
        if (product.getAdvicePrice() != null) {
            rel.setAdvicePrice(product.getAdvicePrice());
        }
        if (product.getSalePrice() != null) {
            rel.setSalePrice(product.getSalePrice());
        }
        rel.setDescription(product.getProductDesc());
        Optional.ofNullable(update.getBaseStyleId())
                .ifPresent(item -> rel.setStyleId(Joiner.on(Punctuation.COMMA).join(item)));
        // 设置图片
        Map<String, String> picHelp = this.fixWithPic(product.getPicId(), product.getPicIds());
        rel.setPicId(Integer.valueOf(picHelp.get(FIX_UPDATE_MAP_KEY_PIC)));
        rel.setPicIds(picHelp.get(FIX_UPDATE_MAP_KEY_PICS));

        rel.setGmtModified(new Date());
        rel.setModifier(userService.getUserById(update.getUserId()).getUserName());
        List<PlatformProductRel> rels = new ArrayList<>();
        if (Platform.PLATFORM_CHANNEL.equals(update.getEditType())) {
            List<Integer> platformIds =
                    platformService.getPlatformIdsByBussinessTypes(
                            Collections.singletonList(Platform.BUSINESS_TYPE_2B));
            setPlatformProductRels(rel, rels, platformIds);
            platform2bProductRelService.updatePlatformProductRels(rels);
        }
        if (Platform.PLATFORM_ONLINE.equals(update.getEditType())) {
            List<Integer> platformIds =
                    platformService.getPlatformIdsByBussinessTypes(
                            Collections.singletonList(Platform.BUSINESS_TYPE_2C));
            setPlatformProductRels(rel, rels, platformIds);
            platform2cProductRelService.updatePlatformProductRels(rels);
        }
    }

    private void releaseModelSource(ProductUpdatePO update, Product product) {
        // 获取旧模型的ID
        Product pro = productService.getProductInfoById(product.getId());
        Integer oldModelId = null;
        if (isNotEmpty(pro)) {
            oldModelId = pro.getModelId();
        }
        Integer modelId = product.getModelId();
        // 比较新旧模型
        if (oldModelId != null && !oldModelId.equals(modelId) && modelId != null) {
            // 更新旧模型的状态
            resModelService.saveResModel(
                    ResModelUpdate.builder().modelId(oldModelId.longValue()).productId(0).build());
        }
        // 更新新模型
        if (update.getId() != null && update.getModelId() != null) {
            resModelService.saveResModel(
                    ResModelUpdate.builder()
                            .modelId(modelId.longValue())
                            .productId(update.getId().intValue())
                            .build());
        }
        if (modelId != null && modelId == -1) {
            // 清空材质信息
            product.setSplitTexturesInfo("");
            product.setMaterialPicIds("");
            if(update.getTextureId()!=null) {
                product.setMaterialPicIds(update.getTextureId()+"");
            }
        }
    }


    private Product setProductBaseInfo(ProductUpdatePO update, @NotNull Product product) {
        product.setId(update.getId());
        product.setProductName(update.getName());
        product.setBrandId(update.getBrandId());
        product.setProductSpec(update.getProductSpec());
        if (update.getModelingId() != null) {
            product.setStyleId(update.getModelingId().longValue());
        }
        if (update.getSeriesId() != null) {
            product.setSeriesId(update.getSeriesId());
        }
        if (update.getPrice() != null) {
            product.setSalePrice(update.getPrice());
        }
        if (update.getAdvicePrice() != null) {
            product.setAdvicePrice(update.getAdvicePrice());
        }
        if (update.getSaleUnionId() != null) {
            product.setSalePriceValue(update.getSaleUnionId());
        }
        if (update.getDesc() != null) {
            product.setProductDesc(update.getDesc());
        }
        if (update.getLength() != null) {
            product.setProductLength(update.getLength());
        }
        if (update.getWidth() != null) {
            product.setProductLength(update.getWidth());
        }
        if (update.getHeight() != null) {
            product.setProductLength(update.getHeight());
        }
        if (StringUtils.isNoneBlank(update.getDefaultPicId())) {
            product.setPicId(Integer.parseInt(update.getDefaultPicId()));
        }
        if (StringUtils.isNoneBlank(update.getPicIds())) {
            product.setPicIds(update.getPicIds());
        }
        update.setProduct(product);
        return product;
    }

    /**
     * 硬装产品更新接口 处理逻辑: 1.更新主产品(主产品未更换直接更新, 更换主产品时,会将 当前主产品的信息 更新到之前的主产品中, 之前的主产品信息做更新或者新增处理)
     * 2.根据产品信息,更新副产品(涉及到 新增/更新/删除 副产品)
     *
     * @param updatePO
     * @return 更新的产品ID集合
     */
    @Override
    @Transactional
    public List<Integer> updateHardProductBiz(HardProductUpdatePO updatePO) {
        // 设置主产品信息
        ProductUpdatePO mainProductPO = setMainProductAndSkuList(updatePO);
        // 更新
        this.updateProductBiz(mainProductPO);
        Product mainProduct = mainProductPO.getProduct();
        setHardProductSizeWithModelSize(mainProduct.getModelId(), mainProduct);
        productService.updateProduct(mainProduct);
        // 删除时使用
        updatePO.setModelId(mainProduct.getModelId());

        // 获取 副产品id-modelId 键值对
        Map<Integer, Integer> productId2ModelId =
                productService.listSecondProductId2ModelIdWithMainId(updatePO.getId());
        // 排除主产品
        productId2ModelId.remove(mainProduct.getId().intValue());

        Dictionary smallType = dictionaryService.getByValueKey(mainProduct.getProductSmallTypeMark());
        Optional.of(smallType)
                .orElseThrow(
                        () -> {
                            log.error("获取小类信息失败,小类编码:{}", mainProduct.getProductSmallTypeMark());
                            return new RuntimeException("获取小类信息失败");
                        });
        // 更新产品
        List<Integer> updateProductIds =
                updatePO
                        .getProductSKUInfos()
                        .stream()
                        .filter(sku -> productId2ModelId.containsValue(sku.getModelId()))
                        .collect(
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        skuInfoList ->
                                                this.syncHardProductInfoWithMainProduct(
                                                        skuInfoList, mainProduct, smallType, updatePO.getCategoryIds())));
        log.info("更新产品ID:{}", updateProductIds);
        // 同步2b/2c表
        syncPlatformProductRels(
                mainProduct,
                updateProductIds.stream().map(Long::valueOf).collect(Collectors.toList()),
                updatePO.getBaseStyleId());

        // 新增产品
        List<Integer> insertProductIds =
                updatePO
                        .getProductSKUInfos()
                        .stream()
                        .filter(sku -> !productId2ModelId.containsValue(sku.getModelId()))
                        .filter(sku -> !Objects.equals(sku.getModelId(), mainProduct.getWindowsU3dmodelId()))
                        .collect(
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        skuInfoList ->
                                                this.syncHardProductInfoWithMainProduct(
                                                        skuInfoList, mainProduct, smallType, updatePO.getCategoryIds())));
        log.info("新增定制产品ID:{}", insertProductIds);

        // 删除硬装产品
        Set<Integer> deleteSet = deleteHardProductOnUpdate(updatePO, productId2ModelId);
        log.info("删除定制产品ID:{}", deleteSet);

        List<Integer> ids = new LinkedList<>();
        ids.addAll(updateProductIds);
        ids.addAll(insertProductIds);
        ids.addAll(deleteSet);
        return ids;
    }

    private ProductUpdatePO setMainProductAndSkuList(HardProductUpdatePO updatePO) {
        ProductUpdatePO mainProductPO = new ProductUpdatePO();
        BeanUtils.copyProperties(updatePO, mainProductPO);
        mainProductPO.setProduct(new Product());

        updatePO
                .getProductSKUInfos()
                .forEach(
                        it -> {
                            if (it.getMainProductFlag() == null) {
                                it.setMainProductFlag(false);
                            }
                        });
        ProductSKUInfo curMainSku =
                updatePO
                        .getProductSKUInfos()
                        .stream()
                        .filter(ProductSKUInfo::getMainProductFlag)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("获取主产品失败"));

        // 预处理sku中的产品属性
        this.setSKUProductPropInfos(updatePO.getProductSKUInfos());

        mainProductPO.setProps(curMainSku.getProps());
        mainProductPO.setModelId(curMainSku.getModelId());
        mainProductPO.setModelTextureInfos(curMainSku.getModelTextureInfos());
        mainProductPO.getProduct().setProductBatchId(mainProductPO.getId());
        mainProductPO.getProduct().setProductBatchType(PRODUCT_TYPE_PROTO_MAIN);
        mainProductPO.setModelTextureInfos(curMainSku.getModelTextureInfos());

        // 处理sku信息
        if (curMainSku.getProductId() != null) {
            // 主产品为非新增模型
            if (!curMainSku.getProductId().equals(updatePO.getId().intValue())) {
                // 更换主产品
                Optional<ProductSKUInfo> first =
                        updatePO
                                .getProductSKUInfos()
                                .stream()
                                .filter(
                                        preMainSku ->
                                                !preMainSku.getMainProductFlag()
                                                        && preMainSku.getProductId() != null
                                                        && updatePO.getId().equals(preMainSku.getProductId().longValue()))
                                .findFirst();
                if (first.isPresent()) {
                    ProductSKUInfo preMainSku = first.get();
                    Integer curMainSkuId = curMainSku.getProductId();
                    curMainSku.setProductId(preMainSku.getProductId());
                    preMainSku.setProductId(curMainSkuId);
                } else {
                    curMainSku.setProductId(updatePO.getId().intValue());
                }
            }
        } else {
            // 主产品为新增模型  将之前主产品sku productId至空
            updatePO
                    .getProductSKUInfos()
                    .stream()
                    .filter(
                            preMainSku ->
                                    !preMainSku.getMainProductFlag()
                                            && preMainSku.getProductId() != null
                                            && updatePO.getId().equals(preMainSku.getProductId().longValue()))
                    .findFirst()
                    .ifPresent(preMainSku -> preMainSku.setProductId(null));
            curMainSku.setProductId(updatePO.getId().intValue());
        }

        return mainProductPO;
    }

    /**
     * 更新硬装产品时删除产品
     *
     * @param updatePO
     * @param productId2ModelId
     * @return
     */
    private Set<Integer> deleteHardProductOnUpdate(
            HardProductUpdatePO updatePO, Map<Integer, Integer> productId2ModelId) {
        // 删除产品
        Set<Integer> allModelIds =
                updatePO
                        .getProductSKUInfos()
                        .stream()
                        .map(ProductSKUInfo::getModelId)
                        .collect(Collectors.toSet());

        // 即modelId存在于productId2ModelId中，但不存在allModelIds中的产品
        Map<Integer, Integer> deleteList =
                productId2ModelId
                        .entrySet()
                        .stream()
                        .filter(entry -> !allModelIds.contains(entry.getValue()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Integer, Integer> integerIntegerMap =
                productService.listSecondProductId2ModelIdWithMainId(updatePO.getId());
        integerIntegerMap
                .entrySet()
                .stream()
                .filter(
                        it ->
                                it.getValue().equals(updatePO.getModelId())
                                        && !it.getKey().equals(updatePO.getId().intValue()))
                .findFirst()
                .ifPresent(it -> deleteList.put(it.getKey(), it.getValue()));

        deleteHardProduct(deleteList.keySet(), true);

        return deleteList.keySet();
    }

    private void updateAttributeInfo(List<ProductSKUInfo> skuInfos, String smallType) {
        List<Long> ids =
                skuInfos
                        .stream()
                        .map(ProductSKUInfo::getProductId)
                        .map(Long::valueOf)
                        .collect(Collectors.toList());
        // 删除之前的记录
        productAttributeService.deleteProductAttributeByProductIds(ids);

        // 预处理产品属性,构造参数
        this.setSKUProductPropInfos(skuInfos);
        // 拼接对象
        List<ProductAttribute> attrs =
                skuInfos
                        .stream()
                        .filter(sku -> sku.getProductId() != null)
                        .map(
                                sku -> {
                                    Product tmp = new Product();
                                    tmp.setProductCode(sku.getProductCode());
                                    tmp.setId(sku.getProductId().longValue());
                                    return setProductAttributesInfo(sku.getProps(), tmp, smallType);
                                })
                        .reduce(
                                new LinkedList<>(),
                                (o1, o2) -> {
                                    o1.addAll(o2);
                                    return o1;
                                });
        // 插入记录
        productAttributeService.saveProductAttributeList(attrs);
    }

    /**
     * @param skuInfo     产品sku信息List
     * @param mainProduct 定制主产品
     * @param smallType   产品小类
     * @param categoryIds 产品运营分类ids
     * @return 更新的产品ids
     */
    private List<Integer> syncHardProductInfoWithMainProduct(
            @NotNull @NotEmpty List<ProductSKUInfo> skuInfo,
            @NotNull Product mainProduct,
            Dictionary smallType,
            List<Integer> categoryIds) {
        // 新增或者更新产品模型相关信息
        if (skuInfo.size() > SAVE_PRODUCT_FIX_THREAD) {
            fixWithManyProducts(skuInfo, mainProduct, smallType);
        } else {
            // 处理副产品
            skuInfo
                    .stream()
                    .filter(sku -> !Objects.equals(sku.getProductId(), mainProduct.getId().intValue()))
                    .forEach(sku -> this.syncHardProductBaseInfoWithMainProduct(sku, mainProduct, smallType));
        }
        List<Integer> ids =
                skuInfo.stream().map(ProductSKUInfo::getProductId).collect(Collectors.toList());
        log.info("Start sync product info,products:{}", ids);

        // 关联产品属性信息
        updateAttributeInfo(skuInfo, smallType.getValuekey());

        // 更新产品运营树
        updateCategoryAttrInfo(categoryIds, ids);
        log.info("End sync product info");
        return ids;
    }

    private void fixWithManyProducts(
            @NotNull @NotEmpty List<ProductSKUInfo> skuInfo,
            @NotNull Product mainProduct,
            Dictionary smallType) {
        int threadNum = (int) Math.ceil(skuInfo.size() * 1.0 / SAVE_PRODUCT_FIX_THREAD);
        log.info("sku info map:{}", skuInfo.stream().collect(Collectors.groupingBy(ProductSKUInfo::getModelId, Collectors.counting())));
        CountDownLatch doneSignal = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            int finalI = i;
            executorService.submit(
                    () -> {
                        try {
                            log.info(
                                    "start sync product base info in Thread :{}", Thread.currentThread().getName());
                            skuInfo
                                    .stream()
                                    .filter(
                                            sku -> !Objects.equals(sku.getProductId(), mainProduct.getId().intValue()))
                                    .skip(finalI * SAVE_PRODUCT_FIX_THREAD)
                                    .limit(SAVE_PRODUCT_FIX_THREAD)
                                    .forEach(
                                            sku ->
                                                    this.syncHardProductBaseInfoWithMainProduct(
                                                            sku, mainProduct, smallType));
                            log.info(
                                    "end sync product base info in Thread :{}", Thread.currentThread().getName());
                        } catch (Exception e) {
                            log.error("保存产品异常...");
                            e.printStackTrace();
                        } finally {
                            doneSignal.countDown();
                        }

                    });

        }
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Sync produc base info end !!!");
    }

    /**
     * 根据硬装主产品,更新其它产品基本信息 以及模型信息 若manProduct中的产品ID不存在,则必然为新增硬装主产品 若mainProduct的ID存在,则为同步副产品
     *
     * @param skuInfo
     * @param mainProduct
     * @param smallType
     */
    private void syncHardProductBaseInfoWithMainProduct(
            @NotNull ProductSKUInfo skuInfo, @NotNull Product mainProduct, Dictionary smallType) {

        // 设置模型
        Product product = setProductModelInfo(mainProduct, "", skuInfo, smallType);
        // 硬装产品规格取模型规格
        setHardProductSizeWithModelSize(skuInfo.getModelId(), product);
        product.setProductBatchType(PRODUCT_TYPE_PROTO_NORMAL);
        if (skuInfo.getProductId() != null) {
            // 更新产品
            product.setId(skuInfo.getProductId().longValue());
            product.setIsDeleted(0);
            productService.updateProduct(product);
        } else { // 新增产品
            product.setId(null);
            product.setProductCode("P_");
            // 生成spu
            BaseGoodsSpu spu = saveGoodsSpu(smallType.getValuekey(), product);
            product.setGoodsSpuId(spu.getId());
            product.setIsDeleted(0);
            productService.saveProduct(product);
            skuInfo.setProductId(product.getId().intValue());
            skuInfo.setProductCode(product.getProductCode());
        }
        // 同步模型归属状态
        if (skuInfo.getMainProductFlag() == null) {
            skuInfo.setMainProductFlag(false);
        }
        resModelService.saveResModel(
                ResModelUpdate.builder()
                        .modelId(product.getWindowsU3dmodelId().longValue())
                        .productId(product.getId().intValue())
                        .mainModelFlag(skuInfo.getMainProductFlag() ? 1 : 0)
                        .build());
        // 回写Product Template
        if (skuInfo.getMainProductFlag() != null
                && skuInfo.getMainProductFlag()
                && mainProduct.getId() == null) {
            // 硬装产品更新时,不会进到这里
            // 新增硬装产品时,处理主产品状态
            product.setProductBatchType(PRODUCT_TYPE_PROTO_MAIN);
            product.setProductBatchId(product.getId());
            productService.updateProduct(product);

            mainProduct.setId(product.getId());
            mainProduct.setProductBatchType(PRODUCT_TYPE_PROTO_MAIN);
            mainProduct.setProductBatchId(product.getId());
        }
    }

    /**
     * 处理硬装产品规格(取模型规格)
     *
     * @param modelId
     * @param product
     */
    private void setHardProductSizeWithModelSize(@NotNull Integer modelId, Product product) {
        ResModel detail = resModelService.getResModelDetail(modelId.longValue());
        product.setProductWidth(detail.getWidth() + "");
        product.setProductLength(detail.getLength() + "");
        product.setProductHeight(detail.getHeight() + "");
    }

    /**
     * 删除硬装产品.释放模型
     *
     * @param releaseModel true 为释放模型,false 反之
     * @param productIds
     */
    private void deleteHardProduct(Set<Integer> productIds, boolean releaseModel) {
        // 物理删除产品

        productService.hardDeleteProductByIds(productIds);
        // 物理删除平台记录表中的数据
        platform2bProductRelService.deleteByProductIds(new LinkedList<>(productIds));
        platform2cProductRelService.deleteByProductIds(new LinkedList<>(productIds));
        // 释放模型
        if (releaseModel) {
            resModelService.resetProductId(new ArrayList<>(productIds));
        }
    }

    /**
     * 处理保存图片是大小图
     *
     * @param picId
     * @param picIds
     * @return Map Key:pic & pics
     */
    private Map<String, String> fixWithPic(Integer picId, String picIds) {
        if (picId == null) {
            picId = 0;
        }
        if (StringUtils.isBlank(picIds)) {
            picIds = 0 + "";
        }
        Map<String, String> result = new HashMap<>(2);
        result.put(FIX_UPDATE_MAP_KEY_PIC, picId + "");
        result.put(FIX_UPDATE_MAP_KEY_PICS, picIds);
        ResPic picInfo = resPicService.getResPicDetail(picId.longValue());
        if (picInfo == null) {
            log.info(" 获取图片失败.... ");
            return result;
        }
        List<String> picIdsList = Splitter.on(Punctuation.COMMA).trimResults().splitToList(picIds);
        if (StringUtils.isNoneBlank(picInfo.getSmallPicInfo())) {
            // 存在小图
            Optional<String> ipad =
                    Splitter.on(";")
                            .trimResults()
                            .splitToList(picInfo.getSmallPicInfo())
                            .stream()
                            .filter(item -> item.startsWith("ipad"))
                            .findFirst();
            ipad.ifPresent(
                    ipadString -> {
                        String smallPicId = ipadString.split(Punctuation.COLON)[1];
                        result.put(FIX_UPDATE_MAP_KEY_PIC, smallPicId);
                        String picIdsPut =
                                picIdsList
                                        .stream()
                                        .filter(item -> !item.equals(smallPicId))
                                        .collect(Collectors.joining(Punctuation.COMMA));
                        result.put(FIX_UPDATE_MAP_KEY_PICS, picIdsPut);
                    });
        } else if (StringUtils.isNoneBlank(picIds)) {
            // 不存在小图,使用大图作为缩略图
            Integer finalPicId = picId;
            result.put(FIX_UPDATE_MAP_KEY_PIC, picId + "");
            List<String> strings =
                    picIdsList
                            .stream()
                            .filter(item -> !item.equals(finalPicId + ""))
                            .collect(Collectors.toList());
            result.put(FIX_UPDATE_MAP_KEY_PICS, Joiner.on(Punctuation.COMMA).join(strings));
            if (StringUtils.isBlank(result.get(FIX_UPDATE_MAP_KEY_PICS))) {
                result.put(FIX_UPDATE_MAP_KEY_PICS, picId + "");
            }
        }
        return result;
    }

    private Map<String, Dictionary> setProductTypeInfo(String smallType, Product product) {
        log.debug(smallType);
        Map<String, Dictionary> typeAndSmallType =
                dictionaryService.getProductTypeValueAndSmallBySmallValueKey(smallType);
        if (typeAndSmallType.isEmpty() || typeAndSmallType.get(Dictionary.DIC_TYPE_TYPE) == null) {
            typeAndSmallType = new HashMap<>(2);
            Dictionary defaultDictionary = new Dictionary();
            defaultDictionary.setValue(-1);
            typeAndSmallType.put(Dictionary.DIC_TYPE_TYPE, defaultDictionary);
            typeAndSmallType.put(Dictionary.DIC_TYPE_SMALL_TYPE, defaultDictionary);
        }
        product.setProductTypeValue(
                typeAndSmallType.get(Dictionary.DIC_TYPE_TYPE).getValue().toString());
        product.setProductTypeMark(typeAndSmallType.get(Dictionary.DIC_TYPE_TYPE).getValuekey());
        product.setProductSmallTypeValue(
                typeAndSmallType.get(Dictionary.DIC_TYPE_SMALL_TYPE).getValue());
        product.setProductSmallTypeMark(
                typeAndSmallType.get(Dictionary.DIC_TYPE_SMALL_TYPE).getValuekey());
        local.set(typeAndSmallType);
        return typeAndSmallType;
    }

    private void setPlatformProductRels(
            PlatformProductRel rel, List<PlatformProductRel> rels, List<Integer> platformIds) {
        for (Integer platformId : platformIds) {
            PlatformProductRel tmp = new PlatformProductRel();
            BeanUtils.copyProperties(rel, tmp);
            tmp.setPlatformId(platformId);
            rels.add(tmp);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductBO findProductInfoByProductId(long productId, String platformType) {
        // 查询产品
        Product product = productService.getProductInfoById(productId);
        if (product == null) {
            return null;
        }

        ProductBO ret = new ProductBO();
        // 设置自定义产品属性值(线上/渠道详情)

        setProductDiyInfo(productId, platformType, product, ret);
        // 设置产品详情回显数据
        setProductDiyViewInfo((int) productId, product, ret);
        if (product.getProductBatchId().equals(product.getId())) {
            // 设置硬装产品数据
            this.showHardProductModelInfo(product, ret);
        }
        return ret;
    }

    /**
     * 设置硬装产品 模型数据
     *
     * @param product 产品
     * @param ret     bo
     */
    private void showHardProductModelInfo(Product product, ProductBO ret) {
        // 获取产品-模型对应关系
        Map<Integer, Integer> productID2ModelId =
                productService.listSecondProductId2ModelIdWithMainId(product.getId());
        // 设置产品对应模型数据
        List<HardProductModelBO> modelInfos =
                productID2ModelId
                        .entrySet()
                        .stream()
                        .map(entry -> this.setHardProductModelInfo(product, entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList());
        // 排序模型
        modelInfos =
                modelInfos
                        .stream()
                        .filter(Objects::nonNull)
                        .peek(it -> it.setTextureCount(it.getModelTextureInfo().stream().mapToInt(area -> area.getRels().size()).sum()))
                        .sorted(
                                (m1, m2) -> {
//										m1.setTextureCount(
//												m1.getModelTextureInfo()
//														.stream()
//														.mapToInt(area -> area.getRels().size())
//														.sum());
//										m2.setTextureCount(
//												m2.getModelTextureInfo()
//														.stream()
//														.mapToInt(area -> area.getRels().size())
//														.sum());
                                    if (!Objects.equals(m1.getMainProductFlag(), m2.getMainProductFlag())) {
                                        // 主模型排序
                                        return m1.getMainProductFlag() ? -1 : 1;
                                    } else if (!m1.getLength().equals(m2.getLength())) {
                                        // 模型长度排序
                                        return m2.getLength() - m1.getLength();
                                    } else {
                                        // 模型高度排序
                                        return m2.getHeight() - m1.getHeight();
                                    }
                                })
                        .collect(Collectors.toList());
        ret.setHardProductModelInfo(modelInfos);
        ret.setModel(null);
        ret.setProps(null);
    }

    /**
     * 设置硬装产品模型信息
     *
     * @param product
     * @param productId
     * @param modelId
     * @return
     */
    private HardProductModelBO setHardProductModelInfo(
            @NotNull Product product, @NotNull Integer productId, @NotNull Integer modelId) {
        // 复制对象
        Product productTmp = Product.copyBean(product);
        productTmp.setId(productId.longValue());
        productTmp.setWindowsU3dmodelId(modelId);
        // 构造参数,携带数据
        ProductBO retTmp = new ProductBO();
        setModelTextureInfo(productTmp, retTmp);
        if (retTmp.getModel() == null) {
            return null;
        }
        // 回填数据
        HardProductModelBO modelBO = new HardProductModelBO();
        modelBO.setId(modelId.longValue());
        modelBO.setProductId(productId);
        modelBO.setModelName(retTmp.getModel().getModelName());
        modelBO.setThumbPicPath(retTmp.getModel().getThumbPicPath());
        modelBO.setModelCode(retTmp.getModel().getModelCode());
        modelBO.setHeight(retTmp.getModel().getHeight());
        modelBO.setWidth(retTmp.getModel().getWidth());
        modelBO.setLength(retTmp.getModel().getLength());
        modelBO.setModelTextureInfo(retTmp.getModelTextureInfo());
        if (modelBO.getModelTextureInfo() == null) {
            modelBO.setModelTextureInfo(Collections.emptyList());
        }
        modelBO.setMainProductFlag(false);
        if (modelBO.getProductId().equals(product.getId().intValue())) {
            modelBO.setMainProductFlag(true);
        }
        // 设置模型属性
        List<ProductPropBO> productPropBOS = setViewProductAttrInfo(modelBO.getProductId());
        modelBO.setProps(productPropBOS);
        return modelBO;
    }


    private void setProductDiyViewInfo(int productId, Product product, ProductBO ret) {
        // 风格
        if (StringUtils.isNoneBlank(product.getProductStyleIdInfo())) {
            ret.setProStyleId(new ArrayList<>());
            ret.setProStyleName(new ArrayList<>());
            StyleParseBO styleBo =
                    JsonParser.fromJson(product.getProductStyleIdInfo(), StyleParseBO.class);
            Optional.ofNullable(styleBo)
                    .map(StyleParseBO::getIsLeaf_0)
                    .map(Splitter.on(Punctuation.COMMA)::splitToList)
                    .orElse(Collections.singletonList("0"))
                    .stream()
                    .filter(StringUtils::isNotBlank)
                    .map(Integer::valueOf)
                    .forEach(
                            item -> {
                                BaseProductStyle style = baseProductStyleService.getBaseProductStyleById(item);
                                Optional.ofNullable(style)
                                        .ifPresent(
                                                styleTmp -> {
                                                    ret.getProStyleName().add(style.getName());
                                                    ret.getProStyleId().add(style.getId().intValue());
                                                });
                            });
        }
        // 查询产品款式
        if (product.getStyleId() != null) {
            ProductStyle productStyle = productStyleService.getProductStyleById(product.getStyleId());
            ret.setModelingId(product.getStyleId().intValue());
            if (productStyle != null) {
                ret.setModelingName(productStyle.getStyleName());
            }
        }
        // 设置产品售价单位
        if (product.getSalePriceValue() != null) {
            Dictionary priceUnion =
                    dictionaryService.getByTypeAndValue("productUnitPrice", product.getSalePriceValue());
            ret.setSaleUnitValue(product.getSalePriceValue());
            if (priceUnion != null) {
                ret.setSaleUnitName(priceUnion.getName());
            }
        }
        // 设置产品分类
        List<ProductCategoryRel> pcrels =
                productCategoryRelService.getProductCategoryRelByProductId(productId);
        List<CategoryTreeNode> categoryInfos =
                categoryService
                        .getCategoryByIds(
                                pcrels.stream().map(ProductCategoryRel::getCategoryId).collect(Collectors.toList()))
                        .stream()
                        .map(
                                category -> {
                                    CategoryTreeNode node = new CategoryTreeNode();
                                    node.setCode(category.getCode());
                                    node.setId(category.getId());
                                    node.setLevel(category.getLevel());
                                    node.setPid(category.getPid());
                                    node.setName(category.getName());
                                    return node;
                                })
                        .collect(Collectors.toList());
        categoryService
                .getCategoryByIds(
                        categoryInfos
                                .stream()
                                .map(CategoryTreeNode::getPid)
                                .distinct()
                                .collect(Collectors.toList()))
                .forEach(
                        pNode ->
                                categoryInfos
                                        .stream()
                                        .filter(item -> item.getPid().equals(pNode.getId().intValue()))
                                        .forEach(item -> item.setParentName(pNode.getName())));
        ret.setCategoryNodes(categoryInfos);
        //        if (pcrels != null && pcrels.size() > 0) {
        //            Category category =
        // categoryService.getCategoryInfoById(pcrels.get(0).getCategoryId());
        //            if (category != null) {
        //                ret.setCategoryCodeList(new ArrayList<>());
        //                ret.getCategoryCodeList().add(category.getSecondStageCode());
        //                ret.getCategoryCodeList().add(category.getThirdStageCode());
        //                ret.getCategoryCodeList().add(category.getFourthStageCode());
        //                List<Category> categorys =
        // categoryService.getCategorysByCodes(ret.getCategoryCodeList());
        //                StringBuilder sb = new StringBuilder();
        //                for (Category tmp : categorys) {
        //                    sb.append(tmp.getName()).append("/");
        //                }
        //                sb.substring(0, sb.length() - 2);
        //                ret.setCategoryCodeNames(sb.toString());
        //            }
        //        }
        // 设置产品系列名称
        BaseSeries series = baseSeriesService.getById(product.getSeriesId());
        ret.setSeriesId(product.getSeriesId());
        if (series != null) {
            ret.setSeriesName(series.getName());
        }
        // 设置品牌名称
        Brand brand = brandService.getBrandById(product.getBrandId());
        if (brand != null) {
            ret.setBrandName(brand.getBrandName());
        }
        // 设置产品型号
        ret.setModelNumber(product.getProductModelNumber());

        // 设置产品属性
        List<ProductPropBO> productPropBOS = setViewProductAttrInfo(productId);
        ret.setProps(productPropBOS);
        // 设置产品大小类信息
        ret.setAttrNames(
                dictionaryService.getProductTypeNameInfos(
                        product.getProductSmallTypeMark(), product.getProductTypeMark()));
        //        if (StringUtils.isNoneBlank(product.getProductSmallTypeMark())) {
        //            StringBuilder sb = new StringBuilder();
        //            dictionaryService.getByValueKeys(Arrays.asList(product.getProductSmallTypeMark(),
        // product.getProductTypeMark()))
        //                    .stream().sorted(Comparator.comparingLong(Dictionary::getId))
        //                    .forEach(item -> sb.append(item.getName()).append(Punctuation.SLASH));
        //            ret.setAttrNames(sb.substring(0, sb.length() - 1));
        //        }
        // 设置产品图片
        List<Integer> ids =
                Splitter.on(Punctuation.COMMA)
                        .splitToList(Optional.ofNullable(product.getPicIds()).orElse(""))
                        .stream()
                        .filter(id -> !"0".equals(id))
                        .filter(StringUtils::isNoneBlank)
                        .map(Integer::valueOf)
                        .collect(Collectors.toList());
        if (product.getPicId() != null) {
            List<ResPic> picInfos =
                    resPicService.getResPicDetailByIds(
                            ids.stream().map(Integer::longValue).collect(Collectors.toList()));
            picInfos
                    .stream()
                    .filter(item -> StringUtils.isNoneBlank(item.getSmallPicInfo()))
                    .filter(item -> item.getSmallPicInfo().contains("ipad:" + product.getPicId()))
                    .findFirst()
                    .ifPresent(first -> product.setPicId(first.getId().intValue()));
            if (!ids.contains(product.getPicId())) {
                ids.add(product.getPicId());
            }
            ret.setPicId(product.getPicId());
        }
        Map<Integer, String> pics = resPicService.idAndPathMap(ids);
        ret.setPicPath(pics.get(product.getPicId()));
        ret.setPicInfos(new LinkedList<>());
        for (Integer picId : ids) {
            if (Objects.nonNull(pics.get(picId))) {
                PicInfo pic = new PicInfo();
                pic.setId(picId);
                pic.setPath(pics.get(picId));
                ret.getPicInfos().add(pic);
            }
        }
        // 设置模型产品贴图信息
        /** -------------------------从model_area_texture_rel中取模型贴图详情---------------------------* */
        setModelTextureInfo(product, ret);
    }

    /**
     * 根据产品ID设置产品属性View
     *
     * @param productId
     * @return
     */
    private List<ProductPropBO> setViewProductAttrInfo(int productId) {
        List<ProductPropBO> propBos = new ArrayList<>();
        List<ProductAttribute> attrs =
                productAttributeService.getProductAttributeByProductId(productId);
        if (!attrs.isEmpty()) {
            for (ProductAttribute attr : attrs) {
                ProductPropBO prop = new ProductPropBO();
                // 设置伍级节点
                prop.setCode(attr.getAttributeValueKey());
                prop.setId(attr.getAttributeValueId());
                prop.setName(attr.getAttributeValueName());
                // 设置四级节点
                prop.setParent(new ProductPropBO());
                prop.getParent().setName(attr.getAttributeName());
                prop.getParent().setId(attr.getAttributeId());
                prop.getParent().setCode(attr.getAttributeKey());
                propBos.add(prop);
            }
        }
        return propBos;
    }

    private void setModelTextureInfo(Product product, ProductBO ret) {
        if (product.getProductSmallTypeMark() != null) {
            Dictionary type;
            // 处理大小类数据异常问题
            if (product.getProductSmallTypeMark().equals(product.getProductTypeMark())) {
                type =
                        dictionaryService.getByTypeAndValue(
                                product.getProductTypeMark(), product.getProductSmallTypeValue());
                if (type != null) {
                    product.setProductSmallTypeMark(type.getValuekey());
                    ret.setSmallType(type.getValuekey());
                }
            } else {
                type = dictionaryService.getByValueKey(product.getProductSmallTypeMark());
            }
            checkModelOrTexture(type);
            if (Objects.isNull(type) || Strings.isNullOrEmpty(type.getAtt1Info())) {
                return;
            }
            if (Category.CATEGORY_TYPE_MODEL.equals(Integer.valueOf(type.getAtt1Info()))) {
                ret.setCheckIsModel(true);
                // 模型产品
                if (product.getWindowsU3dmodelId() != null) {
                    ResModel resModel =
                            resModelService.getResModelDetail(product.getWindowsU3dmodelId().longValue());
                    ret.setModel(resModel);
                    // 软装产品移除产品详情页面,模型贴图区域详情
                    if (!Objects.equals(product.getProductBatchType(), PRODUCT_TYPE_SINGLE)) {
                        List<ModelAreaBO> modelAreaBOS =
                                resModelBizService.showModelTextureInfo(product.getWindowsU3dmodelId().longValue());
                        ret.setModelTextureInfo(modelAreaBOS);
                    }
                }
            } else if (Category.CATEGORY_TYPE_TEXTURE.equals(Integer.valueOf(type.getAtt1Info()))) {
                ret.setCheckIsModel(false);
                // 贴图产品
                if (StringUtils.isNoneBlank(product.getMaterialPicIds())) {
                    String id = product.getMaterialPicIds().split(Punctuation.COMMA)[0];
                    ResTexture detail = resTextureService.getResTextureDetail(Long.parseLong(id));
                    ProductTextureVO productTextureVO = new ProductTextureVO();
                    if (detail != null) {
                        productTextureVO.setId(detail.getId().intValue());
                        productTextureVO.setName(detail.getName());
                        productTextureVO.setCode(detail.getFileCode());
                        productTextureVO.setPicPath(detail.getFilePath());
                        productTextureVO.setModelNumber(detail.getModelNumber());
                        productTextureVO.setTextureCode(detail.getFileCode());
                    }
                    ret.setTexture(productTextureVO);
                }
            }
        }
    }

    private void setProductDiyInfo(long productId, String platformType, Product product, ProductBO ret) {
        BeanUtils.copyProperties(product, ret);
        ret.setSpec(product.getProductSpec());
        ret.setName(product.getProductName());
        ret.setCode(product.getProductCode());
        ret.setHeight(product.getProductHeight());
        ret.setWidth(product.getProductWidth());
        ret.setLength(product.getProductLength());
        ret.setSmallType(product.getProductSmallTypeMark());
        ret.setMaterialPicIds(product.getMaterialPicIds());
        ret.setIsComplexParquet(product.getIsComplexParquet());
        PlatformProductRel rel = null;
        PlatformProductRel relb;
        PlatformProductRel relc;
        Integer platformId = platform2bProductRelService.getPlatformIdByProductId(productId);
        relb = platform2bProductRelService.getByProductIdAndPlatform(platformId, (int) productId);
        platformId = platform2cProductRelService.getPlatformIdByProductId(productId);
        relc = platform2cProductRelService.getByProductIdAndPlatform(platformId, (int) productId);
        if (relb != null && relb.getAllotState() != null) {
            ret.setChannelAllotStatus(relb.getAllotState());
        }
        if (relc != null && relc.getAllotState() != null) {
            ret.setOnlineAllotStatus(relc.getAllotState());
        }
        if (!platformType.equals(Platform.PLATFORM_LIBRARY)) {
            if (platformType.equals(Platform.PLATFORM_CHANNEL)) {
                rel = relb;
            } else if (platformType.equals(Platform.PLATFORM_ONLINE)) {
                rel = relc;
            }
            if (rel != null) {
                if (StringUtils.isNoneBlank(rel.getStyleId())) {
                    product.setProductStyleIdInfo(JsonParser.toJson(StyleParseBO.builder().isLeaf_0(rel.getStyleId()).isLeaf_1("")
                            .build()));
                } else {
                    product.setProductStyleIdInfo(null);
                }
                ret.setDesc(rel.getDescription());
                if (rel.getAdvicePrice() != null) {
                    ret.setAdvicePrice(rel.getAdvicePrice());
                } else {
                    ret.setAdvicePrice(null);
                }
                if (rel.getSalePrice() != null) {
                    ret.setPrice(rel.getSalePrice());
                } else {
                    ret.setPrice(null);
                }
                if (rel.getPicId() != null) {
                    product.setPicId(rel.getPicId());
                } else {
                    product.setPicId(null);
                }
                if (rel.getPicIds() != null) {
                    product.setPicIds(rel.getPicIds());
                } else {
                    product.setPicIds(null);
                }
            }
        } else {
            ret.setPrice(product.getSalePrice());
            ret.setAdvicePrice(product.getAdvicePrice());
            ret.setDesc(product.getProductDesc());
        }
    }

    private List<Integer> stringToIntegerList(String picIds) {

        List<Integer> list = new ArrayList<>();
        if (StringUtils.isBlank(picIds)) {
            return list;
        }
        for (String s : picIds.split(Punctuation.COMMA)) {
            list.add(Integer.parseInt(s));
        }
        list.remove(Integer.valueOf(0));
        return list;
    }

    @Override
    public boolean allotProduct(
            List<Integer> productIds, List<String> platformTypes, String allotMethod) {
        // 过滤出已发布状态的产品(产品表中putawayState = 3)
        List<Integer> canBeAllotIds = productService.getBePutAwayProduct(productIds);
        log.info(
                "发布产品:productIds:"
                        + productIds
                        + ",platformTypes:"
                        + platformTypes
                        + ",method:"
                        + allotMethod);
        List<Integer> platformIds = platformService.getPlatformIdsByBussinessTypes(platformTypes);
        Map<String, List<Integer>> map = platformService.groupPlatformWithBussinessType(platformIds);
        int ret = -1;
        if (!map.get(Platform.BUSINESS_TYPE_2B).isEmpty()) {
            if (Platform.DO_ALLOT.equals(allotMethod)) {
                ret =
                        platform2bProductRelService.issueProductsToPlatformIds(
                                map.get(Platform.BUSINESS_TYPE_2B), canBeAllotIds);
            } else if (Platform.CANCEL_ALLOT.equals(allotMethod)) {
                platform2bProductRelService.cancelIssueProducts(productIds);
            }
        }
        if (!map.get(Platform.BUSINESS_TYPE_2C).isEmpty()) {
            if (Platform.DO_ALLOT.equals(allotMethod)) {
                ret =
                        platform2cProductRelService.issueProductsToPlatformIds(
                                map.get(Platform.BUSINESS_TYPE_2C), canBeAllotIds);
            } else if (Platform.CANCEL_ALLOT.equals(allotMethod)) {
                platform2cProductRelService.cancelIssueProducts(productIds);
            }
        }
        if (!canBeAllotIds.isEmpty()) {
            productDao.updateProductInitStatus(canBeAllotIds);
        }
        return ret > 0;
    }

    /**
     * 内容库产品上下架
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Object> upAndDownProduct(
            List<Integer> ids,
            List<String> tocPlatFormIds,
            List<String> tobPlatFormIds,
            Integer operateType) {
        log.info(
                "产品上架upAndDownProduct:ids:{},tocPlatFormIds:{},tobPlatFormIds:{},operateType:{}",
                ids,
                tocPlatFormIds,
                tobPlatFormIds,
                operateType);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 先判断是否是硬装产品
        String allProductId = productService.getSoftHardProduct(ids);
        if (StringUtils.isNoneBlank(allProductId)) {
            ids.addAll(
                    Arrays.asList(allProductId.split(","))
                            .stream()
                            .map(s -> Integer.parseInt(s))
                            .collect(Collectors.toList()));
        }
        log.info("产品上架软硬装id集合:ids:{}", ids);
        List<Integer> canBeAllotIds = productService.getBePutAwayProduct(ids);
        try {
            if (tocPlatFormIds.isEmpty() && tobPlatFormIds.isEmpty()) {
                resultMap.put("success", "");
                return resultMap;
            }
            // 产品上架
            if (Platform.PUT_STATUS_UP == operateType) {
                List<PlatformProductRel> tocList = new ArrayList<>();
                List<PlatformProductRel> toBList = new ArrayList<>();
                if (tocPlatFormIds != null && tocPlatFormIds.size() > 0) {
                    canBeAllotIds.forEach(
                            productId -> {
                                tocPlatFormIds.forEach(
                                        platformId -> {
                                            setProductPlatformRel(tocList, productId, Integer.parseInt(platformId));
                                        });
                            });
                }
                if (tobPlatFormIds != null && tobPlatFormIds.size() > 0) {
                    canBeAllotIds.forEach(
                            productId -> {
                                tobPlatFormIds.forEach(
                                        platformId -> {
                                            setProductPlatformRel(toBList, productId, Integer.parseInt(platformId));
                                        });
                            });
                }
                if (!tocList.isEmpty()) {
                    platform2cProductRelService.insertRelListIfNotExist(tocList);
                }
                if (!toBList.isEmpty()) {
                    platform2bProductRelService.insertRelListIfNotExist(toBList);
                }
                // 产品下架
            } else {
                if (tocPlatFormIds != null && tocPlatFormIds.size() > 0) {
                    platform2cProductRelService.deleteByIdsAndPlatForm(canBeAllotIds, tocPlatFormIds);
                }
                if (tobPlatFormIds != null && tobPlatFormIds.size() > 0) {
                    platform2bProductRelService.deleteByIdsAndPlatForm(canBeAllotIds, tobPlatFormIds);
                }
            }
            // 取差集,判断是否存在不能上架的商品
            ids.removeAll(canBeAllotIds);
            resultMap.put("success", ids);
        } catch (Exception e) {
            log.error("upAndDownProduct上架失败,产品id{},失败信息{}", ids, e);
            resultMap.put("false", e);
        }
        return resultMap;
    }

    @Override
    public boolean putawayProduct(
            List<Integer> productIds, List<String> platformType, String platformIds, Integer putStatus) {
        if (platformType.isEmpty() || productIds.isEmpty() || platformType.size() != 1) {
            return false;
        }
        if (Platform.BUSINESS_TYPE_2B.equals(platformType.get(0).trim()) && putStatus == null) {
            return false;
        }
        List<PlatformProductRel> rels = new ArrayList<>();
        if (Platform.BUSINESS_TYPE_2B.equals(platformType.get(0))) {
            // 2b上/下架
            List<Integer> ids = platformService.getPlatformIdsByBussinessTypes(platformType);
            for (Integer paltformId : ids) {
                for (Integer productId : productIds) {
                    rels.add(setPlatformProductRelUpdate(paltformId, productId, putStatus));
                }
            }
            platform2bProductRelService.updatePlatformProductRels(rels);
        }
        if (Platform.BUSINESS_TYPE_2C.equals(platformType.get(0))) {
            // 2c上架平台ID
            List<PlatformProductRel> insertRels = new ArrayList<>();
            Map<Integer, String> bePutaway =
                    platform2cProductRelService.getBePutawayPlatformByProductIds(productIds);
            List<Integer> platformIdsPutUp = new ArrayList<>();
            if (StringUtils.isNoneBlank(platformIds)) {
                platformIdsPutUp = stringToIntegerList(platformIds.trim());
            }
            // 2c下架平台ID
            String[] type = {Platform.BUSINESS_TYPE_2C};
            List<Integer> platformIdsPutDown =
                    platformService.getPlatformIdsByBussinessTypes(Arrays.asList(type));
            platformIdsPutDown.removeAll(platformIdsPutUp);
            for (Integer productId : productIds) {
                Set<Integer> bePutPlatformId =
                        Splitter.on(Punctuation.COMMA)
                                .trimResults()
                                .splitToList(bePutaway.get(productId))
                                .stream()
                                .map(Integer::valueOf)
                                .collect(Collectors.toSet());
                for (Integer platformId : platformIdsPutUp) {
                    if (bePutPlatformId.contains(platformId)) {
                        rels.add(setPlatformProductRelUpdate(platformId, productId, 1));
                    } else {
                        setProductPlatformRel(insertRels, productId, platformId);
                    }
                }
                // 关键一步,不要漏了
                rels.addAll(insertRels);
                // 下架
                for (Integer platformId : platformIdsPutDown) {
                    rels.add(setPlatformProductRelUpdate(platformId, productId, 0));
                }
            }
            platform2cProductRelService.updatePlatformProductRels(rels);
        }
        return true;
    }

    @Override
    public PageInfo<ProductListBO> queryProductByParam(ProductQueryPO po) {
        if (StringUtils.isBlank(po.getQueryType())) {
            return null;
        }
        // 设置默认排序
        po.setOrderField("id");
        po.setOrderMethod("desc");
        return productListDoQuery(po);
    }

    @Override
    public PageInfo<SolutionProductListBO> querySolutionProduct(ProductQueryPO po) {
        if (StringUtils.isBlank(po.getQueryType())) {
            if (po.getProductIds() == null || po.getProductIds().isEmpty()) {
                return new PageInfo<>(Collections.emptyList());
            }
        }
        // 设置查询条件
        //        List<Integer> categoryIds =
        // categoryBizService.queryCategoryIdsByCategoryCode(po.getCategoryCode());
        //        po.setCategoryIds(categoryIds);

        List<SolutionProductListBO> list = new ArrayList<>();
        PageInfo<ProductListBO> productListBOS = productService.querySolutionProducts(po);
        Map<Integer, String> putStatus2c = productService.mapProductId2PutStatus(po.getProductIds());
        setProductDiyViewInfo(productListBOS.getList());
        for (ProductListBO bo : productListBOS.getList()) {
            SolutionProductListBO sbo = new SolutionProductListBO();
            if (bo.getSecrecy() == null) {
                bo.setSecrecy(0);
            }
            BeanUtils.copyProperties(bo, sbo);
            // 设置产品线上上架状态
            sbo.setStatus2c(0);
            if (StringUtils.isNoneBlank(putStatus2c.get(bo.getId()))
                    && putStatus2c.get(bo.getId()).contains("1")) {
                sbo.setStatus2c(1);
            }
            // 设置产品渠道上架状态
            sbo.setStatus2b(bo.getPutAwayStatus2b());
            list.add(sbo);
        }
        PageInfo<SolutionProductListBO> ret = new PageInfo<>(list);
        ret.setTotal(productListBOS.getTotal());
        return ret;
    }

    private PageInfo<ProductListBO> productListDoQuery(ProductQueryPO po) {
        // 设置查询条件
        List<Integer> categoryIds =
                categoryBizService.queryCategoryIdsByCategoryCode(po.getCategoryCode());
        po.setCategoryIds(categoryIds);
        PageInfo<ProductListBO> list = new PageInfo<>();
        // 获取2b/2c下的任意一个平台ID
        Integer id2b = platformService.getOnePlatformIdByBussinessType(Platform.BUSINESS_TYPE_2B);
        Integer id2c = platformService.getOnePlatformIdByBussinessType(Platform.BUSINESS_TYPE_2C);
        // 设置公司信息(过滤查询)
        if (po.getCompanyId() != null) {
            List<Brand> brands = brandService.getBrandByCompanyId(po.getCompanyId());
            if (brands.isEmpty()) {
                list.setList(new ArrayList<>());
                return list;
            }
            //            po.setBrandIds(brands.stream().map(item ->
            // item.getId().intValue()).collect(Collectors.toList()));
            po.setBrandIds(Collections.emptyList());
        }
        // 内容库查询
        if (po.getQueryType().equals(Platform.PLATFORM_LIBRARY) || po.getProductIds() != null) {
            po.setPlatform2bId(id2b);
            po.setPlatform2cId(id2c);
            list = productService.queryProducts(po);
            List<Integer> ids =
                    list.getList().stream().map(product -> product.getId()).collect(Collectors.toList());
            Map<Integer, String> upPlatfomrMap = new HashMap<Integer, String>();
            if (ids != null && ids.size() > 0) {
                upPlatfomrMap = productService.queryUpPlatForm(ids);
            }
            //            Map<Integer, String> platformMap = platformService.getAllPlatformIdAndName();
            Map<Integer, String> platformMap = platformService.getUpDownPlatformIdAndName();
            for (ProductListBO bo : list.getList()) {
                //                List<String> platform2bIds = new
                // ArrayList(Arrays.asList(bo.getPlatformIds().split(Punctuation.COMMA)));
                //                List<String> platform2cIds = new
                // ArrayList(Arrays.asList(bo.getPlatform2cIds().split(Punctuation.COMMA)));
                List<String> platformAllIds = new ArrayList<String>();
                //                //获取已上架平台名称
                //                if (platform2bIds != null && platform2bIds.size() > 0) {
                //                    platformAllIds.addAll(platform2bIds);
                //                }
                //                if (platform2cIds != null && platform2cIds.size() > 0) {
                //                    platformAllIds.addAll(platform2cIds);
                //                }
                if (upPlatfomrMap != null && upPlatfomrMap.get(bo.getId()) != null) {
                    String platformIds = upPlatfomrMap.get(bo.getId()).toString();
                    platformAllIds.addAll(new ArrayList(Arrays.asList(platformIds.split(Punctuation.COMMA))));
                }
                List<Integer> platFormIds = new ArrayList<Integer>();
                if (!platformAllIds.isEmpty()) {
                    platFormIds.addAll(
                            platformAllIds
                                    .stream()
                                    .filter(StringUtils::isNoneBlank)
                                    .map(Integer::parseInt)
                                    .collect(Collectors.toList()));
                }
                // 获取已上架平台名称
                bo.setPutAwayPlatformIds(new ArrayList<>());
                bo.setPutAwayPlatformNames(new ArrayList<>());
                platFormIds.forEach(
                        platformId -> {
                            platformMap.forEach(
                                    (key, value) -> {
                                        if (platformId == key) {
                                            bo.getPutAwayPlatformIds().add(platformId);
                                            bo.getPutAwayPlatformNames()
                                                    .add(platformMap.get(Integer.parseInt(key.toString())));
                                        }
                                    });
                        });
            }
        } else if (Platform.PLATFORM_CHANNEL.equals(po.getQueryType())) {
            // 渠道查询
            po.setPlatform2bId(id2b);
            list = productService.query2bProducts(po);
        } else if (Platform.PLATFORM_ONLINE.equals(po.getQueryType())) {
            // 线上查询
            // 获取所有平台的ID和名称<id,name>
            Map<Integer, String> platformMap = platformService.getAllPlatformIdAndName();
            po.setPlatform2cId(id2c);
            list = productService.query2cProducts(po);
            for (ProductListBO bo : list.getList()) {
                List<String> putStatus = Arrays.asList(bo.getPutStatus().split(Punctuation.COMMA));
                List<String> putPlatformIds = Arrays.asList(bo.getPlatformIds().split(Punctuation.COMMA));
                Map<String, String> map = getPlatformPutStatusMap(putPlatformIds, putStatus);
                // 获取已上架平台名称
                bo.setPutAwayPlatformIds(new ArrayList<>());
                bo.setPutAwayPlatformNames(new ArrayList<>());
                for (Map.Entry entry : map.entrySet()) {
                    if ("1".equals(entry.getValue())) {
                        bo.getPutAwayPlatformIds().add(Integer.parseInt(entry.getKey().toString()));
                        bo.getPutAwayPlatformNames()
                                .add(platformMap.get(Integer.parseInt(entry.getKey().toString())));
                    }
                }
            }
        }
        setProductDiyViewInfo(list.getList());
        return list;
    }

    private void setProductDiyViewInfo(List<ProductListBO> list) {
        // 设置产品属性
        List<Integer> brandIds = new ArrayList<>();
        List<Integer> modelIds = new ArrayList<>();
        List<Integer> textureIds = new ArrayList<>();
        List<Integer> categoryIdsret = new ArrayList<>();
        List<Integer> picIds = new ArrayList<>();
        for (ProductListBO tmp : list) {
            // 渠道列表缩略图
            if (tmp.getPic2bId() != null) {
                tmp.setPicId(tmp.getPic2bId());
            }
            // 线上列表缩略图
            if (tmp.getPic2cId() != null) {
                tmp.setPicId(tmp.getPic2cId());
            }
            if (null == tmp.getSecrecy()) {
                tmp.setSecrecy(0);
            }
            if (tmp.getBrandId() != null) {
                brandIds.add(tmp.getBrandId());
            }
            if (tmp.getModelId() != null) {
                modelIds.add(tmp.getModelId());
            }
            if (StringUtils.isNoneBlank(tmp.getTextureId()) && tmp.getTextureId() != null) {
                if (tmp.getTextureId().contains(",")) {
                    String tid = tmp.getTextureId().substring(0, tmp.getTextureId().indexOf(","));
                    textureIds.add(Integer.parseInt(tid));
                    tmp.setTextureId(tid);
                } else {
                    textureIds.add(Integer.parseInt(tmp.getTextureId()));
                }
            }
            if (tmp.getCategoryId() != null) {
                if (!categoryIdsret.contains(tmp.getCategoryId())) {
                    categoryIdsret.add(tmp.getCategoryId());
                }
            }
            if (tmp.getPicId() != null) {
                picIds.add(tmp.getPicId());
            }
        }
        Map<Integer, String> brandMap = brandService.getBrandNameByIds(brandIds);
        Map<Integer, CompanyBrandBo> companyMap = companyBizService.getCompanyByBrandIds(brandIds);
        Map<Integer, ResModel> modelMap = resModelService.getModelByIds(modelIds);
        Map<Integer, String> textureMap = resTextureService.getTextureCodeByIds(textureIds);
        //        Map<Integer, String> categoryMap =
        // categoryService.getCategoryNameAndParentNameByIds(categoryIdsret);
        Map<Integer, String> picMap = resPicService.idAndPathMap(picIds);
        //        Map<String, List<ProductProp>> propMap =
        // productPropService.getAllParentByCodes(list.stream().map(ProductListBO::getSmallType).distinct().collect(Collectors.toList()));
        for (ProductListBO tmp : list) {
            // 设置产品的品牌名称
            if (brandMap != null && tmp.getBrandId() != -1) {
                tmp.setBrandName(brandMap.get(tmp.getBrandId()));
            }
            // 设置产品的公司名称
            if (companyMap != null && tmp.getBrandId() != -1) {
                if (companyMap.get(tmp.getBrandId()) != null) {
                    tmp.setCompanyName(companyMap.get(tmp.getBrandId()).getCompanyName());
                }
            }
            // 设置产品的分类名称(运营属性)
            tmp.setCategoryNames(
                    dictionaryService.getProductTypeNameInfos(tmp.getSmallType(), tmp.getType()));
            //            if (categoryMap != null && tmp.getCategoryId() != null) {
            //                String s = categoryMap.get(tmp.getCategoryId());
            //                tmp.setCategoryNames(s);
            //            }
            // 设置产品的分类名称(基本属性)
            //            if (!propMap.isEmpty() && tmp.getSmallType() != null) {
            //                List<String> names =
            // propMap.get(tmp.getSmallType()).stream().map(ProductProp::getName).collect(Collectors.toList());
            //                if (names != null) {
            //
            // tmp.setCategoryNames(Joiner.on(Punctuation.SLASH).skipNulls().join(names));
            //                }
            //            }

            // 设置产品的模型编码
            if (tmp.getModelId() != null && modelMap.get(tmp.getModelId()) != null) {
                // 模型编码
                tmp.setModelCode(modelMap.get(tmp.getModelId()).getModelCode());
            }
            // 贴图编码
            if (StringUtils.isBlank(tmp.getModelCode()) && StringUtils.isNoneBlank(tmp.getTextureId())) {
                tmp.setModelCode(textureMap.get(Integer.parseInt(tmp.getTextureId())));
            }
            // 设置产品的缩略图
            if (picMap != null && tmp.getPicId() != null) {
                tmp.setPicPath(picMap.get(tmp.getPicId()));
            }
            if (tmp.getStatus2b() == null) {
                tmp.setStatus2b(0);
            }
            if (tmp.getStatus2c() == null) {
                tmp.setStatus2c(0);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Integer> deleteProductsByIds(List<Integer> ids, String platformType) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        List<Integer> result = new LinkedList<>();
        if (Platform.BUSINESS_TYPE_2B.equals(platformType)) {
            platform2bProductRelService.deleteByProductIds(ids);
        } else if (Platform.BUSINESS_TYPE_2C.equals(platformType)) {
            platform2cProductRelService.deleteByProductIds(ids);
        } else if (Platform.PLATFORM_LIBRARY.equals(platformType)) {
            // 软硬装一起删除
            String hardProductIds = productService.getHardProductIds(ids);
            List<Integer> hardProductIdsList =
                    Arrays.stream(Strings.nullToEmpty(hardProductIds).split(","))
                            .filter(StringUtils::isNoneBlank)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

            result.addAll(ids);
            result.addAll(hardProductIdsList);
            // 逻辑删除模型
            resModelService.deleteResModelsByProductIds(
                    result.stream().distinct().map(Integer::longValue).collect(Collectors.toList()));
            // 删除产品
            productService.deleteBussinessyProduct(result);
            //            platform2cProductRelService.deleteByProductIds(ids);
            //            platform2bProductRelService.deleteByProductIds(ids);
            // 删除硬装 物理删除产品以及平台记录
            //            this.deleteHardProduct(new HashSet<>(hardProductIdsList), false);
            log.info("delete product list, soft product ids:{}", ids);
            log.info("delete product list, hard product ids:{}", hardProductIdsList);
        }
        return result;
    }

    @Override
    public PageInfo<EditorProductListBO> queryInEditorProductList(EditorProductQuery query) {
        if (StringUtils.isNoneBlank(query.getCategoryCode())) {
            List<Integer> categoryIds =
                    categoryBizService.queryCategoryIdsByCategoryCode(query.getCategoryCode());
            query.setCategoryIds(categoryIds);
        }
        List<Brand> brandByCompanyId = brandService.getBrandByCompanyId(query.getCompanyId());
        List<Integer> brandIds =
                brandByCompanyId
                        .stream()
                        .map(item -> Integer.parseInt(item.getId().toString()))
                        .collect(Collectors.toList());
        if (brandIds.isEmpty()) {
            PageInfo<EditorProductListBO> pageInfo = new PageInfo<>();
            pageInfo.setList(Collections.emptyList());
            return pageInfo;
        }
        //        query.setBrandIds(brandIds);
        productService.queryProductsInEditor(query);
        PageInfo<EditorProductListBO> rets = productService.queryProductsInEditor(query);
        List<Integer> picIds =
                rets.getList()
                        .stream()
                        .map(EditorProductListBO::getPicId)
                        .filter(Objects::nonNull)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
        Map<Integer, String> picInfo = resPicService.idAndPathMap(picIds);
        rets.getList()
                .stream()
                .filter(item -> StringUtils.isNoneBlank(item.getPicId()))
                .forEach(item -> item.setPicPath(picInfo.get(Integer.parseInt(item.getPicId()))));
        return rets;
    }

    @Override
    public void initProductStatus() {
        List<Integer> ids = productService.getNotBeInitProductIdsAndInitProductStatus();
        log("init product ids {}", ids);
        log("init product count:", ids.size());
        if (ids.isEmpty()) {
            return;
        }
        doSynPlatformProductRels(ids);
    }

    private void doSynPlatformProductRels(List<Integer> ids) {
        if (ids.isEmpty()) {
            return;
        }
        log.info("自动上架产品ids:{}", ids);
        Platform param = new Platform();
        param.setNuma1(1);
        param.setIsDeleted(0);
        List<Platform> platforms = platformService.getList(param);

        List<PlatformProductRel> listRel2c = new ArrayList<>();
        List<PlatformProductRel> listRel2b = new ArrayList<>();
        List<Integer> notExist2b = platform2bProductRelService.getNotExistIds(ids);
        List<Integer> notExist2c = platform2cProductRelService.getNotExistIds(ids);

        notExist2b.forEach(
                productId ->
                        platforms
                                .stream()
                                .filter(item -> item.getPlatformBussinessType().equals(Platform.BUSINESS_TYPE_2B))
                                .map(Platform::getId)
                                .forEach(
                                        platformId ->
                                                setProductPlatformRel(listRel2b, productId, platformId.intValue())));

        notExist2c.forEach(
                productId ->
                        platforms
                                .stream()
                                .filter(item -> item.getPlatformBussinessType().equals(Platform.BUSINESS_TYPE_2C))
                                .map(Platform::getId)
                                .forEach(
                                        platformId ->
                                                setProductPlatformRel(listRel2c, productId, platformId.intValue())));

        platform2bProductRelService.insertRelList(listRel2b);
        platform2cProductRelService.insertRelList(listRel2c);
    }

    @Override
    public void synProductToPlatform() {
        List<Integer> ids = productService.getAutoSynProductIdsAndInitProductStatus();
        doSynPlatformProductRels(ids);
        if (!ids.isEmpty()) {
            doSyncToIndex(SyncMessage.ACTION_UPDATE, ids);
        }
    }

    @Override
    @Deprecated
    public void copyProduct(List<Integer> companyIds, List<Integer> productIds) {
        List<Product> ps = productService.getProductByIds(productIds);
        List<Product> insertList = new ArrayList<>();
        ps.forEach(
                product -> {
                    companyIds.forEach(
                            item -> {
                                Product temp = new Product();
                                BeanUtils.copyProperties(product, temp);
                                temp.setCompanyId(item);
                                temp.setGmtCreate(new Date());
                                temp.setGmtModified(new Date());
                                temp.setOriginId(product.getId().intValue());
                                temp.setId(null);
                                temp.setProductCode(
                                        "P" + ((Long) System.currentTimeMillis()).toString().substring(3));
                                insertList.add(temp);
                            });
                });
        //        productService.insertList(insertList);
    }

    @Override
    public List<CompanyWithDeliveredBO> getProductDeliveredInfo(Integer productId) {
        List<Integer> companyIds = productService.getProductDeliveredInfo(productId);
        return companyService
                .findAllCompanyNameAndId()
                .stream()
                .map(
                        item -> {
                            CompanyWithDeliveredBO bo = new CompanyWithDeliveredBO();
                            bo.setCompanyId(item.getId().intValue());
                            bo.setCompanyName(item.getCompanyName());
                            bo.setDelivered(0);
                            if (companyIds.contains(item.getId().intValue())) {
                                bo.setDelivered(1);
                            }
                            return bo;
                        })
                .sorted(Comparator.comparingInt(CompanyWithDeliveredBO::getDelivered).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getProductNumberWordRecommends(
            String keyWord, Integer brandId, Integer companyId) {
        if (StringUtils.isBlank(keyWord) && Objects.isNull(brandId) && Objects.isNull(companyId)) {
            return Collections.emptyList();
        }
        List<String> words = productService.getProductNumberWordRecommends(keyWord, brandId, companyId);
        return words
                .stream()
                .filter(item -> !Strings.isNullOrEmpty(item))
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }

    @Override
    public void synProductCompanyInfoWithBrandId() {
        productService.synProductCompanyInfoWithBrandId();
    }

    @Override
    public void synProductPutawayState() {
        productService.synProductPutawayState();
    }

    /**
     * 同步索引库
     *
     * @param messageAction 同步类型
     * @param ids           需要同步的产品ID
     */
    private void doSyncToIndex(Integer messageAction, List<Integer> ids) {
        List<Map> content =
                ids.stream()
                        .map(
                                item -> {
                                    HashMap<String, Integer> tmp = new HashMap<>(1);
                                    tmp.put("id", item);
                                    return tmp;
                                })
                        .collect(Collectors.toList());
        SyncMessage message = new SyncMessage();
        message.setAction(messageAction);
        message.setMessageId("P-" + System.currentTimeMillis());
        message.setModule(SyncMessage.MODULE_PRODUCT);
        message.setPlatformType(Constants.PLATFORM_CODE_MERCHANT_MANAGE);
        message.setObject(content);
        queueService.send(message);
    }

    private void setProductPlatformRel(
            List<PlatformProductRel> listRel2b, Integer productId, Integer platformId) {
        PlatformProductRel tmp = new PlatformProductRel();
        Product product = productService.getProductInfoById(productId);
        if (product != null) {
            if (product.getAdvicePrice() != null) {
                tmp.setAdvicePrice(product.getAdvicePrice());
            }
            if (product.getSalePrice() != null) {
                tmp.setSalePrice(product.getSalePrice());
            }
            tmp.setDescription(product.getProductDesc());
            if (StringUtils.isNoneBlank(product.getProductStyleIdInfo())) {
                StyleParseBO styleBo =
                        JsonParser.fromJson(product.getProductStyleIdInfo(), StyleParseBO.class);
                tmp.setStyleId(styleBo == null ? "" : styleBo.getIsLeaf_0());
            }
            tmp.setPicId(product.getPicId());
            tmp.setPicIds(product.getPicIds());
            tmp.setAllotState(1);
            tmp.setPutawayState(1);
            tmp.setProductId(productId);
            tmp.setPlatformId(platformId);
            tmp.setGmtCreate(new Date());
            tmp.setGmtModified(new Date());
        }
        listRel2b.add(tmp);
    }

    private PlatformProductRel setPlatformProductRelUpdate(
            Integer platformId, Integer productId, Integer putStatus) {
        PlatformProductRel tmp = new PlatformProductRel();
        tmp.setProductId(productId);
        tmp.setPlatformId(platformId);
        tmp.setGmtModified(new Date());
        tmp.setPutawayState(putStatus);
        return tmp;
    }

    private Map<String, String> getPlatformPutStatusMap(
            List<String> platformIds, List<String> status) {
        Map<String, String> map = new HashMap<>(platformIds.size());
        for (int i = 0; i < platformIds.size(); i++) {
            map.put(platformIds.get(i), status.get(i));
        }
        return map;
    }

    // 图片地址迁移方法
    public void picTransfer() {
        // 获取属于商家后台创建的、非白膜、is_deleted为0的产品信息
        List<Product> productList = new ArrayList<>();
        final int limit = 200;
        int pageNo = 0;
        do {
            Map<String, Object> para = new HashMap<>();
            para.put("limitString", "limit " + (pageNo * limit) + ", " + limit);
            para.put("isEffective", "0");
            productList = productService.findMerchantProduct(para);
            pageNo++;
            if (productList != null && productList.size() > 0) {
            }

        } while (productList.size() > 0);
    }

    @Override
    public void fixedProductToSpu() {
        productService.fixedProductToSpu();
    }

    @Override
    public List<Integer> getAllProductIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        String hardProductIds = productService.getHardProductIds(ids);
        return Splitter.on(Punctuation.COMMA)
                .trimResults()
                .splitToList(Strings.nullToEmpty(hardProductIds))
                .stream()
                .filter(StringUtils::isNoneBlank)
                .map(Integer::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> getGoodsIdsByProductIds(List<Integer> productIds) {
        return productService.getGoodsIdsByProductIds(productIds);
    }

    @Override
    public Integer validGroupActivity(List<Integer> ids) {
        return productService.validGroupActivity(ids);
    }

    @Override
    public Integer updateGroupActivity(List<Integer> ids) {
        return productService.updateGroupActivity(ids);
    }

    @Override
    public Integer removeGroupActivity(List<Integer> ids) {
        return productService.removeGroupActivity(ids);
    }

    @Override
    public Integer validGroupActivityBySpuId(List<Integer> ids) {
        return productService.validGroupActivityBySpuId(ids);
    }

    @Override
    public Integer updateGroupActivityBySpuId(List<Integer> ids) {
        return productService.updateGroupActivityBySpuId(ids);
    }

    @Override
    public List<Integer> getProductPutInfo(List<Integer> ids) {
        return productService.getProductPutInfo(ids);
    }
}
